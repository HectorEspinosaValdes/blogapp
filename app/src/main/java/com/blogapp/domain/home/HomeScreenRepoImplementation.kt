package com.blogapp.domain.home

import com.blogapp.core.Resource
import com.blogapp.data.model.Post
import com.blogapp.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImplementation ( private val dataSource: HomeScreenDataSource): HomeScreenRepo {

    override suspend fun getLatesPost(): Resource<List<Post>> = dataSource.getLatestPosts()
}