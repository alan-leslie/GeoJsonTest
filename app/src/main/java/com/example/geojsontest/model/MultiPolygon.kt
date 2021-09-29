package com.example.geojsontest.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MultiPolygon(
  val type : GeometryType,
  val coordinates: List<List<List<Position>>>
) : Geometry() {
  init {
    require(type == GeometryType.MULTI_POLYGON) {
      "type must be point"
    }
  }

  override fun getGeometryType(): GeometryType = type
}
