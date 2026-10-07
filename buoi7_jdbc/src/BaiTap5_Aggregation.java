import java.sql.*;

/**
 * BAI TAP 5 (Tuy chon) - Truy van du lieu nang cao
 * Aggregation Functions: SUM, AVG, MIN, MAX, COUNT + GROUP BY
 * Bang NhanVien mo rong co them cot luong (luong)
 */
public class BaiTap5_Aggregation {

    static final String DB_URL = "jdbc:sqlite:nhanvien_luong.db";

    public static void main(String[] args) {

        Connection conn = null;
        Statement  stmt = null;
        ResultSet  rs   = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            System.out.println("=== BAI TAP 5: AGGREGATION FUNCTIONS ===\n");

            // Tao bang NhanVien co them cot luong
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS NhanVien2 (" +
                "   id       INTEGER PRIMARY KEY, " +
                "   ten      TEXT    NOT NULL, " +
                "   chuc_vu  TEXT, " +
                "   luong    REAL" +
                ")"
            );
            stmt.executeUpdate("DELETE FROM NhanVien2");

            // Them du lieu nhieu nhan vien voi luong
            stmt.executeUpdate("INSERT INTO NhanVien2 VALUES (1,  'Nguyen Van An',     'Giam doc',        50000000)");
            stmt.executeUpdate("INSERT INTO NhanVien2 VALUES (2,  'Tran Thi Bich',     'Ke toan',         18000000)");
            stmt.executeUpdate("INSERT INTO NhanVien2 VALUES (3,  'Le Minh Cuong',     'Lap trinh vien',  22000000)");
            stmt.executeUpdate("INSERT INTO NhanVien2 VALUES (4,  'Pham Thi Dung',     'Nhan su',         15000000)");
            stmt.executeUpdate("INSERT INTO NhanVien2 VALUES (5,  'Hoang Van Em',      'Ky thuat',        20000000)");
            stmt.executeUpdate("INSERT INTO NhanVien2 VALUES (6,  'Vo Thi Phuong',     'Lap trinh vien',  25000000)");
            stmt.executeUpdate("INSERT INTO NhanVien2 VALUES (7,  'Dang Quoc Hung',    'Ke toan',         17000000)");
            stmt.executeUpdate("INSERT INTO NhanVien2 VALUES (8,  'Bui Thi Lan',       'Ky thuat',        19000000)");
            stmt.executeUpdate("INSERT INTO NhanVien2 VALUES (9,  'Do Van Manh',       'Lap trinh vien',  23000000)");
            stmt.executeUpdate("INSERT INTO NhanVien2 VALUES (10, 'Nguyen Thi Ngoc',   'Nhan su',         14000000)");
            conn.commit();
            System.out.println(">> Da them 10 nhan vien vao bang.\n");

            
            // SUM - Tong luong
            
            rs = stmt.executeQuery("SELECT SUM(luong) AS tong_luong FROM NhanVien2");
            if (rs.next()) {
                System.out.printf("Tong luong (SUM)         : %,15.0f VND%n", rs.getDouble("tong_luong"));
            }
            rs.close();

            
            // AVG - Luong trung binh
            
            rs = stmt.executeQuery("SELECT AVG(luong) AS avg_luong FROM NhanVien2");
            if (rs.next()) {
                System.out.printf("Luong trung binh (AVG)   : %,15.0f VND%n", rs.getDouble("avg_luong"));
            }
            rs.close();

            
            // MAX - Luong cao nhat
            
            rs = stmt.executeQuery(
                "SELECT ten, chuc_vu, MAX(luong) AS max_luong FROM NhanVien2"
            );
            if (rs.next()) {
                System.out.printf("Luong cao nhat (MAX)     : %,15.0f VND -- %s (%s)%n",
                    rs.getDouble("max_luong"),
                    rs.getString("ten"),
                    rs.getString("chuc_vu"));
            }
            rs.close();

            
            // MIN - Luong thap nhat
            
            rs = stmt.executeQuery(
                "SELECT ten, chuc_vu, MIN(luong) AS min_luong FROM NhanVien2"
            );
            if (rs.next()) {
                System.out.printf("Luong thap nhat (MIN)    : %,15.0f VND -- %s (%s)%n",
                    rs.getDouble("min_luong"),
                    rs.getString("ten"),
                    rs.getString("chuc_vu"));
            }
            rs.close();

            
            // COUNT + GROUP BY - So nhan vien theo chuc vu
            
            System.out.println("\n--- So nhan vien theo chuc vu (COUNT + GROUP BY) ---");
            rs = stmt.executeQuery(
                "SELECT chuc_vu, COUNT(*) AS so_nv, AVG(luong) AS avg_luong " +
                "FROM NhanVien2 " +
                "GROUP BY chuc_vu " +
                "ORDER BY so_nv DESC"
            );
            System.out.printf("%-20s %8s %20s%n", "Chuc vu", "So NV", "Luong TB (VND)");
            System.out.println("-".repeat(51));
            while (rs.next()) {
                System.out.printf("%-20s %8d %,20.0f%n",
                    rs.getString("chuc_vu"),
                    rs.getInt("so_nv"),
                    rs.getDouble("avg_luong"));
            }
            rs.close();

        } catch (ClassNotFoundException e) {
            System.out.println("[LOI] Khong tim thay Driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[LOI SQL] " + e.getMessage());
            try {
                if (conn != null) { conn.rollback(); System.out.println(">> ROLLBACK."); }
            } catch (SQLException ex) { ex.printStackTrace(); }
        } finally {
            try { if (rs   != null) rs.close();   } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close();  } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close();  } catch (SQLException e) { e.printStackTrace(); }
            System.out.println("\n>> Da dong ket noi.");
        }
    }
}

