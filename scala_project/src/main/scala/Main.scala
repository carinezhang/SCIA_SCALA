object Main extends App {
  println("Hello, World!")
    val sca = ScannerTest
    sca.menu()
}

object ScannerTest {
  def menu() {
    Iterator.continually(io.StdIn.readLine)
      .takeWhile(_ != "x")
      .foreach{
        case "this" => println("THIS")  /* do this */
        case "that" => println("THAT")  /* do that */
        case e      => println(e)       /* default */
      }
  }
}
