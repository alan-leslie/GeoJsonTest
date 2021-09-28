package com.example.geojsontest.model

import com.example.geojsontest.adapter.GeoshiJsonAdapterFactory
import com.example.geojsontest.adapter.PositionJsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
//import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PositionJsonAdapterTest {
  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val positionAdapter = PositionJsonAdapter()
//  private val kotlinJsonAdapterFactory = KotlinJsonAdapterFactory()
  private val moshi = Moshi.Builder().add(positionAdapter).build()

  @Test
  fun testValidJsonToPosition() {
    //Given
//    val jsonString = "{\"latitude\":0.0,\"longitude\":100.0}"
    val jsonString = "[100.0, 0.0]"
//
//    val expected : MutableList<Double> = mutableListOf<Double>()
//    expected.add(100.0)
//    expected.add(0.0)

    val expected = Position(
      longitude = 100.0,
      latitude = 0.0
    )

    //When
//    val actual = moshi.adapter<MutableList<Double>>(MutableList::class.java).fromJson(jsonString)
    val actual = moshi.adapter<Position>(Position::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun testValidJsonWithAltitudeToPosition() {
    //Given
    val jsonString = "[100.0, 0.0, 200.0]"
    val expected = Position(
      longitude = 100.0,
      latitude = 0.0,
      altitude = 200.0
    )
    //When
    val actual = moshi.adapter<Position>(Position::class.java).fromJson(jsonString)
    //Then

    Assert.assertEquals(expected, actual)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonStringWithLessThanTwoElements() {
    //Given
    val jsonString = "[100.0]"

    //When
    val expected = moshi.adapter<Position>(Position::class.java).fromJson(jsonString)
  }

  @Test
  fun testPositionToJson() {

    //Given
    val position = Position(
      longitude = 100.0,
      latitude = 0.0
    )

    val expected = "[100.0,0.0]"

    //When
    val actual = moshi.adapter<Position>(Position::class.java).toJson(position)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun testPositionWithAltitudeToJson() {

    //Given
    val position = Position(
      longitude = 100.0,
      latitude = 0.0,
      altitude = 200.0
    )

    val expected = "[100.0,0.0,200.0]"

    //When
    val actual = moshi.adapter<Position>(Position::class.java).toJson(position)

    //Then
    Assert.assertEquals(expected, actual)
  }

}