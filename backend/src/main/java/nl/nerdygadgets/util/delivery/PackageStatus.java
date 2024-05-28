package nl.nerdygadgets.util.delivery;

public enum PackageStatus {

    DELIVERED,
    UNDELIVERED,
    IN_TRANSIT,
    NOT_HOME,
    UNKNOWN;

    public static PackageStatus fromString(String status) {
        switch (status) {
            case "DELIVERED":
                return DELIVERED;
            case "UNDELIVERED":
                return UNDELIVERED;
            case "IN_TRANSIT":
                return IN_TRANSIT;
            default:
                return UNKNOWN;
        }
    }

    public String toString() {
        switch (this) {
            case DELIVERED:
                return "DELIVERED";
            case UNDELIVERED:
                return "UNDELIVERED";
            case IN_TRANSIT:
                return "IN_TRANSIT";
            default:
                return "UNKNOWN";
        }
    }

}
