package id.calocallo.sicape.ui.main.choose.lp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LpOnSkhd
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.choose.lhp.ChooseLhpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_lp_skhd.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListLpSkhdActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1

    private var listPidanaLP = ArrayList<LpResp>()
    private var listDisiplinLP = ArrayList<LpDisiplinResp>()
    private var personelTerLapor = PersonelLapor()
    private var personelPeLapor = PersonelLapor()
    private var satKerResp = SatKerResp()
    private var listPasal = arrayListOf<PasalDilanggarResp>()
    private var listSaksi = arrayListOf<LpSaksiResp>()

    private var adapterLpChoose = ReusableAdapter<LpOnSkhd>(this)
    private lateinit var callbackLpChoose: AdapterCallback<LpOnSkhd>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lp_skhd)
        sessionManager1 = SessionManager1(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Data Laporan Polisi"

//        val jenisLPFromSkhd = intent.extras?.getString(ChooseLpSkhdActivity.CHOOSE_LP_SKHD)

        /*set idLHP for getlist lp*/
        val idLhp = intent.extras?.getParcelable<LhpMinResp>(ChooseLhpActivity.DATA_LHP)
        Log.e("idLP", "$idLhp")

        getListLpByIdLhp(idLhp?.id)
        /*   Log.e("ListLpSkhd", "$idLhp")
        when (jenisLPFromSkhd) {
            "pidana" -> {
                supportActionBar?.title = "Data Laporan Polisi Pidana"
//                getPidana(idLhp)
            }
            "disiplin" -> {
                supportActionBar?.title = "Data Laporan Polisi Disiplin"
//                getDisiplin(idLhp)
            }
        }*/

    }

    private fun getListLpByIdLhp(idLhp: Int?) {
        rl_pb.visible()
        NetworkConfig().getServLp()
            .getLpByIdLhp("Bearer ${sessionManager1.fetchAuthToken()}", idLhp).enqueue(
                object : Callback<ArrayList<LpOnSkhd>> {
                    override fun onResponse(
                        call: Call<ArrayList<LpOnSkhd>>,
                        response: Response<ArrayList<LpOnSkhd>>
                    ) {
                        if (response.isSuccessful) {
                            rl_pb.gone()
                            listLpSkhd(response.body())
                        } else {
                            rl_pb.gone()
                            rl_no_data.visible()
                            rv_choose_lp_skhd.gone()
                            Toast.makeText(
                                this@ListLpSkhdActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<LpOnSkhd>>, t: Throwable) {
                        Toast.makeText(this@ListLpSkhdActivity, "$t", Toast.LENGTH_SHORT).show()
                        rl_no_data.visible()
                        rl_pb.gone()
                        rv_choose_lp_skhd.gone()
                    }
                })
    }

    private fun listLpSkhd(list: ArrayList<LpOnSkhd>?) {
        callbackLpChoose = object : AdapterCallback<LpOnSkhd> {
            override fun initComponent(itemView: View, data: LpOnSkhd, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.lp?.no_lp
            }

            override fun onItemClicked(itemView: View, data: LpOnSkhd, itemIndex: Int) {
                val intent = Intent().apply {
                    this.putExtra(DATA_LP, data)
                }
                setResult(RESULT_LP, intent)
                finish()
            }

        }
        list?.let {
            adapterLpChoose.adapterCallback(callbackLpChoose)
                .isVerticalView().filterable()
                .addData(it)
                .setLayout(R.layout.layout_edit_1_text).build(rv_choose_lp_skhd)
        }
    }

    companion object {
        const val PIDANA = "PIDANA"
        const val DISIPLIN = "DISIPLIN"
        const val RESULT_LP = 123
        const val DATA_LP = "DATA_LP"


    }

}