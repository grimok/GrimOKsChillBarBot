package com.grim.listeners;

import com.grim.database.DatabaseConnection;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageEntryListener extends ListenerAdapter {


    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.isFromGuild()){
            if (!event.getMember().getUser().isBot()){
                DatabaseConnection connection = new DatabaseConnection();
                if (event.getMessage().getAttachments().size() == 0){
                    String message = event.getMessage().getContentRaw().replace("'","ˈ");
                    connection.messageEntryToDB(event.getMessageId(),event.getMember().getId(),message,"0");
                }
            }
        }
    }


}
