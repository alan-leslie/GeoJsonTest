package com.example.geojsontest.model

import com.squareup.moshi.JsonClass

/**
 * A Feature object represents a spatially bounded thing.  Every Feature
object is a GeoJSON object no matter where it occurs in a GeoJSON
text.
 */
data class Feature(
  val id: String? = null,
  val geometry: Geometry? = null,
  val properties: Map<String, Any>
) : Geometry() {
  override fun getGeometryType(): GeometryType = GeometryType.FEATURE
}
