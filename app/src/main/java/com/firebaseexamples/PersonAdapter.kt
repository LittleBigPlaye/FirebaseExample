package com.firebaseexamples

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.media.Rating
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class PersonAdapter(context : Context, val layoutResId: Int, val personList: List<Person>) : ArrayAdapter<Person>(context, layoutResId, personList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(context);
        val view : View = layoutInflater.inflate(layoutResId, null);

        val textViewName = view.findViewById<TextView>(R.id.textViewName);
        val textViewUpdate = view.findViewById<TextView>(R.id.textViewUpdate);

        val person = personList[position]

        textViewName.text = person.name;

        textViewUpdate.setOnClickListener {
            showUpdateDialog(person);
        }

        return view;
    }

    fun showUpdateDialog(person : Person) {
        val builder = AlertDialog.Builder(context);
        builder.setTitle("Update Person");

        val inflater = LayoutInflater.from(context);

        val view = inflater.inflate(R.layout.layout_update_person, null);

        val editText = view.findViewById<EditText>(R.id.editTextName);
        editText.setText(person.name);

        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar);
        ratingBar.rating = person.rating.toFloat();

        builder.setView(view);

        builder.setPositiveButton("Update") { dialog, which ->
            val dbPerson = FirebaseDatabase.getInstance("https://fir-examples-f86c6-default-rtdb.firebaseio.com/").getReference("persons");
            val name : String = editText.text.toString().trim();
            if(name.isEmpty()) {
                editText.error = "Please enter a name!";
                editText.requestFocus();
                return@setPositiveButton;
            }
            val rating : Int = ratingBar.rating.toInt();

            val updatedPerson = Person(person.id, name, rating);
            dbPerson.child(person.id!!).setValue(updatedPerson).addOnCompleteListener{
                Toast.makeText(context,"Person has been updated", Toast.LENGTH_SHORT).show();
            };

        };

        builder.setNeutralButton("Delete") {dialog, which ->
            val dbPerson = FirebaseDatabase.getInstance("https://fir-examples-f86c6-default-rtdb.firebaseio.com/").getReference("persons");

            dbPerson.child(person.id!!).removeValue().addOnCompleteListener{
                Toast.makeText(context,"Person has been deleted", Toast.LENGTH_SHORT).show();
            };
        }

        builder.setNegativeButton("Cancel") { dialog, which ->

        };

        val alert = builder.create();
        alert.show();
    }



}