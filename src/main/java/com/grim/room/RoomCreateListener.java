package com.grim.room;

import com.grim.database.DatabaseConnection;
import com.mysql.cj.xdevapi.JsonArray;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.util.Collection;
import java.util.Collections;

public class RoomCreateListener extends ListenerAdapter {

    ActiveRooms activeRooms = new ActiveRooms();

    @Override
    public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
        if (event.getMember().getUser().isBot()) return;
        if (event.getChannelJoined().getId().equals("1014790201052233830")){
            if(!activeRooms.getActiveRooms().containsKey(event.getMember().getId())){
                DatabaseConnection connection = new DatabaseConnection();
                if (connection.isUserRegisteredToPVC(event.getMember().getId())){
                    event.getGuild().getCategoryById("982598159568097331").createVoiceChannel(connection.getRoomName(event.getMember().getId())).queue(channel ->{
                        String id = channel.getId();
                        channel.getManager().setUserLimit(connection.getRoomLimit(event.getMember().getId())).queue();
                        JSONArray array = new JSONArray(connection.getRoomAllowed(event.getMember().getId()));
                        if (connection.isRoomLocked(event.getMember().getId())){
                            channel.getManager().putRolePermissionOverride(event.getGuild().getPublicRole().getIdLong(),null,Collections.singleton(Permission.VOICE_CONNECT)).queue();
                        }
                        if (!array.isEmpty()){
                            for (int i = 0; i < array.length();i++){
                                String userID = array.getString(i);
                                channel.getManager().putMemberPermissionOverride(Long.parseLong(userID),Collections.singleton(Permission.VOICE_CONNECT),null).queue();
                            }
                        }
                        activeRooms.getActiveRooms().put(event.getMember().getId(),id);
                        event.getGuild().moveVoiceMember(event.getMember(),channel).queue();
                        System.out.println(activeRooms.getActiveRooms().toString());
                    });
                }else{
                    connection.insertRoomToDatabase(event.getMember());
                    event.getGuild().getCategoryById("982598159568097331").createVoiceChannel(event.getMember().getEffectiveName()+"ˈnın Odası").queue(channel ->{
                        String id = channel.getId();
                        channel.getManager().setUserLimit(connection.getRoomLimit(event.getMember().getId())).queue();
                        activeRooms.getActiveRooms().put(event.getMember().getId(),id);
                        event.getGuild().moveVoiceMember(event.getMember(),channel).queue();
                        System.out.println(activeRooms.getActiveRooms().toString());
                    });
                }

            }
        }
    }

    @Override
    public void onGuildVoiceMove(@NotNull GuildVoiceMoveEvent event) {
        if (event.getMember().getUser().isBot()) return;
        if (event.getChannelJoined().getId().equals("1014790201052233830")){
            if(!ActiveRooms.getActiveRooms().containsKey(event.getMember().getId())){
                event.getGuild().getCategoryById("982598159568097331").createVoiceChannel(event.getMember().getEffectiveName()+"'nın Odası").queue(channel ->{
                    String id = channel.getId();
                    ActiveRooms.getActiveRooms().put(event.getMember().getId(),id);
                    event.getGuild().moveVoiceMember(event.getMember(),channel).queue();
                    System.out.println(ActiveRooms.getActiveRooms().toString());
                });
            }
        }
    }
}
