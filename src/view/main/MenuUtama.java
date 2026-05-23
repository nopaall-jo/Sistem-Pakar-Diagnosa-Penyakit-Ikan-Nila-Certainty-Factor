/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.main;
import view.main.FormLogin;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JPanel;
import koneksi.KoneksiDB;
import view.data.FormAturan;
import view.data.FormDiagnosa;
import view.data.FormGejala;
import view.data.FormHasilDiagnosa;
import view.data.FormLaporan;
import view.data.FormPenyakit;
import view.data.FormRiwayat;
import view.dialog.DialogAturan;
import view.dialog.DialogGejala;
import view.dialog.DialogPenyakit;

/**
 *
 * @author NAUFAL
 */

public class MenuUtama extends javax.swing.JFrame {
    Color colorNormal = new Color(255, 243, 236);
    Color colorHover = new Color(255, 220, 230);
    Color colorActive = new Color(173, 216, 255);
    CardLayout cl; 
    
    /**
     * Creates new form MenuUutama
     */
    
    public MenuUtama() {
        initComponents();
        
        if (koneksi.Session.namaAdmin == null || koneksi.Session.namaAdmin.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Hayo, Anda harus Login terlebih dahulu.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose();
            return;
        }

        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null); 
        setJamRealTime();
        hitungDataDashboard();
        tampilRiwayatTerakhir();
        tampilRiwayatDashboard();
        aturFormatTabel();

        for (java.awt.Component comp : sidebar.getComponents()) {
            if (comp instanceof javax.swing.JPanel) {
                comp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }
        }
        
