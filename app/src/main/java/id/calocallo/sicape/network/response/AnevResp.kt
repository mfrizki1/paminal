package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnevResp (
    var id_satuan_kerja: Int?,
    var kesatuan: String?,
    var alamat_kantor: String?,
    var no_telp_kantor: String?,
    var jumlah_kasus: Int?
) : Parcelable