package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnevResp(
    var kesatuan: String?,
    var pangkat: String?,
    var jenis_pelanggaran: String?,
    var total: Int?
) : Parcelable

@Parcelize
data class BaseAnev(
    var data: ArrayList<AnevResp>?,
    val bulan: String?,
    val tahun: String?,
    val grand_total: Int?,
    val message: String?
) : Parcelable