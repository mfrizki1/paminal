package id.calocallo.sicape.network.request

data class FilterReq(
    var tahun: String?,/*LAPBUL/CATPERS/ANEV*/
    var bulan: String?,/*LAPBUL/CATPERS/ANEV*/

    var filter: String?,/*ANEV*/
    var rentang: String?,/*ANEV*/
    var jenis: String?/*ANEV*/
) {
    constructor() : this(null, null, null, null, null)
}
