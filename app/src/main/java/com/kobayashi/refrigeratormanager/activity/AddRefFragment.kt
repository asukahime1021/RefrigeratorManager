package com.kobayashi.refrigeratormanager.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kobayashi.refrigeratormanager.R
import com.kobayashi.refrigeratormanager.entity.Refrigerator
import com.kobayashi.refrigeratormanager.model.MainModel
import kotlinx.android.synthetic.main.fragment_main.*

class AddRefFragment : Fragment() {
    private val model by activityViewModels<MainModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val addRefBtn = view.findViewById<Button>(R.id.regist_ref)
        addRefBtn.setOnClickListener {
            onClickResistRef()
        }

        return view
    }

    private fun onClickResistRef(){
        val refName = editRefName.text.toString()
        if(isInvalidateRefName(refName))
            return

        val ref = Refrigerator()

        ref.refName = editRefName.text.toString()
        ref.doorCount = Integer.parseInt(doorSpinner.selectedItem.toString())
        ref.delFlg = false

        model.insert(ref)
        Toast.makeText(activity as MainActivity, "$refName を登録しました", Toast.LENGTH_LONG).show()

        val fragment = MainTopFragment()
        val fragmentManager = parentFragmentManager

        println("------------------------" + model.hashCode())
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    /**
     * 冷蔵庫名のバリデーション
     * 重複、文字長チェックを行います
     * 50文字以上もしくは空文字でアウト
     *
     * @param refName 入力された冷蔵庫名
     */
    private fun isInvalidateRefName(refName: String): Boolean {
        val validationText =
        if(refName.isBlank())
            "冷蔵庫名が空です"
        else if(refName.length > 50)
            "冷蔵庫名が長すぎます"
        else if(!model.checkRefByName(refName))
            "存在する冷蔵庫名です"
        else
            ""

        return if(validationText.isNotBlank()) {
            Toast.makeText(activity as MainActivity, validationText, Toast.LENGTH_LONG).show()
            true
        }else
            false
    }
}