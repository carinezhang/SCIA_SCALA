import pkg._

import org.scalatest.FunSuite

class ReportTest extends FunSuite{
  println("Loading data")
  fillCountries()
  fillAirports()
  fillRunways()
  println("Data loaded !")

  test("Report.getTypesRunways") {
    assert(Report.getTypesRunways().contains("s") === true)
    assert(Report.getTypesRunways().contains("s1") === true)
    assert(Report.getTypesRunways().contains("s2") === true)
    assert(Report.getTypesRunways().contains("s3") === true)
    assert(Report.getTypesRunways().contains("s4") === true)
    assert(Report.getTypesRunways().contains("s5") === true)
    assert(Report.getTypesRunways().contains("s6") === true)
    assert(Report.getTypesRunways().contains("s7") === true)
    assert(Report.getTypesRunways().contains("s8") === true)
    assert(Report.getTypesRunways().contains("s9") === true)
    assert(Report.getTypesRunways().contains("s10") === false)
  }

  test("Report.getTopCountries") {
    assert(Report.getTopCountries()(2)._1.name === "France2")
    assert(Report.getTopCountries()(0)._1.name === "France")
  }

  test("Report.getTopLatitudes") {
    assert(Report.getTopLatitudes().contains("lat") === true)
    assert(Report.getTopLatitudes().contains("lat1") === true)
    assert(Report.getTopLatitudes().contains("lat2") === true)
    assert(Report.getTopLatitudes().contains("lat3") === true)
    assert(Report.getTopLatitudes().contains("lat4") === true)
    assert(Report.getTopLatitudes().contains("lat5") === true)
    assert(Report.getTopLatitudes().contains("lat6") === true)
    assert(Report.getTopLatitudes().contains("lat7") === true)
    assert(Report.getTopLatitudes().contains("lat8") === true)
    assert(Report.getTopLatitudes().contains("lat9") === true)
    assert(Report.getTopLatitudes().contains("lat10") === true)
    assert(Report.getTopLatitudes().contains("lat11") === false)
  }


  def fillCountries() = {
    Db.save(Country(2,"FR","France","Europe"))
    Db.save(Country(3,"FR1","France1","Europe"))
    Db.save(Country(4,"FR2","France2","Europe"))
    Db.save(Country(5,"FR3","France3","Europe"))
    Db.save(Country(6,"FR4","France4","Europe"))
    Db.save(Country(7,"FR5","France5","Europe"))
    Db.save(Country(8,"FR6","France6","Europe"))
    Db.save(Country(9,"FR7","France7","Europe"))
    Db.save(Country(10,"FR8","France8","Europe"))
    Db.save(Country(11,"FR9","France9","Europe"))
    Db.save(Country(12,"FR10","France10","Europe"))
  }

  def fillRunways() = {
    Db.save(Runway(1,1,"","s","lat"))
    Db.save(Runway(1,1,"","s1","lat1"))
    Db.save(Runway(1,1,"","s2","lat2"))
    Db.save(Runway(1,1,"","s3","lat3"))
    Db.save(Runway(1,1,"","s4","lat4"))
    Db.save(Runway(1,1,"","s5","lat5"))
    Db.save(Runway(1,1,"","s6","lat6"))
    Db.save(Runway(1,1,"","s7","lat7"))
    Db.save(Runway(1,1,"","s8","lat8"))
    Db.save(Runway(1,1,"","s9","lat9"))
    Db.save(Runway(1,1,"","s9","lat10"))
  }

  def fillAirports() = {
    Db.save(Airport(Db.fetchById[Country](2),1,"","","",1.0, 1.0,"",""))
    Db.save(Airport(Db.fetchById[Country](2),1,"","","",1.0, 1.0,"",""))
    Db.save(Airport(Db.fetchById[Country](2),1,"","","",1.0, 1.0,"",""))
    Db.save(Airport(Db.fetchById[Country](2),1,"","","",1.0, 1.0,"",""))
    Db.save(Airport(Db.fetchById[Country](3),1,"","","",1.0, 1.0,"",""))
    Db.save(Airport(Db.fetchById[Country](3),1,"","","",1.0, 1.0,"",""))
    Db.save(Airport(Db.fetchById[Country](3),1,"","","",1.0, 1.0,"",""))
    Db.save(Airport(Db.fetchById[Country](4),1,"","","",1.0, 1.0,"",""))
    Db.save(Airport(Db.fetchById[Country](4),1,"","","",1.0, 1.0,"",""))
    Db.save(Airport(Db.fetchById[Country](5),1,"","","",1.0, 1.0,"",""))
  }
}
