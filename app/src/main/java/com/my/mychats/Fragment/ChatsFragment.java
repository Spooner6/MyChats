package com.my.mychats.Fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.my.mychats.Adapter.UserAdapter;
import com.my.mychats.Model.ChatList;
import com.my.mychats.Model.User;
import com.my.mychats.R;

import java.util.ArrayList;
import java.util.List;


public class ChatsFragment extends Fragment {

 private RecyclerView recyclerView;
 FirebaseUser firebaseUser;
 private UserAdapter userAdapter;
 private List<User> mUsers;

 DatabaseReference databaseReference;
 private List<ChatList> usersList;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                          Bundle saveInstaceState){

     View view = inflater.inflate(R.layout.fragment_chats, container, false);

     recyclerView = view.findViewById(R.id.recyclerView);
     recyclerView.setHasFixedSize(true);
     recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

     firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
     usersList = new ArrayList<>();

     databaseReference = FirebaseDatabase.getInstance().getReference("ChatList").child(firebaseUser.getUid());
     databaseReference.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             usersList.clear();
             for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                 ChatList chatList = dataSnapshot1.getValue(ChatList.class);
                 usersList.add(chatList);
             }
            chatList();
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });



     return view;
 }

    private void chatList() {
        mUsers = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot dataSnapshot2: dataSnapshot.getChildren()){
                    User user = dataSnapshot2.getValue(User.class);
                for(ChatList chatList: usersList){
                    if(user.getId().equals(chatList.getId())){
                        mUsers.add(user);
                        }
                    }
                }
            userAdapter = new UserAdapter(getContext(), mUsers);
            recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
