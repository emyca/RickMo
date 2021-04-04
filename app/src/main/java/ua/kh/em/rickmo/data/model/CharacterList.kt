package ua.kh.em.rickmo.data.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class CharacterList {

    @SerializedName("results")
    var listCharacter = ArrayList<Character>()
}