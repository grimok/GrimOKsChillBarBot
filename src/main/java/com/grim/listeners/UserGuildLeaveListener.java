package com.grim.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserGuildLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        List<Role> roles = event.getMember().getRoles();
        StringBuilder sb = new StringBuilder();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        for(int i = 0; i < roles.size(); i++){
            sb.append(roles.get(i).getAsMention()).append(" ");
        }
        builder.setAuthor(event.getMember().getUser().getAsTag(),null,event.getMember().getEffectiveAvatarUrl());
        builder.setColor(Color.RED);
        builder.addField("","Rolleri: "+ sb,false);
        builder.setFooter(dtf.format(now),event.getGuild().getIconUrl());

        event.getGuild().getTextChannelById("1014491585972543489").sendMessageEmbeds(builder.build()).queue();
    }
}
