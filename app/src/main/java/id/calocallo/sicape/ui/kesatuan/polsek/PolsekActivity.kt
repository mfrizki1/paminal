package id.calocallo.sicape.ui.kesatuan.polsek

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.network.response.UnitPolsekResp
import id.calocallo.sicape.ui.main.addpersonal.AddPersonelActivity
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_polsek.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PolsekActivity : BaseActivity() {
    companion object {
        const val NAMA_POLRES = "NAMA_POLRES"
        const val NAMA_POLSEK = "NAMA_POLSEK"
        const val REQ_UNIT_POLSEK = 12
        const val RES_UNIT_POLSEK = 11
        const val DATA_POLSEK = "DATA_POLSEK"
        const val REQ_PICK_POLSEK = 241

    }

    private var adapterPolsek = ReusableAdapter<SatKerResp>(this)
    private lateinit var callbackPolsek: AdapterCallback<SatKerResp>
    private lateinit var sessionManager1: SessionManager1
    private var namaPolsek = "ta"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_polsek)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        supportActionBar?.title = "Data Kepolisian Sektor"

        val namaPolres = intent.extras?.getString(NAMA_POLRES)
        Log.e("polres onPolsek", "$namaPolres")
        getListPolsek()
    }

    private fun getListPolsek() {
        rl_pb.visible()
        NetworkConfig().getService().showPolsek("Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(object : Callback<ArrayList<SatKerResp>> {
                override fun onFailure(call: Call<ArrayList<SatKerResp>>, t: Throwable) {
                    rl_no_data.visible()
                    rl_pb.gone()
                    rv_list_polsek.gone()
                }

                override fun onResponse(
                    call: Call<ArrayList<SatKerResp>>,
                    response: Response<ArrayList<SatKerResp>>
                ) {
                    rl_pb.gone()
                    if (response.isSuccessful) {
                        listPolsek(response.body())
                    } else {
                        rl_no_data.visible()
                        rv_list_polsek.gone()
                    }
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RES_UNIT_POLSEK) {
            if (requestCode == REQ_UNIT_POLSEK) {
                val unitPolsek =
                    data?.getParcelableExtra<UnitPolsekResp>(SatPolsekActivity.UNIT_POLSEK)
                Log.e("unitPolsek", "$unitPolsek")
                val bundle = Bundle()
                bundle.putParcelable(SatPolsekActivity.UNIT_POLSEK, unitPolsek)
                bundle.putString(NAMA_POLSEK, namaPolsek)
                val intent = Intent()
                intent.putExtras(bundle)
                setResult(AddPersonelActivity.RES_POLSEK, intent)
                finish()
            }
        }
        if (requestCode == REQ_PICK_POLSEK && resultCode == 123) {
            val dataPers = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
            val intent = Intent()
            intent.putExtra("ID_PERSONEL", dataPers)
            setResult(123, intent)
            finish()
        }
    }

    private fun listPolsek(list: ArrayList<SatKerResp>?) {
        callbackPolsek = object : AdapterCallback<SatKerResp> {
            override fun initComponent(itemView: View, data: SatKerResp, itemIndex: Int) {
                itemView.txt_edit_pendidikan.text = data.kesatuan
            }

            override fun onItemClicked(itemView: View, data: SatKerResp, itemIndex: Int) {
                val isListPolsek = intent.extras?.getString(KatPersonelActivity.KAT_POLSEK)
                val isPickPolsek = intent.extras?.getBoolean(KatPersonelActivity.PICK_PERSONEL_2)

                when {
                    isPickPolsek == true -> {
                        val intent = Intent(this@PolsekActivity, ChoosePersonelActivity::class.java)
                        intent.putExtra(PersonelActivity.IS_POLSEK, data)
                        startActivityForResult(intent, REQ_PICK_POLSEK)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    isListPolsek != null -> {
                        val intent =
                            Intent(this@PolsekActivity, PersonelActivity::class.java)
                        intent.putExtra(PersonelActivity.IS_POLRES, data)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    else -> {
                        val intent = Intent()
                        intent.putExtra(SatPolsekActivity.UNIT_POLSEK, data)
                        setResult(AddPersonelActivity.RES_POLSEK, intent)
                        finish()
                    }
                }

                /*when {
                    intent.extras?.getBoolean(PersonelActivity.IS_POLSEK) == true -> {
                        namaPolsek = data.nama_polsek.toString()
                        val intent = Intent(this@PolsekActivity, SatPolsekActivity::class.java)
                        intent.putExtra(PersonelActivity.IS_POLSEK, true)
                        intent.putExtra(NAMA_POLSEK, data.nama_polsek)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    intent.extras?.getBoolean(KatPersonelActivity.PICK_PERSONEL_2) == true -> {
                        namaPolsek = data.nama_polsek.toString()
                        val intent = Intent(this@PolsekActivity, SatPolsekActivity::class.java)
                        intent.putExtra(KatPersonelActivity.PICK_PERSONEL_2, true)
                        intent.putExtra(NAMA_POLSEK, data.nama_polsek)
                        startActivityForResult(intent, 342)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    else -> {
                        namaPolsek = data.nama_polsek.toString()
                        val intent = Intent(this@PolsekActivity, SatPolsekActivity::class.java)
                        intent.putExtra(NAMA_POLSEK, data.nama_polsek)
                        startActivityForResult(intent, REQ_UNIT_POLSEK)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                }*/
            }
        }
        list?.let {
            adapterPolsek.adapterCallback(callbackPolsek)
                .isVerticalView()
                .filterable()
                .build(rv_list_polsek)
                .setLayout(R.layout.layout_edit_1_text)
                .addData(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Polsek"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterPolsek.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}