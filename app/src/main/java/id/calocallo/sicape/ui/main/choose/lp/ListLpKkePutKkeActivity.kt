package id.calocallo.sicape.ui.main.choose.lp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.network.response.LpKkeResp
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.network.response.LpSaksiResp
import id.calocallo.sicape.network.response.SatKerResp
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_lp_kke_put_kke.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class ListLpKkePutKkeActivity : BaseActivity() {
    companion object {
        const val DATA_KKE = "DATA_KKE"
    }
    private lateinit var sessionManager: SessionManager
    private var listLpKke = arrayListOf<LpKkeResp>()
    private var adapterKke = ReusableAdapter<LpKkeResp>(this)
    private lateinit var callbackKke: AdapterCallback<LpKkeResp>

    private var personelTerLapor = PersonelLapor()
    private var personelPeLapor = PersonelLapor()
    private var satKerResp = SatKerResp()
    private var listPasal = arrayListOf<LpPasalResp>()
    private var listSaksi = arrayListOf<LpSaksiResp>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lp_kke_put_kke)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pilih Data Laporan Polisi Kode Etik"
        sessionManager = SessionManager(this)

        getListKke()

    }

    private fun getListKke() {
        satKerResp = SatKerResp(1, "POLDA", "ALAMAT", "081210812", "", "", "", "")
        personelTerLapor =
            PersonelLapor(1, "faisal", "bripda", "jabatan", "1234", "polda kalsel")
        personelPeLapor =
            PersonelLapor(2, "utuh", "ipda", "jabatan", "0987", "polresta banjarmasin")
        listPasal.add(LpPasalResp(1, "Pasal 1", "LOREM IPSUM DOLOR", "", "", ""))
        listSaksi.add(LpSaksiResp(1, "Galuh", "korban", "", "", "", 1, "", "", ""))

        listLpKke.add(
            LpKkeResp(
                1, "LP/KKE1/2019/BIDPROPAM", "kode_etik", personelTerLapor,
                personelPeLapor, "Banjarbaru", "12-12-2000", "Budi",
                "IPDA", "9090", "KOMBES","POLDA KALSEL",
                sessionManager.fetchUser()?.id, "Alat Bukti\nbaju\nsenjata", "isi Laporan",
                listPasal, listSaksi, "", "", ""
            )
        )

        listLpKke.add(
            LpKkeResp(
                2, "LP/KKE2/2019/BIDPROPAM", "kode_etik", personelTerLapor,
                personelPeLapor, "Banjarbaru", "12-12-2000", "Budi",
                "IPDA", "9090", "KOMBES","POLDA KALSEL",
                sessionManager.fetchUser()?.id, "Alat Bukti\nbaju\nsenjata", "isi Laporan",
                listPasal, listSaksi, "", "", ""
            )
        )
        callbackKke = object : AdapterCallback<LpKkeResp> {
            override fun initComponent(itemView: View, data: LpKkeResp, itemIndex: Int) {
                itemView.txt_1_clickable.text = data.no_lp
            }

            override fun onItemClicked(itemView: View, data: LpKkeResp, itemIndex: Int) {
                itemView.img_clickable.toggleVisibility()
                val intent = Intent()
                intent.putExtra(DATA_KKE, data)
                setResult(ChooseLpSkhdActivity.RES_LP_PUT_KKE, intent)
                finish()
            }
        }
        adapterKke.adapterCallback(callbackKke)
            .isVerticalView()
            .addData(listLpKke)
            .setLayout(R.layout.layout_1_text_clickable)
            .build(rv_lp_kke_put_kke)
    }



}