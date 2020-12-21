-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 21 Des 2020 pada 03.41
-- Versi server: 10.4.14-MariaDB
-- Versi PHP: 7.4.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bpltb`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `barang`
--

CREATE TABLE `barang` (
  `sku` varchar(20) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `stock` int(50) NOT NULL,
  `harga_beli` int(20) NOT NULL,
  `harga_jual` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `barang`
--

INSERT INTO `barang` (`sku`, `nama`, `stock`, `harga_beli`, `harga_jual`) VALUES
('B-001', 'Biskuit', 30, 3000, 5000),
('B-002', 'BearBrand', 20, 11000, 12000),
('B-003', 'Sonice', 25, 2000, 5000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `detail_transaksi`
--

CREATE TABLE `detail_transaksi` (
  `id` int(255) NOT NULL,
  `sku` varchar(50) NOT NULL,
  `noresi` varchar(50) NOT NULL,
  `jumlah` int(10) NOT NULL,
  `harga` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `detail_transaksi`
--

INSERT INTO `detail_transaksi` (`id`, `sku`, `noresi`, `jumlah`, `harga`) VALUES
(1, 'B-001', 'ST-20201214-1', 5, 25000),
(2, 'B-002', 'ST-20201214-1', 10, 120000),
(3, 'B-003', 'ST-20201220-1', 30, 150000),
(4, 'B-002', 'ST-20201220-1', 20, 240000),
(5, 'B-001', 'ST-20201220-2', 20, 100000),
(6, 'B-003', 'ST-20201221-1', 20, 100000);

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `noresi` varchar(50) NOT NULL,
  `tanggal` date NOT NULL,
  `username` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`noresi`, `tanggal`, `username`) VALUES
('ST-20201214-1', '2020-12-14', 'rifqi'),
('ST-20201220-1', '2020-12-20', 'rifqi'),
('ST-20201220-2', '2020-12-20', 'prima'),
('ST-20201221-1', '2020-12-21', 'rifqi');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `username` varchar(20) NOT NULL,
  `login_terakhir` date NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`username`, `login_terakhir`, `email`, `password`) VALUES
('nova', '2020-12-14', 'nova@gmail.com', 'nova'),
('prima', '2020-12-14', 'prima@gmail.com', 'prima'),
('rifqi', '2020-12-08', 'rifqi@gmail.com', 'rifqi'),
('vira', '2020-12-14', 'vira@gmail.com', ' vira'),
('yolanda', '2020-12-14', 'yola@gmail.com', ' yolanda');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`sku`);

--
-- Indeks untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD PRIMARY KEY (`id`),
  ADD KEY `detail_transaksi_ibfk_2` (`sku`),
  ADD KEY `detail_transaksi_ibfk_1` (`noresi`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`noresi`),
  ADD KEY `transaksi_ibfk_1` (`username`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD CONSTRAINT `detail_transaksi_ibfk_1` FOREIGN KEY (`noresi`) REFERENCES `transaksi` (`noresi`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `detail_transaksi_ibfk_2` FOREIGN KEY (`sku`) REFERENCES `barang` (`sku`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD CONSTRAINT `transaksi_ibfk_1` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
