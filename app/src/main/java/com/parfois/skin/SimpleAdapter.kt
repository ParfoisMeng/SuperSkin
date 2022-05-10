package com.parfois.skin

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.parfois.skin.UISkin.bindSkinValue

class SimpleAdapter(data: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_simple, data) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tvItem, "This is an Item: $item")
        holder.getView<TextView>(R.id.tvItem).bindSkinValue(SkinConst.TITLE_TEXT_COLOR)
    }
}