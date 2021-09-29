package com.example.geojsontest.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MultiLineString(
  val type : GeometryType,
  val coordinates: List<List<Position>>
) : Geometry() {
  init {
    require(type == GeometryType.MULTI_LINE_STRING) {
      "type must be point"
    }
  }

  override fun getGeometryType(): GeometryType = GeometryType.MULTI_LINE_STRING
}
