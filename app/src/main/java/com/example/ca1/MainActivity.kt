package com.example.ca1

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var etBookId: EditText
    private lateinit var etBookTitle: EditText
    private lateinit var etBookAuthor: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var tvBooks: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = FirebaseDatabase.getInstance().getReference("Books")

        etBookId = findViewById(R.id.etBookId)
        etBookTitle = findViewById(R.id.etBookTitle)
        etBookAuthor = findViewById(R.id.etBookAuthor)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        tvBooks = findViewById(R.id.tvBooks)

        btnAdd.setOnClickListener {
            val id = etBookId.text.toString()
            val title = etBookTitle.text.toString()
            val author = etBookAuthor.text.toString()

            if (id.isNotEmpty() && title.isNotEmpty() && author.isNotEmpty()) {
                val book = Book(id, title, author)
                database.child(id).setValue(book)
                Toast.makeText(this, "Book Added", Toast.LENGTH_SHORT).show()
                etBookId.text.clear()
                etBookTitle.text.clear()
                etBookAuthor.text.clear()
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        btnView.setOnClickListener {
            database.get().addOnSuccessListener {
                val result = StringBuilder()
                for (data in it.children) {
                    val book = data.getValue(Book::class.java)
                    result.append("ID: ${book?.id}\n")
                    result.append("Title: ${book?.title}\n")
                    result.append("Author: ${book?.author}\n\n")
                }
                tvBooks.text = result.toString()
            }
        }
    }
}
