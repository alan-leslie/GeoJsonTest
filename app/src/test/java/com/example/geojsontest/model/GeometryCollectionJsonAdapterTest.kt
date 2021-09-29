package com.example.geojsontest.model

import com.example.geojsontest.adapter.GeoshiJsonAdapterFactory
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class GeometryCollectionJsonAdapterTest {

  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val moshi = Moshi.Builder().add(geoshiJsonAdapterFactory).build()

  @Test
  fun convertJsonStringToGeometryCollection() {
    //Given
    val jsonString =
      "{\"type\":\"GeometryCollection\",\"geometries\":[{\"type\":\"Point\",\"coordinates\":[100.0,0.0]},{\"type\":\"LineString\",\"coordinates\":[[101.0,0.0],[102.0,1.0]]}]}"

    val expected = GeometryCollection(
      geometries = listOf(
        Point(
          type = GeometryType.POINT,
          coordinates = Position(100.0, 0.0)
        ),
        LineString(
          type = GeometryType.LINESTRING,
          coordinates = listOf(
            Position(101.0, 0.0),
            Position(102.0, 1.0)
          )
        )
      )
    )

    //When
    val actual = moshi.adapter(GeometryCollection::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun convertJsonStringWithEmptyArrayToGeometryCollection() {
    //Given
    val jsonString =
      "{\"type\":\"GeometryCollection\",\"geometries\":[]}"

    val expected = GeometryCollection(
      geometries = emptyList()
    )

    //When
    val actual = moshi.adapter(GeometryCollection::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test(expected = JsonDataException::class)
  fun convertInvalidJsonStringWithEmptyType() {
    //Given
    val jsonString =
      "{\"geometries\":[{\"type\":\"Point\",\"coordinates\":[100.0,0.0]},{\"type\":\"LineString\",\"coordinates\":[[101.0,0.0],[102.0,1.0]]}]}"

    //When
    val actual = moshi.adapter(GeometryCollection::class.java).fromJson(jsonString)
  }

  @Test(expected = JsonDataException::class)
  fun convertInvalidJsonGarbageType() {
    //Given
    val jsonString =
      "{\"type\":\"Garbage\",\"geometries\":[{\"type\":\"Point\",\"coordinates\":[100.0,0.0]},{\"type\":\"LineString\",\"coordinates\":[[101.0,0.0],[102.0,1.0]]}]}"

    //When
    val actual = moshi.adapter(GeometryCollection::class.java).fromJson(jsonString)
  }


  @Test
  fun convertGeometryCollectionToJsonString() {
    //Given
    val geometryCollection = GeometryCollection(
      geometries = listOf(
        Point(
          type = GeometryType.POINT,
          coordinates = Position(100.0, 0.0)
        ),
        LineString(
          type = GeometryType.LINESTRING,
          coordinates = listOf(
            Position(101.0, 0.0),
            Position(102.0, 1.0)
          )
        )
      )
    )

    val expected =
      "{\"type\":\"GeometryCollection\",\"geometries\":[{\"type\":\"Point\",\"coordinates\":[100.0,0.0]},{\"type\":\"LineString\",\"coordinates\":[[101.0,0.0],[102.0,1.0]]}]}"

    //When
    val actual = moshi.adapter(GeometryCollection::class.java).toJson(geometryCollection)

    //Then
    Assert.assertEquals(expected, actual)
  }
}