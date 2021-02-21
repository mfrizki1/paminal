package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DokResp(
    var id:  Int?,
    var nama_file:  String?,
    var penggunaan:  String?,
    var jenis:  String?,
    var url:  String?,
    var created_at:  String?,
    var updated_at:  String?
) : Parcelable