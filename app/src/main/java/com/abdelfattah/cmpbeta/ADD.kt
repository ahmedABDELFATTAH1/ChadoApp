package com.abdelfattah.cmpbeta

import android.content.ContentValues
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*
import java.text.SimpleDateFormat
import java.util.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase




class ADD : AppCompatActivity() {
    var b:Bundle?=null
    var dbManager:Dbmanger?=null
    var number:Int?=null
    var oldtitle:String?=null
    var olddes:String?=null
    var DONE:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        b=intent.extras
        number=-1
        try {
            number= b!!.getInt("NUMBER")
            oldtitle=b!!.getString("OLDTITLE")
            olddes=b!!.getString("OLDDIS")
            DONE=b!!.getString("DONE")
            addtiltle.text.append(oldtitle)
            Addtext.text.append(olddes)
        }catch (ex:Exception){}

        if(number!=-1)
        {
            ADDNOTEnow.text="EDIT NOTE"
        }

        dbManager=Dbmanger(this)
    }
   fun addnoteevent(view: View)
   {

       val database = FirebaseDatabase.getInstance()
       val myRef = database.getReference("message")

       myRef.setValue(addtiltle.text.toString())
       var title = addtiltle.text.toString()
       var desc = Addtext.text.toString()
       val dt = Date()
       val days = dt.date.toString()
       val hours = dt.hours.toString()
       val minutes = dt.minutes.toString()
       val curTime = days + " :: " + hours + " : " + minutes
       val sdf = SimpleDateFormat("EEEE")
       val dayOfTheWeek = sdf.format(dt)
       if(number==-1) {
           if (addtiltle.text.toString() != "" && Addtext.text.toString() != "") {

               var CV = ContentValues()
               CV.put("title", title)
               CV.put("DESCRIPTION", desc)
               CV.put("Date", dayOfTheWeek + " " + curTime)
               CV.put("DONE", "FALSE")
               dbManager!!.Insert(CV)
           } else {

               return
           }
       }
       else
       {
           dbManager!!.update(title,desc,oldtitle!!,DONE!!)

       }
       finish()
   }
}
