package com.example.aliwo.model

import com.google.common.truth.Truth.assertThat
import com.google.firebase.Timestamp
import org.junit.Before
import org.junit.Test

internal class ProductBasketModelTest {
    private lateinit var productBasketModel : ProductBasketModel
    private lateinit var productBasketModel2 : ProductBasketModel
    @Before
    fun setUp(){
        productBasketModel =  ProductBasketModel(
            1, "testTitle", 25.5, "testImage", "testCategory",
            "testDescription", 2.6F, 1, Timestamp.now()
        )
        productBasketModel2 = ProductBasketModel(
            11, "testTitle1", 251.5, "testImage1", "testCategory1",
            "testDescription1", 1.6F, 10, Timestamp.now())
    }

    @Test
    fun modelDataVerificationDone() {
        assertThat(productBasketModel.id).isEqualTo(1)
        assertThat(productBasketModel.title).isEqualTo("testTitle")
        assertThat(productBasketModel.price).isEqualTo(25.5)
        assertThat(productBasketModel.description).isEqualTo("testDescription")
        assertThat(productBasketModel.category).isEqualTo("testCategory")
        assertThat(productBasketModel.image_url).isEqualTo("testImage")
        assertThat(productBasketModel.rate).isEqualTo(2.6F)
        assertThat(productBasketModel.count).isEqualTo(1)
    }

    @Test
    fun modelDataVerificationNotDone() {
        assertThat(productBasketModel.id).isNotEqualTo(11)
        assertThat(productBasketModel.title).isNotEqualTo("testTitle1")
        assertThat(productBasketModel.price).isNotEqualTo(251.5)
        assertThat(productBasketModel.description).isNotEqualTo("testDescription1")
        assertThat(productBasketModel.category).isNotEqualTo("testCategory1")
        assertThat(productBasketModel.image_url).isNotEqualTo("testImage1")
        assertThat(productBasketModel.rate).isNotEqualTo(2.7F)
        assertThat(productBasketModel.count).isNotEqualTo(2)
    }

    @Test
    fun  modelDataCompareNotEqual(){
        assertThat(productBasketModel).isNotEqualTo(productBasketModel2)
    }
}