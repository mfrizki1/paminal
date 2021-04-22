package id.calocallo.sicape.ui.main.skhd

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpOnSkhd
import id.calocallo.sicape.model.LpOnSkhd
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.SkhdMinResp
import id.calocallo.sicape.ui.main.skhd.DetailSkhdActivity.Companion.DETAIL_SKHD
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_skhd.*
import kotlinx.android.synthetic.main.item_skhd.view.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListSkhdActivity : BaseActivity() {
    companion object {
        const val SKHD = "SKDH"
    }

    private lateinit var sessionManager1: SessionManager1

//    private lateinit var adapter: ReusableAdapter<SkhdModel>

//    private lateinit var adapterSkhd: SkhdAdapter

    //    private lateinit var adapterSkhd: AdapterCallback<SkhdModel>
//    private lateinit var adapterhkm: ReusableAdapter<ListHukumanSkhd>
//    private lateinit var adapterHkmSkhd: AdapterCallback<ListHukumanSkhd>
//    private lateinit var listHukumanSkhd: ArrayList<ListHukumanSkhd>
//    private lateinit var listSkhd: ArrayList<SkhdModel>
    private var listLhpOnSkhd = LhpOnSkhd()
    private var listLpOnSkhd = LpOnSkhd()

    private var adapterSkhd = ReusableAdapter<SkhdMinResp>(this)
    private lateinit var callbackSkhd: AdapterCallback<SkhdMinResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_skhd)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        val jenisFromPickSkhd = intent.extras?.getString(SKHD)
        supportActionBar?.title = "Surat Keterangan Hasil Disiplin"

        val hak = sessionManager1.fetchHakAkses()
//        if(hak == "operator"){
//            btn_add_skhd.gone()
//        }
        btn_add_skhd.setOnClickListener {
            val intent = Intent(this, AddSkhdActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        apiListSkhd()

    }

    private fun apiListSkhd() {
        rl_pb.visible()
        NetworkConfig().getServSkhd().getSkhd("Bearer ${sessionManager1.fetchAuthToken()}").enqueue(
            object : Callback<ArrayList<SkhdMinResp>> {
                override fun onResponse(
                    call: Call<ArrayList<SkhdMinResp>>,
                    response: Response<ArrayList<SkhdMinResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        listSkhd(response.body())
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_skhd.gone()
                    }
                }

                override fun onFailure(call: Call<ArrayList<SkhdMinResp>>, t: Throwable) {
                    Toast.makeText(this@ListSkhdActivity, "$t", Toast.LENGTH_SHORT).show()
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_skhd.gone()
                }
            })
    }

    private fun listSkhd(list: ArrayList<SkhdMinResp>?) {
        callbackSkhd = object : AdapterCallback<SkhdMinResp> {
            override fun initComponent(itemView: View, data: SkhdMinResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.no_skhd
            }

            override fun onItemClicked(itemView: View, data: SkhdMinResp, itemIndex: Int) {
                val intent = Intent(this@ListSkhdActivity, DetailSkhdActivity::class.java)
                intent.putExtra(DETAIL_SKHD, data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        list?.let {
            adapterSkhd.adapterCallback(callbackSkhd)
                .isVerticalView()
                .filterable()
                .addData(it)
                .setLayout(R.layout.layout_edit_1_text)
                .build(rv_skhd)
        }
        /*   when (tempJenis) {
               "pidana" -> {
                   val listPidanaSkhd = listSkhd.filter { it.lp?.jenis_pelanggaran == "pidana" }
                   adapterSkhd.adapterCallback(callbackSkhd)
                       .isVerticalView()
                       .addData(listPidanaSkhd)
                       .setLayout(R.layout.item_skhd)
                       .build(rv_skhd)
               }
               "disiplin" -> {
                   val listDisiplinSkhd = listSkhd.filter { it.lp?.jenis_pelanggaran == "disiplin" }
                   adapterSkhd.adapterCallback(callbackSkhd)
                       .isVerticalView()
                       .addData(listDisiplinSkhd)
                       .setLayout(R.layout.item_skhd)
                       .build(rv_skhd)
               }
           }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari No SKHD"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterSkhd.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        apiListSkhd()
    }
}