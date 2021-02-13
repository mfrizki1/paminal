package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PernahHukumMinResp(
    var id: Int?,
    var id_personel: Int?,
    var perkara: String?
) : Parcelable