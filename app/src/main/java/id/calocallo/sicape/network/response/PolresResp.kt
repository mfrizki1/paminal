package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PolresResp(
    var id: Int?,
    var nama_polres: String?
) : Parcelable