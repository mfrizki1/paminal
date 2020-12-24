package id.calocallo.sicape.ui.main.editpersonel.pendidikan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.request.SinglePendReq
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_add_single_pend.*
import kotlinx.android.synthetic.main.fragment_add_single_pend.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddSinglePendFragment : Fragment() {
    var jenis_pendidikan = ""
    private lateinit var sessionManager: SessionManager
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
        sessionManager = activity?.let { SessionManager(it) }!!
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
        val animatedDrawable = activity?.let { ContextCompat.getDrawable(it, R.drawable.animated_check) }!!
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

        NetworkConfig().getService().addSinglePend(
            "Bearer ${sessionManager.fetchAuthToken()}",
//            "4",
            sessionManager.fetchID().toString(),
            jenis,
            singlePendReq
        ).enqueue(object : Callback<BaseResp>{
            override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                Toast.makeText(activity, "Error Koneksi", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                if(response.isSuccessful){
                    if(response.body()?.message =="Data riwayat pendidikan saved succesfully"){
                        activity?.finish()
                    }else{
                        Toast.makeText(activity, "Error Tambah Data", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(activity, "Error Tambah Data", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
