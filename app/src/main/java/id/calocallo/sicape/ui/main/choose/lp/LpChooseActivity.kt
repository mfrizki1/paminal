package id.calocallo.sicape.ui.main.choose.lp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity.Companion.DATA_LP
import id.calocallo.sicape.ui.main.lhp.add.ListKetTerlaporLhpActivity.Companion.LIST_KET_TERLAPOR
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lp_choose.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class LpChooseActivity : BaseActivity() {
    companion object {
        const val JENIS_LP_CHOOSE = "JENIS_LP_CHOOSE"
    }

    private var listLp: MutableList<LpResp> = mutableListOf()
    private var listPidanaLP = ArrayList<LpPidanaResp>()
    private var listkkeLP = ArrayList<LpKkeResp>()
    private var listDisiplinLP = ArrayList<LpDisiplinResp>()

    private var personelTerLapor = PersonelLapor()
    private var personelPeLapor = PersonelLapor()
    private var satKerResp = SatKerResp()
    private var listPasal = arrayListOf<LpPasalResp>()
    private var listSaksi = arrayListOf<LpSaksiResp>()

    private lateinit var sessionManager: SessionManager
    private lateinit var adapterLpPidanaChoose: ReusableAdapter<LpPidanaResp>
    private lateinit var callbackLpPidanaChoose: AdapterCallback<LpPidanaResp>

    private var adapterLpKkeChoose = ReusableAdapter<LpKkeResp>(this)
    private lateinit var callbackLpKkeChoose: AdapterCallback<LpKkeResp>

    private lateinit var adapterLpDisiplinChoose: ReusableAdapter<LpDisiplinResp>
    private lateinit var callbackLpDisiplinChoose: AdapterCallback<LpDisiplinResp>
    private var tempJenis: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lp_choose)

        sessionManager = SessionManager(this)
        adapterLpPidanaChoose = ReusableAdapter(this)
        adapterLpDisiplinChoose = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
        when (intent.extras?.getString(JENIS_LP_CHOOSE)) {
            "Pidana" -> {
                supportActionBar?.title = "Pilih Data Laporan Pidana"
                tempJenis = "pidana"
            }
            "Kode Etik" -> {
                supportActionBar?.title = "Pilih Data Laporan Kode Etik"
                tempJenis = "kode_etik"
            }
            "Disiplin" -> {
                tempJenis = "disiplin"
                supportActionBar?.title = "Pilih Data Laporan Disiplin"
            }
        }
        /*set jika ada ket_terlapor*/
        getListLpChoose(tempJenis)
    }


    private fun getListLpChoose(jenis: String?) {
        Log.e("jenisLPCHOOSE", jenis.toString())
            satKerResp = SatKerResp(1, "POLDA", "ALAMAT", "081210812", "", "", "", "")
            personelTerLapor =
                PersonelLapor(1, "faisal", "bripda", "jabatan", "1234", "polda kalsel")
            personelPeLapor =
                PersonelLapor(2, "utuh", "ipda", "jabatan", "0987", "polresta banjarmasin")


            listPasal.add(LpPasalResp(1, "Pasal 1", "LOREM IPSUM DOLOR", "", "", ""))
            listPasal.add(LpPasalResp(2, "Pasal 2", "LOREM IPSUM DOLOR", "", "", ""))
            listPasal.add(LpPasalResp(3, "Pasal 3", "LOREM IPSUM DOLOR", "", "", ""))
            listSaksi.add(LpSaksiResp(1, "Galuh", "korban", "", "", "", 1, "", "", ""))
            listSaksi.add(LpSaksiResp(2, "Akbar", "saksi", "", "", "", 0, "", "", ""))
            listSaksi.add(LpSaksiResp(3, "Wahyu", "saksi", "", "", "", 0, "", "", ""))
            listPidanaLP.add(
                LpPidanaResp(
                    1,
                    "LP/PIDANA/2020/BIDPROPAM",
                    satKerResp,
                    personelTerLapor,
                    "Uraian Pelanggaran",
                    "Banjarmasin",
                    "12-12-2000",
                    "Rojak Ahmad",
                    "Kombes",
                    "12345678",
                    "Polair",
                    "polisi",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    personelPeLapor,
                    resources.getString(R.string.paragraf),
                    resources.getString(R.string.paragraf),
                    listPasal,
                    "",
                    "",
                    ""
                )
            )

            listPidanaLP.add(
                LpPidanaResp(
                    2,
                    "LP/PIDANA2/2020/BIDPROPAM",
                    satKerResp,
                    personelTerLapor,
                    "Uraian Pelanggaran",
                    "Banjarmasin",
                    "12-12-2000",
                    "Rojak Ahmad",
                    "Kombes",
                    "12345678",
                    "Polair",
                    "sipil",
                    "sipil",
                    "islam",
                    "pekerjaan",
                    "Indonesia",
                    "jl xxx",
                    "081212",
                    "123456",
                    null,
                    resources.getString(R.string.paragraf),
                    resources.getString(R.string.paragraf),
                    listPasal,
                    "",
                    "",
                    ""
                )
            )

            listDisiplinLP.add(
                LpDisiplinResp(
                    1, "LP/DISIPLIN1",
                    "disiplin", personelTerLapor, personelPeLapor,
                    "Banjarmasin", "12-01-20", "Budi",
                    "IPDA", "87654321", "KOMBES",
                    "macam_pelanggaran", "keterangan terlapor",
                    "kronologis", "rincian", listPasal, satKerResp, "", ""
                )
            )

            listDisiplinLP.add(
                LpDisiplinResp(
                    2, "LP/DISIPLIN2",
                    "disiplin", personelTerLapor, personelPeLapor,
                    "Banjarmasin", "12-01-20", "Budi",
                    "IPDA", "87654321", "KOMBES",
                    "macam_pelanggaran", "keterangan terlapor",
                    "kronologis", "rincian", listPasal, satKerResp, "", ""
                )
            )

            listkkeLP.add(
                LpKkeResp(
                    1, "LP/KKE1/2019/BIDPROPAM", "kode_etik", personelTerLapor,
                    personelPeLapor, "Banjarbaru", "12-12-2000", "Budi",
                    "IPDA", "9090", "KOMBES",
                    sessionManager.fetchUser()?.id, "Alat Bukti\nbaju\nsenjata", "isi Laporan",
                    listPasal, listSaksi, "", "", ""
                )
            )

            listkkeLP.add(
                LpKkeResp(
                    2, "LP/KKE2/2019/BIDPROPAM", "kode_etik", personelTerLapor,
                    personelPeLapor, "Banjarbaru", "12-12-2000", "Budi",
                    "IPDA", "9090", "KOMBES",
                    sessionManager.fetchUser()?.id, "Alat Bukti\nbaju\nsenjata", "isi Laporan",
                    listPasal, listSaksi, "", "", ""
                )
            )

        //jika jenis = pidana
        when (jenis) {
            "pidana" -> {


                callbackLpPidanaChoose = object : AdapterCallback<LpPidanaResp> {
                    override fun initComponent(
                        itemView: View,
                        data: LpPidanaResp,
                        itemIndex: Int
                    ) {
                        itemView.txt_1_clickable.text = data.no_lp
                        itemView.setOnClickListener {
                        }
                    }

                    override fun onItemClicked(
                        itemView: View,
                        data: LpPidanaResp,
                        itemIndex: Int
                    ) {
                        itemView.img_clickable.visible()
                        val intent = Intent()
                        intent.putExtra(DATA_LP, data)
                        setResult(999, intent)
                        finish()

                    }
                }
                adapterLpPidanaChoose.adapterCallback(callbackLpPidanaChoose)
                    .isVerticalView()
                    .addData(listPidanaLP)
                    .setLayout(R.layout.layout_1_text_clickable)
                    .build(rv_list_lp_choose)
            }
            "kode_etik" -> {
                callbackLpKkeChoose = object : AdapterCallback<LpKkeResp> {
                    override fun initComponent(
                        itemView: View,
                        data: LpKkeResp,
                        itemIndex: Int
                    ) {
                        itemView.txt_1_clickable.text = data.no_lp
                        itemView.setOnClickListener {
                        }
                    }

                    override fun onItemClicked(
                        itemView: View,
                        data: LpKkeResp,
                        itemIndex: Int
                    ) {
                        itemView.img_clickable.visible()
                        val intent = Intent()
                        intent.putExtra(DATA_LP, data)
                        setResult(888, intent)
                        finish()

                    }
                }
                adapterLpKkeChoose.adapterCallback(callbackLpKkeChoose)
                    .isVerticalView()
                    .addData(listkkeLP)
                    .setLayout(R.layout.layout_1_text_clickable)
                    .build(rv_list_lp_choose)

            }
            else -> {
                callbackLpDisiplinChoose = object : AdapterCallback<LpDisiplinResp> {
                    override fun initComponent(
                        itemView: View,
                        data: LpDisiplinResp,
                        itemIndex: Int
                    ) {
                        itemView.txt_1_clickable.text = data.no_lp
                        itemView.setOnClickListener {
                        }
                    }

                    override fun onItemClicked(
                        itemView: View, data: LpDisiplinResp, itemIndex: Int
                    ) {
                        itemView.img_clickable.visible()
                        val intent = Intent()
                        intent.putExtra(DATA_LP, data)
                        setResult(777, intent)
                        finish()

                    }
                }
                adapterLpDisiplinChoose.adapterCallback(callbackLpDisiplinChoose)
                    .isVerticalView()
                    .addData(listDisiplinLP)
                    .setLayout(R.layout.layout_1_text_clickable)
                    .build(rv_list_lp_choose)

            }
        }
    }
}

