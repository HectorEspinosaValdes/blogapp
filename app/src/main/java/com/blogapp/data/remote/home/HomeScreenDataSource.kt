package com.blogapp.data.remote.home

import com.blogapp.core.Resource
import com.blogapp.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class HomeScreenDataSource {

    suspend fun getLatestPosts(): Resource<List<Post>>{
        //Traer informacion de Firebase
        val postList = mutableListOf<Post>()
        val querySnapshot = FirebaseFirestore.getInstance().collection("Post").get().await()

        for ( post in querySnapshot.documents){
            //transformamos json a kotlin al modelo que tenemos
            post.toObject(Post::class.java)?.let {
                postList.add(it)
            }
        }

        return Resource.Success(postList)
    }
}