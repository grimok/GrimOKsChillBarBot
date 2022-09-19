package com.grim.listeners;

import com.grim.moderation.BanCommand;
import com.grim.moderation.KickCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegModalListener extends ListenerAdapter{

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {

        Role member = event.getGuild().getRoleById("975332688372301885");
        Role visitor = event.getGuild().getRoleById("1014482530902212629");

        if (event.getModalId().equals("modal-reg")){

            String name = event.getValue("user-name").getAsString();
            String age = event.getValue("user-age").getAsString();

            TextChannel channel = event.getGuild().getTextChannelById("1014491585972543489");

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            EmbedBuilder builder = new EmbedBuilder();

            builder.addField("","\uD83C\uDD95" + " " + event.getMember().getAsMention() + " Sunucuya yeni kaydoldu!\n",false);//0
            builder.addField("","\uD83D\uDCEB" + " İsim: " + name,false); //2
            builder.addField("", "\uD83D\uDD1E" + " Yaş: " + age , false); //3
            builder.addField("", "\uD83D\uDCC5" + " Katılım Tarihi: " + dtf.format(now), false); //3
            builder.setColor(Color.GREEN);

            builder.setAuthor(event.getMember().getUser().getAsTag(),null,event.getMember().getAvatarUrl());

            MessageCreateData builder1 = new MessageCreateBuilder().setEmbeds(builder.build()).build();
            channel.sendMessage(builder1).queue();

            assert member != null;
            assert visitor != null;
            event.getGuild().addRoleToMember(event.getMember(),member).queue();
            event.getGuild().removeRoleFromMember(event.getMember(),visitor).queue();

            event.reply("Giriş Başarılı! Teşekkürler.").setEphemeral(true).queue();
        }else if(event.getModalId().equals("modal-kick")){
            String reason = event.getValue("kick-reason").getAsString();

            Member kicked = new KickCommand().getLastMember();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor(kicked.getUser().getAsTag(),null,kicked.getEffectiveAvatarUrl());
            builder.setDescription("Bir kullanıcı sunucudan atıldı.");
            builder.addField("\uD83D\uDED1 Atılma Sebebi:",reason,false);

            event.getGuild().getTextChannelById("1014628425648066722").sendMessageEmbeds(builder.build()).queue();

            event.getGuild().kick(kicked).queue();

            event.reply("Atma işlemi tamamlandı").setEphemeral(true).queue();
        }else if(event.getModalId().equals("modal-ban")){
            String reason = event.getValue("ban-reason").getAsString();

            Member kicked = new BanCommand().getLastMember();

            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor(kicked.getUser().getAsTag(),null,kicked.getEffectiveAvatarUrl());
            builder.setDescription("Bir kullanıcı sunucudan yasaklanndı.");
            builder.addField("\uD83D\uDED1 Yasaklanma Sebebi:",reason,false);
            builder.setColor(Color.red);

            event.getGuild().getTextChannelById("1014628425648066722").sendMessageEmbeds(builder.build()).queue();

            event.getGuild().ban(kicked,7, reason).queue();

            event.reply("Atma işlemi tamamlandı").setEphemeral(true).queue();
        }
    }

}
