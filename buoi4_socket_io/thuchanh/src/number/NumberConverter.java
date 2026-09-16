package number;

/**
 * Chuyen so nguyen thanh chuoi chu tieng Viet.
 * Ho tro pham vi: -999,999,999 den 999,999,999
 */
public class NumberConverter {

    // Tu 0-9
    private static final String[] UNITS = {
        "kh\u00f4ng", "m\u1ed9t", "hai", "ba", "b\u1ed1n",
        "n\u0103m", "s\u00e1u", "b\u1ea3y", "t\u00e1m", "ch\u00edn"
    };

    /**
     * Chuyen so n thanh chuoi chu tieng Viet.
     *
     * @param n so nguyen can chuyen
     * @return chuoi chu tuong ung
     */
    public static String convert(long n) {
        if (n < 0) {
            return "\u00e2m " + convert(-n);
        }
        if (n == 0) {
            return "kh\u00f4ng";
        }
        return convertPositive(n).trim();
    }

    private static String convertPositive(long n) {
        if (n == 0) return "";

        // 1-9
        if (n < 10) return UNITS[(int) n];

        // 10-19
        if (n < 20) {
            int units = (int) (n % 10);
            if (units == 0) return "m\u01b0\u1eddi";
            if (units == 5) return "m\u01b0\u1eddi l\u0103m";
            return "m\u01b0\u1eddi " + UNITS[units];
        }

        // 20-99
        if (n < 100) {
            int tens  = (int) (n / 10);
            int units = (int) (n % 10);
            String tensStr = UNITS[tens] + " m\u01b0\u01a1i";
            if (units == 0) return tensStr;
            if (units == 1) return tensStr + " m\u1ed1t";
            if (units == 5) return tensStr + " l\u0103m";
            return tensStr + " " + UNITS[units];
        }

        // 100-999
        if (n < 1_000) {
            int  hundreds = (int) (n / 100);
            long rest     = n % 100;
            String result = UNITS[hundreds] + " tr\u0103m";
            if (rest == 0)   return result;
            if (rest < 10)   return result + " l\u1ebb " + UNITS[(int) rest];
            return result + " " + convertPositive(rest);
        }

        // 1,000 - 999,999
        if (n < 1_000_000) {
            long thousands = n / 1_000;
            long rest      = n % 1_000;
            String result  = convertPositive(thousands) + " ngh\u00ecn";
            if (rest == 0)        return result;
            if (rest < 100)       return result + " kh\u00f4ng tr\u0103m " + convertPositive(rest);
            return result + " " + convertPositive(rest);
        }

        // 1,000,000 - 999,999,999
        if (n < 1_000_000_000) {
            long millions = n / 1_000_000;
            long rest     = n % 1_000_000;
            String result = convertPositive(millions) + " tri\u1ec7u";
            if (rest == 0)          return result;
            if (rest < 100_000)     return result + " kh\u00f4ng tr\u0103m " + convertPositive(rest);
            return result + " " + convertPositive(rest);
        }

        return String.valueOf(n); // out of range fallback
    }
}