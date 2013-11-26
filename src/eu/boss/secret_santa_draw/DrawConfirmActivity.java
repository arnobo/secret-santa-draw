package eu.boss.secret_santa_draw;

import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import eu.boss.secret_santa_draw.adapters.ParticipantsListAdapter;
import eu.boss.secret_santa_draw.model.Contact;
import eu.boss.secret_santa_draw.model.ContactList;

public class DrawConfirmActivity extends Activity {

	private ListView mListView;
	public static final String SELECTED_CONTACTS = "SELECTED_CONTACTS";
	private ContactList selectedContacts = new ContactList();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_list);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Iterator<Contact> it = ((ContactList) getIntent().getExtras().getSerializable(
				SELECTED_CONTACTS)).getParticipantList().iterator();
		while (it.hasNext()) {
			Contact c = it.next();
			if (c.isSelected()) selectedContacts.addParticipant(c);
		}
		try {
			mListView = (ListView) findViewById(R.id.lvMain);
			mListView.setAdapter(new ParticipantsListAdapter(this, selectedContacts));
		} catch (Exception e) {
			finish();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.selection_page, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Iterator<Contact> it = selectedContacts.getParticipantList().iterator();
		while (it.hasNext()) {
			Contact c = it.next();
			sendSMS(c.getPhoneNumber(),
					String.format(getString(R.string.resultKey), c.getResult().getName()));
		}
		return true;
	}

	private void sendSMS(String phoneNumber, String contents) {
		// SmsManager smsManager = SmsManager.getDefault();
		// smsManager.sendTextMessage(phoneNumber, null, contents, null, null);
	}

	public void openConstraintDialog(int position) {

	}

	public void openYesNoDialog(final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.confirmDeleteTitle));
		builder.setMessage(String.format(getString(R.string.confirmDelete), selectedContacts
				.getParticipantList().get(position).getName()));
		builder.setPositiveButton(getString(android.R.string.yes), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				selectedContacts.getParticipantList().remove(position);
				// TODO: notifysetdatachanged
			}
		});

		builder.setNegativeButton(getString(android.R.string.no), null);
		builder.show();
	}
}
