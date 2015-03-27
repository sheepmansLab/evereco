package jp.sheepman.evereco.bean.form;

import jp.sheepman.common.form.BaseForm;


/**
 * イベントの一覧表示のアイテム
 * @author sheepman
 *
 */
public class EventCalendarForm extends BaseForm {
	private int id;
	private String eventName;
	private String eventDate;
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
}
