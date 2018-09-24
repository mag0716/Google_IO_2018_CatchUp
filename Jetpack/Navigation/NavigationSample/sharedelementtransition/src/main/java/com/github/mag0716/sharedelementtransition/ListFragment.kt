package com.github.mag0716.sharedelementtransition

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.apply {
            val items = listOf<Item>(
                    Item("Red", ContextCompat.getColor(context, R.color.red500)),
                    Item("Pink", ContextCompat.getColor(context, R.color.pink500)),
                    Item("Purple", ContextCompat.getColor(context, R.color.purple500)),
                    Item("Deep Purple", ContextCompat.getColor(context, R.color.deepPurple500)),
                    Item("Indigo", ContextCompat.getColor(context, R.color.indigo500)),
                    Item("Blue", ContextCompat.getColor(context, R.color.blue500)),
                    Item("Light Blue", ContextCompat.getColor(context, R.color.lightBlue500)),
                    Item("Cyan", ContextCompat.getColor(context, R.color.cyan500)),
                    Item("Teal", ContextCompat.getColor(context, R.color.teal500)),
                    Item("Green", ContextCompat.getColor(context, R.color.green500)),
                    Item("Light Green", ContextCompat.getColor(context, R.color.lightGreen500)),
                    Item("Lime", ContextCompat.getColor(context, R.color.lime500)),
                    Item("Yellow", ContextCompat.getColor(context, R.color.yellow500)),
                    Item("Amber", ContextCompat.getColor(context, R.color.amber500)),
                    Item("Orange", ContextCompat.getColor(context, R.color.orange500)),
                    Item("Deep Orange", ContextCompat.getColor(context, R.color.deepOrange500)),
                    Item("Brown", ContextCompat.getColor(context, R.color.brown500)),
                    Item("Gray", ContextCompat.getColor(context, R.color.gray500)),
                    Item("Blue Gray", ContextCompat.getColor(context, R.color.blueGray500))
            )
            layoutManager = LinearLayoutManager(context)
            adapter = Adapter(items)
        }
    }

    private inner class Adapter(val items: List<Item>) : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.view.setBackgroundColor(items[position].color)
            holder.textView.text = items[position].name
            val transitionName = items[position].name
            ViewCompat.setTransitionName(holder.view, transitionName)
            holder.root.setOnClickListener {
                val action = ListFragmentDirections.actionMoveToDetail()
                action.setItem(items[position])
                val extras = FragmentNavigatorExtras(
                        holder.view to transitionName
                )
                it.findNavController().navigate(action, extras)
            }
        }

    }

    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val root: View = itemView.rootView
        val view: View = itemView.findViewById(R.id.view)
        val textView: TextView = itemView.findViewById(R.id.text)

    }
}