package com.example.aliwo.model

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

internal class ProductRVChildModelTest {
    private lateinit var productRVChildModel: ProductRVChildModel
    private lateinit var productRVChildModel2: ProductRVChildModel

    @Before
    fun setUp() {
        productRVChildModel = ProductRVChildModel(
            1,
            "testTitle",
            "testImage",
            25.5,
            2.6F,
            "testDescription",
            "testCategory"
        )
        productRVChildModel2 = ProductRVChildModel(
            11,
            "testTitle1",
            "testImage1",
            251.5,
            1.6F,
            "testDescription1",
            "testCategory1"
        )
    }

    @Test
    fun modelDataVerificationDone() {
        assertThat(productRVChildModel.productId).isEqualTo(1)
        assertThat(productRVChildModel.productName).isEqualTo("testTitle")
        assertThat(productRVChildModel.productPrice).isEqualTo(25.5)
        assertThat(productRVChildModel.description).isEqualTo("testDescription")
        assertThat(productRVChildModel.category).isEqualTo("testCategory")
        assertThat(productRVChildModel.productImage).isEqualTo("testImage")
        assertThat(productRVChildModel.productRating).isEqualTo(2.6F)

    }

    @Test
    fun modelDataVerificationNotDone() {
        assertThat(productRVChildModel.productId).isNotEqualTo(11)
        assertThat(productRVChildModel.productName).isNotEqualTo("testTitle1")
        assertThat(productRVChildModel.productPrice).isNotEqualTo(251.5)
        assertThat(productRVChildModel.description).isNotEqualTo("testDescription1")
        assertThat(productRVChildModel.category).isNotEqualTo("testCategory1")
        assertThat(productRVChildModel.productImage).isNotEqualTo("testImage1")
        assertThat(productRVChildModel.productRating).isNotEqualTo(2.7F)
    }

    @Test
    fun modelDataCompareNotEqual() {
        assertThat(productRVChildModel).isNotEqualTo(productRVChildModel2)
    }
}