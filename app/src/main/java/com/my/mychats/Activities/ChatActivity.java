package com.my.mychats.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.my.mychats.Adapter.ChatAdapter;
import com.my.mychats.Model.Chat;
import com.my.mychats.Model.User;
import com.my.mychats.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {


    CircleImageView profileImage;
    ImageView chatImage;
    private static final int CHOOSE_IMAGE = 1;
    TextView username;
    FirebaseUser firebaseUser;

    DatabaseReference reference;
    StorageReference storageReference;
    StorageTask storageTask;

    Intent intent;
    String userId;
    ImageButton sendText, loadPicture;
    EditText messageText;

    ChatAdapter chatAdapter;
    List<Chat> mChat;

    private Uri imgUri;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendText = findViewById(R.id.btn_send);
        loadPicture = findViewById(R.id.btn_loadPicture);
        messageText = findViewById(R.id.text_send);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        reference = FirebaseDatabase.getInstance().getReference("uploads");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        profileImage = findViewById(R.id.profilImage);
        username = findViewById(R.id.username);
        recyclerView =findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        chatImage = findViewById(R.id.showimage);

        intent = getIntent();
        userId = intent.getStringExtra("userid");

        final String userId = intent.getStringExtra("userid");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                username.setText(user.getUsername());
                if(user.getImageURL().equals("default")){
                    profileImage.setImageResource(R.mipmap.ic_launcher);
                }else {
                    Glide.with(ChatActivity.this).load(user.getImageURL()).into(profileImage);
                }

               readMessage(firebaseUser.getUid(), userId, user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        loadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChoose();

            }
        });

        sendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = messageText.getText().toString();

                if (!msg.equals("")){
                    sendMessage(firebaseUser.getUid(), userId, msg);
                }
                messageText.setText("");

                if(storageTask != null && storageTask.isInProgress()){
                    Toast.makeText(ChatActivity.this, "Upload in progress", Toast.LENGTH_LONG).show();
                }else {
                    uploadImage(firebaseUser.getUid(), userId);
                }
            }
        });


        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, ProfileActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, userId);
                startActivity(intent);

            }
        });

    }

 /*   public void sendNotifications(String sender, String receiver, String body){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("title", sender);
        hashMap.put("body", body);

        reference.child("notification").push().setValue(hashMap);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://fcm.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRetrofit api = retrofit.create(ApiRetrofit.class);

        Call<ResponseBody> call = api.sendNotification(sender, receiver, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }*/


    public void showFileChoose(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){

            imgUri = data.getData();

            userId = intent.getStringExtra("userid");
            final String userId = intent.getStringExtra("userid");

           // Picasso.with(this).load(imgUri).into(imageViewSendPhoto);
            Toast.makeText(ChatActivity.this, "Image is Ready to send", Toast.LENGTH_LONG).show();

        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(final String sender,final String receiver){
        if (imgUri != null){
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()+ "."+ getFileExtension(imgUri));

            storageTask = fileReference.putFile(imgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // uploadProgress.setProgress(0);
                        }
                    },500);
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String dataUri = uri.toString();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                            String type = "photo";

                            HashMap<String, Object> hashMap = new HashMap<>();

                            hashMap.put("sender",sender);
                            hashMap.put("receiver", receiver);
                            hashMap.put("message",dataUri);
                            hashMap.put("type", type);

                            reference.child("chats").push().setValue(hashMap);
                        }
                    });

                    Toast.makeText(ChatActivity.this, "Upload sucesfully", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ChatActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }



    private void sendMessage(final String sender, final String receiver, String messager){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        String type = "text";
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("sender",sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", messager);
        hashMap.put("type", type);

        reference.child("chats").push().setValue(hashMap);

        final DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("ChatList")
                .child(firebaseUser.getUid())
                .child(userId);

        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()){
                    reference1.child("id").setValue(userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void readMessage(final String myId, final String userId, final String imageURL){
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId) ||
                    chat.getReceiver().equals(userId) && chat.getSender().equals(myId)){
                        mChat.add(chat);
                    }
                    chatAdapter = new ChatAdapter(ChatActivity.this, mChat, imageURL);
                    recyclerView.setAdapter(chatAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
