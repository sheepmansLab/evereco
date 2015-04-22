package jp.sheepman.evereco.fragment;

import java.util.Calendar;

import jp.sheepman.common.fragment.BaseDialogFragment;
import jp.sheepman.common.fragment.BaseFragment;
import jp.sheepman.common.util.CalendarUtil;
import jp.sheepman.evereco.R;
import jp.sheepman.evereco.bean.form.EventDeleteForm;
import jp.sheepman.evereco.bean.form.EventInputForm;
import jp.sheepman.evereco.bean.form.EventInsertForm;
import jp.sheepman.evereco.model.EventDeleteModel;
import jp.sheepman.evereco.model.EventInsertModel;
import jp.sheepman.evereco.model.EventSelectModel;
import jp.sheepman.evereco.model.EventUpdateModel;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EventInputDialogFragment extends BaseDialogFragment {
	private int id;
	
	private EditText etEventName;
	private TextView tvEventDate;
	private EditText etEventComment;
	private Button btnSubmit;
	private Button btnDelete;
	private Button btnClear;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		//引数を取得
		Bundle args = getArguments();
		if(args != null){
			id = args.getInt("event_id");
		}
		View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_input, null);

		//コンポーネントを保持
		etEventName = (EditText)v.findViewById(R.id.txtEventName);
		tvEventDate = (TextView)v.findViewById(R.id.tvInputEventDate);
		etEventComment = (EditText)v.findViewById(R.id.txtComment);
		btnSubmit = (Button)v.findViewById(R.id.btnInputSubmit);
		btnDelete = (Button)v.findViewById(R.id.btnInputDelete);
		btnClear = (Button)v.findViewById(R.id.btnInputClear);
		
		if(savedInstanceState == null){
			setContents();
		}
		//DialogのBuilder
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
											.setView(v)
											.setPositiveButton("閉じる", lsnrClose);
		//ボタンにクリックイベントをセット
		//日付入力欄
		tvEventDate.setOnClickListener(lsnrDateEdit);
		//入力ボタン
		btnSubmit.setOnClickListener(lsnrInsert);
		//クリアボタン
		btnClear.setOnClickListener(lsnrClear);
		//削除ボタン
		btnDelete.setOnClickListener(lsnrDelete);

		return builder.create();
	}
	
	/**
	 * 画面に初期表示をさせる
	 */
	private void setContents(){
		if(id > 0){
			EventSelectModel model = new EventSelectModel();
			EventInputForm form = model.selectById(getActivity(), id);
			if(form != null){
				etEventName.setText(form.getEventName());
				tvEventDate.setText(form.getEventDate());
				etEventComment.setText(form.getComment());
				//ボタン
				btnDelete.setVisibility(View.VISIBLE);
				btnSubmit.setVisibility(View.VISIBLE);
				btnClear.setVisibility(View.GONE);
			} 
		} else {
			String event_date = null;
			Bundle args = getArguments();
			clearContents();
			if(args != null){
				event_date = args.getString("event_date");
				if(event_date.length() > 0){
					tvEventDate.setText(event_date);
				}
			}
		}
	}

	/**
	 * 入力項目をクリアする
	 */
	private void clearContents(){
		etEventName.setText("");
		tvEventDate.setText("");
		etEventComment.setText("");
		id = 0;
		//ボタン
		btnDelete.setVisibility(View.GONE);
		btnSubmit.setVisibility(View.VISIBLE);
		btnClear.setVisibility(View.VISIBLE);
	}
	
	/**
	 * バリデート処理
	 * @return
	 */
	private boolean validate(){
		boolean ret = true;
		//イベント名チェック
		if(ret && etEventName.getText().length() < 1){
			ret = false;
		}
		//日付チェック
		if(ret && (tvEventDate.getText().length() < 1)){
			ret = false;
		}
		return ret;
	}
	
	/**
	 * ダイアログのクローズ
	 */
	@Override
	public void onDismiss(DialogInterface dialog) {
		if(getTargetFragment() != null && getTargetFragment() instanceof BaseFragment){
			BaseFragment target = (BaseFragment)getTargetFragment();
			target.callback();
		}
		super.onDismiss(dialog);
	}
	
	/**
	 *  クローズボタン押下イベント
	 */
	private OnClickListener lsnrClose = new OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			onDismiss(getDialog());
		}
	};

	/**
	 * DatePicker変更ボタン押下イベント
	 */
	DatePickerDialog.OnDateSetListener lsnrDatePicker = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			tvEventDate.setText(String.valueOf(year) 
						+ "-" + String.format("%1$02d", (monthOfYear + 1))
						+ "-" + String.format("%1$02d", dayOfMonth));
		}
	};
	
	/** 
	 * 日付変更ボタン押下イベント
	 */
	private View.OnClickListener lsnrDateEdit = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			Calendar cal;
			if(tvEventDate.getText().length() > 0){
				cal = CalendarUtil.str2cal(tvEventDate.getText().toString());
			} else {
				cal = Calendar.getInstance();
			}
			new DatePickerDialog(getActivity(), lsnrDatePicker
												,CalendarUtil.getYear(cal)
												,CalendarUtil.getMonth(cal)-1
												,CalendarUtil.getDate(cal)).show();
		}
	};
	
	/**
	 * 入力ボタン押下イベント
	 */
	private android.view.View.OnClickListener lsnrInsert = new  android.view.View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(validate()){
				//入力項目のform
				EventInsertForm form = new EventInsertForm();

				//イベント名
				form.setEventName(etEventName.getText().toString());
				//イベント日付をセット(yyyy-MM-dd)
				form.setEventDate(tvEventDate.getText().toString());
				//コメントをセット
				form.setComment(etEventComment.getText().toString());
				
				if(id == 0){
					//入力用のModel
					EventInsertModel model = new EventInsertModel();
					
					//Insert処理
					model.insert(getActivity(), form);
					Toast.makeText(v.getContext(), "登録しました", Toast.LENGTH_SHORT).show();	
				} else {
					//入力用のModel
					EventUpdateModel model = new EventUpdateModel();
					form.setId(id);
					//Update処理
					model.update(getActivity(), form);
					Toast.makeText(v.getContext(), "更新しました", Toast.LENGTH_SHORT).show();	
				}
				
				onDismiss(getDialog());
			} else {
				Toast.makeText(getActivity(), "入力項目に不足があります", Toast.LENGTH_SHORT).show();
			}
		}
	};
	
	/**
	 * 削除ボタン押下イベント
	 */
	private android.view.View.OnClickListener lsnrDelete = new  android.view.View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if(id > 0){
				EventDeleteModel model = new EventDeleteModel();
				EventDeleteForm form = new EventDeleteForm();
				form.setId(id);
				
				model.deleteByID(v.getContext(), form);
				
				Toast.makeText(v.getContext(), "削除しました", Toast.LENGTH_SHORT).show();
				onDismiss(getDialog());
			} else {
				Toast.makeText(v.getContext(), "削除対象がありません", Toast.LENGTH_SHORT).show();
			}
		}
	};

	/**
	 * クリアボタン押下イベント
	 */
	private android.view.View.OnClickListener lsnrClear = new android.view.View.OnClickListener() {
		@Override
		public void onClick(View v) {
			clearContents();
		}
	};

	@Override
	public void callback() {
	}
}
