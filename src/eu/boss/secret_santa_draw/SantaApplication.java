package eu.boss.secret_santa_draw;

import android.app.Application;
import eu.boss.secret_santa_draw.model.ContactList;

public class SantaApplication extends Application {

	private ContactList participants = new ContactList();

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public ContactList getParticipants() {
		return participants;
	}

	public void setParticipants(ContactList participants) {
		this.participants = participants;
	}

}
