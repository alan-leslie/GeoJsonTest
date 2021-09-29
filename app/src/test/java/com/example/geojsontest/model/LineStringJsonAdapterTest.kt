package com.example.geojsontest.model

import com.example.geojsontest.adapter.GeometryTypeAdapter
import com.example.geojsontest.adapter.GeoshiJsonAdapterFactory
import com.example.geojsontest.adapter.PositionJsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test


class LineStringJsonAdapterTest {

  private val positionAdapter = PositionJsonAdapter()
  private val geometryTypeAdapter = GeometryTypeAdapter()
  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val moshi = Moshi.Builder()
    .add(positionAdapter)
    .add(geometryTypeAdapter)
    .build()

  @Test
  fun testValidJsonToLineString() {
    //Given
    val jsonString = "{\"type\":\"LineString\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"

    val expected = LineString(
      type = GeometryType.LINESTRING,
      coordinates = listOf(
        Position(100.0, 0.0),
        Position(101.0, 1.0)
      )
    )

    //When
    val actual = moshi.adapter(LineString::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonMissingType() {
    //Given
    val jsonString = "{\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"

    //When
    val actual = moshi.adapter(LineString::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeGarbageValue() {
    //Given
    val jsonString = "{\"type\":\"Garbage\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"

    //When
    val actual = moshi.adapter(LineString::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeCoordinatesMissing() {
    //Given
    val jsonString = "{\"type\":\"LineString\"}"

    //When
    val actual = moshi.adapter(LineString::class.java).fromJson(jsonString)

  }

  @Test(expected = IllegalArgumentException::class)
  fun testInvalidJsonOnlyOneCoordinate() {
    //Given
    val jsonString = "{\"type\":\"LineString\",\"coordinates\":[[100.0,0.0]]}"

    //When
    val actual = moshi.adapter(LineString::class.java).fromJson(jsonString)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeNotLineStringValue() {

    //Given
    val jsonString = "{\"type\":\"Polygon\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"

    //When
    val actual = moshi.adapter(Point::class.java).fromJson(jsonString)
  }

  @Test
  fun testLineStringToJson() {
    //Given
    val lineString = LineString(
      type = GeometryType.LINESTRING,
      coordinates = listOf(
        Position(100.0, 0.0),
        Position(101.0, 1.0)
      )
    )

    val expected = "{\"type\":\"LineString\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"

    //When
    val actual = moshi.adapter(LineString::class.java).toJson(lineString)

    //Then
    Assert.assertEquals(expected, actual)
  }
}