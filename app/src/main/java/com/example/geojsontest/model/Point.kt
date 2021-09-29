package com.example.geojsontest.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Point(
  val type : GeometryType,
  val coordinates: Position
)  : Geometry() {
  init {
    require(type == GeometryType.POINT) {
      "type must be point"
    }
  }

  override fun getGeometryType(): GeometryType = type
}
