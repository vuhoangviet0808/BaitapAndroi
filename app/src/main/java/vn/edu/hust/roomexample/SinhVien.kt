package vn.edu.hust.roomexample

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sinh_vien_table")
data class SinhVien(
  @PrimaryKey val mssv: String,
  val hoTen: String,
  val ngaySinh: String,
  val email: String
)

