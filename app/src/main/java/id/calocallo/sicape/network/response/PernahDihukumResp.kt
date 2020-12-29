package id.calocallo.sicape.network.response

import android.os.Parcel
import android.os.Parcelable

data class PernahDihukumResp(
    var id: Int?,
    var id_personel: Int?,
    var perkara: String?,
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
        writeString(perkara)
        writeString(created_at)
        writeString(updated_at)
        writeString(deleted_at)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PernahDihukumResp> =
            object : Parcelable.Creator<PernahDihukumResp> {
                override fun createFromParcel(source: Parcel): PernahDihukumResp =
                    PernahDihukumResp(source)

                override fun newArray(size: Int): Array<PernahDihukumResp?> = arrayOfNulls(size)
            }
    }
}