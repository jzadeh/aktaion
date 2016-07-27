package com.aktaion.shell;

import java.util.Scanner;

public class UserInteractionLogic {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*************************");
        System.out.println("*************************");
        System.out.println("** Aktaion Version 0.1 **");
        System.out.println("*************************");
        System.out.println("*************************");
        System.out.println("1: Analyze Bro HTTP Sample");
        System.out.println("2: Analyze PCAP Sample (Bro must be installed)");
        System.out.println("3: Demo");

        System.out.print("Enter Execution Choice [1-3]: ");

        int userChoice = scanner.nextInt();

        if (userChoice == 1) {
            System.out.print("Specify Input Path: ");
            String fileInputPath = scanner.next();
            System.out.print("Specify Output Path: ");
            String fileOutputPath = scanner.next();
        } else if (userChoice == 2) {
            System.out.print("Specify Input PCAP Path: ");
            String fileInputPath = scanner.next();
            CommandLineUtils.executeBroLogic(fileInputPath);
        } else if (userChoice == 3) {
            CommandLineUtils.executeBroLogic("/Users/rsoto/Aktaion/test.pcap");
        }

    }
}
