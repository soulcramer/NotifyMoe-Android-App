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
    fun insertUsers(vararg users: CachedUser)

    @Update
    fun updateUsers(vararg users: CachedUser)

    @Delete
    fun deleteUsers(vararg users: CachedUser)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun loadById(userId: String): CachedUser

    @Query("SELECT count(name) FROM users")
    suspend fun allUserCount(): Int
}
