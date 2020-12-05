package backend.util.database;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

public class UserProfile {
    public String user_id;
    public String user_name;
    public String user_email;
    public File avatar;
    public Date regis_date;
    public String discription;
    public String user_gender;
    public String user_country;
    public String age;
    public HashMap<String, Boolean> send_list = new HashMap<>();
    public HashMap<String, Boolean> receive_list = new HashMap<>();

    public UserProfile(){};

    public UserProfile (String user_id, String user_name, String user_email, File avatar, Date regis_date,
                        String discription, String user_gender, String user_country, String age){
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.avatar = avatar;
        this.regis_date = regis_date;
        this.discription = discription;
        this.user_gender = user_gender;
        this.user_country = user_country;
        this.age = age;
    }
}
