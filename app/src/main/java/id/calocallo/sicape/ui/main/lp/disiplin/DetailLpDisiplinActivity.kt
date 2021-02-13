package id.calocallo.sicape.ui.main.lp.disiplin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.response.LpDisiplinResp
import id.calocallo.sicape.network.response.LpPasalResp
import id.calocallo.sicape.ui.main.lp.pasal.PickPasalLpEditActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.formatterTanggal
import id.calocallo.sicape.utils.ext.gone
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail_lp_disiplin.*
import kotlinx.android.synthetic.main.item_pasal_lp.view.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import org.marproject.reusablerecyclerviewadapter.ReusableAdapter
import org.marproject.reusablerecyclerviewadapter.interfaces.AdapterCallback

class DetailLpDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var adapterDetailPasalDisiplin = ReusableAdapter<LpPasalResp>(this)
    private lateinit var callbackDetailPasalDisiplin: AdapterCallback<LpPasalResp>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_lp_disiplin)
        sessionManager1 = SessionManager1(this)
        adapterDetailPasalDisiplin = ReusableAdapter(this)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Laporan Polisi Disiplin"
        //get item disiplin
        val disiplin = intent.extras?.getParcelable<LpDisiplinResp>(DETAIL_DISIPLIN)
        getViewDisiplin(disiplin)

        val hak = sessionManager1.fetchHakAkses()
        if (hak == "operator") {
            btn_edit_disiplin.gone()
            btn_edit_pasal_disiplin.gone()
        }

        btn_edit_disiplin.setOnClickListener {
            val intent = Intent(this, EditLpDisiplinActivity::class.java)
            intent.putExtra(EditLpDisiplinActivity.EDIT_DISIPLIN, disiplin)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }

        btn_edit_pasal_disiplin.setOnClickListener {
            val intent = Intent(this, PickPasalLpEditActivity::class.java)
            intent.putExtra(PickPasalLpEditActivity.EDIT_PASAL_DISIPLIN, disiplin)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            startActivity(intent)
        }
        btn_generate_disiplin.attachTextChangeAnimator()
        bindProgressButton(btn_generate_disiplin)
        btn_generate_disiplin.setOnClickListener {
            btn_generate_disiplin.showProgress {
                progressColor = Color.WHITE
            }
            Handler(Looper.getMainLooper()).postDelayed({
                btn_generate_disiplin.hideProgress(R.string.success_generate_doc)
                alert(R.string.download) {
                    positiveButton(R.string.iya) {
                        btn_generate_disiplin.hideProgress(R.string.generate_dokumen)

                    }
                    negativeButton(R.string.tidak) {
                        btn_generate_disiplin.hideProgress(R.string.generate_dokumen)
                    }
                }.show()
            }, 2000)
        }
    }


    private fun getViewDisiplin(disiplin: LpDisiplinResp?) {
        //general
        txt_detail_no_lp_disiplin.text = disiplin?.no_lp
        txt_detail_macam_pelanggaran_disiplin.text = disiplin?.macam_pelanggaran
        txt_detail_kota_buat_disiplin.text = "Kota : ${disiplin?.kota_buat_laporan}"
        txt_detail_tgl_buat_disiplin.text =
            "Tanggal : ${formatterTanggal(disiplin?.tanggal_buat_laporan)}"
        txt_detail_rincian_pelanggaran_disiplin.text = disiplin?.rincian_pelanggaran_disiplin
        //pimpinan
        txt_detail_nama_pimpinan_disiplin.text = "Nama : ${disiplin?.nama_yang_mengetahui}"
        txt_detail_pangkat_nrp_pimpinan_disiplin.text =
            "Pangkat : ${disiplin?.pangkat_yang_mengetahui.toString()
                .toUpperCase()}, NRP : ${disiplin?.nrp_yang_mengetahui}"
        txt_detail_jabatan_pimpinan_disiplin.text = "Jabatan : ${disiplin?.jabatan_yang_mengetahui}"
        txt_detail_kesatuan_pimpinan_disiplin.text =
            "Kesatuan : ${disiplin?.kesatuan_yang_mengetahui.toString().toUpperCase()}"

        //terlapor
        txt_detail_nama_terlapor_disiplin.text = "Nama : ${disiplin?.personel_terlapor?.nama}"
        txt_detail_pangkat_nrp_terlapor_disiplin.text =
            "Pangkat : ${disiplin?.personel_terlapor?.pangkat.toString()
                .toUpperCase()}, NRP : ${disiplin?.personel_terlapor?.nrp}"
        txt_detail_jabatan_terlapor_disiplin.text =
            "Jabatan : ${disiplin?.personel_terlapor?.jabatan}"
        txt_detail_kesatuan_terlapor_disiplin.text =
            "Kesatuan : ${disiplin?.personel_terlapor?.kesatuan.toString().toUpperCase()}"

        //pelapor
        txt_detail_nama_pelapor_disiplin.text = "Nama : ${disiplin?.personel_pelapor?.nama}"
        txt_detail_pangkat_nrp_pelapor_disiplin.text =
            "Pangkat : ${disiplin?.personel_pelapor?.pangkat.toString()
                .toUpperCase()}, NRP : ${disiplin?.personel_pelapor?.nrp}"

        txt_detail_jabatan_pelapor_disiplin.text =
            "Jabatan : ${disiplin?.personel_pelapor?.jabatan}"
        txt_detail_kesatuan_pelapor_disiplin.text =
            "Kesatuan : ${disiplin?.personel_pelapor?.kesatuan.toString().toUpperCase()}"
        txt_detail_keterangan_pelapor_disiplin.text = disiplin?.keterangan_terlapor
        txt_detail_kronologis_pelapor_disiplin.text = disiplin?.kronologis_dari_pelapor

        //pasal
        callbackDetailPasalDisiplin = object : AdapterCallback<LpPasalResp> {
            override fun initComponent(itemView: View, data: LpPasalResp, itemIndex: Int) {
                itemView.txt_item_1.text = data.nama_pasal

            }

            override fun onItemClicked(itemView: View, data: LpPasalResp, itemIndex: Int) {
            }
        }
        disiplin?.pasal_dilanggar?.let {
            adapterDetailPasalDisiplin.adapterCallback(callbackDetailPasalDisiplin)
                .isVerticalView().addData(it).setLayout(R.layout.item_pasal_lp)
                .build(rv_detail_pasal_disiplin)
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

    companion object {
        const val DETAIL_DISIPLIN = "DETAIL_DISIPLIN"
    }
}