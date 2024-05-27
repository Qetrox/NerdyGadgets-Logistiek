package nl.nerdygadgets.logistiek.util;

import nl.nerdygadgets.logistiek.util.web.PackageStatus;
import nl.nerdygadgets.logistiek.util.web.WebHelper;

import java.util.Map;

/**
 * Klasse om alle cache te beheren zoals de login token en route.
 */
public class CacheManager {

    private static WebHelper.WebToken token;

    private static Map<WebHelper.WebPackage, PackageStatus> packageStatuses;
    private static WebHelper.WebDelivery currentDelivery;

    public static void setPackageStatuses(Map<WebHelper.WebPackage, PackageStatus> packageStatuses) {
        CacheManager.packageStatuses = packageStatuses;
    }

    public static Map<WebHelper.WebPackage, PackageStatus> getPackageStatuses() {
        return CacheManager.packageStatuses;
    }

    public static void clearPackageStatuses() {
        CacheManager.packageStatuses = null;
    }

    public static void updatePackageStatus(WebHelper.WebPackage p, PackageStatus status) {
        CacheManager.packageStatuses.put(p, status);
    }

    public static PackageStatus getPackageStatus(WebHelper.WebPackage p) {
        return CacheManager.packageStatuses.get(p);
    }

    public static void clearPackageStatus(WebHelper.WebPackage p) {
        CacheManager.packageStatuses.remove(p);
    }

    public static WebHelper.WebDelivery getCurrentDelivery() {
        return CacheManager.currentDelivery;
    }

    public static void setCurrentDelivery(WebHelper.WebDelivery currentDelivery) {
        CacheManager.currentDelivery = currentDelivery;
    }

    public static void clearCurrentDelivery() {
        CacheManager.currentDelivery = null;
    }

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
