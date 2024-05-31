package nl.nerdygadgets.logistiek.util;

public class WebHelper {

    public static class WebRetour {
        public int retourId;
        public String resolutionType;
        public boolean handled;
        public String returnReason;
        public int orderId;
        public long created;
        public String name;

        public WebRetour(int retourId, String resolutionType, boolean handled, String returnReason, int orderId, long created, String name) {
            this.retourId = retourId;
            this.resolutionType = resolutionType;
            this.handled = handled;
            this.returnReason = returnReason;
            this.orderId = orderId;
            this.created = created;
            this.name = name;
        }
    }

    public static class WebToken {
        public int id;
        public boolean isManager;

        public WebToken(int id, boolean isManager) {
            this.id = id;
            this.isManager = isManager;
        }
    }
}
