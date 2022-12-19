package com.grim.listeners;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SpamProtectionListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (!event.isFromGuild()) return;
        Role role = event.getGuild().getRoleById("982379694022336563");
            if (event.getMember().getRoles().contains(role)){
                List<Character> chars = event.getMessage().getContentRaw().chars().mapToObj(chr -> (char) chr).toList();
                int size = chars.size();
                int caps = 0;

                //Test
                for (char a : chars){
                    if (Character.isUpperCase(a)){
                        caps += 1;
                    }
                }

                if ((float) caps/size >= 0.3){
                    event.getMessage().delete().queue();
                    event.getChannel().sendMessage("❗ Hey " + event.getMember().getAsMention() +" çok fazla caps kullanıyorsun! Bu şekilde devam edersen **susturulabilirsin.**").queue((m) -> m.delete().queueAfter(5, TimeUnit.SECONDS));

                }

            }


    }
}
