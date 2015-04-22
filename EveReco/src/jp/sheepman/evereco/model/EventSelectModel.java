package jp.sheepman.evereco.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import jp.sheepman.common.entity.BaseEntity;
import jp.sheepman.common.model.BaseModel;
import jp.sheepman.common.util.CalendarUtil;
import jp.sheepman.common.util.DatabaseUtil;
import jp.sheepman.evereco.bean.entity.EventEntity;
import jp.sheepman.evereco.bean.form.EventCalendarForm;
import jp.sheepman.evereco.bean.form.EventInputForm;
import jp.sheepman.evereco.bean.form.EventListItemForm;
import jp.sheepman.evereco.bean.form.EventListSearchForm;
import android.content.Context;

public class EventSelectModel extends BaseModel {
	
	/**
	 * 条件なしで全件取得
	 * @param context
	 * @return
	 */
	public List<EventListItemForm> selectForListView(Context context, EventListSearchForm form){
		//検索用のSQL
		final String select = "SELECT * FROM event ";
		final String where = "WHERE event_date BETWEEN ? AND ? ";
		final String orderBy = "ORDER BY DATETIME(event_date) DESC, event_id ";
		
		//検索結果のコレクション
		List<EventListItemForm> itemList = new ArrayList<EventListItemForm>();
		
		DatabaseUtil util = new DatabaseUtil(context);
		try {
			//DBをオープン
			util.open();
			//SQLのパラメータ(全件検索するのでnull
			List <String> params = new ArrayList<String>();
			
			//SQL文を生成
			StringBuffer sb = new StringBuffer();
			sb.append(select);
			if(form != null && (form.getEventDateStart() != null || form.getEventDateEnd() != null)){
				sb.append(where);
				if(form.getEventDateStart() != null){
					params.add(form.getEventDateStart());
				} else {
					params.add("1900-01-01");
				}
				if(form.getEventDateEnd() != null){
					params.add(form.getEventDateEnd());
				} else {
					params.add("2900-12-31");
				}
			}
			sb.append(orderBy);
			
			
			//DatabaseUtilの検索メソッドでsqlを実行
			//第三匹数はModelクラスをセットするので、自分をセット
			List<BaseEntity> entitylist = util.select(sb.toString(), params, this);
			
			//検索結果を処理
			for(BaseEntity e : entitylist){
				//表示用のFormオブジェクトに値をセット
				EventListItemForm item = new EventListItemForm();
				//日付
				Calendar eventDateCal = CalendarUtil.str2cal(((EventEntity)e).getEvent_date());
				Calendar nowCal = Calendar.getInstance(Locale.JAPAN);
				
				StringBuffer event_date = new StringBuffer();
				event_date.append(CalendarUtil.getYear(eventDateCal));
				event_date.append("\r\n");
				event_date.append(CalendarUtil.getMonth(eventDateCal));
				event_date.append("/");
				event_date.append(CalendarUtil.getDate(eventDateCal));
				
				//過去フラグをセット
				item.setIsPast(nowCal.after(eventDateCal));
				
				//DBのPK
				item.setId(((EventEntity)e).getEvent_id());
				//イベント名をセット
				item.setEventName(((EventEntity)e).getEvent_name());
				//イベント日付をセット
				item.setEventDate(event_date.toString());
				//経過日数をセット
				item.setDateDiffYear(CalendarUtil.getDiffAbs(eventDateCal, nowCal, Calendar.YEAR));
				item.setDateDiffMonth(CalendarUtil.getDiffAbs(eventDateCal, nowCal, Calendar.MONTH));
				item.setDateDiffDay(CalendarUtil.getDiffAbs(eventDateCal, nowCal, Calendar.DATE));	
				//コレクションにadd
				itemList.add(item);
			}
		} finally {
			util.close();
		}
		return itemList;
	}
	
	/**
	 * event_idを指定して検索
	 * @param context
	 * @return
	 */
	public EventInputForm selectById(Context context, int event_id){
		//検索用のSQL
		final String sql = "SELECT * FROM event where event_id = ?";
		EventInputForm form = new EventInputForm();
		
		DatabaseUtil util = new DatabaseUtil(context);
		util.open();
		
		List <String> params = new ArrayList<String>();
		params.add(String.valueOf(event_id));
		List<BaseEntity> entity = util.select(sql, params, this);
		for(BaseEntity e : entity){
			form.setEvent_id(((EventEntity)e).getEvent_id());
			form.setEventName(((EventEntity)e).getEvent_name());
			form.setEventDate(((EventEntity)e).getEvent_date());
			form.setComment(((EventEntity)e).getComment());
		}
		util.close();
		return form;
	}
	
	/**
	 * カレンダー用のデータを検索する
	 * @param context
	 * @param year
	 * @param month
	 * @return
	 */
	public List<EventCalendarForm> selectForCalendar(Context context, String year, String month){
		final String sql = "select * from event where strftime('%Y', event_date) = ? and strftime('%m', event_date) = ?";
		List<EventCalendarForm> list = new ArrayList<EventCalendarForm>();
		DatabaseUtil util = new DatabaseUtil(context);
		util.open();
		
		List<String> params = new ArrayList<String>();
		params.add(year);
		params.add(month);
		List<BaseEntity> entity = util.select(sql, params, this);
		for(BaseEntity e : entity){
			EventCalendarForm form = new EventCalendarForm();
			form.setId(((EventEntity)e).getEvent_id());
			form.setEventDate(((EventEntity)e).getEvent_date());
			form.setEventName(((EventEntity)e).getEvent_name());
			list.add(form);
		}
		util.close();
		return list ;
	}
	
	@Override
	public BaseEntity getEntity() {
		return new EventEntity();
	}

}
