package id.calocallo.sicape.ui.gelar.peserta_gelar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.LhgMinResp
import id.calocallo.sicape.network.response.PesertaLhgResp
import id.calocallo.sicape.ui.gelar.AddTanggPesertaGelarActivity
import id.calocallo.sicape.ui.gelar.DetailGelarActivity
import id.calocallo.sicape.ui.gelar.EditGelarActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_peserta_gelar.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPesertaGelarActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var dataLhg: LhgMinResp? = null

    private var adapterPesertaLhg = ReusableAdapter<PesertaLhgResp>(this)
    private lateinit var callbackPesertaLhg: AdapterCallback<PesertaLhgResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_peserta_gelar)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Peserta Gelar Perkara"
        sessionManager1 = SessionManager1(this)
        dataLhg = intent.getParcelableExtra<LhgMinResp>(DetailGelarActivity.DETAIL_LHG)

        apiListPesertaLhg(dataLhg)
        btn_add_peserta_gelar.setOnClickListener {
            val intent = Intent(this, AddSinglePesertaActivity::class.java)
            intent.putExtra(DATA_LHG, dataLhg)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun apiListPesertaLhg(dataLhg: LhgMinResp?) {
        rl_pb.visible()
        NetworkConfig().getServLhg()
            .getListPesertaLhg("Bearer ${sessionManager1.fetchAuthToken()}", dataLhg?.id)
            .enqueue(object :
                Callback<ArrayList<PesertaLhgResp>> {
                override fun onResponse(
                    call: Call<ArrayList<PesertaLhgResp>>,
                    response: Response<ArrayList<PesertaLhgResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        listPeserta(response.body())
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_lis_peserta_gelar.gone()
                    }
                }

                override fun onFailure(call: Call<ArrayList<PesertaLhgResp>>, t: Throwable) {
                    Toast.makeText(this@ListPesertaGelarActivity, "$t", Toast.LENGTH_SHORT).show()
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_lis_peserta_gelar.gone()
                }
            })
    }

    private fun listPeserta(list: ArrayList<PesertaLhgResp>?) {
        callbackPesertaLhg = object : AdapterCallback<PesertaLhgResp> {
            override fun initComponent(itemView: View, data: PesertaLhgResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.nama_peserta
            }

            override fun onItemClicked(itemView: View, data: PesertaLhgResp, itemIndex: Int) {
                val intent =
                    Intent(this@ListPesertaGelarActivity, EditPesertaGelarActivity::class.java)
                intent.putExtra(DATA_PESERTA, data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        list?.let {
            adapterPesertaLhg.adapterCallback(callbackPesertaLhg)
                .isVerticalView().filterable()
                .addData(it)
                .setLayout(R.layout.layout_edit_1_text)
                .build(rv_lis_peserta_gelar)
        }
    }

    override fun onResume() {
        super.onResume()
        apiListPesertaLhg(dataLhg)
    }

    companion object {
        const val IS_SINGLE_GELAR = "IS_SINGLE_GELAR"
        const val DATA_LHG = "DATA_LHG"
        const val DATA_PESERTA = "DATA_PESERTA"
    }
}