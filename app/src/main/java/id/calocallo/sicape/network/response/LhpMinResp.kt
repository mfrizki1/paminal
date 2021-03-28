package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LhpMinResp(
    val id: Int?,
    val no_lhp: String?,
    val is_terbukti: Int?,
    val created_at: String?,
    val updated_at: String?,
    val deleted_at: String?
):Parcelable{
    constructor():this(null, null, null, null, null, null)
}
