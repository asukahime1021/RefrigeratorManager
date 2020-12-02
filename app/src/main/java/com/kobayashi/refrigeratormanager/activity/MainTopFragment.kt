package com.kobayashi.refrigeratormanager.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.kobayashi.refrigeratormanager.R
import com.kobayashi.refrigeratormanager.entity.Refrigerator
import com.kobayashi.refrigeratormanager.model.MainModel
import kotlinx.android.synthetic.main.fragment_main_top.*

class MainTopFragment : Fragment() {
    private val model by activityViewModels<MainModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_top, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // デバッグ用
//        model.delete()
        initSpinner()

        // 冷蔵庫選択スピナー
        val list = mutableListOf<String>()
        ref_spinner.adapter = getAdapter(list)
        model.allRefs.observe(viewLifecycleOwner, Observer {refs ->
            list.clear()
            refs.stream().forEach { ref -> list.add(ref.refName) }
            ref_spinner.adapter = getAdapter(list)
        })
        setSpinner()

        // 冷蔵庫追加ボタン
        addRef.setOnClickListener {
            println("DEVCALL : addRef.CLICKED")
            onClickAddRef()
        }
    }

    private fun setSpinner(){
        ref_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            // 冷蔵庫選択
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                println("DEVCALL : item selected : position = $p2")

                if(p2 > 0) {
                    val intent = Intent(activity as MainActivity, RefActivity::class.java)
                    val refName = ref_spinner.selectedItem.toString()
                    println("DEVCALL : selected item = $refName")
                    intent.putExtra("refName", refName)
                    startActivity(intent)
                }
            }
        }
    }
    /**
     * 「冷蔵庫追加」ボタンタップ時の挙動
     * 「冷蔵庫追加」フラグメントに遷移します
     */
    private fun onClickAddRef(){
        val fragment = AddRefFragment()
        val fragmentManager = parentFragmentManager

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    /**
     * spinner用のアダプターを取得します
     */
    private fun getAdapter(list: MutableList<String>): SpinnerAdapter {
        val activity: MainActivity = activity as MainActivity
        return ArrayAdapter<String>(activity.applicationContext, android.R.layout.simple_spinner_dropdown_item, list)
    }

    /**
     * 初回起動時のスピナー選択セット
     */
    private fun initSpinner(){
        val count: Int = model.count()
        println("DEVCALL : REF_COUNT = $count")

        if(count > 0){
            println("DEVCALL : ref is not empty")
            return
        }

        val ref = Refrigerator()

        ref.refName = "選択してください"
        ref.doorCount = 0
        ref.delFlg = false
        ref.refId = 0

        model.insert(ref)
        println("DEVCALL : spinner is initialized")
    }
}