package eu.boss.secret_santa_draw;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import eu.boss.secret_santa_draw.adapters.ConstraintListAdapter;
import eu.boss.secret_santa_draw.adapters.ParticipantsListAdapter;
import eu.boss.secret_santa_draw.model.Contact;
import eu.boss.secret_santa_draw.model.ContactList;

@SuppressWarnings("deprecation")
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
		}
		if (restart) startDraw();
		confirmSMSSent();
	}

	private boolean drawForContact(Contact c) {
		int lower = 0;
		List<Contact> contacts = selectedContacts.getParticipantList();
		int higher = contacts.size();

		Random r = new Random();
		boolean restartDraw = false, drawDone = false;
		int compteur = 0;
		while ((!drawDone) && (!restartDraw)) {
			Iterator<Contact> it = c.getIncompatibleParticipant().iterator();
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

	private void sendSMSAllList() {
		Iterator<Contact> it = selectedContacts.getParticipantList().iterator();
		while (it.hasNext()) {
			Contact c = it.next();
			Log.d("Phone " + c.getName(), c.getPhoneNumber());
			sendSMS(c.getPhoneNumber(),
					String.format(getString(R.string.resultKey), c.getResult().getName()));
		}
	}

	private void sendSMS(String phoneNumber, String contents) {
		SmsManager smsManager = SmsManager.getDefault();
		smsManager.sendTextMessage(phoneNumber, null, contents, null, null);
	}

	public void confirmSMSSent() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.confirmSMSTitle));
		builder.setMessage(getString(R.string.confirmSMS));
		builder.setPositiveButton(getString(android.R.string.yes), new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				sendSMSAllList();

			}
		});

		builder.setNegativeButton(getString(android.R.string.no), null);
		builder.show();
	}

	public void openYesNoDialog(final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.confirmDeleteTitle));
		builder.setMessage(
				String.format(getString(R.string.confirmDelete), selectedContacts.getParticipantList()
						.get(position).getName())).setPositiveButton(getString(android.R.string.yes),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectedContacts.getParticipantList().remove(position);
						mAdapter.notifyDataSetChanged();
						dialog.dismiss();
					}
				});

		builder.setNegativeButton(getString(android.R.string.no),
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		builder.show();
	}

	public void openConstraintDialog(final int position) {
		final Dialog dialog = new Dialog(DrawConfirmActivity.this, R.style.FullHeightDialog);
		dialog.setContentView(R.layout.custom_dialog);
		Button btnOk = (Button) dialog.findViewById(R.id.btnDialogConfirm);
		ListView lv = (ListView) dialog.findViewById(R.id.lvDialog);
		lv.setAdapter(new ConstraintListAdapter(DrawConfirmActivity.this, selectedContacts,
				selectedContacts.getParticipantList().get(position), lv));
		// if button is clicked, close the custom dialog
		View.OnClickListener listener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		};
		btnOk.setOnClickListener(listener);
		dialog.show();

	}
}
