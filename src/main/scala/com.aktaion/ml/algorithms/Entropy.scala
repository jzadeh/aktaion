package com.aktaion.ml.algorithms
//
////logic bororowed from Spark Mllib
//
object EntropyUtils {
//
//  def log2(x: Double) = scala.math.log(x) / scala.math.log(2)
//
//  /**
//    * :: DeveloperApi ::
//    * information calculation for multiclass classification
//    * @param counts Array[Double] with counts for each label
//    * @param totalCount sum of counts for all labels
//    * @return information value, or 0 if totalCount = 0
//    */
//  override def calculate(counts: Array[Double], totalCount: Double): Double = {
//    if (totalCount == 0) {
//      return 0
//    }
//    val numClasses = counts.length
//    var impurity = 0.0
//    var classIndex = 0
//    while (classIndex < numClasses) {
//      val classCount = counts(classIndex)
//      if (classCount != 0) {
//        val freq = classCount / totalCount
//        impurity -= freq * log2(freq)
//      }
//      classIndex += 1
//    }
//    impurity
//  }
//
  /**
    *
    * @param input
    * @return
    */
  def charDistribution(input: String): Float = {
    val length = input.length
    val charSet = input.toSet
    var out = 0.0f
    for (x <- charSet){
      var prob = input.count(_ == x).toFloat / length
      out = out + prob * math.log(prob).toFloat
    }
    return { out}
  }

//
//  /**
//    *
//    * @param input
//    * @return
//    */
//  def  ideal(input: String): Float = {
//    val length = input.toSet.size
//
//    return {-1.0f * math.log(1.0f/length).toFloat /math.log(36.0).toFloat }
//  }
//
//
//  /**
//    *
//    * @param a
//    * @param b
//    * @return
//    */
//  def sumInts(a: Int, b:Int): Int = if (a > b) 0 else a + sumInts(a+1,b)
//
//
//
//
}
