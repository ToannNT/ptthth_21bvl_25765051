import java.sql.*;

/**
 * BUOI 7 - JDBC (Java Database Connection)
 * B1 -> B5: Ket noi, tao bang NhanVien, them du lieu, truy van, dong ket noi
 * Co xu ly ngoai le va Transaction
 */
public class App {

    static final String JAR_URL = "org.sqlite.JDBC";
    static final String DB_URL = "jdbc:sqlite:nhanvien.db";

    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // B1. Thiet lap ket noi
            Class.forName(JAR_URL);
            String username = ""; // SQLite khong can username/password
            String passwd = "";
            conn = DriverManager.getConnection(DB_URL, username, passwd);
            System.out.println("=== Ket noi SQLite thanh cong! ===\n");

            // Tat auto-commit de su dung Transaction (xu ly ngoai le)
            conn.setAutoCommit(false);

            // B2. Tao doi tuong Statement
            stmt = conn.createStatement();

            // B3.1. Tao bang NhanVien
            String sql_create_table = "CREATE TABLE IF NOT EXISTS NhanVien (" +
                    "   id       INTEGER PRIMARY KEY, " +
                    "   ten      TEXT    NOT NULL, " +
                    "   chuc_vu  TEXT" +
                    ")";
            stmt.executeUpdate(sql_create_table);
            System.out.println(">> Tao bang NhanVien thanh cong.");

            // Xoa du lieu cu (neu chay lai nhieu lan)
            stmt.executeUpdate("DELETE FROM NhanVien");

            // B3.2. Them du lieu (it nhat 3 nhan vien)
            String sql_insert1 = "INSERT INTO NhanVien(id, ten, chuc_vu) VALUES (1, 'Nguyen Van An',    'Giam doc')";
            String sql_insert2 = "INSERT INTO NhanVien(id, ten, chuc_vu) VALUES (2, 'Tran Thi Bich',    'Ke toan')";
            String sql_insert3 = "INSERT INTO NhanVien(id, ten, chuc_vu) VALUES (3, 'Le Minh Cuong',    'Lap trinh vien')";
            String sql_insert4 = "INSERT INTO NhanVien(id, ten, chuc_vu) VALUES (4, 'Pham Thi Dung',    'Nhan su')";
            String sql_insert5 = "INSERT INTO NhanVien(id, ten, chuc_vu) VALUES (5, 'Hoang Van Em',     'Ky thuat')";

            stmt.executeUpdate(sql_insert1);
            stmt.executeUpdate(sql_insert2);
            stmt.executeUpdate(sql_insert3);
            stmt.executeUpdate(sql_insert4);
            stmt.executeUpdate(sql_insert5);
            System.out.println(">> Da them 5 nhan vien vao bang.");

            // Commit giao dich (Transaction)
            conn.commit();
            System.out.println(">> Transaction COMMIT thanh cong.\n");

            // B4. Truy van va hien thi du lieu
            String sql_select = "SELECT * FROM NhanVien";
            rs = stmt.executeQuery(sql_select);

            System.out.println("--- DANH SACH NHAN VIEN ---");
            System.out.printf("%-5s %-25s %-20s%n", "ID", "HO TEN", "CHUC VU");
            System.out.println("-".repeat(52));

            while (rs.next()) {
                int id = rs.getInt("id");
                String ten = rs.getString("ten");
                String chuc_vu = rs.getString("chuc_vu");
                System.out.printf("%-5d %-25s %-20s%n", id, ten, chuc_vu);
            }
            System.out.println("-".repeat(52));

        } catch (ClassNotFoundException e) {
            System.out.println("[LOI] Khong tim thay JDBC Driver: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("[LOI SQL] " + e.getMessage());
            // Rollback neu co loi
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println(">> Transaction ROLLBACK - da huy cac thay doi.");
                }
            } catch (SQLException ex) {
                System.out.println("[LOI ROLLBACK] " + ex.getMessage());
            }
        } finally {

            // B5. Dong ket noi va giai phong tai nguyen
            // (dong theo thu tu nguoc lai)
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("\n>> Da dong ket noi - giai phong tai nguyen.");
        }
    }
}
