/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.main;

import java.awt.Color;
import view.data.FormAturan;
import view.data.FormDiagnosa;
import view.data.FormGejala;
import view.data.FormLaporan;
import view.data.FormPenyakit;
import view.data.FormRiwayat;

/**
 *
 * @author NAUFAL
 */
public class FormAdmin extends javax.swing.JFrame {
    Color colorNormal = new Color(255, 243, 236);
    Color colorHover = new Color(255, 220, 230);
    Color colorActive = new Color(173, 216, 255);
    boolean isPasswordVisible = false;
    javax.swing.ImageIcon iconOpen = new javax.swing.ImageIcon(getClass().getResource("/icon/eye.png"));
    javax.swing.ImageIcon iconClosed = new javax.swing.ImageIcon(getClass().getResource("/icon/hide.png"));

    /**
     * Creates new form FormAdmin
     */
    public FormAdmin() {
        initComponents();
        
        try {
            java.awt.Image icon = javax.imageio.ImageIO.read(getClass().getResource("/icon/logo2.png"));
            setIconImage(icon);
        } catch (Exception e) {
            System.out.println("Gagal load icon: " + e.getMessage());
        }
        if (koneksi.Session.namaAdmin == null || koneksi.Session.namaAdmin.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Hayo, Anda harus Login terlebih dahulu.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose();
            return;
        }
        
        setJamRealTime();
        tampilDataAdmin();
        rapihkanTabel();
        switchWarna(btnDataAdmin);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }
    
    private void tampilDataAdmin() {
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
        model.addColumn("No");
        model.addColumn("ID Admin");
        model.addColumn("Nama Lengkap");
        model.addColumn("Username");
        model.addColumn("Password");
        
        tabelAdmin.setModel(model);

        try {
            int no = 1;
            java.sql.Connection con = koneksi.KoneksiDB.getKoneksi();
            java.sql.Statement st = con.createStatement();
            java.sql.ResultSet rs = st.executeQuery("SELECT * FROM tbl_admin ORDER BY id_admin ASC");

            while (rs.next()) {
                model.addRow(new Object[]{
                    no++,
                    rs.getString("id_admin"),
                    rs.getString("nama_admin"),
                    rs.getString("username"),
                    rs.getString("password")    
                });
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal memuat data tabel: " + e.getMessage());
        }
    }
    
    private void tampilData(String keyword) {
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
        model.addColumn("No");
        model.addColumn("ID Admin");
        model.addColumn("Nama Lengkap");
        model.addColumn("Username");
        model.addColumn("Password");
        
        tabelAdmin.setModel(model);

        try {
            int no = 1;
            java.sql.Connection con = koneksi.KoneksiDB.getKoneksi();
            java.sql.Statement st = con.createStatement();
            String sql = "SELECT * FROM tbl_admin WHERE id_admin LIKE '%" + keyword + "%' OR nama_admin LIKE '%" + keyword + "%' ORDER BY id_admin ASC";
            java.sql.ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                model.addRow(new Object[]{
                    no++,
                    rs.getString("id_admin"),
                    rs.getString("nama_admin"),
                    rs.getString("username"),
                    rs.getString("password")
                });
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
        }
    }
    
    private void rapihkanTabel() {
        tabelAdmin.setRowHeight(28);
        javax.swing.table.TableColumnModel kolomModel = tabelAdmin.getColumnModel();
        kolomModel.getColumn(0).setPreferredWidth(50);
        kolomModel.getColumn(0).setMaxWidth(50);
        kolomModel.getColumn(1).setPreferredWidth(100);
        kolomModel.getColumn(1).setMaxWidth(100);
        kolomModel.getColumn(2).setPreferredWidth(350);
        kolomModel.getColumn(3).setPreferredWidth(150);
    }
   
    private void bersihForm() {
        txtIdAdmin.setText("");
        txtNama.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        txtCari.setText("");
        txtIdAdmin.setEditable(true); 
        txtIdAdmin.requestFocus(); 
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
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
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
        btnLogout = new javax.swing.JPanel();
        lblKeluar = new javax.swing.JLabel();
        btnDataAdmin = new javax.swing.JPanel();
        lblKeluar1 = new javax.swing.JLabel();
        pn_kanan = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblJam = new javax.swing.JLabel();
        lblAdmin = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pn_dasar = new javax.swing.JPanel();
        mainContent = new javax.swing.JPanel();
        txtIdAdmin = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnSimpan = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        lblShowPassword = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelAdmin = new javax.swing.JTable();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        btnKembali = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sidebar.setBackground(new java.awt.Color(147, 255, 232));
        sidebar.setMinimumSize(new java.awt.Dimension(200, 100));
        sidebar.setPreferredSize(new java.awt.Dimension(250, 768));

        jPanel2.setBackground(new java.awt.Color(0, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(250, 130));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel2.setText("SISTEM PAKAR");

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 20)); // NOI18N
        jLabel1.setText("IKAN NILA");

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/LogoDua.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(51, 51, 51))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jPanel9.setBackground(new java.awt.Color(147, 255, 232));
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

