package ua.kh.em.rickmo.data.repository

import io.reactivex.Flowable
import ua.kh.em.rickmo.data.model.CharacterList
import ua.kh.em.rickmo.data.network.ApiClient
import ua.kh.em.rickmo.data.network.ApiService

class AppRepository {

    private var api: ApiService? = null

    fun loadData(): Flowable<CharacterList?>? {
        api = ApiClient.getApiService()
        return api?.loadData()
    }
}