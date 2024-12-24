package vn.edu.hust.roomexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vn.edu.hust.roomexample.databinding.ActivityAddSinhVienBinding

class AddSinhVienActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSinhVienBinding
    private lateinit var database: SinhVienDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSinhVienBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = SinhVienDatabase.getDatabase(this)

        binding.btnSave.setOnClickListener {
            val mssv = binding.etMssv.text.toString()
            val hoTen = binding.etHoTen.text.toString()
            val ngaySinh = binding.etNgaySinh.text.toString()
            val email = binding.etEmail.text.toString()

            val newSinhVien = SinhVien(mssv, hoTen, ngaySinh, email)
            CoroutineScope(Dispatchers.IO).launch {
                database.sinhVienDao().insert(newSinhVien)
                finish()
            }
        }
    }
}
