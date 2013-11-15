package eu.boss.secret_santa_draw;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import eu.boss.secret_santa_draw.adapters.AllContactsListAdapter;
import eu.boss.secret_santa_draw.model.Contact;
import eu.boss.secret_santa_draw.model.ContactList;

public class NewDrawActivity extends Activity {

	private ListView mListView;
	private AllContactsListAdapter mAdapter;
	private boolean showMenu = false;

	ContactList mContactList = new ContactList();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_list);
		mListView = (ListView) findViewById(R.id.lvMain);
		ContactList contacts = getContacts();
		mAdapter = new AllContactsListAdapter(this, contacts, this);
		mListView.setAdapter(mAdapter);
	}

	private ContactList getContacts() {
		mContactList = new ContactList();
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		ContentResolver cr = getContentResolver();
		String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
		Cursor cur = cr.query(uri, null, null, null, sortOrder);
		if (cur.getCount() > 0) {
			String id;
			String name;
			while (cur.moveToNext()) {
				Contact p = new Contact();
				id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
				name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				p.setId(id);
				p.setName(name);
				Log.i("Name: " + name, "Id: " + id);
				mContactList.addParticipant(p);
			}
		}
		cur.close();
		return mContactList;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return showMenu;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = new Intent(NewDrawActivity.this, DrawConfirmActivity.class);
		i.putExtra(DrawConfirmActivity.SELECTED_CONTACTS, mContactList);
		startActivity(i);
		return true;
	}

	/**
	 * @param selected
	 *           number of selected contacts. 0: hide menu, else show menu
	 */
	public void updateMenu(int selected) {
		showMenu = (selected > 0) ? true : false;

		invalidateOptionsMenu();
	}

}
