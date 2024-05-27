package nl.nerdygadgets.logistiek.util;

import nl.nerdygadgets.logistiek.util.web.HttpUtil;
import nl.nerdygadgets.logistiek.util.web.PackageStatus;
import nl.nerdygadgets.logistiek.util.web.WebHelper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasse om alle cache te beheren zoals de login token en route.
 */
public class CacheManager {

    private static WebHelper.WebToken token;

    private static Map<WebHelper.WebPackage, PackageStatus> packageStatuses = new HashMap<>();
    private static WebHelper.WebPackage currentPackage;
    private static WebHelper.WebDelivery currentDelivery;

    public static WebHelper.WebPackage getCurrentPackage() {
        return CacheManager.currentPackage;
    }

    public static void setCurrentPackage(WebHelper.WebPackage currentPackage) {
        CacheManager.currentPackage = currentPackage;
    }

    public static void clearCurrentPackage() {
        CacheManager.currentPackage = null;
    }

    public static void deliverPackage() throws IOException {
        if(!CacheManager.currentDelivery.packages.isEmpty()) {
            CacheManager.updatePackageStatus(CacheManager.currentPackage, PackageStatus.DELIVERED);
            HttpUtil.getRequest(new URL("https://api.nerdy-gadgets.nl/updatePackage?id=" + CacheManager.currentDelivery.id + "&status=DELIVERED&token=" + CacheManager.getToken().token + "&packageId=" + CacheManager.currentPackage.id));
            CacheManager.clearCurrentPackage();
            for(WebHelper.WebPackage p : CacheManager.currentDelivery.packages) {
                if(CacheManager.getPackageStatus(p) == PackageStatus.UNDELIVERED) {
                    CacheManager.setCurrentPackage(p);
                    CacheManager.updatePackageStatus(p, PackageStatus.IN_TRANSIT);
                    HttpUtil.getRequest(new URL("https://api.nerdy-gadgets.nl/updatePackage?id=" + CacheManager.currentDelivery.id + "&status=IN_TRANSIT&token=" + CacheManager.getToken().token + "&packageId=" + p.id));
                    break;
                }
            }
        }
    }
    public static void notHomePackage() throws IOException {
        CacheManager.updatePackageStatus(CacheManager.currentPackage, PackageStatus.NOT_HOME);
        HttpUtil.getRequest(new URL("https://api.nerdy-gadgets.nl/updatePackage?id=" + CacheManager.currentDelivery.id + "&status=NOT_HOME&token=" + CacheManager.getToken().token + "&packageId=" + CacheManager.currentPackage.id));
        CacheManager.clearCurrentPackage();
        for(WebHelper.WebPackage p : CacheManager.currentDelivery.packages) {
            if(CacheManager.getPackageStatus(p) == PackageStatus.UNDELIVERED) {
                CacheManager.setCurrentPackage(p);
                CacheManager.updatePackageStatus(p, PackageStatus.IN_TRANSIT);
                HttpUtil.getRequest(new URL("https://api.nerdy-gadgets.nl/updatePackage?id=" + CacheManager.currentDelivery.id + "&status=IN_TRANSIT&token=" + CacheManager.getToken().token + "&packageId=" + p.id));
                break;
            }
        }
    }

    public static void skipPackage() throws IOException {
        CacheManager.updatePackageStatus(CacheManager.currentPackage, PackageStatus.UNKNOWN);
        HttpUtil.getRequest(new URL("https://api.nerdy-gadgets.nl/updatePackage?id=" + CacheManager.currentDelivery.id + "&status=UNKNOWN&token=" + CacheManager.getToken().token + "&packageId=" + CacheManager.currentPackage.id));
        CacheManager.clearCurrentPackage();
        for(WebHelper.WebPackage p : CacheManager.currentDelivery.packages) {
            if(CacheManager.getPackageStatus(p) == PackageStatus.UNDELIVERED) {
                CacheManager.setCurrentPackage(p);
                CacheManager.updatePackageStatus(p, PackageStatus.IN_TRANSIT);
                HttpUtil.getRequest(new URL("https://api.nerdy-gadgets.nl/updatePackage?id=" + CacheManager.currentDelivery.id + "&status=IN_TRANSIT&token=" + CacheManager.getToken().token + "&packageId=" + p.id));
                break;
            }
        }
    }

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
