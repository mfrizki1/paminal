package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PasalResp(
    var id: Int?,
    var nama_pasal: String?,
    var tentang_pasal: String?,
    var isi_pasal: String?,
    var user_creator: UserCreatorResp?,
    var user_updater: UserCreatorResp?,
    var created_at: String?,
    var updated_at: String?
) : Parcelable {
    constructor() : this(null, null, null, null, null, null, null, null)
}