package com.dengue_em_foco

import android.database.Cursor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dengue_em_foco.adapters.DengueNoticeAdapter

class DengueNoticesActivity : ComponentActivity(), DengueNoticeAdapter.OnDeleteClickListener {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var dengueNoticeAdapter: DengueNoticeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dengue_notices)

        dbHelper = DatabaseHelper(this)
        recyclerView = findViewById(R.id.recycler_view)

        val cursor: Cursor = dbHelper.getAllDengueNoticesWithUserNames()
        dengueNoticeAdapter = DengueNoticeAdapter(this, cursor, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = dengueNoticeAdapter
    }

    override fun onDeleteClick(uuid: String) {
        deleteDengueNoticeByUuid(uuid)
        dengueNoticeAdapter.swapCursor(dbHelper.getAllDengueNoticesWithUserNames())
    }

    private fun deleteDengueNoticeByUuid(uuid: String) {
        dbHelper.deleteDengueNoticeByUuid(uuid)
    }
}
