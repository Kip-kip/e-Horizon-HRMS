package stlhorizon.org.hrmselfservice.activities.Profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_personaldetails.*
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONException
import stlhorizon.org.hrmselfservice.R
import stlhorizon.org.hrmselfservice.model.user.Profile
import stlhorizon.org.hrmselfservice.utils.network.local.NetworkConnection
import stlhorizon.org.hrmselfservice.utils.network.local.OnReceivingResult
import stlhorizon.org.hrmselfservice.utils.network.local.RemoteResponse
import java.io.IOException

class DependantsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dependants)



    }

}
