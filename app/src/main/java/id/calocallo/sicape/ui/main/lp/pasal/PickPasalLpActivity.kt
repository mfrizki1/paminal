package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.zanyastudios.test.PasalTesDetailsLookup
import com.zanyastudios.test.PasalItem
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesItemKeyProvider
import id.calocallo.sicape.ui.main.lp.pasal.tes.PasalTesAdapter
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.LpKkeReq
import id.calocallo.sicape.network.request.LpPidanaReq
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.ui.main.lp.saksi.PickSaksiLpActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_pasal_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class PickPasalLpActivity : BaseActivity() {
    companion object {
        const val LIST_PASAL = "LIST_PASAL"
    }

    private var selectedIdPasal: MutableList<PasalItem> = mutableListOf()
    private lateinit var sessionManager: SessionManager
    private var lpPidanaReq = LpPidanaReq()
    private var lpKKeReq = LpKkeReq()

    //    private lateinit var adapterPasal: PasalAdapter1
//    private lateinit var adapterPasal2: PasalAdapter2
    private lateinit var adapterPasalTes: PasalTesAdapter
    private val listPasal = arrayListOf<PasalItem>()

    private var tracker: SelectionTracker<PasalItem>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pick_pasal_lp)
        sessionManager = SessionManager(this)
//        adapterPasal = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
//        supportActionBar?.title = "Pilih Pasal"
        when (sessionManager.getJenisLP()) {
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
            if (sessionManager.getJenisLP() != "kode_etik") {
                addAllLp(sessionManager.getJenisLP())
            } else {
                sessionManager.setListPasalLP(selectedIdPasal as ArrayList<LpPasalResp>)
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
                lpPidanaReq.no_lp = sessionManager.getNoLP()
//                lpPidanaReq.id_pelanggaran = sessionManager.getIdPelanggaran()
//                lpPidanaReq.id_personel_operator = sessionManager.fetchUser()?.id
//                lpPidanaReq.id_satuan_kerja = jenisLP
                lpPidanaReq.id_personel_terlapor = sessionManager.getIDPersonelTerlapor()
                lpPidanaReq.id_personel_pelapor = sessionManager.getIDPersonelPelapor()
//                lpPidanaReq.id_sipil_pelapor = sessionManager.getIdSipilPelapor()
                lpPidanaReq.nama_yang_mengetahui = sessionManager.getNamaPimpBidLp()
                lpPidanaReq.pangkat_yang_mengetahui = sessionManager.getPangkatPimpBidLp()
                lpPidanaReq.nrp_yang_mengetahui = sessionManager.getNrpPimpBidLp()
                lpPidanaReq.jabatan_yang_mengetahui = sessionManager.getJabatanPimpBidLp()
                lpPidanaReq.status_pelapor = sessionManager.getPelapor()
                lpPidanaReq.pembukaan_laporan = sessionManager.getPembukaanLpLP()
                lpPidanaReq.isi_laporan = sessionManager.getIsiLapLP()
                lpPidanaReq.pasal_dilanggar = selectedIdPasal as ArrayList<LpPasalResp>
            }
            "kode_etik" -> {
                lpKKeReq.no_lp = sessionManager.getNoLP()
//                lpKKeReq.id_personel_operator = sessionManager.fetchUser()?.id
                lpKKeReq.uraian_pelanggaran = jenisLP
                lpKKeReq.id_personel_terlapor = sessionManager.getIDPersonelTerlapor()
                lpKKeReq.id_personel_pelapor = sessionManager.getIDPersonelPelapor()
//                lpKKeReq.id_sipil_pelapor = sessionManager.getIdSipilPelapor()
                lpKKeReq.nama_yang_mengetahui = sessionManager.getNamaPimpBidLp()
                lpKKeReq.pangkat_yang_mengetahui = sessionManager.getPangkatPimpBidLp()
                lpKKeReq.nrp_yang_mengetahui = sessionManager.getNrpPimpBidLp()
                lpKKeReq.jabatan_yang_mengetahui = sessionManager.getJabatanPimpBidLp()
                lpKKeReq.alat_bukti = sessionManager.getAlatBukiLP()
                lpKKeReq.isi_laporan = sessionManager.getIsiLapLP()
                lpPidanaReq.pasal_dilanggar = selectedIdPasal as ArrayList<LpPasalResp>

            }
            "disiplin" -> {

            }
        }
    }

    private fun getPasal() {
        //        NetworkConfig().getService()
        listPasal.add(
            PasalItem(1, "Pasal 1", "")
        )
        listPasal.add(
            PasalItem(2, "Pasal 2", "")
        )
        listPasal.add(
            PasalItem(3, "Pasal 3", "")
        )
        rv_list_pasal.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterPasalTes = PasalTesAdapter(this, listPasal)
        rv_list_pasal.adapter = adapterPasalTes

        tracker = SelectionTracker.Builder<PasalItem>(
            "pasalSelection",
            rv_list_pasal,
            PasalTesItemKeyProvider(adapterPasalTes),
            PasalTesDetailsLookup(rv_list_pasal),
            StorageStrategy.createParcelableStorage(PasalItem::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        adapterPasalTes.tracker = tracker

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
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