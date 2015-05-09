package jp.sheepman.evereco.fragment;

import java.util.Calendar;
import java.util.List;

import jp.sheepman.common.adapter.BaseCustomAdapter;
import jp.sheepman.common.form.BaseForm;
import jp.sheepman.common.fragment.BaseFragment;
import jp.sheepman.common.util.CalendarUtil;
import jp.sheepman.evereco.R;
import jp.sheepman.evereco.bean.form.EventDeleteForm;
import jp.sheepman.evereco.bean.form.EventListItemForm;
import jp.sheepman.evereco.bean.form.EventListSearchForm;
import jp.sheepman.evereco.model.EventDeleteModel;
import jp.sheepman.evereco.model.EventSelectModel;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class EventListFragment extends BaseFragment {
	private ListView lvEventList;
	private LinearLayout llListSearch;
	private TextView tvListFrom;
	private TextView tvListTo;
	private TextView tvSearchCircle;
	private Button btnListNewRecord;
	private Button btnListClearFrom;
	private Button btnListClearTo;
	
	private EventInputDialogFragment dialog;
	private EventListAdapter adapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//アイテム押下時のダイアログ
		dialog = new EventInputDialogFragment();
		//ダイアログのコールバック対象として自身をセット
		dialog.setTargetFragment(this, 0);
		dialog.setRetainInstance(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//Viewを生成
		View v = inflater.inflate(R.layout.fragment_list, null);
		//List追加用のAdapter
		adapter = new EventListAdapter(getActivity());
		
		//コンポーネントを取得
		lvEventList = (ListView)v.findViewById(R.id.lvEventList);
		llListSearch = (LinearLayout)v.findViewById(R.id.llListSearch);
		tvListFrom = (TextView)v.findViewById(R.id.tvListFrom);
		tvListTo = (TextView)v.findViewById(R.id.tvListTo);
		tvSearchCircle = (TextView)v.findViewById(R.id.tvSearchCircle);
		btnListNewRecord = (Button)v.findViewById(R.id.btnListNewRecord);
		btnListClearFrom = (Button)v.findViewById(R.id.btnListClearFrom);
		btnListClearTo = (Button)v.findViewById(R.id.btnListClearTo);
		
		//Adapterをセット
		lvEventList.setAdapter(adapter);
		
		//日付入力
		tvListFrom.setOnClickListener(lsnrDateEdit);
		tvListTo.setOnClickListener(lsnrDateEdit);
		
		//新規作成
		btnListNewRecord.setOnClickListener(lsnrNewRecord);
		
		//クリアボタンの押下イベント
		btnListClearFrom.setOnClickListener(lsnrClear);
		btnListClearTo.setOnClickListener(lsnrClear);
		
		//
		tvSearchCircle.setOnClickListener(lsnrSearchCircle);
		
		//ListViewをリロードする
		reload();
		
		return v;
	}
	
	/**
	 * 日付カウント表示Viewタッチイベント
	 */
	private OnTouchListener lsnrTouch = new OnTouchListener() {
		private float x1, x2, y1, y2;
				
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			boolean ret = false;
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.d("touch", "down");
				x1 = event.getX();
				y1 = event.getY();
				ret = false;
				break;
			case MotionEvent.ACTION_UP:
				Log.d("touch", "up");
				x1 = event.getX();
				y1 = event.getY();
				ret = false;
				break;
			case MotionEvent.ACTION_CANCEL:
				Log.d("touch", "cancel");
				ret = false;
				break;
			case MotionEvent.ACTION_MOVE:
				Log.d("touch", "move");
				ret = true;
				x2 = event.getX();
				y2 = event.getY();
				//横移動の場合
				if(Math.abs(x2 -x1) > Math.abs(y2 - y1)){
					int diff = (int)(x2 -x1);
					
					if(diff > 0){
						//右フリック
						if(v.getTag() != null && (Boolean)v.getTag()){
							ObjectAnimator anime = ObjectAnimator.ofFloat(v, "translationX", -120.0f, 0.0f);
							anime.setDuration(300);
							anime.start();
							v.setTag(Boolean.valueOf(false));
						}
					} else {
						//左フリック
						if(v.getTag() == null || !(Boolean)v.getTag()){
							ObjectAnimator anime = ObjectAnimator.ofFloat(v, "translationX", 0.0f, -120.0f);
							anime.setDuration(300);
							anime.start();
							v.setTag(Boolean.valueOf(true));
						}
					}
					ret = false;
				}
				break;
			default:
				Log.d("touch", "default"+event.getAction());
				ret = false;
				break;
			}
			ret = true;
			return ret;
		}
	};
	
	/**
	 * ListViewのリロード
	 */
	private void reload(){
		//イベント一覧となるformのリストを取得するModelクラスから取得する
		EventSelectModel model = new EventSelectModel();
		EventListSearchForm form = new EventListSearchForm();
		
		if(tvListFrom.getText().length() > 0){
			form.setEventDateStart(tvListFrom.getText().toString());
		}
		if(tvListTo.getText().length() > 0){
			form.setEventDateEnd(tvListTo.getText().toString());
		}
		
		List<EventListItemForm> list = model.selectForListView(getActivity(), form);
		
		//リストを初期化する
		adapter.clear();
		//取得したformクラスをListViewに追加する
		for(EventListItemForm item : list){
			adapter.add(item);
		}
		//ListViewへの変更を反映させる
		adapter.notifyDataSetChanged();
	};

	/**
	 * ListViewのアイテムをクリックしたときのイベントリスナ
	 */
	private OnClickListener lsnrEventListItem = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Log.d("click", "click");
			Bundle args = new Bundle();
			EventListItemForm form = (EventListItemForm)v.getTag();
			args.putInt("event_id", form.getId());
			dialog.setArguments(args);
			dialog.show(getFragmentManager(), "dialog");
		}
	};
	
	/**
	 * 新規作成ボタンをクリックした場合
	 */
	private OnClickListener lsnrNewRecord = new OnClickListener() {
		@Override
		public void onClick(View v) {
			dialog.show(getFragmentManager(), "dialog");
		}
	};
	
	/** 
	 * 日付変更ボタン押下イベント
	 */
	private View.OnClickListener lsnrDateEdit = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Calendar cal;
			DatePickerDialog.OnDateSetListener lsnrDatePicker;
			//設定する日付をセット
			if(((TextView)v).getText().length() > 0){
				cal = CalendarUtil.str2cal(((TextView)v).getText().toString());
			} else {
				cal = Calendar.getInstance();
			}
			//日付ダイアログセット時のイベント
			if(v.getId() == R.id.tvListFrom){
				lsnrDatePicker = lsnrDatePickerFrom;
			} else {
				lsnrDatePicker = lsnrDatePickerTo;
			}
			new DatePickerDialog(getActivity()
					,lsnrDatePicker
					,CalendarUtil.getYear(cal)
					,CalendarUtil.getMonth(cal) - 1
					,CalendarUtil.getDate(cal)).show();
		}
	};

	/**
	 * DatePicker変更ボタン押下イベント
	 */
	DatePickerDialog.OnDateSetListener lsnrDatePickerFrom = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			tvListFrom.setText(String.valueOf(year) + "-" + String.format("%1$02d", (monthOfYear + 1)) + "-" + String.format("%1$02d", dayOfMonth));
			reload();
		}
	};
	/**
	 * DatePicker変更ボタン押下イベント
	 */
	DatePickerDialog.OnDateSetListener lsnrDatePickerTo = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			tvListTo.setText(String.valueOf(year) + "-" + String.format("%1$02d", (monthOfYear + 1)) + "-" + String.format("%1$02d", dayOfMonth));
			reload();
		}
	};
	
	/**
	 * 検索小窓用の円の押下イベント
	 */
	OnClickListener lsnrSearchCircle = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(llListSearch.getVisibility() == View.GONE){
				llListSearch.setVisibility(View.VISIBLE);
			} else {
				llListSearch.setVisibility(View.GONE);
			}
		}
	};
	
	/**
	 * クリアボタン押下
	 */
	OnClickListener lsnrClear = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.btnListClearFrom){
				tvListFrom.setText("");
			} else if(v.getId() == R.id.btnListClearTo) {
				tvListTo.setText("");
			}
			reload();
		}
	};
	
	/**
	 * レコード削除用のクリックイベント
	 */
	OnClickListener lsnrDelete = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if(v.getTag() != null && v.getTag() instanceof Integer){
				EventDeleteForm form = new EventDeleteForm();
				form.setId((Integer)v.getTag());
				EventDeleteModel model = new EventDeleteModel();
				model.deleteByID(v.getContext(), form);
				deleteAnimation((View)v.getParent());
			} else {
				Log.d("click", "aaa");
			}
		}
	};
	
	/**
	 * アイテムの削除用のアニメーションを行う
	 * @param item	削除対象のView
	 */
	private void deleteAnimation(final View item){
		final int height = item.getMeasuredHeight();
		
		Log.d("deleteAnimation", "height:" + height);
		
		Animation anime = new Animation(){
			@Override
			protected void applyTransformation(float interpolatedTime,
					Transformation t) {
				if(interpolatedTime == 1){
					item.setVisibility(View.GONE);
					item.getLayoutParams().height = height;
				} else {
					item.getLayoutParams().height = height - (int)(interpolatedTime * height);
					item.requestLayout();
				}
			}
			
			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};
		anime.setDuration((int)(height * item.getContext().getResources().getDisplayMetrics().density));
		anime.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				reload();
			}
		});
		item.startAnimation(anime);
	}
	
	/**
	 * ListViewのアイテムを操作するためのアダプタ
	 * @author sheepman
	 */
	private class EventListAdapter extends BaseCustomAdapter {

		/**
		 * ViewHolder
		 * @author sheepman
		 *
		 */
		private class EventListViewHolder{
			boolean flgInfrate = false;
			int id = 0;
		}
		
		//レイアウトをViewに変換するインフレータ
		private LayoutInflater inflater;
		
		/**
		 * コンストラクタ
		 * @param context
		 */
		public EventListAdapter(Context context) {
			super(context);
			this.inflater = getActivity().getLayoutInflater();
		}

		/**
		 * getView
		 */
		@Override
		public View getView(final int position, View currentView, ViewGroup parent) {
			final View item;
			
			Log.d("getView", "getView");
			Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "fonts/mplus-1c-black.ttf");
			
			EventListItemForm data = (EventListItemForm)list.get(position);
			
			//すでにViewが存在する場合はinfrateしない
			if(currentView == null){
				item = inflater.inflate(R.layout.layout_list_row, null);
			} else if (currentView.getTag() instanceof EventListViewHolder){
				//TagにViewHolderを持っていて、再作成フラグが経っていたらinfrateする
				if(((EventListViewHolder)currentView.getTag()).flgInfrate){
					item = inflater.inflate(R.layout.layout_list_row, null);
				} else {
					item = currentView;
				}
			} else {
				item = currentView;
			}
			
			//ViewHolderを初期化してセットする
			EventListViewHolder vh = new EventListViewHolder();
			vh.flgInfrate = false;	//再作成はOFF
			vh.id = data.getId();	//IDはデータのID
			item.setTag(vh);
			
			//日付とイベント名をセット
			((TextView)item.findViewById(R.id.tvListEventDate)).setText(data.getEventDate());
			((TextView)item.findViewById(R.id.tvListEventName)).setText(data.getEventName());
			
			//経過日数表示用のTableLayout
			RelativeLayout tlDateCount = (RelativeLayout)item.findViewById(R.id.tlListDateCount);

			ObjectAnimator.ofFloat(tlDateCount, "translationX", 0.0f, 0.0f).start();
			
			LinearLayout llListItemText = (LinearLayout)item.findViewById(R.id.llListItemText);
			//経過日数表示用のTextView
			TextView tvYear = (TextView)tlDateCount.findViewById(R.id.tvListDateCountYear);
			TextView tvMonth = (TextView)tlDateCount.findViewById(R.id.tvListDateCountMonth);
			TextView tvDay = (TextView)tlDateCount.findViewById(R.id.tvListDateCountDay);
			
			LinearLayout trListDateCount01 = (LinearLayout)tlDateCount.findViewById(R.id.trListDateCount01);
			LinearLayout trListDateCount02 = (LinearLayout)tlDateCount.findViewById(R.id.trListDateCount02);
			LinearLayout trListDateCount03 = (LinearLayout)tlDateCount.findViewById(R.id.trListDateCount03);
			//日数表示のViewを表示状態にする
			trListDateCount01.setVisibility(View.VISIBLE);
			trListDateCount02.setVisibility(View.VISIBLE);
			trListDateCount03.setVisibility(View.VISIBLE);

			Button btnListDeleteItem = (Button)item.findViewById(R.id.btnListDelete);

			((RelativeLayout.LayoutParams)trListDateCount01.getLayoutParams()).setMargins(0, 7, 0, 0);
			((RelativeLayout.LayoutParams)trListDateCount02.getLayoutParams()).setMargins(0, -15, 0, 0);
			((RelativeLayout.LayoutParams)trListDateCount03.getLayoutParams()).setMargins(0, -15, 0, 0);
			
			//経過年数
			if(data.getDateDiffYear() > 0){
				tvYear.setText(String.valueOf(data.getDateDiffYear()));
				tvMonth.setText(String.valueOf(data.getDateDiffMonth()));
			} else {
				tlDateCount.findViewById(R.id.trListDateCount01).setVisibility(View.GONE);
				((RelativeLayout.LayoutParams)tlDateCount.findViewById(R.id.trListDateCount02).getLayoutParams()).setMargins(0, 0, 0, 0);
				if(data.getDateDiffMonth() <= 0){
					tlDateCount.findViewById(R.id.trListDateCount02).setVisibility(View.GONE);
					((RelativeLayout.LayoutParams)tlDateCount.findViewById(R.id.trListDateCount03).getLayoutParams()).setMargins(0, 0, 0, 0);
				} else {
					tvMonth.setText(String.valueOf(data.getDateDiffMonth()));
				}
			}
			//未来か過去で処理分岐
			if(data.isPast()){
				tvDay.setText(String.valueOf(data.getDateDiffDay()));
				tlDateCount.setBackgroundResource(R.drawable.list_item_count);
				llListItemText.setBackgroundResource(R.drawable.list_item);
				//tvPastFlg.setText("経過");
			} else {
				tvDay.setText(String.valueOf(data.getDateDiffDay()));
				tlDateCount.setBackgroundResource(R.drawable.list_item_count_future);
				llListItemText.setBackgroundResource(R.drawable.list_item_future);
			}

			//formをセットする
			llListItemText.setTag(data);
			llListItemText.setOnClickListener(lsnrEventListItem);
			
			//削除ボタン用にIDをセットし、イベントを登録する
			btnListDeleteItem.setTag(data.getId());
			//btnListDeleteItem.setOnClickListener(lsnrDelete);
			btnListDeleteItem.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(v.getTag() != null && v.getTag() instanceof Integer){
						EventDeleteForm form = new EventDeleteForm();
						form.setId((Integer)v.getTag());
						EventDeleteModel model = new EventDeleteModel();
						model.deleteByID(v.getContext(), form);
						deleteAnimation(item);
						((EventListViewHolder)item.getTag()).flgInfrate = true;
					} else {
						Log.d("click", "aaa");
					}
				}
			});

			//フォントをセットする
			tvYear.setTypeface(face);
			tvMonth.setTypeface(face);
			tvDay.setTypeface(face);
			
			tlDateCount.setOnTouchListener(lsnrTouch);
			
			return item;
		}
	}

	/**
	 * コールバック
	 */
	@Override
	public void callback() {
		reload();
	}

	@Override
	public void callback(BaseForm arg0) {
		this.callback();
	}
}
