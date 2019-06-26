package app.soulcramer.arn.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.soulcramer.arn.cache.model.CachedUser

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: CachedUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<CachedUser>)

    @Update
    suspend fun updateUsers(vararg users: CachedUser)

    @Delete
    suspend fun deleteUsers(vararg users: CachedUser)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun loadById(userId: String): CachedUser

    @Query("SELECT * FROM users ORDER BY nickname")
    suspend fun getAll(): List<CachedUser>

    @Query("SELECT * FROM users WHERE nickname LIKE '%'||:nickname||'%' ORDER BY nickname")
    suspend fun searchByNickname(nickname: String): List<CachedUser>

    @Query("SELECT count(nickname) FROM users")
    suspend fun allUserCount(): Int
}
