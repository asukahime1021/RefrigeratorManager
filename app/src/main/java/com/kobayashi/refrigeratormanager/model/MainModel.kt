package com.kobayashi.refrigeratormanager.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kobayashi.refrigeratormanager.configure.AppDatabase
import com.kobayashi.refrigeratormanager.entity.GenreMst
import com.kobayashi.refrigeratormanager.entity.Refrigerator
import com.kobayashi.refrigeratormanager.repository.GenreMstRepository
import com.kobayashi.refrigeratormanager.repository.RefrigeratorRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * トップ画面用のViewModel
 * 冷蔵庫を取得,登録します
 */
class MainModel(app: Application): AndroidViewModel(app) {

    private val refrigeratorRepository: RefrigeratorRepository

    val allRefs: LiveData<List<Refrigerator>>

    init{
        val refrigeratorDao = AppDatabase.getInstance(app).refDao()
        refrigeratorRepository = RefrigeratorRepository(refrigeratorDao)
        allRefs = refrigeratorRepository.allRefs
    }

    fun insert(ref: Refrigerator) = viewModelScope.launch(Dispatchers.IO) {
        refrigeratorRepository.insert(ref)
    }

    fun delete() = viewModelScope.launch(Dispatchers.IO) {
        refrigeratorRepository.deleteAll()
    }

    fun count(): Int{
        return refrigeratorRepository.getRefCount()
    }

    fun checkRefByName(refName: String): Boolean{
        val ref = refrigeratorRepository.getRefrigeratorByName(refName)

        return ref == null
    }

}