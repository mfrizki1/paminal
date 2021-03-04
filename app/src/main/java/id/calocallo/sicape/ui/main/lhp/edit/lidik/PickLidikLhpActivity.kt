package id.calocallo.sicape.ui.main.lhp.edit.lidik

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.ListLidik
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.KeluargaMinResp
import id.calocallo.sicape.network.response.LhpMinResp
import id.calocallo.sicape.network.response.PersonelPenyelidikResp
import id.calocallo.sicape.ui.main.lhp.EditLhpActivity
import id.calocallo.sicape.ui.main.lhp.add.AddPersonelLidikActivity
import id.calocallo.sicape.ui.main.lhp.edit.lidik.AddSingleLidikLhpActivity.Companion.ADD_LIDIK
import id.calocallo.sicape.ui.main.lhp.edit.lidik.EditLidikLhpActivity.Companion.EDIT_LIDIK
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_lidik_lhp.*
import kotlinx.android.synthetic.main.item_lidik.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickLidikLhpActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var adapterLidik: ReusableAdapter<PersonelPenyelidikResp>
    private lateinit var callbackLidik: AdapterCallback<PersonelPenyelidikResp>
    private lateinit var detailLhp: LhpMinResp
    private var listLidik = arrayListOf<ListLidik>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_lidik_lhp)
        sessionManager1 = SessionManager1(this)
        adapterLidik = ReusableAdapter(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data Personel Penyelidik"
        detailLhp = intent.extras?.getParcelable<LhpMinResp>(EditLhpActivity.EDIT_LHP)!!
//        rv_list_lidik

        btn_add_single_lidik.setOnClickListener {
            val intent = Intent(this, AddPersonelLidikActivity::class.java)
            intent.putExtra(ADD_LIDIK, ADD_LIDIK)
            intent.putExtra(DATA_LHP, detailLhp)
            startActivity(intent)
        }
        apiListLidik(detailLhp)
//        getListLidik(detailLhp)
    }

    private fun apiListLidik(detailLhp: LhpMinResp) {
     rl_pb.visible()
        NetworkConfig().getServLhp()
            .getAllPersLidik("Bearer ${sessionManager1.fetchAuthToken()}", detailLhp.id).enqueue(
                object :
                    Callback<ArrayList<PersonelPenyelidikResp>> {
                    override fun onResponse(
                        call: Call<ArrayList<PersonelPenyelidikResp>>,
                        response: Response<ArrayList<PersonelPenyelidikResp>>
                    ) {
                        if (response.isSuccessful) {
                            rl_pb.gone()
                            getListLidik(response.body())
                        } else {
                            rl_pb.gone()
                            rl_no_data.visible()
                            rv_list_lidik.gone()
                        }

                    }

                    override fun onFailure(
                        call: Call<ArrayList<PersonelPenyelidikResp>>, t: Throwable
                    ) {
                        Toast.makeText(this@PickLidikLhpActivity, "$t", Toast.LENGTH_SHORT).show()
                        rl_pb.gone()
                        rl_no_data.visible()
                        rv_list_lidik.gone()
                    }
                })
    }

    override fun onResume() {
        super.onResume()
        apiListLidik(detailLhp)
    }

    private fun getListLidik(listLidikPers: ArrayList<PersonelPenyelidikResp>?) {
        callbackLidik = object : AdapterCallback<PersonelPenyelidikResp> {
            @SuppressLint("SetTextI18n")
            override fun initComponent(
                itemView: View,
                data: PersonelPenyelidikResp,
                itemIndex: Int
            ) {
                when (data.is_ketua) {
                    1 -> itemView.ttx_status_lidik.text = "Ketua Tim"
                    0 -> itemView.ttx_status_lidik.text = "Anggota"
                }
                itemView.txt_nama_lidik.text = data.personel?.nama
                itemView.txt_pangkat_nrp_lidik.text =
                    "Pangkat ${data.personel?.pangkat.toString().toUpperCase()} " +
                            "NRP: ${data.personel?.nrp}"
                itemView.txt_jabatan_lidik.text = "Jabatan ${data.personel?.jabatan}"
                itemView.txt_kesatuan_lidik.text =
                    "Kesatuan ${data.personel?.satuan_kerja?.kesatuan.toString().toUpperCase()}"
            }

            override fun onItemClicked(
                itemView: View,
                data: PersonelPenyelidikResp,
                itemIndex: Int
            ) {
                val intent = Intent(this@PickLidikLhpActivity, EditLidikLhpActivity::class.java)
                intent.putExtra(EDIT_LIDIK, data)
                startActivity(intent)
            }
        }
        listLidikPers?.let { sortSttsLidik(it) }?.let {
            adapterLidik.adapterCallback(callbackLidik)
                .filterable()
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.item_lidik)
                .build(rv_list_lidik)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Nama Penyelidik"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterLidik.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private val roles: HashMap<String, Int> = hashMapOf(
        "1" to 0,
        "0" to 1
    )

   private fun sortSttsLidik(lidik: ArrayList<PersonelPenyelidikResp>): ArrayList<PersonelPenyelidikResp> {
        val comparator = Comparator { o1: PersonelPenyelidikResp, o2: PersonelPenyelidikResp ->
            return@Comparator roles[o1.is_ketua.toString()]!! - roles[o2.is_ketua.toString()]!!
        }
        val copy = arrayListOf<PersonelPenyelidikResp>().apply { addAll(lidik) }
        copy.sortWith(comparator)
        return copy
    }
    companion object{
        const val DATA_LHP="DATA_LHP"
    }

}
