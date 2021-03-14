package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonelMinResp(
    var id: Int?,
    var nama: String?,
    var jabatan: String?,
    var pangkat: String?,
    var nrp: String?,
    var satuan_kerja: SatKerResp?,
    var status_pelanggaran: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable