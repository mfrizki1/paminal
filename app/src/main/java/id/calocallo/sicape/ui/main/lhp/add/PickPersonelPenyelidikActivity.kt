package id.calocallo.sicape.ui.main.lhp.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.request.PersonelPenyelidikReq
import id.calocallo.sicape.network.response.PersonelPenyelidikResp
import id.calocallo.sicape.utils.LhpDataManager
import id.calocallo.sicape.utils.SessionManager1
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_pick_personel_penyelidik.*
import kotlinx.android.synthetic.main.item_2_text.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class PickPersonelPenyelidikActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private lateinit var lhpDataManager: LhpDataManager
    private var adapterLidik = ReusableAdapter<PersonelPenyelidikReq>(this)
    private lateinit var callbackLidik: AdapterCallback<PersonelPenyelidikReq>
    private var currPersonelLidik = ArrayList<PersonelPenyelidikResp>()
//    private var templidikReq= PersonelPenyelidikReq()
    private var templidikReq= ArrayList<PersonelPenyelidikReq>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_personel_penyelidik)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Laporan Hasil Penyelidikan"
        sessionManager1 = SessionManager1(this)
        lhpDataManager = LhpDataManager(this)


        //set add for lidik single
        btn_add_lidik_lhp.setOnClickListener {
            val intent = Intent(this, AddPersonelLidikActivity::class.java)
            startActivityForResult(intent, REQ_LIDIK_PICK)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        //set next and save data on personel lidik
        btn_next_lidik_lhp.setOnClickListener {
            lhpDataManager.setListLidikLHP(templidikReq)
//            val intent = Intent(this, PickSaksiAddLhpActivity::class.java)
            val intent = Intent(this, OtherAddLhpActivity::class.java)
            startActivity(intent)
//            Log.e("added Lidik", "$templidikReq")
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

//        getListDataLidik(currPersonelLidik)
    }

    private fun getListDataLidik(currPersonelLidik: ArrayList<PersonelPenyelidikReq>) {
        callbackLidik = object : AdapterCallback<PersonelPenyelidikReq> {
            override fun initComponent(
                itemView: View,
                data: PersonelPenyelidikReq,
                itemIndex: Int
            ) {
                itemView.txt_detail_1.text = data.nama_personel
                when (data.is_ketua) {
                    1 -> itemView.txt_detail_2.text = "Ketua Tim"
                    0 -> itemView.txt_detail_2.text = "Anggota"
                }
            }

            override fun onItemClicked(
                itemView: View,
                data: PersonelPenyelidikReq,
                itemIndex: Int
            ) {}

        }
        adapterLidik.adapterCallback(callbackLidik)
            .isVerticalView()
            .addData(currPersonelLidik)
            .setLayout(R.layout.item_2_text)
            .build(rv_list_add_personel_lidik_lhp)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val personelLidik =
            data?.getParcelableExtra<PersonelPenyelidikResp>(AddPersonelLidikActivity.LIDIK)
        val personelLidikReq = data?.getParcelableExtra<PersonelPenyelidikReq>(AddPersonelLidikActivity.LIDIK_REQ)
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_LIDIK_PICK) {
            Log.e("req", "$personelLidikReq")
            personelLidik?.let { currPersonelLidik.add(it) }
            personelLidikReq?.let { templidikReq.add(it) }
            getListDataLidik(templidikReq)
//            templidikReq.id_personel = personelLidikReq?.id_personel
//            templidikReq.is_ketua = personelLidikReq?.is_ketua

            Log.e("personel", "$personelLidik")
        }
    }

    companion object {
        const val REQ_LIDIK_PICK = 123
    }
}