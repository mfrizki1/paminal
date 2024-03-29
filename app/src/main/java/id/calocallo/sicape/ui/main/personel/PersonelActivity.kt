package id.calocallo.sicape.ui.main.personel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import android.view.View
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.ui.main.addpersonal.AddPersonelActivity
import id.calocallo.sicape.utils.Constants
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
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
    private lateinit var sessionManager: SessionManager
    private lateinit var adapter: ReusableAdapter<PersonelModel>
    private var list = ArrayList<PersonelModel>()
    private lateinit var adapterCallback: AdapterCallback<PersonelModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personel)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Personel"
        setupActionBarWithBackButton(toolbar)
        sessionManager = SessionManager(this)

        initAPI()

        //set adapter
        adapter = ReusableAdapter(this)


        //create adapter callback
        adapterCallback = object : AdapterCallback<PersonelModel> {
            override fun initComponent(itemView: View, data: PersonelModel, itemIndex: Int) {
                itemView.txt_personel_nama.text = data.nama
                itemView.txt_personel_nrp.text = data.nrp
                itemView.txt_personel_pangkat.text = data.pangkat
                itemView.txt_personel_kesatuan.text = data.kesatuan
                itemView.txt_personel_jabatan.text = data.jabatan
            }

            override fun onItemClicked(itemView: View, data: PersonelModel, itemIndex: Int) {
//                Log.e("klik", data.nama.toString())
                val bundle = Bundle()
                val intent = Intent(this@PersonelActivity,      DetailPersonelActivity::class.java)
                bundle.putParcelable("PERSONEL", data)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }



        btn_personel_add.setOnClickListener {
            startActivity(Intent(this, AddPersonelActivity::class.java))
        }
    }

    private fun initAPI() {
        rl_pb.visible()
        NetworkConfig().getService()
            .showPersonel(tokenBearer = "Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<ArrayList<PersonelModel>> {
                override fun onFailure(call: Call<ArrayList<PersonelModel>>, t: Throwable) {
                    rl_no_data.visible()
                    Toast.makeText(this@PersonelActivity, "Gagal Koneksi", Toast.LENGTH_SHORT)
                        .show()

                }

                override fun onResponse(
                    call: Call<ArrayList<PersonelModel>>,
                    response: Response<ArrayList<PersonelModel>>
                ) {
                    if (response.isSuccessful) {
                        rl_pb.gone()
                        val personelResp = response.body()
                        if (personelResp != null) {
                            adapter.filterable()
                                .adapterCallback(adapterCallback)
                                .setLayout(R.layout.item_personel)
                                .isVerticalView()
                                .addData(personelResp)
                                .build(rv_personel)
                        }else{
                            rl_no_data.visible()
                        }

                    } else {
                        Toast.makeText(this@PersonelActivity, "Error", Toast.LENGTH_SHORT).show()

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
                adapter.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}