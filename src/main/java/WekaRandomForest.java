//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//
//import weka.classifiers.CostMatrix;
//import weka.classifiers.Evaluation;
//import weka.classifiers.meta.CostSensitiveClassifier;
//
//import weka.classifiers.trees.RandomForest;
//import weka.core.Instances;
//import weka.core.converters.ArffSaver;
//import weka.filters.Filter;
//import weka.filters.supervised.instance.Resample;
//import weka.filters.unsupervised.instance.Randomize;
//
///**
// * @author samy
// */
//public class WekaTestRFResampleCost {
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) throws Exception {
//        BufferedReader br = null;
//        double numFolds = 10.0d;
//        double precisionOne = 0.0d;
//        double recallOne = 0.0d;
//        double fmeansureOne = 0.0d;
//        double precisionTwo = 0.0d;
//        double recallTwo = 0.0d;
//        double fmeansureTwo = 0.0d;
//
//
//        double ROCone = 0.0d;
//        double ROCtwo = 0.0d;
//
//
//        double PRCone = 0.0d;
//        double PRCtwo = 0.0d;
//
//
//        br = new BufferedReader(new FileReader("D:\\CostResample\\eclipse.arff"));
//
//     //   ArffSaver saverTets = new ArffSaver();
//        ArffSaver saverTraining = new ArffSaver();
//        Instances trainData = new Instances(br);
//        trainData.setClassIndex(trainData.numAttributes() - 1);
//        br.close();
//        Randomize randFilterMain = new Randomize();
//        randFilterMain.setInputFormat(trainData);
//        trainData = Filter.useFilter(trainData, randFilterMain);
//        int size = (int) (trainData.numInstances() / numFolds);
//        int begin = 0; // is index if flod.
//        int end = size - 1; // is index
//
//        System.out.println("Total Size of instances" + trainData.numInstances() + " , flod size=" + size);
//        for (int i = 1; i <= numFolds; i++) {
//            System.out.println("Iteration # " + i + " Begin =" + begin + " , end=" + end);
//            Instances tempTraining = new Instances(trainData);
//            Instances tempTesting = new Instances(trainData, begin, (end - begin));
//            for (int j = 0; j < (end - begin); j++) {
//                tempTraining.delete(begin);
//            }
//
//            //// Filters
//            Resample resample = new Resample();
//
//            resample.setBiasToUniformClass(0.5f);
//            resample.setInvertSelection(false);
//            resample.setNoReplacement(false);
//
//            resample.setRandomSeed(1);
//            //smoteFilter.setClassValue("2");
//            resample.setInputFormat(tempTraining);
//
//            System.out.println("Number of instances before filter " + tempTraining.numInstances());
//
//            Instances resmapleTempTraining = Filter.useFilter(tempTraining, resample);
//
//
//            System.out.println("Number of instances after filter " + resmapleTempTraining.numInstances());
//
//            RandomForest randomForest = new RandomForest();
//            randomForest.setNumTrees(100);
//
//            System.out.println("Started building the model #" + i);
////            randomForest.buildClassifier(resmapleTempTraining);
//
//            CostSensitiveClassifier costSensitiveClassifier = new CostSensitiveClassifier();
//            CostMatrix costMatrix = new CostMatrix(2);
////          costMatrix.setCell(0, 0, 0.8d);
//            //          costMatrix.setCell(0, 1, 5.0d);
//            costMatrix.setCell(1, 0, 2d);
////            costMatrix.setCell(1, 1, 1.0d);
//
//            costSensitiveClassifier.setClassifier(randomForest);
//            costSensitiveClassifier.setCostMatrix(costMatrix);
//            costSensitiveClassifier.buildClassifier(resmapleTempTraining);
//
//            saverTraining.setInstances(resmapleTempTraining);
//            saverTraining.setFile(new File("D:\\SumCost\\eclipse\\" + i + "_training.arff"));
//         //   saverTets.setInstances(tempTesting);
//         //   saverTets.setFile(new File("D:\\SumCost\\eclipse\\" + i + "_testing.arff"));
//
//            saverTraining.writeBatch();
//     //       saverTets.writeBatch();
//
//
//            System.out.println("Done with building the model");
//
//            Evaluation evaluation = new Evaluation(tempTesting);
//
//            evaluation.evaluateModel(costSensitiveClassifier, tempTesting);
//
//            System.out.println("Results For Class -1- ");
//            System.out.println("Precision=  " + evaluation.precision(0));
//            System.out.println("Recall=  " + evaluation.recall(0));
//            System.out.println("F-measure=  " + evaluation.fMeasure(0));
//            System.out.println("ROC=  " + evaluation.areaUnderROC(0));
//            System.out.println("Results For Class -2- ");
//            System.out.println("Precision=  " + evaluation.precision(1));
//            System.out.println("Recall=  " + evaluation.recall(1));
//            System.out.println("F-measure=  " + evaluation.fMeasure(1));
//            System.out.println("ROC=  " + evaluation.areaUnderROC(1));
//            precisionOne += evaluation.precision(0);
//            recallOne += evaluation.recall(0);
//            fmeansureOne += evaluation.fMeasure(0);
//            precisionTwo += evaluation.precision(1);
//            recallTwo += evaluation.recall(1);
//            fmeansureTwo += evaluation.fMeasure(1);
//
//            ROCone += evaluation.areaUnderROC(0);
//            ROCtwo += evaluation.areaUnderROC(1);
//
////            PRCone += evaluation.areaUnderPRC(0);
////            PRCtwo += evaluation.areaUnderPRC(1);
//
//
//            begin = end + 1;
//            end += size;
//            if (i == (numFolds - 1)) {
//                end = trainData.numInstances();
//            }
//        }
//
//
//        System.out.println("####################################################");
//        System.out.println("Results For Class -1- ");
//        System.out.println("Precision=  " + precisionOne / numFolds);
//        System.out.println("Recall=  " + recallOne / numFolds);
//        System.out.println("F-measure=  " + fmeansureOne / numFolds);
//        System.out.println("ROC=  " + ROCone / numFolds);
//        System.out.println("PRC= " + PRCone / numFolds);
//
//
//        System.out.println("Results For Class -2- ");
//        System.out.println("Precision=  " + precisionTwo / numFolds);
//        System.out.println("Recall=  " + recallTwo / numFolds);
//        System.out.println("F-measure=  " + fmeansureTwo / numFolds);
//        System.out.println("ROC=  " + ROCtwo / numFolds);
//        System.out.println("PRC= " + PRCtwo / numFolds);
//
//
//    }
//}
////- See more at: http://www.codemiles.com/weka-examples/cost-sensitive-classifier-random-forest-java-in-weka-t11129.html#sthash.OtQVO6Yh.dpuf