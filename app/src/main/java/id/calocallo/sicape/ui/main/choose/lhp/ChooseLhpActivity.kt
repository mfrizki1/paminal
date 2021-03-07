package id.calocallo.sicape.ui.main.choose.lhp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.choose.lp.ChooseLpSkhdActivity
import id.calocallo.sicape.ui.main.choose.lp.ListLpSkhdActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_choose_lhp.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChooseLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var list = arrayListOf<LhpResp>()
    private var refPenyelidikan = ArrayList<RefPenyelidikanResp>()
    private var personelPenyelidikan = ArrayList<PersonelPenyelidikResp>()
    private var saksiLhp = ArrayList<SaksiLhpResp>()
    private var ketTerlaporLhpResp = ArrayList<KetTerlaporLhpResp>()
    private var adapterChooseLhp = ReusableAdapter<LhpMinResp>(this)
    private lateinit var callbackChooseLhp: AdapterCallback<LhpMinResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_lhp)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data LHP"
//        getListLhp()
        apiListLhp()
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
                            getListLhp(response.body())
                        } else {
                            rl_pb.gone()
                            rl_no_data.visible()
                            rv_choose_lhp.gone()
                            Toast.makeText(
                                this@ChooseLhpActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<LhpMinResp>>, t: Throwable) {
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_choose_lhp.gone()
                        Toast.makeText(this@ChooseLhpActivity, "$t", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
    }

    private fun getListLhp(list: ArrayList<LhpMinResp>?) {

        callbackChooseLhp = object : AdapterCallback<LhpMinResp> {
            override fun initComponent(itemView: View, data: LhpMinResp, itemIndex: Int) {
                itemView.txt_1_clickable.text = data.no_lhp
            }

            override fun onItemClicked(itemView: View, data: LhpMinResp, itemIndex: Int) {
                itemView.img_clickable.toggleVisibility()
                /*   val intent = Intent()
                   intent.putExtra("CHOOSE_LHP", data)
                   setResult(RESULT_OK, intent)
                   finish()*/
                /*Goto Pick LP from ID LHP*/
                val intent =
//                    Intent(this@ChooseLhpActivity, ChooseLpSkhdActivity::class.java).apply {
                    Intent(this@ChooseLhpActivity, ListLpSkhdActivity::class.java).apply {
//                        this.putExtra(PICK_SKHD_ADD, true)
                        this.putExtra(DATA_LHP, data)
                    }
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

        }
        list?.let {
            adapterChooseLhp.adapterCallback(callbackChooseLhp)
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.layout_1_text_clickable)
                .build(rv_choose_lhp)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari LHP"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterChooseLhp.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    companion object {
        const val DATA_LHP = "DATA_LHP"
        const val PICK_SKHD_ADD = "PICK_SKHD_ADD"
    }
}
