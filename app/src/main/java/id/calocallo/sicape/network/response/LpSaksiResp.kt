package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpSaksiResp(
    var id: Int?,
    var nama:String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?,
    var pekerjaan: String?,
    var alamat: String?,
    var is_korban: Int?,
    var created_at:String?,
    var updated_at:String?,
    var deleted_at: String?
):Parcelable