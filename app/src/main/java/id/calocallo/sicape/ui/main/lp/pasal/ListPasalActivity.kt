package id.calocallo.sicape.ui.main.lp.pasal

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.PasalResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_pasal.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListPasalActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterPasal = ReusableAdapter<PasalResp>(this)
    private lateinit var callbackPasal: AdapterCallback<PasalResp>
    companion object{
        const val RES_PASAL_DILANGGAR = 2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_pasal)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Pasal"
        sessionManager1 = SessionManager1(this)
        apiListPasal()

        btn_add_single_pasal_list.setOnClickListener {
            startActivity(Intent(this, AddPasalActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onResume() {
        super.onResume()
        apiListPasal()
    }

    private fun apiListPasal() {
        rl_pb.visible()
        NetworkConfig().getServLp().getAllPasal("Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(object :
                Callback<ArrayList<PasalResp>> {
                override fun onFailure(call: Call<ArrayList<PasalResp>>, t: Throwable) {
                    rl_pb.gone()
                    rl_no_data.visible()
                    rv_list_pasal.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<PasalResp>>,
                    response: Response<ArrayList<PasalResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        getListPasal(response.body())

                    } else {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_pasal.gone()
                    }
                }
            })
    }

    private fun getListPasal(list: ArrayList<PasalResp>?) {
        val pickPasal = intent.getBooleanExtra(AddPasalDilanggarActivity.PICK_PASAL, false)
        if (pickPasal) {
            callbackPasal = object : AdapterCallback<PasalResp> {
                override fun initComponent(itemView: View, data: PasalResp, itemIndex: Int) {
                    itemView.txt_1_clickable.text = data.nama_pasal
                }

                override fun onItemClicked(itemView: View, data: PasalResp, itemIndex: Int) {
                    itemView.img_clickable.visible()
                    val intent = Intent().apply {
                        this.putExtra("DATA_PASAL", data)
                    }
                    setResult(RES_PASAL_DILANGGAR, intent)
                    finish()
                }
            }

            list?.let {
                adapterPasal.adapterCallback(callbackPasal)
                    .isVerticalView()
                    .filterable().addData(it)
                    .setLayout(R.layout.layout_1_text_clickable).build(rv_list_pasal)
            }
        } else {
            callbackPasal = object : AdapterCallback<PasalResp> {
                override fun initComponent(itemView: View, data: PasalResp, itemIndex: Int) {
                    itemView.txt_edit_pendidikan.text = data.nama_pasal
                }

                override fun onItemClicked(itemView: View, data: PasalResp, itemIndex: Int) {
                    val intent =
                        Intent(this@ListPasalActivity, DetailPasalActivity::class.java).apply {
                            this.putExtra(DetailPasalActivity.DETAIL_PASAL, data)
                        }
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }
            }
            list?.let {
                adapterPasal.adapterCallback(callbackPasal)
                    .isVerticalView()
                    .filterable().addData(it)
                    .setLayout(R.layout.layout_edit_1_text).build(rv_list_pasal)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Pasal"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterPasal.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}