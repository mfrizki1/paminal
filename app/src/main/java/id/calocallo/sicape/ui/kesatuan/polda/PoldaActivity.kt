package id.calocallo.sicape.ui.kesatuan.polda

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.ui.main.addpersonal.AddPersonelActivity
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_polda.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PoldaActivity : BaseActivity() {


    private var adapterPolda = ReusableAdapter<SatKerResp>(this)
    private lateinit var callbackPolda: AdapterCallback<SatKerResp>
    private lateinit var sessionManager1: SessionManager1
    companion object {
        const val DATA_POLDA = "DATA_POLDA"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_polda)
        sessionManager1 = SessionManager1(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Data Satuan Kerja Polda Kalsel"

        getListSatkerPolda()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1) {
            if (resultCode == 123) {
                val intent = Intent()
                val dataPersonel = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                intent.putExtra("ID_PERSONEL", dataPersonel)
                setResult(123, intent)
//                startActivity(intent)
                finish()
            }
        }
    }

    private fun getListSatkerPolda() {
        rl_pb.visible()
        NetworkConfig().getServPers().showSatkerPolda(
            "Bearer ${sessionManager1.fetchAuthToken()}"
        ).enqueue(object : Callback<ArrayList<SatKerResp>> {
            override fun onFailure(call: Call<ArrayList<SatKerResp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                rv_list_sat_polda.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<SatKerResp>>, response: Response<ArrayList<SatKerResp>>
            ) {
                rl_pb.gone()
                if (response.isSuccessful) {
                    callbackPolda = object : AdapterCallback<SatKerResp> {
                        override fun initComponent(
                            itemView: View, data: SatKerResp, itemIndex: Int
                        ) {
                            itemView.txt_edit_pendidikan.text = data.kesatuan
                        }

                        override fun onItemClicked(
                            itemView: View, data: SatKerResp, itemIndex: Int
                        ) {
                            val isListPolda = intent.extras?.getString(KatPersonelActivity.KAT_POLDA)
                            val isPickPersonel = intent.extras?.getBoolean(KatPersonelActivity.PICK_PERSONEL)
                            when {
                                isPickPersonel == true -> {
                                    val intent =
                                        Intent(this@PoldaActivity, ChoosePersonelActivity::class.java)
                                    intent.putExtra(PersonelActivity.IS_POLDA, data)
                                    startActivityForResult(intent, 1)
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                                }
                                isListPolda != null -> {
                                    val intent =
                                        Intent(this@PoldaActivity, PersonelActivity::class.java)
                                    intent.putExtra(PersonelActivity.IS_POLDA, data)
                                    startActivity(intent)
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                                }

                                else -> {
                                    val intent = Intent()
                                    intent.putExtra(DATA_POLDA, data)
                                    setResult(AddPersonelActivity.RES_POLDA, intent)
                                    finish()
                                }
                            }
                        }
                    }
                    response.body()?.let {
                        adapterPolda.adapterCallback(callbackPolda)
                            .filterable()
                            .isVerticalView()
                            .build(rv_list_sat_polda)
                            .setLayout(R.layout.layout_edit_1_text)
                            .addData(it)
                    }
                } else {
                    rl_no_data.visible()
                    rv_list_sat_polda.gone()
                }
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Satuan Kerja"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterPolda.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}

