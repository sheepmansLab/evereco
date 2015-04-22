package jp.sheepman.evereco.model;

import jp.sheepman.common.entity.BaseEntity;
import jp.sheepman.common.model.BaseModel;
import jp.sheepman.common.util.DatabaseUtil;
import jp.sheepman.evereco.bean.entity.EventEntity;
import jp.sheepman.evereco.bean.form.EventInsertForm;
import android.content.Context;

public class EventInsertModel extends BaseModel {
	/**
	 * イベント情報をテーブルにInsertする
	 * @param context	Context
	 * @param form		EventInsertForm
	 */
	public void insert(Context context, EventInsertForm form){
		//Entityクラスに値をセットする
		EventEntity entity = new EventEntity();
		entity.setEvent_name(form.getEventName());
		entity.setEvent_date(form.getEventDate());
		entity.setComment(form.getComment());
		
		DatabaseUtil util = new DatabaseUtil(context);
		util.open();
		//Entityの内容でInsertする
		util.insert("event", entity);
		util.close();
	}

	@Override
	public BaseEntity getEntity() {
		return new EventEntity();
	}
}
