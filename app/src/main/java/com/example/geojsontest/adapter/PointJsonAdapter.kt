package com.example.geojsontest.adapter

import com.example.geojsontest.model.GeometryType
import com.example.geojsontest.model.Point
import com.example.geojsontest.model.Position
import com.squareup.moshi.*

internal class PointJsonAdapter constructor(
  moshi: Moshi
) : JsonAdapter<Point>() {
  private val positionJsonAdapter: JsonAdapter<Position> = moshi.adapter(Position::class.java)

  companion object {
    private const val KEY_TYPE = "type"
    private const val KEY_COORDINATES = "coordinates"
    private val KEYS_OPTIONS = JsonReader.Options.of(KEY_TYPE, KEY_COORDINATES)
  }

  @FromJson
  override fun fromJson(reader: JsonReader): Point? {
    reader.beginObject()
    var type: GeometryType? = null
    var position: Position? = null

    while (reader.hasNext()) {
      when (reader.selectName(KEYS_OPTIONS)) {
        0 -> {
          try {
            type = GeometryType.convertFromString(reader.nextString())
          } catch (exception: IllegalArgumentException) {
            throw JsonDataException(("'type' is not of Point at ${reader.path}"), exception)
          }
        }
        1 -> position = positionJsonAdapter.fromJson(reader)
        else -> {
          reader.skipName()
          reader.skipValue()
        }
      }
    }
    reader.endObject()

    if (type == null) {
      throw JsonDataException("Requires field : type is missing at ${reader.path}")
    }

    if (type != GeometryType.POINT) {
      throw JsonDataException("type is not of Point at ${reader.path}")
    }

    if (position == null) {
      throw JsonDataException("Requires field : coordinates is missing at ${reader.path}")
    }

    val point = Point(type, position)

    return point
  }

  @ToJson
  override fun toJson(writer: JsonWriter, value: Point?) {
    if (value != null) {
      writer.beginObject() // {

      writer.name(KEY_TYPE) // "type":
      writer.value(value.getGeometryType().convertToString()) //"Point",
      writer.name(KEY_COORDINATES) //"coordinates":
      positionJsonAdapter.toJson(writer, value.coordinates) //[100.0, 1.0]

      writer.endObject() // }
    } else {
      writer.nullValue()
    }

  }
}