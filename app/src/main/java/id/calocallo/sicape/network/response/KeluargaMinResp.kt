package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KeluargaMinResp(
    var id: Int?,
    var id_personel: Int?,
    var status_hubungan: String?,
    var nama: String?,
    var pekerjaan_terakhir: String?,
    var status_kehidupan: String?
) : Parcelable