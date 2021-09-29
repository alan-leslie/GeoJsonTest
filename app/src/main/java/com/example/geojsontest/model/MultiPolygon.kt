package com.example.geojsontest.model

data class MultiPolygon(
  val coordinates: List<List<List<Position>>>
) : Geometry() {

  override fun getGeometryType(): GeometryType = GeometryType.MULTI_POLYGON

}
