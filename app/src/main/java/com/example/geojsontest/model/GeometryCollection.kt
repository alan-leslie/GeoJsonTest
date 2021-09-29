package com.example.geojsontest.model

import com.squareup.moshi.JsonClass

/**
 * Each element in the "geometries" array of a GeometryCollection is one of the {GeometryType}:
 */
data class GeometryCollection(
  val type : GeometryType,
  val geometries: List<Geometry>
) : Geometry() {
  init {
    require(type == GeometryType.GEOMETRY_COLLECTION) {
      "type must be feature"
    }
  }

  override fun getGeometryType(): GeometryType = type
}
