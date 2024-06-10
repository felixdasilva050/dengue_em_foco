package com.dengue_em_foco.adapters

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dengue_em_foco.DatabaseHelper
import com.dengue_em_foco.R

class DengueNoticeAdapter(
    private val context: Context,
    private val cursor: Cursor
) : RecyclerView.Adapter<DengueNoticeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dengue_notice, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (cursor.moveToPosition(position)) {
            holder.dateUpdateTextView.text = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE_UPDATE))
            holder.nameDistrictTextView.text = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME_DISTRICT))
            holder.casesTextView.text = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CASES)).toString()
            holder.nameUser.text = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)).toString()
        }
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateUpdateTextView: TextView = itemView.findViewById(R.id.date_update)
        val nameDistrictTextView: TextView = itemView.findViewById(R.id.name_district)
        val casesTextView: TextView = itemView.findViewById(R.id.cases)
        val nameUser: TextView = itemView.findViewById(R.id.user_name)
    }
}
