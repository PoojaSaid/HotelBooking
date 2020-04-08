package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.GoMobeil.Perks.utils.Constants
import com.GoMobeil.Perks.utils.ProgressbarStop
import com.sdsmdg.tastytoast.TastyToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {
    private val mService by lazy { Constants.getAPI() }
    private val mDisposable = CompositeDisposable()
    override fun onStop() {
        super.onStop()
        mDisposable.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_login.setOnClickListener {
            if ((et_email.text.toString() == "") && (et_email.text.toString() == "")) {
                Toast.makeText(context, "Please Enter id and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mDisposable.add(
                mService.login(et_email.text.toString(), et_email.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ s ->
                        parseResponse(s.toString())
                        Log.i("resposne", s.toString())
                    }, {
                        context!!.ProgressbarStop()
                        TastyToast.makeText(
                            activity,
                            "No Response Received",
                            TastyToast.LENGTH_SHORT,
                            TastyToast.WARNING
                        )
                    })

            )
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun parseResponse(response: String) {
        val json = JSONObject(response)

        if (json.has("STATUS")) {
            var message = "";
            if (json.has("MESSAGE")) {
                message = json.getString("MESSAGE")
            }
            if (json.getString("STATUS").equals("FAILED") || json.getString(
                    "STATUS"
                ).equals("FAILURE")
            ) {
                TastyToast.makeText(
                    context,
                    "Enter valid credentials",
                    TastyToast.LENGTH_SHORT,
                    TastyToast.ERROR
                )
            } else {
                val jsonData = json.getString("DATA")
                val dataObject = JSONObject(jsonData)
                var firstName = dataObject.get("cust_firstname")
                var lastName = dataObject.getString("cust_lastname")
                var email = dataObject.getString("cust_email")
                var mobile = dataObject.getString("cust_mobile")
                var custId = dataObject.getString("cust_id")
                startActivity(Intent(context, HomeActivity::class.java))
                //finish()
            }
        }

    }
}