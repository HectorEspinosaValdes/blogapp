package com.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blogapp.R
import com.blogapp.databinding.ActivityMainBinding
import com.blogapp.databinding.FragmentSetupProfileBinding

class SetupProfileFragment : Fragment(R.layout.fragment_setup_profile) {

  private lateinit var binding: FragmentSetupProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSetupProfileBinding.bind(view)
    }
}