package id.calocallo.sicape.ui.main.choose

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_choose_personel.*
import kotlinx.android.synthetic.main.item_choose_personel.*
import kotlinx.android.synthetic.main.item_choose_personel.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChoosePersonelActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var personelChooseAdapter: ReusableAdapter<PersonelModel>
    private lateinit var personelChooseCallback: AdapterCallback<PersonelModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_personel)
        sessionManager = SessionManager(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Personel"

        personelChooseAdapter = ReusableAdapter(this)

        personelChooseCallback = object : AdapterCallback<PersonelModel> {
            override fun initComponent(itemView: View, data: PersonelModel, itemIndex: Int) {
                itemView.txt_personel_nama_choose.text = data.nama
                itemView.txt_personel_pangkat_choose.text = "Pangkat ${data.pangkat}"
                itemView.txt_personel_nrp_choose.text = "NRP : ${data.nrp}"
                itemView.txt_personel_jabatan_choose.text = data.jabatan
                itemView.txt_personel_kesatuan_choose.text = data.satuan_kerja?.kesatuan
            }

            override fun onItemClicked(itemView: View, data: PersonelModel, itemIndex: Int) {
                itemView.img_choose_personel.visible()
                val intent = Intent()
                intent.putExtra("ID_PERSONEL",data)
                setResult(RESULT_OK, intent)
                finish()
            }

        }

        getPersonel()
    }

    private fun getPersonel() {
        rl_pb.visible()
        rv_list_choose_personel.gone()
        NetworkConfig().getService().showPersonel(
            "Bearer ${sessionManager.fetchAuthToken()}"
        ).enqueue(object : Callback<ArrayList<PersonelModel>> {
            override fun onFailure(call: Call<ArrayList<PersonelModel>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                Toast.makeText(this@ChoosePersonelActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                    .show()

            }

            override fun onResponse(
                call: Call<ArrayList<PersonelModel>>,
                response: Response<ArrayList<PersonelModel>>

            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    if (response.body()!!.isEmpty()) {
                        rl_no_data.visible()
                        rv_list_choose_personel.gone()
                    } else {
                        rv_list_choose_personel.visible()
                        personelChooseAdapter.adapterCallback(personelChooseCallback)
                            .isVerticalView()
                            .filterable()
                            .addData(response.body()!!)
                            .setLayout(R.layout.item_choose_personel)
                            .build(rv_list_choose_personel)
                    }
                } else {
                    Toast.makeText(this@ChoosePersonelActivity, R.string.error, Toast.LENGTH_SHORT)
                        .show()
                    rl_no_data.visible()
                    rl_pb.gone()
                    rv_list_choose_personel.gone()
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
                personelChooseAdapter.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}