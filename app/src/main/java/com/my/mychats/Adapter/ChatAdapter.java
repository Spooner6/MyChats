package com.my.mychats.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.my.mychats.Model.Chat;
import com.my.mychats.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context mContext;
    private List<Chat> mChat;
    private String imageUrl;
    FirebaseUser firebaseUser;

    public ChatAdapter (Context mContext, List<Chat> mChat, String imageUrl){
        this.mChat = mChat;
        this.mContext = mContext;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_RIGHT) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, parent, false);
            return new ChatAdapter.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, parent, false);
            return new ChatAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Chat chat = mChat.get(position);

        if(chat.getType().equals("text")){
            holder.showMessage.setText(chat.getMessage());

        }else
            Glide.with(mContext).load(chat.getMessage()).into(holder.sendImage);
        //System.out.println(mContext);

        if(imageUrl.equals("default")){
           holder.profileImage.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(mContext).load(imageUrl).into(holder.profileImage);
        }
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView showMessage;
        public ImageView profileImage;
        public ImageView sendImage;


        public ViewHolder(View itemView) {
            super(itemView);
            showMessage = itemView.findViewById(R.id.showMessage);
            profileImage = itemView.findViewById(R.id.profile_image);
            sendImage = itemView.findViewById(R.id.showimage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChat.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
          return MSG_TYPE_LEFT;
        }
    }
}
