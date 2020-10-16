package me.tylerdclark.quinn.command.commands;

import me.tylerdclark.quinn.command.CommandContext;
import me.tylerdclark.quinn.command.ICommand;
import net.dv8tion.jda.api.JDA;

public class PingCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        JDA jda = ctx.getJDA();

        jda.getRestPing().queue((ping) -> ctx.getChannel()
                .sendMessageFormat("Rest ping: %sms\nWS ping: %sms", ping, jda.getGatewayPing()).queue()
        );
    }

    @Override
    public String getName() {
        return "ping";
    }
    @Override
    public String getHelp() {
        return "Shows the current ping for the bot to the discord servers.";
    }
}
