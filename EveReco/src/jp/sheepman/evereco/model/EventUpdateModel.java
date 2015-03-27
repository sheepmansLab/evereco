package jp.sheepman.evereco.model;

import java.util.ArrayList;
import java.util.List;

import jp.sheepman.common.entity.BaseEntity;
import jp.sheepman.common.model.BaseModel;
import jp.sheepman.common.util.DatabaseUtil;
import jp.sheepman.evereco.bean.entity.EventEntity;
import jp.sheepman.evereco.bean.form.EventInsertForm;
import android.content.Context;

public class EventUpdateModel extends BaseModel {
	/**
	 * イベント情報をテーブルにInsertする
	 * @param context	Context
	 * @param form		EventInsertForm
	 */
	public void update(Context context, EventInsertForm form){
		final String table = "event";
		final String whereClause = "event_id = ?";
		//検索条件
		List<String> list = new ArrayList<String>();
		list.add(String.valueOf(form.getId()));
		
		//Entityクラスに値をセットする
		EventEntity entity = new EventEntity();
		entity.setEvent_name(form.getEventName());
		entity.setEvent_date(form.getEventDate());
		entity.setComment(form.getComment());
		
		DatabaseUtil util = new DatabaseUtil(context);
		util.open();
		
		//Entityの内容でUpdateする
		util.update(table, whereClause, entity, list);
		util.close();
	}
}
