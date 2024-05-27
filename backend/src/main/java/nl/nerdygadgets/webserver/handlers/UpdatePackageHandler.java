package nl.nerdygadgets.webserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import nl.nerdygadgets.util.AuthHelper;
import nl.nerdygadgets.util.DeliveryRoutes;
import nl.nerdygadgets.util.PackageStatus;
import nl.nerdygadgets.util.WebHelper;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class UpdatePackageHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if(!WebHelper.handleTokenRequirement(exchange)) {
            return;
        }

        Map<String, String> params = WebHelper.queryToMap(exchange.getRequestURI().getQuery());

        WebHelper.WebToken token = AuthHelper.getToken(params.get("token"));
        assert token != null;

        try {

            int id = Integer.parseInt(params.get("id"));
            String status = params.get("status");
            PackageStatus packageStatus = PackageStatus.valueOf(status);

            WebHelper.WebDelivery route = DeliveryRoutes.getRoute(token.id);

            if(route == null) {
                exchange.sendResponseHeaders(404, 0);
                exchange.getResponseBody().close();
                return;
            }

            if(id == route.id) {

                for (WebHelper.WebPackage p : route.packages) {
                    if(p.id == id) {
                        if(packageStatus == PackageStatus.DELIVERED || packageStatus == PackageStatus.NOT_HOME || packageStatus == PackageStatus.UNKNOWN) {
                            route.packages.remove(p);
                        }
                        if(route.packages.isEmpty()) {
                            DeliveryRoutes.removeRoute(route);
                        }
                        sendDeliveryEmail(packageStatus);

                        exchange.sendResponseHeaders(200, 0);
                        exchange.getResponseBody().close();
                        break;
                    }
                }

            }

        } catch (Exception e) {
            exchange.sendResponseHeaders(400, 0);
            exchange.getResponseBody().close();

        }
    }
    private static void sendDeliveryEmail(PackageStatus packageStatus) {
        System.out.println("Email sent");
        System.out.println("Package status: " + packageStatus);
    }
}
