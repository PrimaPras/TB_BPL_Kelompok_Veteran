package toko;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Main {
    public static ArrayList auth = null;
    public static void main(String[] args) {
	// write your code here
        try {
            AuthControl authControl = new AuthControl();
            do {
                Menu menu = new Menu(authControl.login());
            }while (true);
        }catch (NoSuchElementException e){
            System.out.println("Something Wrong with Database");
        }catch (Exception ex){
            System.out.println("Exception "+ex.getMessage());
        }
    }
}

