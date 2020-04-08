package com.GoMobeil.Perks.utils

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import java.text.DecimalFormat
import java.util.*


fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}


// logs
fun Context.debug(msg: String) {
    Log.d("Log:", msg)
}

// convert GSON object to JSON string
fun Context.convertToJson(obj: Any): JsonObject? = Gson().toJsonTree(obj).asJsonObject

// convert to GSON Array to JSON string
fun Context.convertToJsonArray(obj: List<Any>): JsonArray? = Gson().toJsonTree(obj).asJsonArray

fun convertStringToDouble(value:Any): Double { return value.toString().toDouble()}

//date picker
fun Context.datePicker(button:MaterialButton){
    var year:Int?=0
    var month:Int?=0
    var day:Int?=0

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        val myCalendar: Calendar = Calendar.getInstance()
         year = myCalendar.get(Calendar.YEAR)
         month = myCalendar.get(Calendar.MONTH)
         day = myCalendar.get(Calendar.DAY_OF_MONTH)
    }
    val picker = DatePickerDialog(this,
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            button.text=dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
        }, year!!, month!!, day!!
    )
//    picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000)
    picker.show()
}

//timepicekr
fun Context.timePicker(button: MaterialButton){
    val hr:Int=0
    val min:Int=0
    val timePickerListener =
        TimePickerDialog(this,
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minutes ->
            // TODO Auto-generated method stub
            button.setText(""+hourOfDay+" : "+minutes)
            Log.i("time",""+hourOfDay+" : "+minutes)
        },hr,min,false)
    timePickerListener.show()
}

fun Context.ProgressbarShow(msg:String){
    val progressDialog = ProgressDialog(
        this
    )
    progressDialog.isIndeterminate = true
    progressDialog.setMessage(msg)
    progressDialog.show()
}

fun Context.ProgressbarStop(){
    val progressDialog=ProgressDialog(
        this
    )
    progressDialog.dismiss()
}


// load Images
//fun ImageView.loadImage(context: Context, url: String?, defaultImage: Int, isBaseUrlAdded: Boolean = false) {
//    url?.let {
//        picasso
//                .load(Constants.PHOTO_IMAGE_URL.plus(url))
//                .placeholder(R.mipmap.ic_launcher)
//                .fit()
//                .error(R.drawable.ic_placeholder)
//                .into(this)
//    }
//
//}

//fun View.shakeErrorView() {
//    val shake = AnimationUtils.loadAnimation(this.context, R.anim.shake)
//    this.startAnimation(shake)
//}


//fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
//    val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
//    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
//    return layoutManager.getPosition(snapView)
//}


fun View.slideUp(duration: Int = 500) {
    visibility = View.VISIBLE
    val animate = TranslateAnimation(0f, 0f, this.height.toFloat(), 0f)
    animate.duration = duration.toLong()
    animate.fillAfter = true
    this.startAnimation(animate)
}

fun View.slideDown(duration: Int = 500) {
    visibility = View.VISIBLE
    val animate = TranslateAnimation(0f, 0f, 0f, this.height.toFloat())
    animate.duration = duration.toLong()
    animate.fillAfter = true
    this.startAnimation(animate)
}

enum class Orientation { CENTER, LEFT, RIGHT, TOP, BOTTOM }

//fun View.circularReveal(
//        orientation: List<Orientation> = listOf(Orientation.CENTER),
//        duration: Long = Duration.MEDIUM.toLong()
//) {
//
//    val x = when {
//        orientation.contains(Orientation.LEFT) -> 0
//        orientation.contains(Orientation.CENTER) -> right / 2
//        else -> right
//    }
//
//    val y = when {
//        orientation.contains(Orientation.TOP) -> 0
//        orientation.contains(Orientation.CENTER) -> bottom / 2
//        else -> bottom
//    }
//
//    val startRadius = 0
//    val endRadius = Math.hypot(
//            width.toDouble(),
//            height.toDouble()
//    ).toInt()
//
//    val anim = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//        ViewAnimationUtils.createCircularReveal(
//                this,
//                x,
//                y,
//                startRadius.toFloat(),
//                endRadius.toFloat()
//        )
//    } else {
//        TODO("VERSION.SDK_INT < LOLLIPOP")
//    }
//    anim.duration = duration
//    anim.start()
//
//}

fun EditText.clearText() {
    if (!this.text.isNullOrEmpty()) this.text = null
}

// Round off to 2 decimal places!
fun Float.roundOff(): String {
    val df = DecimalFormat("##.##")
    return df.format(this)
}

val Int.pxTodp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dpTopx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()