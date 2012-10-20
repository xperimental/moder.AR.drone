package ch.gdgzh.devfest.moderardrone.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ch.gdgzh.devfest.moderardrone.R;
import ch.gdgzh.devfest.moderardrone.ui.dialog.TopicDialog;

public class SplashActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        findViewById(R.id.splash_button_host).setOnClickListener(this);
        findViewById(R.id.splash_button_join).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.splash_button_host:
                TopicDialog.show(getFragmentManager());
                break;
            case R.id.splash_button_join:
                Intent clientIntent = new Intent(this, ClientActivity.class);
                startActivity(clientIntent);
                break;
            default:
                throw new IllegalArgumentException("Unknown button: " + view);
        }
    }
}
