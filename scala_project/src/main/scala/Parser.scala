package pkg
import scala.io.Source

object Parser {
  val splitRegex = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"

  def processString(x: String): String = {
    if (x.startsWith("\"")) {
      x.drop(1).dropRight(1)
    } else {
      x.trim()
    }
  }

  def parseCountries(){    
    Source.fromFile("countries.csv").getLines().drop(1).foreach {
      line => {
        val arr = line.split(splitRegex, 6).map(processString); 
        Db.save(Country(arr(0).toInt,arr(1),arr(2),arr(3)))
      }
    }
  }

  def parseAirports(){    
    Source.fromFile("airports.csv").getLines().drop(1).foreach {
      line => {
        val arr = line.split(splitRegex, 11).map(processString);
        val country = Db.query[Country].whereEqual("code", arr(8)).fetchOne()
        Db.save(Airport(
          country.get,
          arr(0).toInt,
          arr(1),
          arr(2),
          arr(3),
          arr(4).toDouble,
          arr(5).toDouble,
          arr(7),
          arr(10)
        ))
      }
    }
  }

  def parseRunways(){    
    Source.fromFile("runways.csv").getLines().drop(1).foreach {
      line => {
        val arr = line.split(splitRegex, 9).map(processString); 
        Db.save(Runway(arr(0).toInt,arr(1).toInt,arr(2),arr(5),arr(8)))
      }
    }
  }
}
