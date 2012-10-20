package ch.gdgzh.devfest.moderardrone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

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
            case R.id.splash_button_join:
                break;
            default:
                throw new IllegalArgumentException("Unknown button: " + view);
        }
    }
}
