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
  
  val s = "This is the menu.\nType Query or Reports. Type 'exit' to leave."
  Iterator.continually({println(s); io.StdIn.readLine})
    .takeWhile(_ != "exit")
    .foreach{
      case "Query" => Query.menu()
      case "Reports" => Report.menu()
      case e      => println("Does not recognize this option.")
    }
}