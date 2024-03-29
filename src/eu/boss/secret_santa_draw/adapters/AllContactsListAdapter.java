package eu.boss.secret_santa_draw.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import eu.boss.secret_santa_draw.NewDrawActivity;
import eu.boss.secret_santa_draw.R;
import eu.boss.secret_santa_draw.model.ContactList;

public class AllContactsListAdapter extends AbstractContactListAdapter {

	private NewDrawActivity mActivity;
	private int numberContactSelected = 0;
	private boolean[] mCheckedState;

	public AllContactsListAdapter(Context context, ContactList contacts, NewDrawActivity activity) {
		super(context, contacts);
		mActivity = activity;
		mCheckedState = new boolean[contacts.getParticipantList().size()];
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
		holder.cbSelected.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				contactList.getParticipantList().get(position).setSelected(isChecked);
				final int position = mActivity.getListView().getPositionForView(buttonView);
				if (position != ListView.INVALID_POSITION) {
					mCheckedState[position] = isChecked;
				}
				if (isChecked) numberContactSelected++;
				else numberContactSelected--;

				mActivity.updateMenu(numberContactSelected);

			}

		});
		return convertView;
	}

}
