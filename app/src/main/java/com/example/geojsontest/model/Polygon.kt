package com.example.geojsontest.model

data class Polygon(
  val coordinates: List<List<Position>>
) : Geometry() {

  override fun getGeometryType(): GeometryType = GeometryType.POLYGON

}
