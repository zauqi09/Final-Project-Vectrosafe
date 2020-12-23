-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 23, 2020 at 09:39 AM
-- Server version: 10.4.14-MariaDB
-- PHP Version: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vectrosafe-operatorpulsa`
--

-- --------------------------------------------------------

--
-- Table structure for table `nomor_telkomsel`
--

CREATE TABLE `nomor_telkomsel` (
  `no_hp` varchar(25) NOT NULL,
  `pulsa` bigint(25) NOT NULL,
  `id` bigint(20) NOT NULL,
  `masa_aktif` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `nomor_telkomsel`
--

INSERT INTO `nomor_telkomsel` (`no_hp`, `pulsa`, `id`, `masa_aktif`) VALUES
('085324556808', 23900, 20401, '2021-01-15'),
('082114931227', 404600, 20402, '2021-09-04');

-- --------------------------------------------------------

--
-- Table structure for table `nomor_xl`
--

CREATE TABLE `nomor_xl` (
  `id` bigint(20) NOT NULL,
  `no_hp` varchar(25) NOT NULL,
  `pulsa` bigint(25) NOT NULL,
  `masa_aktif` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `nomor_xl`
--

INSERT INTO `nomor_xl` (`id`, `no_hp`, `pulsa`, `masa_aktif`) VALUES
(3, '081712341234', 45400, '2021-02-10'),
(4, '081708170817', 20400, '2021-01-28');

-- --------------------------------------------------------

--
-- Table structure for table `telkomsel`
--

CREATE TABLE `telkomsel` (
  `id_produk` bigint(20) NOT NULL,
  `nama_produk` varchar(25) DEFAULT NULL,
  `harga_produk` bigint(20) DEFAULT NULL,
  `operator` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `telkomsel`
--

INSERT INTO `telkomsel` (`id_produk`, `nama_produk`, `harga_produk`, `operator`) VALUES
(1, 'V-10', 11000, 'Telkomsel'),
(2, 'V-20', 21000, 'Telkomsel'),
(3, 'V-30', 31000, 'Telkomsel'),
(4, 'V-50', 51000, 'Telkomsel'),
(5, 'V-100', 101000, 'Telkomsel'),
(6, 'V-200', 201000, 'Telkomsel');

-- --------------------------------------------------------

--
-- Table structure for table `xl`
--

CREATE TABLE `xl` (
  `id_produk` bigint(20) NOT NULL,
  `nama_produk` varchar(20) NOT NULL,
  `harga_produk` bigint(20) NOT NULL,
  `operator` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `xl`
--

INSERT INTO `xl` (`id_produk`, `nama_produk`, `harga_produk`, `operator`) VALUES
(1, 'V-10', 11000, 'XL'),
(2, 'V-15', 16000, 'XL'),
(3, 'V-25', 26000, 'XL'),
(4, 'V-50', 51000, 'XL'),
(5, 'V-100', 101000, 'XL'),
(6, 'V-200', 201000, 'XL');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `nomor_telkomsel`
--
ALTER TABLE `nomor_telkomsel`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `nomor_xl`
--
ALTER TABLE `nomor_xl`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `telkomsel`
--
ALTER TABLE `telkomsel`
  ADD PRIMARY KEY (`id_produk`);

--
-- Indexes for table `xl`
--
ALTER TABLE `xl`
  ADD PRIMARY KEY (`id_produk`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `nomor_telkomsel`
--
ALTER TABLE `nomor_telkomsel`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20403;

--
-- AUTO_INCREMENT for table `nomor_xl`
--
ALTER TABLE `nomor_xl`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `telkomsel`
--
ALTER TABLE `telkomsel`
  MODIFY `id_produk` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `xl`
--
ALTER TABLE `xl`
  MODIFY `id_produk` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
