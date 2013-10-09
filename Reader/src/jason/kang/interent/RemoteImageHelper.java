package jason.kang.interent;

import jason.kang.reader.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StatFs;
import android.util.Log;
import android.widget.ImageView;

/**
 * A helper class facilitates loading a remote image into a given ListView.
 * http://www.cnblogs.com/bjzhanghao/archive/2012/11/11/2764970.html
 * 
 * @author zhaokang
 *
 */
public class RemoteImageHelper {

	private final Map<String, Drawable> cache = new HashMap<String, Drawable>();
	private String TAG="TAG";
	private File cacheFile;  

	private static String[] imageFormatSet = new String[] { "jpg", "png", "gif" };
	public void loadImage(final ImageView imageView, final String urlString) {
		loadImage(imageView, urlString, true);
	}

	public void loadImage(final ImageView imageView, final String urlString, boolean useCache) {
		
		getFiles(Environment.getExternalStorageDirectory()+"/cache/"+MD5(urlString)+".jpg");
		
		if (useCache&&cache.containsKey(MD5(urlString))) {
				//如果缓存中存在图片
				imageView.setImageDrawable(cache.get(MD5(urlString)));
				Log.d("TAG","wo zai shiyong huancun");
		}else{
				//如果缓存中不存在图片
			//You may want to show a "Loading" image here
				//首先显示加载状态的图片
			imageView.setImageResource(R.drawable.image_indicator);
	
			Log.d(this.getClass().getSimpleName(), "Image url:" + urlString);
	
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message message) {
					imageView.setImageDrawable((Drawable) message.obj);
				}
			};
				Runnable runnable = new Runnable() {
					public void run() {
						Drawable drawable = null;
						try {
							InputStream is = download(urlString);
							drawable = Drawable.createFromStream(is, "src");
		
							if (drawable != null) {
								//下载成功存入缓存
								cache.put(MD5(urlString), drawable);
							    Log.i("NAME-CUN", MD5(urlString));
								saveBmpToSd(convertDrawableToBitmap(drawable), urlString);
								Log.i(TAG, "wo de Image saved tosd");
								
							}
						} catch (Exception e) {
							//下载图片出错
							Log.e(this.getClass().getSimpleName(), "Image download failed", e);
							//Show "download fail" image 
							drawable = imageView.getResources().getDrawable(R.drawable.image_fail);
						}
						//通知listview进行界面更新
						//Notify UI thread to show this image using Handler
						Message msg = handler.obtainMessage(1, drawable);
						handler.sendMessage(msg);
					}
				};
			new Thread(runnable).start();
		}
	}

	/**
	 * Download image from given url.
	 * Make sure you have "android.permission.INTERNET" permission set in AndroidManifest.xml.
	 * 
	 * @param urlString
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	private InputStream download(String urlString) throws MalformedURLException, IOException {
		InputStream inputStream = (InputStream) new URL(urlString).getContent();
		return inputStream;
	}
	/**
	 * 判断是否为图片文件
	 * 
	 * @author zhaokang
	 * 
	 */
	private boolean isImageFile(String path) {
		// 遍历数组
		for (String format : imageFormatSet) {
			// 判断是否为合法的图片文件
			if (path.contains(format)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 遍历文件读取所有图片
	 * 
	 * @param zhaokang
	 */
	private void getFiles(String url) {
			// 将文件的路径添加到List集合中
		  File file = new File(url);
		  if (file.exists()) {
			 if(!cache.containsKey(url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."))))
			 {
				 cache.put(url.substring(url.lastIndexOf("/")+1,url.lastIndexOf(".")), convertBitmapToDrawable(getBitmapFromSd(url)));			
			 }
			
		}		
	  //  Log.i("NAME-YOU", url.substring(url.lastIndexOf("/")+1,url.lastIndexOf(".")));
	}
	private void saveBmpToSd(Bitmap bm, String url) {
        if (bm == null) {
            Log.w(TAG, " trying to savenull bitmap");
            return;
        }
         //判断sdcard上的空间
//        if (FREE_SD_SPACE_NEEDED_TO_CACHE >freeSpaceOnSd()) {
//            Log.w(TAG, "Low free space onsd, do not cache");
//            return;
//        }
      
		
        File file = new File(Environment.getExternalStorageDirectory()+"/cache/"+ MD5(url)+".jpg");
        try {
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
            Log.i(TAG, "Image saved tosd");
        } catch (FileNotFoundException e) {
            Log.w(TAG,"FileNotFoundException");
        } catch (IOException e) {
            Log.w(TAG,"IOException");
        }
    }
	public Bitmap getBitmapFromSd(String  imgFilePath) {

		FileInputStream fis = null;

		try {

			fis = new FileInputStream(new File(imgFilePath));//文件输入流

			Bitmap bmp = BitmapFactory.decodeStream(fis);

			return bmp;

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}finally{

			try {

				if(fis!=null){

					fis.close();

				}

			} catch (IOException e) {

				e.printStackTrace();

			}
		
		  }
		return null;

		}
	//保存图片到SD卡上
//    /**
//     * 计算sdcard上的剩余空间
//     * @return
//     */
//    private int freeSpaceOnSd() {
//        StatFs stat = new StatFs(Environment.getExternalStorageDirectory() .getPath());
//        double sdFreeMB = ((double)stat.getAvailableBlocks() * (double) stat.getBlockSize()) / MB;
//        
//        return (int) sdFreeMB;
//    }
	
	//Drawable → Bitmap
	public static Bitmap convertDrawableToBitmap(Drawable drawable){
		BitmapDrawable bd = (BitmapDrawable) drawable;
		return bd.getBitmap();
	}
	// Bitmap → Drawable
	public static Drawable convertBitmapToDrawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
	// 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
	return bd;
	}
	   // MD5加密，32位
    public static String MD5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
 
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];
 
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
 
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
