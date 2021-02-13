package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnitPolsekResp (
    var id: Int?,
    var nama_unit: String?
) : Parcelable