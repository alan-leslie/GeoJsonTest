package com.example.geojsontest.model

import com.example.geojsontest.adapter.GeometryTypeAdapter
import com.example.geojsontest.adapter.GeoshiJsonAdapterFactory
import com.example.geojsontest.adapter.PositionJsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class PolygonJsonAdapterTest {
  private val positionAdapter = PositionJsonAdapter()
  private val geometryTypeAdapter = GeometryTypeAdapter()
  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val moshi = Moshi.Builder()
    .add(positionAdapter)
    .add(geometryTypeAdapter)
    .build()

  @Test
  fun testValidJsonToMultiLineString() {
    //Given
    val jsonString =
      "{\"type\":\"Polygon\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.8,0.8],[100.8,0.2],[100.2,0.2],[100.2,0.8],[100.8,0.8]]]}"

    val expected = Polygon(
      type = GeometryType.POLYGON,
      coordinates = listOf(
        listOf(
          Position(100.0, 0.0),
          Position(101.0, 0.0),
          Position(101.0, 1.0),
          Position(100.0, 1.0),
          Position(100.0, 0.0)
        ),
        listOf(
          Position(100.8, 0.8),
          Position(100.8, 0.2),
          Position(100.2, 0.2),
          Position(100.2, 0.8),
          Position(100.8, 0.8)
        )
      )
    )

    //When
    val actual = moshi.adapter(Polygon::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonMissingType() {
    //Given
    val jsonString =
      "{\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.8,0.8],[100.8,0.2],[100.2,0.2],[100.2,0.8],[100.8,0.8]]]}"

    //When
    val actual = moshi.adapter(Polygon::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeGarbageValue() {
    //Given
    val jsonString =
      "{\"type\":\"Garbage\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.8,0.8],[100.8,0.2],[100.2,0.2],[100.2,0.8],[100.8,0.8]]]}"

    //When
    val actual = moshi.adapter(Polygon::class.java).fromJson(jsonString)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeNotPolygonValue() {
    //Given
    val jsonString =
      "{\"type\":\"Point\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.8,0.8],[100.8,0.2],[100.2,0.2],[100.2,0.8],[100.8,0.8]]]}"

    //When
    val actual = moshi.adapter(Point::class.java).fromJson(jsonString)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeCoordinatesMissing() {
    //Given
    val jsonString =
      "{\"type\":\"Polygon\"}"

    //When
    val actual = moshi.adapter(Polygon::class.java).fromJson(jsonString)

  }

  @Test
  fun testMultiLineStringToJson() {
    //Given
    val polygon = Polygon(
      type = GeometryType.POLYGON,
      coordinates = listOf(
        listOf(
          Position(100.0, 0.0),
          Position(101.0, 0.0),
          Position(101.0, 1.0),
          Position(100.0, 1.0),
          Position(100.0, 0.0)
        ),
        listOf(
          Position(100.8, 0.8),
          Position(100.8, 0.2),
          Position(100.2, 0.2),
          Position(100.2, 0.8),
          Position(100.8, 0.8)
        )
      )
    )

    val expected =
      "{\"type\":\"Polygon\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.8,0.8],[100.8,0.2],[100.2,0.2],[100.2,0.8],[100.8,0.8]]]}"

    //When
    val actual = moshi.adapter(Polygon::class.java).toJson(polygon)

    //Then
    Assert.assertEquals(expected, actual)
  }

}