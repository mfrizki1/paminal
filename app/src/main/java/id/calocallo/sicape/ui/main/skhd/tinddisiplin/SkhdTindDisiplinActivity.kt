package id.calocallo.sicape.ui.main.skhd.tinddisiplin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.TindDisplMinResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_skhd_tind_disiplin.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SkhdTindDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterTindDisiplin = ReusableAdapter<TindDisplMinResp>(this)
    private lateinit var callbackTindDisplMin: AdapterCallback<TindDisplMinResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skhd_tind_disiplin)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "List Data Tindakan Disiplin"
        sessionManager1 = SessionManager1(this)
        apiListTindDispl()
        btn_add_single_tind_disiplin.setOnClickListener {
            startActivity(Intent(this, AddTindDisiplinSkhdActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun apiListTindDispl() {
        rl_pb.visible()
        NetworkConfig().getServSkhd()
            .getPersonelTindDispl("Bearer ${sessionManager1.fetchAuthToken()}").enqueue(
                object :
                    Callback<ArrayList<TindDisplMinResp>> {
                    override fun onResponse(
                        call: Call<ArrayList<TindDisplMinResp>>,
                        response: Response<ArrayList<TindDisplMinResp>>
                    ) {
                        if (response.isSuccessful) {
                            rl_pb.gone()
                            listTindDisiplin(response.body())
                        } else {
                            rl_pb.gone()
                            rv_list_tind_disipin.gone()
                            rl_no_data.visible()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<TindDisplMinResp>>, t: Throwable) {
                        Toast.makeText(this@SkhdTindDisiplinActivity, "$t", Toast.LENGTH_SHORT)
                            .show()
                        rl_pb.gone()
                        rv_list_tind_disipin.gone()
                        rl_no_data.visible()
                    }
                })
    }

    private fun listTindDisiplin(list: ArrayList<TindDisplMinResp>?) {
        callbackTindDisplMin = object : AdapterCallback<TindDisplMinResp> {
            @SuppressLint("SetTextI18n")
            override fun initComponent(itemView: View, data: TindDisplMinResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = "Nama: ${data.personel?.nama}\n" +
                        "Pangkat: ${data.personel?.pangkat}, NRP: ${data.personel?.nrp}"
//                itemView.txt_detail_2.text = data.isi_tindakan_disiplin
            }

            override fun onItemClicked(itemView: View, data: TindDisplMinResp, itemIndex: Int) {
                val intent =
                    Intent(this@SkhdTindDisiplinActivity, DetailTindDisiplinActivity::class.java)
                intent.putExtra(EDIT_TIND_DISIPLIN, data)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
        list?.let {
            adapterTindDisiplin.adapterCallback(callbackTindDisplMin)
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.layout_edit_1_text)
                .build(rv_list_tind_disipin)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari No Nama"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterTindDisiplin.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        apiListTindDispl()
    }

    companion object {
        const val EDIT_TIND_DISIPLIN = "EDIT_TIND_DISIPLIN"
    }
}