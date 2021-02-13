package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SaudaraMinResp(
    var id: Int?,
    var id_personel: Int?,
    var status_ikatan: String?,
    var nama: String?
) : Parcelable