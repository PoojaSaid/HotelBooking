package com.example.loginapp

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
import kotlinx.android.synthetic.main.activity_fragment_booking.*
import kotlinx.android.synthetic.main.fragment_login.*

class bookingFragment : Fragment() {

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
        return inflater.inflate(R.layout.activity_fragment_booking, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btn_login.setOnClickListener {
            if ((et_checkInDate.text.toString() == "") && (et_roomCapacity.text.toString() == "") && (et_roomType.text.toString() == "")) {
                Toast.makeText(context, "Please give all requirement", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mDisposable.add(
                mService.createBooking(et_roomCapacity.,et_roomType.,et_checkInDate.text.toString())
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

}
