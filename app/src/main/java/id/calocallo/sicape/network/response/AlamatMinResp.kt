package id.calocallo.sicape.network.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlamatMinResp(
    var id: Int?,
    var id_personel: Int?,
    var alamat: String?,
    var tahun_awal: String?,
    var tahun_akhir: String?
) : Parcelable

@Parcelize
data class DetailAlamatResp(
    var id: Int?,
    var personel: PersonelMinResp?,
    var alamat: String?,
    var tahun_awal: String?,
    var tahun_akhir: String?,
    var dalam_rangka: String?,
    var keterangan: String?
) : Parcelable