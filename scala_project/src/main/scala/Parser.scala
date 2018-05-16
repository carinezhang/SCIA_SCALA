package pkg
import scala.io.Source

object Parser {


  // def read_data(filename: String) : Any= {// Vector[Data] = {
  //   // for {
  //   //   line <- Source.fromFile(fileName).getLines().toVector
  //   //     data <- parseCsvLine(line)
  //   // } yield data
  //   for (line <- Source.fromFile("countries.csv").getLines) {
  //     println(line)
  //   }
  // }

  // def parseLine(line: String) : Option[Data] = {
  //   line.split(",").toVector.map(_.trim) match {
  //     case Vector(a, b, c, d) => Db.save()
  //     case _ => println(s"WARNING UNKNOWN DATA FORMAT FOR LINE: $line");
  //                                                               None
  //   }
  // }

  def parseCountries(){    
    Source.fromFile("countries.csv").getLines().drop(1).foreach { line =>
      line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)").map(_.trim) match {
        case Array(a,b,c,d, e) => Db.save(Countries(a.toInt,b,c,d))
        case Array(a,b,c,d, e,f) => Db.save(Countries(a.toInt,b,c,d))
        case _ => println(s"WARNING UNKNOWN DATA FORMAT FOR LINE: $line")
      }
    }
  }
}
