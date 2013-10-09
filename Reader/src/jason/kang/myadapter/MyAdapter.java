package jason.kang.myadapter;


import jason.kang.bean.MyRecord;
import jason.kang.interent.RemoteImageHelper;
import jason.kang.reader.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{

	private Context mContext;  
	private List<MyRecord> exampleRecords;
	RemoteImageHelper lazyImageHelper = new RemoteImageHelper();

	public MyAdapter(Context context,List<MyRecord> Records){
		this.exampleRecords=Records;
		this.mContext=context;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return exampleRecords.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return exampleRecords.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder=null;

		if(convertView==null){
			holder=new ViewHolder();
			convertView=LayoutInflater.from(mContext).inflate(R.layout.home_myinfo_list, null);
			holder.img=(ImageView)convertView.findViewById(R.id.img);
			holder.title=(TextView)convertView.findViewById(R.id.lblLabel);
			convertView.setTag(holder);	
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		holder.title.setText(exampleRecords.get(position).getLabel());
		lazyImageHelper.loadImage(holder.img, exampleRecords.get(position).getImageUrl(),true);
		return convertView;
	}
	public final class ViewHolder {    
	    public ImageView img;    
	    public TextView title;    
	   
	} 
}
 
