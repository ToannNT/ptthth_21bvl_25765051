import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Client chat - kết nối đến server, chọn phòng và chat.
 */
public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Đã kết nối đến server!");

            // Thread riêng để nhận tin nhắn từ server
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Mất kết nối với server.");
                }
            });
            receiveThread.setDaemon(true);
            receiveThread.start();

            // Gửi input từ bàn phím đến server
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                out.println(input);
                if (input.trim().equalsIgnoreCase("/exit")) {
                    System.out.println("Đã ngắt kết nối.");
                    break;
                }
            }

        } catch (ConnectException e) {
            System.out.println("Không thể kết nối đến server. Hãy chắc chắn server đang chạy!");
        } catch (IOException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
