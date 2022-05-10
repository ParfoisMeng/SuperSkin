package com.parfois.skin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.parfois.skin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.clPage.bindSkinValue(SkinKey.PAGE_BG)
        binding.tvTitle.bindSkinValue(SkinKey.TITLE_BG_COLOR, SkinKey.TITLE_TEXT_COLOR)
        binding.tvOne.bindSkinValue(SkinKey.ONE_TEXT_COLOR)
        binding.btnTwo.bindSkinValue(SkinKey.TWO_TEXT_COLOR, SkinKey.TWO_BG_COLOR)
        binding.ivThree.bindSkinValue(SkinKey.THREE_IMG)
        binding.btnSkinChange.bindSkinValue(
            SkinKey.SKIN_CHANGE_TEXT_COLOR,
            SkinKey.SKIN_CHANGE_BG_COLOR
        )
        UISkin.binding(this)

        var count = 0
        binding.btnSkinChange.setOnClickListener {
            count++
            if (count % 2 == 0) {
                UISkin.update(Skins.SkinOne)
            } else {
                UISkin.update(Skins.SkinTwo)
            }
        }
    }
}