package com.test.util

internal class Constants {

    interface Database {
        companion object {
            const val DATABASE_NAME = "login_kotlin_db"
            const val DATABASE_VERSION = 1
        }

        interface key {
            companion object {
                const val TOKEN = "token"
            }
        }
    }

    interface WebService{
        companion object {
            const val TOKEN = "token"
            const val CUSTOMER_ID = "id"
            const val CUSTOMER_NAME = "name"
            const val CUSTOMER_SEX = "sex"
            const val CUSTOMER_GRADE = "customerGrade"
            const val IS_CUSTOMER_PREMIUM = "isCustomerPremium"
        }
    }
}