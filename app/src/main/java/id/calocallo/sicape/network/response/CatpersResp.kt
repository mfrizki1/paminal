package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.LpOnCatpersModel
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CatpersResp(
    var id: Int?,
    var lp: LpOnCatpersModel?,
    var skhd: SkhdOnRpsModel?,
    var putkke: PutKkeOnRpphModel?,
    var personel_terlapor: PersonelLapor?,
    var jenis_pelanggaran: String?,
    var pasal_dilanggar: ArrayList<PasalResp>?,
    var status_kasus: String?,
    var created_at: String?,
    var updated_at: String?
) : Parcelable {
    constructor() : this(
        null, null, null, null,
        null, null, null,
        null, null, null
    )
}