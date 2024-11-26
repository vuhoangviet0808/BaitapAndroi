package vn.edu.hust.studentman

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        val editName = findViewById<EditText>(R.id.edit_student_name)
        val editId = findViewById<EditText>(R.id.edit_student_id)
        val btnSave = findViewById<Button>(R.id.btn_save_student)
        val btnCancel = findViewById<Button>(R.id.btn_cancel_student)

        // Nhận dữ liệu từ Intent
        val studentName = intent.getStringExtra("studentName")
        val studentId = intent.getStringExtra("studentId")
        val position = intent.getIntExtra("position", -1)

        // Hiển thị dữ liệu sinh viên
        editName.setText(studentName)
        editId.setText(studentId)

        btnSave.setOnClickListener {
            val name = editName.text.toString()
            val id = editId.text.toString()

            if (name.isNotBlank() && id.isNotBlank()) {
                // Trả kết quả về MainActivity
                val intent = Intent()
                intent.putExtra("studentName", name)
                intent.putExtra("studentId", id)
                intent.putExtra("position", position)
                setResult(Activity.RESULT_OK, intent)
                finish()
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
