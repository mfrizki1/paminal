package id.calocallo.sicape.ui.main.rehab.rps

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.response.RpsMinResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_rps.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListRpsActivity : BaseActivity() {
    private var adapterRps = ReusableAdapter<RpsMinResp>(this)
    private lateinit var callbackRps: AdapterCallback<RpsMinResp>
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_rps)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data RPS"
        sessionManager1 = SessionManager1(this)
        val hak = sessionManager1.fetchHakAkses()
        if(hak == "operator"){
            btn_add_single_rps.gone()
        }
        btn_add_single_rps.setOnClickListener {
            startActivity(Intent(this, AddRpsActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        getListRps()
    }

    private fun getListRps() {
        rl_pb.visible()
        NetworkConfig().getServRps().getListRps("Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(object : Callback<ArrayList<RpsMinResp>> {
                override fun onFailure(call: Call<ArrayList<RpsMinResp>>, t: Throwable) {
                    rl_no_data.toggleVisibility()
                    rv_list_rps.toggleVisibility()
                }

                override fun onResponse(
                    call: Call<ArrayList<RpsMinResp>>,
                    response: Response<ArrayList<RpsMinResp>>
                ) {
                    if (response.isSuccessful) {
                        callbackRps = object : AdapterCallback<RpsMinResp> {
                            override fun initComponent(
                                itemView: View,
                                data: RpsMinResp,
                                itemIndex: Int
                            ) {
                                itemView.txt_edit_pendidikan.text = data.no_rps
                            }

                            override fun onItemClicked(
                                itemView: View,
                                data: RpsMinResp,
                                itemIndex: Int
                            ) {
                                val intent =
                                    Intent(this@ListRpsActivity, DetailRpsActivity::class.java)
                                intent.putExtra(DetailRpsActivity.DETAIL_RPS, data)
                                startActivity(intent)
                                overridePendingTransition(
                                    R.anim.slide_in_right, R.anim.slide_out_left
                                )
                            }
                        }
                        adapterRps.adapterCallback(callbackRps)
                            .isVerticalView().filterable()
                            .addData(response.body()!!)
                            .setLayout(R.layout.layout_edit_1_text)
                            .build(rv_list_rps)
                        rl_pb.gone()
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                    }
                }
            })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari RPS"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterRps.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        getListRps()
    }
}
