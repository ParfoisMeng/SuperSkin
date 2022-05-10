package com.parfois.skin

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import java.io.IOException
import java.io.InputStream

private typealias Skin = HashMap<SkinKey, String>
private fun skinOf(vararg pairs: Pair<SkinKey, String>) = hashMapOf(*pairs)

enum class SkinKey(key: String) {
    PAGE_BG("page_bg"),
    TITLE_BG_COLOR("title_bg"),
    TITLE_TEXT_COLOR("title_text_color"),
    VIEW_LINE_BG_COLOR("view_line_bg"),
    ONE_TEXT_COLOR("one_text_color"),
    TWO_TEXT_COLOR("two_text_color"),
    TWO_BG_COLOR("two_bg"),
    THREE_IMG("three_img"),
    SKIN_CHANGE_TEXT_COLOR("skin_change_text_color"),
    SKIN_CHANGE_BG_COLOR("skin_change_bg"),
}

object Skins {
    val SkinOne = skinOf(
        SkinKey.PAGE_BG to "bg_73.webp",
        SkinKey.TITLE_BG_COLOR to "#FFFFFF",
        SkinKey.TITLE_TEXT_COLOR to "#333333",
        SkinKey.VIEW_LINE_BG_COLOR to "#EEEEEE",
        SkinKey.ONE_TEXT_COLOR to "#000000",
        SkinKey.TWO_TEXT_COLOR to "#FFFFFF",
        SkinKey.TWO_BG_COLOR to "#000000",
        SkinKey.THREE_IMG to "9dTg44Qhx1Q.webp",
        SkinKey.SKIN_CHANGE_TEXT_COLOR to "#000000",
        SkinKey.SKIN_CHANGE_BG_COLOR to "#FFFFFF",
    )

    val SkinTwo = skinOf(
        SkinKey.PAGE_BG to "bg_115.webp",
        SkinKey.TITLE_BG_COLOR to "#000000",
        SkinKey.TITLE_TEXT_COLOR to "#DDDDDD",
        SkinKey.VIEW_LINE_BG_COLOR to "#111111",
        SkinKey.ONE_TEXT_COLOR to "#FFFFFF",
        SkinKey.TWO_TEXT_COLOR to "#000000",
        SkinKey.TWO_BG_COLOR to "#FFFFFF",
        SkinKey.THREE_IMG to "xJ2tjuUHD9M.webp",
        SkinKey.SKIN_CHANGE_TEXT_COLOR to "#FFFFFF",
        SkinKey.SKIN_CHANGE_BG_COLOR to "#000000",
    )
    val Default = SkinOne
}

object UISkin {
    private val currentSkin = MutableLiveData<Skin>()

    init {
        update(Skins.Default)
    }

    fun binding(act: FragmentActivity) {
        val views = act.window.decorView.getAllChildViews()

        currentSkin.observe(act) { skin ->
            for (view in views) {
                val keys = view.getTag(R.id.skin_tag)
                if (keys is Array<*> && keys.size > 0 && keys[0] is SkinKey) {
                    keys.forEach { key ->
                        val value = skin[key] ?: Skins.Default[key] ?: return@forEach
                        when (key) {
                            SkinKey.PAGE_BG -> {
                                view.setBackgroundDrawable(view.context.getAssetsDrawable(value))
                            }
                            SkinKey.TITLE_BG_COLOR, SkinKey.VIEW_LINE_BG_COLOR, SkinKey.TWO_BG_COLOR, SkinKey.SKIN_CHANGE_BG_COLOR -> {
                                view.setBackgroundColor(Color.parseColor(value))
                            }
                            SkinKey.TITLE_TEXT_COLOR, SkinKey.ONE_TEXT_COLOR, SkinKey.TWO_TEXT_COLOR, SkinKey.SKIN_CHANGE_TEXT_COLOR -> {
                                if (view is TextView) {
                                    view.setTextColor(Color.parseColor(value))
                                }
                            }
                            SkinKey.THREE_IMG -> {
                                if (view is ImageView) {
                                    view.setImageDrawable(view.context.getAssetsDrawable(value))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun update(skin: Skin) {
        currentSkin.postValue(skin)
    }

    private fun View.getAllChildViews(): List<View> {
        val views = arrayListOf<View>()
        views.add(this)
        if (this is ViewGroup) {
            for (i in 0..childCount) {
                getChildAt(i)?.let {
                    views.addAll(it.getAllChildViews())
                }
            }
        }
        return views
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

}

fun View.bindSkinValue(vararg key: SkinKey) {
    setTag(R.id.skin_tag, key)
}
