package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class UserResp(
    var id: Int?,
    var status_user: String?,
    var nama: String?,
    var username: String?,
    var hak_akses: String?,
    var satuan_kerja: SatKerResp?,
    var is_aktif: Int?,
    var jenis_operator: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(SatKerResp::class.java.classLoader),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(status_user)
        parcel.writeString(nama)
        parcel.writeString(username)
        parcel.writeString(hak_akses)
        parcel.writeParcelable(satuan_kerja, flags)
        parcel.writeValue(is_aktif)
        parcel.writeString(jenis_operator)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
        parcel.writeString(deleted_at)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserResp> {
        override fun createFromParcel(parcel: Parcel): UserResp {
            return UserResp(parcel)
        }

        override fun newArray(size: Int): Array<UserResp?> {
            return arrayOfNulls(size)
        }
    }
}
