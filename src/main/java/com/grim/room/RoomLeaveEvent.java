package com.grim.room;

import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class RoomLeaveEvent extends ListenerAdapter {

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        if (ActiveRooms.getActiveRooms().containsValue(event.getChannelLeft().getId())){
            if (event.getChannelLeft().getMembers().size() == 0){
                event.getChannelLeft().delete().queue();
                ActiveRooms.getActiveRooms().values().remove(event.getChannelLeft().getId());
                System.out.println(ActiveRooms.getActiveRooms().toString());
            }
        }
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        if (ActiveRooms.getActiveRooms().containsValue(event.getChannelLeft().getId())){
            if (event.getChannelLeft().getMembers().size() == 0){
                event.getChannelLeft().delete().queue();
                ActiveRooms.getActiveRooms().values().remove(event.getChannelLeft().getId());
                System.out.println(ActiveRooms.getActiveRooms().toString());
            }
        }
    }
}
