package jp.sheepman.evereco.bean.entity;

import jp.sheepman.common.entity.BaseEntity;



public class EventEntity extends BaseEntity {
	private int event_id;
	private String event_name;
	private String event_date;
	private String comment;
	
	/**
	 * コンストラクタ
	 */
	public EventEntity() {
	}

	/**
	 * @return id
	 */
	@IgnoreDBAccess
	public int getEvent_id() {
		return event_id;
	}

	/**
	 * @param id セットする id
	 */
	public void setEvent_id(int id) {
		this.event_id = id;
	}
	
	/**
	 * @return event_name
	 */
	public String getEvent_name() {
		return event_name;
	}

	/**
	 * @param event_name セットする event_name
	 */
	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	/**
	 * @return event_date
	 */
	public String getEvent_date() {
		return event_date;
	}

	/**
	 * @param event_date セットする event_date
	 */
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
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
