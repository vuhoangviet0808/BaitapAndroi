package vn.edu.hust.studentman

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_student_layout)

        val editName = findViewById<EditText>(R.id.s_name)
        val editId = findViewById<EditText>(R.id.id)
        val btnSave = findViewById<Button>(R.id.button_ok)
        val btnCancel = findViewById<Button>(R.id.button_cancel)
        btnSave.setOnClickListener {
            val name = editName.text.toString()
            val id = editId.text.toString()

            if (name.isNotBlank() && id.isNotBlank()) {
                // Gửi kết quả về MainActivity
                val intent = Intent()
                intent.putExtra("studentName", name)
                intent.putExtra("studentId", id)
                setResult(Activity.RESULT_OK, intent)
                finish() // Đóng AddStudentActivity
            } else {
                Toast.makeText(this, "Name and ID cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
        btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}
