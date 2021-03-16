package id.calocallo.sicape.ui.main.personel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.ui.main.addpersonal.AddPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.ui.main.addpersonal.relasi.AddRelasiActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_personel.*
import kotlinx.android.synthetic.main.item_personel.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.toolbar
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonelActivity : BaseActivity() {
    companion object {
        const val IS_POLDA = "IS_POLDA"
        const val IS_POLRES = "IS_POLRES"
        const val IS_POLSEK = "IS_POLSEK"
    }

    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapter: ReusableAdapter<PersonelMinResp>
    private var adapterMin = ReusableAdapter<PersonelMinResp>(this)
    private lateinit var adapterCallback: AdapterCallback<PersonelMinResp>
    private lateinit var adapterMinCallback: AdapterCallback<PersonelMinResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personel)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Personel"
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        val isPolda = intent.extras?.getParcelable<SatKerResp>(IS_POLDA)
        val isPolres = intent.extras?.getParcelable<SatKerResp>(IS_POLRES)
        val isPolsek = intent.extras?.getParcelable<SatKerResp>(IS_POLSEK)
        val fromAddPersonel = intent.extras?.getParcelable<SatKerResp>(AddRelasiActivity.ID_SATKER)
        when {
            isPolda != null -> {
                Log.e("isPolda", "$isPolda")
                supportActionBar?.title = "Personel ${isPolda.kesatuan}"
                apiPersonelBySatker(isPolda)
            }
            isPolres != null -> {
                Log.e("isPOlres", "$isPolres")
                supportActionBar?.title = "Personel ${isPolres.kesatuan}"
                apiPersonelBySatker(isPolres)
            }
            isPolsek != null -> {
                Log.e("isPolsek", "$isPolsek")
                supportActionBar?.title = "Personel ${isPolsek.kesatuan}"
                apiPersonelBySatker(isPolsek)
            }
            fromAddPersonel != null -> {
                supportActionBar?.title = "Personel ${fromAddPersonel.kesatuan}"
                apiPersonelBySatker(fromAddPersonel)

            }
//            else -> initAPI()

        }
        btn_personel_add.setOnClickListener {
            startActivity(Intent(this, AddPersonelActivity::class.java))
        }
    }

    private fun apiPersonelBySatker(satker: SatKerResp?) {
        rl_pb.visible()
        NetworkConfig().getServPers().showPersonelBySatker(
            "Bearer ${sessionManager1.fetchAuthToken()}", satker?.id.toString()
        ).enqueue(object : Callback<ArrayList<PersonelMinResp>> {
            override fun onFailure(call: Call<ArrayList<PersonelMinResp>>, t: Throwable) {
               toast("$t")
                rl_no_data.visible()
                rl_pb.gone()
                rv_personel.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<PersonelMinResp>>,
                response: Response<ArrayList<PersonelMinResp>>
            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    if (response.body()?.isEmpty()!!) {
                        rl_no_data.visible()
                        rl_pb.gone()
                        rv_personel.gone()
                    } else {
                        listPersonelBySatker(response.body())
                    }
                } else {
                    toast(R.string.error)
                    rl_no_data.visible()
                    rl_pb.gone()
                    rv_personel.gone()
                }
            }
        })
    }

    private fun listPersonelBySatker(list: ArrayList<PersonelMinResp>?) {
        adapterMinCallback = object : AdapterCallback<PersonelMinResp> {
            override fun initComponent(itemView: View, data: PersonelMinResp, itemIndex: Int) {
                itemView.txt_personel_kesatuan.text = data.satuan_kerja?.kesatuan
                itemView.txt_personel_nama.text = data.nama
                itemView.txt_personel_nrp.text = data.nrp
                itemView.txt_personel_pangkat.text = data.pangkat.toString().toUpperCase()
                itemView.txt_personel_jabatan.text = data.jabatan.toString().toUpperCase()
            }

            override fun onItemClicked(itemView: View, data: PersonelMinResp, itemIndex: Int) {
                val bundle = Bundle()
                val intent = Intent(this@PersonelActivity, DetailPersonelActivity::class.java)
                bundle.putParcelable("PERSONEL", data)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }
        list?.let {
            adapterMin.filterable()
                .adapterCallback(adapterMinCallback)
                .setLayout(R.layout.item_personel)
                .isVerticalView()
                .addData(it)
                .build(rv_personel)
        }
    }

    private fun initAPI() {
        rl_pb.visible()
        rv_personel.gone()
        NetworkConfig().getServPers()
            .showPersonel(tokenBearer = "Bearer ${sessionManager1.fetchAuthToken()}")
            .enqueue(object : Callback<ArrayList<PersonelMinResp>> {
                override fun onFailure(call: Call<ArrayList<PersonelMinResp>>, t: Throwable) {
                    rl_no_data.visible()
                    rl_pb.gone()
                    Toast.makeText(this@PersonelActivity, "Gagal Koneksi", Toast.LENGTH_SHORT)
                        .show()

                }

                override fun onResponse(
                    call: Call<ArrayList<PersonelMinResp>>,
                    response: Response<ArrayList<PersonelMinResp>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        rv_personel.gone()
                        val personelResp = response.body()
                        if (personelResp != null) {
                            rv_personel.visible()
                            adapter.filterable()
                                .adapterCallback(adapterCallback)
                                .setLayout(R.layout.item_personel)
                                .isVerticalView()
                                .addData(personelResp)
                                .build(rv_personel)
                        } else {
                            rl_no_data.visible()
                        }

                    } else {
                        Toast.makeText(this@PersonelActivity, "Error", Toast.LENGTH_SHORT).show()
                        rl_no_data.visible()
                        rv_personel.gone()
                    }

                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Nama / NRP"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterMin.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onResume() {
        super.onResume()
//        initAPI()
        val isPolda = intent.extras?.getParcelable<SatKerResp>(IS_POLDA)
        val isPolres = intent.extras?.getParcelable<SatKerResp>(IS_POLRES)
        val isPolsek = intent.extras?.getParcelable<SatKerResp>(IS_POLSEK)
        val fromAddPersonel = intent.extras?.getParcelable<SatKerResp>(AddRelasiActivity.ID_SATKER)
        when {
            isPolda != null -> {
                Log.e("isPolda", "$isPolda")
                supportActionBar?.title = "Personel ${isPolda.kesatuan}"
                apiPersonelBySatker(isPolda)
            }
            isPolres != null -> {
                Log.e("isPOlres", "$isPolres")
                supportActionBar?.title = "Personel ${isPolres.kesatuan}"
                apiPersonelBySatker(isPolres)
            }
            isPolsek != null -> {
                Log.e("isPolsek", "$isPolsek")
                supportActionBar?.title = "Personel ${isPolsek.kesatuan}"
                apiPersonelBySatker(isPolsek)
            }
            fromAddPersonel != null -> {
                supportActionBar?.title = "Personel ${fromAddPersonel.kesatuan}"
                apiPersonelBySatker(fromAddPersonel)
            }

        }

    }
}