package com.example.aliwo.viewmodel


import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ClickableSpan
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aliwo.R
import com.example.aliwo.util.DialogUtils
import com.example.aliwo.service.firebasedao.FirebaseUserManager
import java.util.*

class SignUpViewModel : ViewModel() {
    private val dialogUtils = DialogUtils()
    private val firebaseUserManager = FirebaseUserManager()
    val errorMessageMLD = MutableLiveData<String>()
    val charSequenceMLD = MutableLiveData<CharSequence>()

    fun firebaseAuthentication(
        context: Context,
        email: String,
        password: String
    ) {
        firebaseUserManager.firebaseEmailAuthentication(context, email, password, errorMessageMLD)
    }

    fun userAgreement(context: Context) {
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                widget.cancelPendingInputEvents()
                dialogUtils.userAgreementBottomSheet(context)
            }
        }

        if (Locale.getDefault().language == "tr") {
            charSequenceMLD.value = languageTr(context, clickableSpan)
        } else {
            charSequenceMLD.value = languageEng(context, clickableSpan)
        }
    }

    private fun languageTr(context: Context, clickableSpan: ClickableSpan): CharSequence {
        val spannableString = SpannableString(context.getString(R.string.user_agreement))
        spannableString.setSpan(
            clickableSpan,
            0,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val charSequence: CharSequence = TextUtils.expandTemplate(
            "^1ni okudum ve onaylıyorum", spannableString
        )
        return charSequence
    }

    private fun languageEng(context: Context, clickableSpan: ClickableSpan): CharSequence {
        val spannableString = SpannableString(context.getString(R.string.user_agreement))
        spannableString.setSpan(
            clickableSpan,
            0,
            spannableString.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val charSequence: CharSequence = TextUtils.expandTemplate(
            "I have read and agree to the ^1", spannableString
        )
        return charSequence
    }
}