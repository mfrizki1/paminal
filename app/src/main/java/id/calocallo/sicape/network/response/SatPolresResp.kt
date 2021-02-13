package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SatPolresResp(
    var id: Int?,
    var nama_satuan_res: String?
) : Parcelable