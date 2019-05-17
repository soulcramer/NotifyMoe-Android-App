package app.soulcramer.arn.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.soulcramer.arn.model.user.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsers(vararg users: User)

    @Update
    fun updateUsers(vararg users: User)

    @Delete
    fun deleteUsers(vararg users: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    fun loadById(userId: String): LiveData<User>

    @Query("SELECT * FROM users WHERE nickName = :nickname")
    fun loadByNick(nickname: String): LiveData<User>

}
