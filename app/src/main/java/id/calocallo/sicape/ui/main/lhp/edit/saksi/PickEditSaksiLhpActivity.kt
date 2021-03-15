package id.calocallo.sicape.ui.main.lhp.edit.saksi

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.LhpMinResp
import id.calocallo.sicape.network.response.SaksiLhpResp
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity.Companion.EDIT_LHP
import id.calocallo.sicape.ui.main.lhp.edit.saksi.AddSingleSaksiLhpActivity.Companion.ADD_SAKSI_LHP
import id.calocallo.sicape.ui.main.lhp.edit.saksi.EditSaksiLhpActivity.Companion.EDIT_SAKSI_LHP
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_edit_saksi_lhp.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickEditSaksiLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterSaksiLhp: ReusableAdapter<SaksiLhpResp>
    private lateinit var callbackSaksiLhp: AdapterCallback<SaksiLhpResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_edit_saksi_lhp)
        sessionManager1 = SessionManager1(this)
        adapterSaksiLhp = ReusableAdapter(this)

        val detailLhp = intent.extras?.getParcelable<LhpMinResp>(EDIT_LHP)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Data Saksi LHP"

        btn_add_single_saksi_lhp.setOnClickListener {
            val intent = Intent(this, AddSingleSaksiLhpActivity::class.java)
            intent.putExtra(ADD_SAKSI_LHP, detailLhp)
            startActivity(intent)

        }
        apiListSaksiLhp(detailLhp)
//        getListSaksiLhp(detailLhp)
    }

    private fun apiListSaksiLhp(detailLhp: LhpMinResp?) {
        NetworkConfig().getServLhp()
            .getAllSaksiLhp("Bearer ${sessionManager1.fetchAuthToken()}", detailLhp?.id)
            .enqueue(object : Callback<ArrayList<SaksiLhpResp>> {
                override fun onResponse(
                    call: Call<ArrayList<SaksiLhpResp>>,
                    response: Response<ArrayList<SaksiLhpResp>>
                ) {
                    if (response.isSuccessful) {
                        getListSaksiLhp(response.body())
                    } else {
                        Toast.makeText(
                            this@PickEditSaksiLhpActivity,
                            R.string.error,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<SaksiLhpResp>>, t: Throwable) {
                    Toast.makeText(this@PickEditSaksiLhpActivity, "$t", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getListSaksiLhp(dataSaksi: ArrayList<SaksiLhpResp>?) {

        callbackSaksiLhp = object : AdapterCallback<SaksiLhpResp> {
            override fun initComponent(itemView: View, data: SaksiLhpResp, itemIndex: Int) {
                if(data.personel == null) {
                    itemView.txt_detail_1.text = data.nama
                }else{
                    itemView.txt_detail_1.text = data.personel?.nama
                }
                when (data.status_saksi) {
                    "personel" -> itemView.txt_detail_2.text = "Personel"
                    "sipil" -> itemView.txt_detail_2.text = "Sipil"

                }
            }

            override fun onItemClicked(itemView: View, data: SaksiLhpResp, itemIndex: Int) {
                val intent = Intent(this@PickEditSaksiLhpActivity, EditSaksiLhpActivity::class.java)
                intent.putExtra(EDIT_SAKSI_LHP, data)
                startActivity(intent)
            }
        }
        dataSaksi?.let {
            adapterSaksiLhp.adapterCallback(callbackSaksiLhp)
                .isVerticalView().addData(it).setLayout(R.layout.item_2_text)
                .build(rv_list_saksi_lhp)
        }
    }

    override fun onResume() {
        super.onResume()
        val detailLhp = intent.extras?.getParcelable<LhpMinResp>(EDIT_LHP)
        apiListSaksiLhp(detailLhp)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Nama Saksi"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterSaksiLhp.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

}