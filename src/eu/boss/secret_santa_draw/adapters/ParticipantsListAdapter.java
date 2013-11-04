package eu.boss.secret_santa_draw.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import eu.boss.secret_santa_draw.R;
import eu.boss.secret_santa_draw.model.ContactList;

public class ParticipantsListAdapter extends AbstractContactListAdapter {

	public ParticipantsListAdapter(Context context, ContactList contacts) {
		super(context, contacts);
	}

	private class ViewHolder {

		TextView tvName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.participant_item, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvParticipantName);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvName.setText(contactList.getParticipantList().get(position).getName());
		return convertView;
	}
}
