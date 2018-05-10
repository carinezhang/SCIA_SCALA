object Parser {

/*
  def read_data(filename: String) : Vector[Data] = {
    for {
      line <- Source.fromFile(fileName).getLines().toVector
        data <- parseCsvLine(line)
    } yield data
  }

  def parseLine(line: String) : Option[Data] = {
    line.split(",").toVector.map(_.trim) match {
      case Vector(, , , ) => Some(Data(date, time, longitude, latitude))
      case _ => println(s"WARNING UNKNOWN DATA FORMAT FOR LINE: $line");
                                                                None
    }
  }
*/
}
