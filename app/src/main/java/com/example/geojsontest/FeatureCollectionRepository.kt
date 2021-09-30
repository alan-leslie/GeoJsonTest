package com.example.geojsontest

import com.example.geojsontest.adapter.GeoshiJsonAdapterFactory
import com.example.geojsontest.model.FeatureCollection

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.geojsontest.adapter.GeometryTypeAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.io.IOException
import java.io.InputStream

internal interface FeatureCollectionRepository {
    suspend fun getFeatureCollection(): LiveData<FeatureCollection>
}

internal object MoshiFactory {
    private val geometryTypeAdapter = GeometryTypeAdapter()
    private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
    private val moshi = Moshi.Builder()
        .add(geometryTypeAdapter)
        .add(geoshiJsonAdapterFactory)
        .build()

    fun getInstance() = moshi
}

internal open class FeatureCollectionRepositoryImpl(
    private val context: Context,
    private val moshi: Moshi = MoshiFactory.getInstance()) : FeatureCollectionRepository {

    private val featureCollectionLiveData = MutableLiveData<FeatureCollection>()

    /**
     * Sets and returns the LiveData object so observers will be notified of the last change
     */
    override suspend fun getFeatureCollection() : LiveData<FeatureCollection> {
        val featureCollectionJson = getFeatureCollectionJSON()

        val adapter: JsonAdapter<FeatureCollection> = moshi.adapter(FeatureCollection::class.java)
        val result = adapter.fromJson(featureCollectionJson)

        featureCollectionLiveData.value = result!!

        return featureCollectionLiveData
    }

    private fun getFeatureCollectionJSON(fileName : String = "geojson_feature_collection.json"): String {
        val inputStream = getInputStreamForJsonFile(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }

    @Throws(IOException::class)
    internal open fun getInputStreamForJsonFile(fileName: String): InputStream {
        return context.assets.open(fileName)
    }
}