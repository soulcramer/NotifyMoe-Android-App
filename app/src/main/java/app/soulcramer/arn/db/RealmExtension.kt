@file:JvmName("RealmUtils")

// pretty name for utils class if called from Java
package app.soulcramer.arn.db

import com.zhuinden.monarchy.Monarchy

fun Monarchy.userDao(): UserDao = UserDao(this)
fun Monarchy.animeListDao(): AnimeListDao = AnimeListDao(this)
