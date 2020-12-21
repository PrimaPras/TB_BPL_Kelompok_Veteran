package toko;

import java.sql.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BarangControl extends Control{
    Connection connection;
    Connect connect = new Connect();

    public BarangControl() throws SQLException{
        connection = connect.getConn();
        connection.close();
    }

    @Override
    public void addMenu() {
        System.out.println("Tambah Barang");
        try {
            sc = new Scanner(System.in);
            System.out.print("sku \t :");
            String sku = sc.nextLine();

            System.out.print("Nama \t :");
            String nama = sc.nextLine();

            System.out.print("Stok \t :");
            int stok = sc.nextInt();

            System.out.print("Harga Beli \t :");
            int harga_beli = sc.nextInt();

            System.out.print("Harga Jual \t :");
            int harga_jual = sc.nextInt();

            Barang barang = new Barang(sku, nama, stok, harga_beli, harga_jual);

            add(barang);

        } catch (NoSuchElementException e) {
            System.out.println("Kesalahan Dalam Input");
        }
    }

    @Override
    public void editMenu() {
        try {
            sc = new Scanner(System.in);
            showMenu();

            System.out.println("-----Edit Barang");
            System.out.print("SKU barang yang akan diubah : ");
            String skuLama;
            showOne(skuLama = sc.nextLine());

            System.out.print("SKU : ");
            String sku = sc.nextLine();

            System.out.print("Nama : ");
            String nama = sc.nextLine();

            System.out.print("Stok : ");
            int stok = sc.nextInt();

            System.out.print("Harga Beli : ");
            int harga_beli = sc.nextInt();

            System.out.print("Harga Jual : ");
            int harga_jual = sc.nextInt();

            Barang barang = new Barang(sku, nama, stok, harga_beli, harga_jual);

            edit(skuLama, barang);

        }catch (NoSuchElementException e){
            System.out.print(e.getMessage());
        }
    }

    @Override
    public void deleteMenu() {
        sc = new Scanner(System.in);
        showMenu();

        System.out.println("----Delete Menu");
        System.out.print("SKU barang yang akan di hapus : ");
        delete(sc.nextLine());
    }

    @Override
    public void showMenu() {
        sc = new Scanner(System.in);
        LinkedList<Barang> listBarang = show();

        System.out.println("sku || nama || stok || Harga beli || Harga Jual");

        for (Barang barang:
             listBarang) {
            System.out.print(barang.getSku());
            System.out.print("||");

            System.out.print(barang.getNama());
            System.out.print("||");

            System.out.print(barang.getStok());
            System.out.print("||");

            System.out.print(barang.getHarga_beli());
            System.out.print("||");

            System.out.print(barang.getHarga_jual());
            System.out.print("\n");

        }
    }
    
    public LinkedList<Barang> search(String key) throws SQLException {
        connection = connect.getConn();

        String sql = "SELECT * FROM barang WHERE nama LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%"+key+"%");

        ResultSet resultSet = statement.executeQuery();
        LinkedList<Barang> listBarang = new LinkedList<Barang>();
        
        while (resultSet.next()){
            Barang barang = new Barang(
                    resultSet.getString("sku"),
                    resultSet.getString("nama"),
                    resultSet.getInt("stock"),
                    resultSet.getInt("harga_beli"),
                    resultSet.getInt("harga_jual")
            );

            listBarang.add(barang);
        }
        connection.close();
        return listBarang;
    }
    @Override
    public void searchMenu() {
    	try {	
    		sc = new Scanner(System.in);
    		System.out.print("Masukkan Nama Barang yang akan dicari : ");
    		LinkedList<Barang> listBarang = search(sc.nextLine());
    		
    		for(Barang barang : listBarang) {
    			System.out.println("SKU : "+barang.getSku());
    			System.out.println("NAMA : "+barang.getNama());
    			System.out.println("Stock : "+barang.getStok());
    			System.out.println("Harga Beli : "+barang.getHarga_beli());
    			System.out.println("Harga Jual : "+barang.getHarga_jual());
    		}
    		
    	}catch (SQLException | NoSuchElementException e) {
    		System.out.println(e.getMessage());
    	}
 
    }

    @Override
    public LinkedList show() {
          LinkedList<Barang> listBarang = new LinkedList<Barang>();

          try{
              connection = connect.getConn();
              Statement statement = connection.createStatement();
              String sql = "SELECT * FROM barang";

              ResultSet resultSet = statement.executeQuery(sql);

              while (resultSet.next()){
                  Barang barang = new Barang(
                          resultSet.getString("sku"),
                          resultSet.getString("nama"),
                          resultSet.getInt("stock"),
                          resultSet.getInt("harga_beli"),
                          resultSet.getInt("harga_jual")
                  );

                  listBarang.add(barang);
              }
          }catch (SQLException e){
              System.out.println(e.getMessage());
          }
        return listBarang;
    }

    public int add(Barang barang) {
        int result = 0;

        try {
            connection = connect.getConn();
            String sql = "INSERT INTO barang (sku, nama, stock, harga_beli, harga_jual) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, barang.getSku());
            statement.setString(2, barang.getNama());
            statement.setInt(3, barang.getStok());
            statement.setInt(4, barang.getHarga_beli());
            statement.setInt(5, barang.getHarga_jual());

            result = statement.executeUpdate();
            System.out.println("Input Data Berhasil");
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int edit(String id, Barang barang) {
        int result = 0;

        try {
            connection = connect.getConn();
            PreparedStatement statement;

            String sql = "UPDATE barang SET sku = ?, nama = ?, stock = ?, harga_jual = ?, harga_beli = ? WHERE sku = ?";
            statement = connection.prepareStatement(sql);

            statement.setString(1 ,barang.getSku());
            statement.setString(2, barang.getNama());
            statement.setInt(3, barang.getStok());
            statement.setInt(4, barang.getHarga_jual());
            statement.setInt(5, barang.getHarga_beli());
            statement.setString(6 , id);

            result = statement.executeUpdate();
            System.out.println("Barang telah berhasil di update");
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return result;
    }

    public int delete(String id) {
        int result = 0;

        try {
            connection = connect.getConn();
            String sql = "DELETE FROM barang WHERE sku = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            result = statement.executeUpdate();
            connection.close();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return result;
    }


    public Barang showOne(String id) {

        try{
            connection = connect.getConn();

            String sql = "SELECT * FROM barang WHERE sku = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();
            Barang barang = null;
            if (resultSet.next()){
                barang = new Barang(
                        resultSet.getString("sku"),
                        resultSet.getString("nama"),
                        resultSet.getInt("stock"),
                        resultSet.getInt("harga_beli"),
                        resultSet.getInt("harga_jual")
                );
            }
            return barang;
        }catch (SQLException e){
            System.out.println(e.getMessage());
            return null;
        }

    }

    public void reStockMenu(){
        Scanner scanner = new Scanner(System.in);
        try {
            showMenu();
            System.out.print("SKU barang yang akan dilakukan restok : ");
            Barang barang = showOne(scanner.nextLine());

            System.out.println("Nama Barang   : "+barang.getNama());
            System.out.println("Stok saat ini : "+barang.getStok());

            System.out.print("Jumlah Restok : ");
            reStock(scanner.nextInt()+barang.getStok(), barang.getSku());

            System.out.println(barang.getNama()+" telah dilakukan restock");
        }catch (NullPointerException | NoSuchElementException e){
            System.out.println("Kesalahan Input");
        }catch (SQLException e){
            System.out.println("Kesalahan pada input data ke database barang");
        }
    }

    public int reStock(int sum, String sku)throws SQLException{
        int result = 0;
        Connection connection = connect.getConn();
        String sql = "UPDATE barang SET stock = ? WHERE sku = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, sum);
        statement.setString(2, sku);
        result = statement.executeUpdate();
        return result;
    }
}
