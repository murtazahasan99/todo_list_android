package com.example.todonotes

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.BaseAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.note_template.view.*

/**
 * A simple [Fragment] subclass.
 */
class home : Fragment() {
    var todoList=ArrayList<NoteTemp>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        add_but_from_addpage.setOnClickListener {
            view!!.findNavController().navigate(R.id.add2)
        }
//        todoList.add(NoteTemp(1,"work out","you have to go to the gym"))
//        todoList.add(NoteTemp(1,"work out","you have to go to the gym"))
//        todoList.add(NoteTemp(1,"work out","you have to go to the gym"))
//        todoList.add(NoteTemp(1,"work out","you have to go to the gym"))
//        todoList.add(NoteTemp(1,"work out","you have to go to the gym"))
        querySersh("%")


    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

            inflater?.inflate(R.menu.home_but,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId){
            R.id.add_id ->{
                view!!.findNavController().navigate(R.id.add2)
            }}
        return super.onOptionsItemSelected(item)
    }

    inner class Myadabter:BaseAdapter{
        var todosList=ArrayList<NoteTemp>()
        var context:Context?=null
        constructor(todosList:ArrayList<NoteTemp>,context:Context){
            this.todosList=todosList
            this.context=context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myview=layoutInflater.inflate(R.layout.note_template,null)
            var todo=todosList[position]
            myview.textView2.text=todo.todoTitle
            myview.textView3.text=todo.todoDes
            myview.button_del.setOnClickListener {
                deletevent(todo,context!!)
            }
            myview.button_edit.setOnClickListener {
                editEvevt(todo)
            }
            return myview
        }

        override fun getItem(position: Int): Any {
            return todosList[position]
        }

        override fun getItemId(position: Int): Long {
            return  position.toLong()
        }

        override fun getCount(): Int {
        return todosList.size
        }
    }

    fun  querySersh(sersh:String){

        var db=DbManeger(this!!.activity!!)
        var projection= arrayOf("ID","Title","Description","Done")
        var selectionArg= arrayOf(sersh)

        val cursor=db.query(projection,"Title like ?",selectionArg,"ID")
        if (cursor.moveToFirst()) {
            todoList.clear()
            do {
                var id = cursor.getInt(cursor.getColumnIndex("ID"))
                var title = cursor.getString(cursor.getColumnIndex("Title"))
                var des = cursor.getString(cursor.getColumnIndex("Description"))
                todoList.add(NoteTemp(id, title, des))

            } while (cursor.moveToNext())
        }
            var myadabter=Myadabter(todoList,this.activity!!)
            list_view.adapter=myadabter

    }

    fun deletevent(todo: NoteTemp,context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("  Delete!  ")
        builder.setMessage(" Are you sure to delete this from your todos list")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialog, which ->
                var db=DbManeger(this.context!!)
                var selectionArgs= arrayOf(todo.todoId.toString())
                db.delet("ID=?",selectionArgs)
                querySersh("%")
            })
        builder.setNegativeButton("No",
            DialogInterface.OnClickListener { dialog, which ->
            })
        builder.show()
    }

    fun editEvevt(todo: NoteTemp) {
        var bundle=Bundle()
        bundle.putInt("ID", todo.todoId!!)
        bundle.putString("Title",todo.todoTitle)
        bundle.putString("Description",todo.todoDes)
        view!!.findNavController().navigate(R.id.add2,bundle)
    }
}



