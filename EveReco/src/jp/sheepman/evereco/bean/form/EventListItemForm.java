package jp.sheepman.evereco.bean.form;

import jp.sheepman.common.form.BaseForm;

/**
 * イベントの一覧表示のアイテム
 * @author sheepman
 *
 */
public class EventListItemForm extends BaseForm {
	private int id;
	private String eventName;
	private String eventDate;
	private int dateDiffYear;
	private int dateDiffMonth;
	private int dateDiffDay;
	private boolean flgPast;
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
	 * @return dateDiffYear
	 */
	public int getDateDiffYear() {
		return dateDiffYear;
	}
	/**
	 * @param dateDiffYear セットする dateDiffYear
	 */
	public void setDateDiffYear(int dateDiffYear) {
		this.dateDiffYear = dateDiffYear;
	}
	/**
	 * @return dateDiffMonth
	 */
	public int getDateDiffMonth() {
		return dateDiffMonth;
	}
	/**
	 * @param dateDiffMonth セットする dateDiffMonth
	 */
	public void setDateDiffMonth(int dateDiffMonth) {
		this.dateDiffMonth = dateDiffMonth;
	}
	/**
	 * @return dateDiffDay
	 */
	public int getDateDiffDay() {
		return dateDiffDay;
	}
	/**
	 * @param dateDiffDay セットする dateDiffDay
	 */
	public void setDateDiffDay(int dateDiffDay) {
		this.dateDiffDay = dateDiffDay;
	}
	/**
	 * @return flgIsPast
	 */
	public boolean isPast() {
		return flgPast;
	}
	/**
	 * @param flgIsPast セットする flgIsPast
	 */
	public void setIsPast(boolean flgPast) {
		this.flgPast = flgPast;
	}
}
