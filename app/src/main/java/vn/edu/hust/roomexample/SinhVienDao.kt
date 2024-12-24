package vn.edu.hust.roomexample
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SinhVienDao {
  @Query("SELECT * FROM sinh_vien_table ORDER BY hoTen ASC")
  fun getAllSinhVien(): LiveData<List<SinhVien>>

  @Query("SELECT * FROM sinh_vien_table WHERE hoTen LIKE :query OR mssv LIKE :query")
  fun searchSinhVien(query: String): LiveData<List<SinhVien>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(sinhVien: SinhVien)

  @Delete
  suspend fun delete(sinhVien: SinhVien)

  @Query("DELETE FROM sinh_vien_table WHERE mssv IN (:mssvList)")
  suspend fun deleteByMssvList(mssvList: List<String>)

  @Update
  suspend fun update(sinhVien: SinhVien)
}
