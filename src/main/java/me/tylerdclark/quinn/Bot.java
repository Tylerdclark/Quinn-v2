package me.tylerdclark.quinn;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Bot {



    public static void main(String[] arguments) throws LoginException {
        JDA api = JDABuilder.createDefault(Config.get("TOKEN"))
                .setActivity(Activity.watching("Humans age."))
                .addEventListeners(new Listener())
                .build();
    }
}
