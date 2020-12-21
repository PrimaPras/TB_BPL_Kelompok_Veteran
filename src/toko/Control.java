package toko;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class Control implements IControl{
    Scanner sc = new Scanner(System.in);

    public void menu(String menu) {
        int choosen;
        do {
            System.out.println("------Menu "+menu);
            System.out.println("------ 1. Lihat Semua");
            System.out.println("------ 2. Tambah");
            System.out.println("------ 3. Hapus");
            System.out.println("------ 4. Edit");
            System.out.println("------ 5. Cari");
            System.out.println("------ 0. Kembali");
            System.out.print("Pilihan : ");
            switch (choosen = sc.nextInt()) {
                case 1:
                    showMenu();
                    break;

                case 2:
                    addMenu();
                    break;

                case 3:
                    deleteMenu();
                    break;

                case 4:
                    editMenu();
                    break;

                case 5:
                    searchMenu();
                    break;

                default:
                    System.out.println("Pilihan tidak ada");
                    break;

            }
            System.out.println("------------------------------- \n");
            tunggu();
        } while (choosen != 0);

    }

    public Object showOne(String id){
        return null;
    }

    public LinkedList show() {
        return null;
    }



    public Object search(String key) throws SQLException {
        return null;
    }

    public void tunggu() throws NoSuchElementException {
        Scanner sc = new Scanner(System.in);
        System.out.print("enter untuk melanjutkan");
        sc.nextLine();
    }
}