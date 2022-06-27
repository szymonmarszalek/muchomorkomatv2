package com.example.muchomorkomat

import android.content.Context
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.squareup.picasso.Picasso

class resultView(context: Context, attrs: AttributeSet?): ConstraintLayout(context, attrs) {
    private val imageView: ImageView
    private val txtViewFirstResult: TextView


    init {
        inflate(context, R.layout.result, this)
        imageView= findViewById<ImageView>(R.id.image_view_result)
        txtViewFirstResult=findViewById<TextView>(R.id.txt_view_first_result)

    }

    fun setPhoto(string: Uri?){
        imageView.setImageURI(string)
    }
    fun setClassifiedResult(result:String?){
        txtViewFirstResult.text=(result)
    }}

