package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.PersonelLapor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OperatorResp(
    var id: Int?,
    var personel: PersonelLapor?,
    var satuan_kerja: SatKerResp?,
    var is_aktif : Int?
) : Parcelable{
    constructor():this(null,null,null, null)
}