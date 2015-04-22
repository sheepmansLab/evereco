package jp.sheepman.evereco;

import jp.sheepman.evereco.fragment.EventCalendarFragment;
import jp.sheepman.evereco.fragment.EventListFragment;
import jp.sheepman.evereco.fragment.SelectTableFragment;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
	private FragmentManager manager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//FragmentManagerを取得
		manager = getFragmentManager();
		if(savedInstanceState == null){
			//Transactionを開始
			FragmentTransaction tran = manager.beginTransaction();
			//メインフレームにデフォルトのフラグメントを表示
			tran.replace(R.id.frmMainFragment, new EventListFragment());
			//下部フレームにセレクタのフラグメントを表示
			tran.replace(R.id.frmMainSelecter, new SelectTableFragment());
			//Transactionをコミットして反映
			tran.commit();
		}
	}

	/**
	 * Fragmentからの処理を受け付ける
	 * @param layout_id
	 */
	public void callback(int layout_id){
		Fragment fragment;
		switch (layout_id) {
			case R.id.tvSelectTable01:
				//カレンダーのフラグメント
				fragment = new EventCalendarFragment();
				break;
			case R.id.tvSelectTable03:
				//オプション画面
				fragment = null;	//TODO 準備中
				Toast.makeText(this, "準備中", Toast.LENGTH_SHORT).show();
				break;
			case R.id.tvSelectTable04:
				//リスト表示のフラグメント
				fragment = new EventListFragment();
				break;
			default:
				//その他の場合は無視
				fragment = null;
				break;
		}
		//フラグメントがセットされた場合のみ表示を切り替え
		if(fragment != null){
			FragmentTransaction tran  = manager.beginTransaction().replace(R.id.frmMainFragment, fragment);
			tran.addToBackStack(null);
			tran.commit();
		}
	}
}
