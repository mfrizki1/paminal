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
import id.calocallo.sicape.network.request.LpReq
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.utils.SessionManager
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_saksi_lp.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickSaksiLpActivity : BaseActivity() {
    //    private lateinit var callbackSaksiLp: AdapterCallback<LpSaksiResp>
    private var lpReq = LpReq()
    private lateinit var sessionManager: SessionManager
    private lateinit var adapterSaksiLp: SaksiLpAdapter
    private var selectedSaksi : MutableList<LpSaksiResp> = mutableListOf()
    private var tracker: SelectionTracker<LpSaksiResp>? = null
    private var listSaksi = arrayListOf(
        LpSaksiResp(1,"Utuh","","",""),
        LpSaksiResp(2,"Galuh","","",""),
        LpSaksiResp(3,"Dulak","","","")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_saksi_lp)
        sessionManager = SessionManager(this)
//        adapterSaksiLp = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
//        supportActionBar?.title = "Pilih Saksi"
        /*
        Log.e(
            "dataLP",
            "${sessionManager.getNoLP()}," + "DIlapor ${sessionManager.getIDPersonelDilapor()}," +
                    " TErlapor ${sessionManager.getIDPersonelTerlapor()}" +
                    "Pelanggaran ${sessionManager.getIdPelanggaran()}, ket ${sessionManager.getKetLP()}" +
                    "id_pasal ${sessionManager.getListPasalLP()}, alat bukti ${sessionManager.getAlatBukiLP()}"
        )

         */
        when (sessionManager.getJenisLP()) {
            "pidana" -> supportActionBar?.title = "Tambah Data Laporan Pidana"
            "disiplin" -> supportActionBar?.title = "Tambah Data Laporan Disiplin"
            "kode_etik" -> supportActionBar?.title = "Tambah Data Laporan Kode Etik"
        }
        getSaksiLp()

        btn_add_single_saksi.setOnClickListener{
            startActivity(Intent(this, AddSaksiLpActivity::class.java))
        }
        bindProgressButton(btn_save_lp_add)
        btn_save_lp_add.attachTextChangeAnimator()
        btn_save_lp_add.setOnClickListener{
            sessionManager.setListSaksiLP(selectedSaksi as ArrayList<LpSaksiResp>)
            val animatedDrawable = ContextCompat.getDrawable(this, R.drawable.animated_check)!!
            val drawableSize = R.dimen.space_25dp
            animatedDrawable.setBounds(0,0, drawableSize, drawableSize)
            lpReq.no_lp = sessionManager.getNoLP()
            lpReq.id_pelanggaran = sessionManager.getIdPelanggaran()
            lpReq.id_personel_dilapor = sessionManager.getIDPersonelDilapor()
            lpReq.id_personel_terlapor = sessionManager.getIDPersonelTerlapor()
            lpReq.keterangan = sessionManager.getKetLP()
            lpReq.listPasal = sessionManager.getListPasalLP()
            lpReq.listSaksi = selectedSaksi
            lpReq.alat_bukti = sessionManager.getAlatBukiLP()
            btn_save_lp_add.showProgress{
                progressColor = Color.WHITE
            }
            btn_save_lp_add.showDrawable(animatedDrawable){
                buttonTextRes = R.string.data_saved
                textMarginRes = R.dimen.space_10dp
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_save_lp_add.hideDrawable(R.string.data_saved)
            },3000)
            Log.e("LP",
                "NoLP :${sessionManager.getNoLP()}," + "IDDilapor ${sessionManager.getIDPersonelDilapor()}," +
                        " IDTerlapor ${sessionManager.getIDPersonelTerlapor()}, Jenis : ${sessionManager.getJenisLP()}" +
                        "IDPelanggaran ${sessionManager.getIdPelanggaran()}, ket ${sessionManager.getKetLP()}" +
                        "id_pasal ${sessionManager.getListPasalLP()}, alat bukti ${sessionManager.getAlatBukiLP()}" +
                        "id_saksi $selectedSaksi"
            )
        }
    }

    private fun getSaksiLp(){
//        NetworkConfig().getService()
        adapterSaksiLp = SaksiLpAdapter(this, listSaksi)
        rv_list_saksi.adapter = adapterSaksiLp
        rv_list_saksi.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        tracker = SelectionTracker.Builder<LpSaksiResp>(
            "saksiSelection",
            rv_list_saksi,
            SaksiItemKeyProvider(adapterSaksiLp),
            SaksiItemDetailsLookup(rv_list_saksi),
            StorageStrategy.createParcelableStorage(LpSaksiResp::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        adapterSaksiLp.tracker = tracker
        tracker?.addObserver(
            object :SelectionTracker.SelectionObserver<Long>(){
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