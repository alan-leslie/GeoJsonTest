package com.example.geojsontest.adapter

import com.example.geojsontest.model.Position
import com.squareup.moshi.*

internal class PositionJsonAdapter : JsonAdapter<Position>() {
  @FromJson
  override fun fromJson(reader: JsonReader): Position? {
    val latLngList = mutableListOf<Double>()

    reader.beginArray()
    while (reader.hasNext()) {
      latLngList.add(reader.nextDouble())
    }
    reader.endArray()


    if (latLngList.size < 2) {
      throw JsonDataException("Coordinates must have at least two values, missing values at ${reader.path}")
    }


    val longitude = latLngList[0]
    val latitude = latLngList[1]
    val altitude = latLngList.getOrNull(2)

    val position = Position(
      longitude = longitude,
      latitude = latitude,
      altitude = altitude
    )

    return position
  }

  @ToJson
  override fun toJson(writer: JsonWriter, value: Position?) {
    if (value != null) {
      writer.beginArray()
      writer.value(value.longitude)
      writer.value(value.latitude)

      if (value.altitude != null) {
        writer.value(value.altitude)
      }

      writer.endArray()
    } else {
      writer.nullValue()
    }
  }
}