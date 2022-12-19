package com.grim.listeners;

import com.grim.database.DatabaseConnection;
import com.grim.level.image.ImageDownloader;
import com.grim.level.image.ImageProcessor;
import com.grim.room.ActiveRooms;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.FileUpload;
import net.dv8tion.jda.api.utils.messages.MessageCreateData;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SlashCommandListener extends ListenerAdapter {

    public HashMap<Member, Long> lastGathered = new HashMap<>();
    long cooldown = 60000;

    @Override
    public synchronized void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("temizle")){
            if (event.getMember().hasPermission(Permission.MESSAGE_MANAGE)){
                OptionMapping value = event.getOption("number");
                if (value == null){
                    event.reply("Mesaj sayısı boş bırakılmış gibi. Eğer sorun devam ederse GrimOK ile iletişime geçin.").setEphemeral(true).queue();
                }
                event.getChannel().getHistory().retrievePast(value.getAsInt()).queue(m->event.getGuild().getTextChannelById(event.getChannel().getId()).deleteMessages(m).queue());
                event.reply("<:pandalove:1014442599647563857> Temizlik tamamlandı!").setEphemeral(true).queue();
            }
            //end of temizle
        }if (event.getName().equals("level")){
            if (event.getSubcommandName().equals("goster")){
                OptionMapping value = event.getOption("user");
                if (value == null){
                    ImageProcessor processor = new ImageProcessor();
                    ImageDownloader downloader = new ImageDownloader();

                    try {
                        event.deferReply().queue();
                        DatabaseConnection connection = new DatabaseConnection();
                        downloader.downloadImage(event.getMember().getEffectiveAvatarUrl(),"pp.png",event);
                        String background = connection.getBackground(event.getMember().getId());
                        System.out.println(background);
                        downloader.downloadImage(background,"background.png",event);
                        BufferedImage avatar = processor.processImage("pp.png");
                        BufferedImage vignette = processor.generateVignette(0.6f);
                        //new ColorPicker().convertColor(event.getMember().getId())
                        BufferedImage border = processor.generateBorder(new Color(0,0,0),8);
                        BufferedImage userStatus = processor.generateUserStatus(Objects.requireNonNull(event.getMember()).getOnlineStatus());
                        BufferedImage avatarBorder = processor.generateAvatarBorder(new Color(0,0,0),5);
                        BufferedImage parser = processor.imageParser(vignette,border,avatar,avatarBorder,userStatus,event.getMember()); //Vignate, border, avatar ,status, member
                        ImageIO.write(parser,"png", new File("output.jpg"));
                        FileUpload upload = FileUpload.fromData(new File("output.jpg"));
                        event.getHook().sendFiles(upload).queue();
                    } catch (IOException e) {
                        event.reply("Kullandığınız arka plan .png veya .jpg değil. Lütfen linkin sonundaki dosya uzantısına dikkat edin.").setEphemeral(true).queue();
                    }

                }else{
                    event.deferReply().queue();
                    Member member = value.getAsMember();
                    ImageProcessor processor = new ImageProcessor();
                    ImageDownloader downloader = new ImageDownloader();
                    try {
                        DatabaseConnection connection = new DatabaseConnection();
                        downloader.downloadImage(member.getUser().getEffectiveAvatarUrl(),"pp.png",event);
                        String background = connection.getBackground(member.getId());
                        System.out.println(background);
                        downloader.downloadImage(background,"background.png",event);
                        BufferedImage avatar = processor.processImage("pp.png");
                        BufferedImage vignette = processor.generateVignette(0.6f);
                        BufferedImage border = processor.generateBorder(new Color(0,0,0),8);
                        BufferedImage userStatus = processor.generateUserStatus(Objects.requireNonNull(member).getOnlineStatus());
                        BufferedImage avatarBorder = processor.generateAvatarBorder(new Color(0,0,0),5);
                        BufferedImage parser = processor.imageParser(vignette,border,avatar,avatarBorder,userStatus,member); //Vignate, border, avatar ,status, member
                        // C:\Users\Administrator\Desktop\Bot\
                        ImageIO.write(parser,"png", new File("output.jpg"));
                        FileUpload upload = FileUpload.fromData(new File("output.jpg"));
                        event.getHook().sendFiles(upload).queue();
                    } catch (IOException e) {
                        event.reply("Bakmak istediğiniz kullanıcının arka planı bozuk.").setEphemeral(true).queue();
                    }


                }
            }else if(event.getSubcommandName().equals("arka-plan")){

                OptionMapping value = event.getOption("url");

                Image image = null;
                try {
                    image = ImageIO.read(new URL(value.getAsString()));
                    if(image != null){
                        DatabaseConnection databaseConnection = new DatabaseConnection();
                        databaseConnection.updateBackground(event.getMember().getId(),value.getAsString());
                        event.reply("Arka planınız başarılı bir şekilde değiştirildi.").setEphemeral(true).queue();
                    }else{
                        event.reply("Girilen linkte resim bulunamadı. Lütfen .png veya .jpg olduğuna emin olun.").setEphemeral(true).queue();
                    }
                } catch (IOException e) {
                    event.reply("Girilen link bozuk").setEphemeral(true).queue();
                }


            }else if(event.getSubcommandName().equals("renk")){

                Button white = Button.primary("color-white","\uD83E\uDD0D Beyaz");
                Button magenta = Button.primary("color-magenta","\uD83D\uDC9C Magenta");
                Button cyan = Button.primary("color-cyan","\uD83D\uDC99 Camgöbeği");
                Button red = Button.primary("color-red","❤️ Kırmızı");
                Button gray = Button.primary("color-gray","⚙️ Koyu Gri");
                Button dark_gray = Button.primary("color-darkgray","\uD83E\uDEA8 Gri");
                Button pink = Button.primary("color-pink","\uD83C\uDF38️ Pembe");
                Button green = Button.primary("color-green","\uD83D\uDC9A Yeşil");
                Button yellow = Button.primary("color-yellow","\uD83D\uDC9B Sarı");
                Button blue = Button.primary("color-blue","\uD83D\uDC99 Mavi");

                EmbedBuilder page1 = new EmbedBuilder();
                page1.setTitle("Renk Seçim Menüsü");
                page1.setDescription("<a:GCB_verify:1014897033376698490> Aşağıda sizin için belirli renkler verilmiştir. Lütfen istediğiniz rengin butonuna basınız.");

                EmbedBuilder page2 = new EmbedBuilder();
                page2.setTitle("Renk Seçim Menüsü");
                page2.setDescription("<a:GCB_verify:1014897033376698490> Aşağıda sizin için belirli renkler verilmiştir. Lütfen istediğiniz rengin butonuna basınız.");

                OptionMapping value = event.getOption("page");

                if (value == null){
                    event.reply("Beklenmedik bir hata oluştu :thinking:").queue();
                }else{
                    if (value.getAsInt() == 1){
                        event.replyEmbeds(page1.build())
                                .setActionRow(white,magenta,cyan,red,gray)
                                .setEphemeral(true)
                                .queue();
                    } else if (value.getAsInt() == 2) {
                        event.replyEmbeds(page2.build())
                                .setActionRow(dark_gray,pink,green,yellow,blue)
                                .setEphemeral(true)
                                .queue();
                    }
                }

            }
        }if (event.getName().equals("oda")){
            long time = System.currentTimeMillis();
            if (!lastGathered.containsKey(event.getMember())){
                lastGathered.put(event.getMember(),System.currentTimeMillis() - cooldown - 2000);
            }
            if (time > lastGathered.get(event.getMember())+cooldown){
                ActiveRooms rooms = new ActiveRooms();
                if (event.getMember().getVoiceState().inAudioChannel()){
                    if (event.getSubcommandName().equals("isim")){
                        System.out.println(rooms.getActiveRooms().toString());
                        if (rooms.getActiveRooms().containsKey(event.getMember().getId())){
                            if (event.getMember().getVoiceState().getChannel().getId().equals(rooms.getActiveRooms().get(event.getMember().getId()))){
                                OptionMapping v = event.getOption("name");
                                event.getGuild().getVoiceChannelById(rooms.getActiveRooms().get(event.getMember().getId())).getManager().setName(v.getAsString()).queue();
                                DatabaseConnection connection = new DatabaseConnection();
                                connection.updateRoomName(event.getMember().getId(),v.getAsString());
                                event.reply("<a:GCB_verify:1014897033376698490> Odanızın ismi başarılı bir şekilde değiştirildi : " + v.getAsString()).queue();
                            }else{
                                event.reply("Sadece kendi odanızda işlem yapabilirsiniz!").setEphemeral(true).queue();
                            }
                        }else{
                            event.reply("Aktif bir odanız bulunamadı!").setEphemeral(true).queue();
                        }
                    }else if(event.getSubcommandName().equals("limit")){
                        if (rooms.getActiveRooms().containsKey(event.getMember().getId())){
                            if (event.getMember().getVoiceState().getChannel().getId().equals(rooms.getActiveRooms().get(event.getMember().getId()))){
                                OptionMapping v = event.getOption("limit");
                                event.getGuild().getVoiceChannelById(rooms.getActiveRooms().get(event.getMember().getId())).getManager().setUserLimit(v.getAsInt()).queue();
                                DatabaseConnection connection = new DatabaseConnection();
                                connection.updateRoomLimit(event.getMember().getId(),v.getAsInt());
                                event.reply("<a:GCB_verify:1014897033376698490> Oda limitiniz başarılı bir şekilde değiştirildi : " + v.getAsString()).queue();
                            }else{
                                event.reply("Sadece kendi odanızda işlem yapabilirsiniz!").setEphemeral(true).queue();
                            }
                        }else{
                            event.reply("Aktif bir odanız bulunamadı!").setEphemeral(true).queue();
                        }
                    } else if (event.getSubcommandName().equals("kilit")) {
                        if (rooms.getActiveRooms().containsKey(event.getMember().getId())){
                            if (event.getMember().getVoiceState().getChannel().getId().equals(rooms.getActiveRooms().get(event.getMember().getId()))){
                                OptionMapping v = event.getOption("islocked");
                                if (v.getAsBoolean()){
                                    event.getGuild().getVoiceChannelById(rooms.getActiveRooms().get(event.getMember().getId())).getManager().putRolePermissionOverride(event.getGuild().getPublicRole().getIdLong(),null, Collections.singleton(Permission.VOICE_CONNECT)).queue();
                                    DatabaseConnection connection = new DatabaseConnection();
                                    connection.updateRoomLock(event.getMember().getId(),1);
                                    event.reply("<a:GCB_verify:1014897033376698490> Odanız artık kitledi!").queue();
                                }else{
                                    event.getGuild().getVoiceChannelById(rooms.getActiveRooms().get(event.getMember().getId())).getManager().putRolePermissionOverride(event.getGuild().getPublicRole().getIdLong(),Collections.singleton(Permission.VOICE_CONNECT), null).queue();
                                    DatabaseConnection connection = new DatabaseConnection();
                                    connection.updateRoomLock(event.getMember().getId(),0);
                                    event.reply("<a:GCB_verify:1014897033376698490> Odanız artık kitli değil!").queue();
                                }

                            }else{
                                event.reply("Sadece kendi odanızda işlem yapabilirsiniz!").setEphemeral(true).queue();
                            }
                        }else{
                            event.reply("Aktif bir odanız bulunamadı!").setEphemeral(true).queue();
                        }
                    } else if (event.getSubcommandName().equals("izin-ver")) {
                        if (rooms.getActiveRooms().containsKey(event.getMember().getId())){
                            if (event.getMember().getVoiceState().getChannel().getId().equals(rooms.getActiveRooms().get(event.getMember().getId()))){
                                OptionMapping v = event.getOption("user");
                                event.getGuild().getVoiceChannelById(rooms.getActiveRooms().get(event.getMember().getId())).getManager().putMemberPermissionOverride(v.getAsMember().getIdLong(),Collections.singleton(Permission.VOICE_CONNECT),null).queue();
                                DatabaseConnection connection = new DatabaseConnection();
                                connection.addAllowedMember(event.getMember().getId(),v.getAsMember().getId());
                                event.reply("<a:GCB_verify:1014897033376698490> Odanıza bir kullanıcı eklendi : " + "`" + v.getAsMember().getUser().getAsTag() + "`").queue();
                            }else{
                                event.reply("Sadece kendi odanızda işlem yapabilirsiniz!").setEphemeral(true).queue();
                            }
                        }else{
                            event.reply("Aktif bir odanız bulunamadı!").setEphemeral(true).queue();
                        }
                    }else if (event.getSubcommandName().equals("izin-al")){
                        if (rooms.getActiveRooms().containsKey(event.getMember().getId())){
                            if (event.getMember().getVoiceState().getChannel().getId().equals(rooms.getActiveRooms().get(event.getMember().getId()))){
                                OptionMapping v = event.getOption("user");
                                event.getGuild().getVoiceChannelById(rooms.getActiveRooms().get(event.getMember().getId())).getManager().removePermissionOverride(v.getAsMember().getIdLong()).queue();
                                DatabaseConnection connection = new DatabaseConnection();
                                connection.removeAllowedMember(event.getMember().getId(),v.getAsMember().getId());
                                event.reply("<a:GCB_verify:1014897033376698490> Odanızdan bir kullanıcının iznini kaldırdınız : " + "`" + v.getAsMember().getUser().getAsTag() + "`").queue();
                            }else{
                                event.reply("Sadece kendi odanızda işlem yapabilirsiniz!").setEphemeral(true).queue();
                            }
                        }else{
                            event.reply("Aktif bir odanız bulunamadı!").setEphemeral(true).queue();
                        }
                    }
                }else{
                    event.reply("Bunu yapabilmek için kurduğunuz sesli odada aktif olmanız gerekli!").setEphemeral(true).queue();
                }
                lastGathered.remove(event.getMember());
                lastGathered.put(event.getMember(), System.currentTimeMillis());
            }else{
                int mils = (int) (time - lastGathered.get(event.getMember()));
                int seconds = (int) (mils / 1000) % 60 ;
                int rem = 7 - seconds;
                event.reply(":no_entry: Komudu tekrar kullanabilmek için `" + rem + "` saniye beklemelisin!").setEphemeral(true).queue();
            }

        }
    }
}
