package ua.kh.em.rickmo.data.network

import io.reactivex.Flowable
import retrofit2.http.GET
import ua.kh.em.rickmo.data.model.CharacterList

interface ApiService {

    @GET("character")
    fun loadData(): Flowable<CharacterList?>?
}