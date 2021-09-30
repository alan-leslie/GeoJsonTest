package com.example.geojsontest.model

/**
 * GeoJSON supports the following geometry types (case sensitive strings):
 * Point
 * LineString
 * Polygon
 * MultiPoint
 * MultiLineString,
 * MultiPolygon
 * GeometryCollection.
 * Feature in GeoJSON contain aGeometry object and additional properties,
 * FeatureCollection contains a list of Features.
 */
enum class GeometryType {
  POINT,
  LINESTRING,
  POLYGON,
  MULTI_POINT,
  MULTI_LINE_STRING,
  MULTI_POLYGON,
  GEOMETRY_COLLECTION,
  FEATURE,
  FEATURE_COLLECTION;

  /**
   * Convert to case sensitive strings
   */
  fun convertToString(): String {
    return when (this) {
      POINT -> "Point"
      LINESTRING -> "LineString"
      POLYGON -> "Polygon"
      MULTI_POINT -> "MultiPoint"
      MULTI_LINE_STRING -> "MultiLineString"
      MULTI_POLYGON -> "MultiPolygon"
      GEOMETRY_COLLECTION -> "GeometryCollection"
      FEATURE -> "Feature"
      FEATURE_COLLECTION -> "FeatureCollection"
    }

  }

  companion object {
    @JvmStatic
    fun convertFromString(stringValue: String): GeometryType {
      return when (stringValue) {
        "Point" -> POINT
        "LineString" -> LINESTRING
        "Polygon" -> POLYGON
        "MultiPoint" -> MULTI_POINT
        "MultiLineString" -> MULTI_LINE_STRING
        "MultiPolygon" -> MULTI_POLYGON
        "GeometryCollection" -> GEOMETRY_COLLECTION
        "Feature" -> FEATURE
        "FeatureCollection" -> FEATURE_COLLECTION
        else -> throw IllegalArgumentException("$stringValue is not specified according to geojson spec")
      }
    }
  }
}