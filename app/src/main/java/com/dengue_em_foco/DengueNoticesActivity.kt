package com.dengue_em_foco

import android.database.Cursor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dengue_em_foco.R
import com.dengue_em_foco.adapters.DengueNoticeAdapter

class DengueNoticesActivity : ComponentActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var dengueNoticeAdapter: DengueNoticeAdapter
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dengue_notices)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recycler_view)

        userId = intent.getStringExtra("USER_ID") ?: ""
        if (userId.isNotEmpty()) {
            val cursor: Cursor = dbHelper.getDengueNoticesByUser(userId)
            dengueNoticeAdapter = DengueNoticeAdapter(this, cursor)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = dengueNoticeAdapter
        }
    }
}
