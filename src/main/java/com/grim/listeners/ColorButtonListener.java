package com.grim.listeners;

import com.grim.database.DatabaseConnection;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ColorButtonListener extends ListenerAdapter {

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        DatabaseConnection connection = new DatabaseConnection();
        if (event.getButton().getId().equals("color-white")){
            connection.updateColor(event.getUser().getId(),"WHITE");
            event.reply("Renginiz başarılı bir şekilde değiştirilmiştir!").setEphemeral(true).queue();
        } else if (event.getButton().getId().equals("color-blue")) {
            connection.updateColor(event.getUser().getId(),"BLUE");
            event.reply("Renginiz başarılı bir şekilde değiştirilmiştir!").setEphemeral(true).queue();
        }else if (event.getButton().getId().equals("color-green")){
            connection.updateColor(event.getUser().getId(),"GREEN");
            event.reply("Renginiz başarılı bir şekilde değiştirilmiştir!").setEphemeral(true).queue();
        }else if (event.getButton().getId().equals("color-red")){
            connection.updateColor(event.getUser().getId(),"RED");
            event.reply("Renginiz başarılı bir şekilde değiştirilmiştir!").setEphemeral(true).queue();
        }else if (event.getButton().getId().equals("color-yellow")){
            connection.updateColor(event.getUser().getId(),"YELLOW");
            event.reply("Renginiz başarılı bir şekilde değiştirilmiştir!").setEphemeral(true).queue();
        }else if (event.getButton().getId().equals("color-magenta")){
            connection.updateColor(event.getUser().getId(),"MAGENTA");
            event.reply("Renginiz başarılı bir şekilde değiştirilmiştir!").setEphemeral(true).queue();
        }else if (event.getButton().getId().equals("color-pink")){
            connection.updateColor(event.getUser().getId(),"PINK");
            event.reply("Renginiz başarılı bir şekilde değiştirilmiştir!").setEphemeral(true).queue();
        }else if (event.getButton().getId().equals("color-gray")){
            connection.updateColor(event.getUser().getId(),"GRAY");
            event.reply("Renginiz başarılı bir şekilde değiştirilmiştir!").setEphemeral(true).queue();
        }else if (event.getButton().getId().equals("color-darkgray")){
            connection.updateColor(event.getUser().getId(),"DARK_GRAY");
            event.reply("Renginiz başarılı bir şekilde değiştirilmiştir!").setEphemeral(true).queue();
        }else if (event.getButton().getId().equals("color-cyan")){
            connection.updateColor(event.getUser().getId(),"CYAN");
            event.reply("Renginiz başarılı bir şekilde değiştirilmiştir!").setEphemeral(true).queue();
        }
    }
}
