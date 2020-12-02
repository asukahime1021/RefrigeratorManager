package com.kobayashi.refrigeratormanager.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.kobayashi.refrigeratormanager.R
import com.kobayashi.refrigeratormanager.configure.appendDateText
import com.kobayashi.refrigeratormanager.entity.Items
import com.kobayashi.refrigeratormanager.model.RefrigeratorViewModel
import kotlinx.android.synthetic.main.fragment_content_item_disp.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ContentItemDispFragment : Fragment() {

    private val model: RefrigeratorViewModel by activityViewModels()

    private val args: ContentItemDispFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_content_item_disp, container, false)
    }

    /**
     * view作成後に値を挿入する
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 検索画面（トップ含む)から遷移してきた場合、items情報を取得する
        val item: Items = args.item

        // アイテム名（空の場合は空文字）
        view.display_itemName.setText(checkNotNull(item.itemName){""})

        // 期限（空の場合は設定しない）
        if(item.expireDate != null){
            val calendar = Calendar.getInstance()
            calendar.time = item.expireDate!!
            view.display_empireYear.setText(calendar.get(Calendar.YEAR).toString())
            view.display_empireMonth.setText((calendar.get(Calendar.MONTH) + 1).toString())
            view.display_empireDay.setText(calendar.get(Calendar.DAY_OF_MONTH).toString())
        }

        // ドアナンバー、ジャンルナンバーをスピナーの選択値に設定
        view.display_door.setSelection(item.doorId)
        view.display_door.adapter = ArrayAdapter(activity as RefActivity, android.R.layout.simple_spinner_dropdown_item, getDoorList())

        // ジャンルIDを再計算
        view.display_genreSpinner.setSelection(reCalcGenreId(item.genreId))

        // 残量シークバーの初期値を設定
        view.display_remainSeekBar.progress = item.remainAmount

        // 自由メモを設定
        view.display_memo.setText(item.memo)

        // 戻るボタン（トップ画面へ遷移）
        view.display_backBtn.setOnClickListener {
            toTop(view)
        }

        // 登録ボタン
        view.display_resistBtn.setOnClickListener {
            val validationResult = validateItem(view)
            if(validationResult.isEmpty()){
                createItems(item, view)
            }else{
                var toastString = ""
                validationResult.forEach{
                    toastString = toastString.plus(it).plus("\r\n")
                }

                Toast.makeText(this.context, toastString.trim(), Toast.LENGTH_LONG).show()
            }
            Toast.makeText(this.context, "登録したよ", Toast.LENGTH_LONG).show()
            toTop(view)
        }

    }

    /**
     * マスター冷蔵庫のドアナンバーの数の要素を持つリストを返却
     */
    private fun getDoorList(): ArrayList<String>{
        val doorList = ArrayList<String>()
        val doorCount = model.masterRef.doorCount
        repeat(doorCount){
            doorList.add(it.toString())
        }

        return doorList
    }

    /**
     * ジャンルIDをスピナーリストに合わせて再計算します
     */
    private fun reCalcGenreId(genreId: Int): Int = if(genreId == 0) 9 else genreId - 1

    /**
     * 登録用アイテムオブジェクトを作成します
     */
    private fun createItems(item: Items, view: View){
        item.itemName = view.display_itemName.text.toString()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN)
        item.expireDate = dateFormat.parse(
            appendDateText(
                view.display_empireYear.text.toString(),
                view.display_empireMonth.text.toString(),
                view.display_empireDay.text.toString()
        ))

        item.doorId = (view.display_door.selectedItem as String).toInt()
        item.genreId = (view.display_genreSpinner.selectedItem as String).substring(0, 1).toInt()
        item.remainAmount = view.display_remainSeekBar.progress
        item.memo = view.display_memo.text.toString()

        item.delFlg = false

        if(item.insDate == null) {
            item.insDate = Date()
        }
        item.updDate = Date()

        val flg = model.isRefExists(model.masterRef.refId)
        if(model.getItemsCount(item.itemId) > 0){
            model.updateItems(item)
        }else{
            item.itemId = 0
            model.insertItems(item)
        }
    }

    /**
     * 入力内容のバリデーションチェック
     */
    private fun validateItem(view: View): List<String>{
        val validationResult = mutableListOf<String>()
        if(view.display_itemName.equals("")){
            validationResult.add("アイテム名は必須項目です")
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.JAPAN)
        dateFormat.isLenient = false
        try{

            val dateString = appendDateText(view.display_empireYear.text.toString(),
                view.display_empireMonth.text.toString(),
                view.display_empireDay.text.toString()
            )

            dateFormat.parse(dateString)
        }catch (e: ParseException){
            validationResult.add("期限日が存在しない日付です")
        }

        return validationResult
    }

    fun toTop(view: View){
        view.findNavController().navigate(R.id.action_contentItemDispFragment_to_contentTopFragment)
    }
}