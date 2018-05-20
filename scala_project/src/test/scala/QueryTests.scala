import pkg._
import org.scalatest.FunSuite

class QueryTest extends FunSuite{
  val country = Db.save(Country(1,"","",""))
  val airport = Db.save(Airport(country,1,"","","",1.0, 1.0,"",""))
  val runway = Db.save(Runway(1,1,"","",""))
  test("Query.fetchAirports") {
    assert(Query.fetchAirports(country) !== Nil)
  }
  
  test("Query.fetchRunways") {
    assert(Query.fetchRunways(airport) !== Nil)
  }
}