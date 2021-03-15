package id.calocallo.sicape.ui.main.rehab.rpph

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.RpphMinResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_rpph.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListRpphActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterRpph = ReusableAdapter<RpphMinResp>(this)
    private lateinit var callbackRpph: AdapterCallback<RpphMinResp>
    private var putKkeOnRpphModel = PutKkeOnRpphModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_rpph)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data RPPH"
        sessionManager1 = SessionManager1(this)
        val hak = sessionManager1.fetchHakAkses()
        if(hak == "operator"){
            btn_add_single_rpph.gone()
        }
        getListRpph()
        btn_add_single_rpph.setOnClickListener {
            startActivity(Intent(this, AddRpphActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }
    }

    override fun onResume() {
        super.onResume()
        getListRpph()
    }

    private fun getListRpph() {
        rl_pb.visible()
        NetworkConfig().getServRpph().getListRpph("Bearer ${sessionManager1.fetchAuthToken()}").enqueue(object : Callback<ArrayList<RpphMinResp>> {
            override fun onFailure(call: Call<ArrayList<RpphMinResp>>, t: Throwable) {
                rl_no_data.visible()
                rv_list_rpph.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<RpphMinResp>>,
                response: Response<ArrayList<RpphMinResp>>
            ) {
                rl_pb.gone()
                if (response.isSuccessful) {
                    callbackRpph = object : AdapterCallback<RpphMinResp> {
                        override fun initComponent(itemView: View, data: RpphMinResp, itemIndex: Int) {
                            itemView.txt_edit_pendidikan.text = data.no_rpph
                        }

                        override fun onItemClicked(itemView: View, data: RpphMinResp, itemIndex: Int) {
                            val intent =
                                Intent(this@ListRpphActivity, DetailRpphActivity::class.java)
                            intent.putExtra(DetailRpphActivity.DETAIL_RPPH, data)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        }
                    }
                    response.body()?.let {
                        adapterRpph.adapterCallback(callbackRpph)
                            .isVerticalView()
                            .filterable()
                            .addData(it)
                            .setLayout(R.layout.layout_edit_1_text)
                            .build(rv_list_rpph)
                    }
                } else {
                    rl_no_data.visible()

                }
            }
        })

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari RPPH"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterRpph.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}