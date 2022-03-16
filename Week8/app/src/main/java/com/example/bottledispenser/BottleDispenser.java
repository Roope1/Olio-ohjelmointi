package com.example.bottledispenser;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BottleDispenser {
    // The array for the Bottle-objects
    private ArrayList<Bottle> Bottles = new ArrayList<>();
    private float money;

    private static BottleDispenser bd = null;

    private BottleDispenser() {
        System.out.println("Bottle dispenser created!");
        money = 0;

        Bottles.add(new Bottle("Pepsi Max", "Pepsi", 0.3f, 0.5f, 1.8f));
        Bottles.add(new Bottle("Pepsi Max", "Pepsi", 0.3f, 1.5f, 2.2f));
        Bottles.add(new Bottle("Coca-Cola Zero", "Coca-Cola", 0.3f, 0.5f, 2f));
        Bottles.add(new Bottle("Coca-Cola Zero", "Coca-Cola", 0.3f, 1.5f, 2.5f));
        Bottles.add(new Bottle("Fanta Zero", "Fanta", 0.3f, 0.5f, 1.95f));

    }

    public static BottleDispenser getInstance(){
        if(bd == null){
            bd = new BottleDispenser();
        }
        return bd;
    }

    public void addMoney(int amount) {
        money += amount;
        System.out.println("Klink! Added more money!");
    }

    public void buyBottle(int index) {
        if(Bottles.size() >= index -1) {
            if (money > Bottles.get(index - 1).getPrice()) {
                money -= Bottles.get(index - 1).getPrice();
                System.out.println("KACHUNK! "+ Bottles.get(index - 1).getName() +" came out of the dispenser!");
                Bottles.remove(index - 1);
            } else {
                System.out.println("Add money first!");
            }
        } else {
            System.out.println("No bottles left!");
        }
    }

    public void returnMoney() {
        System.out.printf("Klink klink. Money came out! You got %.2f€ back\n", money);
        money = 0;
    }

    public float getMoney(){
        return money;
    }

    public void showBottleList() {
        DecimalFormat format = new DecimalFormat("0.0#");
        for(int i = 0; i < Bottles.size(); i++){
            Bottle bottle = Bottles.get(i);
            System.out.printf("%d. Name: %s\n\tSize: %s\tPrice: %s\n", (i + 1), bottle.getName(),
                    format.format(bottle.getSize()), format.format(bottle.getPrice()));

        }
    }

}