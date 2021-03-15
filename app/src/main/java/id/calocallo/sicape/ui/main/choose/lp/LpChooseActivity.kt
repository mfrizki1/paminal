package id.calocallo.sicape.ui.main.choose.lp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.gelar.AddGelarActivity
import id.calocallo.sicape.ui.main.lhp.add.AddLhpActivity
import id.calocallo.sicape.ui.main.lhp.edit.ref_penyelidikan.AddRefPenyelidikActivity
import id.calocallo.sicape.ui.main.rehab.rpph.AddRpphActivity
import id.calocallo.sicape.ui.main.rehab.rps.AddRpsActivity
import id.calocallo.sicape.ui.main.rehab.sktb.AddSktbActivity
import id.calocallo.sicape.ui.main.rehab.sp4.AddSp4Activity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lp_choose.*
import kotlinx.android.synthetic.main.item_2_text.view.*
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

class LpChooseActivity : BaseActivity() {
    companion object {
        const val JENIS_LP_CHOOSE = "JENIS_LP_CHOOSE"
    }

    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterLpChoose: ReusableAdapter<LpResp>
    private lateinit var callbackLpChoose: AdapterCallback<LpResp>

    private var adapterLpAll = ReusableAdapter<LpMinResp>(this)
    private lateinit var callbackLpAll: AdapterCallback<LpMinResp>
    private var tempJenis: String? = null
    private var isLpForRef: Boolean? = null
    private var isLpForLhg: Boolean? = null
    private var isLpForRps: Boolean? = null
    private var isLpForRpph: Boolean? = null
    private var isLpForSktb: Boolean? = null
    private var isLpForSp4: Boolean? = null
    private var urlKasus: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lp_choose)

        sessionManager1 = SessionManager1(this)
        adapterLpChoose = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
        when (intent.extras?.getString(JENIS_LP_CHOOSE)) {
            "Pidana" -> {
                supportActionBar?.title = "Pilih Data Laporan Pidana"
                tempJenis = "pidana"
            }
            "Kode Etik" -> {
                supportActionBar?.title = "Pilih Data Laporan Kode Etik"
                tempJenis = "kode/etik"
            }
            "Disiplin" -> {
                tempJenis = "disiplin"
                supportActionBar?.title = "Pilih Data Laporan Disiplin"
            }
            else -> {
                supportActionBar?.title = "Pilih Data Laporan"
            }
        }
        /*LIST LP FOR REF PENYELIDIKAN*/
        isLpForRef = intent.getBooleanExtra(AddRefPenyelidikActivity.IS_KASUS_MASUK, false)
        isLpForLhg = intent.getBooleanExtra(AddGelarActivity.IS_LHG, false)
        isLpForRps = intent.getBooleanExtra(AddRpsActivity.IS_RPS, false)
        isLpForRpph = intent.getBooleanExtra(AddRpphActivity.IS_RPPH, false)
        isLpForSktb = intent.getBooleanExtra(AddSktbActivity.IS_SKTB, false)
        isLpForSp4 = intent.getBooleanExtra(AddSp4Activity.IS_SP4, false)
        when {
            isLpForRef == true -> {
                urlKasus = "masuk"
            }
            isLpForLhg == true -> {
                Log.e("lhg", "$isLpForLhg")
            }
            isLpForRps == true -> {
                urlKasus = "masa/hukuman/pidana/disiplin"
            }
            isLpForRpph == true -> {
                urlKasus = "masa/hukuman/kode/etik"
            }
            isLpForSktb == true -> {
                urlKasus = "masa/hukuman"
            }
            isLpForSp4 == true -> {
                urlKasus = "masa/rehab"
            }
            else -> {
                Log.e("lhg", "no DATA")
            }
        }
        getListLpRef()
