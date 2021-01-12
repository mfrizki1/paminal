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
    var kesatuan: String?
):Parcelable{
    constructor(): this(0,"","","","",null,"")
}