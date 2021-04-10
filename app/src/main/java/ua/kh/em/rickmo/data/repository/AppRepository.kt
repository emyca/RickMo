package ua.kh.em.rickmo.data.repository

import io.reactivex.Flowable
import ua.kh.em.rickmo.data.model.CharacterList
import ua.kh.em.rickmo.data.network.ApiService
import ua.kh.em.rickmo.di.ApiModule.provideApiService
import javax.inject.Inject

class AppRepository @Inject constructor() {

    private var api: ApiService? = null

    fun loadData(): Flowable<CharacterList?>? {
        api = provideApiService()
        return api?.loadData()
    }
}