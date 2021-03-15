package id.calocallo.sicape.ui.gelar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LhgMinResp
import id.calocallo.sicape.utils.GelarDataManager
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_gelar.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.network.NetworkConfig
import kotlinx.android.synthetic.main.activity_detail_lp_pidana.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.view_no_data.*

class ListGelarActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var gelarDataManager: GelarDataManager
    private lateinit var callbackGelar: AdapterCallback<LhgMinResp>
    private var adapterGelar = ReusableAdapter<LhgMinResp>(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_gelar)
        sessionManager1 = SessionManager1(this)
        gelarDataManager = GelarDataManager(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Gelar Perkara"
        apiListGelar()

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_add_single_gelar.gone()
        }

        btn_add_single_gelar.setOnClickListener {
            startActivity(Intent(this, AddGelarActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        /*PICK GELAR*/

    }

    private fun apiListGelar() {
        rl_pb.visible()
        NetworkConfig().getServLhg().getListLhg("Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(object :
                Callback<ArrayList<LhgMinResp>> {
                override fun onResponse(
                    call: Call<ArrayList<LhgMinResp>>,
                    response: Response<ArrayList<LhgMinResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        listGelar(response.body())
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_gelar.gone()
                    }
                }

                override fun onFailure(call: Call<ArrayList<LhgMinResp>>, t: Throwable) {
                    Toast.makeText(this@ListGelarActivity, "$t", Toast.LENGTH_SHORT).show()
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_gelar.gone()
                }
            })
    }

    private fun listGelar(list: java.util.ArrayList<LhgMinResp>?) {
        callbackGelar = object : AdapterCallback<LhgMinResp> {
            override fun initComponent(itemView: View, data: LhgMinResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.dugaan
            }

            override fun onItemClicked(itemView: View, data: LhgMinResp, itemIndex: Int) {
                val intent = Intent(this@ListGelarActivity, DetailGelarActivity::class.java).apply {
                    this.putExtra(DATA_LHG, data)
                }
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        list?.let {
            adapterGelar.adapterCallback(callbackGelar)
                .isVerticalView().filterable().addData(it).setLayout(R.layout.layout_edit_1_text)
                .build(rv_list_gelar)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Dugaan"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterGelar.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        const val DATA_LHG = "DATA_LHG"
    }

    override fun onResume() {
        super.onResume()
        apiListGelar()
        btn_add_single_gelar.setOnClickListener {
            startActivity(Intent(this, AddGelarActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}