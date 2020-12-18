package id.calocallo.sicape.network.request

data class AddPekerjaanReq(
    var riwayat_pekerjaan: ArrayList<AddSinglePekerjaanReq>,
    var riwayat_pekerjaan_luar_dinas: ArrayList<PekerjaanODinasReq>
) {
    constructor() : this(ArrayList(), ArrayList())
}