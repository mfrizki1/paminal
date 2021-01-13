package id.calocallo.sicape.ui.main.skhp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Adapter
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.response.SkhpResp
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_skhp.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListSkhpActivity : BaseActivity() {
    private var adapteSkhp = ReusableAdapter<SkhpResp>(this)
    private lateinit var callbackSkhp: AdapterCallback<SkhpResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_skhp)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data SKHP"
        btn_add_skhp.setOnClickListener {
            startActivity(Intent(this, AddSkhpActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        getListSkhp()
    }

    private fun getListSkhp() {
        rl_pb.visible()
        NetworkDummy().getService().getSkhp().enqueue(object : Callback<ArrayList<SkhpResp>> {
            override fun onFailure(call: Call<ArrayList<SkhpResp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                rv_list_skhp.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<SkhpResp>>,
                response: Response<ArrayList<SkhpResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    callbackSkhp = object : AdapterCallback<SkhpResp> {
                        override fun initComponent(itemView: View, data: SkhpResp, itemIndex: Int) {
                            itemView.txt_edit_pendidikan.text = data.no_skhp
                        }

                        override fun onItemClicked(itemView: View, data: SkhpResp, itemIndex: Int) {
                            val intent =
                                Intent(this@ListSkhpActivity, DetailSkhpActivity::class.java)
                            intent.putExtra(DetailSkhpActivity.DETAIL_SKHP, data)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        }
                    }
                    response.body()?.let {
                        adapteSkhp.adapterCallback(callbackSkhp)
                            .isVerticalView().filterable().addData(it)
                            .setLayout(R.layout.layout_edit_1_text).build(rv_list_skhp)
                    }
                } else {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_skhp.gone()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari SKHP"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapteSkhp.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}