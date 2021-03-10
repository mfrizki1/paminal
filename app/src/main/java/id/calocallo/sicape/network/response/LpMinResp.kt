package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpMinResp(
    var id: Int?,
    var no_lp: String?,
    var satuan_kerja: SatKerResp?,
    var jenis_pelanggaran: String?,
    var personel_terlapor: PersonelMinResp?,
    var is_melewati_proses_gelar: String?,
    var status_kasus: String?,
    var status_terlapor: String?,
    var created_at: String?,
    var updated_at: String?
) : Parcelable