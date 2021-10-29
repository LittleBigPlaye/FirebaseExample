package com.firebaseexamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.*

class RealTimeDatabaseExample : AppCompatActivity() {

    lateinit var editText : EditText;
    lateinit var ratingBar: RatingBar;
    lateinit var listView: ListView;

    lateinit var button: Button;

    lateinit var personList: MutableList<Person>;

    lateinit var ref: DatabaseReference;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        personList = mutableListOf();

        val database = FirebaseDatabase.getInstance("https://fir-examples-f86c6-default-rtdb.firebaseio.com/");
        ref = database.getReference("persons");

        editText = findViewById<EditText>(R.id.editTextName);
        ratingBar = findViewById<RatingBar>(R.id.ratingBar);
        listView = findViewById<ListView>(R.id.listViewPersons);

        button = findViewById<Button>(R.id.btnSave);

        button.setOnClickListener {
            saveObject();
        }

        ref.addValueEventListener (object: ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                personList.clear()
                //check if there is data in the database
                if(p0!!.exists()) {
                    for(p in p0.children) {
                        val person = p.getValue(Person::class.java)
                        personList.add(person!!)
                    }
                    val adapter = PersonAdapter(this@RealTimeDatabaseExample, R.layout.persons, personList)
                    listView.adapter = adapter
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }

        })
    }

    private fun saveObject() {
        val name : String = editText.text.toString().trim()
        if(name.isEmpty()) {
            editText.error="Please enter a name!"
            return
        }
        val rating: Int = ratingBar.rating.toInt()

        val personId = ref.push().key

        val person = Person(personId, name, rating)
        ref.child(personId!!).setValue(person).addOnCompleteListener{
            Toast.makeText(this@RealTimeDatabaseExample, "Person saved successfully", Toast.LENGTH_SHORT).show()
        }

    }
}