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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.smb.smbapplication.AppExecutors
import com.smb.smbapplication.R
import com.smb.smbapplication.data.model.User
import com.smb.smbapplication.databinding.ItemBannerBinding
import com.smb.smbapplication.databinding.ItemBannerTitleBinding
import com.smb.smbapplication.databinding.ItemUserBinding
import com.smb.smbapplication.ui.BaseDataBindListAdapter
import com.smb.smbapplication.ui.BaseDataBindViewHolder
import java.text.FieldPosition

/**
 * A RecyclerView adapter for [Repo] class.
 */
class BannerListAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val itemClickCallback: ((User) -> Unit)?
) : BaseDataBindListAdapter<User, ItemBannerBinding>(
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

    override fun createBinding(parent: ViewGroup): ItemBannerBinding {
        val binding = DataBindingUtil.inflate<ItemBannerBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_banner,
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



     fun  getItemValue(position: Int):User{
        return getItem(position)
    }
    var images = arrayOf(R.drawable.banner1 , R.drawable.banner2 ,R.drawable.banner3 ,R.drawable.banner4 ,R.drawable.banner5)


    override fun onBindViewHolder(holder: BaseDataBindViewHolder<ItemBannerBinding>, position: Int) {
        holder.binding.name.setImageResource(images[position % currentList.size])
    }
    override fun bind(binding: ItemBannerBinding, item: User) {
        binding.user = item
    }
}
