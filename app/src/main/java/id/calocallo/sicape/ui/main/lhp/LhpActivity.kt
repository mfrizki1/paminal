package id.calocallo.sicape.ui.main.lhp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.*
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.lhp.DetailLhpActivity.Companion.DETAIL_LHP
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lhp.*
import kotlinx.android.synthetic.main.item_lhp.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var list: ArrayList<LhpResp>
    private var refPenyelidikan = ArrayList<RefPenyelidikanResp>()
    private var personelPenyelidikan = ArrayList<PersonelPenyelidikResp>()
    private var saksiLhp = ArrayList<SaksiLhpResp>()
    private var ketTerlaporLhpResp = ArrayList<KetTerlaporLhpResp>()

    //    private lateinit var adapterLhp: LhpAdapter
    private lateinit var adapterLhp: ReusableAdapter<LhpMinResp>
    private lateinit var adapterCallbackLhp: AdapterCallback<LhpMinResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lhp)
        sessionManager1 = SessionManager1(this)
        list = ArrayList()
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Laporan Hasil Penyelidikan"
        apiListLhp()
//        getListLHP(response.body())

        btn_lhp_add.setOnClickListener {
            startActivity(Intent(this, AddLhpActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    private fun apiListLhp() {
        rl_pb.visible()
        NetworkConfig().getServLhp().getLhpAll("Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(
                object :
                    Callback<ArrayList<LhpMinResp>> {
                    override fun onResponse(
                        call: Call<ArrayList<LhpMinResp>>,
                        response: Response<ArrayList<LhpMinResp>>
                    ) {
                        if (response.isSuccessful) {
                            rl_pb.gone()
                            getListLHP(response.body())
                        } else {
                            rl_no_data.visible()
                            rl_pb.gone()
                            rv_lhp.gone()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<LhpMinResp>>, t: Throwable) {
                        Toast.makeText(this@LhpActivity, "$t", Toast.LENGTH_SHORT).show()
                        rl_no_data.visible()
                        rl_pb.gone()
                        rv_lhp.gone()
                    }
                })
    }

    private fun getListLHP(list: ArrayList<LhpMinResp>?) {

        //adapterLibrary
        adapterLhp = ReusableAdapter(this)
        adapterCallbackLhp = object : AdapterCallback<LhpMinResp> {
            override fun initComponent(itemView: View, data: LhpMinResp, itemIndex: Int) {
                itemView.txt_no_lhp.text = data.no_lhp
                /*                val lidik = data.personel_penyelidik?.find { it.is_ketua == 1 }
                if (lidik?.is_ketua == 1) itemView.txt_ketua_tim.text =
                                 "Ketua Tim : ${lidik.nama}"
                             var terbukti: String = if (data.isTerbukti == 0) {
                                 "TIdak Terbukti"
                             } else {
                                 "Terbukti"
                             }
                             itemView.txt_isTerbukti.text = terbukti*/
            }

            override fun onItemClicked(itemView: View, data: LhpMinResp, itemIndex: Int) {
                val intent = Intent(this@LhpActivity, DetailLhpActivity::class.java)
                intent.putExtra(DETAIL_LHP, data)
                startActivity(intent)
            }
        }
        list?.let {
            adapterLhp.filterable()
                .adapterCallback(adapterCallbackLhp)
                .setLayout(R.layout.item_lhp)
                .isVerticalView()
                .addData(it)
                .build(rv_lhp)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari No LHP"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterLhp.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
        apiListLhp()
    }
}