package com.grim.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Security extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.isFromGuild()){
            if (event.getMember().getId().equals("586554541609058329")){
                if (event.getChannel().getId().equals("1014486529403539466")){
                    String[] message = event.getMessage().getContentRaw().split(" ");
                    if (message[0].equals("test")){

                        Button reg = Button.primary("login-button","Giriş Yap!");

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setColor(Color.ORANGE);
                        builder.setTitle("Sunucuya Giriş Yapmak İçin Tıkla!");
                        builder.setDescription("Sunucuya katılmak için lütfen aşağıdaki `Giriş Yap!` butonuna basın.");

                        MessageCreateData messageCreateData = new MessageCreateBuilder().setEmbeds(builder.build()).setActionRow(reg).build();
                        event.getChannel().sendMessage(messageCreateData).queue();

                        System.out.println("Başarılı");

                    }
                }
            }
        }
    }
}
