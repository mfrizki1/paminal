package id.calocallo.sicape.model

import android.os.Parcel
import android.os.Parcelable
import id.calocallo.sicape.network.response.PersonelMinResp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PendidikanModel(
    val id: Int,
    val id_personel: Int,
    val pendidikan: String?,
    val jenis: String?,
    val tahun_awal: String?,
    val tahun_akhir: String?,
    val kota: String?,
    val yang_membiayai: String?,
    val keterangan: String?,
    val created_at: String?,
    val updated_at: String?,
    val deleted_at: String?
) : Parcelable

@Parcelize
data class DetailPendModel(
    val id: Int,
    val personel: PersonelMinResp?,
    val pendidikan: String?,
    val jenis: String?,
    val tahun_awal: String?,
    val tahun_akhir: String?,
    val kota: String?,
    val yang_membiayai: String?,
    val keterangan: String?
) : Parcelable

@Parcelize
data class AddSinglePendResp(
    var riwayat_pendidikan: DetailPendModel?
) : Parcelable