package id.calocallo.sicape.ui.main.choose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.ui.gelar.AddGelarActivity
import id.calocallo.sicape.ui.main.choose.multiple.PersonelDetailsLookup
import id.calocallo.sicape.ui.main.choose.multiple.PersonelItemKeyProvider
import id.calocallo.sicape.ui.main.choose.multiple.PersonelMultipleAdapter
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_choose_personel.*
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
    companion object {
        const val MULTIPLE = "MULTIPLE"
        const val DATA_MULTIPLE = "DATA_MULTIPLE"
        const val REQ_MULTIPLE = "MULTIPLE"
    }

    private lateinit var sessionManager1: SessionManager1
    private lateinit var personelChooseAdapter: ReusableAdapter<PersonelMinResp>
    private lateinit var personelChooseCallback: AdapterCallback<PersonelMinResp>
    private lateinit var selevted : ArrayList<PersonelMinResp>

    private var selectedPersonel : MutableList<PersonelMinResp> = mutableListOf<PersonelMinResp>()
    private lateinit var adapterPersonelMultiple: PersonelMultipleAdapter
    private var tracker: SelectionTracker<PersonelMinResp>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_personel)
        sessionManager1 = SessionManager1(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Personel"
        Log.e("iniChoose", "iniChoosePersonel")
        personelChooseAdapter = ReusableAdapter(this)
        selevted = arrayListOf()

        val isPolda = intent.extras?.getParcelable<SatKerResp>(PersonelActivity.IS_POLDA)
        val isPolres = intent.extras?.getParcelable<SatKerResp>(PersonelActivity.IS_POLRES)
        val isPolsek = intent.extras?.getParcelable<SatKerResp>(PersonelActivity.IS_POLSEK)
        val isMultipleSelect = intent.getBooleanExtra(MULTIPLE, false)
        if (isMultipleSelect) {
            getPersonel()
            ll_button_choose.visible()
        } else {
            when {
                isPolda != null -> {
                    Log.e("isPolda", "$isPolda")
                    apiChoosePersonelBySatker(isPolda)
                }
                isPolres != null -> {
                    Log.e("isPOlres", "$isPolres")
                    apiChoosePersonelBySatker(isPolres)
                }
                isPolsek != null -> {
                    Log.e("isPolsek", "$isPolsek")
                    apiChoosePersonelBySatker(isPolsek)
                }
//            else -> initAPI()

            }
        }


        /*set ketTerlapor*/
/*//        val terlapor = intent.extras?.getString(ListKetTerlaporLhpActivity.KET_TERLAPOR)
//        if(terlapor == null) {
//        getPersonel()
//        }else{
//            getPersonelByTerlapor()
//        }*/
    }

    private fun apiChoosePersonelBySatker(satker: SatKerResp) {
        rl_pb.visible()
        NetworkConfig().getService().showPersonelBySatker(
            "Bearer ${sessionManager1.fetchAuthToken()}", satker.id.toString()
        ).enqueue(object : Callback<ArrayList<PersonelMinResp>> {
            override fun onFailure(call: Call<ArrayList<PersonelMinResp>>, t: Throwable) {
                rl_no_data.visible()
                rl_pb.gone()
                rv_list_choose_personel.gone()
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
                        rv_list_choose_personel.gone()
                    } else {
                        rv_list_choose_personel.visible()
                        listPersonelBySatker(response.body())
                    }
                } else {
                    rl_no_data.visible()
                    rl_pb.gone()
                    rv_list_choose_personel.gone()
                }
            }
        })
    }

    private fun listPersonelBySatker(list: ArrayList<PersonelMinResp>?) {
        personelChooseCallback = object : AdapterCallback<PersonelMinResp> {
            override fun initComponent(itemView: View, data: PersonelMinResp, itemIndex: Int) {
                itemView.txt_personel_nama_choose.text = data.nama
                itemView.txt_personel_pangkat_choose.text =
                    "Pangkat : ${data.pangkat.toString().toUpperCase()}"
                itemView.txt_personel_nrp_choose.text = "NRP : ${data.nrp}"
                itemView.txt_personel_jabatan_choose.text = data.jabatan
                itemView.txt_personel_kesatuan_choose.text = data.satuan_kerja?.kesatuan
            }

            override fun onItemClicked(itemView: View, data: PersonelMinResp, itemIndex: Int) {
                itemView.img_choose_personel.visible()
                val intent = Intent()
                intent.putExtra("ID_PERSONEL", data)
                setResult(123, intent)
//                startActivity(intent)
                finish()
            }
        }
        list?.let {
            personelChooseAdapter.adapterCallback(personelChooseCallback)
                .isVerticalView()
                .filterable()
                .addData(it)
                .setLayout(R.layout.item_choose_personel)
                .build(rv_list_choose_personel)
        }
    }

    private fun getPersonelByTerlapor() {
        rl_pb.visible()
        rv_list_choose_personel.gone()
    }

    private fun getPersonel() {
        rl_pb.visible()
        rv_list_choose_personel.gone()
        NetworkConfig().getService().showPersonel(
            "Bearer ${sessionManager1.fetchAuthToken()}"
        ).enqueue(object : Callback<ArrayList<PersonelMinResp>> {
            override fun onFailure(call: Call<ArrayList<PersonelMinResp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                Toast.makeText(this@ChoosePersonelActivity, R.string.error_conn, Toast.LENGTH_SHORT)
                    .show()

            }

            override fun onResponse(
                call: Call<ArrayList<PersonelMinResp>>,
                response: Response<ArrayList<PersonelMinResp>>

            ) {
                if (response.isSuccessful) {
                    rl_pb.gone()
                    if (response.body()!!.isEmpty()) {
                        rl_no_data.visible()
                        rv_list_choose_personel.gone()
                    } else {
                        rv_list_choose_personel.visible()
                        rvMultipleSelect(response.body())
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

    private fun rvMultipleSelect(list: ArrayList<PersonelMinResp>?) {
        var selection = mutableListOf<PersonelMinResp>()
        rv_list_choose_personel.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterPersonelMultiple = list?.let { PersonelMultipleAdapter(this, it) }!!
        rv_list_choose_personel.adapter = adapterPersonelMultiple

        tracker = SelectionTracker.Builder<PersonelMinResp>(
            "personelSelection",
            rv_list_choose_personel,
            PersonelItemKeyProvider(adapterPersonelMultiple),
            PersonelDetailsLookup(rv_list_choose_personel),
            StorageStrategy.createParcelableStorage(PersonelMinResp::class.java)
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        adapterPersonelMultiple.tracker = tracker
        tracker?.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                    tracker?.let {
                        selectedPersonel = it.selection.toMutableList()
//                        selection = it.selection.toMutableList()
//                        selevted = it.selection.toMutableList()
                        Log.e("personelMultiple", "$selectedPersonel")
//                        Log.e("personelMultiple", "$selection")
//                        Log.e("selevted", "$selevted")
                    }
                }
            }
        )

        btn_chose_personel.setOnClickListener {
            val intent = Intent().apply {
                this.putExtra(DATA_MULTIPLE, selectedPersonel as ArrayList<AllPersonelModel>)
//                this.putExtra(DATA_MULTIPLE, ArrayList(selection))
            }
            setResult(AddGelarActivity.RES_PESERTA, intent)
            finish()
        }
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
                adapterPersonelMultiple.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}
