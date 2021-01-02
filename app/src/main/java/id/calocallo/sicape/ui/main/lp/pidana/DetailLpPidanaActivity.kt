package id.calocallo.sicape.ui.main.lp.pidana

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.network.response.LpPidanaResp
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalLpEditActivity
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalLpEditActivity.Companion.EDIT_PASAL_PIDANA
import id.calocallo.sicape.ui.main.lp.pidana.EditLpPidanaActivity.Companion.EDIT_PIDANA
import id.calocallo.sicape.utils.SessionManager
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.utils.ext.visible
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_lp_pidana.*
import kotlinx.android.synthetic.main.item_pasal_lp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class DetailLpPidanaActivity : BaseActivity() {
    companion object {
        const val DETAIL_PIDANA = "DETAIL_PIDANA"
    }

    private lateinit var sessionManager: SessionManager
    private lateinit var adapterDetailPasal: ReusableAdapter<LpPasalResp>
    private lateinit var callbackDetailPasal: AdapterCallback<LpPasalResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lp_pidana)
        sessionManager = SessionManager(this)
        adapterDetailPasal = ReusableAdapter(this)

        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Laporan Polisi Pidana"
        val pidana = intent.extras?.getParcelable<LpPidanaResp>(DETAIL_PIDANA)
        getDetailPidana(pidana)

        val hak = sessionManager.fetchHakAkses()
        if (hak == "operator") {
            btn_edit_pidana.gone()
            btn_edit_pasal_pidana.gone()
        }

        btn_edit_pidana.setOnClickListener {
            val intent = Intent(this, EditLpPidanaActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            intent.putExtra(EDIT_PIDANA, pidana)
            startActivity(intent)
        }
        btn_edit_pasal_pidana.setOnClickListener {
            val intent = Intent(this, PickPasalLpEditActivity::class.java)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            intent.putExtra(EDIT_PASAL_PIDANA, pidana)
            startActivity(intent)

        }
    }

    private fun getDetailPidana(pidana: LpPidanaResp?) {
        if (pidana?.status_pelapor == "polisi") {
            ll_detail_personel_pidana.visible()
            ll_detail_sipil_pidana.gone()
        } else {
            ll_detail_personel_pidana.gone()
            ll_detail_sipil_pidana.visible()
        }
        //general
        txt_detail_no_lp.text = "No LP : ${pidana?.no_lp}"
        txt_detail_pembukaan_laporan_pidana.text = pidana?.pembukaan_laporan
        txt_detail_isi_laporan_pidana.text = pidana?.isi_laporan
        txt_detail_kota_buat_pidana.text = "Kota : ${pidana?.kota_buat_laporan}"
        txt_detail_tgl_buat_pidana.text = "Tanggal : ${pidana?.tanggal_buat_laporan}"

        //pimpinan
        txt_detail_nama_pimpinan_pidana.text = "Nama : ${pidana?.nama_yang_mengetahui}"
        txt_detail_pangkat_nrp_pimpinan_pidana.text =
            "Pangkat : ${pidana?.pangkat_yang_mengetahui}, NRP : ${pidana?.nrp_yang_mengetahui}"
        txt_detail_jabatan_pimpinan_pidana.text = "Jabatan : ${pidana?.jabatan_yang_mengetahui}"

        //terlapor
        txt_detail_nama_terlapor.text = "Nama : ${pidana?.id_personel_terlapor}"
        txt_detail_pangkat_nrp_terlapor.text =
            "Pangkat : ${pidana?.id_personel_terlapor}, NRP : ${pidana?.id_personel_terlapor}"
        txt_detail_jabatan_terlapor.text = "Jabatan : ${pidana?.id_personel_terlapor}"
        txt_detail_kesatuan_terlapor.text = "Kesatuan : ${pidana?.id_personel_terlapor}"

        //pelapor
        txt_detail_nama_pelapor.text = "Nama : ${pidana?.id_personel_pelapor}"
        txt_detail_pangkat_nrp_pelapor.text =
            "Pangkat : ${pidana?.id_personel_pelapor}, NRP : ${pidana?.id_personel_pelapor}"
        txt_detail_jabatan_pelapor.text = "Jabatan : ${pidana?.id_personel_pelapor}"
        txt_detail_kesatuan_pelapor.text = "Kesatuan : ${pidana?.id_personel_pelapor}"

        //sipil
        txt_detail_nama_sipil.text = "Nama :  ${pidana?.nama_pelapor}"
        txt_detail_agama_sipil.text = "Agama : ${pidana?.agama_pelapor}"
        txt_detail_pekerjaan_sipil.text = "Pekerjaan : ${pidana?.pekerjaan_pelapor}"
        txt_detail_kwg_sipil.text = "Kewarganegaraan : ${pidana?.kewarganegaraan_pelapor}"
        txt_detail_alamat_sipil.text = "Alamat : ${pidana?.alamat_pelapor}"
        txt_detail_no_telp_sipil.text = "No Telepon : ${pidana?.no_telp_pelapor}"
        txt_detail_nik_sipil.text = "NIK KTP : ${pidana?.nik_ktp_pelapor}"

        //setPasal
        callbackDetailPasal = object : AdapterCallback<LpPasalResp> {
            override fun initComponent(itemView: View, data: LpPasalResp, itemIndex: Int) {
                itemView.txt_item_1.text = data.nama_pasal
            }

            override fun onItemClicked(itemView: View, data: LpPasalResp, itemIndex: Int) {
            }
        }
        pidana?.listPasal?.let {
            adapterDetailPasal.adapterCallback(callbackDetailPasal)
                .isVerticalView().addData(it)
                .setLayout(R.layout.item_pasal_lp).build(rv_detail_lp_pidana_pasal)

        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_delete, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_delete_item -> {
                alertDialogDelete()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun alertDialogDelete() {
        this.alert("Hapus Data", "Yakin Hapus?") {
            positiveButton("Iya") {
//                ApiDelete()
                finish()
            }
            negativeButton("Tidak") {
            }
        }.show()
    }
}