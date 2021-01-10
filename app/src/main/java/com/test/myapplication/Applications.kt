package com.test.myapplication

import android.app.Application
import android.content.Context
import com.test.dao.Sqlite

class Applications : Application() {

    companion object {
        private var sInstance: Applications? = null
        private var mDatabase: Sqlite? = null

        fun getAppContext(): Context? {
            return sInstance?.applicationContext
        }

        fun getDb(): Sqlite {
            if (mDatabase == null) {
                mDatabase = Sqlite(getAppContext())
            }
            return mDatabase as Sqlite
        }
    }

    override fun onCreate() {
        super.onCreate()
        sInstance = this
    }


}