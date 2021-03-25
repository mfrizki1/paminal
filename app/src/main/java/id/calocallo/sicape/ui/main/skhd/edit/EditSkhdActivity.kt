package id.calocallo.sicape.ui.main.skhd.edit

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.model.LhpResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SkhdReq
import id.calocallo.sicape.network.response.*
import id.calocallo.sicape.ui.main.choose.lp.ChooseLpSkhdActivity
import id.calocallo.sicape.ui.main.skhd.AddSkhdActivity
import id.calocallo.sicape.ui.main.skhd.DetailSkhdActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.showSnackbar
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_add_skhd.*
import kotlinx.android.synthetic.main.activity_edit_skhd.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditSkhdActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var skhdReq = SkhdReq()
    private var idLhp: Int? = null
    private var idLp: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_skhd)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Edit Data SKHD"
        sessionManager1 = SessionManager1(this)
        val detailSkhd = intent.extras?.getParcelable<SkhdMinResp>(DetailSkhdActivity.DETAIL_SKHD)
        apiDetailSkhd(detailSkhd)

        btn_save_skhd_edit.setOnClickListener {
            edt_no_skhd_edit.text.toString()
            edt_satker_skhd_edit.text.toString()
//            edt_berkas_perkara_skhd_edit.text.toString()
//            edt_memperlihat_skhd_edit.text.toString()
            edt_hukuman_skhd_edit.text.toString()
            edt_tgl_disampaikan_skhd_edit.text.toString()
            edt_pukul_disampaikan_skhd_edit.text.toString()
            edt_kota_dibuat_skhd_edit.text.toString()
            edt_tgl_dibuat_skhd_edit.text.toString()
            edt_nama_bidang_skhd_edit.text.toString()
            edt_pangkat_bidang_skhd_edit.text.toString()
            edt_nrp_bidang_skhd_edit.text.toString()
            edt_jabatan_kepala_bidang_skhd_edit.text.toString()
            edt_tembusan_skhd_edit.text.toString()
        }
