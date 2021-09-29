package com.example.geojsontest.model

import com.squareup.moshi.JsonClass

//@JsonClass(generateAdapter = true)
data class FeatureCollection(
  val features: List<Feature>
) : Geometry() {
  override fun getGeometryType(): GeometryType = GeometryType.FEATURE_COLLECTION
}
