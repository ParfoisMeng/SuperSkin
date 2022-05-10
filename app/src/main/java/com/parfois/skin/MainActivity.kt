package com.parfois.skin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.parfois.skin.UISkin.bindSkinValue
import com.parfois.skin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val actBinding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(actBinding.root)

        actBinding.clPage.bindSkinValue(SkinConst.PAGE_BG)
        actBinding.tvTitle.bindSkinValue(SkinConst.TITLE_BG_COLOR, SkinConst.TITLE_TEXT_COLOR)
        actBinding.tvOne.bindSkinValue(SkinConst.ONE_TEXT_COLOR)
        actBinding.btnTwo.bindSkinValue(SkinConst.TWO_TEXT_COLOR, SkinConst.TWO_BG_COLOR)
        actBinding.ivThree.bindSkinValue(SkinConst.THREE_IMG)
        actBinding.rvList.bindSkinValue(SkinConst.LIST_BG_COLOR)
        actBinding.btnSkinChange.bindSkinValue(
            SkinConst.SKIN_CHANGE_TEXT_COLOR,
            SkinConst.SKIN_CHANGE_BG_COLOR
        )
        UISkin.binding(this)

        val list = arrayListOf("111", "222", "333", "444", "555", "666")
        val adapter = SimpleAdapter(list)
        actBinding.rvList.adapter = adapter

        var count = 0
        actBinding.btnSkinChange.setOnClickListener {
            count++
            if (count % 2 == 0) {
                UISkin.change(SkinConst.SkinOne)
            } else {
                UISkin.change(SkinConst.SkinTwo)
            }
            adapter.addData("btnSkinChange $count")
        }
    }
}