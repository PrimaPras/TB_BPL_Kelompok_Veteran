package toko;

import java.sql.*;
import java.util.LinkedList;
import java.util.Scanner;

public class TransaksiControl extends Control{
    Connect connect = new Connect();
    Connection connection;
    Scanner scanner;

    @Override
    public void menu(String menu) {
        int choosen;
        do {
            System.out.println("------Menu "+menu);
            System.out.println("------ 1. Lihat Semua Penjualan");
            System.out.println("------ 2. Lihat detail transaksi");
            System.out.println("------ 0. Kembali");
            System.out.print("Pilihan : ");
            switch (choosen = sc.nextInt()) {
                case 1:
                    showMenu();
                    break;

                case 2:
                    System.out.println("Masukkan Nomor Resi yang akan dilihat : ");
                    showDetail(sc.nextLine());
                    break;

                case 3:
                    deleteMenu();
                    break;

                case 4:
                    editMenu();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Pilihan tidak ada");
                    break;

            }
            System.out.println("------------------------------- \n");
            tunggu();
            System.out.println("------------------------------- \n");
        } while (choosen != 0);
    }

    @Override
    public void addMenu() {

    }

    @Override
    public void editMenu() {

    }

    @Override
    public void deleteMenu() {

    }

    @Override
    public void showMenu() {
        System.out.println("List Semua Transaksi");
        showAll();
    }

    @Override
    public void searchMenu() {

    }

    @Override
    public Object search(String key) throws SQLException {
        return null;
    }

    @Override
    public Object showOne(String id) {
        return null;
    }

    public void showAll() {
        try{
            connection = connect.getConn();
            Statement statement = connection.createStatement();
            String sql = "SELECT transaksi.noresi as noresi, transaksi.tanggal as tanggal, SUM(detail_transaksi.harga) as harga FROM transaksi JOIN detail_transaksi ON transaksi.noresi = detail_transaksi.noresi";

            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Nomor Resi || Tanggal Transaksi || Total Transaksi");
            while (resultSet.next()){
                System.out.print(resultSet.getString("noresi"));
                System.out.print("||");
                System.out.print(resultSet.getString("tanggal"));
                System.out.print("||");
                System.out.println(resultSet.getString("harga"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void showDetail(String noresi){
        try{
            connection = connect.getConn();
            PreparedStatement statement;
            ResultSet resultSet;
            String sql;
            int total = 0;

            sql = "SELECT barang.nama, detail_transaksi.jumlah, detail_transaksi.harga, transaksi.username, tansaksi.tanggal FROM detail_transaksi JOIN barang ON barang.sku = detail_transaksi.sku JOIN transaksi ON transaksi.noresi = detail_transaksi.noresi WHERE transaksi.noresi = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, noresi);

            resultSet = statement.executeQuery();
            if (resultSet.next()){
                System.out.println("No resi "+noresi);
                System.out.println("Username pembeli "+resultSet.getString("username"));
                System.out.println("Tanggal beli "+resultSet.getString("tanggal"));
                System.out.println("--------------------------------------------");
                System.out.println("------------Daftar Barang dibeli------------");
                System.out.println("Nama || Jumlah || Total");
                while (resultSet.next()){
                    System.out.print(resultSet.getString("nama"+"||"));
                    System.out.print(resultSet.getInt("jumlah"+"||"));
                    System.out.print(resultSet.getInt("harga"+"||"));
                    total = total + resultSet.getInt("harga");
                }
                System.out.println("--------------------------------");
                System.out.println("Total Belanja "+total);
            }
        }catch (SQLException e){
            System.out.println("Kesalahan pada database saat pencarian");
        }
    }

}