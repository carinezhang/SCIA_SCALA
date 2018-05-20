package pkg

object Query {
  def menu() {
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
