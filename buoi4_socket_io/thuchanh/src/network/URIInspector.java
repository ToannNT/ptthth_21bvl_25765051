package network;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class URIInspector {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java network.URIInspector <uri>");
            System.out.println("Vi du: java network.URIInspector https://example.com:8080/path?q=1");
            return;
        }

        String input = args[0];

        // Phan tich URI
        URI uri;
        try {
            uri = new URI(input);
        } catch (URISyntaxException e) {
            System.err.println("URI khong hop le: " + e.getMessage());
            return;
        }

        System.out.println("=== Phan tich URI ===");
        System.out.println("URI day du  : " + uri);
        System.out.println("Scheme      : " + nullSafe(uri.getScheme()));
        System.out.println("UserInfo    : " + nullSafe(uri.getUserInfo()));
        System.out.println("Host        : " + nullSafe(uri.getHost()));
        System.out.println("Port        : " + (uri.getPort() == -1 ? "(mac dinh)" : uri.getPort()));
        System.out.println("Path        : " + nullSafe(uri.getPath()));
        System.out.println("Query       : " + nullSafe(uri.getQuery()));
        System.out.println("Fragment    : " + nullSafe(uri.getFragment()));
        System.out.println();

        // Phan giai host bang InetAddress
        String host = uri.getHost();
        if (host == null || host.isEmpty()) {
            System.err.println("URI khong co phan host de phan giai.");
            return;
        }

        System.out.println("=== Phan giai Host: " + host + " ===");
        try {
            InetAddress[] addresses = InetAddress.getAllByName(host);
            System.out.println("So dia chi tim duoc: " + addresses.length);
            for (InetAddress addr : addresses) {
                String ipVersion = (addr instanceof Inet4Address) ? "IPv4"
                        : (addr instanceof Inet6Address) ? "IPv6" : "Unknown";
                System.out.println("- IP        : " + addr.getHostAddress() + "  [" + ipVersion + "]");
                System.out.println("  Canonical : " + addr.getCanonicalHostName());
                System.out.println("  Loopback  : " + addr.isLoopbackAddress());
                System.out.println("  Site-local: " + addr.isSiteLocalAddress());
                System.out.println();
            }
        } catch (UnknownHostException e) {
            System.err.println("Khong phan giai duoc host: " + host);
        }
    }

    private static String nullSafe(String s) {
        return s == null ? "(khong co)" : s;
    }
}