package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterPelanggaranResp(
    var jenis_pelanggaran: String?,
    var jumlah_kasus: Int?
) : Parcelable