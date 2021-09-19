package com.example.geojsontest.model

abstract class Geometry {
  abstract fun getType(): GeometryType
}