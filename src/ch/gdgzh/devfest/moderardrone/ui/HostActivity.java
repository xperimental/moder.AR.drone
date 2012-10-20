package ch.gdgzh.devfest.moderardrone.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import ch.gdgzh.devfest.moderardrone.R;

public class HostActivity extends Activity {

    public static final String EXTRA_TOPIC = "topic";

    private String topic;

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
    }

}
