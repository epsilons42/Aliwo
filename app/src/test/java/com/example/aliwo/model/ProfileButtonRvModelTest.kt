package com.example.aliwo.model

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


internal class ProfileButtonRvModelTest {
    private lateinit var profileButtonRvModel: ProfileButtonRvModel
    private lateinit var profileButtonRvModel2: ProfileButtonRvModel

    @Before
    fun setUp() {
        profileButtonRvModel = ProfileButtonRvModel(1, "testTitle", 2)
        profileButtonRvModel2 = ProfileButtonRvModel(11, "testTitle1", 21)
    }

    @Test
    fun modelDataVerificationDone() {
        assertThat(profileButtonRvModel.id).isEqualTo(1)
        assertThat(profileButtonRvModel.title).isEqualTo("testTitle")
        assertThat(profileButtonRvModel.image).isEqualTo(2)
    }

    @Test
    fun modelDataVerificationNotDone() {
        assertThat(profileButtonRvModel.id).isNotEqualTo(11)
        assertThat(profileButtonRvModel.title).isNotEqualTo("testTitle1")
        assertThat(profileButtonRvModel.image).isNotEqualTo(11)

    }

    @Test
    fun modelDataCompareNotEqual() {
        assertThat(profileButtonRvModel).isNotEqualTo(profileButtonRvModel2)
    }

}