package com.blogapp.domain.home

import com.blogapp.core.Resource
import com.blogapp.data.model.Post

interface HomeScreenRepo {

    //metodo que va buscar la informacion al servidor
    suspend fun getLatesPost(): Resource<List<Post>>

    //El repositorio tiene una interfas la cual detalla los metodos que vamos a utilizar para ser un tipo de operacion
    //de cualquier tipo y retorna esa operacion.
    //Luego el repositorio es el encargado en la implementacion de conectar el dataSource que es donde vienen los datos
    //para retornar ese valor
}