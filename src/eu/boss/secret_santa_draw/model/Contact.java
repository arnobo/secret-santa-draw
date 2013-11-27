package eu.boss.secret_santa_draw.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable {

	private static final long serialVersionUID = -4917534996998806369L;
	private String id;
	private String name;
	private String phoneNumber;
	private ArrayList<Contact> incompatibleParticipant = new ArrayList<Contact>();
	private Contact result;
	private boolean isSelected;
	private boolean isDrawn;

	public Contact() {
	}

	public Contact(String name, String id) {
		this.name = name;
		this.id = id;
		this.incompatibleParticipant.add(this);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Contact> getIncompatibleParticipant() {
		return incompatibleParticipant;
	}

	public void setIncompatibleParticipant(ArrayList<Contact> incompatibleParticipant) {
		this.incompatibleParticipant = incompatibleParticipant;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Contact getResult() {
		return result;
	}

	public void setResult(Contact result) {
		this.result = result;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isDrawn() {
		return isDrawn;
	}

	public void setDrawn(boolean isDrawn) {
		this.isDrawn = isDrawn;
	}

}
