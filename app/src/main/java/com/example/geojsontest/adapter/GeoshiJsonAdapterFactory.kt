package com.example.geojsontest.adapter

import com.example.geojsontest.model.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class GeoshiJsonAdapterFactory : JsonAdapter.Factory {
  override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
    when (type) {
      Position::class.java -> {
        return PositionJsonAdapter()
      }
      Point::class.java -> {
        return PointJsonAdapter(moshi)
      }
      LineString::class.java -> {
        return LineStringJsonAdapter(moshi)
      }
      Polygon::class.java -> {
        return PolygonJsonAdapter(moshi)
      }
      MultiPoint::class.java -> {
        return MultiPointJsonAdapter(moshi)
      }
      MultiLineString::class.java -> {
        return MultiLineStringJsonAdapter(moshi)
      }
      MultiPolygon::class.java -> {
        return MultiPolygonJsonAdapter(moshi)
      }
      GeometryCollection::class.java -> {
        return GeometryCollectionJsonAdapter(moshi)
      }
      Feature::class.java -> {
        return FeatureJsonAdapter(moshi)
      }
      FeatureCollection::class.java -> {
        return FeatureCollectionJsonAdapter(moshi)
      }
    }
    return null
  }
}