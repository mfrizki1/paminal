package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.*
import id.calocallo.sicape.ui.main.catpers.PasalOnCatpersAdapter
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LapBulResp(
    var id: Int?,
    var lp:LpOnWanjakModel?,
    var skhd:SkhdOnRpsModel?,
    var putkke:PutKkeOnRpphModel?,
    var rps:RpsResp?,
    var rpph:RpphResp?,
    var sktb: SktbOnSp3?,
    var sktt: SkttOnSp3?,
    var sp4:Sp3Resp?,
    var personel_terlapor: PersonelLapor?,
    var pasal_dilanggar: ArrayList<PasalResp>?,
    var status_kasus: String?,
    var is_ada_dokumen: Int?,
    var url_dokumen: String?,
    var created_at: String?,
    var updated_at : String?
) : Parcelable