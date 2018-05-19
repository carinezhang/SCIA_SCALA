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
  Parser.parseAirports()
  Parser.parseCountries()
  //Parser.parseRunways()
  val c = get_country()
  println(c.get)
  val res = get_airport()
  //println(res.get)

  //sca.menu()

  def get_country() : Option[Country] = {
    Db.query[Country].whereEqual("name", "France").fetchOne()
  }

  def get_airport() : Option[Airport] = {
    Db.query[Airport].whereEqual("id", 6).fetchOne()
  }

}

object ScannerTest {
  def menu() {
    println("This is the menu.\nType Query or Reports. Type 'exit' to leave.")
    Iterator.continually(io.StdIn.readLine)
      .takeWhile(_ != "exit")
      .foreach{
        case "Option" => println("THIS")  /* do this */
        case "Reports" => println("THAT")  /* do that */
        case e      => println("Does not recognize this option.")       /* default */
      }
  }
  
}
