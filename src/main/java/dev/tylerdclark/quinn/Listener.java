package dev.tylerdclark.quinn;

import me.duncte123.botcommons.BotCommons;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Listener extends ListenerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private final CommandManager manager = new CommandManager();

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());

    }


    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        User user = event.getAuthor();

        if (user.isBot() || event.isWebhookMessage()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        String prefix = Config.get("PREFIX");
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        MessageChannel channel = event.getChannel();

        if (content.equalsIgnoreCase(prefix + "shutdown")
                && user.getId().equals(Config.get("OWNER_ID"))) {

            LOGGER.info("Shutting down..");
            event.getJDA().shutdown();
            BotCommons.shutdown(event.getJDA());

        }else if(content.startsWith(prefix)){
            manager.handle(event);
        } else{
            channel.sendMessage(content).queue();
        }

    }
}
