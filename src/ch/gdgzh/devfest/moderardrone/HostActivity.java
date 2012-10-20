package ch.gdgzh.devfest.moderardrone;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HostActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_host, menu);
        return true;
    }
}
