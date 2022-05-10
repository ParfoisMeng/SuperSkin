package com.parfois.skin

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import java.io.IOException
import java.io.InputStream
import java.util.*

object UISkin {
    private val currentSkin = MutableLiveData(SkinConst.Default)

    fun binding(owner: LifecycleOwner) {
        currentSkin.observe(owner) { skin ->
            skinViewMap.forEach { it.key.setViewSkins(skin, it.value) }
        }
    }

    private fun View.setViewSkins(skin: Skin, keys: Array<out String>) {
        if (keys.isNotEmpty()) {
            for (key in keys) {
                val value = skin[key] ?: SkinConst.Default[key] ?: break
                when (key) {
                    SkinConst.PAGE_BG -> {
                        setBackgroundDrawable(context.getAssetsDrawable(value))
                    }
                    SkinConst.TITLE_BG_COLOR, SkinConst.VIEW_LINE_BG_COLOR, SkinConst.TWO_BG_COLOR, SkinConst.LIST_BG_COLOR, SkinConst.SKIN_CHANGE_BG_COLOR -> {
                        setBackgroundColor(Color.parseColor(value))
                    }
                    SkinConst.TITLE_TEXT_COLOR, SkinConst.ONE_TEXT_COLOR, SkinConst.TWO_TEXT_COLOR, SkinConst.SKIN_CHANGE_TEXT_COLOR -> {
                        if (this is TextView) {
                            setTextColor(Color.parseColor(value))
                        }
                    }
                    SkinConst.THREE_IMG -> {
                        if (this is ImageView) {
                            setImageDrawable(context.getAssetsDrawable(value))
                        }
                    }
                }
            }
        }
    }

    fun change(skin: Skin) {
        currentSkin.postValue(skin)
    }

    private fun Context.getAssetsDrawable(fileName: String): Drawable? {
        var open: InputStream? = null
        var drawable: Drawable? = null
        try {
            open = assets.open(fileName)
            drawable = Drawable.createFromStream(open, null)
//            drawable = BitmapDrawable(BitmapFactory.decodeStream(open))
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                open?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return drawable
    }

    private val skinViewMap = WeakHashMap<View, Array<out String>>()

    fun View.bindSkinValue(vararg key: String) {
        // 应用当前皮肤
        currentSkin.value?.let { setViewSkins(it, key) }
        // 加入皮肤管理 Map
        skinViewMap[this] = key
    }
}

object SkinConst {
    const val PAGE_BG = "page_bg"
    const val TITLE_BG_COLOR = "title_bg"
    const val TITLE_TEXT_COLOR = "title_text_color"
    const val VIEW_LINE_BG_COLOR = "view_line_bg"
    const val ONE_TEXT_COLOR = "one_text_color"
    const val TWO_TEXT_COLOR = "two_text_color"
    const val TWO_BG_COLOR = "two_bg"
    const val THREE_IMG = "three_img"
    const val LIST_BG_COLOR = "list_bg_color"
    const val SKIN_CHANGE_TEXT_COLOR = "skin_change_text_color"
    const val SKIN_CHANGE_BG_COLOR = "skin_change_bg"

    val SkinOne: Skin = hashMapOf(
        PAGE_BG to "bg_73.webp",
        TITLE_BG_COLOR to "#FFFFFF",
        TITLE_TEXT_COLOR to "#333333",
        VIEW_LINE_BG_COLOR to "#EEEEEE",
        ONE_TEXT_COLOR to "#000000",
        TWO_TEXT_COLOR to "#FFFFFF",
        TWO_BG_COLOR to "#000000",
        THREE_IMG to "9dTg44Qhx1Q.webp",
        LIST_BG_COLOR to "#3399EE",
        SKIN_CHANGE_TEXT_COLOR to "#000000",
        SKIN_CHANGE_BG_COLOR to "#FFFFFF",
    )

    val SkinTwo: Skin = hashMapOf(
        PAGE_BG to "bg_115.webp",
        TITLE_BG_COLOR to "#000000",
        TITLE_TEXT_COLOR to "#DDDDDD",
        VIEW_LINE_BG_COLOR to "#111111",
        ONE_TEXT_COLOR to "#FFFFFF",
        TWO_TEXT_COLOR to "#000000",
        TWO_BG_COLOR to "#FFFFFF",
        THREE_IMG to "xJ2tjuUHD9M.webp",
        LIST_BG_COLOR to "#EE9933",
        SKIN_CHANGE_TEXT_COLOR to "#FFFFFF",
        SKIN_CHANGE_BG_COLOR to "#000000",
    )
    val Default: Skin = SkinOne
}

private typealias Skin = HashMap<String, String>