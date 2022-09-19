package com.grim.level.image;

import com.grim.bot.Bot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageDownloader {

    public synchronized void downloadImage(String url, String file, SlashCommandInteractionEvent event) throws IOException {

        InputStream inputStream = null;


        OutputStream outputStream = null;

        try {
            URL image = new URL(url);

            String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36";

            URLConnection con = image.openConnection();

            con.setRequestProperty("User-Agent", USER_AGENT);

            int contentLength = con.getContentLength();
            System.out.println("File contentLength = " + contentLength + " bytes");

            inputStream = con.getInputStream();

            outputStream = new FileOutputStream(file);

            byte[] buffer = new byte[2048];

            int length;
            int downloaded = 0;

            while ((length = inputStream.read(buffer)) != -1)
            {

                outputStream.write(buffer, 0, length);
                downloaded+=length;
                //System.out.println("Downlad Status: " + (downloaded * 100) / (contentLength * 1.0) + "%");


            }
        } catch (Exception ex) {
            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
            event.getHook().sendMessage("Girilen URL Bozuk!").queue();
        }


        outputStream.close();
        inputStream.close();


    }

}
