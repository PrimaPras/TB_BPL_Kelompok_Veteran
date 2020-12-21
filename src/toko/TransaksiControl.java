package toko;

import java.sql.*;
import java.util.Iterator;
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
            System.out.println("------ 3. Delete Transaksi");
            System.out.println("------ 4. Cari Transaksi");
            System.out.println("------ 0. Kembali");
            System.out.print("Pilihan : ");
            switch (choosen = sc.nextInt()) {
                case 1:
                    showAll();
                    break;

                case 2:
                    sc = new Scanner(System.in);
                    System.out.print("Masukkan Nomor Resi yang akan dilihat : ");
                    showDetail(sc.nextLine());
                    break;

                case 3:
                    deleteMenu();
                    break;

                case 4:
                    searchMenu();
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
        try{
            sc = new Scanner(System.in);
            System.out.print("No Resi yang akan dihapus : ");
            String noresi = sc.nextLine();
            delete(noresi);
            System.out.println("Berhasil Menghapus data");
        }catch (SQLException e){
            System.out.println("Gagal menghapus data");
        }
    }

    @Override
    public void showMenu() {
        System.out.println("List Semua Transaksi");
        showAll();
    }

    @Override
    public void searchMenu() {
        sc = new Scanner(System.in);
        try{
            System.out.println("----Pencarian Data-------");
            System.out.print("Masukkan No resi yang dicari: ");
            LinkedList list = search(sc.nextLine());

            Iterator iterator = list.iterator();
            System.out.println("No Resi || Tanggal || Username");
            while (iterator.hasNext()){
                System.out.print(iterator.next()+"||");
            }
            System.out.println();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public LinkedList search(String key) throws SQLException {
        Connection connection = connect.getConn();
        PreparedStatement preparedStatement;
        LinkedList list = new LinkedList();
        ResultSet resultSet;

        String sql = "SELECT * FROM transaksi WHERE noresi = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, key);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            list.add(resultSet.getString("noresi"));
            list.add(resultSet.getString("tanggal"));
            list.add(resultSet.getString("username"));
        }

        connection.close();
        return list;
    }

    @Override
    public Object showOne(String id) {
        return null;
    }

    public void showAll() {
        try{
            connection = connect.getConn();
            Statement statement = connection.createStatement();
            String sql = "SELECT transaksi.noresi as noresi, transaksi.tanggal as tanggal, SUM(detail_transaksi.harga) as harga FROM transaksi JOIN detail_transaksi ON transaksi.noresi = detail_transaksi.noresi GROUP BY transaksi.noresi";

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

            String sqlTransaksi = "SELECT noresi, username, tanggal FROM transaksi WHERE noresi = ?";
            sql = "SELECT barang.nama as nama, detail_transaksi.jumlah as jumlah, detail_transaksi.harga, transaksi.username, transaksi.tanggal FROM detail_transaksi JOIN barang ON barang.sku = detail_transaksi.sku JOIN transaksi ON transaksi.noresi = detail_transaksi.noresi WHERE transaksi.noresi = ?";

            statement = connection.prepareStatement(sqlTransaksi);
            statement.setString(1, noresi);

            resultSet = statement.executeQuery();
            if (resultSet.next()){
                System.out.println("No resi "+noresi);
                System.out.println("Username pembeli "+resultSet.getString("username"));
                System.out.println("Tanggal beli "+resultSet.getString("tanggal"));
                System.out.println("--------------------------------------------");
                System.out.println("------------Daftar Barang dibeli------------");
                System.out.println("Nama || Jumlah || Total");
            }

            statement = connection.prepareStatement(sql);
            statement.setString(1, noresi);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                System.out.print(resultSet.getString("nama"));
                System.out.print(" || ");
                System.out.print(resultSet.getInt("jumlah"));
                System.out.print(" || ");
                System.out.println(resultSet.getInt("harga"));
                total = total + resultSet.getInt("harga");
            }
            System.out.println("--------------------------------");
            System.out.println("Total Belanja "+total);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public int delete(String noresi) throws SQLException{
        Connection connection = connect.getConn();
        PreparedStatement preparedStatement;

        String sql = "DELETE FROM detail_transaksi WHERE noresi = ?";
        String sqlTransaksi = "DELETE FROM transaksi WHERE noresi = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, noresi);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement(sqlTransaksi);
        preparedStatement.setString(1, noresi);
        int result = preparedStatement.executeUpdate();

        connection.close();
        return result;
    }
}

