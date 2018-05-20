package pkg


import sorm._
object Db extends Instance (
  entities = Set() + Entity[Runway]() + Entity[Airport]() + Entity[Country](),
  url = "jdbc:h2:mem:test",
  initMode = InitMode.DropAllCreate
)

object Main extends App {

  println("Hello, World!")
  val sca = ScannerTest
  Parser.parseCountries()
  Parser.parseAirports()
  Parser.parseRunways()
  val c = get_country()
  println(c.get)
  val res = get_airport()
  println(res.get)

  //sca.menu()

  val rep = Report
  rep.menu()


  def get_country() : Option[Country] = {
    Db.query[Country].whereEqual("name", "France").fetchOne()
  }

  def get_airport() : Option[Airport] = {
    Db.query[Airport].whereEqual("country_code", c.get).fetchOne()
  }

}

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

object ScannerTest {
  def menu() {
    println("This is the menu.\nType Query or Reports. Type 'exit' to leave.")
    Iterator.continually(io.StdIn.readLine)
      .takeWhile(_ != "exit")
      .foreach{
        case "Query" => option()
        case "Reports" => println("THAT")  /* do that */
        case e      => println("Does not recognize this option.")       /* default */
      }
  }

  def option() {
    println("Query Option: Enter a country name or code")
    val input = io.StdIn.readLine
    val nameQuery = Db.query[Country].whereEqual("name", input)
    if (nameQuery.count() > 0) {
      fetchAirports(nameQuery.fetchOne().get)
    }
    val codeQuery = Db.query[Country].whereEqual("code", input)
    if (codeQuery.count() > 0){
      fetchAirports(codeQuery.fetchOne().get)
    }
  }
  
  def fetchAirports(c: Country){
    val query = Db.query[Airport].whereEqual("country_code", c)
    val count = query.count()
    println(s"${c.name}, ${c.continent}: ${count} airports.")
    if (count > 0) {
      query.fetch().foreach{
        x => fetchRunways(x)
      }
    }
  }

  def fetchRunways(a: Airport) {
    val query = Db.query[Runway].whereEqual("airport_ref", a.id_a)
    val count = query.count()
    println(s"${a.name}: $count runways.")
    if (count > 0) {
      query.fetch().foreach{
        x => println(s"- surface: ${x.surface}");
      }
    }
  }
}
