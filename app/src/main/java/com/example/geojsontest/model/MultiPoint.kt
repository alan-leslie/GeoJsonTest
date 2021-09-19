package com.example.geojsontest.model

data class MultiPoint(
  val coordinates: List<Position>
) : Geometry() {

  override fun getType(): GeometryType = GeometryType.MULIT_POINT

}
