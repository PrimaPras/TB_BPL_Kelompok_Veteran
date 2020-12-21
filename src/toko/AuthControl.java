package toko;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class AuthControl {
    Scanner sc = new Scanner(System.in);
    public ArrayList<Auth> authTemp = new ArrayList<>();

    public ArrayList login() throws SQLException, NoSuchElementException {
        Integer check;
        Integer count = 0;
        do {
            System.out.print("Username \t :");
            String username = sc.nextLine();
            System.out.print("Password \t :");
            String password = sc.nextLine();


            check = loginAuth(username, password);

            if(check == 0){
                count = count+1;
                resetCounting(count, username);
            }else if (check == 2){
                System.out.println("Username not registered");
            }else if (check == 1){
                for (Auth auth: authTemp
                     ) {
                    System.out.println("Logged in");
                    System.out.println("Welcome"+ auth.getUsername());
                }
            }
        }while (check != 1);
        return authTemp;
    }

    public int loginAuth(String username, String password) throws SQLException {
        Connect connect = new Connect();
        Connection connection = connect.getConn();
        String usernameDB = null;
        String passwordDB = null;

        String sql = "SELECT username, password FROM user WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, username);

        ResultSet result = preparedStatement.executeQuery();
        while (result.next()){
            usernameDB = result.getString("username");
            passwordDB = result.getString("password");
        }

        int oke = 0;
        if(username.equals(usernameDB)){

            if (password.equals(passwordDB)){
                oke = 1;
                authTemp.clear();
                Auth auth = new Auth(usernameDB, passwordDB);
                authTemp.add(auth);
            }else{
                oke = 0;
            }
        }else{
            oke = 2;
        }

        preparedStatement.close();
        connection.close();

        return oke;
    }

    public void resetCounting(int count, String username){
        if (count == 1){
            System.out.println("Password Salah, 2 Kali kesempatan menuju reset password");
        }else if (count == 2){
            System.out.println("Password Salah, 1 Kali kesempatan menuju reset password");
        }else if (count == 3){
            System.out.println("Password anda telah direset!!");
            try{
                Connect connect = new Connect();
                PreparedStatement statement;
                Connection connection = connect.getConn();

                String passwordReset = randomString();

                String sql = "UPDATE user SET password = ? WHERE username = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, passwordReset);
                statement.setString(2, username);
                statement.executeUpdate();

                connection.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }


        }
    }

    public String randomString(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }



}
