package com.example.geojsontest.adapter

import com.squareup.moshi.FromJson
//import com.squareup.moshi.JsonDataException
import com.squareup.moshi.ToJson
import com.example.geojsontest.model.GeometryType

class GeometryTypeAdapter  {
    @ToJson
    fun toJson(type: GeometryType): String {
        return type.convertToString()
    }

    @FromJson
    fun fromJson(geometryTypeString: String): GeometryType {
        return GeometryType.convertFromString(geometryTypeString)
    }
}