import java.io.IOException;
import java.io.InputStream;

public class test {
    public static void main(String[] args) throws IOException {
        InputStream is = System.in;
        while (true) {
            try {
                int ch = is.read();
                if (ch == -1 || ch == 'q') {
                    break;
                }
                System.out.println((char) ch);
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        }
    }
}
