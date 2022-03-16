public class Bottle {
    private String name;
    private String manufacturer;
    private double total_energy;
    private double size;
    private double price;

    public Bottle(){
        name = "Pepsi Max";
        manufacturer = "Pepsi";
        total_energy = 0.3;
        size = 0.5;
        price = 1.80;
    }

    public Bottle(String name, String manuf, float totE, float size, float price){
        this.name = name;
        manufacturer = manuf;
        total_energy = totE;
        this.size = size;
        this.price = price;
    }

    public String getName(){
        return name;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public double getSize(){
        return size;
    }

    public double getEnergy(){
        return total_energy;
    }
}