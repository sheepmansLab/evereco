package jp.sheepman.evereco.model;

import java.util.ArrayList;
import java.util.List;

import jp.sheepman.common.model.BaseModel;
import jp.sheepman.common.util.DatabaseUtil;
import jp.sheepman.evereco.bean.form.EventDeleteForm;
import android.content.Context;

public class EventDeleteModel extends BaseModel {
	//削除対象テーブル
	final String tablename = "event";
	final String whereClause = "event_id = ?";
	
	/**
	 * IDでレコードを削除
	 * @param context
	 * @param form
	 */
	public void deleteByID(Context context, EventDeleteForm form){
		DatabaseUtil util = new DatabaseUtil(context);
		util.open();
		List<String> list = new ArrayList<String>();
		
		//PKのIDをセット
		if(form != null && form.getId() > 0){
			list.add(String.valueOf(form.getId()));
		}
		if(list.size() > 0){
			util.delete(tablename, whereClause, list);
		}
		util.close();
	}
}
