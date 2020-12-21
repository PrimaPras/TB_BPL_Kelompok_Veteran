package toko;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);
    Integer choosen;
    ArrayList<Auth> authArrayList = new ArrayList<Auth>();

    public Menu(ArrayList auth) throws NoSuchElementException, SQLException {
        authArrayList = auth;

        do {
            System.out.println("----Menu-------------------");
            System.out.println("--- 1. Kelola User");
            System.out.println("--- 2. Kelola Barang");
            System.out.println("--- 3. Kelola Transaksi");
            System.out.println("--- 4. User beli barang");
            System.out.println("--- 5. Restok barang");
            System.out.println("--- 6. Laporan");
            System.out.println("--- 7. logout");
            System.out.println("--- 0. Terminate");
            System.out.print("Pilihan(1/2/3/4/5/6/0): ");
            //varibel menu pilihan
            choosen = sc.nextInt();
            System.out.println("--------------------------");
            directMenu();
        } while (choosen != 0);
    }

    public void directMenu() throws SQLException {
        switch (choosen){
//            case 1:
//                UserControl userControl = new UserControl();
//                userControl.menu("User");
//                break;
//
//            case 2:
//                BarangControl barangControl = new BarangControl();
//                barangControl.menu("Barang");
//                break;
//
//            case 3:
//                TransaksiControl transaksiControl = new TransaksiControl();
//                transaksiControl.showAll();
//                break;
//
//            case 4:
//                JualBarangControl jualBarangControl = new JualBarangControl(authArrayList);
//                jualBarangControl.menuUtamaJual();
//                break;
//
//            case 5:
//                barangControl = new BarangControl();
//                barangControl.reStockMenu();
//                break;
//
//            case 6:
//                LaporanControl laporan = new LaporanControl();
//                laporan.menu();
//                break;

        }

    }
}