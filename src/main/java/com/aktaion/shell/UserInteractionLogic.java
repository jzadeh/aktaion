package com.aktaion.shell;

import java.io.File;
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

        System.out.print("Enter execution choice [1-3]: ");

        int userChoice = scanner.nextInt();

        if (userChoice == 1) {
            System.out.print("Specify Input Path: ");
            String fileInputPath = scanner.next();
            System.out.print("Specify Output Path: ");
            String fileOutputPath = scanner.next();
//            BroUserInputLogic broLogic = new BroUserInputLogic(fileInputPath);
//
//            PcapUserInputLogic pcapInputLogic = new PcapUserInputLogic(fileInputPath);

            //execute main transform
        } else if (userChoice == 2) {
            System.out.print("Specify Input PCAP Path: ");
            String fileInputPath = scanner.next();
            // System.out.print("Specify Output Path: ");
            // String fileOutputPath = scanner.next();


            CommandLineUtils.executeBroLogic(fileInputPath);

//            BroUserInputLogic broLogic = new BroUserInputLogic(fileInputPath);
//
//            if (broLogic.output() == true) {
//
//                //parse bro lo
//
//                    File jarPath = new File(UserInteractionLogic.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//                    String broPath = jarPath.getParentFile().getAbsolutePath();
//                    String broHttpFile = broPath + "/http.log";
//
//                    System.out.println(" Bro HTTP FilePath" + broPath);
//
//                    String[] broHttpData = CommandLineUtils.getFileFromFileSystemPath(broHttpFile);
//                    System.out.println(" File Length" + broHttpData.length);
//
//                    CommandLineUtils.debugBroArray(broHttpData);
//
//                //    PcapUserInputLogic pcapInputLogic = new PcapUserInputLogic(fileInputPath);
//
//            }


        } else if (userChoice == 3){

            CommandLineUtils.executeBroLogic("/Users/User/Aktaion/test.pcap");


        }

    }
}
