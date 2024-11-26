package vn.edu.hust.studentman

import StudentListAdapter
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
  private val REQUEST_CODE_ADD = 100
  private val REQUEST_CODE_EDIT = 101
  private val students = mutableListOf<StudentModel>()
  private lateinit var studentAdapter: StudentListAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val toolbar = findViewById<Toolbar>(R.id.toolbar)
    setSupportActionBar(toolbar)
    students.addAll(
      listOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
      )
    )

    val listView = findViewById<ListView>(R.id.listView)
    studentAdapter = StudentListAdapter(this, students)
    listView.adapter = studentAdapter

    registerForContextMenu(listView)


    listView.setOnItemClickListener { _, _, position, _ ->
      val student = students[position]
      Toast.makeText(this, "Selected: ${student.studentName} (${student.studentId})", Toast.LENGTH_SHORT).show()
    }
  }
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.action_add_new -> {
        val intent = Intent(this, AddStudentActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_ADD)
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.context_menu, menu) // Tải file context_menu.xml
  }
  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val position = info.position // Vị trí của mục trong danh sách

    when (item.itemId) {
      R.id.context_edit -> {
        // Mở Activity để chỉnh sửa thông tin sinh viên
        val student = students[position]
        val intent = Intent(this, EditStudentActivity::class.java)
        intent.putExtra("studentName", student.studentName)
        intent.putExtra("studentId", student.studentId)
        intent.putExtra("position", position)
        startActivityForResult(intent, REQUEST_CODE_EDIT)
        return true
      }

      R.id.context_remove -> {
        // Xóa sinh viên khỏi danh sách
        students.removeAt(position)
        studentAdapter.notifyDataSetChanged()
        Toast.makeText(this, "Student removed", Toast.LENGTH_SHORT).show()
        return true
      }
    }
    return super.onContextItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == REQUEST_CODE_ADD && resultCode == Activity.RESULT_OK) {
      val name = data?.getStringExtra("studentName")
      val id = data?.getStringExtra("studentId")

      if (name != null && id != null) {
        val newStudent = StudentModel(name, id)
        students.add(newStudent)
        studentAdapter.notifyDataSetChanged() // Cập nhật danh sách
      }
    } else if (requestCode == REQUEST_CODE_EDIT && resultCode == Activity.RESULT_OK) {
      val name = data?.getStringExtra("studentName")
      val id = data?.getStringExtra("studentId")
      val position = data?.getIntExtra("position", -1)

      if (name != null && id != null && position != null && position >= 0) {
        // Cập nhật thông tin sinh viên
        val student = students[position]
        student.studentName = name
        student.studentId = id
        studentAdapter.notifyDataSetChanged() // Cập nhật danh sách
        Toast.makeText(this, "Student updated", Toast.LENGTH_SHORT).show()
      }
    }
  }



}