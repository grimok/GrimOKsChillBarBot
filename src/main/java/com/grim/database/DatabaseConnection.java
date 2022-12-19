package com.grim.database;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Member;
import org.json.JSONArray;

import java.sql.*;

public class DatabaseConnection {

    private String user_name = "a";
    private String password = "b";
    private String db_name = "c";
    private String host = "d";
    private int port = 3306;

    private Connection connection;

    private Statement statement = null;

    public DatabaseConnection(){
        String url = "jdbc:mysql://"+host+":"+port+"/"+db_name+"?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver bulunmadı");
        }

        try {
            connection = DriverManager.getConnection(url,user_name,password);
        } catch (SQLException e) {
            System.out.println("Bağlantı Başarısız!");
            e.printStackTrace();
        }
    }

    private void openConnection(){
        try {
            if (!connection.isClosed()) return;
            String url = "jdbc:mysql://"+host+":"+port+"/"+db_name+"?useUnicode=true&characterEncoding=utf8&autoReconnect=true";
            connection = DriverManager.getConnection(url,user_name,password);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void messageEntryToDB(String id,String author,String message,String isEdited){

        openConnection();

        try {
            statement = connection.createStatement();

            String query = "Insert Into messages (id,author,content,isEdited,newContent) VALUES(" +"'"+id+"',"+"'"+author+"',"+"'"+message+"',"+"'"+isEdited+"',"+"'https://img.freepik.com/free-vector/night-ocean-landscape-full-moon-stars-shine_107791-7397.jpg?w=1380&t=st=1671037748~exp=1671038348~hmac=2548d637f671b6332c93c4222565b0b85c3aa0641a1a3a32c1365ea5c15031db')";

            statement.executeUpdate(query);

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public synchronized boolean isDBContainsMessage(String messageID){
        openConnection();
        String query = "Select * From messages";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                if (rs.getString("id").equals(messageID)){
                    return true;
                }
            }

            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public synchronized String getMessage(String messageID){
        openConnection();
        String query = "Select * From messages";
        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                if (rs.getString("id").equals(messageID)){
                    return rs.getString("content");
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public synchronized String getAuthorID(String messageID){
        openConnection();
        String query = "Select * From messages";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                if (rs.getString("id").equals(messageID)){
                    return rs.getString("author");
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void dropMessageData(String messageID){
        openConnection();
        String query = "DELETE FROM messages WHERE id="+"'"+messageID+"'";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    public boolean isUserRegisteredToLevel(String id) {
        openConnection();
        try {
            statement = connection.createStatement();

            String query = "SELECT 1\n" +
                    "FROM level\n" +
                    "WHERE userID = " + "'" + id + "'";

            ResultSet resultSet = statement.executeQuery(query);

            return resultSet.isBeforeFirst();

        }catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public void insertUserToDatabase(String id){
        openConnection();
        try {
            statement = connection.createStatement();

            String query = "INSERT INTO `level` (`userID`, `exp`, `level`, `background`, `color`) VALUES ("+ "'"+id+"',"+"'"+"0"+"',"+"'"+"0"+"',"+"'"+"NaN"+"',"+"'"+"WHITE"+"')";

            statement.executeUpdate(query);
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getExp(String id){
        openConnection();
        String query = "Select * From level";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                if (rs.getString("userID").equals(id)){
                    return rs.getInt("exp");
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public int getLevel(String id){
        openConnection();
        String query = "Select * From level";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                if (rs.getString("userID").equals(id)){
                    return rs.getInt("level");
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public String getBackground(String id){
        openConnection();
        String query = "Select * From level";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                if (rs.getString("userID").equals(id)){
                    String answer = rs.getString("background");
                    if (answer.equals("NaN")){
                        return "https://wallpapercave.com/wp/wp2641159.png";
                    }else{
                        return answer;
                    }
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "NaN";
    }

    public String getColor(String id){
        openConnection();
        String query = "Select * From level";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                if (rs.getString("userID").equals(id)){
                    return rs.getString("color");
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "WHITE";
    }

    public void updateExp(String id,int exp){
        openConnection();
        try {
            statement = connection.createStatement();

            int cexp = getExp(id);
            int nexp = cexp + exp;

            String query = "Update level Set exp = " + "'"+nexp+"' where userID = " + "'"+id+"'";

            statement.executeUpdate(query);

            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateLevel(String id){
        openConnection();
        try {
            statement = connection.createStatement();

            int neww = getLevel(id)+1;

            String query = "Update level Set level = " + "'"+neww+"' where userID = " + "'"+id+"'";

            statement.executeUpdate(query);
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateBackground(String id,String url){
        openConnection();
        try {
            statement = connection.createStatement();

            String query = "Update level Set background = " + "'"+url+"' where userID = " + "'"+id+"'";

            statement.executeUpdate(query);
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateColor(String id,String color){
        openConnection();
        try {
            statement = connection.createStatement();

            String query = "Update level Set color = " + "'"+color+"' where userID = " + "'"+id+"'";

            statement.executeUpdate(query);
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getRank(String ID){
        openConnection();
        try {
            statement = connection.createStatement();
            String query = "select * from level order by exp DESC";
            ResultSet set = statement.executeQuery(query);

            while (set.next()){
                if (set.getString("userID").equals(ID)){
                    return set.getRow();
                }
            }
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void updateMessage(String messageID,String newMessage){
        openConnection();
        try {
            statement = connection.createStatement();

            String query = "Update messages Set content = " + "'"+newMessage+"' where id = " + "'"+messageID+"'";

            statement.executeUpdate(query);
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void insertRoomToDatabase(Member member){
        openConnection();
        try {
            statement = connection.createStatement();

            String query = "INSERT INTO `pvc` (`roomName`, `roomLimit`, `bannedUsers`, `isLocked`, `ownerID`) VALUES ("+ "'"+member.getEffectiveName()+"ˈnın Odası"+"',"+"'"+"5"+"',"+"'"+"[]"+"',"+"'"+0+"',"+"'"+member.getId()+"')";

            statement.executeUpdate(query);

            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean isUserRegisteredToPVC(String id){
        openConnection();
        try {
            statement = connection.createStatement();

            String query = "SELECT 1\n" +
                    "FROM pvc\n" +
                    "WHERE ownerID = " + "'" + id + "'";

            ResultSet resultSet = statement.executeQuery(query);



            return resultSet.isBeforeFirst();

        }catch (SQLException e){
            e.printStackTrace();
        }

        try {
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    public synchronized String getRoomName(String id){
        openConnection();

        String query = "Select * From pvc";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                if (rs.getString("ownerID").equals(id)){
                    return rs.getString("roomName");
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public synchronized int getRoomLimit(String id){
        openConnection();
        String query = "Select * From pvc";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                if (rs.getString("ownerID").equals(id)){
                    return rs.getInt("roomLimit");
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }
    public synchronized String getRoomAllowed(String id){
        openConnection();
        String query = "Select * From pvc";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                if (rs.getString("ownerID").equals(id)){
                    return rs.getString("bannedUsers");
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "[]";
    }
    public synchronized boolean isRoomLocked(String id){
        openConnection();
        String query = "Select * From pvc";

        try {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                if (rs.getString("ownerID").equals(id)){
                    return rs.getBoolean("isLocked");
                }
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void updateRoomName(String id,String roomName){
        openConnection();
        try {
            statement = connection.createStatement();

            String query = "Update pvc Set roomName = " + "'"+roomName.replace("'","ˈ")+"' where ownerID = " + "'"+id+"'";

            statement.executeUpdate(query);
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateRoomLimit(String id,int roomLimit){
        openConnection();
        try {
            statement = connection.createStatement();

            String query = "Update pvc Set roomLimit = " + "'"+roomLimit+"' where ownerID = " + "'"+id+"'";

            statement.executeUpdate(query);
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void updateRoomLock(String id,int booleanEntry){
        openConnection();
        try {
            statement = connection.createStatement();

            String query = "Update pvc Set isLocked = " + "'"+booleanEntry+"' where ownerID = " + "'"+id+"'";

            statement.executeUpdate(query);
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void addAllowedMember(String owner,String addID){
        openConnection();
        try {

            JSONArray array = new JSONArray(getRoomAllowed(owner));

            for(int i = 0; i < array.length(); i++){
                if (array.getString(i).equals(addID)){
                    System.out.println("Kullanıcı Bulundu!");
                    return;
                }
            }

            array.put(addID);
            System.out.println("Kullanıcı Eklendi : " + array.toString());

            statement = connection.createStatement();

            String query = "Update pvc Set bannedUsers = " + "'"+array+"' where ownerID = " + "'"+owner+"'";

            statement.executeUpdate(query);
            connection.close();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void removeAllowedMember(String owner,String removeID){
        openConnection();
        try {

            JSONArray array = new JSONArray(getRoomAllowed(owner));

            for(int i = 0; i < array.length(); i++){
                if (array.getString(i).equals(removeID)){
                    System.out.println(array.getString(i));
                    array.remove(i);
                }
            }

            statement = connection.createStatement();

            String query = "Update pvc Set bannedUsers = " + "'"+array+"' where ownerID = " + "'"+owner+"'";

            statement.executeUpdate(query);
            connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
