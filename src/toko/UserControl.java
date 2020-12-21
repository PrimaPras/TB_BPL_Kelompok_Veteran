package toko;

import java.sql.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserControl extends Control{
    Connect connect = new Connect();
    static Connection connection;
    static Scanner sc = new Scanner(System.in);

    public UserControl() throws SQLException {
        connection = connect.getConn();
        connection.close();
        menu("User");
    }

    public int add(User user){

        int result = 0;

        try {
            connection = connect.getConn();
            String sql = "INSERT INTO user (username, password, email, login_terakhir) VALUES (?, ?, ?, now())";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());

            result = statement.executeUpdate();
            System.out.println("Input Data Berhasil");
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    public LinkedList<User> show() {
        Statement statement;
        LinkedList<User> listUser = new LinkedList<User>();

        try{
            connection = connect.getConn();
            statement = connection.createStatement();
            String sql = "SELECT * FROM user";

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                User user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("login_terakhir"),
                        resultSet.getString("email")
                );
                listUser.add(user);
            }
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return listUser;
    }


    public User search(String key) throws SQLException {
        connection = connect.getConn();

        String sql = "SELECT * FROM user WHERE username LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%"+key+"%");

        ResultSet resultSet = statement.executeQuery();

        User user = new User(
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("login_terakhir"),
                resultSet.getString("email")
        );

        connection.close();
        return user;
    }

    public int edit(String id,User user) throws SQLException {
        int result = 0;
        PreparedStatement statement;
        connection = connect.getConn();
        User userEdited = user;

        String sql = "UPDATE user SET username = ? , password = ? , email = ? WHERE username = ?";
        statement = connection.prepareStatement(sql);
        statement.setString(1, userEdited.getUsername());
        statement.setString(2, userEdited.getPassword());
        statement.setString(3, userEdited.getEmail());
        statement.setString(4, id);

        result = statement.executeUpdate();

        connection.close();

        return result;
    }

    public int delete(String id) throws SQLException {
        int result = 0;
        Connection connection = connect.getConn();

        String sql = "DELETE FROM user WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, id);
        result = statement.executeUpdate();

        return result;
    }

    public User showOne(String id){


        try{
            PreparedStatement statement;
            User user = null;

            connection = connect.getConn();

            String sql = "SELECT * FROM user WHERE username = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, id);


            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("login_terakhir"),
                        resultSet.getString("email")
                );

            }
            connection.close();

            return user;
        }catch (SQLException e){
            System.out.println("Kesalahan mengambil data");
            return null;
        }



    }

    @Override
    public void addMenu() {
        System.out.println("Tambah User");
        try {
            System.out.print("Username \t :");
            String username = sc.nextLine();

            System.out.print("Password \t :");
            String password = sc.nextLine();

            System.out.print("Email \t :");
            String email = sc.nextLine();

            User user = new User(username, password,null,email);
            add(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editMenu() {
        try {
            showMenu();

            System.out.println("-----Edit User");
            System.out.print(" Masukkan Username yang akan diubah : ");
            String username = sc.nextLine();

            User user = showOne(username);

            System.out.print("Username ["+user.getUsername()+"] : ");
            String usernameEdited = sc.nextLine();

            System.out.print("Password ["+user.getPassword()+"] :");
            String password = sc.nextLine();

            System.out.print("Email : ["+user.getEmail()+"] :");
            String email = sc.nextLine();

            User user1 = new User(usernameEdited, password, null, email);

            if (edit(username,user1) == 1){
                System.out.println("Berhasil update user "+username);
            }else{
                System.out.println("Gagal Update");
            }
        }catch (NoSuchElementException e){
            System.out.println("Kesalahan Input perubahan");
        }catch (SQLException ex){
            System.out.println("Kesalahan Query");
            ex.printStackTrace();
        }

    }

    @Override
    public void deleteMenu() {
        try{
            showMenu();
            System.out.println("-------Delete ");
            System.out.println("Masukkan Username yang akan dihapus : ");
            delete(sc.nextLine());
        }catch (SQLException e){
            System.out.println("Kesalahan Input Data");
        }
    }

    @Override
    public void showMenu() {
        System.out.println("Tampil Seluruh User");
        try {
            LinkedList<User> listUser = show();

            System.out.print("Username||"+"Password||"+"Email||"+"Last Login \n");
            for (User user: listUser
            ) {
                System.out.print(user.getUsername());
                System.out.print("||");

                System.out.print(user.getPassword());
                System.out.print("||");

                System.out.print(user.getEmail());
                System.out.print("||");

                System.out.print(user.getLast_login());
                System.out.print("\n");
            }
        }catch (NoSuchElementException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void searchMenu() {
        try {
        	sc = new Scanner(System.in);
            System.out.println("Masukkan username yang akan dicari : ");
            User user = showOne(sc.nextLine());

            System.out.println("Username : "+user.getUsername());
            System.out.println("Password : "+user.getPassword());
            System.out.println("Email \t : "+user.getEmail());
            System.out.println("Last Login : "+user.getLast_login());
            
        }catch (NoSuchElementException e){
            System.out.println("Kesalahan dalam mencari data");
        }

    }

}