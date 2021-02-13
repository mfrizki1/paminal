package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SipilOperatorResp(
    var id: Int?,
    var nama: String?,
    var alamat: String?,
    var username: String?,
    var created_at: String?,
    var updated_at: String?
) : Parcelable