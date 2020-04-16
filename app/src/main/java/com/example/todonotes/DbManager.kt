package com.example.todonotes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DbManeger{
    val dbName="TodoNote"
    val dbTable="TodoFatima"
    val colId="ID"
    val colTitel="Title"
    val colDesc="Description"
    val colDone="Done"
    var dbVersion=1

    val sqlCreateTable= "CREATE TABLE IF NOT EXISTS "+dbTable+" ("+ colId +" INTEGER PRIMARY KEY,"+
            colTitel+" TEXT,"+ colDesc+" TEXT ,"+colDone +" INTEGER DEFAULT 0 );"
    var sqlDB: SQLiteDatabase?=null

    constructor(context: Context){
        var db=DatabaseHelper(context)
        sqlDB=db.writableDatabase

    }
    inner class DatabaseHelper: SQLiteOpenHelper {
        var context: Context?=null
        constructor(context: Context):super(context,dbName,null,dbVersion){
            this.context=context

        }

        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(context,"created table", Toast.LENGTH_LONG).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table IF EXISTS "+dbTable)
        }
    }


    fun insert(values: ContentValues):Long{
        val id =sqlDB!!.insert(dbTable,"",values)
        return id
    }

    fun query(projection:Array<String>,selection:String,selectionArg:Array<String>,SortOrder:String): Cursor {
        val db= SQLiteQueryBuilder()
        db.tables=dbTable
        val cursor=db.query(sqlDB,projection,selection,selectionArg,null,null,SortOrder)
        return cursor
    }

    fun delet(selection:String,selectionArgs:Array<String>):Int{
        var count =sqlDB!!.delete(dbTable,selection,selectionArgs)
        return count
    }
    fun update(values: ContentValues, selection:String, selectionArg: Array<String>):Int{
        var count=sqlDB!!.update(dbTable,values,selection,selectionArg)
        return count
    }
}