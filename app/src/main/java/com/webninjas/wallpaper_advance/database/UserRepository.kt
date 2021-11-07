package com.webninjas.wallpaper_advance.database

import android.content.Context
import android.os.AsyncTask

class UserRepository(context: Context) {

    var db: Wallpaper_dao = mydatabase.getInstance(context)?.Wallpaper_dao()!!


    //Fetch All the Users
    fun getAllUsers(): List<Wallpaper_entity> {
        return db.getalllikes()
    }

    // Insert new user
    fun insertUser(Wallpaper_entity: Wallpaper_entity) {
        insertAsyncTask(db).execute(Wallpaper_entity)
    }


    // Delete user
    fun deleteUser(wallpaper_entity: Wallpaper_entity) {
        db.delete(wallpaper_entity)
    }

    private class insertAsyncTask internal constructor(private val usersDao: Wallpaper_dao) :
        AsyncTask<Wallpaper_entity, Void, Void>() {

        override fun doInBackground(vararg params: Wallpaper_entity): Void? {
            usersDao.add(params[0])
            return null
        }
    }
}