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
        return com.example.geojsontest.model.PointJsonAdapter(moshi)
      }
      LineString::class.java -> {
        return com.example.geojsontest.model.LineStringJsonAdapter(moshi)
      }
      Polygon::class.java -> {
        return com.example.geojsontest.model.PolygonJsonAdapter(moshi)
      }
      MultiPoint::class.java -> {
        return com.example.geojsontest.model.MultiPointJsonAdapter(moshi)
      }
      MultiLineString::class.java -> {
        return com.example.geojsontest.model.MultiLineStringJsonAdapter(moshi)
      }
      MultiPolygon::class.java -> {
        return com.example.geojsontest.model.MultiPolygonJsonAdapter(moshi)
      }
      GeometryCollection::class.java -> {
        return GeometryCollectionJsonAdapter(
          moshi.adapter(Point::class.java),
          moshi.adapter(LineString::class.java),
          moshi.adapter(Polygon::class.java),
          moshi.adapter(MultiPoint::class.java),
          moshi.adapter(MultiLineString::class.java),
          moshi.adapter(MultiPolygon::class.java)
        )
      }
      Feature::class.java -> {
        val wildCardAdapter = moshi.adapter<Any>(Any::class.java)
        return FeatureJsonAdapter(
          wildCardAdapter,
          moshi.adapter(Point::class.java),
          moshi.adapter(LineString::class.java),
          moshi.adapter(Polygon::class.java),
          moshi.adapter(MultiPoint::class.java),
          moshi.adapter(MultiLineString::class.java),
          moshi.adapter(MultiPolygon::class.java),
          moshi.adapter(GeometryCollection::class.java)
        )
      }
      FeatureCollection::class.java -> {
        return FeatureCollectionJsonAdapter(moshi.adapter(Feature::class.java))
      }
    }
    return null
  }


}