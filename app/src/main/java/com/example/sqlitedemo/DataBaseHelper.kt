package com.example.sqlitedemo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import androidx.annotation.Nullable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataBaseHelper(
    context: Context, database_name: String = "customer.db", factory:
    SQLiteDatabase.CursorFactory? = null, version: Int = 1
) :
    SQLiteOpenHelper(context, database_name, factory, version) {

    val CUSTOMER_TABLE = "customer_table"
    val CUSTOMER_ID = "customer_id"
    val CUSTOMER_FIRST_NAME = "customer_first_name"
    val CUSTOMER_LAST_NAME = "customer_last_name"
    val CUSTOMER_AGE = "customer_age"
    val CUSTOMER_ACTIVE = "customer_active"

    val myContext = context

    // this is callled first time you access a database object
    override fun onCreate(db: SQLiteDatabase?) {
        var createTableStatement: String =
            "CREATE TABLE $CUSTOMER_TABLE" +
                    "($CUSTOMER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$CUSTOMER_FIRST_NAME TEXT, " +
                    "$CUSTOMER_LAST_NAME TEXT, " +
                    "$CUSTOMER_AGE INTEGER, " +
                    "$CUSTOMER_ACTIVE BOOLEAN)"

        db?.execSQL(createTableStatement)
    }

    // whenever version number is changed it prevents previous
    // user apps from breaking when you change the database design
    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
//        TODO("Not yet implemented")
    }

    fun addCustomer(customer: Customer) {
        val db = this.writableDatabase
        val cv: ContentValues = ContentValues()
        cv.put(CUSTOMER_FIRST_NAME, customer.firstName)
        cv.put(CUSTOMER_LAST_NAME, customer.lastName)
        cv.put(CUSTOMER_AGE, customer.age)
        cv.put(CUSTOMER_ACTIVE, customer.isActive)
        db.insert(CUSTOMER_TABLE, null, cv)
    }

    fun getAllCustomers(): ArrayList<Customer> {
        val db = this.readableDatabase
        val selectStatement = "SELECT * FROM $CUSTOMER_TABLE"
        val cursor = db.rawQuery(selectStatement, null)
        val customerList = ArrayList<Customer>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val firstName = cursor.getString(1)
                val lastName = cursor.getString(2)
                val age = cursor.getInt(3)
                val isActive = cursor.getInt(4) == 1
                customerList.add(Customer(id, firstName, lastName, age, isActive))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return customerList
    }

    fun deleteCustomerFromId(id: Int) {
        val db = this.writableDatabase
        db.delete(CUSTOMER_TABLE, "$CUSTOMER_ID=?", arrayOf(id.toString()))
        db.close()

    }

    fun deleteCustomer(customer: Customer): Boolean {
        val db = this.writableDatabase
        val query = "DELETE FROM $CUSTOMER_TABLE WHERE $CUSTOMER_ID = " + customer.id
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            cursor.close()
            return true
        } else {
            cursor.close()
            return false
        }

    }


}