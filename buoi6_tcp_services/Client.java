import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Buoi 6 - Cau 2 + Cau 3 + Cau 5:
 * Client goi yeu cau va ket noi toi server qua IP va port cho truoc.
 *
 * Cach chay: java Client <ServerIP> <ServerPort>
 * Vi du: java Client localhost 7000
 * java Client 192.168.1.10 7000
 */
public class Client {

    public static void main(String[] args) {
        // === Cau 2: Ket noi den server qua IP va Port ===
        if (args.length < 2) {
            System.out.println("Usage: java Client <ServerIP> <ServerPort>");
            System.out.println("Vi du: java Client localhost 7000");
            return;
        }

        String serverIP = args[0];
        int serverPort = Integer.parseInt(args[1]);

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║         CLIENT DANG KHOI DONG            ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.println("Dang ket noi toi " + serverIP + ":" + serverPort + "...");

        try (
                Socket socket = new Socket(serverIP, serverPort);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
                Scanner scanner = new Scanner(System.in)) {
            System.out.println("DA KET NOI THANH CONG!\n");

            // === Nhan va hien thi menu tu server ===
            receiveMenu(in);

            // Vong lap chinh
            while (true) {
                // Nhap lua chon dich vu
                System.out.print("\nNhap lua chon cua ban: ");
                String choice = scanner.nextLine().trim();

                // Gui lua chon len server
                out.println(choice);

                // === Cau 5: Kiem tra EXIT. de dong ket noi ===
                if (choice.equalsIgnoreCase("EXIT.")) {
                    System.out.println("\nDa gui EXIT. - Dong ket noi. Tam biet!");
                    break;
                }

                // Nhap cac dong du lieu gui len server
                System.out.println("Nhap cac dong chuoi (nhap '.' de ket thuc, hoac 'EXIT.' de thoat han):");
                boolean exitSent = false;
                while (true) {
                    System.out.print("  > ");
                    String line = scanner.nextLine();
                    out.println(line);

                    if (line.trim().equals(".")) {
                        break; // Ket thuc nhap lieu - doi ket qua
                    }
                    if (line.trim().equalsIgnoreCase("EXIT.")) {
                        exitSent = true;
                        break; // Thoat hoan toan
                    }
                }

                // Nhan ket qua tu server
                System.out.println("\n--- KET QUA TU SERVER ---");
                String responseLine;
                while ((responseLine = in.readLine()) != null) {
                    if (responseLine.equals(">>>END"))
                        break;
                    System.out.println(responseLine);
                }
                System.out.println("-------------------------");

                if (exitSent) {
                    System.out.println("Da gui EXIT. - Dong ket noi. Tam biet!");
                    break;
                }

                // Nhan lai menu de tiep tuc
                receiveMenu(in);
            }

        } catch (IOException e) {
            System.out.println("Loi ket noi: " + e.getMessage());
        }
    }

    /**
     * Doc va hien thi toan bo menu tu server cho den khi nhan marker ">>>READY".
     */
    private static void receiveMenu(BufferedReader in) throws IOException {
        String line;
        while ((line = in.readLine()) != null) {
            if (line.equals(">>>READY"))
                break; // Marker: server san sang nhan lua chon
            System.out.println(line);
        }
    }
}
