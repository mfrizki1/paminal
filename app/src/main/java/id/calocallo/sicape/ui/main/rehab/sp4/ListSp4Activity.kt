package id.calocallo.sicape.ui.main.rehab.sp4

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.Sp4MinResp
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

class ListSp4Activity : BaseActivity() {
    private lateinit var callbackSp4: AdapterCallback<Sp4MinResp>
    private var adapterSp4 = ReusableAdapter<Sp4MinResp>(this)
    private lateinit var sessionManager1: SessionManager1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sp3)

        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data SP3"

        btn_add_single_sp3.setOnClickListener {
            startActivity(Intent(this, AddSp4Activity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        getListSp3()
    }

    private fun getListSp3() {
        rl_pb.visible()
        NetworkConfig().getServSp4().getListSp4("Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(object : Callback<ArrayList<Sp4MinResp>> {
                override fun onFailure(call: Call<ArrayList<Sp4MinResp>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_sp3.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<Sp4MinResp>>,
                    response: Response<ArrayList<Sp4MinResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        callbackSp4 = object : AdapterCallback<Sp4MinResp> {
                            override fun initComponent(
                                itemView: View,
                                data: Sp4MinResp,
                                itemIndex: Int
                            ) {
                                itemView.txt_edit_pendidikan.text = data.no_sp4
                            }

                            override fun onItemClicked(
                                itemView: View,
                                data: Sp4MinResp,
                                itemIndex: Int
                            ) {
                                val intent =
                                    Intent(this@ListSp4Activity, DetailSp4Activity::class.java)
                                intent.putExtra(DetailSp4Activity.DETAIL_SP4, data)
                                startActivity(intent)
                                overridePendingTransition(
                                    R.anim.slide_in_right,
                                    R.anim.slide_out_left
                                )
                            }
                        }
                        response.body()?.let {
                            adapterSp4.adapterCallback(callbackSp4)
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
                adapterSp4.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
    override fun onResume() {
        super.onResume()
        getListSp3()
    }

}