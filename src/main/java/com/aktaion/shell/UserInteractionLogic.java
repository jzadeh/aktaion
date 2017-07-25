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

        // determines where the path to the Jar is (does not work on windows)
        String dataPath = CommandLineUtils.tryToFindPathToDataInSourceCode(5);

        System.out.println("*************************");
        System.out.println("*************************");
        System.out.println("** Aktaion Version 0.1 **");
        System.out.println("*************************");
        System.out.println("*************************");
        System.out.println("1: Analyze Bro HTTP Sample Using Default Model");
        System.out.println("2: Analyze PCAP Sample (Bro must be installed) Using Default Model");
        System.out.println("3: Demo (Unix/OS X System)");
        System.out.print("Enter Execution Choice [1-3]: ");

        int userChoice = scanner.nextInt();

        if (userChoice == 1) {
            System.out.print("Specify Input Path (Bro http.log): ");
            String fileInputPath = scanner.next();
            System.out.print("Specify Window Size For Analysis: ");
            int inputWindowSize = scanner.nextInt();


            String modelFileName = dataPath + "demoData/model.test";

            Option<RandomForestLogic.IocsExtracted> output = RandomForestLogic.
                    scoreBroHttpFile(fileInputPath, modelFileName, inputWindowSize);

            System.out.print("Specify Output Path For Predicted IOC's ");
            String fileOutputPath = scanner.next();

            RandomForestLogic.writeIocsToDisk(output, fileOutputPath);

        } else if (userChoice == 2) {
            System.out.print("Specify Input Path (Bro http.log): ");
            String fileInputPath = scanner.next();
            System.out.print("Specify Window Size For Analysis: ");
            int inputWindowSize = scanner.nextInt();

            String modelFileName = dataPath + "demoData/model.test";

            String extractedFile = CommandLineUtils.extractBroFilesFromPcap(fileInputPath);
            pressAnyKeyToContinue();

            Option<RandomForestLogic.IocsExtracted> output = RandomForestLogic.
                    scoreBroHttpFile(extractedFile, modelFileName, inputWindowSize);


            System.out.print("Specify Output Path For Predicted IOC's ");
            String fileOutputPath = scanner.next();

            RandomForestLogic.writeIocsToDisk(output, fileOutputPath);


        } else if (userChoice == 3) {

            System.out.println("My path" + dataPath);

            /**
             * Step 1a:  Train on Malicious Exploit Traffic
             * (Only Need to Perform Once)
             *
             * Train a model by scoring a whole directory of files
             * in this case we have extracts from 300+ exploit samples
             * with a .webgateway extension
             */
            String exploitDataDirectory = dataPath + "proxyData/exploitData/";
            String exploitDataOutputFileName = dataPath + "demoData/demoExploitData.arff";
            int windowSizeForDemo = 5;

            String maliciousWekaData = WekaUtilities.
                    extractDirectoryToWekaFormat(exploitDataDirectory,
                            ".webgateway",
                            ClassLabel.EXPLOIT(),
                            windowSizeForDemo);

            WekaUtilities.writeWekaDataToFile(maliciousWekaData, exploitDataOutputFileName);

            pressAnyKeyToContinue();

            /*
             * Step 1b:  Train on Benign Traffic Samples
             * (Only Need to Perform Once)
             *
             */
            String benignDataDirectory = dataPath + "broData/benignTraffic/";
            String benignDataOutputFileName = dataPath + "demoData/demoBenignData.arff";

            String benignWekaData = WekaUtilities.
                    extractDirectoryToWekaFormat(benignDataDirectory,
                            "http.log",
                            ClassLabel.BENIGN(),
                            windowSizeForDemo);

            WekaUtilities.writeWekaDataToFile(benignWekaData, benignDataOutputFileName);

            pressAnyKeyToContinue();

            String mergedFileName = dataPath + "demoData/demoBenignAndExploitData.test";

            //mix the malicious and benign labels into a single data set
            String mergedWekaData = WekaUtilities.mergeTwoWekaFiles(exploitDataOutputFileName, benignDataOutputFileName);

            //write final set of behaviors to disk
            WekaUtilities.writeWekaDataToFile(mergedWekaData, mergedFileName);

            //ouput of the serialized random forest
            String modelFileName = dataPath + "demoData/model.test";

            //train a random forest on the file we wrote in the previous line
            RandomForestLogic.trainWekaRandomForest(mergedFileName, modelFileName, 10, 100);

            /**
             * Step 2: get a PCAP file to score and convert it to bro logs
             */
            String demoInputFileName = dataPath + "demoData/demoExploitPcap.pcap"; //change to whatever pcap we want to score
            String extractedFile = CommandLineUtils.extractBroFilesFromPcap(demoInputFileName);
            pressAnyKeyToContinue();

            /**
             *  Step 3a: Score Malicious File Extract the IOCs from the scored file
             */
            Option<RandomForestLogic.IocsExtracted> output = RandomForestLogic.
                    scoreBroHttpFile(extractedFile, modelFileName, windowSizeForDemo);

            pressAnyKeyToContinue();

            /**
             * Step 3b: Score Benign Traffic File
             */
            RandomForestLogic.
                    scoreBroHttpFile(dataPath + "demoData/BENIGNEATOhttp.log", modelFileName, windowSizeForDemo);

            /**
             *  Step 4: Pass the ioc data to an active defense script for
             *  automated Group Policy Object generation in Active Directory
             *  (see https://technet.microsoft.com/en-us/library/hh147307(v=ws.10).aspx for an intro)
             */
            PythonCommandLineLogic.passIocsToActiveDefenseScript(output, 4);
        }
    }


}
