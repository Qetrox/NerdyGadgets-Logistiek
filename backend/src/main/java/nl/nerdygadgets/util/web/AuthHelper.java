package nl.nerdygadgets.util.web;

import nl.nerdygadgets.util.web.WebHelper;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class AuthHelper {

    private static List<WebHelper.WebToken> Tokens = new ArrayList<>();

    /**
     * Check of een token geldig is
     * @param token De token die gecheckt moet worden
     * @return Of de token geldig is
     */
    public static boolean checkToken(WebHelper.WebToken token) {
        return Tokens.contains(token);
    }

    public static WebHelper.WebToken getToken(String token) {
        for(WebHelper.WebToken t : Tokens) {
            if(t.token.equals(token)) {
                return t;
            }
        }
        return null;
    }

    public static boolean checkManager(WebHelper.WebToken token) {
        for(WebHelper.WebToken t : Tokens) {
            if(t.token.equals(token.token) && t.isManager) {
                return true;
            }
        }
        return false;
    }

    /**
     * Genereer een nieuwe token voor de API die bij de gebruiker hoort
     * @param userId Het ID van de gebruiker
     * @return De gegenereerde token
     */
    public static WebHelper.WebToken generateToken(Integer userId, boolean isManager) {
        String token = generateRandomString(64) + "." + userId.toString();
        WebHelper.WebToken t= new WebHelper.WebToken(token, isManager, userId);
        Tokens.add(t);
        return t;
    }

    /**
     * Genereer een random string van een bepaalde lengte
     * @param i De lengte van de string
     * @return De gegenereerde string
     */
    public static String generateRandomString(int i) {
        return RandomStringUtils.random(i, 0, 0, true, true, null, new SecureRandom());
    }

    /**
     * Verwijder een token uit de lijst
     * @param token De token die verwijderd moet worden
     * @return Of het verwijderen gelukt is
     */
    public static boolean removeToken(String token) {
        for(WebHelper.WebToken t : Tokens) {
            if(t.token.equals(token)) {
                Tokens.remove(t);
                return true;
            }
        }
        return false;
    }
}