        jPanel10.setBackground(new java.awt.Color(147, 255, 232));
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

        jPanel11.setBackground(new java.awt.Color(147, 255, 232));
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

        jPanel12.setBackground(new java.awt.Color(147, 255, 232));
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

        jPanel13.setBackground(new java.awt.Color(147, 255, 232));
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

        lblKeluar1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblKeluar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user1.png"))); // NOI18N
        lblKeluar1.setText("ADMIN");

        javax.swing.GroupLayout btnDataAdminLayout = new javax.swing.GroupLayout(btnDataAdmin);
        btnDataAdmin.setLayout(btnDataAdminLayout);
        btnDataAdminLayout.setHorizontalGroup(
            btnDataAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnDataAdminLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblKeluar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        btnDataAdminLayout.setVerticalGroup(
            btnDataAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblKeluar1, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
            .addComponent(btnDataAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDataAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout sidebarLayout = new javax.swing.GroupLayout(sidebar);
        sidebar.setLayout(sidebarLayout);
        sidebarLayout.setHorizontalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sidebarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
        );
        sidebarLayout.setVerticalGroup(
            sidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sidebarLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(6, Short.MAX_VALUE))
        );

        getContentPane().add(sidebar, java.awt.BorderLayout.LINE_START);

        pn_kanan.setBackground(new java.awt.Color(255, 255, 255));
        pn_kanan.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(226, 245, 22));
        jPanel1.setPreferredSize(new java.awt.Dimension(1166, 80));

        lblJam.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblJam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/calendar.png"))); // NOI18N

