package com.example.geojsontest.model

data class MultiPoint(
  val coordinates: List<Position>
) : Geometry() {

  override fun getGeometryType(): GeometryType = GeometryType.MULTI_POINT

}
