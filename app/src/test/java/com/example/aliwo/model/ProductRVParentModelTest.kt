package com.example.aliwo.model

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


internal class ProductRVParentModelTest{
    private lateinit var productRVParentModel : ProductRVParentModel
    private lateinit var productRVParentModel2 : ProductRVParentModel

    @Before
    fun setUp(){
        val testList = ArrayList<ProductRVChildModel>()
        testList.add(
            ProductRVChildModel(
                1,
                "testTitle",
                "testImage",
                25.5,
                2.6F,
                "testDescription",
                "testCategory"))

        productRVParentModel = ProductRVParentModel("testCategoryTitle","testCategory",testList)
        productRVParentModel2 = ProductRVParentModel("testCategoryTitle1","testCategory1",testList)

    }
    @Test
    fun modelDataVerificationDone() {
        assertThat(productRVParentModel.category).isEqualTo("testCategory")
        assertThat(productRVParentModel.categoryTitle).isEqualTo("testCategoryTitle")
        assertThat(productRVParentModel.childList.get(0).productId).isNotEqualTo(2)
    }

    @Test
    fun modelDataVerificationNotDone() {
        assertThat(productRVParentModel.category).isNotEqualTo("testCategory1")
        assertThat(productRVParentModel.categoryTitle).isNotEqualTo("testCategoryTitle1")
        assertThat(productRVParentModel.childList.get(0).productId).isNotEqualTo(2)


    }

    @Test
    fun modelDataCompareNotEqual() {
        assertThat(productRVParentModel).isNotEqualTo(productRVParentModel2)
    }

}