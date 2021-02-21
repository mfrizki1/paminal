package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SahabatMinResp(
    var id: Int?,
    var id_personel: Int?,
    var nama: String?,
    var pekerjaan: String?
) : Parcelable