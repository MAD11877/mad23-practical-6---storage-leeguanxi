package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Random;

public class ListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ArrayList<User> userList;

        /*
        // set user data
        for (int i = 0; i < 20; i++){
            Random random = new Random();
            String randomName = "Name" + random.nextInt(9999);
            String randomDesc = "Description" + random.nextInt(999999);
            User user = new User();
            user.setName(randomName);
            user.setDescription(randomDesc);
            user.followed = false;
            userList.add(user);
        }*/


        dbHandler db = new dbHandler(this, null, null, 1);

        // generate 20 users
        if (db.isEmpty()){
            db.generateUsers();
        }

        userList = db.getUsers();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter mAdapter = new recyclerAdapter(userList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

    }


}