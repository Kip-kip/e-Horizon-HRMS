package stlhorizon.org.hrmselfservice.activities.Payslip

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import stlhorizon.org.hrmselfservice.R

class ProfileItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profileitem)

        val toeditpersonalinfo = findViewById<ImageView>(R.id.editpersonalinfo)

        toeditpersonalinfo.setOnClickListener {

            val intent= Intent(this,EditProfileActivity::class.java)
            startActivity(intent)
        }


    }
}
