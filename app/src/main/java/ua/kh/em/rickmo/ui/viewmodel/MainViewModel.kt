package ua.kh.em.rickmo.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import ua.kh.em.rickmo.data.model.CharacterList
import ua.kh.em.rickmo.data.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppRepository
): ViewModel() {

    private var listCharacter: Flowable<CharacterList?>? = null

    fun loadData(): Flowable<CharacterList?>?{
        listCharacter = repository.loadData()
        return listCharacter
    }
}
