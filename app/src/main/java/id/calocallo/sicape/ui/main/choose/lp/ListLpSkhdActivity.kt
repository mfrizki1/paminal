package id.calocallo.sicape.ui.main.choose.lp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.model.PersonelLapor
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.toggleVisibility
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list_lp_skhd.*
import kotlinx.android.synthetic.main.layout_1_text_clickable.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class ListLpSkhdActivity : BaseActivity() {
    private lateinit var sessionManager: SessionManager

    private var listPidanaLP = ArrayList<LpPidanaResp>()
    private var listDisiplinLP = ArrayList<LpDisiplinResp>()
    private var personelTerLapor = PersonelLapor()
    private var personelPeLapor = PersonelLapor()
    private var satKerResp = SatKerResp()
    private var listPasal = arrayListOf<LpPasalResp>()
    private var listSaksi = arrayListOf<LpSaksiResp>()

    private var adapterLpPidanaChoose = ReusableAdapter<LpPidanaResp>(this)
    private lateinit var callbackLpPidanaChoose: AdapterCallback<LpPidanaResp>
    private var adapterLpDisiplinChoose = ReusableAdapter<LpDisiplinResp>(this)
    private lateinit var callbackLpDisiplinChoose: AdapterCallback<LpDisiplinResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_lp_skhd)
        sessionManager = SessionManager(this)

        setupActionBarWithBackButton(toolbar)
        val jenisLPFromSkhd = intent.extras?.getString(ChooseLpSkhdActivity.CHOOSE_LP_SKHD)

        /*set idLHP for getlist lp*/
        val idLhp = intent.extras?.getInt(ChooseLpSkhdActivity.ID_LHP_FOR_LP)
        Log.e("ListLpSkhd","$idLhp")
        when (jenisLPFromSkhd) {
            "pidana" -> {
                supportActionBar?.title = "Data Laporan Polisi Pidana"
                getPidana(idLhp)
            }
            "disiplin" -> {
                supportActionBar?.title = "Data Laporan Polisi Disiplin"
                getDisiplin(idLhp)
            }
        }

        satKerResp = SatKerResp(1, "POLDA", "ALAMAT", "081210812", "", "", "", "")
        personelTerLapor =
            PersonelLapor(1, "faisal", "bripda", "jabatan", "1234", "polda kalsel")
        personelPeLapor =
            PersonelLapor(2, "utuh", "ipda", "jabatan", "0987", "polresta banjarmasin")
        listPasal.add(LpPasalResp(1, "Pasal 1", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(2, "Pasal 2", "LOREM IPSUM DOLOR", "", "", ""))
        listPasal.add(LpPasalResp(3, "Pasal 3", "LOREM IPSUM DOLOR", "", "", ""))
        listSaksi.add(LpSaksiResp(1, "Galuh", "korban", "", "", "", 1, "", "", ""))
        listSaksi.add(LpSaksiResp(2, "Akbar", "saksi", "", "", "", 0, "", "", ""))
        listSaksi.add(LpSaksiResp(3, "Wahyu", "saksi", "", "", "", 0, "", "", ""))


    }

    private fun getPidana(idLhp: Int?) {
        listPidanaLP.add(
            LpPidanaResp(
                1,
                "LP/PIDANA/2020/BIDPROPAM",
                satKerResp,
                personelTerLapor,
                "Uraian Pelanggaran",
                "Banjarmasin",
                "12-12-2000",
                "Rojak Ahmad",
                "Kombes",
                "12345678",
                "Polair",
                "polda Kalsel",
                "polisi",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                personelPeLapor,
                resources.getString(R.string.paragraf),
                resources.getString(R.string.paragraf),
                listPasal,
                "",
                "",
                ""
            )
        )

        listPidanaLP.add(
            LpPidanaResp(
                2,
                "LP/PIDANA2/2020/BIDPROPAM",
                satKerResp,
                personelTerLapor,
                "Uraian Pelanggaran",
                "Banjarmasin",
                "12-12-2000",
                "Rojak Ahmad",
                "Kombes",
                "12345678",
                "Polair",
                "polda Kalsel",
                "sipil",
                "sipil",
                "islam",
                "pekerjaan",
                "Indonesia",
                "jl xxx",
                "081212",
                "123456",
                null,
                resources.getString(R.string.paragraf),
                resources.getString(R.string.paragraf),
                listPasal,
                "",
                "",
                ""
            )
        )
        callbackLpPidanaChoose = object : AdapterCallback<LpPidanaResp> {
            override fun initComponent(itemView: View, data: LpPidanaResp, itemIndex: Int) {
                itemView.txt_1_clickable.text = data.no_lp
            }

            override fun onItemClicked(itemView: View, data: LpPidanaResp, itemIndex: Int) {
                itemView.img_clickable.toggleVisibility()
                val intent = Intent()
                intent.putExtra(PIDANA, data)
                setResult(999,intent)
                finish()
            }
        }
        adapterLpPidanaChoose.adapterCallback(callbackLpPidanaChoose)
            .isVerticalView().addData(listPidanaLP)
            .setLayout(R.layout.layout_1_text_clickable).build(rv_choose_lp_skhd)
    }

    private fun getDisiplin(idLhp: Int?) {
        listDisiplinLP.add(
            LpDisiplinResp(
                1, "LP/DISIPLIN1",
                "disiplin", personelTerLapor, personelPeLapor,
                "Banjarmasin", "12-01-20", "Budi",
                "IPDA", "87654321", "KOMBES","polda kALSEEL",
                "macam_pelanggaran", "keterangan terlapor",
                "kronologis", "rincian", listPasal, satKerResp, "", ""
            )
        )
        callbackLpDisiplinChoose = object :AdapterCallback<LpDisiplinResp>{
            override fun initComponent(itemView: View, data: LpDisiplinResp, itemIndex: Int) {
                itemView.txt_1_clickable.text = data.no_lp

            }

            override fun onItemClicked(itemView: View, data: LpDisiplinResp, itemIndex: Int) {
                itemView.img_clickable.toggleVisibility()
                val intent = Intent()
                intent.putExtra(DISIPLIN, data)
                setResult(888,intent)
                finish()
            }
        }
        adapterLpDisiplinChoose.adapterCallback(callbackLpDisiplinChoose)
            .isVerticalView().addData(listDisiplinLP)
            .setLayout(R.layout.layout_1_text_clickable).build(rv_choose_lp_skhd)
    }

    companion object {
        const val PIDANA = "PIDANA"
        const val DISIPLIN = "DISIPLIN"

    }

}