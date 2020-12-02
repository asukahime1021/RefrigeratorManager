package com.kobayashi.refrigeratormanager.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kobayashi.refrigeratormanager.configure.AppDatabase
import com.kobayashi.refrigeratormanager.configure.checkAndNotNull
import com.kobayashi.refrigeratormanager.entity.GenreMst
import com.kobayashi.refrigeratormanager.entity.Items
import com.kobayashi.refrigeratormanager.entity.Refrigerator
import com.kobayashi.refrigeratormanager.repository.GenreMstRepository
import com.kobayashi.refrigeratormanager.repository.ItemRepository
import com.kobayashi.refrigeratormanager.repository.RefrigeratorRepository
import kotlinx.coroutines.launch

class RefrigeratorViewModel(val app: Application): AndroidViewModel(app) {

    lateinit var masterRef : Refrigerator
    lateinit var genreMstList : List<GenreMst>
    val itemsLive = MutableLiveData<MutableList<Items>>()

    lateinit var itemsRepo: ItemRepository
    lateinit var refRepo: RefrigeratorRepository

    fun init(refName: String){
        // Refrigerator
        refRepo = RefrigeratorRepository(AppDatabase.getInstance(app).refDao())
        masterRef = checkAndNotNull(refRepo.getRefrigeratorByName(refName)) {
                val ref = Refrigerator()
                ref.refId = -1
                return@checkAndNotNull ref
            }

        // Items
        itemsRepo = ItemRepository(masterRef.refId, AppDatabase.getInstance(app).itemsDao())

        // GenreMst
        val genreMstRepo = GenreMstRepository(AppDatabase.getInstance(app).genreMstDao())
        genreMstList = genreMstRepo.selectAll()

        viewModelScope.launch {
            val itemsList = itemsRepo.getAllItems()
            itemsLive.value = itemsList
        }
    }

    fun updateItems(items: Items){
        viewModelScope.launch {
            itemsRepo.update(items)
        }
    }

    fun insertItems(items: Items){
        viewModelScope.launch {
            itemsRepo.insert(items)
        }
    }

    fun getItemsCount(itemsId: Int):Int{
        var count = 0
        viewModelScope.launch {
            count = itemsRepo.getCount(itemsId)
        }
        return count
    }

    fun isRefExists(refId: Int): Boolean{
        var flg = false
        viewModelScope.launch {
            if(refRepo.getRefrigeratorById(refId) != null){
                flg = true
            }
        }
        return flg
    }
}