package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrganisasiMinResp(
    var id: Int?,
    var id_personel: Int?,
    var organisasi: String?,
    var jabatan: String?
) : Parcelable

@Parcelize
data class DetailOrganisasiResp(
    var id: Int?,
    var personel: PersonelMinResp?,
    var organisasi: String?,
    var tahun_awal: String?,
    var tahun_akhir: String?,
    var tahun_bergabung: String?,
    var jabatan: String?,
    var alamat: String?,
    var keterangan: String?
):Parcelable

@Parcelize
data class AddOrganisasiResp(
    var riwayat_organisasi: DetailOrganisasiResp?
):Parcelable