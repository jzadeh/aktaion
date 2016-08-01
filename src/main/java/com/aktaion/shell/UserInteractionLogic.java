package com.aktaion.shell;

import com.aktaion.ml.weka.randomforest.ClassLabel;
import com.aktaion.ml.weka.randomforest.RandomForestLogic;
import com.aktaion.ml.weka.randomforest.WekaUtilities;
import scala.Option;

import java.util.Scanner;

public class UserInteractionLogic {

    private static void pressAnyKeyToContinue() {
        System.out.println("Press any key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

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
        } else if (userChoice == 3) {
            //does not work on windows determines where the path to the Jar is
            String dataPath = CommandLineUtils.tryToFindPathToDataInSourceCode(4);

            /**
             * Step 1: (Only Need to Perform Once)
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
            pressAnyKeyToContinue();

            /**
             * Step 2: get a PCAP file to score and convert it to bro logs
             */
            String demoInputFileName = dataPath + "demoData/demoExploitPcap.pcap"; //change to whatever pcap we want to score
            String extractedFile = CommandLineUtils.extractBroFilesFromPcap(demoInputFileName);
            pressAnyKeyToContinue();

            /**
             *  Step 3a: Score Malicious File Extract the IOCs from the scored file
             */
            Option<RandomForestLogic.IocsExtracted> output = RandomForestLogic.scoreBroHttpFile(extractedFile, saveModelFileName, 5);
            pressAnyKeyToContinue();

            /**
             * Step 3b: Score Benign Traffic File
             */
            //  RandomForestLogic.scoreBroHttpFile(dataPath + "demoData/BENIGNEATOhttp.log", saveModelFileName,5);
            //todo train the model on benign traffic

            /**
             *  Step 4: Pass the ioc data to an active defense script for
             *  automated Group Policy Object generation in Active Directory
             *  (see https://technet.microsoft.com/en-us/library/hh147307(v=ws.10).aspx for an intro)
             */
            PythonCommandLineLogic.passIocsToActiveDefenseScript(output, 4);
        }
    }


}
