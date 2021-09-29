package com.example.geojsontest.model

data class LineString(
  val coordinates: List<Position>
) : Geometry() {

  override fun getGeometryType(): GeometryType = GeometryType.LINESTRING

}
