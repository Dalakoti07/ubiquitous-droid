package com.example.ubiquitousdroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.ubiquitousdroid.R
import com.example.ubiquitousdroid.models.contactObject

class phoneContactAdapter(private val context: Context, private val contactModelArrayList: ArrayList<contactObject>) : BaseAdapter() {

    override fun getViewTypeCount(): Int {
        return count
    }

    override fun getItemViewType(position: Int): Int {

        return position
    }

    override fun getCount(): Int {
        return contactModelArrayList.size
    }

    override fun getItem(position: Int): Any {
        return contactModelArrayList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.item_phone_contact, null, true)

            holder.tvname = convertView!!.findViewById(R.id.name) as TextView
            holder.tvnumber = convertView.findViewById(R.id.number) as TextView

            convertView.tag = holder
        } else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = convertView.tag as ViewHolder
        }

        holder.tvname!!.setText(contactModelArrayList[position].name)
        holder.tvnumber!!.setText(contactModelArrayList[position].number)

        return convertView
    }

    private inner class ViewHolder {

        var tvname: TextView? = null
        var tvnumber: TextView? = null

    }
}