package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.KasusWanjakModel
import id.calocallo.sicape.model.PersonelLapor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WanjakResp(
    var personel: PersonelLapor?,
    var kasus: KasusWanjakModel?,
    var is_pernah_tersangkut_kasus: Int?,
    var total_kasus_bersalah: Int?,
    var total_kasus_tidak_bersalah: Int?,
    var total_kasus_tidak_terbukti: Int?,
    var total_kasus_berjalan: Int?,
    var total_kasus_selesai: Int?
) : Parcelable {
    constructor() : this(
        null, null, null, null,
        null, null, null, null
    )
}