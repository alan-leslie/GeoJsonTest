package com.example.geojsontest.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MultiPoint(
  val type : GeometryType,
  val coordinates: List<Position>
) : Geometry() {
  init {
    require(type == GeometryType.MULTI_POINT) {
      "type must be point"
    }
  }

  override fun getGeometryType(): GeometryType = type
}
