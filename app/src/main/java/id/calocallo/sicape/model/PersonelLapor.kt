package id.calocallo.sicape.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PersonelLapor(
    var id: Int?,
    var nama: String?,
    var pangkat: String?,
    var jabatan: String?,
    var nrp: String?,
    var id_satuan_kerja: Int?,
    var kesatuan: String?,
    var alamat_kantor: String?,
    var agama_sekarang: String?,
    var jenis_kelamin: String?,
    var tempat_lahir: String?,
    var tanggal_lahir: String?
) : Parcelable {
    constructor() : this(
        0, "", "", "", "", null, "", null,
        null, null, null,null
    )
}