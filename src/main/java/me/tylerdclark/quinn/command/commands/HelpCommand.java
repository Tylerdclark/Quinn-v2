package me.tylerdclark.quinn.command.commands;

import me.tylerdclark.quinn.CommandManager;
import me.tylerdclark.quinn.command.CommandContext;
import me.tylerdclark.quinn.command.ICommand;

import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        Tex
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Shows the list with commands in the bot\n" +
                "Usage: `!!help [command]`";
    }
}