        lblAdmin.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N
        lblAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("DATA ADMIN");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblJam)
                .addGap(43, 43, 43)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                .addGap(54, 54, 54)
                .addComponent(lblAdmin)
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAdmin, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblJam, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(25, 25, 25))
        );

        pn_kanan.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        pn_dasar.setBackground(new java.awt.Color(224, 251, 252));

        mainContent.setBackground(new java.awt.Color(255, 249, 227));
        mainContent.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "DATA ADMIN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 3, 18), new java.awt.Color(1, 1, 1))); // NOI18N

        txtIdAdmin.setEditable(false);
        txtIdAdmin.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtIdAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdAdminActionPerformed(evt);
            }
        });
        txtIdAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIdAdminKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdAdminKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jLabel3.setText("ID Admin:");

        txtNama.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaActionPerformed(evt);
            }
        });
        txtNama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNamaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNamaKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jLabel4.setText("Nama Lengkap:");

        txtUsername.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });
        txtUsername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsernameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsernameKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jLabel5.setText("Username:");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        jLabel6.setText("Password:");

        txtPassword.setFont(new java.awt.Font("Times New Roman", 1, 16)); // NOI18N
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPasswordFocusLost(evt);
            }
        });

        btnSimpan.setBackground(new java.awt.Color(0, 255, 0));
        btnSimpan.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/save-data.png"))); // NOI18N
        btnSimpan.setText("SIMPAN");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnUbah.setBackground(new java.awt.Color(0, 255, 255));
        btnUbah.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        btnUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/edit.png"))); // NOI18N
        btnUbah.setText("UBAH");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(255, 0, 0));
        btnHapus.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/litter.png"))); // NOI18N
        btnHapus.setText("HAPUS");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(255, 255, 0));
        btnBatal.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/block.png"))); // NOI18N
        btnBatal.setText("BATAL");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        lblShowPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/eye.png"))); // NOI18N
        lblShowPassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblShowPasswordMousePressed(evt);
            }
        });

        javax.swing.GroupLayout mainContentLayout = new javax.swing.GroupLayout(mainContent);
        mainContent.setLayout(mainContentLayout);
        mainContentLayout.setHorizontalGroup(
            mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainContentLayout.createSequentialGroup()
                        .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainContentLayout.createSequentialGroup()
                                .addComponent(btnSimpan)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnUbah)
                                .addGap(24, 24, 24)
                                .addComponent(btnHapus)
                                .addGap(18, 18, 18)
                                .addComponent(btnBatal))
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(txtPassword)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblShowPassword)
                        .addGap(24, 24, 24))
                    .addGroup(mainContentLayout.createSequentialGroup()
                        .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdAdmin)
                            .addComponent(txtNama)
                            .addComponent(txtUsername))
                        .addContainerGap())))
        );
        mainContentLayout.setVerticalGroup(
            mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainContentLayout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIdAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNama, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addGap(12, 12, 12)
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainContentLayout.createSequentialGroup()
                        .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                        .addGap(18, 18, 18))
                    .addGroup(mainContentLayout.createSequentialGroup()
                        .addComponent(lblShowPassword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(mainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus)
                    .addComponent(btnBatal))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(240, 255, 240));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.MatteBorder(null), "TABEL ADMIN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 3, 16))); // NOI18N

        tabelAdmin.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        tabelAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "No", "ID Admin", "Nama Lengkap", "Username", "Password"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelAdminMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tabelAdmin);

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

        btnCari.setBackground(new java.awt.Color(127, 255, 212));
        btnCari.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/loupe.png"))); // NOI18N
        btnCari.setText("CARI");
        btnCari.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnCetak.setBackground(new java.awt.Color(255, 0, 255));
        btnCetak.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/printer.png"))); // NOI18N
        btnCetak.setText("CETAK");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        btnKembali.setBackground(new java.awt.Color(255, 165, 0));
        btnKembali.setFont(new java.awt.Font("SansSerif", 0, 16)); // NOI18N
        btnKembali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/previous.png"))); // NOI18N
        btnKembali.setText("KEMBALI");
        btnKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKembaliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCari)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCetak)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKembali))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtCari, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCetak)
                        .addComponent(btnKembali)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pn_dasarLayout = new javax.swing.GroupLayout(pn_dasar);
        pn_dasar.setLayout(pn_dasarLayout);
        pn_dasarLayout.setHorizontalGroup(
            pn_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dasarLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pn_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        pn_dasarLayout.setVerticalGroup(
            pn_dasarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pn_dasarLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(mainContent, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(13, 13, 13)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    private void tabelAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelAdminMouseClicked
        int baris = tabelAdmin.rowAtPoint(evt.getPoint());
        String idAdmin = tabelAdmin.getValueAt(baris, 1).toString();
        String nama = tabelAdmin.getValueAt(baris, 2).toString();
        String username = tabelAdmin.getValueAt(baris, 3).toString();
        String password = tabelAdmin.getValueAt(baris, 4).toString(); 
        
        txtIdAdmin.setText(idAdmin);
        txtNama.setText(nama);
        txtUsername.setText(username);
        txtPassword.setText(password); 
        txtIdAdmin.setEditable(false);
    }//GEN-LAST:event_tabelAdminMouseClicked

    private void txtIdAdminKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdAdminKeyReleased
        tampilData(txtIdAdmin.getText());
    }//GEN-LAST:event_txtIdAdminKeyReleased

    private void txtIdAdminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdAdminKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdAdminKeyPressed

    private void txtIdAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdAdminActionPerformed
        tampilData(txtIdAdmin.getText());
    }//GEN-LAST:event_txtIdAdminActionPerformed

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void txtNamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaKeyPressed

    private void txtNamaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamaKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaKeyReleased

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtUsernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameKeyPressed

    private void txtUsernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsernameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameKeyReleased

    private void txtCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariActionPerformed
        tampilData(txtCari.getText());
    }//GEN-LAST:event_txtCariActionPerformed

    private void txtCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariKeyPressed

    private void txtCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariKeyReleased
        tampilData(txtCari.getText());
    }//GEN-LAST:event_txtCariKeyReleased

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        if (koneksi.Session.namaAdmin == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Anda harus Login terlebih dahulu untuk menambah data.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose();
            return;
        }
        tampilData(txtCari.getText());
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if (txtIdAdmin.getText().isEmpty() || txtNama.getText().isEmpty() || txtUsername.getText().isEmpty() || txtPassword.getPassword().length == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, "Semua kolom wajib diisi!", "Validasi", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            java.sql.Connection con = koneksi.KoneksiDB.getKoneksi();
            String sql = "INSERT INTO tbl_admin (id_admin, nama_admin, username, password) VALUES (?, ?, ?, ?)";
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            String password = new String(txtPassword.getPassword());
            pst.setString(1, txtIdAdmin.getText());
            pst.setString(2, txtNama.getText());
            pst.setString(3, txtUsername.getText());
            pst.setString(4, password); 
            pst.execute();
            javax.swing.JOptionPane.showMessageDialog(this, "Data Admin berhasil disimpan!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            tampilDataAdmin(); 
            bersihForm();      
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage(), "Error Database", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        if (txtIdAdmin.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Pilih data di tabel yang ingin diubah!", "Validasi", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            java.sql.Connection con = koneksi.KoneksiDB.getKoneksi();
            String password = new String(txtPassword.getPassword());
            java.sql.PreparedStatement pst;
            if (!password.isEmpty()) {
                String sql = "UPDATE tbl_admin SET nama_admin=?, username=?, password=? WHERE id_admin=?";
                pst = con.prepareStatement(sql);
                pst.setString(1, txtNama.getText());
                pst.setString(2, txtUsername.getText());
                pst.setString(3, password);
                pst.setString(4, txtIdAdmin.getText());
            } else {
                String sql = "UPDATE tbl_admin SET nama_admin=?, username=? WHERE id_admin=?";
                pst = con.prepareStatement(sql);
                pst.setString(1, txtNama.getText());
                pst.setString(2, txtUsername.getText());
                pst.setString(3, txtIdAdmin.getText());
            }
            pst.execute();
            javax.swing.JOptionPane.showMessageDialog(this, "Data Admin berhasil diubah!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            tampilDataAdmin(); 
            bersihForm();
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal mengubah data: " + e.getMessage(), "Error Database", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnUbahActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        if (txtIdAdmin.getText().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Pilih data di tabel yang ingin dihapus!", "Validasi", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        int konfirmasi = javax.swing.JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus Admin: " + txtNama.getText() + "?", "Konfirmasi Hapus", javax.swing.JOptionPane.YES_NO_OPTION);
        if (konfirmasi == javax.swing.JOptionPane.YES_OPTION) {
            try {
                java.sql.Connection con = koneksi.KoneksiDB.getKoneksi();
                String sql = "DELETE FROM tbl_admin WHERE id_admin=?";
                java.sql.PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, txtIdAdmin.getText());
                pst.execute();
                javax.swing.JOptionPane.showMessageDialog(this, "Data Admin berhasil dihapus!", "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                tampilDataAdmin();
                bersihForm();
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage(), "Error Database", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        bersihForm();
        tampilDataAdmin();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKembaliActionPerformed
        view.main.MenuUtama menu = new view.main.MenuUtama();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnKembaliActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        try {
            java.io.File namafile = new java.io.File("src/report/LaporanAdmin.jasper");
            net.sf.jasperreports.engine.JasperPrint jp = net.sf.jasperreports.engine.JasperFillManager.fillReport(namafile.getPath(), null, koneksi.KoneksiDB.getKoneksi());
            net.sf.jasperreports.view.JasperViewer.viewReport(jp, false);
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal mencetak laporan: " + e.getMessage(), "Error Laporan", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCetakActionPerformed

    private void txtPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusGained
        String pass = new String(txtPassword.getPassword());
        if (pass.equals("Password...")) {
            txtPassword.setText("");
            txtPassword.setEchoChar('\u2022');
        }
    }//GEN-LAST:event_txtPasswordFocusGained

    private void txtPasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPasswordFocusLost
        String pass = new String(txtPassword.getPassword());
        if (pass.equals("")) {
            txtPassword.setEchoChar((char) 0);
            txtPassword.setText("Password...");
        }
    }//GEN-LAST:event_txtPasswordFocusLost

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
    }//GEN-LAST:event_btnDataAdminMousePressed

    private void lblShowPasswordMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblShowPasswordMousePressed
        if (!isPasswordVisible) {
            lblShowPassword.setIcon(iconOpen);
            txtPassword.setEchoChar((char)0);
            isPasswordVisible = true;
        } else {
            lblShowPassword.setIcon(iconClosed);
            txtPassword.setEchoChar('\u2022');
            isPasswordVisible = false;
        }
    }//GEN-LAST:event_lblShowPasswordMousePressed

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
            java.util.logging.Logger.getLogger(FormAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAturan;
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCetak;
    private javax.swing.JPanel btnDashboard;
    private javax.swing.JPanel btnDataAdmin;
    private javax.swing.JPanel btnDiagnosa;
    private javax.swing.JPanel btnGejala;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnKembali;
    private javax.swing.JPanel btnLaporan;
    private javax.swing.JPanel btnLogout;
    private javax.swing.JPanel btnPenyakit;
    private javax.swing.JPanel btnRiwayat;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnUbah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblAdmin;
    private javax.swing.JLabel lblAturan;
    private javax.swing.JLabel lblCetak;
    private javax.swing.JLabel lblDashboard;
    private javax.swing.JLabel lblDiagnosa;
    private javax.swing.JLabel lblGejala;
    private javax.swing.JLabel lblJam;
    private javax.swing.JLabel lblKeluar;
    private javax.swing.JLabel lblKeluar1;
    private javax.swing.JLabel lblPenyakit;
    private javax.swing.JLabel lblRiwayat;
    private javax.swing.JLabel lblShowPassword;
    private javax.swing.JPanel mainContent;
    private javax.swing.JPanel pn_dasar;
    private javax.swing.JPanel pn_kanan;
    private javax.swing.JPanel sidebar;
    private javax.swing.JTable tabelAdmin;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtIdAdmin;
    private javax.swing.JTextField txtNama;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
