package com.grim.moderation;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.jetbrains.annotations.NotNull;

public class KickCommand extends ListenerAdapter {

    private static Member lastUser;

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getMember().hasPermission(Permission.KICK_MEMBERS)){
            if (event.getName().equals("at")){
                Role role = event.getGuild().getRoleById("982379694022336563");
                if (event.getMember().getRoles().contains(role)){

                    lastUser = event.getOption("user").getAsMember();

                    TextInput reason = TextInput.create("kick-reason","Atma Sebebi:", TextInputStyle.SHORT).setPlaceholder("(Troll, Spam, Kural İhlali vb.)").setRequired(true).build();

                    Modal modal = Modal.create("modal-kick","Lütfen Bir Sebep Belirtin").addActionRows(ActionRow.of(reason)).build();

                    event.replyModal(modal).queue();

                }else{
                    event.reply("Bunu yapmak için gereken izne sahip değilsin.").setEphemeral(true).queue();
                }

            }
        }
    }

    public Member getLastMember() {
        return lastUser;
    }

}
