package eu.boss.secret_santa_draw.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import eu.boss.secret_santa_draw.R;
import eu.boss.secret_santa_draw.model.ContactList;

public class AllContactsListAdapter extends AbstractContactListAdapter {

	public AllContactsListAdapter(Context context, ContactList contacts) {
		super(context, contacts);
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
		holder.cbSelected.setSelected(contactList.getParticipantList().get(position).isSelected());
		holder.cbSelected.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				contactList.getParticipantList().get(position).setSelected(isChecked);
			}

		});
		return convertView;
	}
}
