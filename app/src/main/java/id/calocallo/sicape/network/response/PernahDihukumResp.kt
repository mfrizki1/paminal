package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable
import id.calocallo.sicape.network.request.HukumanReq
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PernahDihukumResp(
    /*CATPERS RESP*/
    val status: String?,
    val surat: String?,
    val no_surat: String?,
    val tanggal: String?,

    /*PERSONEL RESP*/
    var id_lp: Int?,
    var id_putkke: Int?,
    var jenis_pelanggaran: String?,

    /*BOTH*/
    var hukuman: ArrayList<String>?
) : Parcelable