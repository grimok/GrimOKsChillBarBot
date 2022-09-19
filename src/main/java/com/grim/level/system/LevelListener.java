package com.grim.level.system;

import com.grim.database.DatabaseConnection;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;

public class LevelListener extends ListenerAdapter {

    List<String> id = new ArrayList<>();
    public HashMap<Member, Long> lastGathered = new HashMap<>();
    LevelCalculator calculator = new LevelCalculator();

    long cooldown = 15000;

    @Override
    public synchronized void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.isFromGuild()){
            if (!event.getMember().getUser().isBot()){
                if (!id.contains(event.getMember().getId())){
                    DatabaseConnection connection = new DatabaseConnection();
                    if (!connection.isUserRegisteredToLevel(event.getMember().getId())){
                        connection.insertUserToDatabase(event.getMember().getId());
                    }
                    lastGathered.put(event.getMember(), System.currentTimeMillis());
                    id.add(event.getMember().getId());
                }else{
                    long time = System.currentTimeMillis();
                    if (time > lastGathered.get(event.getMember())+cooldown){

                        DatabaseConnection connection = new DatabaseConnection();
                        int a = (int) (Math.random() * (10 - 1 + 1)) + 1;
                        connection.updateExp(event.getMember().getId(),a);
                        String message = calculator.updateLevel(event.getMember());
                        if (!message.equals("nan")){
                            Objects.requireNonNull(event.getGuild().getTextChannelById("1014980232182255697")).sendMessage(message).queue();
                        }

                        lastGathered.remove(event.getMember());
                        lastGathered.put(event.getMember(),System.currentTimeMillis());
                    }else{

                    }

                }
            }
        }
    }
}
