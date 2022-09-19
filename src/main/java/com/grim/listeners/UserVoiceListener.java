package com.grim.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserVoiceListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getMember().getUser().getAsTag(),null,event.getMember().getEffectiveAvatarUrl());
        builder.setDescription(event.getMember().getAsMention() + " Bir sesli kanala geçiş yaptı.");
        builder.addField("Ayrılınan Kanal:","`" + event.getChannelLeft().getName() + "`",false);
        builder.addField("Katılınan Kanal:","`" + event.getChannelJoined().getName() + "`",false);
        builder.setFooter(dtf.format(now));
        builder.setColor(Color.ORANGE);

        event.getGuild().getTextChannelById("1015320229234081812").sendMessageEmbeds(builder.build()).queue();

    }

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getMember().getUser().getAsTag(),null,event.getMember().getEffectiveAvatarUrl());
        builder.setDescription(event.getMember().getAsMention() + " Bir sesli kanala katıldı.");
        builder.addField("Katılınan Kanal:","`" + event.getChannelJoined().getName() + "`",false);
        builder.setFooter(dtf.format(now));
        builder.setColor(Color.GREEN);

        event.getGuild().getTextChannelById("1015320229234081812").sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(event.getMember().getUser().getAsTag(),null,event.getMember().getEffectiveAvatarUrl());
        builder.setDescription(event.getMember().getAsMention() + " Sesli kanaldan ayrıldı.");
        builder.addField("Ayrılınan Kanal:","`" + event.getChannelLeft().getName() + "`",false);
        builder.setFooter(dtf.format(now));
        builder.setColor(Color.RED);

        event.getGuild().getTextChannelById("1015320229234081812").sendMessageEmbeds(builder.build()).queue();
    }
}
