package com.aktaion.shell;

import com.aktaion.ml.weka.randomforest.ClassLabel;
import com.aktaion.ml.weka.randomforest.RandomForestLogic;
import com.aktaion.ml.weka.randomforest.WekaUtilities;

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
        System.out.println("3: Demo (Unix/OS X System)");
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
            //CommandLineUtils.executeBroSimpleDebugLogic(fileInputPath);
        } else if (userChoice == 3) {

            //does not work on windows determines where the path to the Jar is
            String dataPath = CommandLineUtils.tryToFindPathToDataInSourceCode(4);

            /**
             * Step 1 (Only Need to Perform Once)
             *
             * Train a model by scoring a whole directory of files
             * in this case we have extracts from 300+ exploit samples
             * with a .webgateway extension
             */
            String trainDirectory = dataPath + "proxyData/exploitData/";
            String trainDataOutFileName = dataPath + "demoData/demoExploitData.arff";
            String saveModelFileName = dataPath + "demoData/model.test";

            WekaUtilities.extractDirectoryToWekaFormat(trainDirectory,
                    trainDataOutFileName,
                    ".webgateway",
                    ClassLabel.EXPLOIT(), 5);
            RandomForestLogic.trainWekaRandomForest(trainDataOutFileName, saveModelFileName, 10, 100);


            /**
             * Step 2 get a PCAP file to score and convert it to bro logs
             */

            String demoInputFileName = "demoData/demoExploitPcap.pcap"; //change to whatever pcap we want to score

            //   Comm.generateBroFiles(demoInputFileName);
            String broExploitExample = dataPath + "demoData/http.log";
            RandomForestLogic.scoreBroHttpFile(broExploitExample, saveModelFileName,5);

            /**
             * Step 3 extract a JSON to pass to GPO creation step
             */
        }
    }
}
