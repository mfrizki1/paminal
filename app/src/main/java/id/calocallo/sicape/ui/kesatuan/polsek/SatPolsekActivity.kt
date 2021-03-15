package id.calocallo.sicape.ui.kesatuan.polsek

import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.response.UnitPolsekResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_polsek.*
import kotlinx.android.synthetic.main.activity_sat_polsek.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SatPolsekActivity : BaseActivity() {
    companion object{
        const val UNIT_POLSEK ="UNIT_POLSEK"
    }
    private var adapterPolsek = ReusableAdapter<UnitPolsekResp>(this)
    private lateinit var callbackPolsek : AdapterCallback<UnitPolsekResp>
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sat_polsek)
        setupActionBarWithBackButton(toolbar)
        val namaPolsek = intent.extras?.getString(PolsekActivity.NAMA_POLSEK)
        supportActionBar?.title = "Data Unit Kerja $namaPolsek"
        sessionManager1 = SessionManager1(this)
        getListUnitPolsek()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 321 && resultCode==123){
            val dataPers = data?.getParcelableExtra<AllPersonelModel>("ID_PERSONEL")
            val intent = Intent()
            intent.putExtra("ID_PERSONEL", dataPers)
            setResult(123, intent)
            finish()
        }
    }

    private fun getListUnitPolsek() {
        rl_pb.visible()
        NetworkDummy().getService().getUnitSekBjm().enqueue(object :
            Callback<ArrayList<UnitPolsekResp>> {
            override fun onFailure(call: Call<ArrayList<UnitPolsekResp>>, t: Throwable) {
                rl_no_data.visible()
                rl_pb.gone()
                rv_list_polsek.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<UnitPolsekResp>>,
                response: Response<ArrayList<UnitPolsekResp>>
            ) {
                rl_pb.gone()
                if(response.isSuccessful){
                    listUntiPolsek(response.body())
                }else{
                    rl_no_data.visible()
                    rv_list_polsek.gone()
                }
            }
        })
    }

    private fun listUntiPolsek(list: ArrayList<UnitPolsekResp>?) {
        callbackPolsek = object :AdapterCallback<UnitPolsekResp>{
            override fun initComponent(itemView: View, data: UnitPolsekResp, itemIndex: Int) {
                itemView.txt_1_clickable.text=data.nama_unit
            }

            override fun onItemClicked(itemView: View, data: UnitPolsekResp, itemIndex: Int) {
                if (intent.extras?.getBoolean(PersonelActivity.IS_POLSEK) == true) {
                    val intent = Intent(this@SatPolsekActivity, PersonelActivity::class.java)
                    intent.putExtra(PersonelActivity.IS_POLSEK, data)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else if (intent.extras?.get(KatPersonelActivity.PICK_PERSONEL_2) == true) {
                    val intent = Intent(this@SatPolsekActivity, ChoosePersonelActivity::class.java)
                    intent.putExtra(PersonelActivity.IS_POLSEK, data)
                    startActivityForResult(intent,321)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else {
                    val intent = Intent()
                    intent.putExtra(UNIT_POLSEK, data)
                    setResult(PolsekActivity.RES_UNIT_POLSEK, intent)
                    finish()
                }
            }
        }
        list?.let {
            adapterPolsek.adapterCallback(callbackPolsek)
                .filterable()
                .isVerticalView()
                .addData(it)
                .setLayout(R.layout.layout_1_text_clickable)
                .build(rv_list_sat_polsek)
        }
    }
}