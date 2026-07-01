-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3307
-- Waktu pembuatan: 01 Jul 2026 pada 09.28
-- Versi server: 8.0.30
-- Versi PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_ikan_nila`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_admin`
--

CREATE TABLE `tbl_admin` (
  `id_admin` int NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `nama_admin` varchar(100) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbl_admin`
--

INSERT INTO `tbl_admin` (`id_admin`, `username`, `password`, `nama_admin`) VALUES
(1, 'abiyu', 'password', 'Abiyu Ramzi'),
(2, 'adit', 'pass12345678', 'Aditya Rahman'),
(3, 'esa', 'makassar', 'Mahesa'),
(4, 'nopaall', 'nopall2480', 'Naufal Rafif');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_aturan`
--

CREATE TABLE `tbl_aturan` (
  `id_aturan` int NOT NULL,
  `kode_penyakit` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `kode_gejala` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `nilai_mb` float DEFAULT NULL,
  `nilai_md` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbl_aturan`
--

INSERT INTO `tbl_aturan` (`id_aturan`, `kode_penyakit`, `kode_gejala`, `nilai_mb`, `nilai_md`) VALUES
(1, 'P01', 'G02', 0.7, 0.1),
(2, 'P01', 'G04', 0.8, 0.1),
(3, 'P01', 'G09', 0.6, 0.1),
(4, 'P01', 'G21', 0.8, 0.1),
(5, 'P01', 'G25', 0.7, 0.1),
(6, 'P01', 'G26', 0.6, 0.1),
(7, 'P02', 'G01', 0.6, 0.1),
(8, 'P02', 'G02', 0.8, 0.1),
(9, 'P02', 'G19', 0.8, 0.1),
(10, 'P02', 'G20', 0.9, 0.1),
(11, 'P02', 'G21', 0.7, 0.1),
(12, 'P02', 'G24', 0.6, 0.1),
(13, 'P03', 'G02', 0.8, 0.1),
(14, 'P03', 'G04', 0.6, 0.1),
(15, 'P03', 'G05', 0.7, 0.1),
(16, 'P03', 'G20', 0.8, 0.1),
(17, 'P03', 'G21', 0.9, 0.1),
(18, 'P04', 'G04', 0.9, 0.1),
(19, 'P04', 'G16', 0.9, 0.1),
(20, 'P04', 'G25', 0.8, 0.1),
(21, 'P04', 'G28', 0.7, 0.1),
(22, 'P05', 'G07', 0.9, 0.1),
(23, 'P05', 'G17', 1, 0.1),
(24, 'P05', 'G22', 0.8, 0.1),
(25, 'P05', 'G23', 0.8, 0.1),
(26, 'P05', 'G24', 0.8, 0.1),
(27, 'P05', 'G27', 0.9, 0.1),
(28, 'P06', 'G13', 1, 0.1),
(29, 'P06', 'G14', 0.9, 0.1),
(30, 'P06', 'G18', 0.8, 0.1),
(31, 'P06', 'G28', 0.6, 0.1),
(32, 'P07', 'G04', 0.8, 0.1),
(33, 'P07', 'G15', 0.8, 0.1),
(34, 'P07', 'G16', 1, 0.1),
(35, 'P07', 'G25', 0.7, 0.1),
(36, 'P08', 'G01', 0.7, 0.1),
(37, 'P08', 'G03', 0.8, 0.1),
(38, 'P08', 'G06', 0.7, 0.1),
(39, 'P08', 'G08', 0.8, 0.1),
(40, 'P08', 'G24', 1, 0.1),
(41, 'P08', 'G26', 0.7, 0.1),
(42, 'P08', 'G27', 1, 0.1),
(43, 'P09', 'G12', 0.7, 0.1),
(44, 'P09', 'G15', 0.9, 0.1),
(45, 'P09', 'G18', 0.8, 0.1),
(46, 'P09', 'G30', 1, 0.1),
(47, 'P09', 'G31', 0.9, 0.1),
(48, 'P10', 'G04', 0.7, 0.1),
(49, 'P10', 'G06', 0.6, 0.1),
(50, 'P10', 'G08', 0.6, 0.1),
(51, 'P10', 'G10', 1, 0.1),
(52, 'P11', 'G11', 0.9, 0.1),
(53, 'P11', 'G12', 0.9, 0.1),
(54, 'P11', 'G17', 0.7, 0.1),
(55, 'P11', 'G23', 0.8, 0.1),
(56, 'P11', 'G24', 0.8, 0.1),
(57, 'P11', 'G29', 1, 0.1),
(58, 'P12', 'G08', 0.7, 0.1),
(59, 'P12', 'G11', 0.8, 0.1),
(60, 'P12', 'G12', 0.9, 0.1),
(61, 'P12', 'G18', 0.9, 0.1),
(62, 'P12', 'G29', 0.8, 0.1),
(63, 'P04', 'G12', 0.8, 0.1),
(64, 'P04', 'G13', 1, 0.3);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_diagnosa`
--

CREATE TABLE `tbl_diagnosa` (
  `id_diagnosa` int NOT NULL,
  `id_admin` int DEFAULT NULL,
  `kode_sampel` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tanggal_diagnosa` datetime NOT NULL DEFAULT (now()),
  `hasil_penyakit` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `confidence` float DEFAULT '0',
  `kemungkinan_lain` text COLLATE utf8mb4_general_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbl_diagnosa`
--

INSERT INTO `tbl_diagnosa` (`id_diagnosa`, `id_admin`, `kode_sampel`, `tanggal_diagnosa`, `hasil_penyakit`, `confidence`, `kemungkinan_lain`) VALUES
(1, 4, 'SPL-001', '2026-06-26 00:46:10', 'P01', 98.2, '- Cacing Kulit Gyrodactylosis (Skin Fluke Disease) (Akurasi: 94.00 %)\n- Cacing Insang Dactylogyriasis (Gill Fluke Disease) (Akurasi: 90.00 %)\n- Cacing Jangkar (Lerneosis) (Akurasi: 88.00 %)'),
(2, 4, 'SPL-002', '2026-06-26 00:47:26', 'P05', 99.74, '- Luka Merah (Aeromoniasis) (Akurasi: 88.00 %)'),
(3, 4, 'SPL-003', '2026-06-26 00:48:13', 'P11', 99.51, '- Penyakit Pseudomonas (Pseudomoniasis) (Akurasi: 97.36 %)\n- Virus Tilapia Lake (TiLV Disease) (Akurasi: 72.00 %)\n- Penyakit Columnaris (Columnariasis) (Akurasi: 60.00 %)'),
(4, 4, 'SPL-004', '2026-06-26 00:49:02', 'P04', 97.92, '- Cacing Jangkar (Lerneosis) (Akurasi: 97.00 %)\n- Penyakit Trichodina (Trichodiniasis) (Akurasi: 70.00 %)\n- Bintik Putih (White Spot Disease / Ichthyophthiriasis) (Akurasi: 60.00 %)'),
(5, 4, 'SPL-005', '2026-06-26 00:49:54', 'P06', 99.12, '- Penyakit Pseudomonas (Pseudomoniasis) (Akurasi: 64.00 %)\n- Penyakit Columnaris (Columnariasis) (Akurasi: 56.00 %)'),
(6, 4, 'SPL-006', '2026-06-26 00:50:33', 'P10', 97.2, '- Cacing Kulit Gyrodactylosis (Skin Fluke Disease) (Akurasi: 80.00 %)\n- Penyakit Trichodina (Trichodiniasis) (Akurasi: 70.00 %)\n- Cacing Jangkar (Lerneosis) (Akurasi: 70.00 %)'),
(7, 4, 'SPL-007', '2026-06-26 14:38:29', 'P11', 99.79, '- Penyakit Pseudomonas (Pseudomoniasis) (Akurasi: 98.20 %)\n- Penyakit Streptococcus (Streptococcal Disease) (Akurasi: 72.00 %)\n- Cacing Kulit Gyrodactylosis (Skin Fluke Disease) (Akurasi: 70.00 %)');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_diagnosa_detail`
--

CREATE TABLE `tbl_diagnosa_detail` (
  `id_detail` int NOT NULL,
  `id_diagnosa` int NOT NULL,
  `kode_gejala` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `kondisi_gejala` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbl_diagnosa_detail`
--

INSERT INTO `tbl_diagnosa_detail` (`id_detail`, `id_diagnosa`, `kode_gejala`, `kondisi_gejala`) VALUES
(1, 1, 'G04', 'Pasti (1.0)'),
(2, 1, 'G09', 'Pasti (1.0)'),
(3, 1, 'G21', 'Pasti (1.0)'),
(4, 1, 'G25', 'Pasti (1.0)'),
(5, 2, 'G07', 'Pasti (1.0)'),
(6, 2, 'G17', 'Pasti (1.0)'),
(7, 2, 'G22', 'Sangat Yakin (0.8)'),
(8, 2, 'G23', 'Pasti (1.0)'),
(9, 3, 'G11', 'Pasti (1.0)'),
(10, 3, 'G12', 'Pasti (1.0)'),
(11, 3, 'G24', 'Sangat Yakin (0.8)'),
(12, 3, 'G29', 'Sangat Yakin (0.8)'),
(13, 4, 'G04', 'Pasti (1.0)'),
(14, 4, 'G16', 'Pasti (1.0)'),
(15, 4, 'G28', 'Sangat Yakin (0.8)'),
(16, 5, 'G13', 'Pasti (1.0)'),
(17, 5, 'G14', 'Pasti (1.0)'),
(18, 5, 'G18', 'Sangat Yakin (0.8)'),
(19, 6, 'G04', 'Pasti (1.0)'),
(20, 6, 'G06', 'Yakin (0.6)'),
(21, 6, 'G10', 'Pasti (1.0)'),
(22, 7, 'G11', 'Pasti (1.0)'),
(23, 7, 'G12', 'Pasti (1.0)'),
(24, 7, 'G17', 'Sangat Yakin (0.8)'),
(25, 7, 'G29', 'Pasti (1.0)');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_gejala`
--

CREATE TABLE `tbl_gejala` (
  `kode_gejala` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `nama_gejala` varchar(255) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbl_gejala`
--

INSERT INTO `tbl_gejala` (`kode_gejala`, `nama_gejala`) VALUES
('G01', 'Aktivitas berenang ikan menurun dan gerakannya terlihat lebih lambat dari kondisi normal'),
('G02', 'Ikan sering muncul ke permukaan sambil membuka mulut seperti kekurangan oksigen'),
('G03', 'Pola renang ikan tidak normal, sering meloncat atau bergerak secara tidak beraturan'),
('G04', 'Ikan sering menggesekkan atau menabrakkan tubuhnya pada dinding kolam, dasar kolam, maupun benda di sekitarnya akibat iritasi.'),
('G05', 'Ikan cenderung berkumpul di sekitar saluran masuk air atau area dengan kandungan oksigen lebih tinggi'),
('G06', 'Nafsu makan ikan mengalami penurunan secara signifikan'),
('G07', 'Ikan berenang berputar, kehilangan arah, atau mengalami gangguan koordinasi gerak'),
('G08', 'Kondisi tubuh ikan terlihat lemah dan respons terhadap lingkungan menurun'),
('G09', 'Warna tubuh ikan berubah menjadi lebih pucat atau lebih gelap dari biasanya'),
('G10', 'Muncul bintik-bintik putih pada tubuh, sirip, atau insang ikan'),
('G11', 'Terdapat luka pada kulit yang berkembang menjadi borok atau ulser'),
('G12', 'Muncul pendarahan pada kulit, sirip, atau bagian tutup insang'),
('G13', 'Terlihat benang-benang halus berwarna putih menyerupai kapas pada permukaan tubuh ikan'),
('G14', 'Terdapat pertumbuhan hifa atau miselium berwarna putih hingga kecokelatan di sekitar luka'),
('G15', 'Sirip tampak rusak, geripis, menguncup, atau mengalami pembusukan'),
('G16', 'Area tempat menempelnya parasit menunjukkan warna kemerahan atau peradangan'),
('G17', 'Salah satu atau kedua mata ikan tampak menonjol'),
('G18', 'Permukaan kulit mengalami kerusakan jaringan atau nekrosis'),
('G19', 'Insang tampak kemerahan atau mengalami peradangan'),
('G20', 'Insang terlihat pucat atau membengkak sehingga mengganggu pernapasan'),
('G21', 'Produksi lendir pada insang meningkat secara berlebihan sehingga sebagian permukaan insang tampak tertutup lendir'),
('G22', 'Kornea mata mengalami kekeruhan atau terdapat infeksi di sekitar mata'),
('G23', 'Perut ikan membesar secara tidak normal dan pada beberapa kasus disertai penumpukan cairan (dropsy).'),
('G24', 'Terjadi peningkatan jumlah kematian ikan dalam waktu relatif singkat'),
('G25', 'Produksi lendir pada permukaan tubuh meningkat secara berlebihan'),
('G26', 'Ikan cenderung memisahkan diri dari kelompok dan lebih sering diam di dasar kolam'),
('G27', 'Ikan mengalami kehilangan keseimbangan saat berenang'),
('G28', 'Permukaan kulit tampak kasar, kusam, atau kehilangan kilap alaminya'),
('G29', 'Terjadi pendarahan pada pangkal sirip atau di sekitar anus'),
('G30', 'Mulut ikan tampak memutih atau mengalami pembusukan'),
('G31', 'Sirip atau bagian tubuh tampak ditutupi lapisan putih keabu-abuan'),
('G32', 'Tubuh tampak kusam, kehilangan warna cerah alaminya, dan sering disertai lapisan lendir berlebih'),
('G33', 'Terdapat bercak putih keabu-abuan pada kulit atau insang'),
('G34', 'organ dalam hati');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbl_penyakit`
--

CREATE TABLE `tbl_penyakit` (
  `kode_penyakit` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `nama_penyakit` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `Deskripsi` text COLLATE utf8mb4_general_ci NOT NULL,
  `solusi` text COLLATE utf8mb4_general_ci,
  `pencegahan` text COLLATE utf8mb4_general_ci
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbl_penyakit`
--

INSERT INTO `tbl_penyakit` (`kode_penyakit`, `nama_penyakit`, `Deskripsi`, `solusi`, `pencegahan`) VALUES
('P01', 'Penyakit Trichodina (Trichodiniasis)', 'Infeksi protozoa Trichodina sp. pada insang dan permukaan tubuh ikan nila yang menyebabkan iritasi kulit, peningkatan produksi lendir, dan gangguan pernapasan.', 'Lakukan pergantian air secara berkala, kurangi kepadatan tebar, serta lakukan perendaman menggunakan larutan garam atau formalin sesuai dosis dan anjuran pakar.', 'Menjaga kualitas air kolam, melakukan pergantian air secara berkala, mengurangi kepadatan tebar, serta menghindari penumpukan bahan organik pada dasar kolam.'),
('P02', 'Branchiomycosis (Busuk Insang)', 'Infeksi jamur Branchiomyces sp. pada jaringan insang yang menyebabkan kerusakan insang, gangguan pernapasan, dan penurunan kondisi ikan secara umum.', 'Perbaiki kualitas air kolam, tingkatkan aerasi, dan lakukan penanganan menggunakan bahan antijamur sesuai dosis dan rekomendasi pakar.', 'Memastikan sirkulasi dan aerasi air berjalan baik, menjaga kebersihan kolam, serta menghindari penurunan kadar oksigen terlarut.'),
('P03', 'Cacing Insang Dactylogyriasis (Gill Fluke Disease)', 'Infestasi cacing monogenea Dactylogyrus sp. pada insang yang menyebabkan kerusakan jaringan insang, produksi lendir berlebih, gangguan pernapasan, dan penurunan kondisi ikan secara umum.', 'Lakukan perendaman menggunakan larutan garam atau formalin sesuai dosis yang dianjurkan, serta kurangi penumpukan lumpur dan bahan organik pada kolam.', 'Menjaga kebersihan kolam, mengurangi kepadatan ikan, dan melakukan karantina terhadap benih baru sebelum ditebar.'),
('P04', 'Cacing Kulit Gyrodactylosis (Skin Fluke Disease)', 'Infestasi parasit Gyrodactylus sp. pada permukaan kulit ikan yang menyebabkan iritasi, peningkatan produksi lendir, luka, dan peradangan pada kulit.', 'Tingkatkan kualitas air dan kadar oksigen terlarut, serta lakukan perendaman menggunakan larutan garam atau bahan antiparasit sesuai anjuran pakar.', 'Menjaga kualitas air dan kadar oksigen terlarut, menghindari kepadatan tebar berlebihan, serta melakukan pemeriksaan kesehatan ikan secara berkala.'),
('P05', 'Penyakit Streptococcus (Streptococcal Disease)', 'Infeksi bakteri Streptococcus sp. yang menyerang sistem saraf dan organ dalam ikan nila sehingga dapat menyebabkan gangguan keseimbangan berenang dan tingkat mortalitas yang tinggi.', 'Pisahkan ikan yang sakit, jaga kualitas air dan suhu kolam tetap stabil, serta lakukan pengobatan menggunakan antibiotik sesuai rekomendasi tenaga ahli atau pakar perikanan.', 'Menjaga kualitas air dan kadar oksigen terlarut tetap stabil, menghindari stres pada ikan, memberikan pakan berkualitas, serta meminimalkan fluktuasi suhu yang ekstrem.'),
('P06', 'Jamur Kapas (Saprolegniasis)', 'Infeksi jamur Saprolegnia sp. pada luka terbuka atau jaringan yang mengalami stres, ditandai dengan pertumbuhan miselium menyerupai kapas pada tubuh ikan.', 'Buang ikan yang mati, bersihkan kolam secara rutin, dan lakukan perendaman menggunakan larutan garam atau bahan antijamur sesuai dosis yang dianjurkan.', 'Menghindari luka pada tubuh ikan, segera memisahkan ikan yang sakit, menjaga kebersihan kolam dan peralatan budidaya, serta menerapkan biosekuriti.'),
('P07', 'Cacing Jangkar (Lerneosis)', 'Infestasi parasit Lernaea cyprinacea yang menancapkan diri pada permukaan tubuh ikan sehingga menimbulkan luka, peradangan, dan infeksi sekunder.', 'Parasit yang menempel dapat diangkat secara hati-hati, kemudian dilakukan perendaman menggunakan bahan desinfektan atau larutan garam sesuai rekomendasi pakar.', 'Melakukan karantina ikan baru, menjaga kualitas air, serta membersihkan kolam dan peralatan secara rutin.'),
('P08', 'Virus Tilapia Lake (TiLV Disease)', 'Infeksi Tilapia Lake Virus (TiLV) yang menyebabkan penurunan kondisi tubuh, gangguan pertumbuhan, dan kematian massal pada ikan nila, serta hingga saat ini belum tersedia pengobatan spesifik.', 'Hingga saat ini belum tersedia pengobatan spesifik. Penanganan dilakukan melalui karantina ikan sakit, peningkatan biosekuriti kolam, pengurangan stres, dan pembuangan ikan mati secara cepat untuk mencegah penyebaran penyakit.', 'Menerapkan biosekuriti, menggunakan benih sehat, menghindari perpindahan ikan tanpa karantina, serta segera membuang ikan yang mati.'),
('P09', 'Penyakit Columnaris (Columnariasis)', 'Infeksi bakteri Flavobacterium columnare yang menyerang kulit, insang, dan sirip ikan serta dapat menyebabkan erosi jaringan dan kerusakan progresif pada tubuh ikan.', 'Tingkatkan kualitas air, kurangi kepadatan tebar, dan lakukan pengobatan sesuai anjuran pakar untuk menghambat perkembangan infeksi bakteri.', 'Menjaga kualitas air dan kadar oksigen terlarut, mencegah luka fisik pada ikan, serta menghindari kepadatan tebar yang terlalu tinggi.'),
('P10', 'Bintik Putih (White Spot Disease / Ichthyophthiriasis)', 'Infeksi protozoa Ichthyophthirius multifiliis yang sangat menular dan ditandai dengan munculnya bintik-bintik putih pada tubuh, sirip, dan insang ikan.', 'Lakukan perendaman menggunakan formalin atau bahan antiparasit sesuai dosis yang dianjurkan serta jaga kualitas air dan suhu kolam agar tetap stabil.', 'Menjaga kualitas air dan suhu kolam tetap stabil, menghindari stres, serta melakukan karantina ikan baru.'),
('P11', 'Luka Merah (Aeromoniasis)', 'Infeksi bakteri Aeromonas hydrophila yang menyebabkan pendarahan, peradangan, dan terbentuknya luka borok pada permukaan tubuh ikan nila.', 'Jaga kebersihan kolam, lakukan pergantian air secara rutin, serta lakukan pengobatan menggunakan antibiotik sesuai rekomendasi pakar dan ketentuan yang berlaku.', 'Menjaga kebersihan kolam, menghindari penumpukan sisa pakan, melakukan pergantian air secara rutin, serta meminimalkan stres pada ikan.'),
('P12', 'Penyakit Pseudomonas (Pseudomoniasis)', 'Infeksi bakteri Pseudomonas sp. yang dapat menyebabkan nekrosis jaringan, pendarahan pada tubuh, serta penurunan kondisi kesehatan ikan secara umum.', 'Pisahkan ikan yang terinfeksi, perbaiki kualitas air, dan lakukan pengobatan sesuai rekomendasi pakar atau tenaga kesehatan perikanan.', 'Menjaga kualitas air tetap baik, menghindari luka pada tubuh ikan, serta melakukan sanitasi kolam dan peralatan budidaya secara rutin.'),
('P13', 'Epistylosis', 'penyakit', 'PH air', 'Hati-hati');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tbl_admin`
--
ALTER TABLE `tbl_admin`
  ADD PRIMARY KEY (`id_admin`);

--
-- Indeks untuk tabel `tbl_aturan`
--
ALTER TABLE `tbl_aturan`
  ADD PRIMARY KEY (`id_aturan`),
  ADD KEY `kode_penyakit` (`kode_penyakit`),
  ADD KEY `kode_gejala` (`kode_gejala`);

--
-- Indeks untuk tabel `tbl_diagnosa`
--
ALTER TABLE `tbl_diagnosa`
  ADD PRIMARY KEY (`id_diagnosa`),
  ADD KEY `hasil_penyakit` (`hasil_penyakit`),
  ADD KEY `fk_diagnosa_admin` (`id_admin`);

--
-- Indeks untuk tabel `tbl_diagnosa_detail`
--
ALTER TABLE `tbl_diagnosa_detail`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `kode_gejala` (`kode_gejala`);

--
-- Indeks untuk tabel `tbl_gejala`
--
ALTER TABLE `tbl_gejala`
  ADD PRIMARY KEY (`kode_gejala`),
  ADD KEY `kode_gejala` (`kode_gejala`);

--
-- Indeks untuk tabel `tbl_penyakit`
--
ALTER TABLE `tbl_penyakit`
  ADD PRIMARY KEY (`kode_penyakit`),
  ADD KEY `kode_penyakit` (`kode_penyakit`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `tbl_admin`
--
ALTER TABLE `tbl_admin`
  MODIFY `id_admin` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `tbl_aturan`
--
ALTER TABLE `tbl_aturan`
  MODIFY `id_aturan` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

--
-- AUTO_INCREMENT untuk tabel `tbl_diagnosa`
--
ALTER TABLE `tbl_diagnosa`
  MODIFY `id_diagnosa` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `tbl_diagnosa_detail`
--
ALTER TABLE `tbl_diagnosa_detail`
  MODIFY `id_detail` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tbl_aturan`
--
ALTER TABLE `tbl_aturan`
  ADD CONSTRAINT `tbl_aturan_ibfk_1` FOREIGN KEY (`kode_penyakit`) REFERENCES `tbl_penyakit` (`kode_penyakit`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tbl_aturan_ibfk_2` FOREIGN KEY (`kode_gejala`) REFERENCES `tbl_gejala` (`kode_gejala`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `tbl_diagnosa`
--
ALTER TABLE `tbl_diagnosa`
  ADD CONSTRAINT `fk_diagnosa_admin` FOREIGN KEY (`id_admin`) REFERENCES `tbl_admin` (`id_admin`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `tbl_diagnosa_ibfk_2` FOREIGN KEY (`hasil_penyakit`) REFERENCES `tbl_penyakit` (`kode_penyakit`) ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `tbl_diagnosa_detail`
--
ALTER TABLE `tbl_diagnosa_detail`
  ADD CONSTRAINT `tbl_diagnosa_detail_ibfk_2` FOREIGN KEY (`kode_gejala`) REFERENCES `tbl_gejala` (`kode_gejala`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
