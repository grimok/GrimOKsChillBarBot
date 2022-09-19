package com.grim.listeners;

import com.grim.bot.Bot;
import com.grim.database.DatabaseConnection;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;


public class MessageEditListener extends ListenerAdapter {

    @Override
    public void onMessageUpdate(@NotNull MessageUpdateEvent event) {
        if (!event.getMember().getUser().isBot()){
            DatabaseConnection connection = new DatabaseConnection();
            String old = connection.getMessage(event.getMessageId());
            EmbedBuilder builder = new EmbedBuilder();
            builder.setColor(Color.ORANGE);
            builder.setAuthor(event.getMember().getUser().getAsTag(),null,event.getMember().getEffectiveAvatarUrl());
            builder.setDescription(event.getMember().getAsMention() + " bir mesajını editledi.");
            builder.addField("Eski Mesaj:","`" + old + "`",false);
            builder.addField("Yeni mesaj: ","`" + event.getMessage().getContentRaw() + "`",false);

            event.getGuild().getTextChannelById("1014503825698209922").sendMessageEmbeds(builder.build()).queue();

            connection.updateMessage(event.getMessageId(),event.getMessage().getContentRaw().replace("'","ˈ"));
        }
    }
}
