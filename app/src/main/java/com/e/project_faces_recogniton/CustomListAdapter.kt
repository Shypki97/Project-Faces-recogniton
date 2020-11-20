package com.e.project_faces_recogniton

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.e.project_faces_recogniton.model.Event

class CustomListAdapter(
    context: Context?,
    eventList: List<Event>
) : BaseAdapter() {
    private val eventList: List<Event>
    private val layoutInflater: LayoutInflater
    private val context: Context?
    init {
        this.context = context;
        this.eventList = eventList
        this.layoutInflater = LayoutInflater.from(context)
    }
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup?): View {
        var view = view
        var holder:ViewHolder;
        if (view == null){
            view = layoutInflater.inflate(R.layout.list_view_layout, null)
            holder = ViewHolder()
            holder.locationView = view.findViewById(R.id.location)
            holder.dateView = view.findViewById(R.id.dateG)
            holder.people_nameView = view.findViewById(R.id.people_name)
            view.tag = holder
        } else {
            holder = view.getTag() as ViewHolder
        }
        val event = eventList[i]
        holder.locationView.setText(event.camera.location)
        holder.dateView.setText(event.timestamp)
        holder.people_nameView.setText(event.people.getName())
        return view!!

    }

    override fun getItem(p0: Int): Any {
       return eventList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return eventList.size
    }
}
internal class ViewHolder {
    lateinit var locationView: TextView
    lateinit var dateView: TextView
    lateinit var people_nameView: TextView
}