package com.grim.bot;

import com.grim.level.system.LevelListener;
import com.grim.listeners.*;
import com.grim.modals.RegistrationModal;
import com.grim.moderation.BanCommand;
import com.grim.moderation.KickCommand;
import com.grim.moderation.WarnCommand;
import com.grim.room.RoomCreateListener;
import com.grim.room.RoomLeaveEvent;
import com.grim.utils.Security;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class Bot {

    public static void main(String[] args) throws LoginException, InterruptedException, IOException {

        JDA jda = JDABuilder.createDefault("MTAxNDQ2NTQ4MzIxNjUzNTYwMw.GTY0Qp.phHD1B2SZq0jVbyaa2Syy1WAFM93sS78mBNQ-c", GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.DIRECT_MESSAGE_REACTIONS,GatewayIntent.DIRECT_MESSAGES,GatewayIntent.GUILD_BANS,GatewayIntent.GUILD_INVITES,GatewayIntent.DIRECT_MESSAGE_TYPING,
                GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_MESSAGES
                ,GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_PRESENCES).enableIntents(GatewayIntent.GUILD_PRESENCES).enableCache(CacheFlag.ONLINE_STATUS).setChunkingFilter(ChunkingFilter.ALL).setEventPassthrough(true).enableCache(CacheFlag.VOICE_STATE).setMemberCachePolicy(MemberCachePolicy.ALL).build().awaitReady();

                jda.getPresence().setActivity(Activity.streaming("Bardaki insanları izliyor","https://www.twitch.tv/grimokay"));


        jda.addEventListener(new BadWordListener());
        jda.addEventListener(new RegistrationModal());
        jda.addEventListener(new RegModalListener());
        jda.addEventListener(new NewMemberListener());
        jda.addEventListener(new SlashCommandListener());
        jda.addEventListener(new Security());
        jda.addEventListener(new MessageDeleteListener());
        jda.addEventListener(new MessageEntryListener());
        jda.addEventListener(new RoomLeaveEvent());
        jda.addEventListener(new RoomCreateListener());
        jda.addEventListener(new ColorButtonListener());
        jda.addEventListener(new LevelListener());
        jda.addEventListener(new UserGuildLeaveListener());
        jda.addEventListener(new KickCommand());
        jda.addEventListener(new BanCommand());
        jda.addEventListener(new WarnCommand());
        jda.addEventListener(new MessageEditListener());
        jda.addEventListener(new UserVoiceListener());
        jda.addEventListener(new EssentialListner());
        jda.addEventListener(new SpamProtectionListener());

        jda.upsertCommand("temizle","Girilen sayı kadar mesaj siler.")
                .addOptions(new OptionData(OptionType.INTEGER,"number","Silinecek mesaj sayısını giriniz.",true)
                        .setRequiredRange(5,Integer.MAX_VALUE))
                .queue();

        jda.upsertCommand("level","Seviye kartını gösterir")
                .addSubcommands(new SubcommandData("arka-plan","Seviye kartınızın arka plan fotoğrafını değiştirir.")
                        .addOption(OptionType.STRING,"url",".png veya .jpg destekleyen bir link girin.",true),
                new SubcommandData("renk","Seviye kartınızın rengini değiştirin.")
                        .addOptions(new OptionData(OptionType.INTEGER,"page","Renk sayfasını seçin (1-2).",true)
                        .setRequiredRange(1,2)),
                        new SubcommandData("goster","Eğer bir kullanıcı etiketlenirse onun seviye kartı gösterilir.")
                                .addOption(OptionType.USER,"user","Kullanıcı etiketleyin.",false))
                .queue();

        jda.upsertCommand("oda","Seviye kartını gösterir")
                .addSubcommands(new SubcommandData("isim","Odanızın ismini değiştirir.")
                                .addOption(OptionType.STRING,"name","Lütfen reklam ve küfür içermeyen bir isim girin.",true),
                        new SubcommandData("limit","Odanızın kullanıcı limitini değiştirin.")
                                .addOptions(new OptionData(OptionType.INTEGER,"limit","Odanın kaç kişilik olduğunu belirleyin (2-99).",true)
                                        .setRequiredRange(2,99)),
                        new SubcommandData("kilit","Eğer bir kullanıcı etiketlenirse onun seviye kartı gösterilir.")
                                .addOption(OptionType.BOOLEAN,"islocked","Kullanıcı etiketleyin.",true),
                        new SubcommandData("izin-ver","Kilitli bir odaya birisinin girmesine izin verir.")
                                .addOption(OptionType.USER,"user","Yetki verilecek kullanıcıyı giriniz.",true),
                        new SubcommandData("izin-al","Aktif olarak izinli birisinin yetkisini alır.")
                                .addOption(OptionType.USER,"user","Yetkisi alınacak kullanıcıyı giriniz.",true)).queue();

        jda.upsertCommand("at","Bir kullanıcıyı sunucudan atar.").addOptions(
                new OptionData(OptionType.USER,"user","Atmak istediğiniz kullanıyı girin.",true)).queue();

        jda.upsertCommand("yasakla","Bir kullanıcıyı sunucudan kesin olarak yasaklar.").addOptions(
                new OptionData(OptionType.USER,"user","Yasaklamak istediğiniz kullanıyı girin.",true)).queue();

        jda.upsertCommand("uyar","Sunucudaki bir kullanıyı uyarır.").addOptions(
                new OptionData(OptionType.USER,"user","Uyarmak istediğiniz kullanıyı girin.",true),
                new OptionData(OptionType.STRING,"reason","Uyarı sebebini giriniz.",true)).queue();

        jda.upsertCommand("sustur","Bir kullanıcıya timeout atar.").addOptions(
                new OptionData(OptionType.USER,"user","Susturmak istediğiniz kullanıyı girin.",true)).queue();

    }
}
