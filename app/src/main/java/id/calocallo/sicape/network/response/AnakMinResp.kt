package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnakMinResp(
    val id: Int?,
    val id_personel: Int?,
    val status_ikatan: String?,
    val nama: String?
) : Parcelable