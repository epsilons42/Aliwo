package com.example.aliwo.model

import org.junit.Test
import com.google.common.truth.Truth.assertThat
import org.junit.Before

internal class ProductsModelTest {
    private lateinit var apiProductsModel: ApiProductsModel
    private lateinit var apiProductsModel2: ApiProductsModel

    @Before
    fun setUp() {
        apiProductsModel = ApiProductsModel(
            1, "testTitle", 25.5, "testDescription",
            "testCategory", "testImage", Rating(2.6F, 150)
        )
        apiProductsModel2 = ApiProductsModel(
            11, "testTitle1", 25.51, "testDescription1",
            "testCategory1", "testImage1", Rating(1.6F, 10)
        )
    }

    @Test
    fun modelDataVerificationDone() {
        assertThat(apiProductsModel.id).isEqualTo(1)
        assertThat(apiProductsModel.title).isEqualTo("testTitle")
        assertThat(apiProductsModel.price).isEqualTo(25.5)
        assertThat(apiProductsModel.description).isEqualTo("testDescription")
        assertThat(apiProductsModel.category).isEqualTo("testCategory")
        assertThat(apiProductsModel.image).isEqualTo("testImage")
        assertThat(apiProductsModel.rating.rate).isEqualTo(2.6F)
        assertThat(apiProductsModel.rating.count).isEqualTo(150)
    }

    @Test
    fun modelDataVerificationNotDone() {
        assertThat(apiProductsModel.id).isNotEqualTo(11)
        assertThat(apiProductsModel.title).isNotEqualTo("testTitle1")
        assertThat(apiProductsModel.price).isNotEqualTo(251.5)
        assertThat(apiProductsModel.description).isNotEqualTo("testDescription1")
        assertThat(apiProductsModel.category).isNotEqualTo("testCategory1")
        assertThat(apiProductsModel.image).isNotEqualTo("testImage1")
        assertThat(apiProductsModel.rating.rate).isNotEqualTo(2.7F)
        assertThat(apiProductsModel.rating.count).isNotEqualTo(15)
    }

    @Test
    fun modelDataCompareNotEqual() {
        assertThat(apiProductsModel).isNotEqualTo(apiProductsModel2)
    }
}