package com.example.bottledispenser;

import java.util.Locale;
import java.util.Scanner;

public class Mainclass {

    public static void main(String[] args){
        BottleDispenser bd = BottleDispenser.getInstance();
        Scanner sc = new Scanner(System.in);
        int choice;

        while(true){
            showMenu();
            choice = sc.nextInt();

            if(choice == 1) {
                bd.addMoney(1);
            } else if (choice == 2) {
                bd.showBottleList();
                System.out.print("Your choice: ");
                choice = sc.nextInt();
                bd.buyBottle(choice);
            } else if (choice == 3) {
                bd.returnMoney();
            } else if (choice == 4) {
                bd.showBottleList();
            } else if (choice == 0) {
                break;
            } else {
                System.out.println("Unknown choice!");
            }
        }
    }

    private static void showMenu(){
        System.out.print("\n*** BOTTLE DISPENSER ***\n" +
                "1) Add money to the machine\n" +
                "2) Buy a bottle\n" +
                "3) Take money out\n" +
                "4) List bottles in the dispenser\n" +
                "0) End\n" +
                "Your choice: ");
    }
}
