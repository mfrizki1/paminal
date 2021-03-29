package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.LpDisiplinReq
import id.calocallo.sicape.network.request.LpKkeReq
import id.calocallo.sicape.network.request.LpPidanaReq
import id.calocallo.sicape.network.request.SaksiLpReq
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.DokLpResp
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.ui.main.lp.kke.ListLpKodeEtikActivity
import id.calocallo.sicape.ui.main.lp.saksi.AddSaksiLpActivity
import id.calocallo.sicape.ui.main.lp.saksi.select_saksi.SelectedSaksiAdapter
import id.calocallo.sicape.ui.main.lp.saksi.select_saksi.SelectedSaksiDetailsLookup
import id.calocallo.sicape.ui.main.lp.saksi.select_saksi.SelectedSaksiItemKeyProvider
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_saksi.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickSaksiActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private val listSaksi = mutableListOf<SaksiLpReq>()
    private lateinit var adapterSaksiMulti: SelectedSaksiAdapter
    private var selectedSaksi: MutableList<LpSaksiResp> = mutableListOf()
    private var tracker: SelectionTracker<LpSaksiResp>? = null

    private var adapterSaksi = ReusableAdapter<SaksiLpReq>(this)
    private lateinit var callbackSaksiLp: AdapterCallback<SaksiLpReq>

    //req
    private var lpPidanaReq = LpPidanaReq()
    private var lpKKeReq = LpKkeReq()
    private var lpDisiplinReq = LpDisiplinReq()

    /*action mode*/
    private var actionMode: ActionMode? = null

    companion object {
        const val REQ_SAKSI_ADD = 1
        const val ID_PELAPOR_SAKSI = "JENIS_LP_SAKSI"
        const val RES_SAKSI_ADD = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_saksi)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)

        when (sessionManager1.getJenisLP()) {
            "pidana" -> {
                supportActionBar?.title = "Tambah Data Laporan Pidana"
            }
            "disiplin" -> {
                supportActionBar?.title = "Tambah Data Laporan Disiplin"
            }
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Kode Etik"
        }
        /* listSaksi.add(
             LpSaksiResp(1, "Utuh", "", "", "", "", 0, "", "", "")
         )
         listSaksi.add(
             LpSaksiResp(2, "Galuh", "", "", "", "", 0, "", "", "")
         )
         listSaksi.add(
             LpSaksiResp(3, "Dulak", "", "", "", "", 1, "", "", "")
         )*/

