package eu.boss.secret_santa_draw;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.util.Log;
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
	private ParticipantsListAdapter mAdapter;

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
			mAdapter = new ParticipantsListAdapter(this, selectedContacts);
			mListView.setAdapter(mAdapter);
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

		startDraw();
		return true;
	}

	private void startDraw() {
		Iterator<Contact> it = selectedContacts.getParticipantList().iterator();
		boolean restart = false;
		while (it.hasNext()) {
			Contact c = it.next();
			restart = drawForContact(c);
			Log.d(c.getName() + " a tiré au sort", c.getResult().getName());
			// sendSMS(c.getPhoneNumber(),
			// String.format(getString(R.string.resultKey), c.getResult().getName()));
		}
		if (restart) startDraw();
	}

	private boolean drawForContact(Contact c) {
		int lower = 0;
		List<Contact> contacts = selectedContacts.getParticipantList();
		int higher = contacts.size();

		Random r = new Random();
		boolean restartDraw = false, drawDone = false;
		int compteur = 0;
		while ((!drawDone) && (!restartDraw)) {

			int position = lower + r.nextInt(higher - lower);
			if ((!c.getIncompatibleParticipant().contains(contacts.get(position)))
					&& (!contacts.get(position).isDrawn())) {
				c.setResult(contacts.get(position));
				contacts.get(position).setDrawn(true);
				drawDone = true;

			} else {
				if (position == higher) restartDraw = true;
				compteur++;
			}
			// Max 50 trys to avoid infinite loop
			if (compteur > 50) restartDraw = true;
		}
		return restartDraw;
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
				mAdapter.notifyDataSetChanged();
			}
		});

		builder.setNegativeButton(getString(android.R.string.no), null);
		builder.show();
	}
}
