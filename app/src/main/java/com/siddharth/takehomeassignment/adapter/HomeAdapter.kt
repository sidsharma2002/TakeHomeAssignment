package com.siddharth.takehomeassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.siddharth.takehomeassignment.R
import com.siddharth.takehomeassignment.constants.Constants.PROFILE_IMAGE
import com.siddharth.takehomeassignment.data.user.User

class HomeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var userList: MutableList<User> = mutableListOf()

    private class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tv_userName = itemView.findViewById<TextView>(R.id.tv_userName)
        private val tv_userDesc = itemView.findViewById<TextView>(R.id.tv_userDesc)
        private val iv_userPic = itemView.findViewById<ImageView>(R.id.iv_user)

        fun setData(holder: UserViewHolder, data: User) {
            holder.tv_userName.text = data.name
            holder.tv_userDesc.text = data.address.suite
            Glide.with(holder.itemView.context)
                .load(PROFILE_IMAGE)
                .circleCrop()
                .into(holder.iv_userPic)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = userList[holder.adapterPosition]
        (holder as UserViewHolder).setData(holder, user)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setUserList(userList: MutableList<User>) {
        this.userList = userList
    }
}