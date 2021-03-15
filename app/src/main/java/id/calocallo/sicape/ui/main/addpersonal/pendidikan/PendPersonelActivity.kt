package id.calocallo.sicape.ui.main.addpersonal.pendidikan

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.fragment.app.FragmentTransaction
import id.calocallo.sicape.R
import id.calocallo.sicape.ui.main.addpersonal.pendidikan.umum.PendidikanUmumFragment
import id.calocallo.sicape.ui.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar_white.*


class PendPersonelActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pend_personel)
        setupActionBarWithBackButton(toolbar)
        supportActionBar?.title = "Pendidikan"

        val pendUmumFrag = PendidikanUmumFragment()
            .apply {
                enterTransition = Slide(Gravity.END)
                exitTransition = Slide(Gravity.START)
            }
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_pendidikan, pendUmumFrag)
//        ft.addToBackStack(null)
        ft.commit()

    }

}

/*

        sheenValidator = SheenValidator(this)
        sheenValidator.registerAsRequired(edt_nama_umum_custom)
        sheenValidator.registerAsRequired(edt_thn_awal_umum_custom)
        sheenValidator.registerAsRequired(edt_thn_akhir_umum_custom)
        sheenValidator.registerAsRequired(edt_tempat_umum_custom)
        sheenValidator.registerAsRequired(edt_membiayai_umum_custom)
        sheenValidator.registerAsRequired(edt_ket_umum_custom)

        list = ArrayList()
        parent = ParentListPendUmum(list)

        btn_next_pend.setOnClickListener {
            Log.e("parent", parent.pendUmumList[0].nama_pend)
            Log.e("parent", parent.pendUmumList[1].nama_pend)
            Log.e("parent", parent.pendUmumList[2].nama_pend)
            sheenValidator.validate()

            startActivity(Intent(this, PendDinasActivity::class.java))
        }
                setAdapter()

         */

