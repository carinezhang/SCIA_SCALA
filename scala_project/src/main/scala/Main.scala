package pkg


import sorm._
object Db extends Instance (
  entities = Set() + Entity[Runway]() + Entity[Airport]() + Entity[Country](),
  url = "jdbc:h2:mem:test",
  initMode = InitMode.DropAllCreate
)

object Main extends App {

  println("Loading data from the CSV files.")
  Parser.parseCountries()
  Parser.parseAirports()
  Parser.parseRunways()
  println("Data loaded !")
  val sca = ScannerTest
  sca.menu()
}


object ScannerTest {
  def menu() {
    val report = Report
    println("This is the menu.\nType Query or Reports. Type 'exit' to leave.")
    Iterator.continually(io.StdIn.readLine)
      .takeWhile(_ != "exit")
      .foreach{
        case "Query" => option()
        case "Reports" => report.menu()  /* do that */
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
