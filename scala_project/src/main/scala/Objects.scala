case class Runway {
  id : Int,
  airport_ref : Int,
  airport_ident : String,
  surface : String,
  le_indent : String
}

case class Airport {
  id : Int,
  ident : String,
  type : String,
  name : String,
  latitude_deg : Float,
  longitude_deg : Float,
  continent : String,
  municipality : String
}

case class Countries {
  id : Int,
  code : String,
  name : String,
  continent : String
}

