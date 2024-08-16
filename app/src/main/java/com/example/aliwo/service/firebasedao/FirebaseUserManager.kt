package com.example.aliwo.service.firebasedao

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.aliwo.R
import com.example.aliwo.view.activity.MainActivity
import com.example.aliwo.util.IntentUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class FirebaseUserManager {
    private lateinit var firebaseAuth: FirebaseAuth
    fun currentUser(): Boolean {
        firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.currentUser != null
    }

    fun firebaseCurrentUserEmail(): String? {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        return currentUser?.email
    }

    fun googleLastSignedInAccount(context: Context): Boolean {
        val getLastSigned = GoogleSignIn.getLastSignedInAccount(context)
        return getLastSigned == null
    }

    fun currentUserEmail(): String {
        firebaseAuth = FirebaseAuth.getInstance()
        val email = firebaseAuth.currentUser!!.email
        return email
    }

    fun firebaseEmailAuthentication(
        context: Context,
        email: String,
        password: String,
        errorMesage : MutableLiveData<String>
    ) {
        val intentUtils = IntentUtils()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    intentUtils.intentAndClear(context, MainActivity())
                }
            }.addOnFailureListener { exception ->
              errorMesage.value =  exception.localizedMessage
            }

    }


    fun changeFirebaseEmail(context: Context, newEmail: String, newEmailAgain: String) {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser!!
        if (newEmail != "" && newEmailAgain != "") {
            if (newEmail == newEmailAgain) {
                currentUser.updateEmail(newEmail).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.change_email),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(context, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.email_not_same),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        } else {
            Toast.makeText(context, context.getString(R.string.empty_field), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun changeFirebasePassword(context: Context, newPassword: String) {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser!!
        currentUser.updatePassword(newPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    context,
                    context.getString(R.string.password_updated),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        }.addOnFailureListener { exception ->
            exception.printStackTrace()
        }
    }

    fun firebaseIsEmailVerified(): Boolean {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser!!
        return currentUser.isEmailVerified

    }

    fun firebaseEmailVerification(context: Context) {
        val currentUser = firebaseAuth.currentUser!!
        if (currentUser.isEmailVerified) {
            Toast.makeText(
                context,
                context.getString(R.string.email_already_verified),
                Toast.LENGTH_SHORT
            )
                .show()
        } else {
            currentUser.sendEmailVerification()
            Toast.makeText(
                context,
                context.getString(R.string.verification_link_sent),
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    fun refreshFirebaseEmailVerification(textView: TextView) {
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser!!
        currentUser.reload().addOnSuccessListener { void ->
            if (currentUser.isEmailVerified) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.asset_tic, 0, 0, 0)
            }
        }
    }

    fun logOut(context: Context) {
        val intentUtils = IntentUtils()
        firebaseAuth = FirebaseAuth.getInstance()
        val googleSignInClient =

            GoogleSignIn.getClient(
                context,
                GoogleSignInOptions.DEFAULT_SIGN_IN
            )

        firebaseAuth.signOut()
        googleSignInClient.revokeAccess()
        intentUtils.intentAndClear(context, MainActivity())
    }
}