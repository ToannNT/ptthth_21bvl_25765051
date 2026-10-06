import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Buoi 6 - Cau 1 + Cau 3 + Cau 4:
 * Thread xu ly tung client ket noi.
 * - STT le -> cung cap Dich vu 1 va Dich vu 3
 * - STT chan -> cung cap Dich vu 2 va Dich vu 4
 *
 * Protocol:
 * Server -> Client: menu + ">>>READY"
 * Client -> Server: so dich vu HOAC "EXIT."
 * Client -> Server: cac dong du lieu, ket thuc bang "."
 * Server -> Client: ket qua + ">>>END"
 * (lap lai tu dau)
 */
public class ClientHandler extends Thread {
    private final Socket socket;
    private final int stt; // So thu tu client (1, 2, 3, ...)
    private final boolean isOdd; // Le -> true | Chan -> false

    public ClientHandler(Socket socket, int stt) {
        this.socket = socket;
        this.stt = stt;
        this.isOdd = (stt % 2 != 0);
    }

    @Override
    public void run() {
        System.out.println("[Handler #" + stt + "] Thread bat dau.");

        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true)) {
            // === Cau 3: Gui danh sach dich vu sau khi chap nhan ket noi ===
            sendMenu(out);

            // Vong lap chinh: xu ly cac yeu cau cho den khi client gui EXIT.
            while (true) {
                // Doc lua chon dich vu tu client
                String choice = in.readLine();
                if (choice == null) {
                    System.out.println("[Handler #" + stt + "] Client ngat ket noi.");
                    break;
                }
                choice = choice.trim();

                // === Cau 5: Xu ly EXIT. ===
                if (choice.equalsIgnoreCase("EXIT.")) {
                    System.out.println("[Handler #" + stt + "] Nhan EXIT. - dong ket noi.");
                    break;
                }

                // Kiem tra lua chon hop le
                int service;
                try {
                    service = Integer.parseInt(choice);
                } catch (NumberFormatException e) {
                    out.println(">>> Lua chon khong hop le! Vui long chon lai.");
                    out.println(">>>END");
                    sendMenu(out);
                    continue;
                }

                // Kiem tra dich vu dung voi STT
                if (!isValidChoice(service)) {
                    String allowed = isOdd ? "1 hoac 3" : "2 hoac 4";
                    out.println(
                            ">>> Client STT " + (isOdd ? "le" : "chan") + " chi duoc chon dich vu " + allowed + "!");
                    out.println(">>>END");
                    sendMenu(out);
                    continue;
                }

                System.out.println("[Handler #" + stt + "] Chon dich vu " + service + ". Dang nhan du lieu...");

                // === Doc cac dong du lieu tu client (ket thuc bang ".") ===
                List<String> lines = new ArrayList<>();
                boolean exitRequested = false;
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.trim().equalsIgnoreCase("EXIT.")) {
                        exitRequested = true;
                        break;
                    }
                    if (line.trim().equals(".")) {
                        break; // Ket thuc nhap lieu
                    }
                    lines.add(line);
                }

                // === Cau 4: Xu ly va gui ket qua ve client ===
                String result = processService(service, lines);
                out.println(result);
                out.println(">>>END");

                System.out.println("[Handler #" + stt + "] Da xu ly va gui ket qua dich vu " + service + ".");

                if (exitRequested) {
                    System.out.println("[Handler #" + stt + "] Nhan EXIT. trong qua trinh nhap - dong ket noi.");
                    break;
                }

                // Gui lai menu cho lan ke tiep
                sendMenu(out);
            }

        } catch (IOException e) {
            System.out.println("[Handler #" + stt + "] Loi IO: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
            System.out.println("[Handler #" + stt + "] Thread ket thuc. Socket da dong.");
        }
    }

    // -----------------------------------------------------------------------
    // Gui menu dich vu tuong ung voi STT chan/le
    // -----------------------------------------------------------------------
    private void sendMenu(PrintWriter out) {
        if (isOdd) {
            out.println("╔══════════════════════════════════════════════════════════════╗");
            out.println("║  DANH SACH DICH VU - Client #" + stt + " (STT Le)              ║");
            out.println("╠══════════════════════════════════════════════════════════════╣");
            out.println("║  1. Dao nguoc toan bo chuoi + in hoa ky tu dau tung tu      ║");
            out.println("║     VD: PHAT TRIEN HE THONG → Poh Gnoht Eh Niert Tahp      ║");
            out.println("║  3. Dem so luong tu tong cong cua tat ca cac dong           ║");
            out.println("╚══════════════════════════════════════════════════════════════╝");
            out.println("Chon dich vu (1 hoac 3) hoac nhap EXIT. de thoat:");
        } else {
            out.println("╔══════════════════════════════════════════════════════════════╗");
            out.println("║  DANH SACH DICH VU - Client #" + stt + " (STT Chan)            ║");
            out.println("╠══════════════════════════════════════════════════════════════╣");
            out.println("║  2. Dao nguoc tung tu + in hoa ky tu dau, giu nguyen vi tri ║");
            out.println("║     VD: PHAT TRIEN HE THONG → Tahp Niert Eh Gnoht          ║");
            out.println("║  4. Dem so luong tu tung dong                               ║");
            out.println("╚══════════════════════════════════════════════════════════════╝");
            out.println("Chon dich vu (2 hoac 4) hoac nhap EXIT. de thoat:");
        }
        out.println(">>>READY");
    }

    // Kiem tra lua chon co phu hop voi STT khong
    private boolean isValidChoice(int service) {
        if (isOdd)
            return (service == 1 || service == 3);
        else
            return (service == 2 || service == 4);
    }

    // -----------------------------------------------------------------------
    // Xu ly dich vu va tra ve ket qua dang String
    // -----------------------------------------------------------------------
    private String processService(int service, List<String> lines) {
        StringBuilder result = new StringBuilder();

        switch (service) {
            case 1:
                // DV1: Dao nguoc TOAN BO chuoi, sau do in hoa ky tu dau moi tu
                result.append("=== KET QUA DICH VU 1 ===\n");
                for (String line : lines) {
                    String reversed = new StringBuilder(line).reverse().toString();
                    result.append(capitalizeEachWord(reversed)).append("\n");
                }
                break;

            case 2:
                // DV2: Dao nguoc TUNG TU, in hoa ky tu dau, giu nguyen vi tri tu
                result.append("=== KET QUA DICH VU 2 ===\n");
                for (String line : lines) {
                    result.append(reverseEachWord(line)).append("\n");
                }
                break;

            case 3:
                // DV3: Dem tong so luong tu tat ca cac dong
                result.append("=== KET QUA DICH VU 3 ===\n");
                int totalWords = 0;
                for (String line : lines) {
                    if (!line.trim().isEmpty()) {
                        totalWords += line.trim().split("\\s+").length;
                    }
                }
                result.append("Tong so luong tu cua tat ca cac dong: ").append(totalWords).append(" tu");
                break;

            case 4:
                // DV4: Dem so luong tu tung dong
                result.append("=== KET QUA DICH VU 4 ===\n");
                for (int i = 0; i < lines.size(); i++) {
                    String l = lines.get(i).trim();
                    int count = l.isEmpty() ? 0 : l.split("\\s+").length;
                    result.append("  Dong ").append(i + 1).append(": ").append(count).append(" tu\n");
                }
                break;

            default:
                result.append("Dich vu khong ton tai.");
        }

        return result.toString().trim();
    }

    /**
     * In hoa ky tu DAU cua tung tu, phan con lai viet thuong.
     * VD: "POH GNOHT EH" → "Poh Gnoht Eh"
     */
    private String capitalizeEachWord(String s) {
        String[] tokens = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            if (token.isEmpty()) {
                sb.append(" ");
                continue;
            }
            sb.append(Character.toUpperCase(token.charAt(0)));
            if (token.length() > 1)
                sb.append(token.substring(1).toLowerCase());
            sb.append(" ");
        }
        return sb.toString().trim();
    }

    /**
     * Dao nguoc tung tu, in hoa ky tu dau, giu nguyen vi tri cua tung tu.
     * VD: "PHAT TRIEN HE" → "Tahp Niert Eh"
     */
    private String reverseEachWord(String s) {
        String[] tokens = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String token : tokens) {
            if (token.isEmpty()) {
                sb.append(" ");
                continue;
            }
            String rev = new StringBuilder(token).reverse().toString();
            sb.append(Character.toUpperCase(rev.charAt(0)));
            if (rev.length() > 1)
                sb.append(rev.substring(1).toLowerCase());
            sb.append(" ");
        }
        return sb.toString().trim();
    }
}
