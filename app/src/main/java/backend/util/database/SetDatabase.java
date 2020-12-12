package backend.util.database;

import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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

    public void getProfile(Consumer<UserProfile> callback) {
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("user").child(auth.getUid());
        UserRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile userProfile = snapshot.getValue(UserProfile.class);
                if (userProfile.user_name == null) Log.d("ababaab", "nullString!!!!");
                userProfile.user_name = userProfile.user_name != null ? userProfile.user_name : "unspecified";
                userProfile.user_gender = userProfile.user_gender != null ? userProfile.user_gender : "unspecified";
                userProfile.user_country = userProfile.user_country != null ? userProfile.user_country : "unspecified";
                userProfile.age = userProfile.age != null ? userProfile.age : "unspecified";
                userProfile.user_email = userProfile.user_email != null ? userProfile.user_email : "unspecified";
                userProfile.privacy = userProfile.privacy != null ? userProfile.privacy : "unspecified";
                callback.accept(userProfile);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

    //upload the avatar to the database
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

    public void getAvatar(Consumer<String> callback) {
        String user_id = auth.getUid();
        DatabaseReference avatarRef = FirebaseDatabase.getInstance().getReference("avatars/");
        avatarRef = avatarRef.child(user_id);
        avatarRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ss : snapshot.getChildren()) {
                    String url = ss.getValue(String.class);
                    callback.accept(url);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // get sent bottles for the bag
    public void get_sent_bottles(ArrayList<Bottle_back> sentBottle) {
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
                        DatabaseReference bottle_ref = ref.child("bottle");
                        bottle_ref.orderByChild("bottleID").equalTo(set.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                    Bottle_back this_bottle = snapshot1.getValue(Bottle_back.class);

                                    if (this_bottle == null) {
                                        return;
                                    }

                                    sentBottle.add(this_bottle);
                                }
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

    //get picked bottles for the bag
    public void get_picked_bottles(ArrayList<Bottle_back> pickedBottle) {
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
                if (snapshot.getValue(hm_obj.getClass()) != null) {
                    HashMap<String, Boolean> hp = (HashMap) snapshot.getValue(hm_obj.getClass());
                    for (Map.Entry<String, Boolean> set : hp.entrySet()) {
                        if (set.getValue() == true) {
                            //set.getKey() is the bottle id
                            //Log.d("HashMap: ","Key: "+ set.getKey() + " Val: " + set.getValue());
                            DatabaseReference bottle_ref = ref.child("bottle");
                            bottle_ref.orderByChild("bottleID").equalTo(set.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                        Bottle_back this_bottle = snapshot1.getValue(Bottle_back.class);

                                        if (this_bottle == null) {
                                            return;
                                        }

                                        pickedBottle.add(this_bottle);
                                    }
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

    //throw a bottle back function
    public void throw_bottle_back(String finalBottleID) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bottle");
        DatabaseReference this_bottle_data = reference.child(finalBottleID);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String current_user = fAuth.getUid();

        // set isViewed to false
        Map<String, Object> bottle_update = new HashMap<>();
        bottle_update.put("isViewed", false);
        this_bottle_data.updateChildren(bottle_update);

        //add the user info to bottle history
        final DatabaseReference added_user= this_bottle_data.child("pickHistory").child(current_user);
        added_user.setValue(true);

        //remove the bottle from user's receive list
        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("user").child(current_user);
        final DatabaseReference added_bottle= UserRef.child("receive_list");
        Map<String, Object> user_update = new HashMap<>();
        user_update.put(finalBottleID, false);
        added_bottle.updateChildren(user_update);
    }

    //set isviewed to be true
    public void view_bottle(String bottleID){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bottle");
        DatabaseReference this_bottle_data = reference.child(bottleID);
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String current_user = fAuth.getUid();

        if(!bottleID.equals("")) {
            Map<String, Object> bottle_update = new HashMap<>();
            bottle_update.put("isViewed", true);
            this_bottle_data.updateChildren(bottle_update);

            //save the bottle id in user's receive list
            DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("user").child(current_user);
            final DatabaseReference added_bottle = UserRef.child("receive_list");
            Map<String, Object> user_update = new HashMap<>();
            user_update.put(bottleID, true);
            added_bottle.updateChildren(user_update);
        }
    }

    //get a new bottle
    public void get_bottle(Bottle_back[] this_bottle_list) {
        //get database reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bottle");
        //get current userID
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        reference.orderByChild("isViewed").equalTo(false).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Bottle_back this_bottle = snapshot1.getValue(Bottle_back.class);
                    Log.d("urlefawefea", this_bottle.picture);
                    //String bottleID = this_bottle.getBottleID();
                    String userID = fAuth.getUid();
                    HashMap<String, Boolean> this_history = this_bottle.getPickHistory();

                    //debug: print picked history
                    for (String users : this_history.keySet()) {
                        Log.d("", "picked content:");
                        Log.d("user:", users);
                    }

                    //check if the bottle is viewed
                    if (this_bottle.getIsViewed()) {
                        Log.d("isViewed", "A viewed bottle was returned");
                        continue;
                    }

                    //TODO: comment for test purpose, REUSE for formal product
                    //check if the bottle is from the same user
//                                    if(this_bottle.getUserID().equals(userID)){
//                                        continue;
//                                    }

                    //check if the bottle has been picked up by the same user before
                    if (this_bottle.pickHistory.containsKey(userID)) {
                        Log.d("isPicked", "A bottle picked before was returned");
                        continue;
                    } else {
                        reference.removeEventListener(this);
                        this_bottle_list[0] = this_bottle;
                    }
                }
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

    //get bottle likes
    public void get_likes(String bottleID, TextView like_count ){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bottle");
        DatabaseReference this_bottle_data = reference.child(bottleID);
        //final int[] like = new int[1];

        this_bottle_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int this_like = (snapshot.child("likes").getValue() != null) ?  Integer.parseInt(snapshot.child("likes").getValue().toString()) : 0;
                like_count.setText(this_like+"");
                Log.d("", "fromdatachange" +  "qqqq" + this_like);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        Log.d("", "fromreturnnnnn" + like[0]);
//        return like[0];

    }

    //update bottle likes
    public void update_likes(String bottleID, int this_like){

        //reach the bottle
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bottle");
        DatabaseReference this_bottle_data = reference.child(bottleID);

        if(!bottleID.equals("")) {
            Map<String, Object> bottle_update = new HashMap<>();
            bottle_update.put("likes", (this_like+1));
            this_bottle_data.updateChildren(bottle_update);
        }

    }

    public void add_friend_info_text(String[] info, TextView [] text_render, String fromUserID){
        //contact
        DatabaseReference ContactsRef = FirebaseDatabase.getInstance().getReference().child("Contacts");
        //chat
        DatabaseReference ChatRef = FirebaseDatabase.getInstance().getReference().child("Chats");


        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        String current_user = fAuth.getUid();




        //retrieve user data from database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user");
        DatabaseReference this_user_data = reference.child(fromUserID);
        this_user_data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                info[0] = (snapshot.child("user_name").getValue() != null) ? snapshot.child("user_name").getValue().toString() : "unspecified";
                info[1] = (snapshot.child("user_email").getValue() != null) ? snapshot.child("user_email").getValue().toString() : "unspecified";
                info[2] = (snapshot.child("user_gender").getValue() != null) ? snapshot.child("user_gender").getValue().toString() : "unspecified";
                info[3] = (snapshot.child("age").getValue() != null) ? snapshot.child("age").getValue().toString() : "unspecified";
                info[4] = (snapshot.child("user_country").getValue() != null) ? snapshot.child("user_country").getValue().toString() : "unspecified";


                
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

    public void add_friend_avatar(ImageView profileImage, String fromUserID){
        DatabaseReference avatarRef = FirebaseDatabase.getInstance().getReference("avatars/");
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


