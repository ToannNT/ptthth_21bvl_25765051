package network;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Bai 4.1 - Khao sat dia chi mang
 *
 * Su dung InetAddress de phan giai hostname va in cac thong tin:
 * - Dia chi IP
 * - Canonical hostname
 * - Loopback / Site-local
 * - Loai dia chi: IPv4 hay IPv6 <- bo sung theo yeu cau
 *
 * Ghi chu ve Loopback vs Site-local:
 * - Loopback : dia chi noi bo may (127.0.0.1 hoac ::1). Goi tin khong roi
 * khoi card mang, chi dung de giao tiep trong cung mot may.
 * - Site-local: dia chi mang rieng (private) dung trong noi bo to chuc,
 * vi du 192.168.x.x, 10.x.x.x, 172.16-31.x.x (IPv4) hoac
 * fec0::/10 (IPv6 cu). Goi tin khong duoc dinh tuyen ra Internet.
 *
 * Luu y: phan loai lop A/B/C la kien thuc lich su (classful networking),
 * khong con duoc ap dung trong dinh tuyen hien dai (CIDR tu 1993).
 *
 * Bien dich va chay:
 * javac -d out src/network/HostInspector.java
 * java -cp out network.HostInspector localhost
 * java -cp out network.HostInspector example.com
 * java -cp out network.HostInspector host-khong-ton-tai.invalid
 */
public class HostInspector {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java network.HostInspector <hostname>");
            return;
        }

        try {
            InetAddress[] addresses = InetAddress.getAllByName(args[0]);
            System.out.println("Host: " + args[0]);
            System.out.println("So dia chi tim duoc: " + addresses.length);
            System.out.println("----------------------------------------");

            for (InetAddress address : addresses) {
                String ipVersion;
                if (address instanceof Inet4Address) {
                    ipVersion = "IPv4";
                } else if (address instanceof Inet6Address) {
                    ipVersion = "IPv6";
                } else {
                    ipVersion = "Unknown";
                }

                System.out.println("- IP        : " + address.getHostAddress()
                        + "  [" + ipVersion + "]");
                System.out.println("  Canonical : " + address.getCanonicalHostName());
                System.out.println("  Loopback  : " + address.isLoopbackAddress()
                        + "   // giao tiep noi bo trong cung may");
                System.out.println("  Site-local: " + address.isSiteLocalAddress()
                        + "  // dia chi mang rieng (private), khong ra Internet");
                System.out.println();
            }

        } catch (UnknownHostException e) {
            System.err.println("Khong phan giai duoc host: " + args[0]);
        }
    }
}
