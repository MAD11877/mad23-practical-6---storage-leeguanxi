package sg.edu.np.mad.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //boolean followed;
    dbHandler db = new dbHandler(this, null, null, 1);
    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onFollowClick(View v) {
        Log.v("HELLO", "Button Clicked!");
        Button button = (Button)findViewById(R.id.btnFollow);
        if (user.followed){
            Toast.makeText(getBaseContext(),"Unfollowed", Toast.LENGTH_SHORT).show();
            button.setText("FOLLOW");
            user.setFollowed(false);
            db.updateUser(user);
            Log.v("boo", String.valueOf(user.getFollowed()));
        }
        else {
            Toast.makeText(getBaseContext(),"Followed", Toast.LENGTH_SHORT).show();
            button.setText("UNFOLLOW");
            user.setFollowed(true);
            db.updateUser(user);
        }

    }

    public void onMessageClick(View v) {
        // MainActivity to MessageGroup
        Intent intent = new Intent(MainActivity.this, MessageGroup.class);
        startActivity(intent);
    }

    protected void onResume() {
        super.onResume();
        // receive data from ListActivity
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID", 0);
        String name = intent.getStringExtra("USERNAME");
        String description = intent.getStringExtra("DESCRIPTION");
        boolean followed = intent.getBooleanExtra("FOLLOWED", false);

        TextView nameTV = findViewById(R.id.textView);
        TextView descTV = findViewById(R.id.textView2);
        Button button = findViewById(R.id.btnFollow);
        nameTV.setText(name);
        descTV.setText(description);
        if (followed){
            button.setText("UNFOLLOW");
        }
        else {
            button.setText("FOLLOW");
        }

        user.setId(id);
        user.setName(name);
        user.setDescription(description);
        user.setFollowed(followed);

        /*
        int randomInt = intent.getIntExtra("random_integer", 0);
        TextView textview = findViewById(R.id.textView);
        textview.setText("MAD " + randomInt); */
    }
}