package com.blogapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.blogapp.R
import com.blogapp.core.Resource
import com.blogapp.data.remote.auth.AuthDataSource
import com.blogapp.databinding.FragmentLoginBinding
import com.blogapp.databinding.FragmentRegisterBinding
import com.blogapp.domain.auth.AuthRepoImplement
import com.blogapp.presentation.AuthViewModel
import com.blogapp.presentation.AuthViewModelFactory

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(
        AuthRepoImplement(
        AuthDataSource()
        )
    )}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)
    }

    private fun signUp(){

        binding.btnSignUp.setOnClickListener{

            val username = binding.editTextUsername.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            val confirmPassword = binding.editTextConfirmPassword.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()

            if (validateUserData(
                    password,
                    confirmPassword,
                    email,
                    username
                )
            ) return@setOnClickListener

            createUser(email, password, username)
        }
    }

    private fun createUser(email: String, password: String, username: String) {
        viewModel.signUp(email, password, username).observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Resource.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignUp.isEnabled = false
                }
                is Resource.Success->{
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_registerFragment_to_homeScreenFragment)
                }
                is Resource.Failure->{
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignUp.isEnabled = true
                    Toast.makeText(requireContext(), "Error ${result.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun validateUserData(
        password: String,
        confirmPassword: String,
        emial: String,
        username: String
    ): Boolean {
        if (password != confirmPassword) {
            binding.editTextConfirmPassword.error = "Las contraseñas no coinciden"
            binding.editTextPassword.error = "Las contraseñas no coinciden"
            return true
        }
        if (password.isEmpty()) {
            binding.editTextPassword.error = "La contraseña esta vacia"
            return true
        }
        if (confirmPassword.isEmpty()) {
            binding.editTextPassword.error = "La contraseña esta vacia"
            return true
        }
        if (emial.isEmpty()) {
            binding.editTextEmail.error = "El email esta vacia"
            return true
        }
        if (username.isEmpty()) {
            binding.editTextPassword.error = "El usuario esta vacio"
            return true
        }
        return false
    }
}