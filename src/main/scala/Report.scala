package pkg

object Report {
  def topCountries() = {
    def print(l: List[(Country, Int)], acc: Int = 10) : Unit = (l, acc) match {
      case (Nil, _) => Unit
      case (_, 0) => Unit
      case (e :: reste, a) => println(e._1.name + ": (" + e._2 + ")"); print(reste, a - 1)
    }
    println("Countries with the most airports")
    val topC = getTopCountries()
    print(topC)
    println("Countries with the least airports")
    print(topC.reverse)
  }

  def getTopCountries() : List[(Country, Int)] = {
    val res = Db.query[Airport].order("country_code.name").fetch()
    val resGroup = res.groupBy(_.country_code).map{
      case (country, airports) => country -> airports.length
    }.toStream.sortBy(_._2).reverse
    resGroup.toList
  }

  def typeRunways() = {
    getTypesRunways()
  }

  // Get the differents types of runways
  def getTypesRunways() = {
    // Pour chq country get les airports
    // Pour chq airport, get les runway
    def getRunways(airports : Stream[Airport]) : Set[String] = airports match {
      case Stream.Empty => Set[String]()
      case a #:: reste => val runways = Db.query[Runway].whereEqual("airport_ref", a.id_a).fetch().map{x => x.surface};
                          runways.toSet ++ getRunways(reste)
    }
    def print(c: Country, s : Set[String]) = {
      println(c.name + ":")
      s.foreach{
        runway => println(" - " + runway)
      }
    }
    val res = Db.query[Country].order("name").fetch()
    res.foreach{
      c => val airports = Db.query[Airport].whereEqual("country_code", c).fetch();
          val toPrint = getRunways(airports);
          print(c, toPrint)
    }
  }
  
  def topLatitudes() = {
    def print(l : List[String], acc: Int = 10) : Unit = (l, acc) match {
      case (Nil, _) => Unit
      case (_, 0) => Unit
      case (s :: reste, ac) => println(s + ","); print(reste, ac - 1)
    }
    println("the most common latitudes are:")
    val res = getTopLatitudes()
    print(res)
  }

  def getTopLatitudes() : List[String] = {
    val res = Db.query[Runway].order("le_indent").fetch()
    val resGroup = res.groupBy(_.le_indent).map{
      case (str, runways) => str -> runways.length
      }.toStream.sortBy(_._2).reverse
    resGroup.toList.map{e => e._1}
  }

  def menu() = {
    println("This is the Report menu.\nType \'Top countries\' or \'Type runways\' or \'Top latitudes\'. Type 'exit' to leave.")
    Iterator.continually(io.StdIn.readLine)
      .takeWhile(_ != "exit")
      .foreach{
        case "Top countries" => topCountries()
        case "Type runways" => typeRunways()
        case "Top latitudes" => topLatitudes()
        case e      => println("Does not recognize this option.")
      }
  }
}
