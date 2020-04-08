package com.example.loginapp

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.GoMobeil.Perks.utils.Constants
import com.GoMobeil.Perks.utils.ProgressbarStop
import com.sdsmdg.tastytoast.TastyToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_login.et_email
import kotlinx.android.synthetic.main.fragment_register.*
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {
    private val mService by lazy { Constants.getAPI() }
    private val mDisposable = CompositeDisposable()
    override fun onStop() {
        super.onStop()
        mDisposable.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_register.setOnClickListener {
            if ((et_firstName.text.toString() == "" ) && (et_lastName.text.toString() == "") && (et_email.text.toString() == "") && (et_mobileNumber.text.toString()) == "") {
                Toast.makeText(
                    context,
                    "Please Enter all credentials for successful registration.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            mDisposable.add(
                mService.register(
                    et_firstName.text.toString(),
                    et_lastName.text.toString(),
                    et_email.text.toString(),
                    et_mobileNumber.text.toString()
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ s ->
                        parseResponse(s.toString())
                        Log.i("RESPONSE", s.toString())

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
            var message = ""
            if (json.has("MESSAGE")) {
                message = json.getString("MESSAGE")
            }
            if (json.getString("STATUS").equals("FAILED")) {
                TastyToast.makeText(
                    context,
                    "Enter all credentials for successful registration",
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

            }
        }

    }

}