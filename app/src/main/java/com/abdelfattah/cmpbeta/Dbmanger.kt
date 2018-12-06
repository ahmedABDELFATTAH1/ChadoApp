package com.abdelfattah.cmpbeta

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.ID
import android.widget.Toast

class Dbmanger:SQLiteOpenHelper {
    var dbtablee="Table"
    val sqlstring="create table Table_ahmedqqqq (ID INTEGER PRIMARY KEY AUTOINCREMENT,Title TEXT,DESCRIPTION TEXT,Date TEXT,DONE TEXT)"
    var contxt:Context?=null
    constructor(context:Context):super(context,"ahmedas.db",null,1)
    {
        this.contxt=context

    }
    override fun onCreate(db: SQLiteDatabase?) {
        //Toast.makeText(contxt,"TABLE IS CREATED ",Toast.LENGTH_SHORT).show()
        db!!.execSQL(sqlstring)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS Table_ahmedqqqq")
        onCreate(db)
    }
    fun Insert(contentValues:ContentValues)
    {
        var db:SQLiteDatabase=this.writableDatabase
        var test= db.insert("Table_ahmedqqqq",null,contentValues)
        if(test.toInt()!=-1) {

        }
        else{

        }

    }
    fun getalldata(tablename:String):Cursor
    {
        var db:SQLiteDatabase=this.writableDatabase
        var cur=db.rawQuery("select * from "+ tablename,null)
        return cur
    }

    fun deleteAll() {
        var db:SQLiteDatabase=this.writableDatabase
         db.delete("Table_ahmedqqqq",null,null);
        db.execSQL("delete from "+"Table_ahmedqqq");


    }
    fun delete(name:String)
    {
        var db:SQLiteDatabase=this.writableDatabase
        db.delete("Table_ahmedqqqq", "Title=?", arrayOf(name))

    }
    fun update(title:String,dis:String,oldtitle:String,done:String)    {

        var db:SQLiteDatabase=this.writableDatabase
        var contentValues=ContentValues()
        contentValues.put("title",title)
        contentValues.put("DESCRIPTION",dis)
        contentValues.put("DONE",done)
        db.update("Table_ahmedqqqq", contentValues, "Title = ?", arrayOf(oldtitle))

    }
    fun updatecheckbox(oldtitle:String,done:String)    {

        var db:SQLiteDatabase=this.writableDatabase
        var contentValues=ContentValues()
        contentValues.put("DONE",done)
        db.update("Table_ahmedqqqq", contentValues, "Title = ?", arrayOf(oldtitle))

    }
}
