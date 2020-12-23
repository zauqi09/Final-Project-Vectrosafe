-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 23, 2020 at 09:40 AM
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
-- Database: `vectrosafe`
--

-- --------------------------------------------------------

--
-- Table structure for table `auth`
--

CREATE TABLE `auth` (
  `id_auth` bigint(20) NOT NULL,
  `username` varchar(40) NOT NULL,
  `password` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `auth`
--

INSERT INTO `auth` (`id_auth`, `username`, `password`) VALUES
(68, 'zauqi09', '1tQQXzVZkHWJJSpQl9fUJg=='),
(85, 'z18234', '1tQQXzVZkHWJJSpQl9fUJg=='),
(86, 'fuadzqnr', '1tQQXzVZkHWJJSpQl9fUJg=='),
(87, 'fuadzauqinur', '1tQQXzVZkHWJJSpQl9fUJg=='),
(88, 'razisf', 'b4WE4bXVBj0='),
(89, 'asdasda', 'Xuq6t7Fr/Dg='),
(90, 'fwef2332e', 'b4WE4bXVBj0='),
(91, 'z182341231', '1tQQXzVZkHWJJSpQl9fUJg=='),
(92, 'z2182341231', '1tQQXzVZkHWJJSpQl9fUJg=='),
(93, 'z2182321311231', '1tQQXzVZkHWJJSpQl9fUJg=='),
(94, 'zauqi09q', '1tQQXzVZkHWJJSpQl9fUJg==');

-- --------------------------------------------------------

--
-- Table structure for table `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(68);

-- --------------------------------------------------------

--
-- Table structure for table `nasabah`
--

CREATE TABLE `nasabah` (
  `id_nasabah` bigint(20) NOT NULL,
  `nama_lengkap` varchar(60) DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  `saldo` bigint(20) DEFAULT NULL,
  `alamat` varchar(70) DEFAULT NULL,
  `no_rekening` varchar(25) NOT NULL,
  `no_hp` varchar(20) NOT NULL,
  `id_auth` bigint(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `nasabah`
--

INSERT INTO `nasabah` (`id_nasabah`, `nama_lengkap`, `tgl_lahir`, `saldo`, `alamat`, `no_rekening`, `no_hp`, `id_auth`) VALUES
(41, 'Fuad Zauqi Nur', '1997-08-05', 14871000, 'Cirebon', '1608339509273', '088214931227', 68),
(71, 'Fuad Zauqi Nur', '1997-08-05', 0, 'Cirebon', '1608626797792', '088214931227', 85),
(74, 'Fuad Zauqi Nur', '2020-12-02', 0, 'Cirebon', '1608633535112', '08172382193201', 87),
(75, 'Reza Azis Fauzan', '2020-12-01', 0, 'Sedong Cirebon', '1608634348856', '088231283212983', 88),
(76, 'asdasdasda', '2020-12-07', 0, '123123', '1608634537783', '1231231', 89),
(77, '232d23d79', '2020-12-01', 0, '1bidubkq,a', '1608634977751', '1283y128', 90),
(78, 'Fuad Zauqi Nur', '1997-08-05', 0, 'Cirebon', '1608643626475', '088214931227', 91),
(79, 'Fuad Zauqi Nur', '1997-08-05', 0, 'Cirebon', '1608643647846', '088214931227', 92),
(80, 'Fuad Zauqi Nur', '1997-08-05', 0, 'Cirebon', '1608643900071', '088214931227', 93),
(81, 'asdas', '2020-12-09', 0, '12312312', '1608644749543', '123123', 94);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `id_transaksi` bigint(20) NOT NULL,
  `no_transaksi` bigint(20) NOT NULL,
  `id_nasabah` bigint(20) NOT NULL,
  `tipe` varchar(15) NOT NULL,
  `mutasi` bigint(20) NOT NULL,
  `saldo` bigint(20) NOT NULL,
  `waktu` timestamp(6) NOT NULL DEFAULT current_timestamp(6) ON UPDATE current_timestamp(6),
  `keterangan` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`id_transaksi`, `no_transaksi`, `id_nasabah`, `tipe`, `mutasi`, `saldo`, `waktu`, `keterangan`) VALUES
(65, 1608339939190, 41, 'Debit', 500000, 500000, '2020-12-19 01:05:39.000000', 'setoran tunai'),
(66, 1608340034140, 41, 'Debit', 500000, 1000000, '2020-12-19 01:07:14.000000', 'setoran tunai'),
(67, 1608340034487, 41, 'Debit', 500000, 1500000, '2020-12-19 01:07:14.000000', 'setoran tunai'),
(68, 1608340049767, 41, 'Debit', 500000, 2000000, '2020-12-19 01:07:29.000000', 'setoran tunai'),
(69, 1608340684146, 41, 'Kredit', 101000, 1899000, '2020-12-19 01:18:04.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(70, 1608340684751, 41, 'Kredit', 101000, 1798000, '2020-12-19 01:18:04.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(71, 1608340685381, 41, 'Kredit', 101000, 1697000, '2020-12-19 01:18:05.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(72, 1608340685881, 41, 'Kredit', 101000, 1596000, '2020-12-19 01:18:05.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(73, 1608340686402, 41, 'Kredit', 101000, 1495000, '2020-12-19 01:18:06.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(74, 1608340686989, 41, 'Kredit', 101000, 1394000, '2020-12-19 01:18:06.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(75, 1608340687438, 41, 'Debit', 500000, 1894000, '2020-12-19 01:18:07.000000', 'setoran tunai'),
(76, 1608340688395, 41, 'Debit', 500000, 2394000, '2020-12-19 01:18:08.000000', 'setoran tunai'),
(77, 1608340689281, 41, 'Debit', 500000, 2894000, '2020-12-19 01:18:09.000000', 'setoran tunai'),
(78, 1608340689827, 41, 'Debit', 500000, 3394000, '2020-12-19 01:18:09.000000', 'setoran tunai'),
(79, 1608340690838, 41, 'Debit', 500000, 3894000, '2020-12-19 01:18:10.000000', 'setoran tunai'),
(80, 1608340691694, 41, 'Kredit', 101000, 3793000, '2020-12-19 01:18:11.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(81, 1608341658982, 41, 'Kredit', 101000, 3692000, '2020-12-19 01:34:18.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(82, 1608341659343, 41, 'Kredit', 101000, 3591000, '2020-12-19 01:34:19.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(83, 1608341659606, 41, 'Kredit', 101000, 3490000, '2020-12-19 01:34:19.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(84, 1608341659811, 41, 'Debit', 500000, 3990000, '2020-12-19 01:34:19.000000', 'setoran tunai'),
(85, 1608341660000, 41, 'Debit', 500000, 4490000, '2020-12-19 01:34:20.000000', 'setoran tunai'),
(86, 1608341660202, 41, 'Debit', 500000, 4990000, '2020-12-19 01:34:20.000000', 'setoran tunai'),
(87, 1608341660407, 41, 'Debit', 500000, 5490000, '2020-12-19 01:34:20.000000', 'setoran tunai'),
(88, 1608341660639, 41, 'Debit', 500000, 5990000, '2020-12-19 01:34:20.000000', 'setoran tunai'),
(89, 1608341660836, 41, 'Kredit', 101000, 5889000, '2020-12-19 01:34:20.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(90, 1608341660993, 41, 'Kredit', 101000, 5788000, '2020-12-19 01:34:20.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(91, 1608341661159, 41, 'Debit', 500000, 6288000, '2020-12-19 01:34:21.000000', 'setoran tunai'),
(92, 1608341661333, 41, 'Debit', 500000, 6788000, '2020-12-19 01:34:21.000000', 'setoran tunai'),
(93, 1608341661514, 41, 'Debit', 500000, 7288000, '2020-12-19 01:34:21.000000', 'setoran tunai'),
(94, 1608341661707, 41, 'Debit', 500000, 7788000, '2020-12-19 01:34:21.000000', 'setoran tunai'),
(95, 1608341661883, 41, 'Debit', 500000, 8288000, '2020-12-19 01:34:21.000000', 'setoran tunai'),
(96, 1608341662050, 41, 'Debit', 200000, 8488000, '2020-12-19 01:34:22.000000', 'setoran tunai'),
(97, 1608341662237, 41, 'Debit', 200000, 8688000, '2020-12-19 01:34:22.000000', 'setoran tunai'),
(98, 1608341662522, 41, 'Debit', 250000, 8938000, '2020-12-19 01:34:22.000000', 'setoran tunai'),
(99, 1608341673362, 41, 'Debit', 250000, 9188000, '2020-12-19 01:34:33.000000', 'setoran tunai'),
(100, 1608341694204, 41, 'Debit', 250000, 9438000, '2020-12-19 01:34:54.000000', 'setoran tunai'),
(101, 1608341696303, 41, 'Debit', 250000, 9688000, '2020-12-19 01:34:56.000000', 'setoran tunai'),
(102, 1608341698212, 41, 'Debit', 250000, 9938000, '2020-12-19 01:34:58.000000', 'setoran tunai'),
(103, 1608341750270, 41, 'Debit', 250000, 10188000, '2020-12-19 01:35:50.000000', 'setoran tunai'),
(104, 1608341793609, 41, 'Kredit', 900000, 9288000, '2020-12-19 01:36:33.000000', 'Tagihan'),
(105, 1608341915882, 41, 'Kredit', 900000, 8388000, '2020-12-19 01:38:35.000000', 'Tagihan'),
(106, 1608341923760, 41, 'Kredit', 101000, 8287000, '2020-12-19 01:38:43.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(107, 1608350248079, 41, 'Kredit', 900000, 7387000, '2020-12-19 03:57:28.000000', 'Tagihan'),
(108, 1608350269131, 41, 'Kredit', 900000, 6487000, '2020-12-19 03:57:49.000000', 'Tagihan'),
(109, 1608350392210, 41, 'Kredit', 900000, 5587000, '2020-12-19 03:59:52.000000', 'Tagihan'),
(110, 1608350445990, 41, 'Kredit', 900000, 4687000, '2020-12-19 04:00:45.000000', 'Tagihan'),
(111, 1608350648819, 41, 'Kredit', 101000, 4586000, '2020-12-19 04:04:08.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(112, 1608350728867, 41, 'Kredit', 101000, 4485000, '2020-12-19 04:05:28.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(113, 1608350744426, 41, 'Kredit', 101000, 4384000, '2020-12-19 04:05:44.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(114, 1608350781896, 41, 'Kredit', 101000, 4283000, '2020-12-19 04:06:21.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(115, 1608350922754, 41, 'Kredit', 101000, 4182000, '2020-12-19 04:08:42.000000', 'pembelian voucher telkomsel dengan id_produk 5'),
(116, 1608350931682, 41, 'Kredit', 900000, 3282000, '2020-12-19 04:08:51.000000', 'Tagihan'),
(117, 1608350951351, 41, 'Debit', 900000, 4182000, '2020-12-19 04:09:11.000000', 'Tabungan'),
(118, 1608350990263, 41, 'Debit', 900000, 5082000, '2020-12-19 04:09:50.000000', 'Tabungan'),
(119, 1608351024237, 41, 'Debit', 900000, 5982000, '2020-12-19 04:10:24.000000', 'Tabungan'),
(120, 1608351036440, 41, 'Kredit', 51000, 5931000, '2020-12-19 04:10:36.000000', 'pembelian voucher telkomsel dengan id_produk 4'),
(121, 1608351070436, 41, 'Kredit', 51000, 5880000, '2020-12-19 04:11:10.000000', 'pembelian voucher telkomsel dengan id_produk 4'),
(122, 1608351453876, 41, 'Kredit', 51000, 5829000, '2020-12-19 04:17:33.000000', 'pembelian voucher telkomsel dengan id_produk 4'),
(123, 1608351458592, 41, 'Kredit', 51000, 5778000, '2020-12-19 04:17:38.000000', 'pembelian voucher telkomsel dengan id_produk 4'),
(124, 1608351508124, 41, 'Debit', 900000, 6678000, '2020-12-19 04:18:28.000000', 'Tabungan'),
(125, 1608351518066, 41, 'Debit', 900000, 7578000, '2020-12-19 04:18:38.000000', 'Tabungan'),
(126, 1608351519575, 41, 'Debit', 900000, 8478000, '2020-12-19 04:18:39.000000', 'Tabungan'),
(127, 1608351520925, 41, 'Debit', 900000, 9378000, '2020-12-19 04:18:40.000000', 'Tabungan'),
(128, 1608351603187, 41, 'Debit', 900000, 10278000, '2020-12-19 04:20:03.000000', 'Tabungan'),
(129, 1608351730547, 41, 'Debit', 900000, 11178000, '2020-12-19 04:22:10.000000', 'Tabungan'),
(130, 1608351839661, 41, 'Debit', 900000, 12078000, '2020-12-19 04:23:59.000000', 'Tabungan'),
(131, 1608351961565, 41, 'Debit', 900000, 12978000, '2020-12-19 04:26:01.000000', 'Tabungan'),
(132, 1608352092047, 41, 'Debit', 900000, 13878000, '2020-12-19 04:28:12.000000', 'Tabungan'),
(133, 1608352181301, 41, 'Debit', 900000, 14778000, '2020-12-19 04:29:41.000000', 'Tabungan'),
(134, 1608352459023, 41, 'Debit', 900000, 15678000, '2020-12-19 04:34:19.000000', 'Tabungan'),
(135, 1608352515352, 41, 'Debit', 900000, 16578000, '2020-12-19 04:35:15.000000', 'Tabungan'),
(136, 1608352546091, 41, 'Debit', 900000, 17478000, '2020-12-19 04:35:46.000000', 'Tabungan'),
(137, 1608352691497, 41, 'Debit', 900000, 18378000, '2020-12-19 04:38:11.000000', 'Tabungan'),
(138, 1608352726931, 41, 'Kredit', 31000, 18347000, '2020-12-19 04:38:46.000000', 'pembelian voucher telkomsel dengan id_produk 3'),
(139, 1608352801361, 41, 'Kredit', 31000, 18316000, '2020-12-19 04:40:01.000000', 'pembelian voucher telkomsel dengan id_produk 3'),
(140, 1608352892690, 41, 'Kredit', 31000, 18285000, '2020-12-19 04:41:32.000000', 'pembelian voucher telkomsel dengan id_produk 3'),
(141, 1608545071799, 41, 'Kredit', 201000, 18084000, '2020-12-21 10:04:31.000000', 'pembelian voucher XL dengan id_produk 6'),
(142, 1608545075550, 41, 'Kredit', 201000, 17883000, '2020-12-21 10:04:35.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(143, 1608545077293, 41, 'Kredit', 31000, 17852000, '2020-12-21 10:04:37.000000', 'pembelian voucher Telkomsel dengan id_produk 3'),
(144, 1608545078546, 41, 'Kredit', 31000, 17821000, '2020-12-21 10:04:38.000000', 'pembelian voucher Telkomsel dengan id_produk 3'),
(145, 1608545079702, 41, 'Kredit', 31000, 17790000, '2020-12-21 10:04:39.000000', 'pembelian voucher Telkomsel dengan id_produk 3'),
(146, 1608545080905, 41, 'Kredit', 31000, 17759000, '2020-12-21 10:04:40.000000', 'pembelian voucher Telkomsel dengan id_produk 3'),
(147, 1608545308715, 41, 'Kredit', 31000, 17728000, '2020-12-21 10:08:28.000000', 'pembelian voucher Telkomsel dengan id_produk 3'),
(148, 1608545338636, 41, 'Kredit', 31000, 17697000, '2020-12-21 10:08:58.000000', 'pembelian voucher Telkomsel dengan id_produk 3'),
(149, 1608545907398, 41, 'Kredit', 51000, 17646000, '2020-12-21 10:18:27.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(150, 1608545936729, 41, 'Kredit', 51000, 17595000, '2020-12-21 10:18:56.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(151, 1608545939321, 41, 'Kredit', 51000, 17544000, '2020-12-21 10:18:59.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(152, 1608546538498, 41, 'Kredit', 51000, 17493000, '2020-12-21 10:28:58.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(153, 1608551731433, 41, 'Kredit', 51000, 17442000, '2020-12-21 11:55:31.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(154, 1608551905321, 41, 'Kredit', 51000, 17391000, '2020-12-21 11:58:25.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(155, 1608551908200, 41, 'Kredit', 51000, 17340000, '2020-12-21 11:58:28.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(156, 1608551908783, 41, 'Kredit', 51000, 17289000, '2020-12-21 11:58:28.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(157, 1608551909540, 41, 'Kredit', 51000, 17238000, '2020-12-21 11:58:29.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(158, 1608551914050, 41, 'Kredit', 51000, 17187000, '2020-12-21 11:58:34.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(159, 1608551921599, 41, 'Kredit', 51000, 17136000, '2020-12-21 11:58:41.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(160, 1608551947804, 41, 'Kredit', 51000, 17085000, '2020-12-21 11:59:07.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(161, 1608551949454, 41, 'Kredit', 51000, 17034000, '2020-12-21 11:59:09.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(162, 1608551951879, 41, 'Kredit', 51000, 16983000, '2020-12-21 11:59:11.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(163, 1608551954053, 41, 'Kredit', 51000, 16932000, '2020-12-21 11:59:14.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(164, 1608551955920, 41, 'Kredit', 51000, 16881000, '2020-12-21 11:59:15.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(165, 1608551969926, 41, 'Kredit', 51000, 16830000, '2020-12-21 11:59:29.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(166, 1608552083727, 41, 'Kredit', 51000, 16779000, '2020-12-21 12:01:23.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(167, 1608552146156, 41, 'Kredit', 51000, 16728000, '2020-12-21 12:02:26.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(168, 1608552146981, 41, 'Kredit', 51000, 16677000, '2020-12-21 12:02:26.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(169, 1608552148129, 41, 'Kredit', 51000, 16626000, '2020-12-21 12:02:28.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(170, 1608552174195, 41, 'Kredit', 51000, 16575000, '2020-12-21 12:02:54.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(171, 1608552257642, 41, 'Kredit', 51000, 16524000, '2020-12-21 12:04:17.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(172, 1608553029611, 41, 'Kredit', 101000, 16423000, '2020-12-21 12:17:09.000000', 'pembelian voucher Telkomsel dengan id_produk 5'),
(173, 1608554219840, 41, 'Kredit', 201000, 16222000, '2020-12-21 12:36:59.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(174, 1608554401235, 41, 'Kredit', 201000, 16021000, '2020-12-21 12:40:01.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(175, 1608554483554, 41, 'Kredit', 201000, 15820000, '2020-12-21 12:41:23.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(176, 1608555349496, 41, 'Kredit', 201000, 15619000, '2020-12-21 12:55:49.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(177, 1608556443665, 41, 'Kredit', 201000, 15418000, '2020-12-21 13:14:03.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(178, 1608585019055, 41, 'Debit', 900000, 16318000, '2020-12-21 21:10:19.000000', 'Tabungan'),
(179, 1608585049166, 41, 'Debit', 900000, 17218000, '2020-12-21 21:10:49.000000', 'Tabungan'),
(180, 1608585163748, 41, 'Debit', 900000, 18118000, '2020-12-21 21:12:43.000000', 'Tabungan'),
(181, 1608585180889, 41, 'Debit', 900000, 19018000, '2020-12-21 21:13:00.000000', 'Tabungan'),
(182, 1608594635746, 41, 'Kredit', 201000, 18817000, '2020-12-21 23:50:35.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(183, 1608594907256, 41, 'Kredit', 201000, 18616000, '2020-12-21 23:55:07.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(184, 1608595749439, 41, 'Kredit', 201000, 18415000, '2020-12-22 00:09:09.000000', 'pembelian voucher XL dengan id_produk 6'),
(185, 1608595755519, 41, 'Kredit', 201000, 18214000, '2020-12-22 00:09:15.000000', 'pembelian voucher XL dengan id_produk 6'),
(186, 1608597050798, 41, 'Kredit', 201000, 18013000, '2020-12-22 00:30:50.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(187, 1608602791388, 41, 'Kredit', 201000, 17812000, '2020-12-22 02:06:31.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(188, 1608603937339, 41, 'Kredit', 31000, 17781000, '2020-12-22 02:25:37.000000', 'pembelian voucher Telkomsel dengan id_produk 3'),
(189, 1608622850354, 41, 'Debit', 900000, 18681000, '2020-12-22 07:40:50.000000', 'Tabungan'),
(190, 1608623003663, 41, 'Kredit', 51000, 18630000, '2020-12-22 07:43:23.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(191, 1608627914068, 41, 'Kredit', 201000, 18429000, '2020-12-22 09:05:14.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(192, 1608628091884, 41, 'Kredit', 201000, 18228000, '2020-12-22 09:08:11.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(193, 1608628670799, 41, 'Kredit', 51000, 18177000, '2020-12-22 09:17:50.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(194, 1608629244016, 41, 'Kredit', 51000, 18126000, '2020-12-22 09:27:24.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(195, 1608629340214, 41, 'Kredit', 101000, 18025000, '2020-12-22 09:29:00.000000', 'pembelian voucher Telkomsel dengan id_produk 5'),
(196, 1608629968164, 41, 'Kredit', 31000, 17994000, '2020-12-22 09:39:28.000000', 'pembelian voucher Telkomsel dengan id_produk 3'),
(197, 1608629994624, 41, 'Kredit', 101000, 17893000, '2020-12-22 09:39:54.000000', 'pembelian voucher Telkomsel dengan id_produk 5'),
(198, 1608630286114, 41, 'Kredit', 51000, 17842000, '2020-12-22 09:44:46.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(199, 1608630631837, 41, 'Kredit', 51000, 17791000, '2020-12-22 09:50:31.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(200, 1608630808949, 41, 'Kredit', 31000, 17760000, '2020-12-22 09:53:28.000000', 'pembelian voucher Telkomsel dengan id_produk 3'),
(201, 1608632616033, 41, 'Debit', 900000, 18660000, '2020-12-22 10:23:36.000000', 'Tabungan'),
(202, 1608632761984, 41, 'Kredit', 51000, 18609000, '2020-12-22 10:26:01.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(203, 1608632834006, 41, 'Kredit', 31000, 18578000, '2020-12-22 10:27:14.000000', 'pembelian voucher Telkomsel dengan id_produk 3'),
(204, 1608632857717, 41, 'Kredit', 101000, 18477000, '2020-12-22 10:27:37.000000', 'pembelian voucher Telkomsel dengan id_produk 5'),
(205, 1608635072531, 41, 'Kredit', 201000, 18276000, '2020-12-22 11:04:32.000000', 'pembelian voucher XL dengan id_produk 6'),
(207, 1608639223116, 41, 'Kredit', 31000, 18245000, '2020-12-22 12:13:43.000000', 'pembelian voucher Telkomsel dengan id_produk 3'),
(208, 1608639223532, 41, 'Kredit', 201000, 18044000, '2020-12-22 12:13:43.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(209, 1608639223778, 41, 'Kredit', 51000, 17993000, '2020-12-22 12:13:43.000000', 'pembelian voucher Telkomsel dengan id_produk 4'),
(210, 1608639295663, 41, 'Kredit', 201000, 17792000, '2020-12-22 12:14:55.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(211, 1608639419090, 41, 'Kredit', 201000, 17591000, '2020-12-22 12:16:59.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(212, 1608643537194, 41, 'Kredit', 101000, 17490000, '2020-12-22 13:25:37.000000', 'pembelian voucher Telkomsel dengan id_produk 5'),
(213, 1608643546322, 41, 'Kredit', 101000, 17389000, '2020-12-22 13:25:46.000000', 'pembelian voucher Telkomsel dengan id_produk 5'),
(214, 1608645602461, 41, 'Kredit', 201000, 17188000, '2020-12-22 14:00:02.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(215, 1608647340840, 41, 'Kredit', 201000, 16987000, '2020-12-22 14:29:00.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(216, 1608659354727, 41, 'Kredit', 201000, 16786000, '2020-12-22 17:49:14.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(217, 1608660194738, 41, 'Kredit', 201000, 16585000, '2020-12-22 18:03:14.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(218, 1608664080151, 41, 'Kredit', 201000, 16384000, '2020-12-22 19:08:00.000000', 'pembelian voucher Telkomsel dengan id_produk 6'),
(219, 1608675822133, 41, 'Kredit', 201000, 16183000, '2020-12-22 22:23:42.000000', 'pembelian voucher pulsa Telkomsel'),
(220, 1608690338095, 41, 'Kredit', 101000, 16082000, '2020-12-23 02:25:38.000000', 'pembelian voucher pulsa Telkomsel'),
(221, 1608690369387, 41, 'Kredit', 101000, 15981000, '2020-12-23 02:26:09.000000', 'pembelian voucher pulsa Telkomsel'),
(222, 1608690440310, 41, 'Kredit', 101000, 15880000, '2020-12-23 02:27:20.000000', 'pembelian voucher pulsa Telkomsel'),
(223, 1608690449805, 41, 'Kredit', 101000, 15779000, '2020-12-23 02:27:29.000000', 'pembelian voucher pulsa Telkomsel'),
(224, 1608690658117, 41, 'Kredit', 101000, 15678000, '2020-12-23 02:30:58.000000', 'pembelian voucher pulsa Telkomsel'),
(225, 1608691084180, 41, 'Kredit', 101000, 15577000, '2020-12-23 02:38:04.000000', 'pembelian voucher pulsa Telkomsel'),
(226, 1608691313731, 41, 'Kredit', 101000, 15476000, '2020-12-23 02:41:53.000000', 'pembelian voucher pulsa Telkomsel'),
(227, 1608691654689, 41, 'Kredit', 101000, 15375000, '2020-12-23 02:47:34.000000', 'pembelian voucher pulsa Telkomsel'),
(228, 1608691905543, 41, 'Kredit', 101000, 15274000, '2020-12-23 02:51:45.000000', 'pembelian voucher pulsa Telkomsel'),
(229, 1608691995197, 41, 'Kredit', 101000, 15173000, '2020-12-23 02:53:15.000000', 'pembelian voucher pulsa Telkomsel'),
(230, 1608692082727, 41, 'Kredit', 101000, 15072000, '2020-12-23 02:54:42.000000', 'pembelian voucher pulsa Telkomsel'),
(231, 1608710921239, 41, 'Kredit', 201000, 14871000, '2020-12-23 08:08:41.000000', 'pembelian voucher pulsa Telkomsel');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `auth`
--
ALTER TABLE `auth`
  ADD PRIMARY KEY (`id_auth`);

--
-- Indexes for table `nasabah`
--
ALTER TABLE `nasabah`
  ADD PRIMARY KEY (`id_nasabah`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `auth`
--
ALTER TABLE `auth`
  MODIFY `id_auth` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=95;

--
-- AUTO_INCREMENT for table `nasabah`
--
ALTER TABLE `nasabah`
  MODIFY `id_nasabah` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=82;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=232;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
