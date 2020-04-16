package com.example.todonotes

import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_add2.*

/**
 * A simple [Fragment] subclass.
 */
class Add : Fragment() {
    var id:Int?=0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add2, container, false)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater?.inflate(R.menu.add_but,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        id=arguments?.getInt("ID")
        if(id!! >0) {
            var title = arguments?.getString("Title")
            editText_title.setText(title)
            var desc = arguments?.getString("Description")
            editText_des.setText(desc)
        }
        add_but_from_addpage.setOnClickListener {

//            confirmDialogDemo(this.context!!)
            addEvent()
           // view!!.findNavController().navigate(R.id.home2)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId){
            R.id.home_id ->{
                view!!.findNavController().navigate(R.id.home2)
            }}
        return super.onOptionsItemSelected(item)
    }


//    private fun confirmDialogDemo(context: Context) {
//        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
//        builder.setTitle("  Add!  ")
//        builder.setMessage(" Are you sure to add this todo to your todos list")
//        builder.setCancelable(false)
//        builder.setPositiveButton("Yes",
//            DialogInterface.OnClickListener { dialog, which ->
//                addEvent()
//            })
//        builder.setNegativeButton("No",
//            DialogInterface.OnClickListener { dialog, which ->
//            })
//        builder.show()
//    }


    fun addEvent(){
        var title=editText_title.text.toString()
        var des=editText_des.text.toString()
        val values = ContentValues()
        values.put("Title",title)
        values.put("Description",des)
        if (values.get("Title")==""){

        }else{


        val dbManeger=DbManeger(this.activity!!)

        if (id!! >0) {
            var selectionArgs = arrayOf(id.toString())
            val id = dbManeger.update(values, "ID=?", selectionArgs)

            if (id > 0) {
                view!!.findNavController().navigate(R.id.home2)
            } else {
                Toast.makeText(this!!.activity!!, "fail to add Todo ", Toast.LENGTH_LONG).show()
            }
        }
        else{
            val id=dbManeger.insert(values)

            if(id>0){
                view!!.findNavController().navigate(R.id.home2)
            }else{
                Toast.makeText(this!!.activity!!,"fail to add Todo ",Toast.LENGTH_LONG).show()
            }
        }
    }
    }
}
