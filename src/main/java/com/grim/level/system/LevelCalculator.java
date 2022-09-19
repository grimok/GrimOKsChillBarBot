package com.grim.level.system;

import com.grim.database.DatabaseConnection;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;

import java.text.DecimalFormat;

public class LevelCalculator {

    private DatabaseConnection connection = null;

    private String[] suffix = new String[]{"","k", "m", "b", "t"};
    private int MAX_LENGTH = 4;

    private String format(double number) {
        String r = new DecimalFormat("##0E0").format(number);
        r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
        while(r.length() > MAX_LENGTH || r.matches("[0-9]+\\.[a-z]")){
            r = r.substring(0, r.length()-2) + r.substring(r.length() - 1);
        }
        return r;
    }

    public LevelCalculator(){
        connection = new DatabaseConnection();
    }

    public String calculateRequiredFormated(String id){
        int level = connection.getLevel(id);

        // (level * 250) + (100 * (level + 1))

        return format((level * 250) + (100*(level+1)));
    }

    public String expFormat(String id){
        return format((connection.getExp(id)));
    }

    public int calculateRequired(String id){
        int level = connection.getLevel(id);

        // (level * 250) + (100 * (level + 1))

        return (level * 250) + (100*(level+1));
    }

    public String updateLevel(Member member){
        int exp = connection.getExp(member.getId());
        int req = calculateRequired(member.getId());

        if (exp >= req){
            connection.updateLevel(member.getId());
            return "<a:GCB_chickiesclap:1014442651027767357> " + member.getAsMention() + " kullanıcısı **seviye atladı.** Artık kendisi " + connection.getLevel(member.getId()) + " seviye!";
        }

        return "nan";
    }



}
