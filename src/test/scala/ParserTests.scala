import pkg._
import org.scalatest.FunSuite

class ParserTest extends FunSuite {
  test("Parser.processString") {
    assert(Parser.processString("\"string\"\"") == "string\"")
  }
}
 