package id.calocallo.sicape.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import id.calocallo.sicape.R
import id.calocallo.sicape.model.FiturModel
import id.calocallo.sicape.ui.main.personel.PersonelActivity
import id.calocallo.sicape.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment(),FiturAdapter.FiturListener {
    private lateinit var sessionmanager: SessionManager
    lateinit var list: ArrayList<FiturModel>
    val listFitur = listOf(
        FiturModel("Personel", R.drawable.ic_personel),
        FiturModel("Laporan Polisi", R.drawable.bg_button_oval_primary),
        FiturModel("Laporan Hasil Penyelidikan", R.drawable.bg_button_oval_primary),
        FiturModel("SKHD", R.drawable.bg_button_oval_primary),
        FiturModel("CATPERS", R.drawable.bg_button_oval_primary),
        FiturModel("SKHP", R.drawable.bg_button_oval_primary),
        FiturModel("REHAB", R.drawable.bg_button_oval_primary),
        FiturModel("WANJAK", R.drawable.bg_button_oval_primary),
        FiturModel("Laporan Bulanan", R.drawable.bg_button_oval_primary),
        FiturModel("Analisa", R.drawable.bg_button_oval_primary)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionmanager = activity?.let { SessionManager(it) }!!
        rv_fitur.setHasFixedSize(true)
        rv_fitur.layoutManager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)
        val adapter = FiturAdapter(activity!!, ArrayList(listFitur), this)
        rv_fitur.adapter = adapter



    }

    override fun onClick(position: Int) {
        if (listFitur[position].nameFitur == "Personel") {
            startActivity(Intent(activity, PersonelActivity::class.java))
//            startActivity(Intent(this, CatpersActivity::class.java))
        } else {
            Toast.makeText(activity, listFitur[position].nameFitur, Toast.LENGTH_SHORT).show()
        }
    }
}