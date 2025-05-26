package com.example.sqlitedemo

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sqlitedemo.ui.theme.SQLiteDemoTheme

class MainActivity : AppCompatActivity() {
    lateinit var firstName: EditText
    lateinit var lastName: EditText
    lateinit var Age: EditText
    lateinit var switch: Switch
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var listview: ListView
    val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firstName = findViewById(R.id.textView1)
        lastName = findViewById(R.id.textView2)
        Age = findViewById(R.id.textView3)
        switch = findViewById(R.id.switch1)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        listview = findViewById(R.id.listView)


        val dataBaseHelper: DataBaseHelper = DataBaseHelper(application, "customer.db")
        var customers = dataBaseHelper.getAllCustomers()
        var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, customers)

        // val db: SQLiteDatabase = openOrCreateDatabase("customers.db", MODE_PRIVATE, null)
        button1.setOnClickListener {
            val customer = Customer(
                firstName.text.toString(),
                lastName.text.toString(),
                Age.text.toString().toIntOrNull(),
                switch.isChecked
            )
            dataBaseHelper.addCustomer(customer)

            Toast.makeText(this, "added customer", Toast.LENGTH_LONG).show()

        }

        button2.setOnClickListener {
            customers = dataBaseHelper.getAllCustomers()
            arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, customers)
            listview.adapter = arrayAdapter

            for (customer in customers) {
                Log.d(TAG, "Customer: " + customer.toString())
            }

        }

        val listener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val customer = arrayAdapter.getItem(position) as Customer
//            val deleted = dataBaseHelper.deleteCustomer(customer)
            dataBaseHelper.deleteCustomerFromId(customer.id)
            customers = dataBaseHelper.getAllCustomers()
            arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, customers)
            listview.adapter = arrayAdapter
            Toast.makeText(this, "deleted customer", Toast.LENGTH_LONG).show()
        }

        listview.onItemClickListener = listener

    }

}

