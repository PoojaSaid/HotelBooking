package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.android.synthetic.main.activity_add_room.*
import kotlinx.android.synthetic.main.activity_fragment_booking.*
import kotlinx.android.synthetic.main.fragment_login.*
import org.json.JSONObject

class addRoomFragment : Fragment() {

    private val mService by lazy { Constants.getAPI() }
    private val mDisposable = CompositeDisposable()
    override fun onStop() {
        super.onStop()
        mDisposable.clear()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.activity_add_room, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_roomCreate.setOnClickListener {

            if ((sp_roomType.selectedItem.toString() == "") && (et_noOfGuest.text.toString() == "")) {
                Toast.makeText(context, "Please Enter number of guest and type of room", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mDisposable.add(
                mService.createRoom(et_noOfGuest.text.toString().toInt(),sp_roomType.selectedItem.toString())
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
                    "Enter valid room credentials",
                    TastyToast.LENGTH_SHORT,
                    TastyToast.ERROR
                )
            } else {
                val jsonData = json.getString("DATA")
                val dataObject = JSONObject(jsonData)
                var  noOfGuest = dataObject.get("r_capacity")
                var roomType = dataObject.getString("r_type")
                var startDate = dataObject.getString("r_startdate")
                var endDate = dataObject.getString("r_enddate")
                var roomId = dataObject.getString("r_id")
                var status = dataObject.getString("r_status")
                startActivity(Intent(context, HomeActivity::class.java))
                //finish()
            }
        }

    }
}
