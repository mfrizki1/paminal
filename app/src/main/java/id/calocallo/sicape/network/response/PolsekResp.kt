package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PolsekResp(
    var id: Int?,
    var nama_polsek: String?
) : Parcelable