package jason.kang.reader;


import java.io.File;

import jason.kang.reader.R;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ViewFlipper;
import android.widget.RadioGroup.OnCheckedChangeListener;
/**
 * 防新浪微博底部工具栏的TabActivity。Android开发技术交流群86686524欢迎大家交流学习
 * @author 飞雪无情
 * @since 2011-3-8
 */
public class MainActivity extends ActivityGroup implements OnCheckedChangeListener{
	public static MainActivity mainactivity;//
    private ViewFlipper container;//
    private File cacheFile;  
    private RadioGroup radio_group;//
	private RadioButton mRadioButton0;
	//内容Intent
//	private Intent mHomeIntent;
//	private Intent mNewsIntent;
//	private Intent mInfoIntent;
//	private Intent mSearchIntent;
//	private Intent mMoreIntent;
//	
//	private final static String TAB_TAG_HOME="tab_tag_home";
//	private final static String TAB_TAG_NEWS="tab_tag_news";
//	private final static String TAB_TAG_INFO="tab_tag_info";
//	private final static String TAB_TAG_SEARCH="tab_tag_search";
//	private final static String TAB_TAG_MORE="tab_tag_more";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tabhost);
        //创建缓存目录，系统一运行就得创建缓存目录的，  
        cacheFile = new File(Environment.getExternalStorageDirectory()+"/cache");  
          
        if(!cacheFile.exists()){  
        	cacheFile.mkdirs();  
        }  
        container = (ViewFlipper) findViewById(R.id.container);
        mRadioButton0=(RadioButton)findViewById(R.id.radio_button0);	
        mRadioButton0.setBackgroundResource(R.drawable.home_btn_bg_d);
        radio_group=(RadioGroup)findViewById(R.id.radio_group);
        radio_group.setOnCheckedChangeListener(this);
        switchActivity(0);

    }
    
    private void switchActivity(int position) {
        Intent intent = null;
        switch (position) {
        case 0:
            intent = new Intent(this, HomeActivity.class);
            break;
        case 1:
            intent = new Intent(this, NewsActivity.class);
            break;
        case 2:
            intent = new Intent(this, MyInfoActivity.class);
            break;
        case 3:
            intent = new Intent(this, SearchActivity.class);
            break;
        case 4:
            intent = new Intent(this, MoreActivity.class);
            break;
        }
        container.removeAllViews();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Window subActivity = getLocalActivityManager().startActivity("subActivity", intent);
        container.addView(subActivity.getDecorView(), new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
    }

    
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.id.radio_button0:
			switchActivity(0);
			break;
		case R.id.radio_button1:
			switchActivity(1);
			mRadioButton0.setBackgroundResource(R.drawable.home_btn_bg);
			break;
		case R.id.radio_button2:
			switchActivity(2);
			mRadioButton0.setBackgroundResource(R.drawable.home_btn_bg);
			break;
		case R.id.radio_button3:
			switchActivity(3);
			mRadioButton0.setBackgroundResource(R.drawable.home_btn_bg);
			break;
		case R.id.radio_button4:
			switchActivity(4);
			mRadioButton0.setBackgroundResource(R.drawable.home_btn_bg);
			break;
		}
	}
    
    
}