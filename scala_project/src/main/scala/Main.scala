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
  // Parser.parseAirports()
  // Parser.parseRunways()
  val res = Db.query[Country].whereEqual("name", "France").fetchOne()
  println(res.get)

  //sca.menu()
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
