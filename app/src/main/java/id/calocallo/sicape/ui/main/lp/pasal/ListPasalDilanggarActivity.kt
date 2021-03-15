package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_pasal_dilanggar.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPasalDilanggarActivity : BaseActivity() {
    /*   private val listPasal = arrayListOf(
           PasalDilanggarResp(1, "Pasal 1", "", "", "", ""),
           PasalDilanggarResp(2, "Pasal 2", "", "", "", ""),
           PasalDilanggarResp(3, "Pasal 3", "", "", "", "")
       )*/
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterPasalDilanggarEdit: ReusableAdapter<PasalDilanggarResp>
    private lateinit var callbackPasalDilanggarEdit: AdapterCallback<PasalDilanggarResp>
    private var jenisPelanggaran: String? = null

    companion object {
        const val EDIT_PASAL_PIDANA = "EDIT_PASAL_PIDANA"
        const val EDIT_PASAL_DILANGGAR = "EDIT_PASAL_DILANGGAR"
        const val EDIT_PASAL_KKE = "EDIT_PASAL_KKE"
        const val EDIT_PASAL_DISIPLIN = "EDIT_PASAL_DISIPLIN"
        const val ADD_PASAL_DILANGGAR = "ADD_PASAL_DILANGGAR"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pasal_dilanggar)
        sessionManager1 = SessionManager1(this)
        adapterPasalDilanggarEdit = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Pasal Dilanggar"
        val detailLp = intent.extras?.getParcelable<LpMinResp>(EDIT_PASAL_DILANGGAR)
        jenisPelanggaran = sessionManager1.getJenisLP()
     /*   if (pidana != null) {
            apiPasalDilanggarList(pidana)
        } else if (kodeEtik != null) {
            apiPasalDilanggarList(kodeEtik)
            supportActionBar?.title = "Edit Data Laporan Polisi Kode Etik"
        } else if (disiplin != null) {
            apiPasalDilanggarList(disiplin)
            supportActionBar?.title = "Edit Data Laporan Polisi Disiplin"
        } else {
            supportActionBar?.title = "Edit Data Laporan Polisi"
        }*/

//        when (detailLp?.jenis) {
//            "pidana" -> supportActionBar?.title = "Edit Data Laporan Pidana"
//            "disiplin" -> supportActionBar?.title = "Edit Data Laporan Disiplin"
//            "kode_etik" -> supportActionBar?.title = "Edit Data Laporan Kode Etik"
//        }


//        getPasalEdit()
        apiPasalDilanggarList(detailLp)
        btn_add_single_pasal_edit.setOnClickListener {
            val intent = Intent(this, AddPasalDilanggarActivity::class.java)
            intent.putExtra(ADD_PASAL_DILANGGAR, detailLp)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

    }

    private fun apiPasalDilanggarList(lp: LpMinResp?) {
        rl_pb.visible()
        lp?.id?.let {
            NetworkConfig().getServLp().getPasalDilanggar(
                "Bearer ${sessionManager1.fetchAuthToken()}",
                it
            ).enqueue(object :
                Callback<ArrayList<PasalDilanggarResp>> {
                override fun onFailure(call: Call<ArrayList<PasalDilanggarResp>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_pasal_edit.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<PasalDilanggarResp>>,
                    response: Response<ArrayList<PasalDilanggarResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        getPasalEdit(response.body())
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_pasal_edit.gone()
                    }
                }
            })
        }
    }

    private fun getPasalEdit(list: ArrayList<PasalDilanggarResp>?) {
        callbackPasalDilanggarEdit = object : AdapterCallback<PasalDilanggarResp> {
            override fun initComponent(itemView: View, data: PasalDilanggarResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.pasal?.nama_pasal
            }

            override fun onItemClicked(itemView: View, data: PasalDilanggarResp, itemIndex: Int) {
                val intent = Intent(this@ListPasalDilanggarActivity, EditPasalDilanggarActivity::class.java)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                intent.putExtra("NAMA_JENIS", jenisPelanggaran)
                intent.putExtra("DETAIL_EDIT_PASAL", data)
                startActivity(intent)

            }
        }
        list?.let {
            adapterPasalDilanggarEdit.adapterCallback(callbackPasalDilanggarEdit)
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.layout_edit_1_text)
                .build(rv_list_pasal_edit)
        }
    }

    override fun onResume() {
        super.onResume()
        val detailLp = intent.extras?.getParcelable<LpMinResp>(EDIT_PASAL_DILANGGAR)
        apiPasalDilanggarList(detailLp)
    }
}