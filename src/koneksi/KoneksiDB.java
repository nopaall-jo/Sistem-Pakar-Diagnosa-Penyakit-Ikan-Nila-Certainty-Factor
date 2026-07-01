/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author NAUFAL
 */
public class KoneksiDB {
    private static Connection mysqlconfig;

    public static Connection getKoneksi() throws SQLException {
        try {
            String url = "jdbc:mysql://localhost:3307/db_ikan_nila";
            String user = "root";
            String pass = "";

            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            
            mysqlconfig = DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            System.err.println("Koneksi Gagal: " + e.getMessage());

            JOptionPane.showMessageDialog(
                null,
                "Koneksi Database Gagal! Pastikan XAMPP menyala.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        return mysqlconfig;
    }
}
