package id.calocallo.sicape.ui.main.addpersonal.pekerjaan

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import androidx.fragment.app.FragmentTransaction
import id.calocallo.sicape.R
import id.co.iconpln.smartcity.ui.base.BaseActivity
import kotlinx.android.synthetic.main.layout_toolbar_white.*

class PekerjaanPersonelActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pekerjaan_personel)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Pekerjaan"

        setupActionBarWithBackButton(toolbar)

        val pekerjaanFrag = PekerjaanFragment().apply {
            enterTransition = Slide(Gravity.END)
            exitTransition = Slide(Gravity.START)
        }

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_pekerjaan, pekerjaanFrag)
        ft.commit()
    }
}