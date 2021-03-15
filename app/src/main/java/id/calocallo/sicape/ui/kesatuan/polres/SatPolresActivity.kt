package id.calocallo.sicape.ui.kesatuan.polres

import android.content.Intent
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AllPersonelModel
import id.calocallo.sicape.network.NetworkDummy
import id.calocallo.sicape.network.response.SatPolresResp
import id.calocallo.sicape.ui.main.choose.ChoosePersonelActivity
import id.calocallo.sicape.ui.main.personel.KatPersonelActivity
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sat_polres.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_progress_dialog.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import kotlinx.android.synthetic.main.view_no_data.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SatPolresActivity : BaseActivity() {
    private var adapterSatPolres = ReusableAdapter<SatPolresResp>(this)
    private lateinit var callbackSatPolres: AdapterCallback<SatPolresResp>
    private lateinit var sessionManager1: SessionManager1

    companion object {
        const val NAMA_POLRES = "NAMA_POLRES"
        const val DATA_POLRES_SAT = "DATA_POLRES_SAT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sat_polres)
        setupActionBarWithBackButton(toolbar)
        sessionManager1 = SessionManager1(this)
        val namaPolres = intent.extras?.getString(NAMA_POLRES)
        supportActionBar?.title = "Data Satuan Kerja $namaPolres"

        getListSatPolres()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 453 && resultCode== 123){
            val dataChoosePers = data?.getParcelableExtra<AllPersonelModel>("ID_PERSONEL")
            val intent = Intent()
            intent.putExtra("ID_PERSONEL", dataChoosePers)
            setResult(1, intent)
            finish()
        }
    }

    private fun getListSatPolres() {
        rl_pb.visible()
        NetworkDummy().getService().getSatResBjm().enqueue(object :
            Callback<ArrayList<SatPolresResp>> {
            override fun onFailure(call: Call<ArrayList<SatPolresResp>>, t: Throwable) {
                rl_pb.gone()
                rl_no_data.visible()
                rv_list_sat_polres.gone()
            }

            override fun onResponse(
                call: Call<ArrayList<SatPolresResp>>,
                response: Response<ArrayList<SatPolresResp>>
            ) {
                rl_pb.gone()
                if (response.isSuccessful) {
                    listSatPolres(response.body())
                } else {
                    rl_no_data.visible()
                    rv_list_sat_polres.gone()
                }
            }
        })
    }

    private fun listSatPolres(list: ArrayList<SatPolresResp>?) {
        callbackSatPolres = object : AdapterCallback<SatPolresResp> {
            override fun initComponent(itemView: View, data: SatPolresResp, itemIndex: Int) {
                itemView.txt_1_clickable.text = data.nama_satuan_res
            }

            override fun onItemClicked(itemView: View, data: SatPolresResp, itemIndex: Int) {
                when {
                    intent.extras?.getBoolean(PersonelActivity.IS_POLRES) == true -> {
                        val intent = Intent(this@SatPolresActivity, PersonelActivity::class.java)
                        intent.putExtra(PersonelActivity.IS_POLRES, data)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    intent.extras?.getBoolean(KatPersonelActivity.PICK_PERSONEL) == true -> {
                        val intent = Intent(this@SatPolresActivity, ChoosePersonelActivity::class.java)
                        intent.putExtra(PersonelActivity.IS_POLRES, data)
                        startActivityForResult(intent, 453)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                    else -> {
                        val intent = Intent()
                        intent.putExtra(DATA_POLRES_SAT, data)
                        setResult(PolresActivity.RES_POLRES_SAT, intent)
                        finish()
                    }
                }
            }
        }
        list?.let {
            adapterSatPolres.adapterCallback(callbackSatPolres)
                .isVerticalView()
                .filterable()
                .addData(it)
                .build(rv_list_sat_polres)
                .setLayout(R.layout.layout_1_text_clickable)
        }
    }
}