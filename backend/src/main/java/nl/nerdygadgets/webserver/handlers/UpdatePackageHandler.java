package nl.nerdygadgets.webserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import nl.nerdygadgets.Main;
import nl.nerdygadgets.util.DatabaseConnector;
import nl.nerdygadgets.util.EmailUtil;
import nl.nerdygadgets.util.web.AuthHelper;
import nl.nerdygadgets.util.delivery.DeliveryRoutes;
import nl.nerdygadgets.util.delivery.PackageStatus;
import nl.nerdygadgets.util.web.WebHelper;

import java.io.IOException;
import java.sql.*;
import java.util.Map;

public class UpdatePackageHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if(!WebHelper.handleTokenRequirement(exchange)) {
            return;
        }

        System.out.println("UpdatePackageHandler");

        Map<String, String> params = WebHelper.queryToMap(exchange.getRequestURI().getQuery());

        WebHelper.WebToken token = AuthHelper.getToken(params.get("token"));
        assert token != null;

        //params.forEach((k, v) -> System.out.println(k + " : " + v));

        try {

            long PackageId = Long.parseLong(params.get("packageId"));
            long DeliveryId = Long.parseLong(params.get("id"));
            String status = params.get("status");
            PackageStatus packageStatus = PackageStatus.valueOf(status);

            WebHelper.WebDelivery route = DeliveryRoutes.getRoute(token.id);

            System.out.println(route);

            if(route == null) {
                exchange.sendResponseHeaders(404, 0);
                exchange.getResponseBody().close();
                return;
            }

            System.out.println("DeliveryId: " + DeliveryId);
            System.out.println("PackageId: " + PackageId);

            if(DeliveryId == route.id) {
                System.out.println("DeliveryId matches");

                for (WebHelper.WebPackage p : route.packages) {
                    if(p.id == PackageId) {
                        System.out.println("PackageId matches");
                        if(packageStatus == PackageStatus.DELIVERED || packageStatus == PackageStatus.NOT_HOME || packageStatus == PackageStatus.UNKNOWN) {
                            route.packages.remove(p);
                        }
                        if(route.packages.isEmpty()) {
                            DeliveryRoutes.removeRoute(route);
                        }
                        sendDeliveryEmail(packageStatus, p.userId, p);
                        System.out.println("Package status updated: " + packageStatus);

                        exchange.sendResponseHeaders(200, 0);
                        exchange.getResponseBody().close();
                        break;
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(400, 0);
            exchange.getResponseBody().close();

        }
    }
    private static void sendDeliveryEmail(PackageStatus packageStatus, int userId, WebHelper.WebPackage pack) throws SQLException {

        if(packageStatus == PackageStatus.DELIVERED) {
            //update in order database
            DatabaseConnector conn = Main.getDatabaseConnection();
            Connection connection = conn.getConnection();

            PreparedStatement stmt = connection.prepareStatement("UPDATE `order` SET delivered = 1 WHERE id = ?");
            System.out.println(pack.id);
            stmt.setInt(1, pack.id);
            stmt.executeUpdate();
        }

        DatabaseConnector conn = Main.getDatabaseConnection();
        Connection connection = conn.getConnection();

        PreparedStatement stmt = connection.prepareStatement("SELECT email FROM user WHERE id = ?");
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            String email = rs.getString("email");

            String message = "";
            String subject = "";
            String time = new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date());

            switch(packageStatus) {
                case DELIVERED:
                    message = "We hebben je pakket #" + pack.id + " om " + time + " bij je bezorgd.\n\nBezorgadres:\n" + pack.address + "\n\n\nMet vriendelijke groet,\nNerdyGadgets";
                    subject = "Je pakket #" + pack.id + " is bezorgd!";
                    break;
                case NOT_HOME:
                    message = "We hebben je pakket #" + pack.id + " niet kunnen bezorgen. We proberen het later nog een keer.\n\nBezorgadres:\n" + pack.address + "\n\n\nMet vriendelijke groet,\nNerdyGadgets";
                    subject = "Je pakket #" + pack.id + " hebben we niet kunnen bezorgen!";
                    break;
                case UNKNOWN:
                    message = "We hebben je pakket #" + pack.id + " niet kunnen bezorgen vanwege redenen. Neem contact met ons op.\n\nBezorgadres:\n" + pack.address + "\n\n\nMet vriendelijke groet,\nNerdyGadgets";
                    subject = "Je pakket #" + pack.id + " hebben we niet kunnen bezorgen!";
                    break;
                case IN_TRANSIT:
                    message = "We zijn onderweg naar je bezorgadres met je pakket #" + pack.id + "!\n\nBezorgadres:\n" + pack.address + "\n\n\nMet vriendelijke groet,\nNerdyGadgets";
                    subject = "Je pakket #" + pack.id + " is onderweg!";
                    break;
            }


            EmailUtil.sendEmail(email, subject, message);
            System.out.println("Email sent to: " + email);
        }
    }
}
