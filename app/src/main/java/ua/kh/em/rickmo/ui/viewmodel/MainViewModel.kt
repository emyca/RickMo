package ua.kh.em.rickmo.ui.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.Flowable
import ua.kh.em.rickmo.data.model.CharacterList
import ua.kh.em.rickmo.data.repository.AppRepository

class MainViewModel : ViewModel() {

    private val listCharacter: Flowable<CharacterList?>?

    fun loadData(): Flowable<CharacterList?>?{
        return listCharacter
    }

    init {
        val appRepository = AppRepository()
        listCharacter = appRepository.loadData()
    }

}