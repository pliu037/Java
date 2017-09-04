package mesosphere_2016;

import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.Scanner;

public class Simulation {

    public ElevatorControlSystem ecs;
    private int minFloor = 1;
    private int maxFloor = 20;
    private Scanner scanner;

    public Simulation() {
        Injector injector = Guice.createInjector(new SimpleModule());
        ecs = injector.getInstance(ElevatorControlSystem.class);
        scanner = new Scanner(System.in);
    }

    public void run(String[] args) {
        if (validArgs(args)) {
            int numElevators = Integer.parseInt(args[0]);
            ecs.setProperties(numElevators, minFloor, maxFloor);
        }

        boolean cont = true;
        while(cont) {
            printMenu();
            char command = getChar();
            switch (command) {
                case 'A':
                    addFloorRequest();
                    break;
                case 'B':
                    checkElevatorStatus();
                    break;
                case 'C':
                    ecs.simulateNextMove();
                    break;
                default:
                    cont = false;
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("Please select one of the following options:");
        System.out.println("A) Add a floor request");
        System.out.println("B) Check the status of an elevator");
        System.out.println("C) Step forward");
        System.out.println("D) Quit");
    }

    private char getChar() {
        while(true) {
            String s = scanner.nextLine();
            if (s.length() != 1) {
                System.out.println("A single character please.");
                continue;
            }
            char c = Character.toUpperCase(s.charAt(0));
            if ("ABCD".indexOf(c) != -1) {
                return c;
            }
        }
    }

    private void addFloorRequest() {
        System.out.println("Please enter two integers between " + minFloor + " and " + maxFloor + ", the first being " +
                           "the request floor and the second being the target floor, separated by a space.");
        int requestFloor, targetFloor;
        try {
            String s = scanner.nextLine();
            String[] parsed = s.split(" ");
            if (parsed.length == 2) {
                requestFloor = Integer.parseInt(parsed[0]);
                targetFloor = Integer.parseInt(parsed[1]);
            } else {
                System.out.println("Two integers separated by a space please.");
                return;
            }
        } catch (NumberFormatException ex) {
            System.out.println("Two integers separated by a space please.");
            return;
        }
        ecs.requestAtFloor(requestFloor, targetFloor);
    }

    private void checkElevatorStatus() {
        System.out.println("Please enter the id of the elevator you'd like to check.");
        int id;
        try {
            String s = scanner.nextLine();
            id = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            System.out.println("An integer please.");
            return;
        }
        ecs.printElevatorStats(id);
    }

    private boolean validArgs(String[] args) {
        if (args.length == 3) {
            try {
                Integer.parseInt(args[0]);
                minFloor = Integer.parseInt(args[1]);
                maxFloor = Integer.parseInt(args[2]);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Simulation s = new Simulation();
        s.run(args);
    }
}
