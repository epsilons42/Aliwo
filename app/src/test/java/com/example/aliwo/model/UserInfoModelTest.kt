package com.example.aliwo.model

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


internal class UserInfoModelTest {
    private lateinit var userInfoModel: UserInfoModel
    private lateinit var userInfoModel2: UserInfoModel

    @Before
    fun setUp() {
        userInfoModel = UserInfoModel("testName", "testLastName", "testPhone")
        userInfoModel2 = UserInfoModel("testName1", "testLastName1", "testPhone1")
    }

    @Test
    fun modelDataVerificationDone() {
        assertThat(userInfoModel.name).isEqualTo("testName")
        assertThat(userInfoModel.lastName).isEqualTo("testLastName")
        assertThat(userInfoModel.phone).isEqualTo("testPhone")
    }

    @Test
    fun modelDataVerificationNotDone() {
        assertThat(userInfoModel.name).isNotEqualTo("testName1")
        assertThat(userInfoModel.lastName).isNotEqualTo("testLastName1")
        assertThat(userInfoModel.phone).isNotEqualTo("testPhone1")
    }

    @Test
    fun modelDataCompareNotEqual() {
        assertThat(userInfoModel).isNotEqualTo(userInfoModel2)
    }
}