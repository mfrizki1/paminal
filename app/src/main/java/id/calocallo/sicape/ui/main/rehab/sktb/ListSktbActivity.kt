package id.calocallo.sicape.ui.main.rehab.sktb

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.SktbMinResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_sktb.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListSktbActivity : BaseActivity() {
    private var adapterSktb = ReusableAdapter<SktbMinResp>(this)
    private lateinit var callbackSktb: AdapterCallback<SktbMinResp>
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sktb)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data SKTB"
        sessionManager1 = SessionManager1(this)
        val hak = sessionManager1.fetchHakAkses()
        if(hak == "operator"){
            btn_add_single_sktb.gone()
        }
        btn_add_single_sktb.setOnClickListener {
            startActivity(Intent(this, AddSktbActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        getListSktb()
    }


    private fun getListSktb() {
        rl_pb.visible()
        NetworkConfig().getServSktb().getListSktb("Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(object : Callback<ArrayList<SktbMinResp>> {
                override fun onFailure(call: Call<ArrayList<SktbMinResp>>, t: Throwable) {
                    rl_no_data.visible()
                    rv_list_sktb.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<SktbMinResp>>,
                    response: Response<ArrayList<SktbMinResp>>
                ) {
                    rl_pb.gone()
                    if (response.isSuccessful) {
                        callbackSktb = object : AdapterCallback<SktbMinResp> {
                            override fun initComponent(
                                itemView: View,
                                data: SktbMinResp,
                                itemIndex: Int
                            ) {
                                itemView.txt_edit_pendidikan.text = data.no_sktb
                            }

                            override fun onItemClicked(
                                itemView: View,
                                data: SktbMinResp,
                                itemIndex: Int
                            ) {
                                val intent =
                                    Intent(this@ListSktbActivity, DetailSktbActivity::class.java)
                                intent.putExtra(DetailSktbActivity.DETAIL_SKTB, data)
                                startActivity(intent)
                                overridePendingTransition(
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left
                                )
                            }
                        }
                        response.body()?.let {
                            adapterSktb.adapterCallback(callbackSktb)
                                .isVerticalView().filterable()
                                .addData(it)
                                .setLayout(R.layout.layout_edit_1_text)
                                .build(rv_list_sktb)
                        }
                    } else {
                        rl_no_data.visible()
                        rv_list_sktb.gone()
                    }
                }
            })


    }

    override fun onResume() {
        super.onResume()
        getListSktb()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari SKTB"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterSktb.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}