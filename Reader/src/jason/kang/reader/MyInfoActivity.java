/**
 * 
 */
package jason.kang.reader;

import jason.kang.bean.MyRecord;
import jason.kang.myadapter.MyAdapter;
import jason.kang.reader.R;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 我的资料Activity
 * @author 飞雪无情
 * @since 2011-3-8
 */
public class MyInfoActivity extends Activity {
	List<MyRecord> exampleRecords;
	//RemoteImageHelper lazyImageHelper = new RemoteImageHelper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//TextView textView=new TextView(this);
		//textView.setText("这是我的资料！");
		//setContentView(textView);
		  setContentView(R.layout.home_myinfo);
		//Add some example records
			exampleRecords = new ArrayList<MyRecord>();
			exampleRecords.add(new MyRecord(0, "Mobile Phone 0",
					"http://img10.360buyimg.com/n2/g9/M03/03/10/rBEHaVA67HIIAAAAAAGfjv1prxgAAAr2wPYjK0AAZ-m072.jpg"));
			exampleRecords.add(new MyRecord(1, "Mobile Phone 1",
					"http://img11.360buyimg.com/n2/g5/M02/00/19/rBEIC0-_J5AIAAAAAAFXNd_SpUEAAAJ_wBRAXYAAVdN166.jpg"));
			exampleRecords.add(new MyRecord(2, "Mobile Phone 2",
					"http://img13.360buyimg.com/n2/g9/M03/00/10/rBEHalA0swQIAAAAAAHXHyx_4dUAAAGgQLkv04AAdc3943.jpg"));
			exampleRecords.add(new MyRecord(3, "Mobile Phone 3",
					"http://img12.360buyimg.com/n2/g7/M03/07/0D/rBEHZVBcFIgIAAAAAAGe2oQbuxMAABclQM87zkAAZ7y716.jpg"));
			exampleRecords.add(new MyRecord(4, "Mobile Phone 4",
					"http://img1.icson.com/product/pic160/21/013/21-013-007.jpg"));
			exampleRecords.add(new MyRecord(5, "Mobile Phone 5",
					"http://img2.icson.com/product/pic160/21/187/21-187-776.jpg"));
			exampleRecords.add(new MyRecord(6, "Mobile Phone 6",
					"http://img2.icson.com/product/pic200/21/136/21-136-902.jpg"));
			exampleRecords.add(new MyRecord(7, "Mobile Phone 7\n(Image not available)",
					"http://www.test.com/image/does/not/exist"));
			exampleRecords.add(new MyRecord(8, "Mobile Phone 8",
					"http://img2.icson.com/product/pic160/21/106/21-106-732.jpg"));
			exampleRecords.add(new MyRecord(9, "Mobile Phone 9",
					"http://img1.icson.com/product/pic200/21/587/21-587-157.jpg"));
			exampleRecords.add(new MyRecord(10, "Mobile Phone 10",
					"http://img2.icson.com/product/pic200/21/587/21-587-128.jpg"));

		  ListView list=(ListView)this.findViewById(R.id.listview);
		  
		  MyAdapter adapter=new MyAdapter(this,exampleRecords);
		
		  list.setAdapter(adapter);
//		  TextView text=(TextView)this.findViewById(R.id.txt_title);
//		  text.setText("我的资料");
	}
	
}
