package id.calocallo.sicape.model

import android.os.Parcelable
import id.calocallo.sicape.network.response.RpphResp
import id.calocallo.sicape.network.response.RpsResp
import id.calocallo.sicape.network.response.Sp3Resp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KasusWanjakModel(
    var lp:LpOnWanjakModel?,
    var skhd:SkhdOnRpsModel?,
    var putkke:PutKkeOnRpphModel?,
    var rps:RpsResp?,
    var rpph:RpphResp?,
    var sktb:SktbOnSp3?,
    var sktt:SkttOnSp3?,
    var sp4:Sp3Resp?,
    var status_terlapor:String?,
    var status_kasus:String?
) : Parcelable