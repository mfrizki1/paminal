package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SipilPelaporResp(
    var id: Int?,
    var nama_sipil: String?,
    var agama_sipil: String?,
    var pekerjaan_sipil: String?,
    var kewarganegaraan_sipil: String?,
    var alamat_sipil: String?,
    var no_telp_sipil: String?,
    var nik_sipil: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable