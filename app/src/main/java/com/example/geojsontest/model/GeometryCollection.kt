package com.example.geojsontest.model

import com.squareup.moshi.JsonClass

/**
 * Each element in the "geometries" array of a GeometryCollection is one of the {GeometryType}:
 */
data class GeometryCollection(
  val geometries: List<Geometry>
) : Geometry() {
  override fun getGeometryType(): GeometryType = GeometryType.GEOMETRY_COLLECTION
}
