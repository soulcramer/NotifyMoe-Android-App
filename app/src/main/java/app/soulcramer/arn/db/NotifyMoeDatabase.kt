package app.soulcramer.arn.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.soulcramer.arn.vo.anime.Anime
import app.soulcramer.arn.vo.animelist.AnimeListItem
import app.soulcramer.arn.vo.user.NickToUser
import app.soulcramer.arn.vo.user.User

@Database(
    entities = [
        Anime::class,
        AnimeListItem::class,
        NickToUser::class,
        User::class
    ],
    version = 1
)
@TypeConverters(NotifyMoeTypeConverters::class)
abstract class NotifyMoeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun userDao(): UserDao
    abstract fun animeListDao(): AnimeListDao
}