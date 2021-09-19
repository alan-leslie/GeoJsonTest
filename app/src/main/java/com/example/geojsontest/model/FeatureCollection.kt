package com.example.geojsontest.model

data class FeatureCollection(
  val features: List<Feature>
) : Geometry() {

  override fun getType(): GeometryType = GeometryType.FEATURE_COLLECTION

}
