package com.grim.listeners;

import com.grim.database.DatabaseConnection;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class MessageDeleteListener extends ListenerAdapter {

    @Override
    public void onMessageDelete(@NotNull MessageDeleteEvent event) {
        if (event.isFromGuild()){
            DatabaseConnection connection = new DatabaseConnection();
            if (connection.isDBContainsMessage(event.getMessageId())){

                Member member = event.getJDA().getGuildById("975007505744793660").getMemberById(connection.getAuthorID(event.getMessageId()));

                if (!(member == null)){
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.addField("","***"+member.getEffectiveName()+"***"+" kullanıcısının mesajı silindi",false);
                    builder.addField("","Kanal: "+ event.getChannel().getAsMention(),false);
                    builder.addField("Mesaj","`"+connection.getMessage(event.getMessageId())+"`",false);
                    builder.setColor(Color.ORANGE);

                    builder.setAuthor(member.getUser().getAsTag(),null,member.getEffectiveAvatarUrl());

                    event.getGuild().getTextChannelById("1014503825698209922").sendMessageEmbeds(builder.build()).queue();

                    connection.dropMessageData(event.getMessageId());
                }
            }
        }
    }
}
