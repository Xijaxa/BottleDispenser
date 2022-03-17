package com.example.bottledispenser;

import java.util.ArrayList;

public class BottleDispenser {
    private int bottles;
    private ArrayList<Bottle> bottle_array;
    private double money;

    private static BottleDispenser bd = null;

    public static BottleDispenser getInstance() {
        if (bd == null) {
            bd = new BottleDispenser();
        }
        return bd;
    }

    public int bottleCount() {
        return bottles;
    }

    private BottleDispenser() {
        bottles = 10;
        money = 0;

        bottle_array = new ArrayList<Bottle>();
        String n, m;
        double e, s, p;

        for(int i = 0; i<bottles; i++) {
            if(i<4) {
                n = "Pepsi Max";
                m = "Pepsi";
                e = 0.3;
                if(i<2) {
                    s = 0.5;
                    p = 1.80;
                }
                else {
                    s = 1.5;
                    p = 2.20;
                }
            }
            else if(i < 8) {
                n = "Coca-Cola Zero";
                m = "Coca-Cola";
                e = 0.4;
                if(i<6) {
                    s = 0.5;
                    p = 2.00;
                }
                else {
                    s = 1.5;
                    p = 2.50;
                }
            }
            else {
                n = "Fanta Zero";
                m = "Coca-Cola";
                e = 0.5;
                if(i < 9) {
                    s = 0.5;
                    p = 1.95;
                } else {
                    s = 1.5;
                    p = 2.35;
                }
            }
            bottle_array.add(i, new Bottle(n, m, e, s, p));
        }
    }

    public void addMoney(int i) {
        money += i;
        System.out.println("Klink! Added more money!");
    }

    public int buyBottle2(int b_index) {
        Bottle b;
        int i, j = 0;
        double size = 0;
        String name = "";
        if(b_index == 1) {
            name = "Pepsi Max";
            size = 0.5;
        } else if (b_index == 2) {
            name = "Pepsi Max";
            size = 1.5;
        } else if (b_index == 3) {
            name = "Coca-Cola";
            size = 0.5;
        } else if (b_index == 4) {
            name = "Coca-Cola";
            size = 1.5;
        } else if (b_index == 5) {
            name = "Fanta Zero";
            size = 0.5;
        } else if (b_index == 6) {
            name = "Fanta Zero";
            size = 1.5;
        }
        for(i = 0; i < bottles; i++) {
            b = bottle_array.get(i);
            if((name.equals(b.getName()) && b.getSize() == size)) {
                if(b.getPrize() > money) {
                    j = 2;
                    break;
                } else {
                    bottles -= 1;
                    money -= b.getPrize();
                    System.out.println("KACHUNK! " + b.getName() + " came out of the dispenser!");
                    deleteBottle(b);
                    j = 1;
                    break;
                }

            }
        }
        System.out.println("j: " + j);
        return j;
    }

    public ArrayList listBottles() {
        for(int i = 0; i < bottles; i++) {
            System.out.println(i+1 + ". Name: " + bottle_array.get(i).getName());
            System.out.print("\tSize: " + bottle_array.get(i).getSize());
            System.out.println("\tPrice: €" + bottle_array.get(i).getPrize());
        }
        return bottle_array;
    }

    public double getMoney() {
        return money;
    }

    private void deleteBottle(Bottle b) {
        bottle_array.remove(b);
    }

    public void returnMoney() {
        System.out.printf("Klink klink. Money came out! You got %.2f€ back\n", money);
        money = 0;
    }
}


