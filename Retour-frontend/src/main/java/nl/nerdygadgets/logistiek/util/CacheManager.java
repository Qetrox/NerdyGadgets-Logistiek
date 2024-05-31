package nl.nerdygadgets.logistiek.util;

public class CacheManager {

    private static WebHelper.WebToken token;

    public static void setToken(WebHelper.WebToken token) {
        CacheManager.token = token;
    }

    public static WebHelper.WebToken getToken() {
        return CacheManager.token;
    }

    public static void clearToken() {
        CacheManager.token = null;
    }

    public static boolean hasToken() {
        return CacheManager.token != null;
    }

    public static boolean isManager() {
        return CacheManager.token.isManager;
    }

    public static int getUserId() {
        return CacheManager.token.id;
    }
}
