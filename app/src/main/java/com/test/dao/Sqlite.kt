package com.test.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.test.model.CustomerModel
import com.test.util.Constants
import java.util.ArrayList

class Sqlite(context: Context?) {
    private var mHelper: DBHelper?
    private var mDatabase: SQLiteDatabase?
    private val TAG: String = "Sqlite"

    init {
        mHelper = DBHelper(context)
        mDatabase = mHelper?.writableDatabase
    }

    fun getAppValue(key: String): String? {
        var value: String? = null
        var mCursor: Cursor? = null
        try {
            mCursor = mDatabase?.rawQuery(
                "SELECT * FROM " + DBHelper.TABLE_APP_INFORMATION + " WHERE " + DBHelper.COLUMNS_APP_KEY + " = ? ",
                arrayOf(key)
            )
        } catch (e: SQLException) {
            Log.e(TAG, "getAppValue: ")
        } finally {
            mCursor?.close()
        }
        return value
    }

    fun insertAppValue(key: String?, value: String?): Long {
        var resultRow: Long = -1
        var cv: ContentValues? = null
        try {
            cv = ContentValues()
            cv.put(DBHelper.COLUMNS_APP_KEY, key)
            cv.put(DBHelper.COLUMNS_APP_VALUE, value)
            resultRow = mDatabase!!.insertWithOnConflict(
                DBHelper.Companion.TABLE_APP_INFORMATION,
                null,
                cv,
                SQLiteDatabase.CONFLICT_REPLACE
            )
        } catch (e: SQLException) {
            resultRow = -1
        } finally {
            cv?.clear()
        }
        return resultRow
    }

    fun deleteAppValue(key: String): Long {
        var result: Long = -1
        try {
            result = mDatabase!!.delete(
                DBHelper.Companion.TABLE_APP_INFORMATION,
                DBHelper.Companion.COLUMNS_APP_KEY + " = ? ",
                arrayOf(key)
            ).toLong()
        } catch (e: SQLException) {
            Log.e(TAG, "deleteData: " + e.message)
        }
        return result
    }

    fun getCustomerList(): List<CustomerModel> {
        val customerList = ArrayList<CustomerModel>()
        var mCursor: Cursor? = null
        try {
            mCursor = mDatabase!!.rawQuery(
                "SELECT * FROM " + DBHelper.Companion.TABLE_CUSTOMER_INFORMATION,
                null
            )
            if (mCursor != null && mCursor.moveToFirst()) {
                do {
                    val model = CustomerModel()
                    model.id =
                        mCursor.getString(mCursor.getColumnIndex(DBHelper.Companion.COLUMNS_CUSTOMER_ID))
                    model.name =
                        mCursor.getString(mCursor.getColumnIndex(DBHelper.Companion.COLUMNS_CUSTOMER_NAME))
                    customerList.add(model)
                } while (mCursor.moveToNext())
            }
        } catch (e: SQLException) {
            Log.e(TAG, "getCustomerList: " + e.message)
        } finally {
            mCursor?.close()
        }
        return customerList
    }

    fun insertCustomerInformation(id: String?, name: String?): Long {
        var resultRow: Long = -1
        var cv: ContentValues? = null
        try {
            cv = ContentValues()
            cv.put(DBHelper.COLUMNS_CUSTOMER_ID, id)
            cv.put(DBHelper.COLUMNS_CUSTOMER_NAME, name)
            resultRow = mDatabase!!.insertWithOnConflict(
                DBHelper.TABLE_CUSTOMER_INFORMATION,
                null,
                cv,
                SQLiteDatabase.CONFLICT_REPLACE
            )
        } catch (e: SQLException) {
            resultRow = -1
        } finally {
            cv?.clear()
        }
        return resultRow
    }

    class DBHelper(context: Context?) : SQLiteOpenHelper(
        context,
        Constants.Database.DATABASE_NAME,
        null,
        Constants.Database.DATABASE_VERSION
    ) {
        private val TAG: String = "DBHelper"

        companion object {
            const val TABLE_APP_INFORMATION = "APP_INFORMATION"
            const val TABLE_CUSTOMER_INFORMATION = "CUSTOMER_INFORMATION"

            const val COLUMNS_APP_KEY = "app_key"
            const val COLUMNS_APP_VALUE = "app_value"

            const val COLUMNS_CUSTOMER_ID = "customer_id"
            const val COLUMNS_CUSTOMER_NAME = "customer_name"

            const val CREATE_TABLE_APP_INFORMATION =
                "CREATE TABLE " + TABLE_APP_INFORMATION + " (" +
                        COLUMNS_APP_KEY + " TEXT(100) PRIMARY KEY, " +
                        COLUMNS_APP_VALUE + " TEXT(100));"

            const val CREATE_TABLE_CUSTOMER_INFORMATION =
                "CREATE TABLE " + TABLE_CUSTOMER_INFORMATION + " (" +
                        COLUMNS_CUSTOMER_ID + " TEXT(100) PRIMARY KEY, " +
                        COLUMNS_CUSTOMER_NAME + " TEXT(100));"
        }

        override fun onCreate(sqliteDB: SQLiteDatabase?) {
            Log.d(TAG, "onCreate: ")
            sqliteDB?.execSQL(CREATE_TABLE_APP_INFORMATION)
            sqliteDB?.execSQL(CREATE_TABLE_CUSTOMER_INFORMATION)
        }

        override fun onUpgrade(sqliteDB: SQLiteDatabase?, p1: Int, p2: Int) {
            Log.d(TAG, "onUpgrade: ")
        }

    }
}