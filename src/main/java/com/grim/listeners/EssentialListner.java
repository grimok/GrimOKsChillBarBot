package com.grim.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.ChannelDeleteEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleRemoveEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.events.role.update.RoleUpdatePermissionsEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.pagination.AuditLogPaginationAction;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EssentialListner extends ListenerAdapter {

    @Override
    public void onGuildMemberRoleAdd(@NotNull GuildMemberRoleAddEvent event) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        AuditLogPaginationAction logs = event.getGuild().retrieveAuditLogs();
        logs.type(ActionType.MEMBER_ROLE_UPDATE);
        logs.limit(1);
        logs.queue(entry ->{
            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor(event.getUser().getAsTag(),null,event.getMember().getEffectiveAvatarUrl());
            builder.setDescription("Bir kullanıcının rolleri düzenlendi!");
            builder.addField("<:GCB_Staff:1014898764693119036> Düzenen Kullanıcı: ",event.getMember().getAsMention(),false);
            builder.addField("<a:GCB_verify:1014897033376698490>  Eklenen Rol: ",event.getRoles().get(0).getAsMention(),false);
            builder.addField("<:GCB_mod:1014897017694203975> Silen Yetkili: ",entry.get(0).getUser().getAsMention(), false);
            builder.addField("","Daha fazla bilgi **Audit Log'da** bulunabilir.",false);
            builder.setFooter(dtf.format(now),event.getGuild().getIconUrl());

            event.getGuild().getTextChannelById("1014628425648066722").sendMessageEmbeds(builder.build()).queue();
        });

    }

    @Override
    public void onGuildMemberRoleRemove(@NotNull GuildMemberRoleRemoveEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        AuditLogPaginationAction logs = event.getGuild().retrieveAuditLogs();
        logs.type(ActionType.MEMBER_ROLE_UPDATE);
        logs.limit(1);
        logs.queue(entry ->{
            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor(event.getUser().getAsTag(),null,event.getMember().getEffectiveAvatarUrl());
            builder.setDescription("Bir kullanıcının rolleri düzenlendi!");
            builder.addField("<:GCB_Staff:1014898764693119036> Düzenen Kullanıcı: ",event.getMember().getAsMention(),false);
            builder.addField(":no_entry: Silinen Rol: ",event.getRoles().get(0).getAsMention(),false);
            builder.addField("<:GCB_mod:1014897017694203975> Silen Yetkili: ",entry.get(0).getUser().getAsMention(), false);
            builder.addField("","Daha fazla bilgi **Audit Log'da** bulunabilir.",false);
            builder.setFooter(dtf.format(now),event.getGuild().getIconUrl());

            event.getGuild().getTextChannelById("1014628425648066722").sendMessageEmbeds(builder.build()).queue();
        });

    }

    @Override
    public void onChannelCreate(@NotNull ChannelCreateEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Yeni bir kanal oluşturuldu",null,event.getGuild().getIconUrl());
        builder.setDescription("Yeni bir kanal oluşturuldu!");
        builder.addField("Kanal: ",event.getChannel().getAsMention(),false);
        builder.addField("Tür: ",event.getChannelType().name(),false);
        builder.addField("","Daha fazla bilgi **Audit Log'da** bulunabilir.",false);
        builder.setFooter(dtf.format(now),event.getGuild().getIconUrl());

        event.getGuild().getTextChannelById("1014628425648066722").sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public void onChannelDelete(@NotNull ChannelDeleteEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Mevcut bir kanal silindi",null,event.getGuild().getIconUrl());
        builder.addField("Kanal: ",event.getChannel().getName(),false);
        builder.addField("Tür: ",event.getChannelType().name(),false);
        builder.addField("","Daha fazla bilgi **Audit Log'da** bulunabilir.",false);
        builder.setFooter(dtf.format(now),event.getGuild().getIconUrl());

        event.getGuild().getTextChannelById("1014628425648066722").sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public void onRoleCreate(@NotNull RoleCreateEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Yeni bir rol oluşturuldu",null,event.getGuild().getIconUrl());
        builder.addField("Rol: ",event.getRole().getAsMention(),false);
        builder.addField("Kanalları Görme: ", String.valueOf(event.getRole().hasPermission(Permission.VIEW_CHANNEL)),true);
        builder.addField("Kanalları Düzenleme: ", String.valueOf(event.getRole().hasPermission(Permission.MANAGE_CHANNEL)),true);
        builder.addField("Rolleri Düzenleme: ", String.valueOf(event.getRole().hasPermission(Permission.MANAGE_ROLES)),true);
        builder.addField("Emoji ve Sticker Düzenleme: ", String.valueOf(event.getRole().hasPermission(Permission.MANAGE_EMOJIS_AND_STICKERS)),true);
        builder.addField("Log Görebilme: ", String.valueOf(event.getRole().hasPermission(Permission.VIEW_AUDIT_LOGS)),true);
        builder.addField("Webhook Düzenleme: ", String.valueOf(event.getRole().hasPermission(Permission.MANAGE_WEBHOOKS)),true);
        builder.addField("Sever Insight Görebilme: ", String.valueOf(event.getRole().hasPermission(Permission.VIEW_GUILD_INSIGHTS)),true);
        builder.addField("Invite Oluşturabilme: ", String.valueOf(event.getRole().hasPermission(Permission.CREATE_INSTANT_INVITE)),true);
        builder.addField("Kullanıcıları Atabilme: ", String.valueOf(event.getRole().hasPermission(Permission.KICK_MEMBERS)),true);
        builder.addField("Kullanıcıları Yasaklayabilme: ", String.valueOf(event.getRole().hasPermission(Permission.BAN_MEMBERS)),true);
        builder.addField("Mesajları Düzenleyebilme: ", String.valueOf(event.getRole().hasPermission(Permission.MESSAGE_MANAGE)),true);
        builder.addField("Mesaj Geçmişine Erişebilme: ", String.valueOf(event.getRole().hasPermission(Permission.MESSAGE_HISTORY)),true);
        builder.addField("Herkesi Etiketleyebilme: ", String.valueOf(event.getRole().hasPermission(Permission.MESSAGE_MENTION_EVERYONE)),true);
        builder.addField("Sunucuyu Düzenleyebilme: ", String.valueOf(event.getRole().hasPermission(Permission.MANAGE_SERVER)),true);
        builder.addField("Administrator: ", String.valueOf(event.getRole().hasPermission(Permission.ADMINISTRATOR)),true);

        builder.addField("","Daha fazla bilgi **Audit Log'da** bulunabilir.",false);
        builder.setFooter(dtf.format(now),event.getGuild().getIconUrl());

        event.getGuild().getTextChannelById("1014628425648066722").sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public void onRoleUpdatePermissions(@NotNull RoleUpdatePermissionsEvent event) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        AuditLogPaginationAction logs = event.getGuild().retrieveAuditLogs();
        logs.type(ActionType.ROLE_UPDATE);
        logs.limit(1);
        logs.queue(entry ->{
            AuditLogEntry entry1 = entry.get(0);
            List<Permission> oldList = new ArrayList<>(event.getOldPermissions());
            List<Permission> newList = new ArrayList<>(event.getNewPermissions());
            List<Permission> subOTN;
            List<Permission> subNTO;

            oldList.removeAll(newList);
            subOTN = oldList;
            oldList = new ArrayList<>(event.getOldPermissions());
            newList.removeAll(oldList);
            subNTO = newList;

            if (subNTO.isEmpty() && !subOTN.isEmpty()){
                EmbedBuilder builder = new EmbedBuilder();
                builder.setAuthor("Mevcut bir rol düzenlendi",null,event.getGuild().getIconUrl());
                builder.addField("Düzenlenen Rol: ",event.getRole().getAsMention(),false);
                for (int i = 0; i < subOTN.size(); i++){
                    builder.addField("\uD83D\uDED1 Kaldırılan Yetki:",subOTN.get(i).getName(),true);
                }
                builder.addField("Düzenleyen Yetkili:",entry1.getUser().getAsMention(),false);
                builder.addField("","Daha fazla bilgi **Audit Log'da** bulunabilir.",false);
                builder.setFooter(dtf.format(now),event.getGuild().getIconUrl());

                event.getGuild().getTextChannelById("1014628425648066722").sendMessageEmbeds(builder.build()).queue();
            }else if(!subNTO.isEmpty() && subOTN.isEmpty()){
                EmbedBuilder builder = new EmbedBuilder();
                builder.setAuthor("Mevcut bir rol düzenlendi",null,event.getGuild().getIconUrl());
                builder.addField("Düzenlenen Rol: ",event.getRole().getAsMention(),false);
                for (int i = 0; i < subNTO.size(); i++){
                    builder.addField("✅ Eklenen Yetki:",subNTO.get(i).getName(),true);
                }
                builder.addField("Düzenleyen Yetkili:",entry1.getUser().getAsMention(),false);
                builder.addField("","Daha fazla bilgi **Audit Log'da** bulunabilir.",false);
                builder.setFooter(dtf.format(now),event.getGuild().getIconUrl());

                event.getGuild().getTextChannelById("1014628425648066722").sendMessageEmbeds(builder.build()).queue();
            }else if(!subNTO.isEmpty() && !subOTN.isEmpty()){
                EmbedBuilder builder = new EmbedBuilder();
                builder.setAuthor("Mevcut bir rol düzenlendi",null,event.getGuild().getIconUrl());
                builder.addField("Düzenlenen Rol: ",event.getRole().getAsMention(),false);
                for (int i = 0; i < subNTO.size(); i++){
                    builder.addField("✅ Eklenen Yetki:",subNTO.get(i).getName(),true);
                }
                for (int i = 0; i < subOTN.size(); i++){
                    builder.addField("\uD83D\uDED1 Kaldırılan Yetki:",subOTN.get(i).getName(),true);
                }
                builder.addField("Düzenleyen Yetkili:",entry1.getUser().getAsMention(),false);
                builder.addField("","Daha fazla bilgi **Audit Log'da** bulunabilir.",false);
                builder.setFooter(dtf.format(now),event.getGuild().getIconUrl());

                event.getGuild().getTextChannelById("1014628425648066722").sendMessageEmbeds(builder.build()).queue();
            }
        });



    }

    @Override
    public void onRoleDelete(@NotNull RoleDeleteEvent event) {
        super.onRoleDelete(event);
    }
}
