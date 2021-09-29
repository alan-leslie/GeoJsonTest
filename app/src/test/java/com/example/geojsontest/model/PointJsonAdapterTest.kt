package com.example.geojsontest.model

import com.example.geojsontest.adapter.GeometryTypeAdapter
//import com.example.geojsontest.adapter.GeoshiJsonAdapterFactory
//import com.example.geojsontest.adapter.PointJsonAdapter
import com.example.geojsontest.adapter.PositionJsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PointJsonAdapterTest {
  private val positionAdapter = PositionJsonAdapter()
  private val geometryTypeAdapter = GeometryTypeAdapter()
//  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val moshi = Moshi.Builder()
                      .add(positionAdapter)
                      .add(geometryTypeAdapter)
                      .build()

  @Test
  fun testValidJsonToPoint() {

    //Given
    val jsonString = "{\"type\":\"Point\",\"coordinates\":[100.0,0.0]}"

    val expected = Point(
      type = GeometryType.POINT,
      coordinates = Position(
        longitude = 100.0,
        latitude = 0.0
      )
    )

    //When
    val actual = moshi.adapter(Point::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }


  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeMissing() {

    //Given
    val jsonString = "{\"coordinates\":[100.0,0.0]}"

    //When
    val actual = moshi.adapter(Point::class.java).fromJson(jsonString)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeGarbageValue() {

    //Given
    val jsonString = "{\"type\":\"Unknown\",\"coordinates\":[100.0,0.0]}"

    //When
    val actual = moshi.adapter(Point::class.java).fromJson(jsonString)
  }

  @Test(expected = IllegalArgumentException::class)
  fun testInvalidJsonTypeNotPointValue() {

    //Given
    val jsonString = "{\"type\":\"Polygon\",\"coordinates\":[100.0,0.0]}"

    //When
    val actual = moshi.adapter(Point::class.java).fromJson(jsonString)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonCoordinateMissing() {

    //Given
    val jsonString = "{\"type\":\"Point\"}"

    //When
    val actual = moshi.adapter(Point::class.java).fromJson(jsonString)
  }

  @Test
  fun testPointToJson() {
    //Given
    val expected = "{\"type\":\"Point\",\"coordinates\":[100.0,0.0]}"
    val point = Point(
      type = GeometryType.POINT,
      coordinates = Position(
        longitude = 100.0,
        latitude = 0.0
      )
    )

    //When
    val adapter = moshi.adapter(Point::class.java)
    val actual = adapter.toJson(point)

    //Then
    Assert.assertEquals(expected, actual)

  }
}