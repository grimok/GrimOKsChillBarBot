package com.grim.moderation;


import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WarnCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getMember().hasPermission(Permission.KICK_MEMBERS)){
            if (event.getName().equals("uyar")){
                Role role = event.getGuild().getRoleById("982379694022336563"); //Barman
                Role w1 = event.getGuild().getRoleById("1015173980811956274");
                Role w2 = event.getGuild().getRoleById("1015173993977876520");
                Role w3 = event.getGuild().getRoleById("1015173994464423996");
                Role musteri = event.getGuild().getRoleById("975332688372301885");
                Role jail = event.getGuild().getRoleById("1015181856641134672");

                if (event.getMember().getRoles().contains(role)){
                    Member member = event.getOption("user").getAsMember();
                    String reason = event.getOption("reason").getAsString();

                    if (member.getRoles().contains(jail)){
                        event.reply("Kullanıcı zaten hapiste!").setEphemeral(true).queue();
                    }else if (!member.getRoles().contains(w1)){
                        event.getGuild().addRoleToMember(member,w1).queue();
                        event.reply("<:GCB_ping:1014442613581025310> " + member.getAsMention() + " `" + reason +"` sebebinden dolayı **ilk uyarısını** aldı.").queue();
                    }else if (member.getRoles().contains(w1) && !(member.getRoles().contains(w2))){
                        event.getGuild().addRoleToMember(member,w2).queue();
                        event.reply("<:GCB_ping:1014442613581025310> " + member.getAsMention() + " `" + reason +"` sebebinden dolayı **ikinci uyarısını** aldı.").queue();
                    }else if (member.getRoles().contains(w2)){
                        event.getGuild().addRoleToMember(member,w3).queue();
                        List<Role> roles = member.getRoles();
                        for(int i = 0; i < roles.size();i++){
                            event.getGuild().removeRoleFromMember(member,roles.get(i)).queue();
                        }
                        event.getGuild().addRoleToMember(member,jail).queue();
                        event.reply("<:GCB_ping:1014442613581025310> " + member.getAsMention() + " `" + reason +"` sebebinden dolayı **üçüncü uyarısını** aldı ve hapse gönderildi.").queue();
                    }

                }else{
                    event.reply("Bunu yapmak için gereken izne sahip değilsin.").setEphemeral(true).queue();
                }

            }
        }
    }
}
