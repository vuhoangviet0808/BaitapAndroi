package vn.edu.hust.roomexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.edu.hust.roomexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var adapter: SinhVienAdapter
  private lateinit var database: SinhVienDatabase

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Khởi tạo Room Database
    database = SinhVienDatabase.getDatabase(this)

    insertSampleData()

    // Thiết lập RecyclerView
    adapter = SinhVienAdapter(
      onItemClicked = { sinhVien ->
        val intent = Intent(this, SinhVienDetailActivity::class.java)
        intent.putExtra("mssv", sinhVien.mssv)
        startActivity(intent)
      },
      onItemChecked = { sinhVien, isChecked ->

      }
    )

    binding.recyclerView.apply {
      layoutManager = LinearLayoutManager(this@MainActivity)
      adapter = this@MainActivity.adapter
    }


    database.sinhVienDao().getAllSinhVien().observe(this) { sinhVienList ->
      adapter.submitList(sinhVienList)
    }

    binding.etSearch.addTextChangedListener { text ->
      val query = text.toString()
      database.sinhVienDao().searchSinhVien("%$query%").observe(this) { filteredList ->
        adapter.submitList(filteredList)
      }
    }

    // Thêm sinh viên mới
    binding.fabAddSinhVien.setOnClickListener {
      val intent = Intent(this, AddSinhVienActivity::class.java)
      startActivity(intent)
    }

    binding.btnDeleteSelected.setOnClickListener {
      val selectedSinhVien = adapter.getSelectedItems()
      CoroutineScope(Dispatchers.IO).launch {
        database.sinhVienDao().deleteByMssvList(selectedSinhVien.map { it.mssv })
      }
    }
  }

  private fun insertSampleData() {
    val sampleStudents = listOf(
      SinhVien(mssv = "10001", hoTen = "Nguyen Van A", ngaySinh = "01/01/2000", email = "a@gmail.com"),
      SinhVien(mssv = "10002", hoTen = "Tran Thi B", ngaySinh = "02/02/2000", email = "b@gmail.com"),
      SinhVien(mssv = "10003", hoTen = "Le Thi C", ngaySinh = "03/03/2001", email = "c@gmail.com"),
      SinhVien(mssv = "10004", hoTen = "Pham Van D", ngaySinh = "04/04/2002", email = "d@gmail.com"),
      SinhVien(mssv = "10005", hoTen = "Hoang Van E", ngaySinh = "05/05/2000", email = "e@gmail.com") ,
      SinhVien(mssv = "10006", hoTen = "Hoang Van E", ngaySinh = "05/05/2000", email = "e@gmail.com"),
      SinhVien(mssv = "10007", hoTen = "Hoang Van E", ngaySinh = "05/05/2000", email = "e@gmail.com"),
      SinhVien(mssv = "10008", hoTen = "Hoang Van E", ngaySinh = "05/05/2000", email = "e@gmail.com")
    )
    CoroutineScope(Dispatchers.IO).launch {
      sampleStudents.forEach { database.sinhVienDao().insert(it) }
    }
  }
}
