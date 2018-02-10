//*************Zeenat & Khushal***************
//*************ICP 4**************
import org.apache.spark.{SparkConf, SparkContext}

object ICP {
  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf().setAppName("ICP4").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)
    val predictionLabels=sc.parallelize(Seq((0.0,0.1),(1.0,1.0),(0.0,0.0),(1.0,1.0),(1.0,0.0),(0.0,0.0),(0.0,0.0),(1.0,1.0),(0.0,1.0),(0.0,0.0)));
    val accuracy = 100 * predictionLabels.filter(x => x._1 == x._2).count() / 10
    println(accuracy)
    ModelEvaluation.evaluateModel(predictionLabels)
    var tp=4.0
    var tn=3.0
    var fp=2.0
    var fn=1.0
    var t=10.0
    println("Data Accuracy:",(tp+tn)/t)
    println(" Misclassification Rate:",(fp+fn)/t)
    //  True Positive Rate: tp/a-yes
    println("True Positive Rate:",tp/5)
    // False Positive Rate: fp/a-no
    println(" False Positive Rate:",fp/5)
    // Specificity: tn/a-no
    println("Specificity:",tn/5)
    // Precision: tp/p-yes
    println(" Precision:",tp/6)
    //Prevalence:a-yes/t
    println("Prevalence:",5/t)
  }

}