//        getListLpByJenis(tempJenis)
        /* sktt
         val sktt = intent.extras?.getString(AddSkttActivity.LP_SKTT)
         if (sktt == null) {
             getListLpChoose(tempJenis)
         } else {
             getListLpWithoutSktbPutkkeSktt(tempJenis)
         }
         Log.e("sktt", "$sktt")*/
    }

    private fun getListLpRef() {
        rl_pb.visible()
        NetworkConfig().getServLp()
            .getLpForRefPenyelidikan("Bearer ${sessionManager1.fetchAuthToken()}", urlKasus)
            .enqueue(
                object : Callback<ArrayList<LpMinResp>> {
                    override fun onResponse(
                        call: Call<ArrayList<LpMinResp>>,
                        response: Response<ArrayList<LpMinResp>>
                    ) {
                        if (response.isSuccessful) {
                            rl_pb.gone()
                            listLp(response.body())
                        } else {
                            rl_pb.gone()
                            rv_list_lp_choose.gone()
                            rl_no_data.visible()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<LpMinResp>>, t: Throwable) {
                        rl_pb.gone()
                        rv_list_lp_choose.gone()
                        rl_no_data.visible()
                        Toast.makeText(this@LpChooseActivity, "$t", Toast.LENGTH_SHORT).show()
                    }
                })
    }

    private fun getListLpByJenis(tempJenis: String?) {
        rl_pb.visible()
        NetworkConfig().getServLp()
            .getLpByJenis("Bearer ${sessionManager1.fetchAuthToken()}", tempJenis).enqueue(
                object : Callback<ArrayList<LpMinResp>> {
                    override fun onResponse(
                        call: Call<ArrayList<LpMinResp>>,
                        response: Response<ArrayList<LpMinResp>>
                    ) {
                        if (response.isSuccessful) {
                            rl_pb.gone()
                            listLp(response.body())
                        } else {
                            rl_pb.gone()
                            rv_list_lp_choose.gone()
                            rl_no_data.visible()
                            Toast.makeText(
                                this@LpChooseActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<LpMinResp>>, t: Throwable) {
                        Toast.makeText(this@LpChooseActivity, "$t", Toast.LENGTH_SHORT).show()
                        rl_pb.gone()
                        rv_list_lp_choose.gone()
                        rl_no_data.visible()
                    }
                })
    }

    private fun listLp(list: java.util.ArrayList<LpMinResp>?) {
        callbackLpAll = object : AdapterCallback<LpMinResp> {
            override fun initComponent(itemView: View, data: LpMinResp, itemIndex: Int) {
                itemView.txt_detail_1.text = data.no_lp
                itemView.txt_detail_2.text = data.jenis_pelanggaran.toString().toUpperCase()
            }

            override fun onItemClicked(itemView: View, data: LpMinResp, itemIndex: Int) {
                val intent = Intent().apply {
                    this.putExtra(AddLhpActivity.DATA_LP, data)
                }
                setResult(PickJenisLpActivity.RES_LP_CHOOSE, intent)
                finish()
            }
        }
        list?.let { sortJenisLP(it) }?.let {
            adapterLpAll.adapterCallback(callbackLpAll)
                .isVerticalView()
                .filterable()
                .addData(it)
                .setLayout(R.layout.item_2_text)
                .build(rv_list_lp_choose)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari LP"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterLpAll.filter.filter(newText)
//                adapterLpKkeChoose.filter.filter(newText)
//                adapterLpPidanaChoose.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private val roles: HashMap<String, Int> = hashMapOf(
        "pidana" to 0,
        "kode_etik" to 1,
        "disiplin" to 2
    )

    private fun sortJenisLP(lp: ArrayList<LpMinResp>): ArrayList<LpMinResp> {
        val comparator = Comparator { o1: LpMinResp, o2: LpMinResp ->
            return@Comparator roles[o1.jenis_pelanggaran]!! - roles[o2.jenis_pelanggaran]!!
        }
        val copy = arrayListOf<LpMinResp>().apply { addAll(lp) }
        copy.sortWith(comparator)
        return copy
    }
}

