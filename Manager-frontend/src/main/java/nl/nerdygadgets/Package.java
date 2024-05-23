package nl.nerdygadgets;

public class Package {

    private String adress;
    private String name;
    private long id;
    private double weight;
    private Size size;
    private boolean delivered;

    public Package(String adress, String name, long id, double weight, Size size) {
        this.adress = adress;
        this.name = name;
        this.id = id;
        this.weight = weight;
        this.size = size;
        this.delivered = false;
    }

    public Package(String adress, String name, long id, double weight, int width, int height, int depth) {
        this(adress, name, id, weight, new Size(width, height, depth));
    }

    public String getAdress() {
        return adress;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public Size getSize() {
        return size;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String toString() {
        return "Package #" + this.id + " Adress: " + this.adress + " Name: " + this.name + " Weight: " + this.weight + " size: " + this.size.toString();
    }

}