//        getListSaksiMulti(listSaksi)
        getListSaksi(listSaksi)
        val idPelaporSaksi = intent.extras?.getInt(ID_PELAPOR_SAKSI)

        bindProgressButton(btn_save_lp_all_2)
        btn_save_lp_all_2.attachTextChangeAnimator()
        btn_save_lp_all_2.setOnClickListener {
            btn_save_lp_all_2.showProgress {
                progressColor = Color.WHITE
            }
            addAllLp(sessionManager1.getJenisLP(), idPelaporSaksi)
        }

        btn_add_single_saksi_2.setOnClickListener {
            val intent = Intent(this, AddSaksiLpActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivityForResult(intent, REQ_SAKSI_ADD)
        }

    }

    private fun getListSaksi(listSaksiLp: MutableList<SaksiLpReq>) {
        callbackSaksiLp = object : AdapterCallback<SaksiLpReq> {
            override fun initComponent(itemView: View, data: SaksiLpReq, itemIndex: Int) {
                itemView.txt_detail_1.text = data.nama
                if (data.is_korban == 0) {
                    itemView.txt_detail_2.text = "Saksi"
                } else {
                    itemView.txt_detail_2.text = "Korban"
                }
            }

            override fun onItemClicked(itemView: View, data: SaksiLpReq, itemIndex: Int) {}
        }
        adapterSaksi.adapterCallback(callbackSaksiLp)
            .isVerticalView()
            .addData(listSaksiLp)
            .setLayout(R.layout.item_2_text)
            .build(rv_list_saksi_2)
    }

    private fun getListSaksiMulti(listSaksiLp: MutableList<SaksiLpReq>) {
        rv_list_saksi_2.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterSaksiMulti = SelectedSaksiAdapter(this, listSaksiLp as ArrayList<LpSaksiResp>)
        rv_list_saksi_2.adapter = adapterSaksiMulti

        tracker = SelectionTracker.Builder<LpSaksiResp>(
            "pasalSelection",
            rv_list_saksi_2,
            SelectedSaksiItemKeyProvider(adapterSaksiMulti),
            SelectedSaksiDetailsLookup(rv_list_saksi_2),
            StorageStrategy.createParcelableStorage(LpSaksiResp::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        adapterSaksiMulti.tracker = tracker

        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<LpSaksiResp>() {
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
//                lpKKeReq.no_lp = sessionManager1.getNoLP()
//                lpKKeReq.id_personel_operator = sessionManager.fetchUser()?.id
                lpKKeReq.uraian_pelanggaran = sessionManager1.getUraianPelanggaranLP()
                lpKKeReq.kota_buat_laporan = sessionManager1.getKotaBuatLp()
                lpKKeReq.tanggal_buat_laporan = sessionManager1.getTglBuatLp()
                lpKKeReq.id_personel_terlapor = sessionManager1.getIDPersonelTerlapor()
                lpKKeReq.id_personel_pelapor = idPelapor
//                lpKKeReq.id_sipil_pelapor = sessionManager.getIdSipilPelapor()
                lpKKeReq.nama_yang_mengetahui = sessionManager1.getNamaPimpBidLp()
                lpKKeReq.pangkat_yang_mengetahui = sessionManager1.getPangkatPimpBidLp()
                lpKKeReq.nrp_yang_mengetahui = sessionManager1.getNrpPimpBidLp()
                lpKKeReq.jabatan_yang_mengetahui = sessionManager1.getJabatanPimpBidLp()
                lpKKeReq.alat_bukti = sessionManager1.getAlatBukiLP()
                lpKKeReq.isi_laporan = sessionManager1.getIsiLapLP()
                lpKKeReq.uraian_pelanggaran = sessionManager1.getUraianPelanggaranLP()
                lpKKeReq.pasal_dilanggar = sessionManager1.getListPasalLP()
                lpKKeReq.saksi_kode_etik = listSaksi as ArrayList<SaksiLpReq>
//                lpKKeReq.saksi_kode_etik = selectedSaksi as ArrayList<LpSaksiResp>
//                lpKKeReq.kesatuan_yang_mengetahui = sessionManager1.getKesatuanPimpBidLp()
                Log.e("KKE", "$lpKKeReq")
                apiAddKke()
            }
        }

    }

    private fun apiAddKke() {
        NetworkConfig().getServLp().addLpKke("Bearer ${sessionManager1.fetchAuthToken()}", lpKKeReq)
            .enqueue(object :
                Callback<Base1Resp<DokLpResp>> {
                override fun onFailure(call: Call<Base1Resp<DokLpResp>>, t: Throwable) {
                    Toast.makeText(this@PickSaksiActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                        .show()
                    btn_save_lp_all_2.hideDrawable(R.string.not_save)
                }

                override fun onResponse(
                    call: Call<Base1Resp<DokLpResp>>,
                    response: Response<Base1Resp<DokLpResp>>
                ) {
                    if (response.body()?.message == "Data lp kode etik saved succesfully") {
                        val animatedDrawable =
                            ContextCompat.getDrawable(
                                this@PickSaksiActivity,
                                R.drawable.animated_check
                            )!!
                        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
                        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)
                        btn_save_lp_all_2.showDrawable(animatedDrawable) {
                            buttonTextRes = R.string.data_saved
                            textMarginRes = R.dimen.space_10dp
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent =
                                Intent(this@PickSaksiActivity, ListLpKodeEtikActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            startActivity(intent)
                            finish()
                        }, 500)
                    } else {
                        Toast.makeText(
                            this@PickSaksiActivity,
                            R.string.error_conn,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        btn_save_lp_all_2.hideDrawable(R.string.not_save)
                    }
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_SAKSI_ADD && resultCode == RES_SAKSI_ADD) {
            val dataSaksi = data?.getParcelableExtra<SaksiLpReq>("DATA_SAKSI")
            Log.e("dataSaksi", "$dataSaksi")
            dataSaksi?.let { listSaksi.add(it) }
        }
    }
}