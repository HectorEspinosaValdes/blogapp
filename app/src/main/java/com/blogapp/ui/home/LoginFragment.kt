package com.blogapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.blogapp.R
import com.blogapp.core.Resource
import com.blogapp.data.remote.auth.AuthDataSource
import com.blogapp.databinding.FragmentLoginBinding
import com.blogapp.domain.auth.AuthRepoImplement
import com.blogapp.presentation.AuthViewModel
import com.blogapp.presentation.AuthViewModelFactory
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val viewModel by viewModels<AuthViewModel> { AuthViewModelFactory(AuthRepoImplement(
        AuthDataSource()
    )) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        isUserLoggedIn()
        doLogin()
        goToSignUpPage()
    }

    //Metodo para checar si el usuario esta regustrado
    private fun isUserLoggedIn(){
        firebaseAuth.currentUser?.let {
            findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
        }
    }

    //Metodo para obtener email y contraseña
    private fun doLogin(){
        binding.btnSignin.setOnClickListener{
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            validateCredencials(email, password)
            signIn(email, password)
        }
    }

    //metodo para validar credenciales
    private fun validateCredencials(email:String, password:String){
        if(email.isEmpty()){
            binding.editTextEmail.error = "E-mail vacio"
            return
        }

        if(password.isEmpty()){
            binding.editTextPassword.error = "Password vacio"
            return
        }
    }

    //Metodo para registarte
    private fun goToSignUpPage(){
        binding.txtSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    //Manda el email y pasword a firebase para logear
    private fun signIn(email: String, password: String){
        viewModel.signIn(email, password).observe(viewLifecycleOwner, Observer { result->
            when(result){
                is Resource.Loading->{
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSignin.isEnabled = false
                }
                is Resource.Success->{
                    binding.progressBar.visibility = View.GONE
                    findNavController().navigate(R.id.action_loginFragment_to_homeScreenFragment)
                }
                is Resource.Failure->{
                    binding.progressBar.visibility = View.GONE
                    binding.btnSignin.isEnabled = true
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }
}