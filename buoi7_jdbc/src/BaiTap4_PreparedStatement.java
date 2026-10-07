import java.sql.*;

/**
 * BAI TAP 4 - PreparedStatement
 * Su dung PreparedStatement thay cho Statement thuong
 * - Tang cuong bao mat (chong SQL Injection)
 * - Tang hieu nang khi thuc thi nhieu lan
 */
public class BaiTap4_PreparedStatement {

    static final String DB_URL = "jdbc:sqlite:sanpham_ps.db";

    public static void main(String[] args) {

        Connection        conn   = null;
        PreparedStatement pstmt  = null;
        ResultSet         rs     = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false);

            System.out.println("=== BAI TAP 4: PREPARED STATEMENT ===\n");

            // Tao bang
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS SanPham2 (" +
                "   maSP    INTEGER PRIMARY KEY, " +
                "   tenSP   TEXT    NOT NULL, " +
                "   gia     REAL, " +
                "   soLuong INTEGER" +
                ")"
            );
            stmt.executeUpdate("DELETE FROM SanPham2");
            stmt.close();

            
            // INSERT voi PreparedStatement
            
            System.out.println("--- [INSERT] Dung PreparedStatement ---");
            String sql_insert = "INSERT INTO SanPham2(maSP, tenSP, gia, soLuong) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql_insert);

            // San pham 1
            pstmt.setInt(1, 1);
            pstmt.setString(2, "iPhone 16 Pro");
            pstmt.setDouble(3, 32000000);
            pstmt.setInt(4, 20);
            pstmt.executeUpdate();

            // San pham 2
            pstmt.setInt(1, 2);
            pstmt.setString(2, "Samsung Galaxy S25");
            pstmt.setDouble(3, 28000000);
            pstmt.setInt(4, 15);
            pstmt.executeUpdate();

            // San pham 3
            pstmt.setInt(1, 3);
            pstmt.setString(2, "Xiaomi 14T Pro");
            pstmt.setDouble(3, 18000000);
            pstmt.setInt(4, 30);
            pstmt.executeUpdate();

            conn.commit();
            System.out.println(">> Da them 3 san pham bang PreparedStatement.");
            pstmt.close();

            
            // SELECT voi PreparedStatement (tim theo gia)
            
            System.out.println("\n--- [SELECT] Tim san pham gia < 30,000,000 ---");
            String sql_select = "SELECT * FROM SanPham2 WHERE gia < ?";
            pstmt = conn.prepareStatement(sql_select);
            pstmt.setDouble(1, 30000000);
            rs = pstmt.executeQuery();

            System.out.printf("%-6s %-22s %15s %10s%n", "maSP", "tenSP", "Gia (VND)", "So luong");
            System.out.println("-".repeat(57));
            while (rs.next()) {
                System.out.printf("%-6d %-22s %,15.0f %10d%n",
                    rs.getInt("maSP"),
                    rs.getString("tenSP"),
                    rs.getDouble("gia"),
                    rs.getInt("soLuong"));
            }
            rs.close();
            pstmt.close();

            
            // UPDATE voi PreparedStatement
            
            System.out.println("\n--- [UPDATE] Cap nhat gia theo maSP ---");
            conn.setAutoCommit(false);
            String sql_update = "UPDATE SanPham2 SET gia=? WHERE maSP=?";
            pstmt = conn.prepareStatement(sql_update);
            pstmt.setDouble(1, 19500000);
            pstmt.setInt(2, 3);
            int rows = pstmt.executeUpdate();
            conn.commit();
            System.out.println(">> Cap nhat " + rows + " ban ghi (Xiaomi 14T Pro -> 19,500,000).");
            pstmt.close();

            
            // DELETE voi PreparedStatement
            
            System.out.println("\n--- [DELETE] Xoa san pham theo maSP ---");
            conn.setAutoCommit(false);
            String sql_delete = "DELETE FROM SanPham2 WHERE maSP=?";
            pstmt = conn.prepareStatement(sql_delete);
            pstmt.setInt(1, 2);
            int deleted = pstmt.executeUpdate();
            conn.commit();
            System.out.println(">> Da xoa " + deleted + " san pham (maSP=2).");
            pstmt.close();

            // Hien thi ket qua cuoi
            System.out.println("\n--- [READ] Danh sach sau cung ---");
            Statement s = conn.createStatement();
            rs = s.executeQuery("SELECT * FROM SanPham2");
            System.out.printf("%-6s %-22s %15s %10s%n", "maSP", "tenSP", "Gia (VND)", "So luong");
            System.out.println("-".repeat(57));
            while (rs.next()) {
                System.out.printf("%-6d %-22s %,15.0f %10d%n",
                    rs.getInt("maSP"),
                    rs.getString("tenSP"),
                    rs.getDouble("gia"),
                    rs.getInt("soLuong"));
            }
            s.close();

        } catch (ClassNotFoundException e) {
            System.out.println("[LOI] Khong tim thay JDBC Driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[LOI SQL] " + e.getMessage());
            try {
                if (conn != null) { conn.rollback(); System.out.println(">> ROLLBACK."); }
            } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try { if (rs    != null) rs.close();    } catch (SQLException e) { e.printStackTrace(); }
            try { if (pstmt != null) pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn  != null) conn.close();  } catch (SQLException e) { e.printStackTrace(); }
            System.out.println("\n>> Da dong ket noi.");
        }
    }
}