        switchWarna(btnDashboard); 
        String nama = koneksi.Session.namaAdmin;
        String role = koneksi.Session.role;
        lblAdmin.setText(role + " | " + nama);
    }
    
    private void aturFormatTabel() {
        tblRiwayatDashboard.setRowHeight(45); 
        tblRiwayatDashboard.setShowGrid(true); 
        tblRiwayatDashboard.setShowVerticalLines(true);   
        tblRiwayatDashboard.setShowHorizontalLines(true); 
        tblRiwayatDashboard.setGridColor(new java.awt.Color(153, 153, 153));
        tblRiwayatDashboard.setIntercellSpacing(new java.awt.Dimension(1, 1)); 
        
        if (tblRiwayatDashboard.getColumnCount() == 4) {
            javax.swing.table.TableColumnModel cm = tblRiwayatDashboard.getColumnModel();
            
            cm.getColumn(0).setPreferredWidth(45);
            cm.getColumn(1).setPreferredWidth(140);
            cm.getColumn(2).setPreferredWidth(180);
            cm.getColumn(3).setPreferredWidth(280);
            cm.getColumn(2).setCellRenderer(new MultiLineCellRenderer()); 
            cm.getColumn(3).setCellRenderer(new MultiLineCellRenderer()); 
            
            javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            cm.getColumn(0).setCellRenderer(centerRenderer);
            cm.getColumn(1).setCellRenderer(centerRenderer);
        }
    }
    
    public void setNamaAdmin(String nama) {
        lblAdmin.setText(nama);
    }
    
    private void setJamRealTime() {
        javax.swing.Timer timer = new javax.swing.Timer(1000, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                java.util.Date tanggal = new java.util.Date();
                java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("dd MMMM yyyy HH:mm:ss");
                lblJam.setText(format.format(tanggal));
            }
        });
        timer.start();
    }
    
    private void hitungDataDashboard() {
        try {
            Connection con = KoneksiDB.getKoneksi();
            Statement st = con.createStatement();
            ResultSet rs;
            rs = st.executeQuery("SELECT COUNT(*) AS total FROM tbl_penyakit");
            if(rs.next()) {
                lblTotalPenyakit.setText(rs.getString("total"));
            }
            rs = st.executeQuery("SELECT COUNT(*) AS total FROM tbl_gejala");
            if(rs.next()) {
                lblTotalGejala.setText(rs.getString("total"));
            }
            rs = st.executeQuery("SELECT COUNT(*) AS total FROM tbl_aturan");
            if(rs.next()) {
                lblTotalAturan.setText(rs.getString("total"));
            }
            rs = st.executeQuery("SELECT COUNT(*) AS total FROM tbl_diagnosa");
            if(rs.next()) {
                lblTotalRiwayat.setText(rs.getString("total"));
            }
            
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal memuat data dashboard: " + e.getMessage());
        }
    }
    
    private void tampilRiwayatTerakhir() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblRiwayatDashboard.getModel();
        model.setRowCount(0);
        
        try {
            java.sql.Connection con = koneksi.KoneksiDB.getKoneksi();
            java.sql.Statement st = con.createStatement();
            String sql = "SELECT d.tanggal_diagnosa, d.nama_pembudidaya, p.nama_penyakit " +
                         "FROM tbl_diagnosa d " +
                         "LEFT JOIN tbl_penyakit p ON d.hasil_penyakit = p.kode_penyakit " +
                         "ORDER BY d.tanggal_diagnosa DESC LIMIT 5";
                         
            java.sql.ResultSet rs = st.executeQuery(sql);
            
            int no = 1;
            while(rs.next()) {
                String tanggal = rs.getString("tanggal_diagnosa");
                String nama = rs.getString("nama_pembudidaya");
                String hasil = rs.getString("nama_penyakit");
                if(hasil == null) { hasil = "Tidak Dikenali"; }
                model.addRow(new Object[]{no++, tanggal, nama, hasil});
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Gagal memuat tabel riwayat: " + e.getMessage());
        }
    }
    
    private void tampilRiwayatDashboard() {
     javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblRiwayatDashboard.getModel();
     model.setRowCount(0);

     try {
         java.sql.Connection con = koneksi.KoneksiDB.getKoneksi();
         java.sql.Statement st = con.createStatement();
         String sql = "SELECT d.tanggal_diagnosa, p.nama_penyakit, d.nama_pembudidaya " +
                      "FROM tbl_diagnosa d " +
                      "JOIN tbl_penyakit p ON d.hasil_penyakit = p.kode_penyakit " +
                      "ORDER BY d.id_diagnosa DESC LIMIT 5";

         java.sql.ResultSet rs = st.executeQuery(sql);
         int no = 1;
         while(rs.next()) {
             model.addRow(new Object[]{
                 no++,
                 rs.getString("tanggal_diagnosa"),
                 rs.getString("nama_penyakit"),
                 rs.getString("nama_pembudidaya")
             });
         } 
    } catch (Exception e) {
         System.out.println("Error dashboard riwayat: " + e.getMessage());
     }
}
    
    private void switchWarna(JPanel activePanel) {
        btnDashboard.setBackground(colorNormal);
        btnPenyakit.setBackground(colorNormal);
        btnGejala.setBackground(colorNormal);
        btnAturan.setBackground(colorNormal);
        btnDiagnosa.setBackground(colorNormal);
        btnRiwayat.setBackground(colorNormal);
        btnLaporan.setBackground(colorNormal);
        activePanel.setBackground(colorActive);
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
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
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
        jPanel1 = new javax.swing.JPanel();
        lblJam = new javax.swing.JLabel();
        lblAdmin = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pn_dasar = new javax.swing.JPanel();
        mainContent = new javax.swing.JPanel();
        pn_penyakit = new javax.swing.JPanel();
        lblTotalPenyakit = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        pn_gejala = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblTotalGejala = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        pn_aturan = new javax.swing.JPanel();
        lblTotalAturan = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        pn_riwayat = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblTotalRiwayat = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnLihatRiwayat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRiwayatDashboard = new javax.swing.JTable();
        btnInfo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sidebar.setBackground(new java.awt.Color(165, 255, 214));
        sidebar.setMinimumSize(new java.awt.Dimension(200, 100));
        sidebar.setPreferredSize(new java.awt.Dimension(250, 768));

        jPanel2.setBackground(new java.awt.Color(81, 226, 245));
        jPanel2.setPreferredSize(new java.awt.Dimension(250, 130));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/farmer.png"))); // NOI18N

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/fish.png"))); // NOI18N

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
                .addContainerGap(86, Short.MAX_VALUE))
        );

        getContentPane().add(sidebar, java.awt.BorderLayout.LINE_START);

        pn_kanan.setBackground(new java.awt.Color(255, 255, 255));
        pn_kanan.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(81, 226, 245));
        jPanel1.setPreferredSize(new java.awt.Dimension(1166, 80));

        lblJam.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblJam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/calendar.png"))); // NOI18N

        lblAdmin.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblAdmin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("DASHBOARD");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblJam)
                .addGap(18, 18, 18)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 1061, Short.MAX_VALUE)
                .addGap(54, 54, 54)
                .addComponent(lblAdmin)
                .addGap(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblJam, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(lblAdmin))
                .addGap(32, 32, 32))
        );

        pn_kanan.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pn_dasar.setBackground(new java.awt.Color(254, 217, 183));

        mainContent.setBackground(new java.awt.Color(255, 168, 182));

        pn_penyakit.setBackground(new java.awt.Color(251, 248, 204));
        pn_penyakit.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(0, 255, 0)));
        pn_penyakit.setPreferredSize(new java.awt.Dimension(220, 80));

        lblTotalPenyakit.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        lblTotalPenyakit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalPenyakit.setText("0");
        lblTotalPenyakit.setMaximumSize(new java.awt.Dimension(25, 60));
        lblTotalPenyakit.setMinimumSize(new java.awt.Dimension(25, 60));
        lblTotalPenyakit.setPreferredSize(new java.awt.Dimension(25, 60));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/shoal.png"))); // NOI18N
        jLabel16.setRequestFocusEnabled(false);
        jLabel16.setVerifyInputWhenFocusTarget(false);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("DATA PENYAKIT");
        jLabel9.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabel9.setMinimumSize(new java.awt.Dimension(100, 20));
        jLabel9.setPreferredSize(new java.awt.Dimension(100, 20));

        javax.swing.GroupLayout pn_penyakitLayout = new javax.swing.GroupLayout(pn_penyakit);
        pn_penyakit.setLayout(pn_penyakitLayout);
        pn_penyakitLayout.setHorizontalGroup(
            pn_penyakitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_penyakitLayout.createSequentialGroup()
                .addGroup(pn_penyakitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                    .addComponent(lblTotalPenyakit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pn_penyakitLayout.setVerticalGroup(
            pn_penyakitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_penyakitLayout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotalPenyakit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pn_penyakitLayout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pn_gejala.setBackground(new java.awt.Color(251, 248, 204));
        pn_gejala.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(204, 0, 51)));
        pn_gejala.setPreferredSize(new java.awt.Dimension(220, 80));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("DATA GEJALA");
        jLabel7.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabel7.setMinimumSize(new java.awt.Dimension(100, 20));
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 20));

        lblTotalGejala.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        lblTotalGejala.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalGejala.setText("0");
        lblTotalGejala.setMaximumSize(new java.awt.Dimension(25, 60));
        lblTotalGejala.setMinimumSize(new java.awt.Dimension(25, 60));
        lblTotalGejala.setPreferredSize(new java.awt.Dimension(25, 60));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/syphilis.png"))); // NOI18N
        jLabel17.setRequestFocusEnabled(false);
        jLabel17.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout pn_gejalaLayout = new javax.swing.GroupLayout(pn_gejala);
        pn_gejala.setLayout(pn_gejalaLayout);
        pn_gejalaLayout.setHorizontalGroup(
            pn_gejalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_gejalaLayout.createSequentialGroup()
                .addGroup(pn_gejalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(lblTotalGejala, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );
        pn_gejalaLayout.setVerticalGroup(
            pn_gejalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_gejalaLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(pn_gejalaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pn_gejalaLayout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(lblTotalGejala, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pn_aturan.setBackground(new java.awt.Color(251, 248, 204));
        pn_aturan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(0, 51, 255)));
        pn_aturan.setPreferredSize(new java.awt.Dimension(220, 80));

        lblTotalAturan.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        lblTotalAturan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalAturan.setText("0");
        lblTotalAturan.setMaximumSize(new java.awt.Dimension(25, 60));
        lblTotalAturan.setMinimumSize(new java.awt.Dimension(25, 60));
        lblTotalAturan.setPreferredSize(new java.awt.Dimension(25, 60));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/decision.png"))); // NOI18N
        jLabel18.setRequestFocusEnabled(false);
        jLabel18.setVerifyInputWhenFocusTarget(false);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("BASIS ATURAN");
        jLabel15.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabel15.setMinimumSize(new java.awt.Dimension(100, 20));
        jLabel15.setPreferredSize(new java.awt.Dimension(100, 20));

        javax.swing.GroupLayout pn_aturanLayout = new javax.swing.GroupLayout(pn_aturan);
        pn_aturan.setLayout(pn_aturanLayout);
        pn_aturanLayout.setHorizontalGroup(
            pn_aturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_aturanLayout.createSequentialGroup()
                .addGroup(pn_aturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(lblTotalAturan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pn_aturanLayout.setVerticalGroup(
            pn_aturanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_aturanLayout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTotalAturan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pn_aturanLayout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pn_riwayat.setBackground(new java.awt.Color(251, 248, 204));
        pn_riwayat.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(255, 0, 255)));
        pn_riwayat.setPreferredSize(new java.awt.Dimension(220, 80));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("RIWAYAT DIAGNOSA PENYAKIT IKAN NILA");
        jLabel10.setMaximumSize(new java.awt.Dimension(100, 20));
        jLabel10.setMinimumSize(new java.awt.Dimension(100, 20));
        jLabel10.setPreferredSize(new java.awt.Dimension(100, 20));

        lblTotalRiwayat.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        lblTotalRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalRiwayat.setText("0");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/harvest.png"))); // NOI18N
        jLabel4.setRequestFocusEnabled(false);
        jLabel4.setVerifyInputWhenFocusTarget(false);

        javax.swing.GroupLayout pn_riwayatLayout = new javax.swing.GroupLayout(pn_riwayat);
        pn_riwayat.setLayout(pn_riwayatLayout);
        pn_riwayatLayout.setHorizontalGroup(
            pn_riwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_riwayatLayout.createSequentialGroup()
                .addGroup(pn_riwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addComponent(lblTotalRiwayat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );
        pn_riwayatLayout.setVerticalGroup(
            pn_riwayatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_riwayatLayout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotalRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnLihatRiwayat.setBackground(new java.awt.Color(255, 234, 0));
        btnLihatRiwayat.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        btnLihatRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/inspection.png"))); // NOI18N
        btnLihatRiwayat.setText("Lihat Semua Hasil Riwayat");
        btnLihatRiwayat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLihatRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLihatRiwayatActionPerformed(evt);
            }
        });

        tblRiwayatDashboard.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        tblRiwayatDashboard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "No", "Tanggal", "Nama Pembudidaya", "Penyakit Terdeteksi"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblRiwayatDashboard.setRowHeight(40);
        tblRiwayatDashboard.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tblRiwayatDashboard.setShowHorizontalLines(false);
        tblRiwayatDashboard.setShowVerticalLines(false);
        tblRiwayatDashboard.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tblRiwayatDashboardKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblRiwayatDashboard);

        btnInfo.setBackground(new java.awt.Color(255, 153, 255));
        btnInfo.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        btnInfo.setText("Bantuan & Info!");
        btnInfo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainContentLayout = new javax.swing.GroupLayout(mainContent);
        mainContent.setLayout(mainContentLayout);
        mainContentLayout.setHorizontalGroup(
            mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(mainContentLayout.createSequentialGroup()
                        .addComponent(btnInfo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLihatRiwayat))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainContentLayout.createSequentialGroup()
                        .addComponent(pn_penyakit, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pn_gejala, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pn_aturan, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(pn_riwayat, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        mainContentLayout.setVerticalGroup(
            mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContentLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pn_riwayat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pn_aturan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pn_penyakit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pn_gejala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLihatRiwayat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInfo))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pn_dasarLayout = new javax.swing.GroupLayout(pn_dasar);
        pn_dasar.setLayout(pn_dasarLayout);
        pn_dasarLayout.setHorizontalGroup(
            pn_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dasarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(mainContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        pn_dasarLayout.setVerticalGroup(
            pn_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dasarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(mainContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );

        pn_kanan.add(pn_dasar, java.awt.BorderLayout.CENTER);

        getContentPane().add(pn_kanan, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLihatRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLihatRiwayatActionPerformed
        FormRiwayat riwayat = new FormRiwayat();
        riwayat.setVisible(true);
    }//GEN-LAST:event_btnLihatRiwayatActionPerformed

    private void tblRiwayatDashboardKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblRiwayatDashboardKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tblRiwayatDashboardKeyReleased

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
        FormDiagnosa formD = new FormDiagnosa();
        formD.setVisible(true);
        this.dispose();
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
            FormLogin login = new FormLogin();
            login.setVisible(true);
            
        } else {
            btnLogout.setBackground(colorNormal);
        }
    }//GEN-LAST:event_btnLogoutMousePressed

    private void btnInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInfoActionPerformed
        view.dialog.DialogInformasi info = new view.dialog.DialogInformasi(this, true);
        info.setLocationRelativeTo(this);
        info.setVisible(true);
    }//GEN-LAST:event_btnInfoActionPerformed

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

            // BIKIN FONT OTOMATIS NGUKUTIN BAWAAN TABEL (Biar gak belang)
            setFont(table.getFont());

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
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAturan;
    private javax.swing.JPanel btnDashboard;
    private javax.swing.JPanel btnDiagnosa;
    private javax.swing.JPanel btnGejala;
    private javax.swing.JButton btnInfo;
    private javax.swing.JPanel btnLaporan;
    private javax.swing.JButton btnLihatRiwayat;
    private javax.swing.JPanel btnLogout;
    private javax.swing.JPanel btnPenyakit;
    private javax.swing.JPanel btnRiwayat;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JLabel lblTotalAturan;
    private javax.swing.JLabel lblTotalGejala;
    private javax.swing.JLabel lblTotalPenyakit;
    private javax.swing.JLabel lblTotalRiwayat;
    private javax.swing.JPanel mainContent;
    private javax.swing.JPanel pn_aturan;
    private javax.swing.JPanel pn_dasar;
    private javax.swing.JPanel pn_gejala;
    private javax.swing.JPanel pn_kanan;
    private javax.swing.JPanel pn_penyakit;
    private javax.swing.JPanel pn_riwayat;
    private javax.swing.JPanel sidebar;
    private javax.swing.JTable tblRiwayatDashboard;
    // End of variables declaration//GEN-END:variables
}
