package backend.util.database;

import android.net.Uri;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.drifting.AddFriendActivity;
import com.example.drifting.HomeFragment;
import com.example.drifting.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import backend.util.time.DriftTime;

public class SetDatabase {
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public static Pair<String, String> parseName(String filename) {
        int i = filename.lastIndexOf('.');
        if (i > 0 && i < filename.length() - 1) {
            return new Pair<String, String>(filename.substring(0, i), filename.substring(i + 1));
        } else return null;
    }

    //add a new user to the database
    public void addNewUser(UserProfile userProfile) {
        //System.out.println(database == null);
        DatabaseReference usersRef = database.child("user");
        usersRef.child(userProfile.user_id).setValue(userProfile);
    }

    //add a new chat to the database
    public void addNewChat(Chat chat) {
        //System.out.println(database == null);
        DatabaseReference chatsRef = database.child("Chats");
        chatsRef.push().setValue(chat);
        Log.d("successful add ", "yay" + chat.receiver + chat.sender);
    }

    //add a new bottle to the database
    public void addNewBottle(Bottle_back this_bottle, Uri file) {
        DatabaseReference bottlesRef = database.child("bottle");

        if (this_bottle.ext != null) {
            StorageReference targetRef;

            if (!this_bottle.isVideo)
                targetRef = storageRef.child("picture/" + this_bottle.bottleID + ".jpg");
            else
                targetRef = storageRef.child("video/" + this_bottle.bottleID + ".mp4");

            UploadTask uploadTask = targetRef.putFile(file);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    targetRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUrl = uri;
                            //Log.d("eafawnvaw", uri.toString());


                            if (this_bottle.isVideo) this_bottle.video = uri.toString();
                            else this_bottle.picture = uri.toString();
                            //Do what you want with the url
                            bottlesRef.child(String.valueOf(this_bottle.bottleID)).setValue(this_bottle);
                        }

                    });
                }
            });
        } else {
            bottlesRef.child(String.valueOf(this_bottle.bottleID)).setValue(this_bottle);
        }

    }

    public void uploadAvatars(String user_id, Uri file) {
        StorageReference targetRef = storageRef.child("avatars/" + user_id + ".jpg");
        DatabaseReference targetdataRef = database.child("avatars/");

        UploadTask uploadTask = targetRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String user_id = auth.getUid();
                targetRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        targetdataRef.child(user_id + "/picture").setValue(uri.toString());
                        //Do what you want with the url
                    }

                });
            }
        });
    }


    public void uploadBottleFile(String path, String bottle_id, boolean isVideo) {
        Uri file = Uri.fromFile(new File(path));
        StorageReference targetRef = null;
        if (isVideo) {
            targetRef = storageRef.child("videos/" + bottle_id + "/" + file.getLastPathSegment());
        } else {
            targetRef = storageRef.child("images/" + bottle_id + "/" + file.getLastPathSegment());
        }
        UploadTask uploadTask = targetRef.putFile(file);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
    }

    public File downloadBottleFile(String bottle_id, String file_name, boolean isVideo) throws IOException {
        StorageReference targetRef = null;
        Pair<String, String> fileName = parseName(file_name);
        if (fileName == null) {
            return null;
        }
        if (isVideo) {
            targetRef = storageRef.child("videos/" + bottle_id + "/" + file_name);
        } else {
            targetRef = storageRef.child("images/" + bottle_id + "/" + file_name);
        }
        File localFile = File.createTempFile(bottle_id + fileName.first, fileName.second);
        targetRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Local temp file has been created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        return localFile;
    }

    public void get_sent_bottles(ArrayList<String> sentBottle, ArrayList<String>sentTime, ArrayList<String> sentLocation) {
        ArrayList<Bottle_back> sent_bottles = new ArrayList<Bottle_back>();
        //get current userID
        FirebaseAuth fAuth;
        DriftTime d_time = new DriftTime();
        fAuth = FirebaseAuth.getInstance();
        String userID = fAuth.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference user_ref = ref.child("user").child(userID).child("send_list");

        Object hm_obj = new Object();
        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getValue(hm_obj.getClass()) != null) {
                    HashMap<String, Boolean> hp = (HashMap) snapshot.getValue(hm_obj.getClass());
                    for (Map.Entry<String, Boolean> set : hp.entrySet()) {
                        //set.getKey() is the bottle id
                        //Log.d("HashMap: ","Key: "+ set.getKey() + " Val: " + set.getValue());
                        DatabaseReference bottle_ref = ref.child("bottle").child(set.getKey());
                        bottle_ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot_2) {
                                String msg = snapshot_2.child("message").getValue(String.class);
                                sentBottle.add(msg);
                                long time = snapshot_2.child("timestamp").getValue(Long.class);
                                sentTime.add(d_time.getDate(time));
                                String city = snapshot_2.child("city").getValue(String.class);
                                sentLocation.add(city);
                                bottle_ref.removeEventListener(this);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }

                user_ref.removeEventListener(this);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void get_picked_bottles(ArrayList<String> pickedBottle, ArrayList<String>pickedTime, ArrayList<String> pickedLocation) {
        //get current userID
        DriftTime d_time = new DriftTime();
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        String userID = fAuth.getUid();
        //Log.d("IDIDIDIDID", userID);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference user_ref = ref.child("user").child(userID).child("receive_list");

        Object hm_obj = new Object();



        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(hm_obj.getClass()) != null) {
                    HashMap<String, Boolean> hp = (HashMap) snapshot.getValue(hm_obj.getClass());
                    for (Map.Entry<String, Boolean> set : hp.entrySet()) {
                        if (set.getValue() == true) {
                            //set.getKey() is the bottle id
                            //Log.d("HashMap: ","Key: "+ set.getKey() + " Val: " + set.getValue());
                            DatabaseReference bottle_ref = ref.child("bottle").child(set.getKey());
                            bottle_ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot_2) {
                                    String msg = snapshot_2.child("message").getValue(String.class);
                                    pickedBottle.add(msg);
                                    long time = snapshot_2.child("timestamp").getValue(Long.class);
                                    pickedTime.add(d_time.getDate(time));
                                    String city = snapshot_2.child("city").getValue(String.class);
                                    pickedLocation.add(city);
                                    bottle_ref.removeEventListener(this);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                }
                user_ref.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    public void get_chat_info(ArrayList<String> name, ArrayList<String> message, ArrayList<Chat> chat_messages, ArrayList<String> Uer_id){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String currentUserID = mAuth.getCurrentUser().getUid();
        DatabaseReference ContacsRef = FirebaseDatabase.getInstance().getReference().child("Contacts").child(currentUserID).child("friend_list");

        //Get chat history from chatRef
        DatabaseReference ChatRef = FirebaseDatabase.getInstance().getReference().child("Chats");
        ChatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    Chat chat = (Chat) snapshot1.getValue(Chat.class);
                    Log.d(">>>>>>>>>>", chat.receiver + "...");
                    Log.d(">>>>>>>>>>", chat.message + "aaa");

                    if (chat.getReceiver().equals(currentUserID) || chat.getSender().equals(currentUserID)) {

                        String sender_id = snapshot1.child("sender").getValue().toString();
                        String receiver_id = snapshot1.child("receiver").getValue().toString();

                        //set the display name always to be others
                        String needed_id;
                        if(receiver_id.equals(currentUserID))  needed_id = sender_id ;
                        else    needed_id = receiver_id;

                        //need to get receiver's name
                        DatabaseReference friendRef = FirebaseDatabase.getInstance().getReference().child("user").child(needed_id);
                        friendRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot_) {
                                String this_name = snapshot_.child("user_name").getValue() != null ?
                                        snapshot_.child("user_name").getValue().toString() : "Default User";

                                //if the conversation exists already
                                if(name.contains(this_name)){
                                    int index = name.indexOf(this_name);
                                    message.set(index, snapshot1.child("message").getValue().toString());
                                    chat_messages.add(chat);
                                }

                                else {
                                    //if not repeated
                                    name.add(this_name);
                                    Uer_id.add(needed_id);
                                    message.add(snapshot1.child("message").getValue().toString());
                                    chat_messages.add(chat);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    } else {
                        continue;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void add_friend_info_text(String[] info, TextView [] text_render){
        //contact
        DatabaseReference ContactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");
        //chat
        DatabaseReference ChatRef = FirebaseDatabase.getInstance().getReference().child("Chats");


        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String current_user = fAuth.getUid();



        String fromUserID = HomeFragment.currBottle.userID;

        //retrieve user data from database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");
        DatabaseReference this_user_data = reference.child(fromUserID);
        this_user_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                info[0] = (snapshot.child("user_name").getValue() != null) ? snapshot.child("user_name").getValue().toString() : "unspecified";
                info[1] = (snapshot.child("user_gender").getValue() != null) ? snapshot.child("user_gender").getValue().toString() : "unspecified";
                info[2] = (snapshot.child("user_country").getValue() != null) ? snapshot.child("user_country").getValue().toString() : "unspecified";
                info[3] = (snapshot.child("user_age").getValue() != null) ? snapshot.child("user_age").getValue().toString() : "unspecified";
                info[4] = (snapshot.child("user_email").getValue() != null) ? snapshot.child("user_email").getValue().toString() : "unspecified";
                
                text_render[0].setText(info[0]);

                text_render[1].setText(info[1]);

                text_render[2].setText(info[2]);

                text_render[3].setText(info[3]);

                text_render[4].setText(info[4]);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(AddFriendActivity.this, "Failed to retrieve user's data :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void add_friend_avatar(ImageView profileImage){
        DatabaseReference avatarRef = FirebaseDatabase.getInstance().getReference("avatars/");
        String fromUserID = HomeFragment.currBottle.userID;
        avatarRef = avatarRef.child(fromUserID);
        avatarRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ss : snapshot.getChildren()) {
                    String url = ss.getValue(String.class);
                    Picasso.get().load(url).into(profileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(AddFriendActivity.this, "Failed to retrieve user's avatar :(", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
