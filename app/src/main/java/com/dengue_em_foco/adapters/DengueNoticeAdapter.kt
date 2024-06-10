package com.dengue_em_foco.adapters

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dengue_em_foco.DatabaseHelper
import com.dengue_em_foco.ProfileActivity
import com.dengue_em_foco.R

class DengueNoticeAdapter(
    private val context: Context,
    private var cursor: Cursor?,
    private val onDeleteClickListener: OnDeleteClickListener
) : RecyclerView.Adapter<DengueNoticeAdapter.ViewHolder>() {

    interface OnDeleteClickListener {
        fun onDeleteClick(uuid: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dengue_notice, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor?.let {
            if (it.moveToPosition(position)) {
                val uuid = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID))
                val name = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME))
                val cases = it.getInt(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CASES)).toString()
                val city = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME_DISTRICT))
                val date = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE_UPDATE))
                val geocode = it.getString(it.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GEOCODE_DISTRICT))
                holder.dateUpdateTextView.text = date
                holder.nameDistrictTextView.text = city
                holder.casesTextView.text = cases
                holder.nameUser.text = name

                holder.deleteButton.setOnClickListener {
                    onDeleteClickListener.onDeleteClick(uuid)
                }

                holder.itemView.setOnClickListener{
                    val profileIntent = Intent(context, ProfileActivity::class.java)
                    profileIntent.putExtra("uuid",uuid)
                    profileIntent.putExtra("name",name)
                    profileIntent.putExtra("cases",cases)
                    profileIntent.putExtra("city",city)
                    profileIntent.putExtra("date",date)
                    profileIntent.putExtra("geocode",geocode)

                    context.startActivity(profileIntent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    fun swapCursor(newCursor: Cursor?) {
        cursor?.close()
        cursor = newCursor
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateUpdateTextView: TextView = itemView.findViewById(R.id.date_update)
        val nameDistrictTextView: TextView = itemView.findViewById(R.id.name_district)
        val casesTextView: TextView = itemView.findViewById(R.id.cases)
        val nameUser: TextView = itemView.findViewById(R.id.user_name)
        val deleteButton: Button = itemView.findViewById(R.id.btn_item_delete)
    }
}
