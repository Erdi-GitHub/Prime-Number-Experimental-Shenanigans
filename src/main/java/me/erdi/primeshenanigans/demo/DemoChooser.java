package me.erdi.primeshenanigans.demo;

import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class DemoChooser {
    public static void main(String[] args) {
        System.out.println("1 - Graphical Demo");
        System.out.println("2 - Performance Demo");

        handleInput();
    }

    private static void handleInput() {
        System.out.print("Choose app: ");

        char choice = new Scanner(System.in).next().charAt(0);

        switch(choice) {
            case '1':
                clearCon();

                System.out.println("Opening graphical demo...");
                System.out.println("Note: the graphical demo has a manual 0.75s delay between each iteration, else it just does everything before you can even blink.");

                GraphicalDemo.main(new String[] {});
                return;
            case '2':
                clearCon();

                System.out.println("Executing performance demo...");
                PerformanceDemo.main(new String[] {});

                return;
        }

        System.err.println();
        System.err.println("Invalid app!");
        System.err.flush();

        handleInput();
    }
    private static void clearCon() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
