package pkg

import org.scalatest.FunSuite

class ReportTest extends FunSuite{
  println("Loading data from CSV files")
  Parser.parseCountries()
  Parser.parseAirports()
  Parser.parseRunways()
  println("Data loaded !")


  test("Report.getTypesRunways") {
    assert(Report.getTypesRunways() !== Nil)
  }

  test("Report.getTopCountries") {
    assert(Report.getTopCountries() !== Nil)
  }

  test("Report.getTopLatitudes") {
    assert(Report.getTopLatitudes() !== Nil)
  }
}
