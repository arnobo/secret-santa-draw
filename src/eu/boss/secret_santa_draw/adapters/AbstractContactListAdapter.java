package eu.boss.secret_santa_draw.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import eu.boss.secret_santa_draw.model.ContactList;

public abstract class AbstractContactListAdapter extends BaseAdapter {

	protected ContactList contactList;
	protected LayoutInflater inflater;
	protected Context context;

	public AbstractContactListAdapter(Context context, ContactList contactList) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		this.contactList = contactList;
	}

	@Override
	public int getCount() {
		return contactList.getParticipantList().size();
	}

	@Override
	public Object getItem(int position) {
		return contactList.getParticipantList().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}

	public void addAll(ContactList list) {
		this.contactList.getParticipantList().addAll(list.getParticipantList());
		notifyDataSetChanged();
	}

	public void clear() {
		contactList.getParticipantList().clear();
	}

	public void replace(ContactList list) {
		clear();
		addAll(list);
	}

	public ContactList getContactList() {
		return contactList;
	}

	public void setContactList(ContactList contactList) {
		this.contactList = contactList;
	}

}
