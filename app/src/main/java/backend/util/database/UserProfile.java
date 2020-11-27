package backend.util.database;

import java.io.File;
import java.util.Date;
import java.util.Locale;

public class UserProfile {
    public String user_id;
    public String user_email;
    public File avatar;
    public Date regis_date;
    public String discription;
    public EnumD.gender user_gender;
    public Locale user_country;

    public UserProfile(){};

    public UserProfile (String user_id, String user_email, File avatar, Date regis_date,
                        String discription, EnumD.gender user_gender, Locale user_country){
        this.user_id = user_id;
        this.user_email = user_email;
        this.avatar = avatar;
        this.regis_date = regis_date;
        this.discription = discription;
        this.user_gender = user_gender;
        this.user_country = user_country;
    }
}
