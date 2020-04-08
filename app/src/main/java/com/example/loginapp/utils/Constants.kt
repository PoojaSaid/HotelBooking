package com.GoMobeil.Perks.utils

import BookingService
import com.appvantis.perkserp.helper.RetrofitClient


//import com.google.firebase.iid.FirebaseInstanceId

class Constants {

    companion object {
        enum class TokenFrom { LOGIN, BASE }
        const val OTPBASEURL="http://api.textlocal.in/"
        //        const val BASE_URL = "https://api.perksapp.in"
      const val BASE_URL = "http://192.168.1.210:8080/api/"
//       const val BASE_URL = "http://192.168.0.109:8081/api/"
//       const val BASE_URL = "http://192.168.0.109:8080/api/"
//        const val BASE_URL = "http://devapi.perksapp.in/api/"
//        const val BASE_URL = "http://api.perksapp.in/api/"
//        const val BASE_URL = "https://208.109.10.70:8443/perkserp/api/"
//        const val BASE_URL = "https://development.dwellze.com:8443/perkserp/api/"

        fun getAPI(): BookingService = RetrofitClient.getClient(BASE_URL)!!.create(BookingService::class.java)
        fun getOTPAPI(): BookingService = RetrofitClient.getOTPClient(OTPBASEURL)!!.create(BookingService::class.java)

    }
}
