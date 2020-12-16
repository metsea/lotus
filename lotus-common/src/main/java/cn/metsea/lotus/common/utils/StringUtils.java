package cn.metsea.lotus.common.utils;

/**
 * String Utils
 */
public class StringUtils {

    public static final String EMPTY = "";

    private StringUtils() {
        throw new UnsupportedOperationException("Construct StringUtils");
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isBlank(String s) {
        if (isEmpty(s)) {
            return true;
        }
        return s.trim().length() == 0;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    public static String replaceNRTtoUnderline(String src) {
        return src.replaceAll("[\n|\r|\t]", "_");
    }
}
