package pkg

object Query {
  def menu() {
    println("Query Option: Enter a country name or code")
    val input = io.StdIn.readLine
    val nameQuery = Db.query[Country].whereEqual("name", input)
    val codeQuery = Db.query[Country].whereEqual("code", input)
    (codeQuery.count() != 0, nameQuery.count() != 0) match {
      case (false, false) => println("No airports has been recognised")
      case (true, _) => fetchAirports(codeQuery.fetchOne().get)
      case (_, true) => fetchAirports(nameQuery.fetchOne().get)
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
