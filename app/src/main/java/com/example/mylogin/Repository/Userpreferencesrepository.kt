package com.example.mylogin.Repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class Userpreferencesrepository(private val dataStore: DataStore<androidx.datastore.preferences.core.Preferences>) {

    companion object {
        private val USER_ID_KEY = stringPreferencesKey("user_id")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val USER_LOGGED_IN_KEY = booleanPreferencesKey("user_logged_in")
    }

    val userLoggedInStateFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[USER_LOGGED_IN_KEY] ?: false
        }

    suspend fun setUserLoggedInState(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[USER_LOGGED_IN_KEY] = isLoggedIn
        }
    }

    suspend fun loginUser(userId: String, username: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
            preferences[USERNAME_KEY] = username
            preferences[USER_LOGGED_IN_KEY] = true
        }
    }

    suspend fun logoutUser() {
        dataStore.edit { preferences ->
            preferences.remove(USER_ID_KEY)
            preferences.remove(USERNAME_KEY)
            preferences[USER_LOGGED_IN_KEY] = false
        }
    }
}


