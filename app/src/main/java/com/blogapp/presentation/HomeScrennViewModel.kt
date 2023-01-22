package com.blogapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.blogapp.core.Resource
import com.blogapp.domain.home.HomeScreenRepo
import kotlinx.coroutines.Dispatchers

class HomeScreenViewModel(private val repo: HomeScreenRepo): ViewModel() {

    fun fetchLatesPosts() = liveData(Dispatchers.IO){

        emit(Resource.Loading())
        try {
            emit(repo.getLatesPost())
        }catch(e: Exception){
            emit(Resource.Failure(e))
        }
    }
}

class HomeScrennViewModelFactory(private val repo: HomeScreenRepo): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeScreenRepo::class.java).newInstance(repo)
    }
}