package ua.kh.em.rickmo.utils

import ua.kh.em.rickmo.data.model.Character
import java.util.ArrayList

object SortUtil {

    fun sortCharactersByName(items: ArrayList<Character>): ArrayList<Character> {
        val comparator = Comparator { c1: Character, c2: Character ->
            return@Comparator c2.name?.let { c1.name?.compareTo(it) }!!
        }
        val copy = arrayListOf<Character>().apply { addAll(items) }
        copy.sortWith(comparator)
        return copy
    }
}