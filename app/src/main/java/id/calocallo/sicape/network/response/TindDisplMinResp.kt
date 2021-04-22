package id.calocallo.sicape.network.response

import android.os.Parcelable
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.model.PersonelLapor
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TindDisplMinResp (

    var id: Int?,
    var personel: PersonelMinResp?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
):Parcelable

@Parcelize
data class TindDisplResp(
    var id: Int?,
    var personel: AllPersonelModel?,
    var isi_tindakan_disiplin: String?,
    var keterangan: String?,
    var user_creator: UserResp?,
    var user_updater: UserResp?,
    var user_deleter: UserResp?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
):Parcelable

@Parcelize
data class AddTindDisplResp(
    var tindakan_disiplin: TindDisplResp?
):Parcelable
