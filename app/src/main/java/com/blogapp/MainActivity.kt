package com.blogapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.blogapp.core.hide
import com.blogapp.core.show
import com.blogapp.databinding.ActivityMainBinding
import com.blogapp.databinding.FragmentLoginBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var imgView : ImageView
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        observeDestinationChange()


    }

    private fun observeDestinationChange(){

        navController.addOnDestinationChangedListener{controller, destination, arguments->
            when(destination.id){
                R.id.loginFragment->{
                    binding.bottomNavigationView.hide()
                }
                R.id.registerFragment->{
                    binding.bottomNavigationView.hide()
                }
                else->{
                    binding.bottomNavigationView.show()
                }
            }
        }
    }
}

