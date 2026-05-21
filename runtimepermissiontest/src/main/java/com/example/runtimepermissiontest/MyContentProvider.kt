package com.example.runtimepermissiontest

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

class MyContentProvider : ContentProvider() {
//    private var dbHelper: MyContentProvider? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String? {
        TODO("Implement this to handle requests for the MIME type of the data at the given URI")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    // context?.let { dbHelper = MyContentProvider(it, "BookStore.db", 2) true } ?: false
    override fun onCreate() : Boolean{
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        TODO("Implement this to handle query requests from clients.")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}