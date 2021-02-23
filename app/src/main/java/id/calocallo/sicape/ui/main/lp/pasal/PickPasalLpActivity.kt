package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesDetailsLookup
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesItemKeyProvider
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesAdapter
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.LpKkeReq
import id.calocallo.sicape.network.request.LpPidanaReq
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.ui.main.lp.saksi.PickSaksiLpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_pasal_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class PickPasalLpActivity : BaseActivity() {
    companion object {
        const val LIST_PASAL = "LIST_PASAL"
    }

    private var selectedIdPasal: MutableList<PasalResp> = mutableListOf()
    private lateinit var sessionManager1: SessionManager1
    private var lpPidanaReq = LpPidanaReq()
    private var lpKKeReq = LpKkeReq()

    //    private lateinit var adapterPasal: PasalAdapter1
//    private lateinit var adapterPasal2: PasalAdapter2
    private lateinit var adapterPasalTes: PasalTesAdapter
    private val listPasal = arrayListOf<PasalResp>()

    private var tracker: SelectionTracker<PasalResp>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pick_pasal_lp)
        sessionManager1 = SessionManager1(this)
//        adapterPasal = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
//        supportActionBar?.title = "Pilih Pasal"
        when (sessionManager1.getJenisLP()) {
            "pidana" -> supportActionBar?.title = "Tambah Data Laporan Pidana"
            "disiplin" -> supportActionBar?.title = "Tambah Data Laporan Disiplin"
            "kode_etik" -> {
                supportActionBar?.title = "Tambah Data Laporan Kode Etik"
                btn_save_lp_all_pidana.text = resources.getString(R.string.next)
            }
        }

        getPasal()
        btn_add_single_pasal.setOnClickListener {
            val intent = Intent(this@PickPasalLpActivity, AddPasalLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }
        btn_save_lp_all_pidana.setOnClickListener {
            if (sessionManager1.getJenisLP() != "kode_etik") {
                addAllLp(sessionManager1.getJenisLP())
            } else {
                sessionManager1.setListPasalLP(selectedIdPasal as ArrayList<LpPasalResp>)
                val intent = Intent(this@PickPasalLpActivity, PickSaksiLpActivity::class.java)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                startActivity(intent)
            }

        }


    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        if (outState != null) {
            tracker?.onSaveInstanceState(outState)
        }
    }

    private fun addAllLp(jenisLP: String?) {
        when (jenisLP) {
            "pidana" -> {
                lpPidanaReq.no_lp = sessionManager1.getNoLP()
//                lpPidanaReq.id_pelanggaran = sessionManager.getIdPelanggaran()
//                lpPidanaReq.id_personel_operator = sessionManager.fetchUser()?.id
//                lpPidanaReq.id_satuan_kerja = jenisLP
                lpPidanaReq.id_personel_terlapor = sessionManager1.getIDPersonelTerlapor()
                lpPidanaReq.id_personel_pelapor = sessionManager1.getIDPersonelPelapor()
//                lpPidanaReq.id_sipil_pelapor = sessionManager.getIdSipilPelapor()
                lpPidanaReq.nama_yang_mengetahui = sessionManager1.getNamaPimpBidLp()
                lpPidanaReq.pangkat_yang_mengetahui = sessionManager1.getPangkatPimpBidLp()
                lpPidanaReq.nrp_yang_mengetahui = sessionManager1.getNrpPimpBidLp()
                lpPidanaReq.jabatan_yang_mengetahui = sessionManager1.getJabatanPimpBidLp()
                lpPidanaReq.status_pelapor = sessionManager1.getPelapor()
                lpPidanaReq.pembukaan_laporan = sessionManager1.getPembukaanLpLP()
                lpPidanaReq.isi_laporan = sessionManager1.getIsiLapLP()
                lpPidanaReq.pasal_dilanggar = selectedIdPasal as ArrayList<LpPasalResp>
            }
            "kode_etik" -> {
                lpKKeReq.no_lp = sessionManager1.getNoLP()
//                lpKKeReq.id_personel_operator = sessionManager.fetchUser()?.id
                lpKKeReq.uraian_pelanggaran = jenisLP
                lpKKeReq.id_personel_terlapor = sessionManager1.getIDPersonelTerlapor()
                lpKKeReq.id_personel_pelapor = sessionManager1.getIDPersonelPelapor()
//                lpKKeReq.id_sipil_pelapor = sessionManager.getIdSipilPelapor()
                lpKKeReq.nama_yang_mengetahui = sessionManager1.getNamaPimpBidLp()
                lpKKeReq.pangkat_yang_mengetahui = sessionManager1.getPangkatPimpBidLp()
                lpKKeReq.nrp_yang_mengetahui = sessionManager1.getNrpPimpBidLp()
                lpKKeReq.jabatan_yang_mengetahui = sessionManager1.getJabatanPimpBidLp()
                lpKKeReq.alat_bukti = sessionManager1.getAlatBukiLP()
                lpKKeReq.isi_laporan = sessionManager1.getIsiLapLP()
                lpPidanaReq.pasal_dilanggar = selectedIdPasal as ArrayList<LpPasalResp>

            }
            "disiplin" -> {

            }
        }
    }

    private fun getPasal() {
        //        NetworkConfig().getService()
      /*  listPasal.add(
            PasalResp(1, 1, "Pasal 1", "")
        )
        listPasal.add(
            PasalResp(2, 2, "Pasal 2", "")
        )
        listPasal.add(
            PasalResp(3, 3, "Pasal 3", "")
        )*/
        rv_list_pasal.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterPasalTes = PasalTesAdapter(this, listPasal)
        rv_list_pasal.adapter = adapterPasalTes

        tracker = SelectionTracker.Builder<PasalResp>(
            "pasalSelection",
            rv_list_pasal,
            PasalTesItemKeyProvider(adapterPasalTes),
            PasalTesDetailsLookup(rv_list_pasal),
            StorageStrategy.createParcelableStorage(PasalResp::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        adapterPasalTes.tracker = tracker

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<PasalResp>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    tracker?.let {
                        selectedIdPasal = it.selection.toMutableList()
                        Log.e("idPasal", "$selectedIdPasal")
                    }
                }
            }
        )

        /**
         * Pasal Adapter 2
         */

//        adapterPasal2 = PasalAdapter2(this, listPasal)
    }

    override fun onResume() {
        super.onResume()
        getPasal()
    }
}