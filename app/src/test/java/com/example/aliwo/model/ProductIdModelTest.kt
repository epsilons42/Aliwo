package com.example.aliwo.model


import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


internal class ProductIdModelTest {
    private lateinit var productIdModel: ProductIdModel
    private lateinit var productIdModel2: ProductIdModel

    @Before
    fun setUp() {
        productIdModel = ProductIdModel(1)
        productIdModel2 = ProductIdModel(10)
    }

    @Test
    fun modelDataVerificationDone() {
        val productIdModel = ProductIdModel(1)
        assertThat(productIdModel.id).isEqualTo(1)
    }

    @Test
    fun modelDataVerificationNotDone() {
        val productIdModel = ProductIdModel(1)
        assertThat(productIdModel.id).isNotEqualTo(11)

    }

    @Test
    fun modelDataCompareNotEqual() {
        assertThat(productIdModel).isNotEqualTo(productIdModel2)
    }
}