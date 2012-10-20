package ch.gdgzh.devfest.moderardrone;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class JoinActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_join, menu);
        return true;
    }
}
