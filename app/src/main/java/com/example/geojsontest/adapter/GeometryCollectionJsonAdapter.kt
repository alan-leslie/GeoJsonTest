package com.example.geojsontest.adapter

import com.example.geojsontest.model.*
import com.squareup.moshi.*

class GeometryCollectionJsonAdapter constructor(
  moshi: Moshi
) : JsonAdapter<GeometryCollection>() {
  private val pointJsonAdapter: JsonAdapter<Point> = moshi.adapter(Point::class.java)
  private val lineStringJsonAdapter: JsonAdapter<LineString> = moshi.adapter(LineString::class.java)
  private val polygonJsonAdapter: JsonAdapter<Polygon> = moshi.adapter(Polygon::class.java)
  private val multiPointJsonAdapter: JsonAdapter<MultiPoint> = moshi.adapter(MultiPoint::class.java)
  private val multiLineStringJsonAdapter: JsonAdapter<MultiLineString> = moshi.adapter(MultiLineString::class.java)
  private val multiPolygonJsonAdapter: JsonAdapter<MultiPolygon> = moshi.adapter(MultiPolygon::class.java)

  companion object {
    private const val KEY_TYPE = "type"
    private const val KEY_GEOMETRIES = "geometries"
    private val KEYS_OPTIONS = JsonReader.Options.of(KEY_TYPE, KEY_GEOMETRIES)
  }


  @FromJson
  override fun fromJson(reader: JsonReader): GeometryCollection? {
    var type: GeometryType? = null
    var geometries = mutableListOf<Geometry>()

    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(GeometryCollectionJsonAdapter.KEYS_OPTIONS)) {
        0 -> {
          try {
            type = GeometryType.convertFromString(reader.nextString())
          } catch (exception: IllegalArgumentException) {
            throw JsonDataException(("'type' is not of GeometryCollection at ${reader.path}"), exception)
          }
        }
        1 -> {
          reader.beginArray()

          while (reader.hasNext()) {
            val jsonValueMap = reader.readJsonValue() as Map<String, String>
            val geometryTypeString = jsonValueMap[KEY_TYPE]
            if (geometryTypeString != null) {
              val type = GeometryType.convertFromString(geometryTypeString)
              when (type) {
                GeometryType.POINT -> {
                  val point = pointJsonAdapter.fromJsonValue(jsonValueMap)
                  if (point != null) geometries.add(point)
                }
                GeometryType.LINESTRING -> {
                  val lineString = lineStringJsonAdapter.fromJsonValue(jsonValueMap)
                  if (lineString != null) geometries.add(lineString)
                }
                GeometryType.POLYGON -> {
                  val polygon = polygonJsonAdapter.fromJsonValue(jsonValueMap)
                  if (polygon != null) geometries.add(polygon)
                }
                GeometryType.MULTI_POINT -> {
                  val multiPoint = multiPointJsonAdapter.fromJsonValue(jsonValueMap)
                  if (multiPoint != null) geometries.add(multiPoint)
                }
                GeometryType.MULTI_LINE_STRING -> {
                  val multiLineString = multiLineStringJsonAdapter.fromJsonValue(jsonValueMap)
                  if (multiLineString != null) geometries.add(multiLineString)
                }
                GeometryType.MULTI_POLYGON -> {
                  val multiPolygon = multiPolygonJsonAdapter.fromJsonValue(jsonValueMap)
                  if (multiPolygon != null) geometries.add(multiPolygon)
                }
                GeometryType.GEOMETRY_COLLECTION -> {
                  // Do Nothing, consider to throw an exception?
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

          reader.endArray()
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

    if (type != GeometryType.GEOMETRY_COLLECTION) {
      throw JsonDataException("'type' is not of GeometryCollection at ${reader.path}")
    }

    return GeometryCollection(type, geometries)
  }

  @ToJson
  override fun toJson(writer: JsonWriter, value: GeometryCollection?) {
    if (value == null) {
      writer.nullValue()
    } else {
      writer.beginObject()
      writer.name(GeometryCollectionJsonAdapter.KEY_TYPE) // "type":
      writer.value(value.getGeometryType().convertToString()) // "GeometryCollection",

      writer.name(GeometryCollectionJsonAdapter.KEY_GEOMETRIES) // "geometries":
      writer.beginArray() // [
      value.geometries.forEach { geometry ->
        val type = geometry.getGeometryType()

        when (geometry) {
          is Point -> pointJsonAdapter.toJson(writer, geometry)
          is LineString -> lineStringJsonAdapter.toJson(writer, geometry)
          is Polygon -> polygonJsonAdapter.toJson(writer, geometry)
          is MultiPoint -> multiPointJsonAdapter.toJson(writer, geometry)
          is MultiLineString -> multiLineStringJsonAdapter.toJson(writer, geometry)
          is MultiPolygon -> multiPolygonJsonAdapter.toJson(writer, geometry)
          else -> throw JsonDataException("GeometryCollection cannot serialize geometry of type :$type")
        }

      }
      writer.endArray()

      writer.endObject()
    }
  }

}