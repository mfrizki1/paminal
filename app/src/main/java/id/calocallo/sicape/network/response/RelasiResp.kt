package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable

data class RelasiResp(
    var id: Int?,
    var id_personel: Int?,
    var nama: String?,
    var created_at: String?,
    var updated_at: String?,
    var deleted_at: String?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readValue(Int::class.java.classLoader) as Int?,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeValue(id)
        writeValue(id_personel)
        writeString(nama)
        writeString(created_at)
        writeString(updated_at)
        writeString(deleted_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RelasiResp> = object : Parcelable.Creator<RelasiResp> {
            override fun createFromParcel(source: Parcel): RelasiResp =
                RelasiResp(source)
            override fun newArray(size: Int): Array<RelasiResp?> = arrayOfNulls(size)
        }
    }
}