package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KetTerlaporLhpResp(
    var id: Int?,
    var id_personel: Int?,
    var nama: String?,
    var pangkat: String?,
    var jabatan: String?,
    var nrp: String?,
    var id_satuan_kerja: Int?,
    var kesatuan: String?,
    var isi_keterangan_terlapor: String?
) : Parcelable