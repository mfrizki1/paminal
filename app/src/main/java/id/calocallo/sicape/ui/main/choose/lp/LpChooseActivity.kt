package id.calocallo.sicape.ui.main.choose.lp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.network.NetworkConfig
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
    private var listPasal = arrayListOf<PasalDilanggarResp>()
    private var listSaksi = arrayListOf<LpSaksiResp>()

    private lateinit var sessionManager1: SessionManager1
    private var adapterLpAll = ReusableAdapter<LpMinResp>(this)
    private lateinit var callbackLpAll: AdapterCallback<LpMinResp>
    private var tempJenis: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lp_choose)

        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        when (intent.extras?.getString(JENIS_LP_CHOOSE)) {
            "Pidana" -> {
                supportActionBar?.title = "Pilih Data Laporan Pidana"
                tempJenis = "pidana"
            }
            "Kode Etik" -> {
                supportActionBar?.title = "Pilih Data Laporan Kode Etik"
                tempJenis = "kode/etik"
            }
            "Disiplin" -> {
                tempJenis = "disiplin"
                supportActionBar?.title = "Pilih Data Laporan Disiplin"
            }
        }

        apiListLp(tempJenis)
        /*set jika ada ket_terlapor*/


        /*sktt
        val sktt = intent.extras?.getString(AddSkttActivity.LP_SKTT)
        if (sktt == null) {
            getListLpChoose(tempJenis)
        } else {
            getListLpWithoutSktbPutkkeSktt(tempJenis)
        }
        Log.e("sktt", "$sktt")*/
    }

    private fun apiListLp(tempJenis: String?) {
        rl_pb.visible()
        NetworkConfig().getServLp()
            .getLpByJenis("Bearer ${sessionManager1.fetchAuthToken()}", tempJenis.toString())
            .enqueue(
                object : Callback<ArrayList<LpMinResp>> {
                    override fun onResponse(
                        call: Call<ArrayList<LpMinResp>>,
                        response: Response<ArrayList<LpMinResp>>
                    ) {
                        if (response.isSuccessful) {
                            rl_pb.gone()
                            getListLp(response.body())
                        } else {
                            rl_pb.gone()
                            rl_no_data.visible()
                            rv_list_lp_choose.gone()

                        }
                    }

                    override fun onFailure(call: Call<ArrayList<LpMinResp>>, t: Throwable) {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_lp_choose.gone()
                        Toast.makeText(this@LpChooseActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun getListLp(list: java.util.ArrayList<LpMinResp>?) {
        callbackLpAll = object : AdapterCallback<LpMinResp> {
            override fun initComponent(itemView: View, data: LpMinResp, itemIndex: Int) {
                itemView.txt_1_clickable.text = data.no_lp
            }

            override fun onItemClicked(itemView: View, data: LpMinResp, itemIndex: Int) {
                itemView.img_clickable.toggleVisibility()
                val intent = Intent().apply {
                    this.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    this.putExtra(PickJenisLpActivity.GET_LP_CHOOSE, data)
                }
                setResult(PickJenisLpActivity.RES_LP_CHOOSE, intent)
                finish()

            }
        }
        list?.let {
            adapterLpAll.adapterCallback(callbackLpAll)
                .isVerticalView()
                .filterable()
                .addData(it)
                .setLayout(R.layout.layout_1_text_clickable)
                .build(rv_list_lp_choose)
        }
    }

    /* private fun getListLpWithoutSktbPutkkeSktt(tempJenis: String?) {
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
                                 intent.putExtra(PickJenisLpActivity.GET_LP_WITHOUT_SKTBB_DLL, data)
                                 setResult(PickJenisLpActivity.RES_LP_CHOOSE, intent)
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
                             setResult(PickJenisLpActivity.RES_LP_CHOOSE, intent)
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
     }*/

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

