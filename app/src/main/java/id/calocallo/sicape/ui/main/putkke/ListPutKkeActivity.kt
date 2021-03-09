package id.calocallo.sicape.ui.main.putkke

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
import id.calocallo.sicape.network.response.PutKkeMinResp
import id.calocallo.sicape.network.response.PutKkeResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_put_kke.*
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

class ListPutKkeActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterPutKke = ReusableAdapter<PutKkeMinResp>(this)
    private lateinit var callbackPutKke: AdapterCallback<PutKkeMinResp>
    private var listLhpOnSkhd = LhpOnSkhd()
    private var listLpOnSkhd = LpOnSkhd()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_put_kke)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Putusan Kode Etik"
        sessionManager1 = SessionManager1(this)
        btn_add_single_put_kke.setOnClickListener {
            startActivity(Intent(this, AddPutKkeActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }
        apiListPutKke()
    }

    private fun apiListPutKke() {
        rl_pb.visible()
        NetworkConfig().getServSkhd().getPutKke("Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(
                object :
                    Callback<ArrayList<PutKkeMinResp>> {
                    override fun onResponse(
                        call: Call<ArrayList<PutKkeMinResp>>,
                        response: Response<ArrayList<PutKkeMinResp>>
                    ) {
                        if (response.isSuccessful) {
                            rl_pb.gone()
                            getListPutKke(response.body())
                        } else {
                            rl_pb.gone()
                            rl_no_data.visible()
                            rv_put_kke.gone()
                            Toast.makeText(
                                this@ListPutKkeActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<PutKkeMinResp>>, t: Throwable) {
                        Toast.makeText(this@ListPutKkeActivity, "$t", Toast.LENGTH_SHORT).show()
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_put_kke.gone()
                    }
                })
    }

    private fun getListPutKke(list: ArrayList<PutKkeMinResp>?) {
        callbackPutKke = object : AdapterCallback<PutKkeMinResp> {
            override fun initComponent(itemView: View, data: PutKkeMinResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.no_putkke
            }

            override fun onItemClicked(itemView: View, data: PutKkeMinResp, itemIndex: Int) {
                val intent = Intent(this@ListPutKkeActivity, DetailPutKkeActivity::class.java)
                intent.putExtra(DETAIL_PUTKKE, data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        list?.let {
            adapterPutKke.adapterCallback(callbackPutKke)
                .isVerticalView().addData(it)
                .setLayout(R.layout.layout_edit_1_text)
                .build(rv_put_kke)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari No PUT KKE"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterPutKke.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        apiListPutKke()
    }

    companion object {
        const val DETAIL_PUTKKE = "DETAIL_PUTKKE"
    }
}