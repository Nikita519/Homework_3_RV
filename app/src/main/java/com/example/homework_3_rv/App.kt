package com.example.homework_3_rv

import android.app.Application
import com.example.homework_3_rv.model.ContactService

class App : Application() {
    val contactService = ContactService()
}