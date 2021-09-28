package com.example.geojsontest

import com.example.geojsontest.model.FeatureCollection
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import java.io.FileInputStream
import java.io.InputStream

const val ASSET_BASE_PATH = "../app/src/main/assets/"

internal class FeatureCollectionRepositoryTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()


    private val featureCollectionRepository: FeatureCollectionRepository = object :
        FeatureCollectionRepositoryImpl(mockk()) {

        override fun getInputStreamForJsonFile(fileName: String): InputStream {
            return FileInputStream(ASSET_BASE_PATH + fileName)
        }
    }

    @Test
    fun `given live data feature collection is initialised, when observer added, then observer notified`() = runBlockingTest {
        val observer = mockk<Observer<FeatureCollection>>()
        every{ observer.onChanged(any()) } just Runs

        featureCollectionRepository.getFeatureCollection().observe(GeoJsonLifeCycleOwner(), observer)

        verify {observer.onChanged(any()) }
    }

    @Test
    fun `given live data feature collection is called, when flat json file parsed, then feature collection created`() = runBlockingTest {

        val featureCollection = featureCollectionRepository.getFeatureCollection().value

        assertEquals(1, featureCollection?.features?.size)
    }
}