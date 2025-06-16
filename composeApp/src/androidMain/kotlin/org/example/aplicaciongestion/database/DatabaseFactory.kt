package com.example.aplicaciongestion.database

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

object DatabaseFactory {
    fun create(context: Context): AppDatabase {
        val driver = AndroidSqliteDriver(AppDatabase.Schema, context, "material.db")
        return AppDatabase(driver)
    }
}
