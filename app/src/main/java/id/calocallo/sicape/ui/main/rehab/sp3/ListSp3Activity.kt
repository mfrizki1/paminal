package id.calocallo.sicape.ui.main.rehab.sp3

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.response.Sp3Resp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_sp3.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListSp3Activity : BaseActivity() {
    private lateinit var callbackSp3: AdapterCallback<Sp3Resp>
    private var adapterSp3 = ReusableAdapter<Sp3Resp>(this)
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sp3)

        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data SP3"

        btn_add_single_sp3.setOnClickListener {
            startActivity(Intent(this, AddSp3Activity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        getListSp3()
    }

    private fun getListSp3() {
        rl_pb.visible()
        NetworkDummy().getService().getSp3().enqueue(object : Callback<ArrayList<Sp3Resp>> {
            override fun onFailure(call: Call<ArrayList<Sp3Resp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                rv_list_sp3.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<Sp3Resp>>,
                response: Response<ArrayList<Sp3Resp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    callbackSp3 = object : AdapterCallback<Sp3Resp> {
                        override fun initComponent(itemView: View, data: Sp3Resp, itemIndex: Int) {
                            itemView.txt_edit_pendidikan.text = data.no_sp4
                        }

                        override fun onItemClicked(itemView: View, data: Sp3Resp, itemIndex: Int) {
                            val intent = Intent(this@ListSp3Activity, DetailSp3Activity::class.java)
                            intent.putExtra(DetailSp3Activity.DETAIL_SP3, data)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        }
                    }
                    response.body()?.let {
                        adapterSp3.adapterCallback(callbackSp3)
                            .isVerticalView().filterable()
                            .addData(it)
                            .setLayout(R.layout.layout_edit_1_text)
                            .build(rv_list_sp3)
                    }
                } else {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_sp3.gone()
                }
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari SP3"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterSp3.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}