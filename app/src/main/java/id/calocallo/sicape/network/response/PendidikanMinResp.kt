package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PendidikanMinResp(
    var id: Int?,
    var id_personel: Int?,
    var pendidikan: String?,
    var jenis: String?
) : Parcelable