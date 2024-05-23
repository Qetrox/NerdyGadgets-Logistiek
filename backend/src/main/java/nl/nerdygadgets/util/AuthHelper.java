package nl.nerdygadgets.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class AuthHelper {

    private static Map<Integer, String> Tokens = new HashMap<>();

    /**
     * Check of een token geldig is
     * @param token De token die gecheckt moet worden
     * @return Of de token geldig is
     */
    public static boolean checkToken(String token) {
        if(Tokens.containsValue(token)) {
            try {
                Integer id = Integer.parseInt(token.split("\\.")[1]);
                return Tokens.get(id).equals(token);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Genereer een nieuwe token voor de API die bij de gebruiker hoort
     * @param userId Het ID van de gebruiker
     * @return De gegenereerde token
     */
    public static String generateToken(Integer userId) {
        String token = generateRandomString(64) + "." + userId.toString();
        Tokens.put(userId, token);
        return token;
    }

    /**
     * Genereer een random string van een bepaalde lengte
     * @param i De lengte van de string
     * @return De gegenereerde string
     */

    private static String generateRandomString(int i) {
        return RandomStringUtils.random(i, 0, 0, true, true, null, new SecureRandom());
    }

    /**
     * Verwijder een token uit de lijst
     * @param token De token die verwijderd moet worden
     * @return Of het verwijderen gelukt is
     */
    public static boolean removeToken(String token) {
        if(Tokens.containsValue(token)) {
            try {
                Integer id = Integer.parseInt(token.split("\\.")[1]);
                Tokens.remove(id);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

}
