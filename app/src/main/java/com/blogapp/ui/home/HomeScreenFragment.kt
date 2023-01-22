package com.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.blogapp.R
import com.blogapp.core.Resource
import com.blogapp.data.remote.home.HomeScreenDataSource
import com.blogapp.databinding.FragmentHomeScreenBinding
import com.blogapp.domain.home.HomeScreenRepoImplementation
import com.blogapp.presentation.HomeScreenViewModel
import com.blogapp.presentation.HomeScrennViewModelFactory
import com.blogapp.ui.adapter.HomeScreenAdapter

class HomeScreenFragment : Fragment(R.layout.fragment_home_screen) {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel by viewModels<HomeScreenViewModel> {
        HomeScrennViewModelFactory(HomeScreenRepoImplementation(HomeScreenDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeScreenBinding.bind(view)

        viewModel.fetchLatesPosts().observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Resource.Loading->{
                    binding.progresBar.visibility = View.VISIBLE
                }
                is Resource.Success->{
                    binding.progresBar.visibility = View.GONE
                    binding.rvHome.adapter = HomeScreenAdapter(result.data)
                }
                is Resource.Failure->{
                    binding.progresBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error : ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }
}