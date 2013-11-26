package eu.boss.secret_santa_draw.adapters;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import eu.boss.secret_santa_draw.DrawConfirmActivity;
import eu.boss.secret_santa_draw.R;
import eu.boss.secret_santa_draw.model.ContactList;

public class ParticipantsListAdapter extends AbstractContactListAdapter {

	private DrawConfirmActivity mActivity;

	public ParticipantsListAdapter(DrawConfirmActivity activity, ContactList contacts) {
		super(activity, contacts);
		mActivity = activity;
	}

	private class ViewHolder {

		TextView tvName;
		Button btnConstraint;
		Button btnRemove;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.participant_item, null);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvParticipantName);
			holder.btnConstraint = (Button) convertView.findViewById(R.id.btnConstraint);
			holder.btnRemove = (Button) convertView.findViewById(R.id.btnRemove);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tvName.setText(contactList.getParticipantList().get(position).getName());

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.btnConstraint:
					mActivity.openConstraintDialog(position);
					break;
				case R.id.btnRemove:
					mActivity.openYesNoDialog(position);
					break;
				default:
					break;
				}

			}
		};

		holder.btnConstraint.setOnClickListener(listener);
		holder.btnRemove.setOnClickListener(listener);
		return convertView;
	}

	public void addAll(ContactList list) {
		this.contactList.getParticipantList().addAll(list.getParticipantList());
		notifyDataSetChanged();
	}

	public void clear() {
		this.contactList.getParticipantList().clear();
	}

	public void replace(ContactList list) {
		clear();
		addAll(list);
	}
}
