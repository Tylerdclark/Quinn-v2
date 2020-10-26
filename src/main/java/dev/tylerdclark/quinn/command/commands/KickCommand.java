package me.tylerdclark.quinn.command.commands;

import me.tylerdclark.quinn.command.CommandContext;
import me.tylerdclark.quinn.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class KickCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {
        final TextChannel textChannel = ctx.getChannel();
        final Message message = ctx.getMessage();
        final Member member = ctx.getMember();
        final List<String> args = ctx.getArgs();

        if (args.size() < 2 || message.getMentionedMembers().isEmpty()){
            textChannel.sendMessage("Missing arguments").queue();
            return;
        }

        final Member target = message.getMentionedMembers().get(0);

        if (!member.canInteract(target) || !member.hasPermission(Permission.KICK_MEMBERS)){
            textChannel.sendMessage("You are missing permissions to kick this member.").queue();
            return;
        }

        final Member selfMember = ctx.getSelfMember();

        if (!selfMember.canInteract(target) || !selfMember.hasPermission(Permission.KICK_MEMBERS)){
            textChannel.sendMessage("I am missing permissions to kick this member.").queue();
            return;
        }

        final String reason = String.join(" ", args.subList(1, args.size()));

        ctx.getGuild()
                .kick(target, reason)
                .reason(reason)
                .queue(
                        (__) ->textChannel.sendMessage("Kick was successful.").queue(),
                        (error) -> textChannel.sendMessageFormat("Could not kick &s" , error.getMessage()).queue()
                );

    }

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getHelp() {
        return "Kick a member off the server.\n" +
                "Usage: `!!kick <@user> <reason>`";
    }
}
