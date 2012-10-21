package ch.gdgzh.devfest.moderardrone.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import ch.gdgzh.devfest.moderardrone.R;
import ch.gdgzh.devfest.moderardrone.service.HostService;
import ch.gdgzh.devfest.moderardrone.service.HostServiceConnection;

public class HostActivity extends Activity {

    public static final String EXTRA_TOPIC = "topic";

    private String topic;
    private HostServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        topic = getIntent().getStringExtra(EXTRA_TOPIC);
        if (topic == null || topic.length() == 0) {
            Toast.makeText(this, getString(R.string.toast_no_topic), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        TextView topicView = (TextView) findViewById(R.id.topic);
        topicView.setText(topic);

        serviceConnection = new HostServiceConnection();
    }

    @Override
    protected void onStart() {
        super.onStart();

        HostService.bind(this, serviceConnection);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unbindService(serviceConnection);
    }
}
