package com.example.geojsontest.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Polygon(
  val type : GeometryType,
  val coordinates: List<List<Position>>
) : Geometry() {
  init {
    require(type == GeometryType.POLYGON) {
      "type must be point"
    }
  }

  override fun getGeometryType(): GeometryType = type
}
