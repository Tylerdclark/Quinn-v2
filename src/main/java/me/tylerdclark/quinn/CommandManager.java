package me.tylerdclark.quinn;

import me.tylerdclark.quinn.command.CommandContext;
import me.tylerdclark.quinn.command.ICommand;
import me.tylerdclark.quinn.command.commands.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
    private final List<ICommand> commands = new ArrayList<>();

    public CommandManager(){
        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));
        addCommand(new HasteCommand());
        addCommand(new KickCommand());
        addCommand(new MemeCommand());
    }

    private void addCommand(ICommand cmd) {
        boolean nameFound = this.commands.stream().anyMatch((it) ->
                it.getName().equalsIgnoreCase(cmd.getName()));

        if (nameFound){
            throw new IllegalArgumentException("A command with this name already exists.");
        }else {
            commands.add(cmd);
        }
    }

    @Nullable
    public ICommand getCommand(String search) {

        String searchLower = search.toLowerCase();

        for (ICommand cmd: commands) {
            if (cmd.getName().equals(searchLower) || cmd.getAliases().contains(searchLower) ){
                return cmd;
            }
        }
        return null;
    }

    public List<ICommand> getCommands() {
        return commands;
    }

    void handle(@NotNull GuildMessageReceivedEvent event){
        String[] split = event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Config.get("PREFIX")), "")
                .split("\\s+");

        String invoke = split[0].toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {
            event.getChannel().sendTyping().queue();
            List<String> args = Arrays.asList(split).subList(1, split.length);

            CommandContext ctx = new CommandContext(event, args);

            cmd.handle(ctx);
        } else {
            event.getChannel().sendMessage(String.format("%s is not a command", invoke)).queue();
        }

    }
}
