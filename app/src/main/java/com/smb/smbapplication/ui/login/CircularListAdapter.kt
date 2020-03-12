/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smb.smbapplication.ui.login

/**
 * Created by Shijil Kadambath on 03/08/2018
 * for NewAgeSMB
 * Email : shijil@newagesmb.com
 */

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.smb.smbapplication.AppExecutors
import com.smb.smbapplication.R
import com.smb.smbapplication.data.model.User
import com.smb.smbapplication.databinding.ItemBannerTitleBinding
import com.smb.smbapplication.databinding.ItemUserBinding
import com.smb.smbapplication.ui.BaseDataBindListAdapter
import com.smb.smbapplication.ui.BaseDataBindViewHolder
import com.smb.smbapplication.utils.logger.Log
import java.text.FieldPosition

/**
 * A RecyclerView adapter for [Repo] class.
 */
class CircularListAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val itemClickCallback: ((User) -> Unit)?
) : BaseDataBindListAdapter<User, ItemBannerTitleBinding>(
        appExecutors = appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
                        && oldItem.name .equals( newItem.name)
            }
        }
) {
    var titleSelectedPosition = 0

    fun pageSelected( position: Int){
        val old = titleSelectedPosition
        titleSelectedPosition = position
        notifyItemChanged(old)
        notifyItemChanged(titleSelectedPosition)
    }

    override fun createBinding(parent: ViewGroup): ItemBannerTitleBinding {
        val binding = DataBindingUtil.inflate<ItemBannerTitleBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_banner_title,
                parent,
                false,
                dataBindingComponent
        )
     /*   binding.root.setOnClickListener {
            binding.user?.let {
                itemClickCallback?.invoke(it)
            }
        }*/
        return binding
    }

    override fun getItemCount(): Int {
        //return super.getItemCount()
        if (currentList.isNullOrEmpty()){
            return  0
        }
        return  Integer.MAX_VALUE
    }

    override fun getItemId(position: Int): Long {
        Log.e("Circularrrr getItemId",position.toString() +" || "+currentList.size)
        return (position % currentList.size).toLong()
    }
    fun getCenterPage(position: Int = 0) = Integer.MAX_VALUE / 2 + position


    override fun getItem(position: Int): User {

       /* Log.e("Circularrrr getItem",position.toString() +" || "+currentList.size)

         var user=super.getItem(position).name
        Log.e("Circularrrr getItem",user.toString())*/


        return  currentList[position % currentList.size]

       // return super.getItem(position)
    }

    override fun onBindViewHolder(holder: BaseDataBindViewHolder<ItemBannerTitleBinding>, position: Int) {
        //bind(holder.binding, getItem(position))
        //holder.binding.user = getItem(position)
        holder.binding.name.setTextColor(if(position==titleSelectedPosition)Color.WHITE else Color.parseColor("#f9c1b9"))


        val typeface = ResourcesCompat.getFont( holder.binding.name.context, R.font.normal)
        val typefaceMedium = ResourcesCompat.getFont( holder.binding.name.context, R.font.medium)

       // holder.binding.name.setTextAppearance(if(position==titleSelectedPosition) android.R.style.TextAppearance_Large
                           // else android.R.style.TextAppearance_Small);
        holder.binding.name.typeface =(if(position==titleSelectedPosition) typefaceMedium else typeface);

        if(position==titleSelectedPosition){
            holder.binding.name.animateText( getItem(position).name)
        }else{
            holder.binding.name.text=( getItem(position).name)
        }



        holder.binding.executePendingBindings()
    }

    override fun bind(binding: ItemBannerTitleBinding, item: User) {

    }
}
