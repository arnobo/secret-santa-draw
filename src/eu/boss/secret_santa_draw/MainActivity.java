package eu.boss.secret_santa_draw;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.widget.ListView;
import eu.boss.secret_santa_draw.adapters.ParticipantsListAdapter;

public class MainActivity extends Activity {

	private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (ListView) findViewById(R.id.lvMain);
		startActivity(new Intent(MainActivity.this, AllContactsListActivity.class));
	}

	@Override
	protected void onResume() {
		super.onResume();
		mListView.setAdapter(new ParticipantsListAdapter(this, ((SantaApplication) getApplication())
				.getParticipants()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void sendSMS(String phoneNumber, String contents) {
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(phoneNumber, null, contents, null, null);
	}
}
