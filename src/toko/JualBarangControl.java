package toko;

	import java.sql.*;
	import java.text.SimpleDateFormat;
	import java.util.*;
	import java.util.Date;

	public class JualBarangControl {
	    Connection connection;
	    Scanner scanner;
	    Connect connect = new Connect();
	    BarangControl barangControl;

	    ArrayList<Auth> authArrayList;
	    LinkedList<DetailTransaksi> listTransaksi = new LinkedList<DetailTransaksi>();

	    Auth auth;

	    public JualBarangControl(ArrayList<Auth> a){
	        authArrayList = a;
	    }

	    public void menuUtamaJual(){
	        try{
	            Integer pilihan;
	            scanner = new Scanner(System.in);
	            do{
	                System.out.println("------Menu Beli barang");
	                System.out.println("--- 1 Beli barang");
	                System.out.println("--- 2 List barang");
	                System.out.println("--- 3 Selesaikan Pembelian");
	                System.out.println("--- 4 Batalkan Pembelian");
	                System.out.print(">>>>>> Pilihan 1/2/3/4 : ");
	                pilihan = scanner.nextInt();

	                switch (pilihan){
	                    case 1:
	                        menuJual();
	                        break;

	                    case 2:
	                        cart(listTransaksi);
	                        break;

	                    case 3:
	                        System.out.println("List Belanja");
	                        if (listTransaksi == null){
	                            pilihan = 0;
	                            break;
	                        }else{
	                            System.out.println("Total Belanja Kamu : "+concTrasaction(listTransaksi, authArrayList));
	                            pilihan = 0;
	                        }
	                        break;

	                    case 4:
	                        batalTransaksiStock(listTransaksi);
	                        listTransaksi.clear();
	                        pilihan = 0;
	                        break;
	                }
	                scanner = new Scanner(System.in);
	                System.out.println("Tekan Enter untuk melanjutkan \n --------------------");
	                scanner.nextLine();
	            }while (pilihan != 0);

	        }catch (NoSuchElementException e){
	            System.out.println("Input Pilihan Salah");
	        }catch (SQLException e){
	            System.out.println("Kesalahan pada database");
	        }

	    }

	    private void menuJual(){

	        try {
	            scanner = new Scanner(System.in);
	            barangControl = new BarangControl();

	            System.out.println("----Silahakan pilih Barang yang ingin dibeli");

	            barangControl.showMenu();
	            System.out.print("Nomor sku barang yang ingin dibeli : ");
	            String pilihBarang = scanner.nextLine();

	            Barang barang = barangControl.showOne(pilihBarang);

	            System.out.println("Nama Barang : "+barang.getNama());
	            int stok = barang.getStok();
	            int jumlah;
	            //tukang cek berhasil belanja
	            boolean confirmedStock = false;
	            do {
	                System.out.print("Jumlah Beli : ");
	                jumlah = scanner.nextInt();

	                if (jumlah < 0){
	                    System.out.println("Jumlah harus besar dari 0");
	                }else if(jumlah > stok){
	                    System.out.println("Stok Tidak mencukupi");
	                }else if (jumlah <= stok){
	                    confirmedStock = true;
	                }

	            }while (!confirmedStock);

	            scanner = new Scanner(System.in);
	            int harga = barang.getHarga_jual();

	            System.out.println("Total Pembelian : "+harga*jumlah);
	            System.out.print("Simpan pembelian [y/n] : ");
	            String conf = scanner.nextLine();

	            if(conf.equals("Y") || conf.equals("y")){
	                //membuat objek menyimpan barang pihan, jumlah barang, dan harga total barang dibeli
	                DetailTransaksi detailTransaksi = new DetailTransaksi(pilihBarang, jumlah, harga*jumlah);
	                //menambhakan ke linked list
	                listTransaksi.add(detailTransaksi);
	                //query/control mengurangi stock barang di database
	                reduceStock(jumlah, pilihBarang);
	            }else{
	                System.out.println("Pembelian barang dibatalkan");
	            }

	        }catch (SQLException e){
	            System.out.println("Kesalahan saat mengambil data barang");
	        }catch (NoSuchElementException ex){
	            System.out.println("Kesalahan input data");
	        }catch (NullPointerException ex){
	            System.out.println("Tidak ada barang yang dipilih");
	        }
	    }

	    public double concTrasaction(LinkedList<DetailTransaksi> detail, ArrayList<Auth> auths){
//	        {"ST-20201212-1"}
	        double total = 0;
	        try{
	            PreparedStatement statement;
	            String noresi = generateNoResi();
	            connection = connect.getConn();

	            String insertTransaksiSQL = "INSERT INTO transaksi(noresi, tanggal, username) VALUES (?, current_date(), ?)";
	            String insertDetailTransaksiSQL = "INSERT INTO detail_transaksi(sku, noresi, jumlah, harga) VALUES (?, ?, ?, ?)";
	            String updateBarang = "UPDATE barang SET stock = stock - ? WHERE sku = ?";
	            for (Auth auth: auths
	            ) {
	                String username = auth.getUsername();

	                statement = connection.prepareStatement(insertTransaksiSQL);
	                statement.setString(1, noresi);
	                statement.setString(2, username);
	                statement.executeUpdate();
	            }

	            for (DetailTransaksi detailTransaksi :
	                    detail) {
	                String sku = detailTransaksi.getSku();
	                int jumlah = detailTransaksi.getJumlah();
	                int harga = detailTransaksi.getHarga();

	                statement = connection.prepareStatement(insertDetailTransaksiSQL);
	                statement.setString(1, sku);
	                statement.setString(2, noresi);
	                statement.setInt(3, jumlah);
	                statement.setInt(4, harga);
	                statement.executeUpdate();


	               /* statement = connection.prepareStatement(updateBarang);
	                statement.setInt(1, jumlah);
	                statement.setString(2, sku);
	                statement.executeUpdate();
	*/
	                total = total+harga;
	            }

	            System.out.println("Transaksi Berhasil");

	            connection.close();
	            listTransaksi.clear();
	            return total;
	        }catch (SQLException e){
	            e.printStackTrace();
	        }catch (Exception ex){
	            System.out.println(ex.getMessage());

	        }
	        return 0;
	    }

	    public void cart(LinkedList<DetailTransaksi> listTransaksi){

	        try {
	            barangControl = new BarangControl();
	            System.out.println("Daftar Beli");
	            System.out.println("SKU||Nama||Jumlah||Total Harga");
	            for (DetailTransaksi details :
	                    listTransaksi) {

	                String sku = details.getSku();
	                int jumlah = details.getJumlah();

	                Barang barang = barangControl.showOne(sku);
	                String nama = barang.getNama();
	                int harga = barang.getHarga_jual();

	                System.out.print(sku);
	                System.out.print("||");
	                System.out.print(nama);
	                System.out.print("||");
	                System.out.print(jumlah);
	                System.out.print("||");
	                System.out.println(harga*jumlah);
	            }

	        }catch (SQLException e){
	            System.out.println("Kesalahan saat pengambilan data barang");
	        }
	    }

	    public String generateNoResi() throws SQLException {
	        String noResi;
	        String tanggal = null;
	        Integer count = 0;
	        Statement statement;
	        ResultSet resultSet;
	        Date date = new Date();
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

	        tanggal = formatter.format(date);
	        connection = connect.getConn();
	        String countId = "SELECT COUNT(noresi) as count from transaksi where tanggal = current_date()";
	        statement = connection.createStatement();
	        resultSet = statement.executeQuery(countId);

	        if(resultSet.next()){
	            count = resultSet.getInt("count");
	            if(count == null){
	                count = 0;
	            }
	        }
	        connection.close();
	        count = count+1;
	        noResi = "ST-"+tanggal+"-"+count;
	        System.out.println(noResi);

	        return noResi;
	    }

	    public void reduceStock(int jumlah, String sku) throws SQLException{
	        connection = connect.getConn();
	        PreparedStatement statement;

	        String sql = "UPDATE barang SET stock = stock - ? WHERE sku = ?";
	        statement = connection.prepareStatement(sql);
	        statement.setInt(1, jumlah);
	        statement.setString(2, sku);
	        statement.executeUpdate();

	        connection.close();
	    }

	    public void batalTransaksiStock(LinkedList<DetailTransaksi> listTransaksi) throws SQLException{
	        connection = connect.getConn();
	        PreparedStatement statement;
	        String sql;


	        for (DetailTransaksi details :
	                listTransaksi) {
	            String sku = details.getSku();
	            int jumlah = details.getJumlah();

	            sql = "UPDATE barang SET stock = stock + ? WHERE sku = ?";
	            statement = connection.prepareStatement(sql);
	            statement.setInt(1, jumlah);
	            statement.setString(2, sku);
	            statement.executeUpdate();

	        }
	        connection.close();
	    }

	}


