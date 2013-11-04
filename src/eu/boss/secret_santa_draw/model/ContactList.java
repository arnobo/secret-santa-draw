package eu.boss.secret_santa_draw.model;

import java.util.ArrayList;
import java.util.List;

public class ContactList {

	private List<Contact> participantList = new ArrayList<Contact>();

	public List<Contact> getParticipantList() {
		return participantList;
	}

	public void setParticipantList(List<Contact> participantList) {
		this.participantList = participantList;
	}

	public void addParticipant(Contact p) {
		participantList.add(p);
	}

}
