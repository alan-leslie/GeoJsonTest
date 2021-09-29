package com.example.geojsontest.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LineString(
  val type : GeometryType,
  val coordinates: List<Position>
) : Geometry() {
  init {
    require(type == GeometryType.LINESTRING) {
      "type must be line string"
    }
    require(coordinates.size > 1) {
      "there must be more than one coordinate"
    }
  }
  override fun getGeometryType(): GeometryType = type
}
