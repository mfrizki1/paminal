package id.calocallo.sicape.ui.main.profil

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import id.calocallo.sicape.R
import id.calocallo.sicape.network.NetworkConfig
import id.calocallo.sicape.network.response.BaseResp
import id.calocallo.sicape.ui.login.LoginActivity
import id.calocallo.sicape.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_profil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilFragment : Fragment() {
    private lateinit var sessionManager: SessionManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = activity?.let { SessionManager(it) }!!
        val user = sessionManager.fetchUser()
        var hakAkses = sessionManager.fetchHakAkses()

        hakAkses = when(hakAkses){
            "operator"-> "Operator"
            "admin"-> "Admin"
            else-> "Super Admin"
        }
        txt_nama_profil.text = user?.nama
        txt_jenis_hak_akses.text = hakAkses
        btn_logout.setOnClickListener {
            doLogout()
        }
    }

    private fun doLogout() {
        NetworkConfig().getService().logout("Bearer ${sessionManager.fetchAuthToken()}")
            .enqueue(object : Callback<BaseResp> {
                override fun onFailure(call: Call<BaseResp>, t: Throwable) {
                    Toast.makeText(activity, "Gagal Logout", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<BaseResp>, response: Response<BaseResp>) {
                    if (response.isSuccessful) {
//                        deleteUser()
                        sessionManager.clearUser()
                        startActivity(Intent(activity, LoginActivity::class.java))
                        activity?.finish()
                    } else {
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }

//    private fun deleteUser() {
//        val realm = Realm.getDefaultInstance()
//        realm.beginTransaction()
//        realm.deleteAll()
//        realm.commitTransaction()
//    }
}