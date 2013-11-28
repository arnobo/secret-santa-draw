package eu.boss.secret_santa_draw.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import eu.boss.secret_santa_draw.R;
import eu.boss.secret_santa_draw.model.Contact;
import eu.boss.secret_santa_draw.model.ContactList;

public class ConstraintListAdapter extends AbstractContactListAdapter {

	private ListView mListView;
	private boolean[] mCheckedState;
	private Contact mSelectedContact;

	public ConstraintListAdapter(Context context, ContactList contacts, Contact selectedContact,
			ListView listView) {
		super(context, contacts);
		mCheckedState = new boolean[contacts.getParticipantList().size()];
		mSelectedContact = selectedContact;
		mListView = listView;
	}

	private class ViewHolder {

		TextView tvName;
		CheckBox cbSelected;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.contact_item, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvContactName);
			holder.cbSelected = (CheckBox) convertView.findViewById(R.id.cbContact);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvName.setText(contactList.getParticipantList().get(position).getName());
		holder.cbSelected.setOnCheckedChangeListener(null);
		holder.cbSelected.setChecked(mCheckedState[position]);
		if (mSelectedContact.getIncompatibleParticipant().contains(
				contactList.getParticipantList().get(position))) {
			holder.cbSelected.setChecked(true);
			mCheckedState[position] = true;
		}
		holder.cbSelected.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				mSelectedContact.getIncompatibleParticipant().add(
						contactList.getParticipantList().get(position));
				final int position = mListView.getPositionForView(buttonView);
				if (position != ListView.INVALID_POSITION) {
					mCheckedState[position] = isChecked;
				}
			}

		});

		return convertView;
	}

}
