
import org.apache.flink.api.scala._
import com.joprice.flink.FlinkExtensions._

case class Data(word: String, count: Int)

object Main {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    // get input data
    val text = env.fromElements("To be, or not to be,--that is the question:--",
      "Whether 'tis nobler in the mind to suffer", "The slings and arrows of outrageous fortune",
      "Or to take arms against a sea of troubles,")

    val counts = text
      .flatMap { _.toLowerCase.split("\\W+") }
      .map(Data(_, 1))
      .groupByField("word")
      .sum(1)

    // emit result
    counts.print()
  }

}

