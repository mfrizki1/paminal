package id.calocallo.sicape.ui.main.rehab.sktt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.response.SkttResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_sktt.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListSkttActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private var adapterSktt = ReusableAdapter<SkttResp>(this)
    private lateinit var callbackSktt: AdapterCallback<SkttResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sktt)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data SKTT"
        getListSktt()
        btn_add_single_sktt.setOnClickListener {
            startActivity(Intent(this, AddSkttActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun getListSktt() {
        rl_pb.visible()
        NetworkDummy().getService().getSktt().enqueue(object : Callback<ArrayList<SkttResp>> {
            override fun onFailure(call: Call<ArrayList<SkttResp>>, t: Throwable) {
                rl_no_data.visible()
                rv_list_sktt.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<SkttResp>>,
                response: Response<ArrayList<SkttResp>>
            ) {
                rl_pb.gone()
                if (response.isSuccessful) {
                    callbackSktt = object : AdapterCallback<SkttResp> {
                        override fun initComponent(itemView: View, data: SkttResp, itemIndex: Int) {
                            itemView.txt_edit_pendidikan.text = data.no_sktt
                        }

                        override fun onItemClicked(itemView: View, data: SkttResp, itemIndex: Int) {
                            val intent =
                                Intent(this@ListSkttActivity, DetailSkttActivity::class.java)
                            intent.putExtra(DetailSkttActivity.DETAIL_SKTT, data)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        }
                    }
                    response.body()?.let {
                        adapterSktt.adapterCallback(callbackSktt)
                            .isVerticalView().filterable()
                            .addData(it)
                            .setLayout(R.layout.layout_edit_1_text)
                            .build(rv_list_sktt)
                    }
                } else {
                    rl_no_data.visible()
                    rv_list_sktt.gone()
                }
            }
        })


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari SKTT"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterSktt.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}