package id.calocallo.sicape.ui.main.lp.saksi

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.LpKkeReq
import id.calocallo.sicape.network.request.LpPidanaReq
import id.calocallo.sicape.network.request.LpReq
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.ui.main.lp.pidana.tes.SelectedSaksiAdapter
import id.calocallo.sicape.ui.main.lp.pidana.tes.SelectedSaksiDetailsLookup
import id.calocallo.sicape.ui.main.lp.pidana.tes.SelectedSaksiItemKeyProvider
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_saksi_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class PickSaksiLpActivity : BaseActivity() {
    companion object{
        const val ID_PELAPOR_SAKSI = "JENIS_LP_SAKSI"
    }
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterSaksiLp: SelectedSaksiAdapter
    private var lpPidanaReq = LpPidanaReq()
    private var lpKKeReq = LpKkeReq()
    private var selectedSaksi: MutableList<LpSaksiResp> = mutableListOf()
    private var tracker: SelectionTracker<LpSaksiResp>? = null
    private var listSaksi = arrayListOf(
        LpSaksiResp(1, "Utuh", "", "", "","",1,"","",""),
        LpSaksiResp(2, "Galuh", "", "", "","" ,0,"","",""),
        LpSaksiResp(3, "Dulak", "", "", "","",0,"","","")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_saksi_lp)
        sessionManager = SessionManager(this)
//        adapterSaksiLp = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
//        supportActionBar?.title = "Pilih Saksi"
        when (sessionManager.getJenisLP()) {
            "pidana" -> {
                supportActionBar?.title = "Tambah Data Laporan Pidana"
            }
            "disiplin" -> {
                supportActionBar?.title = "Tambah Data Laporan Disiplin"
            }
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Kode Etik"
        }
        val idPelaporSaksi = intent.extras?.getInt(ID_PELAPOR_SAKSI)
        getSaksiLp()

        btn_add_single_saksi.setOnClickListener {
            startActivity(Intent(this, AddSaksiLpActivity::class.java))
        }
        bindProgressButton(btn_save_lp_add)
        btn_save_lp_add.attachTextChangeAnimator()
        btn_save_lp_add.setOnClickListener {
            sessionManager.setListSaksiLP(selectedSaksi as ArrayList<LpSaksiResp>)
            addAllLp(sessionManager.getJenisLP(), idPelaporSaksi)

            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

            btn_save_lp_add.showProgress {
                progressColor = Color.WHITE
            }
            btn_save_lp_add.showDrawable(animatedDrawable) {
                buttonTextRes = R.string.data_saved
                textMarginRes = R.dimen.space_10dp
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_save_lp_add.hideDrawable(R.string.data_saved)
            }, 3000)
        }
    }

    private fun addAllLp(jenisLP: String?, idPelaporSaksi: Int?) {
        when (jenisLP) {
            "kode_etik" -> {
                lpKKeReq.no_lp = sessionManager.getNoLP()
//                lpKKeReq.id_personel_operator = sessionManager.fetchUser()?.id
                lpKKeReq.uraian_pelanggaran = jenisLP
                lpKKeReq.kota_buat_laporan = sessionManager.getKotaBuatLp()
                lpKKeReq.tanggal_buat_laporan = sessionManager.getTglBuatLp()
                lpKKeReq.id_personel_terlapor = sessionManager.getIDPersonelTerlapor()
                lpKKeReq.id_personel_pelapor = idPelaporSaksi
//                lpKKeReq.id_sipil_pelapor = sessionManager.getIdSipilPelapor()
                lpKKeReq.nama_yang_mengetahui = sessionManager.getNamaPimpBidLp()
                lpKKeReq.pangkat_yang_mengetahui = sessionManager.getPangkatPimpBidLp()
                lpKKeReq.nrp_yang_mengetahui = sessionManager.getNrpPimpBidLp()
                lpKKeReq.jabatan_yang_mengetahui = sessionManager.getJabatanPimpBidLp()
                lpKKeReq.alat_bukti = sessionManager.getAlatBukiLP()
                lpKKeReq.isi_laporan = sessionManager.getIsiLapLP()
                lpKKeReq.uraian_pelanggaran = sessionManager.getUraianPelanggaranLP()
                lpKKeReq.pasal_dilanggar = sessionManager.getListPasalLP()
                lpKKeReq.saksi_kode_etik = selectedSaksi as ArrayList<LpSaksiResp>

                Log.e("KKE", "${lpKKeReq}")
            }
        }
    }

    private fun getSaksiLp() {
//        NetworkConfig().getService()
        rv_list_saksi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterSaksiLp = SelectedSaksiAdapter(this, listSaksi)
        rv_list_saksi.adapter = adapterSaksiLp

        tracker = SelectionTracker.Builder<LpSaksiResp>(
            "saksiSelection",
            rv_list_saksi,
            SelectedSaksiItemKeyProvider(adapterSaksiLp),
            SelectedSaksiDetailsLookup(rv_list_saksi),
            StorageStrategy.createParcelableStorage(LpSaksiResp::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        adapterSaksiLp.tracker = tracker
        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    tracker?.let {
                        selectedSaksi = it.selection.toMutableList()
                        Log.e("idSaksi", "$selectedSaksi")
                    }
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()
        getSaksiLp()
    }
}