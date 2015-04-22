package jp.sheepman.evereco.bean.form;

import jp.sheepman.common.form.BaseForm;

/**
 * イベント登録用DTO
 * @author sheepman
 */
public class EventInsertForm extends BaseForm {
	private int id;
	private String eventName;
	private String eventDate;
	private String comment;

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id セットする id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return eventName
	 */
	public String getEventName() {
		return eventName;
	}
	/**
	 * @param eventName セットする eventName
	 */
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	/**
	 * @return eventDate
	 */
	public String getEventDate() {
		return eventDate;
	}
	/**
	 * @param eventDate セットする eventDate
	 */
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	/**
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment セットする comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}
