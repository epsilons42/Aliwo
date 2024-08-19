package com.example.aliwo.model

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

internal class ProductDetailModelTest {
    private lateinit var productDetailModel: ProductDetailModel
    private lateinit var productDetailModel2: ProductDetailModel

    @Before
    fun setUp() {
        productDetailModel = ProductDetailModel(
            1, "testTitle", 25.5, "testDescription",
            "testCategory", "testImage", 2.6F
        )
        productDetailModel2 = ProductDetailModel(
            10, "testTitle1", 250.5, "testDescription1",
            "testCategory1", "testImage1", 1.6F
        )
    }

    @Test
    fun modelDataVerificationDone() {
        assertThat(productDetailModel.id).isEqualTo(1)
        assertThat(productDetailModel.title).isEqualTo("testTitle")
        assertThat(productDetailModel.price).isEqualTo(25.5)
        assertThat(productDetailModel.description).isEqualTo("testDescription")
        assertThat(productDetailModel.category).isEqualTo("testCategory")
        assertThat(productDetailModel.image).isEqualTo("testImage")
        assertThat(productDetailModel.rate).isEqualTo(2.6F)

    }

    @Test
    fun modelDataVerificationNotDone() {
        assertThat(productDetailModel.id).isNotEqualTo(11)
        assertThat(productDetailModel.title).isNotEqualTo("testTitle1")
        assertThat(productDetailModel.price).isNotEqualTo(251.5)
        assertThat(productDetailModel.description).isNotEqualTo("testDescription1")
        assertThat(productDetailModel.category).isNotEqualTo("testCategory1")
        assertThat(productDetailModel.image).isNotEqualTo("testImage1")
        assertThat(productDetailModel.rate).isNotEqualTo(2.7F)

    }

    @Test
    fun modelDataCompareNotEqual() {
        assertThat(productDetailModel).isNotEqualTo(productDetailModel2)
    }
}