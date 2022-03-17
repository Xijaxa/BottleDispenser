package com.example.bottledispenser;

public class Bottle {
    private final String name;
    private final String manufacturer;
    private final double total_energy;
    private final double size;
    private final double prize;


    public Bottle(String n, String m, double t, double s, double p) {
        name = n;
        manufacturer = m;
        total_energy = t;
        size = s;
        prize = p;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public double getEnergy() {
        return total_energy;
    }

    public double getSize() {
        return size;
    }

    public double getPrize() {
        return prize;
    }
}
