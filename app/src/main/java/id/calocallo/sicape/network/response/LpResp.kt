package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LpResp(
    var id: Int?,
    var jenis: String?,
    var no_lp: String?,
    var id_personel_dilapor: Int?,
    var id_personel_terlapor: Int?,
    var id_pelanggaran: Int?,
    var alatBukti : String?,
    var listPasalDilanggar: ArrayList<PasalDilanggarResp>?,
    var listSaksi: ArrayList<LpSaksiResp>?,
    var keterangan: String?,
    var created_at:String?,
    var updated_at:String?,
    var deleted_at: String?
):Parcelable{
    constructor():this(0,"","",0,0,0,"",
    ArrayList(),ArrayList(), "","","","")
}