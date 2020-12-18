package id.calocallo.sicape.network.request

import id.calocallo.sicape.model.AddPendidikanModel

data class AddPendReq(
    var riwayat_Add_pendidikan_umum: ArrayList<AddPendidikanModel>,
    var riwayat_Add_pendidikan_dinas: ArrayList<AddPendidikanModel>,
    var riwayat_Add_pendidikan_lain_lain: ArrayList<AddPendidikanModel>
) {
    constructor() : this(ArrayList(), ArrayList(), ArrayList())
}