package dev.tylerdclark.quinn;

import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {


    public static void main(String[] arguments) throws LoginException {


        WebUtils.setUserAgent("Mozilla/5.0 Quinn Bot v2#2605 / SoMuchClark#1766");

        JDA api = JDABuilder.createDefault(Config.get("TOKEN"))
                .setActivity(Activity.watching("Humans age."))
                .addEventListeners(new Listener())
                .build();
    }
}
