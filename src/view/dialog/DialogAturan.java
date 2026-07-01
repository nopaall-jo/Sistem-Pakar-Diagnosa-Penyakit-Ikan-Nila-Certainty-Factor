/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.dialog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import koneksi.KoneksiDB;

/**
 *
 * @author NAUFAL
 */

public class DialogAturan extends javax.swing.JDialog {
    public boolean isEdit = false;
    private String idAturanEdit = "";
    
    /**
     * Creates new form DialogAturan
     */
    
    public DialogAturan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        try {
            java.awt.Image icon = javax.imageio.ImageIO.read(getClass().getResource("/icon/logo2.png"));
            setIconImage(icon);
        } catch (Exception e) {
            System.out.println("Gagal load icon: " + e.getMessage());
        }
        this.setLocationRelativeTo(parent);
        
        tampilCmbPenyakit();
        tampilTabelGejala();
        setupComboBoxDiTabel();
        rapihkanTabel();
    }
    
    private void tampilCmbPenyakit() {
        try {
            cmbPenyakit.removeAllItems();
            cmbPenyakit.addItem("-- Pilih Penyakit --");
            Connection con = KoneksiDB.getKoneksi();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tbl_penyakit ORDER BY kode_penyakit ASC");
            while (rs.next()) {
                cmbPenyakit.addItem("[" + rs.getString("kode_penyakit") + "] " + rs.getString("nama_penyakit"));
            }
        } catch (Exception e) {
            System.out.println("Error Load Cmb Penyakit: " + e.getMessage());
        }
    }

    private void tampilTabelGejala() {
        DefaultTableModel model = (DefaultTableModel) tabelGejala.getModel();
        model.setRowCount(0);
        try {
            Connection con = KoneksiDB.getKoneksi();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tbl_gejala ORDER BY kode_gejala ASC");
            while (rs.next()) {
                model.addRow(new Object[]{
                    false, 
                    rs.getString("kode_gejala"), 
                    rs.getString("nama_gejala"), 
                    "0.0",
                    "0.0"
                }); 
            }
        } catch (Exception e) {
            System.out.println("Error Load Tabel Gejala: " + e.getMessage());
        }
    }

    public void modeTambah() {
        this.isEdit = false;
        cmbPenyakit.setSelectedIndex(0);
        tampilCmbPenyakit();
        tampilTabelGejala();
        setupComboBoxDiTabel();
    }
    
    private void setupComboBoxDiTabel() {
        javax.swing.JComboBox<String> cmbNilaiCF = new javax.swing.JComboBox<>();
        cmbNilaiCF.addItem("0.0");
        cmbNilaiCF.addItem("0.1");
        cmbNilaiCF.addItem("0.2");
        cmbNilaiCF.addItem("0.3");
        cmbNilaiCF.addItem("0.4");
        cmbNilaiCF.addItem("0.5");
        cmbNilaiCF.addItem("0.6");
        cmbNilaiCF.addItem("0.7");
        cmbNilaiCF.addItem("0.8");
        cmbNilaiCF.addItem("0.9");
        cmbNilaiCF.addItem("1.0");

        javax.swing.table.TableColumn kolomMB = tabelGejala.getColumnModel().getColumn(3);
        kolomMB.setCellEditor(new javax.swing.DefaultCellEditor(cmbNilaiCF));
        javax.swing.table.TableColumn kolomMD = tabelGejala.getColumnModel().getColumn(4);
        kolomMD.setCellEditor(new javax.swing.DefaultCellEditor(cmbNilaiCF));
    }
    
    private void rapihkanTabel() {
        tabelGejala.setRowHeight(30);
        javax.swing.table.TableColumnModel kolomModel = tabelGejala.getColumnModel();
        kolomModel.getColumn(0).setPreferredWidth(50);
        kolomModel.getColumn(0).setMaxWidth(50);
        kolomModel.getColumn(1).setPreferredWidth(80);
        kolomModel.getColumn(1).setMaxWidth(80);
        kolomModel.getColumn(2).setPreferredWidth(450);
        kolomModel.getColumn(3).setPreferredWidth(80);
        kolomModel.getColumn(3).setMaxWidth(80);
        kolomModel.getColumn(4).setPreferredWidth(80);
        kolomModel.getColumn(4).setMaxWidth(80);
    }

    public void modeEdit(String id_aturan, String penyakit, String kodeGejala, String mb, String md) {
        this.isEdit = true;
        this.idAturanEdit = id_aturan;
        cmbPenyakit.setSelectedItem(penyakit);
        for(int i=0; i<tabelGejala.getRowCount(); i++) {
            if(tabelGejala.getValueAt(i, 1).toString().equals(kodeGejala)) {
                tabelGejala.setValueAt(true, i, 0);
                tabelGejala.setValueAt(mb, i, 3);
                tabelGejala.setValueAt(md, i, 4);
                break;
            }
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

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbPenyakit = new javax.swing.JComboBox<>();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelGejala = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jPanel1.setBackground(new java.awt.Color(233, 255, 112));

        jPanel2.setBackground(new java.awt.Color(255, 125, 0));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Tambah Aturan Baru");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 1210, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(128, 255, 219));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(2, 1, 2, 1, new java.awt.Color(0, 0, 0)), "INPUT DATA ATURAN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 3, 18))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("Pilih Penyakit");

        cmbPenyakit.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        cmbPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPenyakitActionPerformed(evt);
            }
        });

        btnSimpan.setBackground(new java.awt.Color(255, 20, 147));
        btnSimpan.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/save-data.png"))); // NOI18N
        btnSimpan.setText("+ SIMPAN DATA");
        btnSimpan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(255, 255, 102));
        btnBatal.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/block.png"))); // NOI18N
        btnBatal.setText("BATAL");
        btnBatal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "DAFTAR GEJALA", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 16))); // NOI18N

        tabelGejala.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Pilih", "Kode Gejala", "Nama Gejala", "Nilai MB", "Nilai MD"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelGejala);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbPenyakit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbPenyakit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBatal, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPenyakitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPenyakitActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        if (koneksi.Session.namaAdmin == null || koneksi.Session.namaAdmin.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Akses Ditolak! Anda harus Login terlebih dahulu.", "Peringatan", javax.swing.JOptionPane.WARNING_MESSAGE);
            new view.main.FormLogin().setVisible(true);
            this.dispose(); 
            return; 
        }
        if (cmbPenyakit.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Silakan pilih Penyakit terlebih dahulu!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String getPenyakit = cmbPenyakit.getSelectedItem().toString();
            String kodeP = getPenyakit.substring(1, 4); 
            
            Connection con = KoneksiDB.getKoneksi();
            boolean adaYgDisimpan = false;
            for (int i = 0; i < tabelGejala.getRowCount(); i++) {
                Boolean isChecked = (Boolean) tabelGejala.getValueAt(i, 0);
                if (isChecked != null && isChecked == true) {
                    String kodeG = tabelGejala.getValueAt(i, 1).toString();
                    String nilaiMB = tabelGejala.getValueAt(i, 3).toString();
                    String nilaiMD = tabelGejala.getValueAt(i, 4).toString();
                    
                    double mb = Double.parseDouble(nilaiMB);
                    double md = Double.parseDouble(nilaiMD);
                    if (mb < md) {
                        JOptionPane.showMessageDialog(this, "Gejala " + kodeG + ": Nilai MB harus lebih besar atau sama dengan MD!", "Validasi CF", JOptionPane.WARNING_MESSAGE);
                        continue; 
                    }
                    
                    PreparedStatement pst;
                    if (isEdit == false) {
                        String sql = "INSERT INTO tbl_aturan (kode_penyakit, kode_gejala, nilai_mb, nilai_md) VALUES (?, ?, ?, ?)";
                        pst = con.prepareStatement(sql);
                        pst.setString(1, kodeP);
                        pst.setString(2, kodeG);
                        pst.setString(3, nilaiMB);
                        pst.setString(4, nilaiMD);
                        pst.execute();
                    } else {
                        String sql = "UPDATE tbl_aturan SET kode_penyakit=?, kode_gejala=?, nilai_mb=?, nilai_md=? WHERE id_aturan=?";
                        pst = con.prepareStatement(sql);
                        pst.setString(1, kodeP);
                        pst.setString(2, kodeG);
                        pst.setString(3, nilaiMB);
                        pst.setString(4, nilaiMD);
                        pst.setString(5, idAturanEdit);
                        pst.execute();
                    }
                    adaYgDisimpan = true;
                }
            }
            
            if(adaYgDisimpan) {
                JOptionPane.showMessageDialog(this, "Basis aturan Certainty Factor berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Pilih minimal 1 gejala untuk disimpan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal simpan: " + e.getMessage(), "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

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
            java.util.logging.Logger.getLogger(DialogAturan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogAturan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogAturan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogAturan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogAturan dialog = new DialogAturan(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cmbPenyakit;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelGejala;
    // End of variables declaration//GEN-END:variables
}
