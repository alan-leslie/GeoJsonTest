package com.example.geojsontest.model

data class MultiLineString(
  val coordinates: List<List<Position>>
) : Geometry() {

  override fun getGeometryType(): GeometryType = GeometryType.MULTI_LINE_STRING

}
