package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.showDrawable
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.LpDisiplinReq
import id.calocallo.sicape.network.request.LpKkeReq
import id.calocallo.sicape.network.request.LpPidanaReq
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.ui.main.lp.pidana.tes.SelectedSaksiAdapter
import id.calocallo.sicape.ui.main.lp.pidana.tes.SelectedSaksiDetailsLookup
import id.calocallo.sicape.ui.main.lp.pidana.tes.SelectedSaksiItemKeyProvider
import id.calocallo.sicape.ui.main.lp.saksi.AddSaksiLpActivity
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_saksi.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class PickSaksiActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private val listSaksi = mutableListOf<LpSaksiResp>()
    private lateinit var adapterSaksi: SelectedSaksiAdapter
    private var selectedSaksi: MutableList<LpSaksiResp> = mutableListOf()
    private var tracker: SelectionTracker<LpSaksiResp>? = null

    //req
    private var lpPidanaReq = LpPidanaReq()
    private var lpKKeReq = LpKkeReq()
    private var lpDisiplinReq = LpDisiplinReq()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_saksi)
        setupActionBarWithBackButton(toolbar)
        sessionManager = SessionManager(this)

        when (sessionManager.getJenisLP()) {
            "pidana" -> {
                supportActionBar?.title = "Tambah Data Laporan Pidana"
            }
            "disiplin" -> {
                supportActionBar?.title = "Tambah Data Laporan Disiplin"
            }
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Kode Etik"
        }
        listSaksi.add(
            LpSaksiResp(1, "Utuh", "", "", "", "", 0, "", "", "")
        )
        listSaksi.add(
            LpSaksiResp(2, "Galuh", "", "", "", "", 0, "", "", "")
        )
        listSaksi.add(
            LpSaksiResp(3, "Dulak", "", "", "", "", 1, "", "", "")
        )

        getListSaksi(listSaksi)

        val idPelaporSaksi = intent.extras?.getInt(ID_PELAPOR_SAKSI)

        bindProgressButton(btn_save_lp_all_2)
        btn_save_lp_all_2.attachTextChangeAnimator()
        btn_save_lp_all_2.setOnClickListener {
            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
            animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

            addAllLp(sessionManager.getJenisLP(), idPelaporSaksi)
            btn_save_lp_all_2.showProgress {
                progressColor = Color.WHITE
            }
            btn_save_lp_all_2.showDrawable(animatedDrawable) {
                buttonTextRes = R.string.data_saved
                textMarginRes = R.dimen.space_10dp
            }
        }

        btn_add_single_saksi_2.setOnClickListener {
            val intent = Intent(this, AddSaksiLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

    }

    private fun getListSaksi(listSaksi: MutableList<LpSaksiResp>) {
        rv_list_saksi_2.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterSaksi = SelectedSaksiAdapter(this, listSaksi as ArrayList<LpSaksiResp>)
        rv_list_saksi_2.adapter = adapterSaksi

        tracker = SelectionTracker.Builder<LpSaksiResp>(
            "pasalSelection",
            rv_list_saksi_2,
            SelectedSaksiItemKeyProvider(adapterSaksi),
            SelectedSaksiDetailsLookup(rv_list_saksi_2),
            StorageStrategy.createParcelableStorage(LpSaksiResp::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        adapterSaksi.tracker = tracker

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    tracker?.let {
                        selectedSaksi = it.selection.toMutableList()
                        Log.e("idPasal", "$selectedSaksi")
                    }
                }
            }
        )
    }

    private fun addAllLp(jenisLP: String?, idPelapor: Int?) {
        when (jenisLP) {
            "kode_etik" -> {
                lpKKeReq.no_lp = sessionManager.getNoLP()
//                lpKKeReq.id_personel_operator = sessionManager.fetchUser()?.id
                lpKKeReq.uraian_pelanggaran = jenisLP
                lpKKeReq.kota_buat_laporan = sessionManager.getKotaBuatLp()
                lpKKeReq.tanggal_buat_laporan = sessionManager.getTglBuatLp()
                lpKKeReq.id_personel_terlapor = sessionManager.getIDPersonelTerlapor()
                lpKKeReq.id_personel_pelapor = idPelapor
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
                lpKKeReq.kesatuan_yang_mengetahui = sessionManager.getKesatuanPimpBidLp()
                Log.e("KKE", "$lpKKeReq")
            }
        }

    }

    companion object {
        const val ID_PELAPOR_SAKSI = "JENIS_LP_SAKSI"
    }
}