package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedSosMinResp (
    val id: Int?,
    val id_personel: Int?,
    val nama_medsos: String?,
    val nama_akun: String?
) : Parcelable