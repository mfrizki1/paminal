package id.calocallo.sicape.ui.gelar.peserta_gelar

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.AddPesertaLhgResp
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.network.response.LhgMinResp
import id.calocallo.sicape.network.response.PesertaLhgResp
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.action
import id.calocallo.sicape.utils.ext.showSnackbar
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_add_single_peserta.*
import kotlinx.android.synthetic.main.layout_toolbar_white.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSinglePesertaActivity : BaseActivity() {
    private var pesertaLhgResp = PesertaLhgResp()
    private lateinit var sessionManager1: SessionManager1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_single_peserta)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Tambah Data Peserta Gelar Perkara"
        sessionManager1 = SessionManager1(this)

        val dataLhg = intent.getParcelableExtra<LhgMinResp>(ListPesertaGelarActivity.DATA_LHG)
        btn_save_single_peserta_add.attachTextChangeAnimator()
        bindProgressButton(btn_save_single_peserta_add)
        btn_save_single_peserta_add.setOnClickListener {
            btn_save_single_peserta_add.showProgress {
                progressColor = Color.WHITE
            }
         pesertaLhgResp.nama_peserta =    edt_nama_peserta_add_single_peserta.editText?.text.toString()
            pesertaLhgResp.pendapat =    edt_pendapat_peserta_add_single_peserta.editText?.text.toString()
            NetworkConfig().getServLhg().addPesertaLhg(
                "Bearer ${sessionManager1.fetchAuthToken()}", dataLhg?.id, pesertaLhgResp
            ).enqueue(object :
                Callback<Base1Resp<AddPesertaLhgResp>> {
                override fun onResponse(
                    call: Call<Base1Resp<AddPesertaLhgResp>>,
                    response: Response<Base1Resp<AddPesertaLhgResp>>
                ) {
                    if (response.body()?.message == "Data peserta gelar saved succesfully") {
                        btn_save_single_peserta_add.hideProgress(R.string.data_saved)
                        btn_save_single_peserta_add.showSnackbar(R.string.data_saved) {
                            action(R.string.next) {
                                finish()
                            }
                        }
                    } else {
                        btn_save_single_peserta_add.hideProgress(R.string.not_save)
                    }
                }

                override fun onFailure(call: Call<Base1Resp<AddPesertaLhgResp>>, t: Throwable) {
                    Toast.makeText(this@AddSinglePesertaActivity, "$t", Toast.LENGTH_SHORT).show()
                    btn_save_single_peserta_add.hideProgress(R.string.not_save)
                }
            })
        }
    }
}