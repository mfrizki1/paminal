package id.calocallo.sicape.ui.main.profil

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.ui.login.ChangePassActivity
import id.calocallo.sicape.ui.login.KatUserActivity
import id.calocallo.sicape.ui.manajemen.KatOperatorActivity
import id.calocallo.sicape.ui.manajemen.admin.ListAdminPersonelActivity
import id.calocallo.sicape.utils.SessionManager1
import id.calocallo.sicape.utils.ext.visible
import kotlinx.android.synthetic.main.fragment_profil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilFragment : Fragment() {
    private lateinit var sessionManager: SessionManager1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = activity?.let { SessionManager1(it) }!!
        val user = sessionManager.fetchUser()
        txt_nama_profil.text = user?.nama

        val hakAkses = sessionManager.fetchHakAkses()
        when (hakAkses) {
            "operator" -> txt_jenis_hak_akses.text = "Operator"
            "admin" -> {
                txt_jenis_hak_akses.text = "Admin"
                btn_manajemen_operator.visible()
            }
            else -> {
                txt_jenis_hak_akses.text = "Super Admin"
                btn_manajemen_admin.visible()
                btn_manajemen_operator.visible()
            }
        }

        btn_logout.attachTextChangeAnimator()
        bindProgressButton(btn_logout)
        btn_logout.setOnClickListener {
            doLogout()
        }
        btn_manajemen_operator.setOnClickListener {
            val intent = Intent(activity, KatOperatorActivity::class.java)
//            intent.putExtra("list", "operator")
            startActivity(intent)
            activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        btn_manajemen_admin.setOnClickListener {
            val intent = Intent(activity, ListAdminPersonelActivity::class.java)
            intent.putExtra("manajemen", "admin")
            startActivity(intent)
            activity!!.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        //ganti password
        btn_change_password.setOnClickListener {
            startActivity(Intent(activity, ChangePassActivity::class.java))
            activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


    }

    private fun doLogout() {
        btn_logout.showProgress {
            progressColor = R.color.colorPrimary
        }
      NetworkConfig().getServUser().logout("Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(activity, "Gagal Logout", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
//                        deleteUser()
                        sessionManager.clearUser()
                        btn_logout.hideProgress("Logout")
                        val intent = Intent(activity, KatUserActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        activity?.finish()
                    } else {
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }
}