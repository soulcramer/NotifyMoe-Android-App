package app.soulcramer.arn.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.soulcramer.arn.cache.model.CachedUser

@Database(
    entities = [
        CachedUser::class
    ],
    version = 1
)
@TypeConverters(NotifyMoeTypeConverters::class)
abstract class NotifyMoeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}