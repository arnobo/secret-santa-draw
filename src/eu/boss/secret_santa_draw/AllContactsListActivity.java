package eu.boss.secret_santa_draw;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ListView;
import eu.boss.secret_santa_draw.adapters.AllContactsListAdapter;
import eu.boss.secret_santa_draw.model.Contact;
import eu.boss.secret_santa_draw.model.ContactList;

public class AllContactsListActivity extends Activity {

	private ListView mListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mListView = (ListView) findViewById(R.id.lvMain);
		ContactList contacts = getContacts();
		mListView.setAdapter(new AllContactsListAdapter(this, contacts));
		((SantaApplication) getApplication()).setParticipants(contacts);
	}

	private ContactList getContacts() {
		ContactList contactList = new ContactList();
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
				contactList.addParticipant(p);
			}
		}
		cur.close();
		return contactList;
	}
}
