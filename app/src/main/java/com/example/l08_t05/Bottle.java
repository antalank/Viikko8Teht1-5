package com.example.l08_t05;

public class Bottle {
    private int ide;
    private String name = "Pepsi Max";
    private String manufacturer = "Pepsi";
    private float total_energy = 0.3f;
    private float price = 1.80f;
    private float size = 0.5f;

    protected Bottle(int ide, String name, String manufacturer, float total_energy, float price, float size) {
        this.ide = ide;
        this.name = name;
        this.manufacturer = manufacturer;
        this.total_energy = total_energy;
        this.price = price;
        this.size = size;

    }

    public String getName() {
        return name;
    }

    public int getIde() {return ide; }

    public String getManufacturer() {
        return manufacturer;
    }

    public float getEnergy() {
        return total_energy;
    }

    public float getPrice() {
        return price;
    }

    public float getSize() {return size;}

    public void printBottles() {

        System.out.println("Nimi: " + name);
        System.out.println("    Koko: " + size + "  Hinta: " + price);

    }

    @Override
    public String toString() {
        return name;
    }
}
