
import org.apache.flink.api.scala._
import com.joprice.flink.FlinkExtensions._

case class Data(word: String, count: Int)

case class Data2(a: String, b: String, count: Int)

object Main {

  def main(args: Array[String]): Unit = {

    val env = ExecutionEnvironment.getExecutionEnvironment

    // get input data
    val text = env.fromElements("To be, or not to be,--that is the question:--",
      "Whether 'tis nobler in the mind to suffer", "The slings and arrows of outrageous fortune",
      "Or to take arms against a sea of troubles,")

    val countsByString = text
      .flatMap { _.toLowerCase.split("\\W+") }
      .map(x => Data2(x, x, 1))
      .groupByField("a", "b")
      .sumField('count)

    println("string keys")
    countsByString.print()

    val countsByStringSingle = text
      .flatMap { _.toLowerCase.split("\\W+") }
      .map(x => Data2(x, x, 1))
      .groupByField('a)
      .sumField("count")

    println("single")
    countsByStringSingle.print()

    val countsByString2 = text
      .flatMap { _.toLowerCase.split("\\W+") }
      .map(x => Data2(x, x, 1))
      .groupByField('a, 'b)

  }

}

