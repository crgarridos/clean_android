package me.cgarrido.cleanandroid.data.remote.service

import io.reactivex.Single
import me.cgarrido.cleanandroid.domain.model.Song
import retrofit2.http.GET

interface SongService {
    //http://jsonplaceholder.typicode.com/photos
    @GET("photos")
    fun getSongs(): Single<List<Song>>
}