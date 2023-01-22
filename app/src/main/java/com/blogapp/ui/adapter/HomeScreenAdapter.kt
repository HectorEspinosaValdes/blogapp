package com.blogapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blogapp.core.BaseViewHolder
import com.blogapp.data.model.Post
import com.blogapp.databinding.PostItemViewBinding
import com.bumptech.glide.Glide

//Adaptador para mostar la informacion en la pantalla home
class HomeScreenAdapter ( private val postList:List<Post>): RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {

        val itmeBinding = PostItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeScreenViewHolder(itmeBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is HomeScreenViewHolder->holder.bind(postList[position])
        }
    }

    //retornar los elementos(items)que tenemos en postList
    override fun getItemCount(): Int = postList.size

    private inner class HomeScreenViewHolder(
        val binding: PostItemViewBinding,
        val context: Context
    ): BaseViewHolder<Post>(binding.root){

        override fun bind(item: Post) {

            //cargar la foto del post y del perfil
            Glide.with(context).load(item.post_image).centerCrop().into(binding.postImage)
            Glide.with(context).load(item.profile_picture).centerCrop().into(binding.profilePicture)
            //cargando mombre de perfil y tiempo
            binding.profileName.text = item.profile_name

            if (item.post_description.isEmpty()){
                binding.postDescription.visibility = View.GONE
            }else{
                binding.postDescription.text = item.post_description
            }

            binding.postTimestamp.text = "Hace 2 horas"
        }

    }
}