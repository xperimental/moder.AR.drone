package ch.gdgzh.devfest.moderardrone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button hostButton = (Button) findViewById(R.id.button_host);
		Button joinButton = (Button) findViewById(R.id.button_join);
		hostButton.setOnClickListener(hostClickListener);
		joinButton.setOnClickListener(joinClickListener);

	}

	private OnClickListener hostClickListener = new OnClickListener() {

		public void onClick(View view) {
			Intent i = new Intent(MainActivity.this, HostActivity.class);
			startActivity(i);
		}

	};

	private OnClickListener joinClickListener = new OnClickListener() {
		
		public void onClick(View v) {
			Intent i = new Intent(MainActivity.this, JoinActivity.class);
			startActivity(i);
		}
		
	};

}
