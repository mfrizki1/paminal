package id.calocallo.sicape.ui.main.choose.lp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity.Companion.DATA_LP
import id.calocallo.sicape.ui.main.rehab.sktt.AddSkttActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lp_choose.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterLpPidanaChoose: ReusableAdapter<LpPidanaResp>
    private lateinit var callbackLpPidanaChoose: AdapterCallback<LpPidanaResp>

    private var adapterLpKkeChoose = ReusableAdapter<LpKkeResp>(this)
    private lateinit var callbackLpKkeChoose: AdapterCallback<LpKkeResp>

    private lateinit var adapterLpDisiplinChoose: ReusableAdapter<LpDisiplinResp>
    private lateinit var callbackLpDisiplinChoose: AdapterCallback<LpDisiplinResp>
    private var adapterLpAll = ReusableAdapter<LpCustomResp>(this)
    private lateinit var callbackLpAll: AdapterCallback<LpCustomResp>
    private var tempJenis: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lp_choose)

        sessionManager1 = SessionManager1(this)
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


        /*sktt*/
        val sktt = intent.extras?.getString(AddSkttActivity.LP_SKTT)
        if (sktt == null) {
            getListLpChoose(tempJenis)
        } else {
            getListLpWithoutSktbPutkkeSktt(tempJenis)
        }
        Log.e("sktt", "$sktt")
    }

    private fun getListLpWithoutSktbPutkkeSktt(tempJenis: String?) {
        rl_pb.visible()
        NetworkDummy().getService().getLpNotHaveSktbPutkkeSktt()
            .enqueue(object : Callback<ArrayList<LpCustomResp>> {
                override fun onFailure(call: Call<ArrayList<LpCustomResp>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_lp_choose.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<LpCustomResp>>,
                    response: Response<ArrayList<LpCustomResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        val listLpWithoutSktbDll =
                            response.body()?.filter { it.jenis_pelanggaran == tempJenis }
                        callbackLpAll = object : AdapterCallback<LpCustomResp> {
                            override fun initComponent(
                                itemView: View,
                                data: LpCustomResp,
                                itemIndex: Int
                            ) {
                                itemView.txt_1_clickable.text = data.no_lp
                            }

                            override fun onItemClicked(
                                itemView: View,
                                data: LpCustomResp,
                                itemIndex: Int
                            ) {
                                itemView.img_clickable.toggleVisibility()
                                val intent = Intent()
                                intent.putExtra(ChooseLpActivity.GET_LP_WITHOUT_SKTBB_DLL, data)
                                setResult(ChooseLpActivity.RES_LP_CHOOSE, intent)
                                finish()
                            }
                        }
                        listLpWithoutSktbDll?.let {
                            adapterLpAll.adapterCallback(callbackLpAll)
                                .isVerticalView().filterable()
                                .addData(it)
                                .setLayout(R.layout.layout_1_text_clickable)
                                .build(rv_list_lp_choose)
                        }
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_lp_choose.gone()
                    }
                }
            })
    }

    private fun getListLpChoose(jenis: String?) {
        rl_pb.visible()
        NetworkDummy().getService().getLpNotHaveLhp().enqueue(object :
            Callback<ArrayList<LpCustomResp>> {
            override fun onFailure(call: Call<ArrayList<LpCustomResp>>, t: Throwable) {
                rl_no_data.visible()
                rv_list_lp_choose.gone()
                rl_pb.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<LpCustomResp>>,
                response: Response<ArrayList<LpCustomResp>>
            ) {
                rl_pb.gone()
                if (response.isSuccessful) {
                    val listLpWithoutLhp = response.body()?.filter { it.jenis_pelanggaran == jenis }
                    callbackLpAll = object : AdapterCallback<LpCustomResp> {
                        override fun initComponent(
                            itemView: View,
                            data: LpCustomResp,
                            itemIndex: Int
                        ) {
                            itemView.txt_1_clickable.text = data.no_lp
                        }

                        override fun onItemClicked(
                            itemView: View,
                            data: LpCustomResp,
                            itemIndex: Int
                        ) {
                            itemView.img_clickable.toggleVisibility()
                            val intent = Intent()
                            intent.putExtra(DATA_LP, data)
                            setResult(ChooseLpActivity.RES_LP_CHOOSE, intent)
                            finish()
                        }
                    }
                    listLpWithoutLhp?.let {
                        adapterLpAll.adapterCallback(callbackLpAll)
                            .isVerticalView().filterable()
                            .setLayout(R.layout.layout_1_text_clickable)
                            .build(rv_list_lp_choose)
                            .addData(it)
                    }
                } else {
                    rl_no_data.visible()
                    rv_list_lp_choose.gone()
                }

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari LP"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterLpAll.filter.filter(newText)
//                adapterLpKkeChoose.filter.filter(newText)
//                adapterLpPidanaChoose.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }


}

