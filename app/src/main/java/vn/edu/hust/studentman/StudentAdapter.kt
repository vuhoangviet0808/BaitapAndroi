package vn.edu.hust.studentman

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar


class StudentAdapter(var students: List<StudentModel>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
  class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
       parent, false)
    return StudentViewHolder(itemView)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId

    holder.imageEdit.setOnClickListener {
      val dialog = Dialog(holder.itemView.context)
      dialog.setContentView(R.layout.add_student_layout)

      // Lấy các thành phần từ layout
      val editName = dialog.findViewById<EditText>(R.id.s_name)
      val editID = dialog.findViewById<EditText>(R.id.id)

      // Hiển thị dữ liệu hiện tại của sinh viên
      editName.setText(student.studentName)
      editID.setText(student.studentId)

      dialog.findViewById<Button>(R.id.button_ok).setOnClickListener {
        // Cập nhật dữ liệu sinh viên
        val newName = editName.text.toString()
        val newID = editID.text.toString()

        if (newName.isNotBlank() && newID.isNotBlank()) {
          student.studentName = newName
          student.studentId = newID

          notifyItemChanged(position) // Thông báo Adapter cập nhật item
          dialog.dismiss()
        } else
          Toast.makeText(holder.itemView.context, "Name and ID cannot be empty", Toast.LENGTH_SHORT).show()
      }
      dialog.findViewById<Button>(R.id.button_cancel).setOnClickListener {
        dialog.dismiss()
      }

      dialog.window?.setLayout(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
      )
      dialog.show()
    }

    holder.imageRemove.setOnClickListener{
      val dialog = Dialog(holder.itemView.context)
      dialog.setContentView(R.layout.add_student_layout)

      val builder: AlertDialog.Builder = AlertDialog.Builder(holder.itemView.context)
      builder
        .setMessage("Remove Student")
        .setTitle("Are you want to remove this student")
        .setPositiveButton("Yes"){dialog, which ->
          val removedStudent = students[position]
          var mutableStudents = students.toMutableList()
          mutableStudents.removeAt(position)
          students = mutableStudents

          notifyItemRemoved(position)
          notifyItemChanged(position, students.size)
          Toast.makeText(holder.itemView.context, "Student removed", Toast.LENGTH_SHORT).show()

          Snackbar.make(holder.itemView, "${removedStudent.studentName} removed", Snackbar.LENGTH_LONG)
            .setAction("Undo"){
              val restoredStudents = students.toMutableList()
              restoredStudents.add(position, removedStudent)
              students = restoredStudents

              notifyItemInserted(position)
              notifyItemChanged(position, students.size)
            }.show()
          dialog.dismiss()
        }

        .setNegativeButton("No"){dialog, _->
          dialog.dismiss()
        }
        .create().show()

    }
  }
}