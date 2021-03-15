package id.calocallo.sicape.ui.main.choose

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.SktbOnSp3
import id.calocallo.sicape.model.SkttOnSp3
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_choose_sktb_sktt.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseSktbSkttActivity : BaseActivity() {
    companion object {
        const val GET_SKTT = "GET_SKTT"
        const val GET_SKTB = "GET_SKTB"
    }

    private var adapterSktb = ReusableAdapter<SktbOnSp3>(this)
    private lateinit var callbackSktb: AdapterCallback<SktbOnSp3>
    private var adapterSktt = ReusableAdapter<SkttOnSp3>(this)
    private lateinit var callbackSktt: AdapterCallback<SkttOnSp3>
    private var getIsSkttFromSp3: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_sktb_sktt)
        setupActionBarWithBackButton(toolbar)
        if (getIsSkttFromSp3.isNullOrBlank()) {
            getListSktb()
            supportActionBar?.title = "Pilih Data SKTB"

        } else {
            getListSktt()
            supportActionBar?.title = "Pilih Data SKTT"
        }
    }

    private fun getListSktb() {
        rl_pb.visible()
        NetworkDummy().getService().getSktbWithoutSp3()
            .enqueue(object : Callback<ArrayList<SktbOnSp3>> {
                override fun onFailure(call: Call<ArrayList<SktbOnSp3>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_choose_sktb_sktt.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<SktbOnSp3>>,
                    response: Response<ArrayList<SktbOnSp3>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        callbackSktb = object : AdapterCallback<SktbOnSp3> {
                            override fun initComponent(
                                itemView: View,
                                data: SktbOnSp3,
                                itemIndex: Int
                            ) {
                                itemView.txt_1_clickable.text = data.no_sktb

                            }

                            override fun onItemClicked(
                                itemView: View,
                                data: SktbOnSp3,
                                itemIndex: Int
                            ) {
                                itemView.img_clickable.toggleVisibility()
                                val intent = Intent()
                                intent.putExtra(GET_SKTB, data)
                                setResult(Activity.RESULT_OK, intent)
                                finish()
                            }
                        }
                        response.body()?.let {
                            adapterSktb.adapterCallback(callbackSktb)
                                .isVerticalView().filterable()
                                .addData(it)
                                .setLayout(R.layout.layout_1_text_clickable)
                                .build(rv_choose_sktb_sktt)
                        }
                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_choose_sktb_sktt.gone()
                    }
                }
            })
    }

    private fun getListSktt() {
        rl_pb.visible()
        NetworkDummy().getService().getSkttWithoutSp3().enqueue(object :
            Callback<ArrayList<SkttOnSp3>> {
            override fun onFailure(call: Call<ArrayList<SkttOnSp3>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                rv_choose_sktb_sktt.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<SkttOnSp3>>,
                response: Response<ArrayList<SkttOnSp3>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    callbackSktt = object : AdapterCallback<SkttOnSp3> {
                        override fun initComponent(
                            itemView: View,
                            data: SkttOnSp3,
                            itemIndex: Int
                        ) {
                            itemView.txt_1_clickable.text = data.no_sktt
                        }

                        override fun onItemClicked(
                            itemView: View,
                            data: SkttOnSp3,
                            itemIndex: Int
                        ) {
                            itemView.img_clickable.toggleVisibility()
                            val intent = Intent()
                            intent.putExtra(GET_SKTT, data)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }
                    }
                    response.body()?.let {
                        adapterSktt.adapterCallback(callbackSktt)
                            .isVerticalView().filterable()
                            .addData(it)
                            .setLayout(R.layout.layout_1_text_clickable)
                            .build(rv_choose_sktb_sktt)
                    }
                } else {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_choose_sktb_sktt.gone()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        if (getIsSkttFromSp3.isNullOrBlank()) {
            searchView.queryHint = "Cari SKTB"
        } else {
            searchView.queryHint = "Cari SKTT"
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (getIsSkttFromSp3.isNullOrBlank()) {
                    adapterSktb.filter.filter(newText)

                } else {
                    adapterSktt.filter.filter(newText)
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}