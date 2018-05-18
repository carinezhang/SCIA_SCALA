package pkg

case class Country (
  id_a : Int,
  code : String,
  name : String,
  continent : String
)

case class Airport (
  country_code : Country,
  id_a : Int,
  ident : String,
  type_a : String,
  name : String,
  latitude_deg : Float,
  longitude_deg : Float,
  continent : String,
  municipality : String
)

case class Runway (
  id_a : Int,
  airport_ref : Int,
  airport_ident : String,
  surface : String,
  le_indent : String
)
