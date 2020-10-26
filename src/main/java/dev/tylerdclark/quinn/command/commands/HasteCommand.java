package me.tylerdclark.quinn.command.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.duncte123.botcommons.web.ContentType;
import me.duncte123.botcommons.web.WebParserUtils;
import me.duncte123.botcommons.web.WebUtils;
import me.tylerdclark.quinn.command.CommandContext;
import me.tylerdclark.quinn.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.function.Consumer;

public class HasteCommand implements ICommand {

    private static final String HASTE_SERVER = "https://hastebin.com/";

    @Override
    public void handle(CommandContext ctx) {

        final TextChannel channel = ctx.getChannel();

        if (ctx.getArgs().isEmpty()){
            channel.sendMessage("This command needs arguments").queue();
            return;
        }

        final String invoke = this.getName();
        final String contentRaw = ctx.getMessage().getContentRaw();
        final int index = contentRaw.indexOf(invoke) + invoke.length();
        final String body = contentRaw.substring(index).trim();

        this.createPaste(body, (text) -> {
            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle(text,text)
                    .setDescription("```")
                    .appendDescription("\n")
                    .appendDescription(body)
                    .appendDescription("```");
            channel.sendMessage(builder.build()).queue();
        });

    }

    private void createPaste(String text, Consumer<String> callback) {
        RequestBody body = RequestBody.create(null, text.getBytes());
        Request request = WebUtils.defaultRequest()
                .post(body)
                .addHeader("Content-Type", ContentType.TEXT_HTML.getType())
                .url(HASTE_SERVER+"documents")
                .build();

        WebUtils.ins.prepareRaw(request, (req) -> WebParserUtils.toJSONObject(req, new ObjectMapper())).async(
                (json) -> {
                    String key = json.get("key").asText();
                    System.out.println(json.get("language").asText());
                    callback.accept(HASTE_SERVER+key);
                },
                (err) -> callback.accept("Error: "+err.getMessage())
        );
    }

    @Override
    public String getName() {
        return "haste";
    }

    @Override
    public String getHelp() {
        return "Posts text to hastebin and returns URL\n"+
                "Usage: `!!haste <test>`";
    }
}
