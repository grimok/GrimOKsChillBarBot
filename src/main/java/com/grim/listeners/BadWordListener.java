package com.grim.listeners;

import com.grim.utils.JsonReader;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BadWordListener extends ListenerAdapter {
    private LinkedList<String> a = new LinkedList<>();

    public BadWordListener() throws IOException {
        final JSONArray BAD_WORDS = JsonReader.readJsonArrayFromUrl("https://raw.githubusercontent.com/ooguz/turkce-kufur-karaliste/master/karaliste.json");
        List<?> badwordlist = BAD_WORDS.toList();
        for (int i = 0; i <= badwordlist.size() - 1; i++){
            a.add(BAD_WORDS.getString(i));
        }
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if (!event.isFromGuild()) return;

        for (String w : a){
            if (!event.getAuthor().isBot()){
                List<String> message2 = Arrays.stream(event.getMessage().getContentRaw().split(" ")).collect(Collectors.toList());
                List<String> edit = new ArrayList<>();
                message2.forEach( m->{
                    edit.add(m.toLowerCase(Locale.ROOT));
                });
                if (edit.contains(w)){

                    if (w.equalsIgnoreCase("allah")) return;
                    if (w.equalsIgnoreCase("aptal")) return;
                    if (w.equalsIgnoreCase("salak")) return;
                    if (w.equalsIgnoreCase("anan")) return;
                    if (w.equalsIgnoreCase("anneni")) return;
                    if (w.equalsIgnoreCase("amk")) return;
                    if (w.equalsIgnoreCase("aq")) return;

                        event.getChannel().sendMessage("❗ Hey " + event.getMember().getAsMention() +" lütfen kelimelerine dikkat et! Eğer küfür etmeye devam edersen **susturulabilirsin.**").queue((m) -> m.delete().queueAfter(5, TimeUnit.SECONDS));
                        event.getMessage().delete().queue(null);
                        System.out.println(w);
                        TextChannel channel = event.getJDA().getTextChannelById("1014503825698209922");

                        if (channel != null){

                            EmbedBuilder builder = new EmbedBuilder();
                            builder.addField("","\uD83D\uDDD1" + " " + event.getMember().getAsMention() + " kullanıcısı küfür ettiği için mesajı silindi.\n",false);//0
                            builder.addField("","\uD83D\uDED1" + " Kanal: " + event.getChannel().getAsMention(),false); //2
                            builder.addField("", "\uD83D\uDED1" + " Algılanan Küfür: " + w , false); //3
                            builder.addField("", "\uD83D\uDED1" + " Tüm Mesaj: " + "`" + event.getMessage().getContentRaw() + "`", false); //3
                            builder.addField("", "\uD83C\uDD94" + " Kullanıcı ID: " + event.getMember().getId(),false); //4
                            builder.setColor(Color.BLACK);

                            builder.setAuthor(event.getMember().getUser().getAsTag(),null,event.getMember().getAvatarUrl());

                            MessageCreateData builder1 = new MessageCreateBuilder().setEmbeds(builder.build()).build();

                            channel.sendMessage(builder1).queue();

                    }
                    break;
                }

            }
        }

    }

}
