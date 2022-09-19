package com.grim.level.image;

import com.grim.database.DatabaseConnection;
import com.grim.level.system.ColorPicker;
import com.grim.level.system.LevelCalculator;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Member;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class ImageProcessor {

    public BufferedImage processImage(String fileName) throws IOException {
        BufferedImage image = ImageIO.read(new File(fileName));
        BufferedImage rescaledImage = new BufferedImage(300,300,BufferedImage.TYPE_INT_ARGB);
        Graphics2D rescale = rescaledImage.createGraphics();
        rescale.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        rescale.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        rescale.drawImage(image, 10, 0, 300, 300, null);
        rescale.dispose();
        int width = rescaledImage.getWidth();
        BufferedImage circleBuffer = new BufferedImage(225,225,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circleBuffer.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        int center_X = -(rescaledImage.getWidth() - circleBuffer.getWidth()) / 2;
        int center_Y = -(rescaledImage.getHeight() - circleBuffer.getHeight()) / 2;
        g2.setClip(new Ellipse2D.Float(0,0,225, 225));
        g2.drawImage(rescaledImage,center_X,center_Y,width,width,null);
        g2.dispose();

        return circleBuffer;
    }

    public BufferedImage generateVignette(float alpha) throws IOException {
        BufferedImage rectangle = ImageIO.read(new File("vignate.png"));

        return rectangle;
    }

    public BufferedImage generateBorder(Color color, float thick){
        BufferedImage border = new BufferedImage(934,282,BufferedImage.TYPE_INT_ARGB);
        Graphics2D borderGraphics = border.createGraphics();
        borderGraphics.setColor(color);
        borderGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        float thickness = thick;
        Stroke oldStroke = borderGraphics.getStroke();
        borderGraphics.setStroke(new BasicStroke(thickness));
        borderGraphics.drawRect(0,0,934,282);
        //To return default stroke value:
        borderGraphics.setStroke(oldStroke);
        borderGraphics.dispose();

        return border;
    }

    public BufferedImage generateAvatarBorder(Color color,float oval_thickness){
        BufferedImage p1 = new BufferedImage(934,282,BufferedImage.TYPE_INT_ARGB);
        Graphics2D p1g = p1.createGraphics();
        p1g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        p1g.setStroke(new BasicStroke(oval_thickness,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
        p1g.setColor(color);
        p1g.drawOval(25,28,225,225);

        return p1;
    }

    public BufferedImage generateUserStatus(OnlineStatus status){
        BufferedImage p1;
        switch (status){
            case ONLINE:
                p1 = new BufferedImage(934,282,BufferedImage.TYPE_INT_ARGB);
                Graphics2D online = p1.createGraphics();
                online.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                online.setColor(new Color(0,0,0));
                online.fillOval(187,198,50,50);
                online.setColor(Color.green);
                online.fillOval(192,203,40,40);
                break;
            case DO_NOT_DISTURB:
                p1 = new BufferedImage(934,282,BufferedImage.TYPE_INT_ARGB);
                Graphics2D doNot = p1.createGraphics();
                doNot.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                doNot.setColor(new Color(0,0,0));
                doNot.fillOval(187,198,50,50);
                doNot.setColor(Color.red);
                doNot.fillOval(192,203,40,40);
                break;
            case IDLE:
                p1 = new BufferedImage(934,282,BufferedImage.TYPE_INT_ARGB);
                Graphics2D idle = p1.createGraphics();
                idle.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                idle.setColor(new Color(0,0,0));
                idle.fillOval(187,198,50,50);
                idle.setColor(Color.ORANGE);
                idle.fillOval(192,203,40,40);
                break;
            default:
                p1 = new BufferedImage(934,282,BufferedImage.TYPE_INT_ARGB);
                Graphics2D offline = p1.createGraphics();
                offline.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                offline.setColor(new Color(0,0,0));
                offline.fillOval(187,198,50,50);
                offline.setColor(Color.GRAY);
                offline.fillOval(192,203,40,40);
                break;
        }

        return p1;
    }

    public BufferedImage imageParser(BufferedImage vignette, BufferedImage border, BufferedImage avatar,BufferedImage avatarBorder , BufferedImage status, Member member) throws IOException {
        DatabaseConnection connection = new DatabaseConnection();
        BufferedImage p1 = new BufferedImage(934,282,BufferedImage.TYPE_INT_ARGB);
        BufferedImage overlay = ImageIO.read(new File("background.png"));
        int overlay_height = p1.getHeight() + (overlay.getHeight() * -1);
        int overlay_width =p1.getWidth() + (overlay.getWidth() * -1);
        Graphics2D p1g = p1.createGraphics();
        p1g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        p1g.drawImage(overlay,overlay_width/2,overlay_height/2,null);
        p1g.drawImage(vignette,0,0,null);
        //p1g.drawImage(border,0,0,null);
        p1g.drawImage(avatar,25,28,null);
        p1g.drawImage(avatarBorder,0,0,null);
        p1g.drawImage(status,0,0,null);
        float exp_bar = 25;
        Stroke oldStroke2 = p1g.getStroke();
        p1g.setStroke(new BasicStroke(exp_bar,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
        p1g.setColor(Color.darkGray);

        Graphics2D p2g = p1.createGraphics();
        p2g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        p2g.setStroke(new BasicStroke(28,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
        p2g.setColor(new Color(0,0,0));
        p2g.drawLine(270,210,870,210);


        p1g.drawLine(270,210,870,210);
        p1g.setColor(new ColorPicker().convertColor(member.getId()));

        //Haha Math Goes Brrr
        int exp = connection.getExp(member.getId());
        int required = new LevelCalculator().calculateRequired(member.getId());

        double pers = ((exp *100)/required);


        p1g.drawLine(270,210, 270+(int) (6*pers),210);
        p1g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,26));
        p1g.setColor(Color.white);
        p1g.drawString(member.getUser().getAsTag(), 265,190);
        p1g.drawString("Level " + connection.getLevel(member.getId()), 265,160);
        p1g.drawString(new LevelCalculator().expFormat(member.getId()) + " / " + new LevelCalculator().calculateRequiredFormated(member.getId()),780,190);
        p1g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,40));
        p1g.drawString("RANK #"+connection.getRank(member.getId()),725,75);
        p1g.setStroke(oldStroke2);

        p1g.dispose();

        return p1;
    }

}
