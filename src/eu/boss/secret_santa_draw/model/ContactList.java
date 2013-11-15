package eu.boss.secret_santa_draw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactList implements Serializable{

	private static final long serialVersionUID = 936219385237565452L;
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
