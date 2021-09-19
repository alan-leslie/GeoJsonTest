package com.example.geojsontest.model

data class Point(
  val coordinates: Position
) : Geometry() {

  override fun getType(): GeometryType = GeometryType.POINT

}