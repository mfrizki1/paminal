package id.calocallo.sicape.ui.main.catpers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LpOnCatpersModel
import id.calocallo.sicape.network.response.CatpersResp
import id.calocallo.sicape.network.response.PasalResp
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_catpers.*
import kotlinx.android.synthetic.main.item_catpers.view.*
import kotlinx.android.synthetic.main.item_pasal_lp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class DetailCatpersActivity : BaseActivity() {
    companion object {
        const val DETAIL_CATPERS = "DETAIL_CATPERS"
    }

    private var adapterPasalOnCatpers = ReusableAdapter<PasalResp>(this)
    private lateinit var callbackPasalOnCatpers: AdapterCallback<PasalResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_catpers)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Catatan Personel"
        val dataCatpers = intent.extras?.getParcelable<CatpersResp>(DETAIL_CATPERS)
        getDataCatper(dataCatpers)

        btn_edit_catpers_detail.setOnClickListener {
            val intent = Intent(this, EditCatpersActivity::class.java)
            intent.putExtra(EditCatpersActivity.EDIT_CATPERS, dataCatpers)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun getDataCatper(dataCatpers: CatpersResp?) {
        txt_nama_personel_catpers_detail.text = dataCatpers?.personel_terlapor?.nama
        txt_pangkat_nrp_personel_catpers_detail.text =
            "Pangkat : ${dataCatpers?.personel_terlapor?.pangkat.toString()
                .toUpperCase()}  ${dataCatpers?.personel_terlapor?.nrp}"
        txt_jabatan_personel_catpers_detail.text = dataCatpers?.personel_terlapor?.jabatan
        txt_kesatuan_personel_catpers_detail.text =
            dataCatpers?.personel_terlapor?.kesatuan.toString().toUpperCase()
        txt_no_lp_catpers_detail.text = dataCatpers?.lp?.no_lp
        txt_tgl_no_lp_catpers_detail.text =
            "Tanggal : ${dataCatpers?.lp?.tanggal_buat_laporan}"
        when (dataCatpers?.status_kasus) {
            "proses" -> { txt_status_kasus_catpers_detail.text = "Proses" }
            "dalam_masa_hukuman" -> { txt_status_kasus_catpers_detail.text = "Dalam Masa Hukuman"}
            "selesai" -> { txt_status_kasus_catpers_detail.text = "Selesai"}
        }
        if (dataCatpers?.skhd?.id != null) {
            txt_no_skhd_putkke_catpers_detail.text = dataCatpers.skhd?.no_skhd
        } else {
            txt_no_skhd_putkke_catpers_detail.text = dataCatpers?.putkke?.no_putkke
        }
        callbackPasalOnCatpers = object : AdapterCallback<PasalResp> {
            override fun initComponent(itemView: View, data: PasalResp, itemIndex: Int) {
                itemView.txt_item_1.text = data.nama_pasal
            }

            override fun onItemClicked(itemView: View, data: PasalResp, itemIndex: Int) {
            }
        }
        dataCatpers?.pasal_dilanggar?.let {
            adapterPasalOnCatpers.adapterCallback(callbackPasalOnCatpers)
                .isVerticalView().filterable()
                .addData(it).setLayout(R.layout.item_pasal_lp)
                .build(rv_pasal_catpers_detail)
        }

    }
}