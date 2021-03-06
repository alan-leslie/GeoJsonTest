package com.example.geojsontest.adapter

import com.example.geojsontest.model.*
import com.squareup.moshi.*

internal class FeatureJsonAdapter(
  moshi: Moshi
) : JsonAdapter<Feature>() {
  private val pointJsonAdapter: JsonAdapter<Point> = moshi.adapter(Point::class.java)
  private val lineStringJsonAdapter: JsonAdapter<LineString> = moshi.adapter(LineString::class.java)
  private val polygonJsonAdapter: JsonAdapter<Polygon> = moshi.adapter(Polygon::class.java)
  private val multiPointJsonAdapter: JsonAdapter<MultiPoint> = moshi.adapter(MultiPoint::class.java)
  private val multiLineStringJsonAdapter: JsonAdapter<MultiLineString> = moshi.adapter(MultiLineString::class.java)
  private val multiPolygonJsonAdapter: JsonAdapter<MultiPolygon> = moshi.adapter(MultiPolygon::class.java)
  private val geometryCollectionJsonAdapter: JsonAdapter<GeometryCollection>  = moshi.adapter(GeometryCollection::class.java)
  private val wildCardJsonAdapter = moshi.adapter<Any>(Any::class.java)

  companion object {
    private const val KEY_ID = "id"
    private const val KEY_TYPE = "type"
    private const val KEY_GEOMETRY = "geometry"
    private const val KEY_PROPERTIES = "properties"
    private val KEYS_OPTIONS = JsonReader.Options.of(KEY_ID, KEY_TYPE, KEY_GEOMETRY, KEY_PROPERTIES)
  }

  @FromJson
  override fun fromJson(reader: JsonReader): Feature? {

    var id: String? = null
    var type: GeometryType? = null
    var geometry: Geometry? = null
    var properties = mapOf<String, Any>()

    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(KEYS_OPTIONS)) {
        0 -> {
          id = reader.nextString()
        }
        1 -> {
          try {
            type = GeometryType.convertFromString(reader.nextString())
          } catch (exception: IllegalArgumentException) {
            throw JsonDataException(("'type' is not of Feature at ${reader.path}"), exception)
          }

        }
        2 -> {
          val jsonValue = reader.readJsonValue()
          if (jsonValue != null) {
            val jsonValueMap = jsonValue as Map<String, String>
            val geometryTypeString = jsonValueMap[FeatureJsonAdapter.KEY_TYPE]
            if (geometryTypeString != null) {
              val geometryType = GeometryType.convertFromString(geometryTypeString)
              when (geometryType) {
                GeometryType.POINT -> {
                  val point = pointJsonAdapter.fromJsonValue(jsonValueMap)
                  if (point != null) geometry = point
                }
                GeometryType.LINESTRING -> {
                  val lineString = lineStringJsonAdapter.fromJsonValue(jsonValueMap)
                  if (lineString != null) geometry = lineString
                }
                GeometryType.POLYGON -> {
                  val polygon = polygonJsonAdapter.fromJsonValue(jsonValueMap)
                  if (polygon != null) geometry = polygon
                }
                GeometryType.MULTI_POINT -> {
                  val multiPoint = multiPointJsonAdapter.fromJsonValue(jsonValueMap)
                  if (multiPoint != null) geometry = multiPoint
                }
                GeometryType.MULTI_LINE_STRING -> {
                  val multiLineString = multiLineStringJsonAdapter.fromJsonValue(jsonValueMap)
                  if (multiLineString != null) geometry = multiLineString
                }
                GeometryType.MULTI_POLYGON -> {
                  val multiPolygon = multiPolygonJsonAdapter.fromJsonValue(jsonValueMap)
                  if (multiPolygon != null) geometry = multiPolygon
                }
                GeometryType.GEOMETRY_COLLECTION -> {
                  val geometryCollection = geometryCollectionJsonAdapter.fromJsonValue(jsonValueMap)
                  if (geometryCollection != null) geometry = geometryCollection
                }
                GeometryType.FEATURE -> {
                  // Do Nothing, consider to throw an exception?
                }
                GeometryType.FEATURE_COLLECTION -> {
                  // Do Nothing, consider to throw an exception?
                }
              }
            }
          }

        }
        3 -> {
          properties = reader.readJsonValue() as Map<String, Any>
        }
        else -> {
          reader.skipName()
          reader.skipValue()
        }
      }
    }
    reader.endObject()

    if (type == null) {
      throw JsonDataException("Requires field : 'type' is missing at ${reader.path}")
    }

    if (type != GeometryType.FEATURE) {
      throw JsonDataException("'type' is not of Feature at ${reader.path}")
    }

    return Feature(
      id = id,
      type = GeometryType.FEATURE,
      geometry = geometry,
      properties = properties
    )
  }

  @ToJson
  override fun toJson(writer: JsonWriter, value: Feature?) {
    if (value == null) {
      writer.nullValue()
    } else {
      writer.beginObject()
      writer.name(FeatureJsonAdapter.KEY_TYPE) // "type":
      writer.value(GeometryType.FEATURE.convertToString()) // "Feature",

      writer.name(FeatureJsonAdapter.KEY_GEOMETRY) // "geometry":
      val geometry = value.geometry

      if (geometry != null) {

        val type = geometry?.getGeometryType()

        when (geometry) {
          is Point -> pointJsonAdapter.toJson(writer, geometry)
          is LineString -> lineStringJsonAdapter.toJson(writer, geometry)
          is Polygon -> polygonJsonAdapter.toJson(writer, geometry)
          is MultiPoint -> multiPointJsonAdapter.toJson(writer, geometry)
          is MultiLineString -> multiLineStringJsonAdapter.toJson(writer, geometry)
          is MultiPolygon -> multiPolygonJsonAdapter.toJson(writer, geometry)
          is GeometryCollection -> geometryCollectionJsonAdapter.toJson(writer, geometry)
          else -> throw JsonDataException("GeometryCollection cannot serialize geometry of type :$type")
        }
      } else {
        writer.serializeNulls = true
        writer.nullValue()
      }

      writer.name(FeatureJsonAdapter.KEY_PROPERTIES) // "properties":
      val properties = value.properties
      writer.beginObject()
      properties.forEach { (propertyName, propertyValue) ->
        writer.name(propertyName)
        wildCardJsonAdapter.toJson(writer, propertyValue)
      }
      writer.endObject()

      writer.endObject()
    }
  }
}
