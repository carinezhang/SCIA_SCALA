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
    // resGroup = Map[Country, Stream[Airport]]
    val resGroup = res.groupBy(_.country_code).map{
      case (country, airports) => country -> airports.length
    }.toStream.sortBy(_._2).reverse
    resGroup.toList
  }

  def typeRunways() = {
    def print(l : List[String]) : Unit = l match {
      case Nil => Unit
      case s :: reste => println(s + ","); print(reste)
    }
    println("The differents types of runways are:")
    val runways = getTypesRunways()
    print(runways)
    println("DONE TYPE RUNWAAYS")
  }

  // Get the differents types of runways
  def getTypesRunways() : List[String] = {
    val res = Db.query[Runway].order("surface").fetch()
    // type res = Stream[Runway with Persistent]
    def aux(stream : Stream[Runway]) : List[String] = stream match {
      case Stream.Empty => List()
      case e #:: Stream.Empty => List(e.surface)
      case e #:: e2 #:: reste if e.surface == e2.surface => aux(e #:: reste)
      case e #:: reste => e.surface :: aux(reste)
    }
    aux(res)
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
    println("DONE")
  }

  def getTopLatitudes() : List[String] = {
    val res = Db.query[Runway].order("le_indent").fetch()
    // type res = Map[String, Stream[Runway]]
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
        case "Top countries" => topCountries()  /* do this */
        case "Type runways" => typeRunways()  /* do that */
        case "Top latitudes" => topLatitudes()  /* do that */
        case e      => println("Does not recognize this option.")       /* default */
      }
  }
}
