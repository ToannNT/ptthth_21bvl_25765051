import java.sql.*;

/**
 * BAI TAP 1 - Quan ly Danh muc San Pham
 * Bang SanPham (maSP, tenSP, gia, soLuong)
 * Thuc hanh CRUD: Create, Read, Update, Delete
 * + Transaction + Exception Handling (try-catch-finally)
 */
public class BaiTap1_SanPham {

    static final String DB_URL = "jdbc:sqlite:sanpham.db";

    public static void main(String[] args) {

        Connection conn = null;
        Statement  stmt = null;
        ResultSet  rs   = null;

        try {
            // Ket noi
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false); // Bat dau Transaction
            stmt = conn.createStatement();

            // ----------------------------------------
            // CREATE TABLE
            // ----------------------------------------
            String sql_create =
                "CREATE TABLE IF NOT EXISTS SanPham (" +
                "   maSP     INTEGER PRIMARY KEY, " +
                "   tenSP    TEXT    NOT NULL, " +
                "   gia      REAL, " +
                "   soLuong  INTEGER" +
                ")";
            stmt.executeUpdate(sql_create);
            stmt.executeUpdate("DELETE FROM SanPham"); // reset
            System.out.println("=== BAI TAP 1: QUAN LY SAN PHAM ===\n");

            // ----------------------------------------
            // CREATE (Them moi - it nhat 3 san pham)
            // ----------------------------------------
            System.out.println("--- [CREATE] Them san pham ---");
            stmt.executeUpdate("INSERT INTO SanPham VALUES (1, 'Laptop Dell XPS',   25000000, 10)");
            stmt.executeUpdate("INSERT INTO SanPham VALUES (2, 'Chuot Logitech',       350000, 50)");
            stmt.executeUpdate("INSERT INTO SanPham VALUES (3, 'Ban phim Co',         850000, 30)");
            stmt.executeUpdate("INSERT INTO SanPham VALUES (4, 'Man hinh LG 27 inch', 6500000, 15)");
            stmt.executeUpdate("INSERT INTO SanPham VALUES (5, 'Tai nghe Sony',      1200000, 25)");
            System.out.println(">> Da them 5 san pham.");
            conn.commit();

            // ----------------------------------------
            // READ - Liet ke tat ca san pham
            // ----------------------------------------
            System.out.println("\n--- [READ] Tat ca san pham ---");
            hienThiSanPham(stmt);

            // ----------------------------------------
            // READ - Tim kiem theo tenSP
            // ----------------------------------------
            System.out.println("\n--- [READ] Tim kiem theo ten: 'Chuot' ---");
            rs = stmt.executeQuery("SELECT * FROM SanPham WHERE tenSP LIKE '%Chuot%'");
            System.out.printf("%-6s %-25s %15s %10s%n", "maSP", "tenSP", "Gia (VND)", "So luong");
            System.out.println("-".repeat(60));
            while (rs.next()) {
                System.out.printf("%-6d %-25s %,15.0f %10d%n",
                    rs.getInt("maSP"),
                    rs.getString("tenSP"),
                    rs.getDouble("gia"),
                    rs.getInt("soLuong"));
            }
            rs.close();

            // ----------------------------------------
            // UPDATE - Cap nhat gia san pham maSP=1
            // ----------------------------------------
            System.out.println("\n--- [UPDATE] Cap nhat gia Laptop (maSP=1) -> 27,000,000 ---");
            conn.setAutoCommit(false);
            int rowsUpdated = stmt.executeUpdate(
                "UPDATE SanPham SET gia=27000000 WHERE maSP=1"
            );
            conn.commit();
            System.out.println(">> Da cap nhat " + rowsUpdated + " ban ghi.");

            // UPDATE so luong
            System.out.println("--- [UPDATE] Cap nhat so luong Chuot (maSP=2) -> 60 ---");
            conn.setAutoCommit(false);
            stmt.executeUpdate("UPDATE SanPham SET soLuong=60 WHERE maSP=2");
            conn.commit();
            System.out.println(">> Da cap nhat so luong.");

            // ----------------------------------------
            // DELETE - Xoa san pham maSP=5
            // ----------------------------------------
            System.out.println("\n--- [DELETE] Xoa san pham maSP=5 ---");
            conn.setAutoCommit(false);
            int rowsDeleted = stmt.executeUpdate("DELETE FROM SanPham WHERE maSP=5");
            conn.commit();
            System.out.println(">> Da xoa " + rowsDeleted + " san pham.");

            // READ lai sau khi chinh sua
            System.out.println("\n--- [READ] Danh sach sau khi cap nhat ---");
            hienThiSanPham(stmt);

        } catch (ClassNotFoundException e) {
            System.out.println("[LOI] Khong tim thay JDBC Driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[LOI SQL] " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println(">> Transaction ROLLBACK.");
                }
            } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            // Dong tai nguyen theo thu tu nguoc lai
            try { if (rs   != null) rs.close();   } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close();  } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close();  } catch (SQLException e) { e.printStackTrace(); }
            System.out.println("\n>> Da dong ket noi.");
        }
    }

    // Ham tien ich: hien thi tat ca san pham
    static void hienThiSanPham(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM SanPham");
        System.out.printf("%-6s %-25s %15s %10s%n", "maSP", "tenSP", "Gia (VND)", "So luong");
        System.out.println("-".repeat(60));
        while (rs.next()) {
            System.out.printf("%-6d %-25s %,15.0f %10d%n",
                rs.getInt("maSP"),
                rs.getString("tenSP"),
                rs.getDouble("gia"),
                rs.getInt("soLuong"));
        }
        rs.close();
    }
}

