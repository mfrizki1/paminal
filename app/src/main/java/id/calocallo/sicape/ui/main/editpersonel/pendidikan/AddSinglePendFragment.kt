package id.calocallo.sicape.ui.main.editpersonel.pendidikan

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.*
import id.calocallo.sicape.R
import id.calocallo.sicape.model.AddSinglePendResp
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SinglePendReq
import id.calocallo.sicape.network.response.Base1Resp
import id.calocallo.sicape.utils.SessionManager1
import kotlinx.android.synthetic.main.fragment_add_single_pend.*
import kotlinx.android.synthetic.main.fragment_add_single_pend.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSinglePendFragment : Fragment() {
    var jenis_pendidikan = ""
    private lateinit var sessionManager1: SessionManager1
    private val singlePendReq = SinglePendReq()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_single_pend, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager1 = activity?.let { SessionManager1(it) }!!
        val item = listOf("Umum", "Kedinasan", "Lain-Lain")
        val adapter = activity?.let { ArrayAdapter(it, R.layout.item_spinner, item) }
        view.sp_jenis_pendidikan_single.setAdapter(adapter)
        view.sp_jenis_pendidikan_single.setOnItemClickListener { parent, _view, position, id ->
            if (position == 0) {
                jenis_pendidikan = "umum"
//                doAdd(jenis_pendidikan)
            } else if (position == 1) {
                jenis_pendidikan = "kedinasan"
//                doAdd(jenis_pendidikan)
            } else {
                jenis_pendidikan = "lain_lain"
//                doAdd(jenis_pendidikan)
            }
        }
        view.btn_save_add_pend_single.attachTextChangeAnimator()
        bindProgressButton(view.btn_save_add_pend_single)
        view.btn_save_add_pend_single.setOnClickListener {
            doAdd(jenis_pendidikan)
        }

    }

    private fun doAdd(jenis: String) {
        val animatedDrawable =
            activity?.let { ContextCompat.getDrawable(it, R.drawable.animated_check) }!!
        //Defined bounds are required for your drawable
        val drawableSize = resources.getDimensionPixelSize(R.dimen.space_25dp)
        animatedDrawable.setBounds(0, 0, drawableSize, drawableSize)

        singlePendReq.kota = edt_tempat_add_pend_single.text.toString()
        singlePendReq.yang_membiayai = edt_membiayai_add_pend_single.text.toString()
        singlePendReq.tahun_awal = edt_thn_awal_add_pend_single.text.toString()
        singlePendReq.tahun_akhir = edt_thn_akhir_add_pend_single.text.toString()
        singlePendReq.tahun_akhir = edt_thn_akhir_add_pend_single.text.toString()
        singlePendReq.keterangan = edt_ket_add_pend_single.text.toString()
        singlePendReq.pendidikan = edt_nama_add_pend_single.text.toString()
        singlePendReq.jenis = jenis

        view?.btn_save_add_pend_single?.showProgress {
            progressColor = Color.WHITE
        }
        NetworkConfig().getServPers().addSinglePend(
            "Bearer ${sessionManager1.fetchAuthToken()}",
//            "4",
            sessionManager1.fetchID().toString(),
//            jenis,
            singlePendReq
        ).enqueue(object : Callback<Base1Resp<AddSinglePendResp>> {
            override fun onFailure(call: Call<Base1Resp<AddSinglePendResp>>, t: Throwable) {
                Toast.makeText(activity, "$t", Toast.LENGTH_SHORT).show()
                btn_save_add_pend_single.hideDrawable(R.string.save)
            }

            override fun onResponse(
                call: Call<Base1Resp<AddSinglePendResp>>,
                response: Response<Base1Resp<AddSinglePendResp>>
            ) {
                if (response.isSuccessful) {
                    if (response.body()?.message == "Data riwayat pendidikan personel saved succesfully") {
                        view?.btn_save_add_pend_single?.showDrawable(animatedDrawable) {
                            buttonTextRes = R.string.data_saved
                            textMarginRes = R.dimen.space_10dp
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            fragmentManager?.popBackStack()
                        }, 500)
//                        activity?.finish()
                    } else {
                        Toast.makeText(activity, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            btn_save_add_pend_single.hideDrawable(R.string.save)
                        }, 3000)
                        btn_save_add_pend_single.hideDrawable(R.string.not_save)
                    }
                } else {
                    Toast.makeText(activity, "${response.body()?.message}", Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        btn_save_add_pend_single.hideDrawable(R.string.save)
                    }, 3000)
                    btn_save_add_pend_single.hideDrawable(R.string.not_save)
                }
            }
        })
    }
}
