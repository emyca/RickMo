package ua.kh.em.rickmo.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
class Character (

    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("species")
    var species: String? = null,

    @SerializedName("gender")
    var gender: String? = null
): Parcelable