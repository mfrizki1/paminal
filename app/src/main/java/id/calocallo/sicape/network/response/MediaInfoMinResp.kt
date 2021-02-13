package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MediaInfoMinResp(
    var id: Int?,
    var id_personel: Int?,
    var sumber: String?,
    var topik: String?
) : Parcelable