/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.data;
import view.data.FormGejala;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import koneksi.KoneksiDB;

/**
 *
 * @author NAUFAL
 */

public class FormAturan extends javax.swing.JFrame {
    Color colorNormal = new Color(255, 243, 236);
    Color colorHover = new Color(255, 220, 230);
    Color colorActive = new Color(173, 216, 255);
    
    /**
     * Creates new form FormAturanA
     */
    
    public FormAturan() {
        initComponents();
        try {
            java.awt.Image icon = javax.imageio.ImageIO.read(getClass().getResource("/icon/logo2.png"));
            setIconImage(icon);
        } catch (Exception e) {
            System.out.println("Gagal load icon: " + e.getMessage());
        }
        
        aturFormatTabel();
        
        if (koneksi.Session.namaAdmin == null || koneksi.Session.namaAdmin.equals("")) {
            javax.swing.JOptionPane.showMessageDialog(this,"Akses Ditolak! Hayo, Anda harus Login terlebih dahulu.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose(); 
            return; 
        }
        
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);        
        setJamRealTime();
        
        for (Component comp : sidebar.getComponents()) {
            if (comp instanceof JPanel) {
                comp.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }
        switchWarna(btnAturan); 
        tampilData("");
        String nama = koneksi.Session.namaAdmin;
        String role = koneksi.Session.role;
        lblAdmin.setText(role + " | " + nama);
    }
    
    private void aturFormatTabel() {
        tblAturan.setRowHeight(70); 
        tblAturan.setShowGrid(true); 
        tblAturan.setShowVerticalLines(true);   
        tblAturan.setShowHorizontalLines(true); 
        tblAturan.setGridColor(new java.awt.Color(153, 153, 153)); 
        tblAturan.setIntercellSpacing(new java.awt.Dimension(1, 1)); 

        if (tblAturan.getColumnCount() == 6) {
            javax.swing.table.TableColumnModel cm = tblAturan.getColumnModel();
            
            cm.getColumn(0).setPreferredWidth(40); 
            cm.getColumn(1).setPreferredWidth(80); 
            cm.getColumn(2).setPreferredWidth(200);
            cm.getColumn(3).setPreferredWidth(300);
            cm.getColumn(4).setPreferredWidth(80);
            cm.getColumn(5).setPreferredWidth(80);
            
            cm.getColumn(2).setCellRenderer(new MultiLineCellRenderer()); 
            cm.getColumn(3).setCellRenderer(new MultiLineCellRenderer()); 

            javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            cm.getColumn(0).setCellRenderer(centerRenderer);
            cm.getColumn(1).setCellRenderer(centerRenderer);
            cm.getColumn(4).setCellRenderer(centerRenderer);
            cm.getColumn(5).setCellRenderer(centerRenderer);
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
        btnDataAdmin.setBackground(colorNormal);
        activePanel.setBackground(colorActive);
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
    
    private void tampilData(String keyword) {
       DefaultTableModel model = (DefaultTableModel) tblAturan.getModel();
        model.setRowCount(0);
        
        try {
            Connection con = KoneksiDB.getKoneksi();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM tbl_aturan ";
            if (!keyword.isEmpty()) {
                sql += "WHERE id_aturan LIKE '%" + keyword + "%' "
                     + "OR kode_penyakit LIKE '%" + keyword + "%' "
                     + "OR kode_gejala LIKE '%" + keyword + "%' ";
            }
            sql += "ORDER BY id_aturan ASC";
            
            ResultSet rs = st.executeQuery(sql);
            int no = 1;
            while(rs.next()) {
                model.addRow(new Object[]{
                    no++,
                    rs.getString("id_aturan"),   
                    rs.getString("kode_penyakit"), 
                    rs.getString("kode_gejala"),   
                    rs.getString("nilai_mb"),
                    rs.getString("nilai_md")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error tampil data Aturan: " + e.getMessage());
        }
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
        jLabel19 = new javax.swing.JLabel();
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
        btnDataAdmin = new javax.swing.JPanel();
        lblKeluar3 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JPanel();
        lblKeluar = new javax.swing.JLabel();
        pn_kanan = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblJam = new javax.swing.JLabel();
        lblAdmin = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pn_dasar = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAturan = new javax.swing.JTable();
        btnCari = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        btnCetak = new javax.swing.JButton();
        btnTambah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sidebar.setBackground(new java.awt.Color(81, 226, 245));
        sidebar.setMinimumSize(new java.awt.Dimension(200, 100));
        sidebar.setPreferredSize(new java.awt.Dimension(250, 768));

        jPanel2.setBackground(new java.awt.Color(81, 226, 245));
        jPanel2.setPreferredSize(new java.awt.Dimension(250, 130));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/LogoDua.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel9.setBackground(new java.awt.Color(81, 226, 245));
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

        jPanel10.setBackground(new java.awt.Color(81, 226, 245));
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

        jPanel11.setBackground(new java.awt.Color(81, 226, 245));
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

        jPanel12.setBackground(new java.awt.Color(81, 226, 245));
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

        jPanel13.setBackground(new java.awt.Color(81, 226, 245));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "SISTEM", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        btnDataAdmin.setBackground(new java.awt.Color(255, 255, 255));
        btnDataAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDataAdmin.setPreferredSize(new java.awt.Dimension(130, 50));
        btnDataAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDataAdminMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDataAdminMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDataAdminMousePressed(evt);
            }
        });

        lblKeluar3.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblKeluar3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKeluar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/farmer.png"))); // NOI18N
        lblKeluar3.setText("ADMIN");

        javax.swing.GroupLayout btnDataAdminLayout = new javax.swing.GroupLayout(btnDataAdmin);
        btnDataAdmin.setLayout(btnDataAdminLayout);
        btnDataAdminLayout.setHorizontalGroup(
            btnDataAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDataAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblKeluar3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnDataAdminLayout.setVerticalGroup(
            btnDataAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblKeluar3, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

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
            .addComponent(lblKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDataAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(btnDataAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(sidebar, java.awt.BorderLayout.LINE_START);

        pn_kanan.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(165, 255, 214));
        jPanel1.setPreferredSize(new java.awt.Dimension(1166, 80));

        lblJam.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblJam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/calendar.png"))); // NOI18N

        lblAdmin.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N
        lblAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAdminMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("FORM DATA BASIS PENGETAHUAN ATURAN BARU");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblJam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
                .addGap(91, 91, 91)
                .addComponent(lblAdmin)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAdmin)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblJam, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)))
                .addGap(25, 25, 25))
        );

        pn_kanan.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pn_dasar.setBackground(new java.awt.Color(253, 252, 220));

        jPanel4.setBackground(new java.awt.Color(202, 240, 248));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(2, 1, 2, 1, new java.awt.Color(0, 0, 0)), "TABEL DATA ATURAN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        tblAturan.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tblAturan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "ID Aturan", "Data Penyakit", "Gejala Terkait", "Nilai MB", "Nilai MD"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAturan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAturanMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAturan);

        btnCari.setBackground(new java.awt.Color(22, 244, 208));
        btnCari.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loupe.png"))); // NOI18N
        btnCari.setText("CARI");
        btnCari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        txtCari.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        txtCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariActionPerformed(evt);
            }
        });
        txtCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCariKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariKeyReleased(evt);
            }
        });

        btnCetak.setBackground(new java.awt.Color(155, 246, 255));
        btnCetak.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/cetak.png"))); // NOI18N
        btnCetak.setText("CETAK");
        btnCetak.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        btnTambah.setBackground(new java.awt.Color(156, 255, 0));
        btnTambah.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/add.png"))); // NOI18N
        btnTambah.setText("TAMBAH DATA");
        btnTambah.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(255, 107, 53));
        btnHapus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/litter.png"))); // NOI18N
        btnHapus.setText("HAPUS");
        btnHapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnEdit.setBackground(new java.awt.Color(255, 234, 0));
        btnEdit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnEdit.setText("EDIT");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnCari)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(89, 89, 89)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(btnHapus, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
                            .addComponent(btnTambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("copyright © Skripsi Teknik Informatika | Naufal Rafif (202243501684)");

        javax.swing.GroupLayout pn_dasarLayout = new javax.swing.GroupLayout(pn_dasar);
        pn_dasar.setLayout(pn_dasarLayout);
        pn_dasarLayout.setHorizontalGroup(
            pn_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dasarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pn_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pn_dasarLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dasarLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        pn_dasarLayout.setVerticalGroup(
            pn_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pn_dasarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6))
        );

        pn_kanan.add(pn_dasar, java.awt.BorderLayout.CENTER);

        getContentPane().add(pn_kanan, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

            view.main.FormLogin login = new view.main.FormLogin();
            login.setVisible(true);
            
        } else {
            btnLogout.setBackground(colorNormal);
        }
    }//GEN-LAST:event_btnLogoutMousePressed

    private void tblAturanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAturanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblAturanMouseClicked

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        tampilData(txtCari.getText());
    }//GEN-LAST:event_btnCariActionPerformed

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        tampilData(txtCari.getText());
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariKeyPressed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        tampilData(txtCari.getText());
    }//GEN-LAST:event_txtCariKeyReleased

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        if (koneksi.Session.namaAdmin == null || koneksi.Session.namaAdmin.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Anda harus Login terlebih dahulu.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose(); 
            return; 
        }
        
        view.dialog.DialogAturan popUp = new view.dialog.DialogAturan(this, true);
        popUp.modeTambah();
        popUp.setVisible(true);
        tampilData("");
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if (koneksi.Session.namaAdmin == null || koneksi.Session.namaAdmin.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Anda harus Login terlebih dahulu.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose(); 
            return; 
        }
        
        int baris = tblAturan.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris data di tabel yang mau dihapus terlebih dahulu!");
            return;
        }

        String idYgDihapus = tblAturan.getValueAt(baris, 1).toString();

        int konfirmasi = JOptionPane.showConfirmDialog(this, "Yakin hapus data aturan " + idYgDihapus + "?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            try {
                Connection con = KoneksiDB.getKoneksi();
                PreparedStatement pst = con.prepareStatement("DELETE FROM tbl_aturan WHERE id_aturan=?");
                pst.setString(1, idYgDihapus);
                pst.execute();

                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                tampilData("");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Gagal hapus: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (koneksi.Session.namaAdmin == null || koneksi.Session.namaAdmin.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Anda harus Login terlebih dahulu.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose(); 
            return; 
        }
        
        int baris = tblAturan.getSelectedRow();
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris data di tabel yang mau diedit terlebih dahulu!");
            return;
        }

        String id_aturan = tblAturan.getValueAt(baris, 1).toString();
        String penyakit = tblAturan.getValueAt(baris, 2).toString();
        String gejala = tblAturan.getValueAt(baris, 3).toString();
        String nilai_mb = tblAturan.getValueAt(baris, 4).toString();
        String nilai_md = tblAturan.getValueAt(baris, 5).toString();

        view.dialog.DialogAturan popUp = new view.dialog.DialogAturan(this, true);
        popUp.modeEdit(id_aturan, penyakit, gejala, nilai_mb, nilai_md);
        popUp.setVisible(true);
        tampilData("");
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        if (koneksi.Session.namaAdmin == null || koneksi.Session.namaAdmin.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Anda harus Login terlebih dahulu.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose(); 
            return; 
        }
        try {
            String path = "src/report/laporanaturan.jasper"; 
            java.io.File file = new java.io.File(path);

            java.util.HashMap<String, Object> parameter = new java.util.HashMap<>();
            parameter.put("ADMIN", koneksi.Session.namaAdmin); 
            net.sf.jasperreports.engine.JasperPrint print = net.sf.jasperreports.engine.JasperFillManager.fillReport(
                file.getPath(), 
                parameter, 
                koneksi.KoneksiDB.getKoneksi()
            );
            net.sf.jasperreports.view.JasperViewer.viewReport(print, false);

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal mencetak Laporan Basis Aturan: " + e.getMessage());
        }
    }//GEN-LAST:event_btnCetakActionPerformed

    private void btnDataAdminMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDataAdminMouseEntered
        if (btnDataAdmin.getBackground().equals(colorNormal)) {
            btnDataAdmin.setBackground(colorHover);
        }
    }//GEN-LAST:event_btnDataAdminMouseEntered

    private void btnDataAdminMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDataAdminMouseExited
        if (btnDataAdmin.getBackground().equals(colorHover)) {
            btnDataAdmin.setBackground(colorNormal);
        }
    }//GEN-LAST:event_btnDataAdminMouseExited

    private void btnDataAdminMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDataAdminMousePressed
        switchWarna(btnDataAdmin);
        view.main.FormAdmin formA = new view.main.FormAdmin();
        formA.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnDataAdminMousePressed

    private void lblAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAdminMouseClicked
        view.main.FormAdmin formA = new view.main.FormAdmin();
        formA.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblAdminMouseClicked

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
            java.util.logging.Logger.getLogger(FormAturan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormAturan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormAturan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormAturan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormAturan().setVisible(true);
            }
        });
    }
    
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAturan;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCetak;
    private javax.swing.JPanel btnDashboard;
    private javax.swing.JPanel btnDataAdmin;
    private javax.swing.JPanel btnDiagnosa;
    private javax.swing.JButton btnEdit;
    private javax.swing.JPanel btnGejala;
    private javax.swing.JButton btnHapus;
    private javax.swing.JPanel btnLaporan;
    private javax.swing.JPanel btnLogout;
    private javax.swing.JPanel btnPenyakit;
    private javax.swing.JPanel btnRiwayat;
    private javax.swing.JButton btnTambah;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
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
    private javax.swing.JLabel lblKeluar3;
    private javax.swing.JLabel lblPenyakit;
    private javax.swing.JLabel lblRiwayat;
    private javax.swing.JPanel pn_dasar;
    private javax.swing.JPanel pn_kanan;
    private javax.swing.JPanel sidebar;
    private javax.swing.JTable tblAturan;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
