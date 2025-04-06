package com.example.travelmanager.factory

import androidx.lifecycle.ViewModel
import com.example.travelmanager.dao.UserDao
import com.example.travelmanager.data.LoginUserViewModel

class LoginUserViewModelFactory(
    private val userDao: UserDao
):androidx.lifecycle.ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginUserViewModel(userDao) as T
    }
}