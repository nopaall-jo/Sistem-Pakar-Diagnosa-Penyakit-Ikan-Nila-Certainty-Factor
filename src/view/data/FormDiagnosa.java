/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.data;
import view.data.FormGejala;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import koneksi.KoneksiDB;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author NAUFAL
 */
public class FormDiagnosa extends javax.swing.JFrame {
    // --- WARNA TEMA ---
    Color colorNormal = new Color(255, 243, 236);
    Color colorHover = new Color(255, 220, 230);
    Color colorActive = new Color(173, 216, 255);
    /**
     * Creates new form FormDiagnosa
     */
    public FormDiagnosa() {
        initComponents();
        if (koneksi.Session.namaAdmin == null || koneksi.Session.namaAdmin.equals("")) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Hayo, Anda harus Login terlebih dahulu.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose(); 
            return; 
        }

        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);        
        setJamRealTime();
        setTanggalSekarang();
        
        for (Component comp : sidebar.getComponents()) {
            if (comp instanceof JPanel) {
                comp.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
        
        switchWarna(btnDiagnosa); 
        setupComboBoxDiTabel();
        tampilGejala();
        aturFormatTabel();
        String nama = koneksi.Session.namaAdmin;
        String role = koneksi.Session.role;
        lblAdmin.setText(role + " | " + nama);
    }
    
    private void aturFormatTabel() {
        // ===============================================
        // 1. FORMAT TABEL UTAMA (tblDiagnosa)
        // ===============================================
        tblDiagnosa.setRowHeight(45); // Kasih ruang napas buat teks gejala
        
        // --- TAMBAHAN: MUNCULIN GARIS TABEL DIAGNOSA ---
        tblDiagnosa.setShowGrid(true); 
        tblDiagnosa.setShowVerticalLines(true);   
        tblDiagnosa.setShowHorizontalLines(true); 
        tblDiagnosa.setGridColor(new java.awt.Color(153, 153, 153)); 
        tblDiagnosa.setIntercellSpacing(new java.awt.Dimension(1, 1)); 
        
        if (tblDiagnosa.getColumnCount() == 4) {
            javax.swing.table.TableColumnModel cm = tblDiagnosa.getColumnModel();
            
            // Atur Lebar Kolom
            cm.getColumn(0).setPreferredWidth(45);   // No
            cm.getColumn(1).setPreferredWidth(90);   // Kode Gejala
            cm.getColumn(2).setPreferredWidth(500);  // Gejala yang Dialami Ikan (Paling Lebar)
            cm.getColumn(3).setPreferredWidth(160);  // Pilih Kondisi (ComboBox)
            
            // Pasang Word Wrap HANYA untuk kolom Gejala (Index 2)
            cm.getColumn(2).setCellRenderer(new MultiLineCellRenderer()); 
            
            // Rata Tengah (Center) untuk No & Kode Gejala
            javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            cm.getColumn(0).setCellRenderer(centerRenderer);
            cm.getColumn(1).setCellRenderer(centerRenderer);
        }

        // ===============================================
        // 2. FORMAT TABEL KEDUA (tblPenyakitLain)
        // ===============================================
        // Cek dulu apakah tabelnya beneran ada dan punya 2 kolom
        if (tblPenyakitLain != null && tblPenyakitLain.getColumnCount() == 2) {
            tblPenyakitLain.setRowHeight(35);
            
            // --- TAMBAHAN: MUNCULIN GARIS TABEL PENYAKIT LAIN ---
            tblPenyakitLain.setShowGrid(true); 
            tblPenyakitLain.setShowVerticalLines(true);   
            tblPenyakitLain.setShowHorizontalLines(true); 
            tblPenyakitLain.setGridColor(new java.awt.Color(153, 153, 153)); 
            tblPenyakitLain.setIntercellSpacing(new java.awt.Dimension(1, 1)); 

            javax.swing.table.TableColumnModel cm2 = tblPenyakitLain.getColumnModel();
            
            cm2.getColumn(0).setPreferredWidth(250); // Nama Penyakit Lain
            cm2.getColumn(1).setPreferredWidth(100); // Tingkat Kecocokan
            
            // Rata Tengah untuk kolom persentase
            javax.swing.table.DefaultTableCellRenderer centerLain = new javax.swing.table.DefaultTableCellRenderer();
            centerLain.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            cm2.getColumn(1).setCellRenderer(centerLain);
        }
    }
    
    private void setTanggalSekarang() {
        Date sekarang = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        txtTanggal.setText(format.format(sekarang));
        txtTanggal.setEditable(false);
    }
    
    private void setupComboBoxDiTabel() {
        JComboBox<String> cmbKondisi = new JComboBox<>();
        cmbKondisi.addItem("-- Pilih --");
        cmbKondisi.addItem("Pasti (1.0)");
        cmbKondisi.addItem("Sangat Yakin (0.8)"); 
        cmbKondisi.addItem("Yakin (0.6)");
        cmbKondisi.addItem("Cukup Yakin (0.4)");
        cmbKondisi.addItem("Kurang Yakin (0.2)");
        cmbKondisi.addItem("Tidak Tahu (0.0)"); 

        TableColumn kolomKondisi = tblDiagnosa.getColumnModel().getColumn(3);
        kolomKondisi.setCellEditor(new DefaultCellEditor(cmbKondisi));
    }
    
    private void tampilGejala() {
        DefaultTableModel model = (DefaultTableModel) tblDiagnosa.getModel();
        model.setRowCount(0);
        
        try {
            Connection con = KoneksiDB.getKoneksi();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tbl_gejala ORDER BY kode_gejala ASC");
            
            int no = 1;
            while(rs.next()) {
                model.addRow(new Object[]{
                    no++,
                    rs.getString("kode_gejala"),
                    rs.getString("nama_gejala"),
                    "-- Pilih --" 
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error load gejala: " + e.getMessage());
        }
    }
    
    private double konversiCFUser(String kondisi) {
        if (kondisi.equals("Pasti (1.0)")) {
            return 1.0;
        } else if (kondisi.equals("Sangat Yakin (0.8)")) {
            return 0.8;
        } else if (kondisi.equals("Yakin (0.6)")) {
            return 0.6;
        } else if (kondisi.equals("Cukup Yakin (0.4)")) {
            return 0.4;
        } else if (kondisi.equals("Kurang Yakin (0.2)")) {
            return 0.2;
        } else {
            return 0.0; 
        }
    }
    
    private void switchWarna(javax.swing.JPanel activePanel) {
        btnDashboard.setBackground(colorNormal);
        btnPenyakit.setBackground(colorNormal);
        btnGejala.setBackground(colorNormal);
        btnAturan.setBackground(colorNormal);
        btnDiagnosa.setBackground(colorNormal);
        btnRiwayat.setBackground(colorNormal);
        btnLaporan.setBackground(colorNormal);
        activePanel.setBackground(colorActive);
    }
    
    private void setJamRealTime() {
        javax.swing.Timer timer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Date tanggal = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
                lblJam.setText(format.format(tanggal));
            }
        });
        timer.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sidebar = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        btnDashboard = new javax.swing.JPanel();
        lblDashboard = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        btnGejala = new javax.swing.JPanel();
        lblGejala = new javax.swing.JLabel();
        btnPenyakit = new javax.swing.JPanel();
        lblPenyakit = new javax.swing.JLabel();
        btnAturan = new javax.swing.JPanel();
        lblAturan = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        btnDiagnosa = new javax.swing.JPanel();
        lblDiagnosa = new javax.swing.JLabel();
        btnRiwayat = new javax.swing.JPanel();
        lblRiwayat = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        btnLaporan = new javax.swing.JPanel();
        lblCetak = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        btnLogout = new javax.swing.JPanel();
        lblKeluar = new javax.swing.JLabel();
        pn_kanan = new javax.swing.JPanel();
        mainContent = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnProses = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblDiagnosa = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        txtHasilPenyakit = new javax.swing.JTextField();
        txtAkurasi = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtPencegahanHasil = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtSolusi = new javax.swing.JTextArea();
        btnSimpanRiwayat = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pbAkurasi = new javax.swing.JProgressBar();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPenyakitLain = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDeskripsi = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        lblJam = new javax.swing.JLabel();
        lblAdmin = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sidebar.setBackground(new java.awt.Color(165, 255, 214));
        sidebar.setMinimumSize(new java.awt.Dimension(200, 100));
        sidebar.setPreferredSize(new java.awt.Dimension(250, 768));

        jPanel2.setBackground(new java.awt.Color(81, 226, 245));
        jPanel2.setPreferredSize(new java.awt.Dimension(250, 130));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/fish.png"))); // NOI18N

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/farmer.png"))); // NOI18N

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/kolam.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel2.setText("SISTEM PAKAR");

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel1.setText("IKAN NILA");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(36, 36, 36)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(34, 34, 34))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(165, 255, 214));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MENU UTAMA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnDashboard.setBackground(new java.awt.Color(255, 255, 255));
        btnDashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDashboard.setPreferredSize(new java.awt.Dimension(130, 50));
        btnDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDashboardMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDashboardMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDashboardMousePressed(evt);
            }
        });

        lblDashboard.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/home.png"))); // NOI18N
        lblDashboard.setText("Dashboard");

        javax.swing.GroupLayout btnDashboardLayout = new javax.swing.GroupLayout(btnDashboard);
        btnDashboard.setLayout(btnDashboardLayout);
        btnDashboardLayout.setHorizontalGroup(
            btnDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnDashboardLayout.setVerticalGroup(
            btnDashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnDashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel10.setBackground(new java.awt.Color(165, 255, 214));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DATA MASTER", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnGejala.setBackground(new java.awt.Color(255, 255, 255));
        btnGejala.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGejala.setPreferredSize(new java.awt.Dimension(130, 50));
        btnGejala.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGejalaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGejalaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnGejalaMousePressed(evt);
            }
        });

        lblGejala.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblGejala.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/temperature.png"))); // NOI18N
        lblGejala.setText("Data Gejala");

        javax.swing.GroupLayout btnGejalaLayout = new javax.swing.GroupLayout(btnGejala);
        btnGejala.setLayout(btnGejalaLayout);
        btnGejalaLayout.setHorizontalGroup(
            btnGejalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnGejalaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGejala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnGejalaLayout.setVerticalGroup(
            btnGejalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGejalaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGejala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnPenyakit.setBackground(new java.awt.Color(255, 255, 255));
        btnPenyakit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPenyakit.setPreferredSize(new java.awt.Dimension(130, 50));
        btnPenyakit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPenyakitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPenyakitMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnPenyakitMousePressed(evt);
            }
        });

        lblPenyakit.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bacteria.png"))); // NOI18N
        lblPenyakit.setText("Data Penyakit");

        javax.swing.GroupLayout btnPenyakitLayout = new javax.swing.GroupLayout(btnPenyakit);
        btnPenyakit.setLayout(btnPenyakitLayout);
        btnPenyakitLayout.setHorizontalGroup(
            btnPenyakitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnPenyakitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPenyakit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnPenyakitLayout.setVerticalGroup(
            btnPenyakitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnPenyakitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblPenyakit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnAturan.setBackground(new java.awt.Color(255, 255, 255));
        btnAturan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAturan.setPreferredSize(new java.awt.Dimension(130, 50));
        btnAturan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAturanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAturanMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAturanMousePressed(evt);
            }
        });

        lblAturan.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblAturan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/settings.png"))); // NOI18N
        lblAturan.setText("Basis Aturan");

        javax.swing.GroupLayout btnAturanLayout = new javax.swing.GroupLayout(btnAturan);
        btnAturan.setLayout(btnAturanLayout);
        btnAturanLayout.setHorizontalGroup(
            btnAturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnAturanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAturan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnAturanLayout.setVerticalGroup(
            btnAturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAturanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblAturan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAturan, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
            .addComponent(btnPenyakit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
            .addComponent(btnGejala, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(btnPenyakit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGejala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAturan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(165, 255, 214));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PROSES PAKAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnDiagnosa.setBackground(new java.awt.Color(255, 255, 255));
        btnDiagnosa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDiagnosa.setPreferredSize(new java.awt.Dimension(130, 50));
        btnDiagnosa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDiagnosaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDiagnosaMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDiagnosaMousePressed(evt);
            }
        });

        lblDiagnosa.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/diagnosis.png"))); // NOI18N
        lblDiagnosa.setText("Mulai Diagnosa");

        javax.swing.GroupLayout btnDiagnosaLayout = new javax.swing.GroupLayout(btnDiagnosa);
        btnDiagnosa.setLayout(btnDiagnosaLayout);
        btnDiagnosaLayout.setHorizontalGroup(
            btnDiagnosaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDiagnosaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDiagnosa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnDiagnosaLayout.setVerticalGroup(
            btnDiagnosaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDiagnosaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDiagnosa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnRiwayat.setBackground(new java.awt.Color(255, 255, 255));
        btnRiwayat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRiwayat.setPreferredSize(new java.awt.Dimension(130, 50));
        btnRiwayat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRiwayatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRiwayatMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnRiwayatMousePressed(evt);
            }
        });

        lblRiwayat.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/deadline.png"))); // NOI18N
        lblRiwayat.setText("Riwayat Diagnosa");

        javax.swing.GroupLayout btnRiwayatLayout = new javax.swing.GroupLayout(btnRiwayat);
        btnRiwayat.setLayout(btnRiwayatLayout);
        btnRiwayatLayout.setHorizontalGroup(
            btnRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnRiwayatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRiwayat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnRiwayatLayout.setVerticalGroup(
            btnRiwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnRiwayatLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRiwayat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnRiwayat, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
            .addComponent(btnDiagnosa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(btnDiagnosa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(165, 255, 214));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "LAPORAN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnLaporan.setBackground(new java.awt.Color(255, 255, 255));
        btnLaporan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLaporan.setPreferredSize(new java.awt.Dimension(130, 50));
        btnLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLaporanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLaporanMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLaporanMousePressed(evt);
            }
        });

        lblCetak.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/printer.png"))); // NOI18N
        lblCetak.setText("Cetak Laporan");

        javax.swing.GroupLayout btnLaporanLayout = new javax.swing.GroupLayout(btnLaporan);
        btnLaporan.setLayout(btnLaporanLayout);
        btnLaporanLayout.setHorizontalGroup(
            btnLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLaporanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCetak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnLaporanLayout.setVerticalGroup(
            btnLaporanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLaporanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCetak, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(165, 255, 214));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SISTEM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnLogout.setBackground(new java.awt.Color(255, 255, 255));
        btnLogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogout.setPreferredSize(new java.awt.Dimension(130, 50));
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogoutMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLogoutMousePressed(evt);
            }
        });

        lblKeluar.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/exit.png"))); // NOI18N
        lblKeluar.setText("Keluar");

        javax.swing.GroupLayout btnLogoutLayout = new javax.swing.GroupLayout(btnLogout);
        btnLogout.setLayout(btnLogoutLayout);
        btnLogoutLayout.setHorizontalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLogoutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnLogoutLayout.setVerticalGroup(
            btnLogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLogoutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
        );

        getContentPane().add(sidebar, java.awt.BorderLayout.LINE_START);

        pn_kanan.setBackground(new java.awt.Color(255, 255, 255));
        pn_kanan.setLayout(new java.awt.BorderLayout());

        mainContent.setBackground(new java.awt.Color(251, 248, 204));

        jPanel4.setBackground(new java.awt.Color(255, 168, 182));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(2, 1, 2, 1, new java.awt.Color(0, 0, 0)), "TABEL GEJALA KLINIS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        btnProses.setBackground(new java.awt.Color(58, 176, 250));
        btnProses.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        btnProses.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/verify.png"))); // NOI18N
        btnProses.setText("PROSES DIAGNOSA");
        btnProses.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProsesActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(233, 255, 112));
        btnReset.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/refresh.png"))); // NOI18N
        btnReset.setText("RESET");
        btnReset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(255, 105, 235));
        btnBatal.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/banned.png"))); // NOI18N
        btnBatal.setText("BATAL");
        btnBatal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        tblDiagnosa.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblDiagnosa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "Kode Gejala", "Gejala yang Dialami Ikan", "Pilih Kondisi"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblDiagnosa);
        if (tblDiagnosa.getColumnModel().getColumnCount() > 0) {
            tblDiagnosa.getColumnModel().getColumn(0).setHeaderValue("No");
            tblDiagnosa.getColumnModel().getColumn(1).setHeaderValue("Kode Gejala");
            tblDiagnosa.getColumnModel().getColumn(2).setHeaderValue("Gejala yang Dialami Ikan");
            tblDiagnosa.getColumnModel().getColumn(3).setHeaderValue("Pilih Kondisi");
        }

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnProses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane4)
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnProses)
                        .addComponent(btnReset))
                    .addComponent(btnBatal))
                .addContainerGap())
        );

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel3.setText("Nama Pembudidaya");

        txtNama.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel4.setText("Tanggal Diagnosa");

        txtTanggal.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(207, 186, 240));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HASIL OUTPUT", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 3, 18))); // NOI18N
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtHasilPenyakit.setEditable(false);
        txtHasilPenyakit.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        txtHasilPenyakit.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHasilPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHasilPenyakitActionPerformed(evt);
            }
        });

        txtAkurasi.setEditable(false);
        txtAkurasi.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        txtAkurasi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAkurasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAkurasiActionPerformed(evt);
            }
        });

        txtPencegahanHasil.setEditable(false);
        txtPencegahanHasil.setColumns(20);
        txtPencegahanHasil.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        txtPencegahanHasil.setLineWrap(true);
        txtPencegahanHasil.setRows(5);
        txtPencegahanHasil.setToolTipText("");
        txtPencegahanHasil.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtPencegahanHasil);

        txtSolusi.setEditable(false);
        txtSolusi.setColumns(20);
        txtSolusi.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        txtSolusi.setLineWrap(true);
        txtSolusi.setRows(5);
        txtSolusi.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txtSolusi);
        txtSolusi.getAccessibleContext().setAccessibleName("");

        btnSimpanRiwayat.setBackground(new java.awt.Color(191, 255, 64));
        btnSimpanRiwayat.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        btnSimpanRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/diskette.png"))); // NOI18N
        btnSimpanRiwayat.setText("SIMPAN KE RIWAYAT");
        btnSimpanRiwayat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSimpanRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanRiwayatActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel5.setText("Nama Penyakit");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel6.setText("Solusi");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel7.setText("Langkah Pencegahan");

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel8.setText("Nilai Persentase Certainty Factor");

        pbAkurasi.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        pbAkurasi.setForeground(new java.awt.Color(102, 255, 255));
        pbAkurasi.setStringPainted(true);

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jLabel9.setText("Kemungkinan Penyakit Lainnya");

        tblPenyakitLain.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblPenyakitLain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Penyakit Lain", "Tingkat Kecocokan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblPenyakitLain);

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        jLabel10.setText("Deskripsi Penyakit");

        txtDeskripsi.setEditable(false);
        txtDeskripsi.setColumns(20);
        txtDeskripsi.setFont(new java.awt.Font("Monospaced", 0, 16)); // NOI18N
        txtDeskripsi.setLineWrap(true);
        txtDeskripsi.setRows(5);
        txtDeskripsi.setToolTipText("");
        txtDeskripsi.setWrapStyleWord(true);
        jScrollPane5.setViewportView(txtDeskripsi);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pbAkurasi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSimpanRiwayat, javax.swing.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3)))
                    .addComponent(jLabel9)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHasilPenyakit, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAkurasi, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jScrollPane2))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtHasilPenyakit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtAkurasi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(2, 2, 2)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pbAkurasi, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSimpanRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout mainContentLayout = new javax.swing.GroupLayout(mainContent);
        mainContent.setLayout(mainContentLayout);
        mainContentLayout.setHorizontalGroup(
            mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainContentLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(mainContentLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)))
                .addGap(20, 20, 20))
        );
        mainContentLayout.setVerticalGroup(
            mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pn_kanan.add(mainContent, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(81, 226, 245));
        jPanel1.setPreferredSize(new java.awt.Dimension(1166, 80));

        lblJam.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblJam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/calendar.png"))); // NOI18N

        lblAdmin.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("FORM DIAGNOSA PENYAKIT IKAN NILA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblJam)
                .addGap(18, 18, 18)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 1139, Short.MAX_VALUE)
                .addGap(91, 91, 91)
                .addComponent(lblAdmin)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAdmin)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(lblJam, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pn_kanan.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(pn_kanan, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        if (koneksi.Session.namaAdmin == null || koneksi.Session.namaAdmin.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Anda harus Login terlebih dahulu.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose(); 
            return; 
        }
        
        txtNama.setText("");
        tampilGejala(); 
        txtHasilPenyakit.setText("");
        txtAkurasi.setText("");
        txtDeskripsi.setText("");
        txtPencegahanHasil.setText("");
        txtSolusi.setText("");
        txtNama.requestFocus();
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtHasilPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHasilPenyakitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHasilPenyakitActionPerformed

    private void txtAkurasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAkurasiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAkurasiActionPerformed

    private void btnProsesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProsesActionPerformed
        if (koneksi.Session.namaAdmin == null || koneksi.Session.namaAdmin.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Anda harus Login terlebih dahulu.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose(); 
            return; 
        }
        
        if (txtNama.getText().trim().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Nama Pembudidaya wajib diisi sebelum melakukan diagnosa!", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            txtNama.requestFocus();
            return;
        }
        
        java.util.HashMap<String, Double> inputUser = new java.util.HashMap<>();
        
        for (int i = 0; i < tblDiagnosa.getRowCount(); i++) {
            String kodeGejala = tblDiagnosa.getValueAt(i, 1).toString();
            String kondisiPilihan = tblDiagnosa.getValueAt(i, 3).toString();
            
            double cfUser = konversiCFUser(kondisiPilihan);
            if (cfUser > 0) {
                inputUser.put(kodeGejala, cfUser);
            }
        }
        
        if (inputUser.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Silakan pilih minimal 1 gejala (Kondisi) yang dialami ikan!", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.util.HashMap<String, Double> cfGabunganPenyakit = new java.util.HashMap<>();

        try {
            java.sql.Connection con = koneksi.KoneksiDB.getKoneksi();
            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rsAturan = st.executeQuery("SELECT * FROM tbl_aturan");
            
            while (rsAturan.next()) {
                String kodePenyakit = rsAturan.getString("kode_penyakit");
                String kodeGejalaAturan = rsAturan.getString("kode_gejala");
                double cfPakar = rsAturan.getDouble("cf_pakar");
                if (inputUser.containsKey(kodeGejalaAturan)) {
                    double cfUser = inputUser.get(kodeGejalaAturan);
                    double cfGejala = cfPakar * cfUser; 
                    if (cfGabunganPenyakit.containsKey(kodePenyakit)) {
                        double cfOld = cfGabunganPenyakit.get(kodePenyakit);
                        double cfCombine = cfOld + cfGejala * (1.0 - cfOld);
                        cfGabunganPenyakit.put(kodePenyakit, cfCombine); 
                    } else {
                        cfGabunganPenyakit.put(kodePenyakit, cfGejala);
                    }
                }
            }
            
            java.util.List<java.util.Map.Entry<String, Double>> listHasil = new java.util.ArrayList<>(cfGabunganPenyakit.entrySet());
            listHasil.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

            javax.swing.table.DefaultTableModel modelLain = (javax.swing.table.DefaultTableModel) tblPenyakitLain.getModel();
            modelLain.setRowCount(0);

            if (!listHasil.isEmpty() && listHasil.get(0).getValue() > 0) {
                
                String penyakitTerdeteksi = listHasil.get(0).getKey();
                double nilaiTertinggi = listHasil.get(0).getValue();

                String sqlHasil = "SELECT nama_penyakit, deskripsi, solusi, pencegahan FROM tbl_penyakit WHERE kode_penyakit = ?";
                java.sql.PreparedStatement pst = con.prepareStatement(sqlHasil);
                pst.setString(1, penyakitTerdeteksi);
                java.sql.ResultSet rsHasil = pst.executeQuery();

                if (rsHasil.next()) {
                    double persentase = nilaiTertinggi * 100;
                    
                    txtHasilPenyakit.setText(rsHasil.getString("nama_penyakit"));
                    txtAkurasi.setText(String.format("%.2f", persentase) + " %");
                    pbAkurasi.setValue((int) Math.round(persentase));
                    txtDeskripsi.setText(rsHasil.getString("deskripsi")); 
                    txtPencegahanHasil.setText(rsHasil.getString("pencegahan"));
                    txtSolusi.setText(rsHasil.getString("solusi"));
                    
                    for (int i = 1; i < listHasil.size(); i++) {
                        double cfLain = listHasil.get(i).getValue();
                        if (cfLain > 0 && i <= 3) { 
                            String kodeLain = listHasil.get(i).getKey();
                            java.sql.PreparedStatement pstLain = con.prepareStatement("SELECT nama_penyakit FROM tbl_penyakit WHERE kode_penyakit = ?");
                            pstLain.setString(1, kodeLain);
                            java.sql.ResultSet rsLain = pstLain.executeQuery();
                            
                            if (rsLain.next()) {
                                String namaLain = rsLain.getString("nama_penyakit");
                                double persenLain = cfLain * 100;
                                modelLain.addRow(new Object[]{
                                    namaLain,
                                    String.format("%.2f", persenLain) + " %"
                                });
                            }
                        }
                    }
                    javax.swing.JOptionPane.showMessageDialog(this, "Diagnosa Selesai! Penyakit berhasil diprediksi.", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Sistem tidak dapat mengidentifikasi penyakit dari gejala yang dipilih. Coba pilih gejala lain.", "Hasil Tidak Ditemukan", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                btnResetActionPerformed(null);
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error Proses Diagnosa: " + e.getMessage(), "Error Database", javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnProsesActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        if (koneksi.Session.namaAdmin == null || koneksi.Session.namaAdmin.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Anda harus Login terlebih dahulu.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose(); 
            return; 
        }
        
        view.main.MenuUtama menu = new view.main.MenuUtama();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnSimpanRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanRiwayatActionPerformed
        if (koneksi.Session.namaAdmin == null || koneksi.Session.namaAdmin.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Anda harus Login terlebih dahulu.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose(); 
            return; 
        }
        if (txtHasilPenyakit.getText().trim().isEmpty() || txtAkurasi.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Silakan klik tombol PROSES DIAGNOSA terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection con = KoneksiDB.getKoneksi();
            String namaPenyakit = txtHasilPenyakit.getText();
            String kodePenyakit = "";
            java.sql.PreparedStatement pstCari = con.prepareStatement("SELECT kode_penyakit FROM tbl_penyakit WHERE nama_penyakit = ?");
            pstCari.setString(1, namaPenyakit);
            java.sql.ResultSet rsPenyakit = pstCari.executeQuery();
            if (rsPenyakit.next()) {
                kodePenyakit = rsPenyakit.getString("kode_penyakit");
            }

            String strAkurasi = txtAkurasi.getText().replace(" %", "").replace(",", ".");
            float nilaiConfidence = Float.parseFloat(strAkurasi);
            String sqlDiagnosa = "INSERT INTO tbl_diagnosa (id_admin, nama_pembudidaya, tanggal_diagnosa, hasil_penyakit, confidence) VALUES (?, ?, ?, ?, ?)";
            java.sql.PreparedStatement pstDiag = con.prepareStatement(sqlDiagnosa, java.sql.Statement.RETURN_GENERATED_KEYS);
            java.sql.Timestamp waktuSekarang = new java.sql.Timestamp(System.currentTimeMillis());

            pstDiag.setInt(1, 1); 
            pstDiag.setString(2, txtNama.getText());
            pstDiag.setTimestamp(3, waktuSekarang);
            pstDiag.setString(4, kodePenyakit);
            pstDiag.setFloat(5, nilaiConfidence);
            pstDiag.executeUpdate();
            java.sql.ResultSet rsKey = pstDiag.getGeneratedKeys();
            int idDiagnosaBaru = 0;
            if (rsKey.next()) {
                idDiagnosaBaru = rsKey.getInt(1);
            }

            String sqlDetail = "INSERT INTO tbl_diagnosa_detail (id_diagnosa, kode_gejala) VALUES (?, ?)";
            java.sql.PreparedStatement pstDetail = con.prepareStatement(sqlDetail);
            for (int i = 0; i < tblDiagnosa.getRowCount(); i++) {
                String kondisiPilihan = tblDiagnosa.getValueAt(i, 3).toString();
                if (konversiCFUser(kondisiPilihan) > 0) {
                    String kodeGejala = tblDiagnosa.getValueAt(i, 1).toString();
                    
                    pstDetail.setInt(1, idDiagnosaBaru);
                    pstDetail.setString(2, kodeGejala);
                    pstDetail.executeUpdate();
                }
            }

            JOptionPane.showMessageDialog(this, "Mantap Bro! Riwayat diagnosa pembudidaya berhasil disimpan ke Riwayat!.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            FormRiwayat riwayat = new FormRiwayat(); 
            riwayat.setVisible(true);
            this.dispose();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Wah, gagal menyimpan riwayat: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSimpanRiwayatActionPerformed

    private void btnDashboardMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseEntered
        if (btnDashboard.getBackground().equals(colorNormal)) {
            btnDashboard.setBackground(colorHover);
        }
    }//GEN-LAST:event_btnDashboardMouseEntered

    private void btnDashboardMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMouseExited
        if (btnDashboard.getBackground().equals(colorHover)) {
            btnDashboard.setBackground(colorNormal);
        }
    }//GEN-LAST:event_btnDashboardMouseExited

    private void btnDashboardMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDashboardMousePressed
        switchWarna(btnDashboard);
        view.main.MenuUtama menu = new view.main.MenuUtama();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnDashboardMousePressed

    private void btnGejalaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGejalaMouseEntered
        if (btnGejala.getBackground().equals(colorNormal)) {
            btnGejala.setBackground(colorHover);
        }
    }//GEN-LAST:event_btnGejalaMouseEntered

    private void btnGejalaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGejalaMouseExited
        if (btnGejala.getBackground().equals(colorHover)) {
            btnGejala.setBackground(colorNormal);
        }
    }//GEN-LAST:event_btnGejalaMouseExited

    private void btnGejalaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGejalaMousePressed
        switchWarna(btnGejala);
        FormGejala formG = new FormGejala();
        formG.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnGejalaMousePressed

    private void btnPenyakitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPenyakitMouseEntered
        if (btnPenyakit.getBackground().equals(colorNormal)) {
            btnPenyakit.setBackground(colorHover);
        }
    }//GEN-LAST:event_btnPenyakitMouseEntered

    private void btnPenyakitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPenyakitMouseExited
        if (btnPenyakit.getBackground().equals(colorHover)) {
            btnPenyakit.setBackground(colorNormal);
        }
    }//GEN-LAST:event_btnPenyakitMouseExited

    private void btnPenyakitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPenyakitMousePressed
        switchWarna(btnPenyakit);
        FormPenyakit formP = new FormPenyakit();
        formP.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnPenyakitMousePressed

    private void btnAturanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAturanMouseEntered
        if (btnAturan.getBackground().equals(colorNormal)) {
            btnAturan.setBackground(colorHover);
        }
    }//GEN-LAST:event_btnAturanMouseEntered

    private void btnAturanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAturanMouseExited
        if (btnAturan.getBackground().equals(colorHover)) {
            btnAturan.setBackground(colorNormal);
        }
    }//GEN-LAST:event_btnAturanMouseExited

    private void btnAturanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAturanMousePressed
        switchWarna(btnAturan);
        FormAturan formA = new FormAturan();
        formA.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnAturanMousePressed

    private void btnDiagnosaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDiagnosaMouseEntered
        if (btnDiagnosa.getBackground().equals(colorNormal)) {
            btnDiagnosa.setBackground(colorHover);
        }
    }//GEN-LAST:event_btnDiagnosaMouseEntered

    private void btnDiagnosaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDiagnosaMouseExited
        if (btnDiagnosa.getBackground().equals(colorHover)) {
            btnDiagnosa.setBackground(colorNormal);
        }
    }//GEN-LAST:event_btnDiagnosaMouseExited

    private void btnDiagnosaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDiagnosaMousePressed
        switchWarna(btnDiagnosa);
    }//GEN-LAST:event_btnDiagnosaMousePressed

    private void btnRiwayatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRiwayatMouseEntered
        if (btnRiwayat.getBackground().equals(colorNormal)) {
            btnRiwayat.setBackground(colorHover);
        }
    }//GEN-LAST:event_btnRiwayatMouseEntered

    private void btnRiwayatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRiwayatMouseExited
        if (btnRiwayat.getBackground().equals(colorHover)) {
            btnRiwayat.setBackground(colorNormal);
        }
    }//GEN-LAST:event_btnRiwayatMouseExited

    private void btnRiwayatMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRiwayatMousePressed
        switchWarna(btnRiwayat);
        FormRiwayat formR = new FormRiwayat();
        formR.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRiwayatMousePressed

    private void btnLaporanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaporanMouseEntered
        if (btnLaporan.getBackground().equals(colorNormal)) {
            btnLaporan.setBackground(colorHover);
        }
    }//GEN-LAST:event_btnLaporanMouseEntered

    private void btnLaporanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaporanMouseExited
        if (btnLaporan.getBackground().equals(colorHover)) {
            btnLaporan.setBackground(colorNormal);
        }
    }//GEN-LAST:event_btnLaporanMouseExited

    private void btnLaporanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLaporanMousePressed
        switchWarna(btnLaporan);
        FormLaporan formL = new FormLaporan();
        formL.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLaporanMousePressed

    private void btnLogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseEntered
        btnLogout.setBackground(new java.awt.Color(231, 76, 60));
    }//GEN-LAST:event_btnLogoutMouseEntered

    private void btnLogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMouseExited
        btnLogout.setBackground(colorNormal);
    }//GEN-LAST:event_btnLogoutMouseExited

    private void btnLogoutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogoutMousePressed
        btnLogout.setBackground(new java.awt.Color(192, 57, 43)); // Merah gelap saat diklik

        int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin keluar dari aplikasi?", "Konfirmasi Logout",
            javax.swing.JOptionPane.YES_NO_OPTION);

        if (konfirmasi == javax.swing.JOptionPane.YES_OPTION) {
            
            koneksi.Session.namaAdmin = null;
            koneksi.Session.role = null;

            for (java.awt.Window window : java.awt.Window.getWindows()) {
                window.dispose();
            }

            view.main.FormLogin login = new view.main.FormLogin();
            login.setVisible(true);
            
        } else {
            btnLogout.setBackground(colorNormal);
        }
    }//GEN-LAST:event_btnLogoutMousePressed
    class MultiLineCellRenderer extends javax.swing.JTextArea implements javax.swing.table.TableCellRenderer {
        public MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
            setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 5, 8, 5)); 
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            setFont(table.getFont()); // Font nyatu sama tabel

            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
            }
            return this;
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormDiagnosa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormDiagnosa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormDiagnosa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormDiagnosa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormDiagnosa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAturan;
    private javax.swing.JButton btnBatal;
    private javax.swing.JPanel btnDashboard;
    private javax.swing.JPanel btnDiagnosa;
    private javax.swing.JPanel btnGejala;
    private javax.swing.JPanel btnLaporan;
    private javax.swing.JPanel btnLogout;
    private javax.swing.JPanel btnPenyakit;
    private javax.swing.JButton btnProses;
    private javax.swing.JButton btnReset;
    private javax.swing.JPanel btnRiwayat;
    private javax.swing.JButton btnSimpanRiwayat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblAdmin;
    private javax.swing.JLabel lblAturan;
    private javax.swing.JLabel lblCetak;
    private javax.swing.JLabel lblDashboard;
    private javax.swing.JLabel lblDiagnosa;
    private javax.swing.JLabel lblGejala;
    private javax.swing.JLabel lblJam;
    private javax.swing.JLabel lblKeluar;
    private javax.swing.JLabel lblPenyakit;
    private javax.swing.JLabel lblRiwayat;
    private javax.swing.JPanel mainContent;
    private javax.swing.JProgressBar pbAkurasi;
    private javax.swing.JPanel pn_kanan;
    private javax.swing.JPanel sidebar;
    private javax.swing.JTable tblDiagnosa;
    private javax.swing.JTable tblPenyakitLain;
    private javax.swing.JTextField txtAkurasi;
    private javax.swing.JTextArea txtDeskripsi;
    private javax.swing.JTextField txtHasilPenyakit;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextArea txtPencegahanHasil;
    private javax.swing.JTextArea txtSolusi;
    private javax.swing.JTextField txtTanggal;
    // End of variables declaration//GEN-END:variables
}
