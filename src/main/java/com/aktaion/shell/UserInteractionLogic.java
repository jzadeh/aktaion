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
            CommandLineUtils.executeBroSimpleDebugLogic(fileInputPath);
        } else if (userChoice == 3) {
            //does not work on windows
            String localPath = CommandLineUtils.tryToFindPathToDataInSourceCode(4);

            String demoFileName = "test.pcap";
            String testFile = localPath + demoFileName;
            CommandLineUtils.executeBroSimpleDebugLogic(testFile);

            //guess where the weka data is
            String dataPath = CommandLineUtils.tryToFindPathToDataInSourceCode(4);

//            CommandLineUtils.crossValidationWekaRf(10.0d,
//                    trainData, "/Users/User/Aktaion/data/");

            String trainDirectory = dataPath + "proxyData/exploitData/";
            String trainData = "/Users/User/Aktaion/data/exploitData.arff";

            //Todo change to a seperate set of files to score
            String scoringData = "/Users/User/Aktaion/data/exploitData.arff";

            WekaUtilities.extractAndNormalizeDataIntoWekaFormat(trainDirectory,
                    scoringData,
                    ".webgateway",
                    ClassLabel.EXPLOIT(),
                    false, 5);

            RandomForestLogic.trainWekaRandomForest(trainData, scoringData, 10, 100);

            //   RandomForestLogic.scoreWekaRandomForest(outputData,10, 1000)

        }
    }
}
