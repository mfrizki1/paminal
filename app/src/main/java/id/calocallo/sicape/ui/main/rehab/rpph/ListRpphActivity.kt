package id.calocallo.sicape.ui.main.rehab.rpph

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Adapter
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PutKkeOnRpphModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.response.RpphResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
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
    private lateinit var sessionManager: SessionManager
    private var listRpph = arrayListOf<RpphResp>()
    private var adapterRpph = ReusableAdapter<RpphResp>(this)
    private lateinit var callbackRpph: AdapterCallback<RpphResp>
    private var putKkeOnRpphModel = PutKkeOnRpphModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_rpph)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data RPPH"

        getListRpph()
        btn_add_single_rpph.setOnClickListener {
            startActivity(Intent(this, AddRpphActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }
    }

    private fun getListRpph() {
        rl_pb.visible()
        /*putKkeOnRpphModel = PutKkeOnRpphModel(1, "PUTKKE1/2020/xxx")
        listRpph.add(
            RpphResp(
                1, putKkeOnRpphModel,
                "RPPH/1/2020/xxx", "dasarPE\nini adada,dsah per", resources.getString(R.string.kop),
                "12-20-2000", "Banjarmasin", "Jabatan", "Faisal Rizki", "bripda",
                "123456", "tembusan abc abc abc\nabc\n abc", "", ""
            )
        )

        listRpph.add(
            RpphResp(
                2, PutKkeOnRpphModel(2, "PUTKKE2/2021/XXX"),
                "RPPH/1/2020/xxx", "dasarPE\nini adada,dsah per", resources.getString(R.string.kop),
                "12-20-2000", "Banjarmasin", "Jabatan", "Faisal Rizki", "bripda",
                "123456", "tembusan abc abc abc\nabc\n abc", "", ""
            )
        )

         */
        NetworkDummy().getService().getRpph().enqueue(object : Callback<ArrayList<RpphResp>> {
            override fun onFailure(call: Call<ArrayList<RpphResp>>, t: Throwable) {
                rl_no_data.visible()
                rv_list_rpph.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<RpphResp>>,
                response: Response<ArrayList<RpphResp>>
            ) {
                rl_pb.gone()
                if (response.isSuccessful) {
                    callbackRpph = object : AdapterCallback<RpphResp> {
                        override fun initComponent(itemView: View, data: RpphResp, itemIndex: Int) {
                            itemView.txt_edit_pendidikan.text = data.no_rpph
                        }

                        override fun onItemClicked(itemView: View, data: RpphResp, itemIndex: Int) {
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