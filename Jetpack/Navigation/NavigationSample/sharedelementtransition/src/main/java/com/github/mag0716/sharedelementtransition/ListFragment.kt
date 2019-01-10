package com.github.mag0716.sharedelementtransition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_list.*
import androidx.core.util.Pair as UtilPair

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
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            adapter = Adapter(items)
        }
    }

    private inner class Adapter(val items: List<Item>) : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {
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
                // to Fragment
//                val action = ListFragmentDirections.actionMoveToDetailFragment()
//                action.setItem(items[position])
//                val extras = FragmentNavigatorExtras(
//                        holder.view to transitionName
//                )
//                it.findNavController().navigate(action, extras)

                // to Activity
                val action = ListFragmentDirections.actionMoveToDetailActivity()
                action.setItem(items[position])
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        checkNotNull(activity),
                        UtilPair.create(holder.view, transitionName)
                )
                val extras = ActivityNavigator.Extras.Builder().setActivityOptions(options).build()
                it.findNavController().navigate(action, extras)
            }
        }

    }

    private class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        val root: View = itemView.rootView
        val view: View = itemView.findViewById(R.id.view)
        val textView: TextView = itemView.findViewById(R.id.text)

    }
}