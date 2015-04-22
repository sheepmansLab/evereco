package jp.sheepman.evereco.bean.form;

import jp.sheepman.common.form.BaseForm;

/**
 * イベントの一覧表示のアイテム
 * @author sheepman
 *
 */
public class EventListSearchForm extends BaseForm {
	private String eventName;
	private String eventDate;
	private String eventDateStart;
	private String eventDateEnd;
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
	 * @return eventDateStart
	 */
	public String getEventDateStart() {
		return eventDateStart;
	}
	/**
	 * @param eventDateStart セットする eventDateStart
	 */
	public void setEventDateStart(String eventDateStart) {
		this.eventDateStart = eventDateStart;
	}
	/**
	 * @return eventDateEnd
	 */
	public String getEventDateEnd() {
		return eventDateEnd;
	}
	/**
	 * @param eventDateEnd セットする eventDateEnd
	 */
	public void setEventDateEnd(String eventDateEnd) {
		this.eventDateEnd = eventDateEnd;
	}
	
}
