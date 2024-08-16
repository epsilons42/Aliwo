package com.example.aliwo.util

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import com.example.aliwo.R
import com.example.aliwo.view.activity.MainActivity
import com.example.aliwo.service.firebasedao.FirebaseDataDeleter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class DialogUtils {
    private lateinit var firebaseAuth: FirebaseAuth
    private val intentUtils = IntentUtils()
    private val firebaseDataDeleter = FirebaseDataDeleter()
    fun deleteAccoundAlerdDialog(context: Context, userEmail: String) {
        firebaseAuth = FirebaseAuth.getInstance()
        val currendUser = firebaseAuth.currentUser!!
        val userEmailAddress = userEmail

        val alertDialog = android.app.AlertDialog.Builder(context)

        alertDialog.setTitle(context.getString(R.string.delete_account))
        alertDialog.setMessage(context.getString(R.string.delete_account_alert_message))
        alertDialog.setNegativeButton(context.getString(R.string.cancel)) { dialogInterface, i ->
            alertDialog.create().dismiss()
        }
        alertDialog.setNeutralButton(context.getString(R.string.yes)) { dialogInterface, i ->
            currendUser.delete().addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val googleSignInClient = GoogleSignIn.getClient(
                        context,
                        GoogleSignInOptions.DEFAULT_SIGN_IN
                    )
                    googleSignInClient.revokeAccess()

                    intentUtils.intentAndClear(context, MainActivity())
                    firebaseDataDeleter.deleteAllFireStoreCollection()
                }

            }.addOnFailureListener { exception ->
                Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
        if (currendUser.email == userEmailAddress) {
            alertDialog.show()
        } else {
            Toast.makeText(
                context,
                context.getString(R.string.enter_info_correctly),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    @SuppressLint("InflateParams")
    fun userAgreementBottomSheet(context: Context) {
        val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        val bottomSheetView =
            LayoutInflater.from(context).inflate(R.layout.user_agreement_bottom_sheet, null)
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.create()

        val userAgreementScv =
            bottomSheetView.findViewById(R.id.userAgreementScv) as NestedScrollView
        userAgreementScv.layoutParams.height = LayoutParams.MATCH_PARENT
        bottomSheetDialog.show()

    }
}