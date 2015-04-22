package jp.sheepman.evereco.model;

import java.util.ArrayList;
import java.util.List;

import jp.sheepman.common.model.BaseModel;
import jp.sheepman.common.util.DatabaseUtil;
import jp.sheepman.evereco.bean.entity.EventEntity;
import jp.sheepman.evereco.bean.form.EventInsertForm;
import android.content.Context;

public class EventUpdateModel extends BaseModel {
	/**
	 * 繧､繝吶Φ繝域ュ蝣ｱ繧偵ユ繝ｼ繝悶Ν縺ｫInsert縺吶ｋ
	 * @param context	Context
	 * @param form		EventInsertForm
	 */
	public void update(Context context, EventInsertForm form){
		final String table = "event";
		final String whereClause = "event_id = ?";
		//讀懃ｴ｢譚｡莉ｶ
		List<String> list = new ArrayList<String>();
		list.add(String.valueOf(form.getId()));
		
		//Entity繧ｯ繝ｩ繧ｹ縺ｫ蛟､繧偵そ繝�ヨ縺吶ｋ
		EventEntity entity = new EventEntity();
		entity.setEvent_name(form.getEventName());
		entity.setEvent_date(form.getEventDate());
		entity.setComment(form.getComment());
		
		DatabaseUtil util = new DatabaseUtil(context);
		util.open();
		
		//Entity縺ｮ蜀�ｮｹ縺ｧUpdate縺吶ｋ
		util.update(table, whereClause, entity, list);
		util.close();
	}
}
