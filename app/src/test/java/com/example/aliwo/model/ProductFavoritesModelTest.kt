package com.example.aliwo.model

import com.google.common.truth.Truth.assertThat
import com.google.firebase.Timestamp
import org.junit.Before
import org.junit.Test


internal class ProductFavoritesModelTest {
    private lateinit var productFavoritesModel: ProductFavoritesModel
    private lateinit var productFavoritesModel2: ProductFavoritesModel

    @Before
    fun setUp() {
        productFavoritesModel = ProductFavoritesModel(
            1, "testTitle", 25.5, "testImage", "testCategory",
            "testDescription", 2.6F, Timestamp.now()
        )
        productFavoritesModel2 = ProductFavoritesModel(
            10, "testTitle1", 251.5, "testImage1", "testCategory1",
            "testDescription1", 1.6F, Timestamp.now()
        )
    }

    @Test
    fun modelDataVerificationDone() {
        assertThat(productFavoritesModel.id).isEqualTo(1)
        assertThat(productFavoritesModel.title).isEqualTo("testTitle")
        assertThat(productFavoritesModel.price).isEqualTo(25.5)
        assertThat(productFavoritesModel.description).isEqualTo("testDescription")
        assertThat(productFavoritesModel.category).isEqualTo("testCategory")
        assertThat(productFavoritesModel.image_url).isEqualTo("testImage")
        assertThat(productFavoritesModel.rate).isEqualTo(2.6F)

    }

    @Test
    fun modelDataVerificationNotDone() {
        assertThat(productFavoritesModel.id).isNotEqualTo(11)
        assertThat(productFavoritesModel.title).isNotEqualTo("testTitle1")
        assertThat(productFavoritesModel.price).isNotEqualTo(251.5)
        assertThat(productFavoritesModel.description).isNotEqualTo("testDescription1")
        assertThat(productFavoritesModel.category).isNotEqualTo("testCategory1")
        assertThat(productFavoritesModel.image_url).isNotEqualTo("testImage1")
        assertThat(productFavoritesModel.rate).isNotEqualTo(2.7F)
    }

    @Test
    fun modelDataCompareNotEqual() {
        assertThat(productFavoritesModel).isNotEqualTo(productFavoritesModel2)
    }
}