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
      line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", 6).map(_.trim) match {
        case Array(a,b,c,d,e,f) => Db.save(Countries(a.toInt,b,c,d))
      }
    }
  }

  def parseAirports(){    
    Source.fromFile("airports.csv").getLines().drop(1).foreach { line =>
      line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", 11).map(_.trim) match {
        case Array(a,b,c,d,e,f,g,h,i,j,k) => Db.save(Airport(a.toInt,b,c,d,e.toFloat,f.toFloat,h,k))
      }
    }
  }
}
