package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.LpOnCatpersModel
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.model.SkhdOnRpsModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CatpersLapbulResp(
    var id_lp: Int?,
    var nama: String?,
    var pangkat: String?,
    var nrp: String?,
    var jabatan: String?,
    var kesatuan: String?,
    var no_lp: String?,
    var tanggal_buat_laporan: String?,
    var jenis_pelanggaran: String?,
    var uraian_pelanggaran: String?,
    var pasal_dilanggar: ArrayList<String>?,
    var putusan_hukuman:PernahDihukumResp?,/*Catpers*/
    var no_putusan: String?,/*Lapbul*/
    var tanggal_putusan: String?,/*Lapbul*/
    var hukuman: ArrayList<String>?,/*Lapbul*/
    var surat_rehab: ArrayList<SuratRehabResp>?,/*Lapbul*/
    var tanggal_surat_rehab: String?,/*Lapbul*/
    var keterangan: String?
) : Parcelable

@Parcelize
data class SuratRehabResp(
    var surat:String?,
    var no_surat:String?
): Parcelable