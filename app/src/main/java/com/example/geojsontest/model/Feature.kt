package com.example.geojsontest.model

/**
 * A Feature object represents a spatially bounded thing.  Every Feature
object is a GeoJSON object no matter where it occurs in a GeoJSON
text.
 */
data class Feature(
  val id: String? = null,
  val type : GeometryType,
  val geometry: Geometry? = null,
  val properties: Map<String, Any>
): Geometry() {
  init {
    require(type == GeometryType.FEATURE) {
      "type must be feature"
    }
  }

  override fun getGeometryType(): GeometryType = type
}
