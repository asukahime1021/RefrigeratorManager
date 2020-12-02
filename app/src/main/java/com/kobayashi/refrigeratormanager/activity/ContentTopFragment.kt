package com.kobayashi.refrigeratormanager.activity

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kobayashi.refrigeratormanager.R
import com.kobayashi.refrigeratormanager.adapter.ItemListAdapter
import com.kobayashi.refrigeratormanager.configure.checkAndNotNull
import com.kobayashi.refrigeratormanager.entity.Items
import com.kobayashi.refrigeratormanager.model.RefrigeratorViewModel
import kotlinx.android.synthetic.main.fragment_content_top.view.*
import java.util.*

class ContentTopFragment : Fragment() {

    private val model : RefrigeratorViewModel by activityViewModels()

    private val genreMap: MutableMap<Int, Int> = mutableMapOf()
    private lateinit var itemsList: MutableList<Items>
    private lateinit var viewLayoutManager: RecyclerView.LayoutManager
    private lateinit var viewRecycleAdapter: ItemListAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_content_top, container, false)

        // ジャンルマップを初期化
        initGenreMap()

        // アイテムを表示するviewを取得
        val recycleView = view.findViewById<RecyclerView>(R.id.content_top_recycle)

        // アイテムリストの変更を監視
        model.itemsLive.observe(viewLifecycleOwner, Observer {
            recycleView.adapter = viewRecycleAdapter
            (recycleView.adapter as ItemListAdapter).notifyDataSetChanged()
        })

        // viewにレイアウトマネージャーを設定
        this.viewLayoutManager = LinearLayoutManager(activity as RefActivity)
        recycleView.layoutManager = this.viewLayoutManager

        // アイテムリストをDBから取得
        val items = model.itemsLive.value
        itemsList = checkAndNotNull(items){
            return@checkAndNotNull mutableListOf<Items>()
        }

        // viewのアダプターを設定
        this.viewRecycleAdapter = ItemListAdapter(genreMap, this.itemsList)

        // for test
        testItemList(itemsList)

        // アイテム登録ボタン
        view.toItemAddBtn.setOnClickListener {
            val item = Items()
            item.refId = model.masterRef.refId
            val action = ContentTopFragmentDirections.actionContentTopFragmentToContentItemDispFragment(item)
            findNavController().navigate(action)
        }

        // 検索ボタン
        view.toItemSearchBtn.setOnClickListener {
            val action = ContentTopFragmentDirections.actionContentTopFragmentToContentSearchFragment()
            findNavController().navigate(action)
        }

        // view内のアイテム
        viewRecycleAdapter.setOnClickListener(object: ItemListAdapter.ItemClickListener{
            override fun onItemClick(position: Int, item: Items) {
                val action = ContentTopFragmentDirections.actionContentTopFragmentToContentItemDispFragment(item)
                findNavController().navigate(action)
            }
        })
        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        model.itemsLive.observe(viewLifecycleOwner, Observer {newItems ->
//            itemsList.clear()
//            itemsList.addAll(newItems)
//        })
//    }

    /**
     * ジャンルマップに画像IDをマッピングします
     */
    fun initGenreMap(){
        genreMap[0] = R.drawable.illustrain04_kaden03
        genreMap[1] = R.drawable.illust01_vegetable
        genreMap[2] = R.drawable.illust02_niku_pack
        genreMap[3] = R.drawable.illust03_fish
        genreMap[4] = R.drawable.illust04_chomiryo
        genreMap[5] = R.drawable.illust05_drink
        genreMap[6] = R.drawable.illust06_okashi
        genreMap[7] = R.drawable.illust07_ryori
        genreMap[8] = R.drawable.illust08_kakofood
        genreMap[9] = R.drawable.illust09_freezefood
    }

    /**
     * デバッグ用初期アイテム生成
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun initItem(num: Int): Items{
        val item = Items()
        item.refId = model.masterRef.refId
        item.itemId = 0
        item.insDate = Date()
        item.doorId = 0
        item.genreId = num
        item.itemName = "sample$num"

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        item.expireDate = calendar.time

        return item
    }

    /**
     * デバッグ用の初期アイテムリスト
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun testItemList(itemsList: MutableList<Items>){
        repeat(2){
            itemsList.add(initItem(it))
        }

        model.itemsLive.value = itemsList
    }
}