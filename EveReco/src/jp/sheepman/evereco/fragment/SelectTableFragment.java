package jp.sheepman.evereco.fragment;

import jp.sheepman.common.form.BaseForm;
import jp.sheepman.common.fragment.BaseFragment;
import jp.sheepman.evereco.MainActivity;
import jp.sheepman.evereco.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class SelectTableFragment extends BaseFragment implements OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_select_table, null);
		TextView tvMenuCal = (TextView)v.findViewById(R.id.tvSelectTable01);
		TextView tvMenuOpt = (TextView)v.findViewById(R.id.tvSelectTable03);
		TextView tvMenuList = (TextView)v.findViewById(R.id.tvSelectTable04);
		
		tvMenuCal.setOnClickListener(this);
		tvMenuList.setOnClickListener(this);
		tvMenuOpt.setOnClickListener(this);
		return v;
	}

	@Override
	public void onClick(View v) {
		MainActivity parent = (MainActivity)getActivity();
		parent.callback(v.getId());
	}

	@Override
	public void callback() {
	}

	@Override
	public void callback(BaseForm arg0) {
		this.callback();
	}
}