/*

        bnt_pick_lhp_skhd_edit.setOnClickListener {
            val intent = Intent(this, ChooseLhpActivity::class.java)
            startActivityForResult(intent, AddSkhdActivity.REQ_CHOOSE_LHP)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
*/

        /*set button for edit id_lp*/
        /* btn_pick_lp_skhd_edit.setOnClickListener {
             val intent = Intent(this, ChooseLpSkhdActivity::class.java)
             intent.putExtra(AddSkhdActivity.IDLHP_FOR_LP, idLhp)
             startActivityForResult(intent, AddSkhdActivity.REQ_CHOOSE_LP)
             overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
         }*/

        btn_save_skhd_edit.setOnClickListener {
            updateSkhd(detailSkhd)
        }
    }

    private fun apiDetailSkhd(detailSkhd: SkhdMinResp?) {
        NetworkConfig().getServSkhd()
            .detailSkhd("Bearer ${sessionManager1.fetchAuthToken()}", detailSkhd?.id).enqueue(
                object :
                    Callback<SkhdResp> {
                    override fun onResponse(call: Call<SkhdResp>, response: Response<SkhdResp>) {
                        if (response.isSuccessful) {
                            getDetailSkhd(response.body())
                        } else {
                            Toast.makeText(
                                this@EditSkhdActivity, R.string.error, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<SkhdResp>, t: Throwable) {
                        Toast.makeText(
                            this@EditSkhdActivity, "$t", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
    }

    private fun getDetailSkhd(detailSkhd: SkhdResp?) {
        edt_no_skhd_edit.setText(detailSkhd?.no_skhd)
        edt_satker_skhd_edit.setText(detailSkhd?.bidang)
        edt_no_berkas_perkara_skhd_edit.setText(detailSkhd?.no_berkas_perkara)
        edt_tgl_berkas_perkara_skhd_edit.setText(detailSkhd?.tanggal_buat_berkas_perkara)
        edt_tanggal_sidang_skhd_edit.setText(detailSkhd?.tanggal_sidang_disiplin)
        /* edt_berkas_perkara_skhd_edit.setText(detailSkhd?.menimbang_p2)
         edt_memperlihat_skhd_edit.setText(detailSkhd?.memperlihatkan)*/
        edt_hukuman_skhd_edit.setText(detailSkhd?.hukuman)
        edt_tgl_disampaikan_skhd_edit.setText(detailSkhd?.tanggal_disampaikan_ke_terhukum)
        edt_pukul_disampaikan_skhd_edit.setText(detailSkhd?.waktu_disampaikan_ke_terhukum)
        edt_kota_dibuat_skhd_edit.setText(detailSkhd?.kota_penetapan)
        edt_tgl_dibuat_skhd_edit.setText(detailSkhd?.tanggal_penetapan)
        edt_nama_bidang_skhd_edit.setText(detailSkhd?.nama_yang_menetapkan)
        edt_kesatuan_kepala_bidang_skhd_edit.setText(detailSkhd?.kesatuan_yang_menetapkan)
        edt_pangkat_bidang_skhd_edit.setText(
            detailSkhd?.pangkat_yang_menetapkan.toString().toUpperCase()
        )
        edt_nrp_bidang_skhd_edit.setText(detailSkhd?.nrp_yang_menetapkan)
        edt_jabatan_kepala_bidang_skhd_edit.setText(detailSkhd?.jabatan_yang_menetapkan)
        edt_tembusan_skhd_edit.setText(detailSkhd?.tembusan)
        /* txt_lhp_skhd_edit.text = detailSkhd?.lhp?.no_lhp*/
        txt_lp_skhd_edit.text = detailSkhd?.lp?.no_lp
    }

    private fun updateSkhd(detailSkhd: SkhdMinResp?) {
        skhdReq.no_skhd = edt_no_skhd_edit.text.toString()
        skhdReq.bidang = edt_satker_skhd_edit.text.toString().toUpperCase()
        /*  skhdReq.menimbang_p2 = edt_berkas_perkara_skhd_edit.text.toString()
          skhdReq.memperlihatkan = edt_memperlihat_skhd_edit.text.toString()*/
        skhdReq.hukuman = edt_hukuman_skhd_edit.text.toString()
        skhdReq.tanggal_disampaikan_ke_terhukum = edt_tgl_disampaikan_skhd_edit.text.toString()
        skhdReq.waktu_disampaikan_ke_terhukum = edt_pukul_disampaikan_skhd_edit.text.toString()
        skhdReq.kota_penetapan = edt_kota_dibuat_skhd_edit.text.toString()
        skhdReq.tanggal_penetapan = edt_tgl_dibuat_skhd_edit.text.toString()
        skhdReq.nama_yang_menetapkan = edt_nama_bidang_skhd_edit.text.toString()
        skhdReq.jabatan_yang_menetapkan =
            edt_jabatan_kepala_bidang_skhd_edit.text.toString().toUpperCase()
        skhdReq.pangkat_yang_menetapkan = edt_pangkat_bidang_skhd_edit.text.toString()
        skhdReq.nrp_yang_menetapkan = edt_nrp_bidang_skhd_edit.text.toString()
        skhdReq.kesatuan_yang_menetapkan = edt_kesatuan_kepala_bidang_skhd_edit.text.toString()
        skhdReq.tembusan = edt_tembusan_skhd_edit.text.toString()
        /*skhdReq.id_lhp = idLhp*/
        skhdReq.id_lp = idLp
        skhdReq.no_berkas_perkara = edt_no_berkas_perkara_skhd_edit.text.toString()
        skhdReq.tanggal_buat_berkas_perkara = edt_tgl_berkas_perkara_skhd_edit.text.toString()
        skhdReq.tanggal_sidang_disiplin = edt_tanggal_sidang_skhd_edit.text.toString()
        Log.e("editSKHD", "$skhdReq")
        btn_save_skhd_edit.showProgress {
            progressColor = Color.WHITE
        }
        apiUpdSkhd(detailSkhd)

        /* btn_save_skhd_edit.showDrawable(animated) {
             textMarginRes = R.dimen.space_10dp
             buttonTextRes = R.string.data_updated
         }

         btn_save_skhd_edit.hideDrawable(R.string.save)*/
    }

    private fun apiUpdSkhd(detailSkhd: SkhdMinResp?) {
        NetworkConfig().getServSkhd()
            .updSkhd("Bearer ${sessionManager1.fetchAuthToken()}", detailSkhd?.id, skhdReq).enqueue(
                object : Callback<Base1Resp<AddSkhdResp>> {
                    override fun onResponse(
                        call: Call<Base1Resp<AddSkhdResp>>,
                        response: Response<Base1Resp<AddSkhdResp>>
                    ) {
                        if (response.body()?.message == "Data skhd updated succesfully") {
                            btn_save_skhd_edit.hideProgress(R.string.data_updated)
                            btn_save_skhd_edit.showSnackbar(R.string.data_updated) {
                                finish()
                            }

                        } else {
                            toast("${response.body()?.message}")
                            btn_save_skhd_edit.hideProgress(R.string.not_update)
                        }
                    }

                    override fun onFailure(call: Call<Base1Resp<AddSkhdResp>>, t: Throwable) {
                        Toast.makeText(this@EditSkhdActivity, "$t", Toast.LENGTH_SHORT).show()
                        btn_save_skhd_edit.hideProgress(R.string.not_update)
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AddSkhdActivity.REQ_CHOOSE_LHP) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val lhpResp = data?.getParcelableExtra<LhpResp>("CHOOSE_LHP")
                    idLhp = lhpResp?.id
                    txt_lhp_skhd_edit.text = lhpResp?.no_lhp
                }
            }
        } else if (requestCode == AddSkhdActivity.REQ_CHOOSE_LP) {
            when (resultCode) {
                ChooseLpSkhdActivity.RES_LP_PIDANA_SKHD -> {
                    val pidana = data?.getParcelableExtra<LpResp>(AddSkhdActivity.ID_LP)
                    idLp = pidana?.id
                    txt_lp_skhd_edit.text = pidana?.no_lp
                }
                ChooseLpSkhdActivity.RES_LP_DISIPLIN_SKHD -> {
                    val disiplin = data?.getParcelableExtra<LpDisiplinResp>(AddSkhdActivity.ID_LP)
                    idLp = disiplin?.id
                    txt_lp_skhd_edit.text = disiplin?.no_lp
                }
            }
        }
    }
}