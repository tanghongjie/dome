package tanghongjie.myapplication.common.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import tanghongjie.myapplication.constast.Constant;


/**
 * 创建时间: 2017/08/20 14:47
 * 作者:lixu
 * 邮箱:rishli@163.com
 * 功能描述: 处理文件相关的工具类
 * 修改时间:
 * 修改描述:
 */
public class FileUtils {

	/**
	 * 目录
	 * @return
	 */
	public  static File getDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

	/**
	 * 以当前时间戳命名生成对应类型文件
	 * @param path 路径
	 * @param suffix 后缀
	 * @return
	 */
	public static File makeFile(String path, String suffix) {
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "." + suffix;
		File out = new File(path);
		if (!out.exists()) {
			out.mkdirs();
		}
		return new File(path+fileName);
	}

	/**
	 * 获取路径，不带文件名，末尾带'/'
	 * @param filePathName
	 * @return
	 */
	public static String getPath(String filePathName) {
		try {
			return filePathName.substring(0, filePathName.lastIndexOf('/') + 1);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 删除文件
	 */
	public static void delete(String filePathName) {
		if(TextUtils.isEmpty(filePathName)){
			return ;
		}

		File file = new File(filePathName);
		if (file.isFile() && file.exists()) {
			boolean flag = file.delete();
			LogUtils.d("filePathName reset:"+filePathName+" flag:"+flag);
		}
	}

	/**
	 * 读取uri所在的图片
	 *
	 * @param uri      图片对应的Uri
	 * @param mContext 上下文对象
	 * @return 获取图像的Bitmap
	 */
	public static Bitmap getBitmapFromUri(Uri uri, Context mContext) {
		try {
			Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 复制文件
	 * @param fromPathName
	 * @param toPathName
	 * @return
	 */
	public static int copy(String fromPathName, String toPathName) {
		try {
			InputStream from = new FileInputStream(fromPathName);
			return copy(from, toPathName);
		} catch (FileNotFoundException e) {
			return -1;
		}
	}

	/**
	 * 复制文件
	 * @param from
	 * @param toPathName
	 * @return
	 */
	public static int copy(InputStream from, String toPathName) {
		try {
			FileUtils.delete(toPathName); //20120925 先删除
			OutputStream to = new FileOutputStream(toPathName);
			byte buf[] = new byte[1024];
			int c;
			while ((c = from.read(buf)) > 0) {
				to.write(buf, 0, c);
			}
			from.close();
			to.close();
			return 0;
		} catch (Exception ex) {
			return -1;
		}
	}


	/**
	 * 获取文件名，带后缀
	 * @param filePathName
	 * @return
	 */
	public static String getName(String filePathName) {
		try {
			return filePathName.substring(filePathName.lastIndexOf('/') + 1);
		} catch (Exception e) {
			return "";
		}
	}

	/***
	 * 根据路径判断文件是否存在
	 * 文件可读
	 * @param path
	 * @return
	 */
	public static boolean isFileExist(String path) {
		if (!TextUtils.isEmpty(path)) {
			File file = new File(path);
            return file.exists() && file.canRead();
		} else {
			return false;
		}
	}

	/***
	 * 将bitmap保存为图片
	 * @param bitmap
	 * @param savePath
	 * @return
	 */
	public static File saveBitmapFile(Bitmap bitmap, String savePath){

		try {
			File file=new File(savePath);//将要保存图片的路径
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();// 创建目录
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();

			return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Uri getImageContentUri(Context context, File imageFile) {
		String filePath = imageFile.getAbsolutePath();
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Images.Media._ID },
				MediaStore.Images.Media.DATA + "=? ",
				new String[] { filePath }, null);

		if (cursor != null && cursor.moveToFirst()) {
			int id = cursor.getInt(cursor
					.getColumnIndex(MediaStore.MediaColumns._ID));
			Uri baseUri = Uri.parse("content://media/external/images/media");
			return Uri.withAppendedPath(baseUri, "" + id);
		} else {
			if (imageFile.exists()) {
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.DATA, filePath);
				return context.getContentResolver().insert(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			} else {
				return null;
			}
		}
	}

	/**
	 * 创建目录，整个路径上的目录都会创建
	 * @param path
	 */
	public static void createDir(String path) {
		try{
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
				LogUtils.d("mkdirs success");
			}
		}catch (Exception e){
			e.printStackTrace();
			//LogUtils.e(e);
		}
	}

	/**
	 *  初始化系统需要的文件夹
	 */
	public static void initDir(){
		//创建sd卡目录
		createDir(Constant.SD_ROOT_PATH);
	}

}
