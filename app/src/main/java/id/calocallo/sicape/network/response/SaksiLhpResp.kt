package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  SaksiLhpResp (
    var id: Int?,/**/
    var id_lhp: Int?,/**/
    var status_saksi: String?,/**/
    var personel: PersonelMinResp?,/**/
    var nama: String?,
    var tempat_lahir: String?,
    var tanggal_lahir:String?,
    var pekerjaan: String?,
    var alamat: String?,
    var is_korban: Int?,/**/
    var isi_keterangan_saksi: String?/**/
):Parcelable