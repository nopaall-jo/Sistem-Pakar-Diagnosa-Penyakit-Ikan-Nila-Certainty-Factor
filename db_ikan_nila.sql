-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.30 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for db_ikan_nila
CREATE DATABASE IF NOT EXISTS `db_ikan_nila` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `db_ikan_nila`;

-- Dumping structure for table db_ikan_nila.tbl_admin
CREATE TABLE IF NOT EXISTS `tbl_admin` (
  `id_admin` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `nama_admin` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_ikan_nila.tbl_admin: ~2 rows (approximately)
INSERT INTO `tbl_admin` (`id_admin`, `username`, `password`, `nama_admin`) VALUES
	(1, 'admin', '$2y$10$bVsvqz8.0cy2QMGJL.Q2jekjf9nAp94uqA4XhUN7ICgXKmBSZ1Gtq', 'Aditya Rahman'),
	(2, 'abi', 'password', 'Abiyu');

-- Dumping structure for table db_ikan_nila.tbl_aturan
CREATE TABLE IF NOT EXISTS `tbl_aturan` (
  `id_aturan` int NOT NULL AUTO_INCREMENT,
  `kode_penyakit` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `kode_gejala` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `cf_pakar` float DEFAULT NULL,
  PRIMARY KEY (`id_aturan`),
  KEY `kode_penyakit` (`kode_penyakit`),
  KEY `kode_gejala` (`kode_gejala`),
  CONSTRAINT `tbl_aturan_ibfk_1` FOREIGN KEY (`kode_penyakit`) REFERENCES `tbl_penyakit` (`kode_penyakit`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tbl_aturan_ibfk_2` FOREIGN KEY (`kode_gejala`) REFERENCES `tbl_gejala` (`kode_gejala`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_ikan_nila.tbl_aturan: ~48 rows (approximately)
INSERT INTO `tbl_aturan` (`id_aturan`, `kode_penyakit`, `kode_gejala`, `cf_pakar`) VALUES
	(1, 'P01', 'G01', 0.4),
	(2, 'P01', 'G02', 0.4),
	(3, 'P01', 'G04', 0.8),
	(4, 'P01', 'G05', 0.6),
	(5, 'P01', 'G08', 0.6),
	(6, 'P02', 'G01', 0.4),
	(7, 'P02', 'G02', 0.6),
	(8, 'P02', 'G05', 0.6),
	(9, 'P02', 'G10', 0.4),
	(10, 'P02', 'G21', 0.8),
	(11, 'P03', 'G01', 0.4),
	(12, 'P03', 'G04', 0.6),
	(13, 'P03', 'G10', 0.4),
	(14, 'P03', 'G16', 0.8),
	(15, 'P04', 'G01', 0.4),
	(16, 'P04', 'G04', 0.6),
	(17, 'P04', 'G16', 0.6),
	(18, 'P04', 'G19', 0.8),
	(19, 'P05', 'G06', 0.6),
	(20, 'P05', 'G09', 0.6),
	(21, 'P05', 'G13', 0.8),
	(22, 'P05', 'G20', 0.6),
	(23, 'P05', 'G21', 0.6),
	(24, 'P05', 'G27', 0.8),
	(25, 'P06', 'G14', 1),
	(26, 'P06', 'G15', 0.8),
	(27, 'P06', 'G12', 0.6),
	(28, 'P07', 'G04', 0.6),
	(29, 'P07', 'G17', 1),
	(30, 'P07', 'G16', 0.6),
	(31, 'P08', 'G01', 0.4),
	(32, 'P08', 'G09', 0.6),
	(33, 'P08', 'G28', 0.8),
	(34, 'P08', 'G30', 1),
	(35, 'P09', 'G10', 0.4),
	(36, 'P09', 'G13', 0.6),
	(37, 'P09', 'G16', 0.8),
	(38, 'P09', 'G29', 0.8),
	(39, 'P10', 'G11', 1),
	(40, 'P10', 'G04', 0.8),
	(41, 'P10', 'G01', 0.4),
	(42, 'P11', 'G01', 0.4),
	(43, 'P11', 'G18', 0.8),
	(44, 'P11', 'G25', 0.8),
	(45, 'P12', 'G10', 0.6),
	(46, 'P12', 'G13', 0.8),
	(47, 'P12', 'G16', 0.6),
	(48, 'P12', 'G20', 0.8);

-- Dumping structure for table db_ikan_nila.tbl_diagnosa
CREATE TABLE IF NOT EXISTS `tbl_diagnosa` (
  `id_diagnosa` int NOT NULL AUTO_INCREMENT,
  `id_admin` int DEFAULT NULL,
  `nama_pembudidaya` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `tanggal_diagnosa` datetime NOT NULL DEFAULT (now()),
  `hasil_penyakit` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `confidence` float DEFAULT '0',
  PRIMARY KEY (`id_diagnosa`),
  KEY `hasil_penyakit` (`hasil_penyakit`),
  CONSTRAINT `tbl_diagnosa_ibfk_2` FOREIGN KEY (`hasil_penyakit`) REFERENCES `tbl_penyakit` (`kode_penyakit`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_ikan_nila.tbl_diagnosa: ~9 rows (approximately)
INSERT INTO `tbl_diagnosa` (`id_diagnosa`, `id_admin`, `nama_pembudidaya`, `tanggal_diagnosa`, `hasil_penyakit`, `confidence`) VALUES
	(1, 1, 'Afif', '2026-05-18 00:00:00', 'P10', 100),
	(2, 1, 'Nanda', '2026-05-18 00:00:00', 'P06', 89.06),
	(3, 1, 'Adit', '2026-05-18 00:00:00', 'P05', 96.92),
	(4, 1, 'ada', '2026-05-18 11:24:16', 'P12', 85.6),
	(5, 1, 'Bagas', '2026-05-18 11:34:41', 'P12', 48),
	(6, 1, 'ada', '2026-05-18 11:52:50', 'P12', 32),
	(7, 1, 'gdfgr', '2026-05-18 11:53:27', 'P06', 100),
	(8, 1, 'JJJJ', '2026-05-18 12:06:34', 'P06', 16),
	(9, 1, 'LKLKNK', '2026-05-18 12:12:15', 'P02', 60);

-- Dumping structure for table db_ikan_nila.tbl_diagnosa_detail
CREATE TABLE IF NOT EXISTS `tbl_diagnosa_detail` (
  `id_detail` int NOT NULL AUTO_INCREMENT,
  `id_diagnosa` int NOT NULL,
  `kode_gejala` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id_detail`),
  KEY `kode_gejala` (`kode_gejala`),
  CONSTRAINT `tbl_diagnosa_detail_ibfk_2` FOREIGN KEY (`kode_gejala`) REFERENCES `tbl_gejala` (`kode_gejala`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_ikan_nila.tbl_diagnosa_detail: ~23 rows (approximately)
INSERT INTO `tbl_diagnosa_detail` (`id_detail`, `id_diagnosa`, `kode_gejala`) VALUES
	(1, 1, 'G01'),
	(2, 1, 'G04'),
	(3, 1, 'G11'),
	(4, 2, 'G12'),
	(5, 2, 'G14'),
	(6, 2, 'G15'),
	(7, 3, 'G06'),
	(8, 3, 'G09'),
	(9, 3, 'G13'),
	(10, 3, 'G20'),
	(11, 3, 'G27'),
	(12, 4, 'G10'),
	(13, 4, 'G13'),
	(14, 5, 'G10'),
	(15, 5, 'G15'),
	(16, 5, 'G23'),
	(17, 6, 'G13'),
	(18, 7, 'G03'),
	(19, 7, 'G14'),
	(20, 8, 'G15'),
	(21, 9, 'G02'),
	(22, 9, 'G18'),
	(23, 9, 'G29');

-- Dumping structure for table db_ikan_nila.tbl_gejala
CREATE TABLE IF NOT EXISTS `tbl_gejala` (
  `kode_gejala` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `nama_gejala` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`kode_gejala`),
  KEY `kode_gejala` (`kode_gejala`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_ikan_nila.tbl_gejala: ~30 rows (approximately)
INSERT INTO `tbl_gejala` (`kode_gejala`, `nama_gejala`) VALUES
	('G01', 'Ikan berenang lamban atau malas bergerak'),
	('G02', 'Ikan sering mengap-mengap di permukaan air'),
	('G03', 'Ikan sering melompat atau berenang tidak beraturan (abnormal)'),
	('G04', 'Ikan menggosokkan badan ke dinding/dasar/benda (iritasi kulit)'),
	('G05', 'Ikan berkumpul di area air masuk/daerah oksigen tinggi'),
	('G06', 'Nafsu makan menurun drastis'),
	('G07', 'Pergerakan tidak terarah/berputar/kejang-kejang'),
	('G08', 'Ikan tampak gelisah/sering muncul di permukaan'),
	('G09', 'Ikan terlihat lemah dan kesadarannya menurun'),
	('G10', 'Warna tubuh pucat atau menjadi lebih gelap dari normal'),
	('G11', 'Terdapat bercak putih pada tubuh, sirip, atau insang (indikasi Ich)'),
	('G12', 'Luka pada kulit yang kemudian berkembang menjadi borok (ulcer)'),
	('G13', 'Terdapat pendarahan (hemoragi) di kulit, sirip, atau tutup insang'),
	('G14', 'Adanya benang halus menyerupai kapas (Saprolegnia)'),
	('G15', 'Terlihat Hifa/miselia putih-kecoklatan di sekitar luka'),
	('G16', 'Sirip rusak, geripis, atau menguncup'),
	('G17', 'Warna kemerahan (inflamasi) di area penempelan parasit (Lernaea)'),
	('G18', 'Mata menonjol (exophthalmus)'),
	('G19', 'Permukaan kulit menunjukkan tanda nekrosis (jaringan mati)'),
	('G20', 'Insang berwarna merah cerah (tanda awal infeksi)'),
	('G21', 'Insang pucat atau membengkak (hipoksia/infeksi parasit)'),
	('G22', 'Produksi mukus berlebih pada insang'),
	('G23', 'Insang terinfeksi dan tampak berlendir tebal'),
	('G24', 'Hilangnya lingkaran emas (golden ring) di sekitar mata'),
	('G25', 'Terjadi infeksi di sekitar mata / kornea keruh (katarak)'),
	('G26', 'Perut membengkak (dropsy), kadang berisi cairan'),
	('G27', 'Hati, limpa, atau ginjal mengalami pembengkakan (swollen organs)'),
	('G28', 'Kematian massal dalam waktu singkat (infeksi sistemik)'),
	('G29', 'Produksi lendir bercampur darah di kulit (Aeromonas)'),
	('G30', 'Mortalitas meningkat mendadak pada suhu air tinggi (indikasi TiLV)');

-- Dumping structure for table db_ikan_nila.tbl_penyakit
CREATE TABLE IF NOT EXISTS `tbl_penyakit` (
  `kode_penyakit` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `nama_penyakit` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `solusi` text COLLATE utf8mb4_general_ci,
  `pencegahan` text COLLATE utf8mb4_general_ci,
  PRIMARY KEY (`kode_penyakit`),
  KEY `kode_penyakit` (`kode_penyakit`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table db_ikan_nila.tbl_penyakit: ~12 rows (approximately)
INSERT INTO `tbl_penyakit` (`kode_penyakit`, `nama_penyakit`, `solusi`, `pencegahan`) VALUES
	('P01', 'Penyakit Trichodiniasis', 'Perendaman formalin 25 ppm 30 menit atau garam 5-10 g/L selama 10-15 menit. Ganti air kolam secara berkala dan kurangi kepadatan tebar.', 'Jaga kualitas air tetap bersih, kurangi kepadatan ikan.'),
	('P02', 'Penyakit Jamur Insang', 'Bersihkan kolam, ganti air, rendam ikan dengan fungisida ringan (malachite green / KMnO4).', 'Hindari pemberian pakan berlebih yang memicu jamur.'),
	('P03', 'Penyakit Cacing Insang', 'Gunakan larutan formalin 25 ppm atau NaCl 2% selama 10-15 menit. Keringkan kolam dan kurangi lumpur.', 'Keringkan dan kapur dasar kolam sebelum tebar benih.'),
	('P04', 'Penyakit Cacing Kulit', 'Lakukan perendaman ikan dengan formalin 25-50 ppm atau air garam 2-5%. Jaga kualitas air tetap jernih dan oksigen cukup.', 'Karantina ikan baru sebelum dimasukkan ke kolam utama.'),
	('P05', 'Penyakit Streptococcosis', 'Berikan antibiotik seperti oksitetrasiklin 50-75 mg/kg pakan selama 5-7 hari. Isolasi ikan sakit dan jaga suhu air di bawah 30°C.', 'Hindari kepadatan tinggi, jangan memberi pakan berlebih.'),
	('P06', 'Penyakit Jamur Kapas', 'Bersihkan kolam, hilangkan ikan mati, dan rendam ikan dalam larutan NaCl 2% selama 10 menit. Gunakan fungisida malachite green 0,1 ppm bila perlu.', 'Hindari penanganan ikan yang kasar agar tubuh ikan tidak luka.'),
	('P07', 'Penyakit Cacing Jangkar', 'Angkat parasit secara manual dengan pinset lalu rendam ikan dalam larutan KMnO4 2 ppm selama 30 menit. Tambahkan garam 5 g/liter untuk desinfeksi.', 'Pasang filter pada saluran masuk air untuk mencegah masuknya parasit.'),
	('P08', 'Penyakit Tilapia Lake Virus (TiLV)', 'Belum ada obat spesifik, lakukan karantina ikan sakit, buang ikan mati segera, dan tingkatkan biosekuriti kolam. Hindari stres dan perubahan suhu mendadak.', 'Gunakan benih bersertifikat bebas TiLV (SPF), batasi lalu lintas orang/alat.'),
	('P09', 'Penyakit Busuk Sirip', 'Lakukan perendaman dengan antibiotik oksitetrasiklin 10 mg/l atau KMnO4 2 ppm. Kurangi kepadatan tebar dan perbaiki aerasi kolam.', 'Perbaiki manajemen kualitas air dan hindari penumpukan bahan organik.'),
	('P10', 'Penyakit Bintik Putih', 'Gunakan larutan formalin 25 ppm atau malachite green 0,1 ppm. Jaga suhu air pada 30-32°C untuk mempercepat siklus hidup parasit.', 'Jaga kestabilan suhu air (gunakan heater jika perlu).'),
	('P11', 'Penyakit Mata Menonjol', 'Lakukan perendaman ikan dalam larutan formalin 25 ppm selama 30 menit dan ganti air secara berkala.', 'Jaga kebersihan lingkungan budidaya dan berikan pakan bernutrisi.'),
	('P12', 'Penyakit Luka Merah', 'Berikan antibiotik oksitetrasiklin 75 mg/kg pakan selama 5 hari dan jaga kebersihan air. Lakukan pergantian air 50% setiap 2 hari.', 'Lakukan vaksinasi anti-Aeromonas dan sanitasi kolam rutin.');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
