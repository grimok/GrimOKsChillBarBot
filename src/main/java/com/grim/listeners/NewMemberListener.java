package com.grim.listeners;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class NewMemberListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {

        Role visitor = event.getGuild().getRoleById("1014482530902212629");

        event.getGuild().addRoleToMember(event.getMember(),visitor).queue();

    }
}
