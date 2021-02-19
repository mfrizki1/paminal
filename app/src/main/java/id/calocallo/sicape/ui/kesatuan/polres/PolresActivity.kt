package id.calocallo.sicape.ui.kesatuan.polres

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.PersonelMinResp
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.ui.main.addpersonal.AddPersonelActivity
import id.calocallo.sicape.ui.main.addpersonal.AddPersonelActivity.Companion.RES_POLRES
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_polres.*
import kotlinx.android.synthetic.main.layout_edit_1_text.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PolresActivity : BaseActivity() {
    private var adapterPolres = ReusableAdapter<SatKerResp>(this)
    private lateinit var callbackPolres : AdapterCallback<SatKerResp>
    private lateinit var sessionManager1: SessionManager1
    companion object{
        const val IS_POLSEK = "IS_POLSEK"
        const val REQ_POLRES_SAT = 198
        const val RES_POLRES_SAT = 189
        const val RES_POLRES = 214
        const val DATA_POLRES = "DATA_POLRES"
        const val REQ_TO_ADD = 213
        const val REQ_PICK_POLRES = 1234
    }
    private var bundle = Bundle()
    private var namaPolres = "t"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_polres)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        supportActionBar?.title = "Data Kepolisian Resort"

        getListPolres()
        Log.e("polresACtivity", "katPOLSEK : ${intent.extras?.getString(KatPersonelActivity.KAT_POLSEK)},\n" +
                "KA_POLRES ${intent.extras?.getString(KatPersonelActivity.KAT_POLRES)},\n" +
                "IS_POLSEK ${intent.extras?.getString(IS_POLSEK)},\n")

    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQ_POLRES_SAT){
            if(resultCode == RES_POLRES_SAT){
                val satPolres = data?.getParcelableExtra<SatPolresResp>(SatPolresActivity.DATA_POLRES_SAT)
                Log.e("satPolres", "$satPolres")

                val intent = Intent()
                bundle.putParcelable(SatPolresActivity.DATA_POLRES_SAT, satPolres)
                bundle.putString(DATA_POLRES,namaPolres)

                intent.putExtras(bundle)
                setResult(AddPersonelActivity.RES_POLRES, intent)
                finish()
            }
        }
        if(requestCode == REQ_PICK_POLRES && resultCode == 1){
            val dataPers = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
            val intent = Intent()
            intent.putExtra("ID_PERSONEL", dataPers)
            setResult(123, intent)
            finish()
        }
        if(requestCode == REQ_PICK_POLSEK && resultCode == 123){
            val dataPers = data?.getParcelableExtra<PersonelModel>("ID_PERSONEL")
            val intent = Intent()
            intent.putExtra("ID_PERSONEL", dataPers)
            setResult(123, intent)
            finish()
        }
        if(requestCode == REQ_TO_ADD){
            val dataPolsek = data?.extras
            Log.e("polsek","${dataPolsek?.getParcelable<UnitPolsekResp>(SatPolsekActivity.UNIT_POLSEK)}\n" +
                    "${dataPolsek?.getString(PolsekActivity.NAMA_POLSEK)}")
            val intent = Intent()
            dataPolsek?.let { intent.putExtras(it) }
            setResult(AddPersonelActivity.RES_POLSEK ,intent)
            finish()
        }
    }*/

    private fun getListPolres() {
        rl_pb.visible()
        NetworkConfig().getService().showPolres("Bearer ${sessionManager1.fetchAuthToken()}").
        enqueue(object : Callback<ArrayList<SatKerResp>> {
            override fun onFailure(call: Call<ArrayList<SatKerResp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                rv_list_polres.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<SatKerResp>>,
                response: Response<ArrayList<SatKerResp>>
            ) {
                rl_pb.gone()
                if(response.isSuccessful){
                    callbackPolres = object :AdapterCallback<SatKerResp>{
                        override fun initComponent(itemView: View, data: SatKerResp, itemIndex: Int) {
                            itemView.txt_edit_pendidikan.text = data.kesatuan
                        }

                        override fun onItemClicked(itemView: View, data: SatKerResp, itemIndex: Int) {
                            val isPickPersonel = intent.extras?.getBoolean(KatPersonelActivity.PICK_PERSONEL)
                            val isListPolres = intent.extras?.getString(KatPersonelActivity.KAT_POLRES)
                            when{
                                isPickPersonel == true->{
                                    val intent = Intent(this@PolresActivity, ChoosePersonelActivity::class.java)
                                    intent.putExtra(PersonelActivity.IS_POLRES, data)
                                    startActivityForResult(intent, REQ_PICK_POLRES)
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                                }
                                isListPolres != null ->{
                                    val intent =
                                        Intent(this@PolresActivity, PersonelActivity::class.java)
                                    intent.putExtra(PersonelActivity.IS_POLRES, data)
                                    startActivity(intent)
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                                }
                                else->{
                                    val intent = Intent()
                                    intent.putExtra(SatPolresActivity.DATA_POLRES_SAT, data)
//                            bundle.putString(DATA_POLRES,namaPolres)

//                            intent.putExtras(bundle)
                                    setResult(AddPersonelActivity.RES_POLRES, intent)
                                    finish()
                                }
                            }
                            if(isPickPersonel == true){

                            }else {


                            }
                            //clickedSingle(data)
                        }
                    }
                    response.body()?.let {
                        adapterPolres.adapterCallback(callbackPolres)
                            .filterable()
                            .isVerticalView()
                            .build(rv_list_polres)
                            .setLayout(R.layout.layout_edit_1_text)
                            .addData(it)
                    }
                }else{
                    rl_no_data.visible()
                    rv_list_polres.gone()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQ_PICK_POLRES->{
                if(resultCode == 123){
                    val data = data?.getParcelableExtra<PersonelMinResp>("ID_PERSONEL")
                    val intent = Intent().apply {
                        this.putExtra("ID_PERSONEL", data)
                    }
                    setResult(123, intent)
                    finish()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_bar, menu)
        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.queryHint = "Cari Polres"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterPolres.filter.filter(newText)
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
    private fun clickedSingle(data: SatKerResp) {
        /*
        if(intent.extras?.getString(KatPersonelActivity.KAT_POLSEK)?.isNotEmpty()!!){
            val intent = Intent(this@PolresActivity, PolsekActivity::class.java)
            intent.putExtra(PersonelActivity.IS_POLSEK,true)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }else{
            val intent = Intent(this@PolresActivity, PolsekActivity::class.java)
            intent.putExtra(PolsekActivity.NAMA_POLRES, data.nama_polres)
            startActivityForResult(intent, REQ_TO_ADD)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        if(intent.extras?.getString(KatPersonelActivity.KAT_POLRES) == KatPersonelActivity.KAT_POLRES){
            namaPolres = data.nama_polres.toString()
            val intent = Intent(this@PolresActivity, SatPolresActivity::class.java)
            intent.putExtra(SatPolresActivity.NAMA_POLRES, data.nama_polres)
            intent.putExtra(PersonelActivity.IS_POLRES, true)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        if(intent.extras?.getString(IS_POLSEK).isNullOrEmpty()){
            namaPolres = data.nama_polres.toString()
            val intent = Intent(this@PolresActivity, SatPolresActivity::class.java)
            intent.putExtra(SatPolresActivity.NAMA_POLRES, data.nama_polres)
            startActivityForResult(intent, REQ_POLRES_SAT)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

         */
        /*when {
            intent.extras?.getString(KatPersonelActivity.KAT_POLSEK) != null -> {
                val intent = Intent(this@PolresActivity, PolsekActivity::class.java)
                intent.putExtra(PersonelActivity.IS_POLSEK,true)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            intent.extras?.getString(KatPersonelActivity.KAT_POLRES) == KatPersonelActivity.KAT_POLRES -> {
                namaPolres = data.nama_polres.toString()
                val intent = Intent(this@PolresActivity, SatPolresActivity::class.java)
                intent.putExtra(SatPolresActivity.NAMA_POLRES, data.nama_polres)
                intent.putExtra(PersonelActivity.IS_POLRES, true)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }

            intent.extras?.getBoolean(KatPersonelActivity.PICK_PERSONEL_2) == true->{
                namaPolres = data.nama_polres.toString()
                val intent = Intent(this@PolresActivity, PolsekActivity::class.java)
                intent.putExtra(KatPersonelActivity.PICK_PERSONEL_2, true)
                intent.putExtra(SatPolresActivity.NAMA_POLRES, data.nama_polres)
                startActivityForResult(intent, REQ_PICK_POLSEK)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            intent.extras?.getBoolean(KatPersonelActivity.PICK_PERSONEL) == true->{
                namaPolres = data.nama_polres.toString()
                val intent = Intent(this@PolresActivity, SatPolresActivity::class.java)
                intent.putExtra(SatPolresActivity.NAMA_POLRES, data.nama_polres)
                intent.putExtra(KatPersonelActivity.PICK_PERSONEL, true)
                startActivityForResult(intent, REQ_PICK_POLRES)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
            intent.extras?.getString(IS_POLSEK).isNullOrEmpty() -> {
                namaPolres = data.nama_polres.toString()
                val intent = Intent(this@PolresActivity, SatPolresActivity::class.java)
                intent.putExtra(SatPolresActivity.NAMA_POLRES, data.nama_polres)
                startActivityForResult(intent, REQ_POLRES_SAT)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }


           else -> {
                val intent = Intent(this@PolresActivity, PolsekActivity::class.java)
                intent.putExtra(PolsekActivity.NAMA_POLRES, data.nama_polres)
                startActivityForResult(intent, REQ_TO_ADD)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }*/
    }

}