package com.blogapp.data.remote.auth

import android.graphics.Bitmap
import com.blogapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthDataSource {

    suspend fun signIn(email: String, password: String): FirebaseUser?{
        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    suspend fun signUp(email: String, password: String, username: String): FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        authResult.user?.uid?.let { uid->
           FirebaseFirestore.getInstance().collection("users").document(uid).set(User(email, username, "Foto_URL_PNG")).await()
        }
        return authResult.user

       suspend fun updateUserProfile(imageBitmap: Bitmap, username: String){

       }
    }
}