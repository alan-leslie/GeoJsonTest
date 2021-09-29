package com.example.geojsontest.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeatureCollection(
  val type : GeometryType,
  val features: List<Feature>
) : Geometry() {
  init {
    require(type == GeometryType.FEATURE_COLLECTION) {
      "type must be feature collection"
    }
  }

  override fun getGeometryType(): GeometryType = type
}
