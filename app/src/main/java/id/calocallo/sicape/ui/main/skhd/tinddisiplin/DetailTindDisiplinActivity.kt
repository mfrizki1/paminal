package id.calocallo.sicape.ui.main.skhd.tinddisiplin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.network.response.TindDisplMinResp
import id.calocallo.sicape.network.response.TindDisplResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.alert
import id.calocallo.sicape.utils.ext.gone
import id.calocallo.sicape.ui.base.BaseActivity
import id.calocallo.sicape.utils.ext.toast
import kotlinx.android.synthetic.main.activity_detail_tind_disiplin.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTindDisiplinActivity : BaseActivity() {
    private lateinit var sessionManager1: SessionManager1
    private var detailTindDisiplin: TindDisplMinResp? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tind_disiplin)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Detail Data Tindakan Disiplin"
        sessionManager1 = SessionManager1(this)
        val hakAkses = sessionManager1.fetchHakAkses()
        if (hakAkses == "operator") {
            btn_edit_tind_disiplin.gone()
        }


        detailTindDisiplin =
            intent.extras?.getParcelable<TindDisplMinResp>(SkhdTindDisiplinActivity.EDIT_TIND_DISIPLIN)
        apiDetailTindDispl(detailTindDisiplin)


//        getDetailTindDisiplin(detailTindDisiplin)
    }

    private fun apiDetailTindDispl(detailTindDispl: TindDisplMinResp?) {
        NetworkConfig().getServSkhd().detailPersonelTindDispl(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            detailTindDispl?.id
        ).enqueue(
            object :
                Callback<TindDisplResp> {
                override fun onResponse(
                    call: Call<TindDisplResp>,
                    response: Response<TindDisplResp>
                ) {
                    if (response.isSuccessful) {
                        getDetailTindDisiplin(response.body())
                    } else {
                        Toast.makeText(
                            this@DetailTindDisiplinActivity, R.string.error, Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<TindDisplResp>, t: Throwable) {
                    Toast.makeText(this@DetailTindDisiplinActivity, "$t", Toast.LENGTH_SHORT).show()
                }
            })
    }

    @SuppressLint("SetTextI18n")
    private fun getDetailTindDisiplin(detailTindDisplMin: TindDisplResp?) {
        if (detailTindDisplMin?.isi_tindakan_disiplin == "Lain-Lainnya") {
            txt_tindakan_detail.text = detailTindDisplMin.keterangan

        } else {
            txt_tindakan_detail.text = detailTindDisplMin?.isi_tindakan_disiplin
        }
        txt_nama_personel_tind_disiplin_detail.text = "Nama : ${detailTindDisplMin?.personel?.nama}"
        txt_pangkat_personel_tind_disiplin_detail.text =
            "Pangkat : ${detailTindDisplMin?.personel?.pangkat.toString().toUpperCase()}"
        txt_nrp_personel_tind_disiplin_detail.text = "NRP : ${detailTindDisplMin?.personel?.nrp}"
        txt_jabatan_personel_tind_disiplin_detail.text =
            "Jabatan : ${detailTindDisplMin?.personel?.jabatan}"
        txt_kesatuan_personel_tind_disiplin_detail.text =
            "Kesatuan : ${detailTindDisplMin?.personel?.satuan_kerja?.kesatuan}"

        btn_edit_tind_disiplin.setOnClickListener {
            val intent = Intent(this, EditTindDisiplinSkhdActivity::class.java)
            intent.putExtra(SkhdTindDisiplinActivity.EDIT_TIND_DISIPLIN, detailTindDisplMin)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
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
                val hak = sessionManager1.fetchHakAkses()
                if(hak != "operator"){
                    alertDialogDelete()
                }else{
                    toast("Hanya bisa melalui Admin")
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun alertDialogDelete() {
        this.alert("Hapus Data", "Yakin Hapus?") {
            positiveButton("Iya") {
                ApiDelete(detailTindDisiplin)
                finish()
            }
            negativeButton("Tidak") {
            }

        }.show()
    }

    private fun ApiDelete(detailTindDisiplin: TindDisplMinResp?) {
        NetworkConfig().getServSkhd().delPersonelTindDispl(
            "Bearer ${sessionManager1.fetchAuthToken()}",
            detailTindDisiplin?.id
        ).enqueue(
            object : Callback<BaseResp> {
                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.body()?.message == "Data tindakan disiplin removed succesfully") {
                        Toast.makeText(
                            this@DetailTindDisiplinActivity,
                            R.string.data_deleted,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        toast("${response.body()?.message}")
                    }
                }

                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(this@DetailTindDisiplinActivity, "$t", Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    override fun onResume() {
        super.onResume()
        apiDetailTindDispl(detailTindDisiplin)
    }
}