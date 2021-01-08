package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.PersonelLapor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TindDisiplinResp (
    val id: Int?,
    val personel: PersonelLapor?,
    val isi_tindakan_disiplin: String?,
    val created_at: String?,
    val updated_at: String?
):Parcelable