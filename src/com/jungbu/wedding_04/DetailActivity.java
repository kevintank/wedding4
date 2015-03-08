package com.jungbu.wedding_04;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

  
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
 
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

 
 

public class DetailActivity extends FragmentActivity  {

	    private ViewPager viewPager;
	    private static int mCnt = 9;  //사진의 갯수 
	    
	    
	    HorizontalScrollView hv;

	    private Handler handler;
	  
	    private LinearLayout m_li;
	    ImageView imageView;
	    private ImageView m_btnArray[]=null;   			
	    public int m_category = 0;   
	    private ImageButton button1;
	    boolean pon;
	    private int mPoint;
		Bitmap bm1, bm2, bm3;
		Bitmap resultBmp, resized;
		Gallery gallery;
		
		private ProgressDialog m_pbProgrss = null;
		
		Bitmap[] arrBit;  
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	    	
	    	super.onCreate(savedInstanceState);
	    	setContentView(R.layout.detail_activity);
 
	    	UISetFromData();

	    }
	    
	  
	    /**
	     * 꼼 메모리를 해제해 주세요 
	     * 
	     */
	    protected void onDestroy() {
	   
	     	
	    	 if(bm1 != null)
	    		 bm1.recycle();
	          if(bm2 != null)
	    		  bm2.recycle();
	    	  if(bm3 != null)
	    	  	  bm3.recycle();
	    	
	    	  if(resultBmp != null)
	    	      resultBmp.recycle(); 
	    	  if(resized != null)
	    		  resized.recycle();
	    	  
	    	  if(arrBit != null)
	    		   arrRecycle(); 
	    	  
	    	super.onDestroy();
	    	
	    };
	    
	    private void arrRecycle(){
	    	
           int cnt 	= arrBit.length;
           
           for(int i=0 ; i < cnt; i++){
        	   
        	   arrBit[i].recycle();
           }
           
	    }

	 
		
	/*    public void loadDataFromServer()
		{
		 
			AsyncHttpClient myClient = new AsyncHttpClient();
			myClient.setTimeout(DEFAULT_SOCKET_TIMEOUT);
			
			//?�라메�? �?��
			RequestParams params = new RequestParams();
			params.put("CateUid", mCateUid);// 기기?�이
			
			//쿠키 �?��
			PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
			myClient.setCookieStore(myCookieStore);
			BasicClientCookie newCookie = new BasicClientCookie("cookiesare", "awesome");
			newCookie.setVersion(1);
			newCookie.setDomain(StaticStringInfo.server_info);
			newCookie.setPath("/");
			myCookieStore.addCookie(newCookie);


			myClient.post(StaticStringInfo.server_info + "/Diet/Images", params, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(Throwable error, String content) {
	 
					//SimpleToast(getString(R.string.msg_internet_retry));	
					m_pbProgrss.dismiss();
					super.onFailure(error, content);
				}

			 
				@Override
				public void onSuccess(String response) {

				 
					try {
						
						InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
						String strRes =  convertStreamToString(is);
	 				    JSONObject json = new JSONObject(strRes);
					 
	 				    int tot  = json.getJSONArray("Item").length();
	 				    
	 				  //  Log.d("DEBUG","--------------------" + tot);
	 				    
						for(int i=0; i< tot ; i++)
						{
							JSONObject aObject = json.getJSONArray("Item").getJSONObject(i);
							
							Detail_Item_Data data = new Detail_Item_Data();

							data.Guid     = aObject.getString("Guid");
							data.Title    = aObject.getString("Title");
							data.Content  = aObject.getString("Content");
							data.Img      = aObject.getString("Img");
							data.Date     = aObject.getString("Date");
							
							normalItemList.add(data);
						}
						
					 
						m_pbProgrss.dismiss();
						if(normalItemList.size() > 0){
							
						   arrBit = new Bitmap[normalItemList.size()];
						   img(); // UISetFromData();
						}
						 
							  //SimpleToast(getString(R.string.msg_internet_retry) + "0x01");	
						
					} catch (UnsupportedEncodingException e) {
						m_pbProgrss.dismiss();
						e.printStackTrace();
					  
					} catch (JSONException e) {
						m_pbProgrss.dismiss();
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//?�레?�후 창닫�?
					//mHandler.postDelayed(new  Runnable() {@Override public void run() { mHandler.sendEmptyMessage(0);}}, 3000);
				}
			});
		}*/
	 
	    
	 // CONVERT HTTP STREAM TO STRING //

/*		private static String convertStreamToString(InputStream is) {

			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;

			try{
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			}
			catch (IOException e) { 
				e.printStackTrace(); 
			}finally {
				try { is.close(); }
				catch (IOException e) { e.printStackTrace(); }
			}
			return sb.toString();
		}*/

	/*	public void img(){
			
			new ImgDown().execute();
			
		}*/

		public void UISetFromData()
		{
		
		  viewPager = (ViewPager) findViewById(R.id.pager);
			 
		  viewPager.setAdapter(new MyAdapter(getApplicationContext()));
	 
		  viewPager.requestLayout();
		  
		   
		  viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			    
				public void onPageSelected(int position) {
			         
			    	 ImageCountViewReset(position);   
			    }
				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
				}
				@Override
				public void onPageScrolled(int position, float offset, int offsetPixel) {
				
				}
				
			});
			
		   
			
		   viewPager.setOnTouchListener(new OnTouchListener() {
				
				public boolean onTouch(View v, MotionEvent event) {
					
			        return false;
				}
				
			});
		 
		   
		   //하단 갤러리 생성
		   gallery = (Gallery)findViewById(R.id.gallery);
		   gallery.setAdapter(new ImageAdapter(this));

		   gallery.setOnItemClickListener(new OnItemClickListener() {
			   @Override
			   public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				   
				   //Toast.makeText(GalleryActivity.this, ""+position, Toast.LENGTH_SHORT).show();
				   
				    viewPager.setCurrentItem(position);
			   }
		   });
		}

 
		 
		private void ImageCountViewReset(int i) {
		    	
			   mPoint = i;   
			   gallery.setSelection(i);
			  
		    }
		   
 
		private void updateUI( int i) {
		    	
			   mPoint = i;   
			   viewPager.setCurrentItem(i);
		        
		}
		   
		   
/*		View.OnClickListener eventlistener = new OnClickListener() 
		{

			public void onClick(View v) 
			{
				m_category = v.getId();  
				
				updateUI(m_category);
				  
			}
		};
		*/
		
		/**
		 * 갤러리에서 사용  
		 * @author KevinNote
		 *
		 */
		public class ImageAdapter extends BaseAdapter {
			 private Context mContext;
             private int background;
             
			 public ImageAdapter(Context c) {
				 
				 mContext = c;
				 TypedArray a = obtainStyledAttributes(R.styleable.Theme);
				 background = a.getResourceId(R.styleable.Theme_android_galleryItemBackground, 0);
                 
				 a.recycle();
				 
			    
			 }
			 @Override
			 public int getCount() {
			  return mCnt;
			 }
			 
			 @Override
			 public Object getItem(int position) {
			  return position; 
			 }
			
			 @Override
			 public long getItemId(int position) {
			  return position;
			 }
			 
			 
			 @Override
			 public View getView(int position, View convertView, ViewGroup parent) {  
			  
				 ImageView imageView;

				 if(convertView == null){
					 imageView = new ImageView(mContext);
					 imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
					 imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));
					 imageView.setPadding(2,2,2,2);
				 }else{
					 imageView = (ImageView)convertView;
				 }
			  
				 
				 //리소스 아이디 얻어 이미지 뷰에 불러 온다.
	             int resId = StringToResId("p"+(position+1), "drawable"); 
				 imageView.setImageResource(resId);
				 //imageView.setBackgroundResource(background);	
					
			     // imageView.setImageBitmap(resId);
			  
			     // ImageLoader.DisplayImage(normalItemList.get(position).Img, imageView, R.drawable.btn_brand1_0);  //배경 
			     
			  return imageView;
			 }
	  }
			 
		/**
		 * 뷰페페이져 에서 사용 
		 * @author Kevin
		 *
		 */
		
		public class MyAdapter extends PagerAdapter {

			private LayoutInflater mInflater;
			
			public MyAdapter(Context c) {
				super();
				mInflater = LayoutInflater.from(c);
			}
			
			@Override
			public void destroyItem(ViewGroup pager, int position,	Object view) {
				// TODO Auto-generated method stub
			//	super.destroyItem(container, position, object);
			
				((ViewPager)pager).removeView((View)view); 
				
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mCnt;
			}

			@Override
			public Object instantiateItem(View pager, int position) {
				// v = null;
				// if(position==0){

				View v = mInflater.inflate(R.layout.item, null);
				imageView = (ImageView) v.findViewById(R.id.imageView1);
				//imageView.setScaleType(ImageView.ScaleType.CENTER);
				
			    //리소스 아이디 얻어 이미지 뷰에 불러 온다.
                int resId = StringToResId("p"+(position+1), "drawable"); 
				imageView.setImageResource(resId);
				
				//클릭시 지정
				Button button = (Button) v.findViewById(R.id.button1);
				button.setOnClickListener(banner_listener);
				button.setTag(position);

				((ViewPager)pager).addView(v, ((ViewPager)pager).getChildCount() > position ? position : ((ViewPager)pager).getChildCount());
				
				//((ViewPager) pager).addView(v, 0);
				return v;
			}
			
			@Override
			public void destroyItem(View pager, int position, Object view) {
				((ViewPager) pager).removeView((View) view);
			}
			
			@Override
			public boolean isViewFromObject(View pager, Object obj) {
				// TODO Auto-generated method stub
				return pager == obj;
			}
	        
	    }
		 
		Button.OnClickListener banner_listener = new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Button button = (Button) v;
				Integer tag = (Integer) button.getTag();
			}
		};
		
/*	 	private void AIAW(){
	 		 
			LayoutInflater inflater = (LayoutInflater)DetailActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.popup_window, (ViewGroup)findViewById(R.id.popup));
			
			pw = new PopupWindow(layout, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, false); //4번째 ?�자??false?�야 꺼짐
			//pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
		 
			pw.setOutsideTouchable(true);
			pw.showAsDropDown(layout, 0, 0);
			 
			
			final View pwv = pw.getContentView();

			
			//?�동 ?�하??리스??
	      	pwv.setOnKeyListener(new View.OnKeyListener() { 
				@Override
				public boolean onKey(View arg0, int keyCode, KeyEvent arg2) {
					
					if(keyCode==KeyEvent.KEYCODE_BACK)
					{

							pw.dismiss();
					}
					return false;
				}
			});  

	     	//?�이?�북
			Button btnDismiss1 = (Button)pwv.findViewById(R.id.button1);
			btnDismiss1.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					
					
					ImageLoader.ChangeRequired_size(480);
					bm1 = ImageLoader.getBitmap2(normalItemList.get(mPoint).Img);
					
				    
			 		bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.save);
			 		
			 		bm3 = overlayMark(bm1,bm2);
			 		
					DataSave save = new DataSave(DetailActivity.this , bm3,"?�예???�이?�트","223642964451321");
					save.facebook(); 
					  
					
				}
				
			});
					
			//카카?�스?�리고유
			Button btnDismiss2 = (Button)pwv.findViewById(R.id.button2);
			btnDismiss2.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					
					StoryLink storyLink = StoryLink.getLink(getApplicationContext());
					// check, intent is available.
					if (!storyLink.isAvailableIntent()) {
						alert("KakaoStory�??�치?�있�??�습?�다.");
						return;
					}

					ImageLoader.ChangeRequired_size(480);
					bm1 = ImageLoader.getBitmap2(normalItemList.get(mPoint).Img);
					
				    
			 		bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.save);
			 		
			 		bm3 = overlayMark(bm1,bm2);

			 	    String url = ImageLoacalSave2(bm3);
			 		
				 
					if(!"".equals(url)){
						storyLink.openStoryLinkImageApp(DetailActivity.this, url);
					}

				}
				
			});
			
			
			//?�범????��?�기
			Button btnDismiss3 = (Button)pwv.findViewById(R.id.button3);
			btnDismiss3.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					//??��

					ImageLoader.ChangeRequired_size(480);
					bm1 = ImageLoader.getBitmap2(normalItemList.get(mPoint).Img);
					
				    
			 		bm2 = BitmapFactory.decodeResource(getResources(), R.drawable.save);
			 		
			 		bm3 = overlayMark(bm1,bm2);

			 		ImageLoacalSave(bm3);
				}
				
			});
			
			
			//종료
			Button btnDismiss4 = (Button)pwv.findViewById(R.id.button4);
			btnDismiss4.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					 
					pw.dismiss();
				}
				
			});
			
			
        } */
	
	/* 	
	 	 private Bitmap decodeFile(File f){
	          
	 		Bitmap bitmap=null;
	 		
	 		 try{
	             
	             BitmapFactory.Options o = new BitmapFactory.Options();
	             o.inJustDecodeBounds = true;
	             FileInputStream stream1=new FileInputStream(f);
	             BitmapFactory.decodeStream(stream1,null,o);
	             stream1.close();
	             
	             //Find the correct scale value. It should be the power of 2.
	             final int REQUIRED_SIZE=480;
	             int width_tmp=o.outWidth, height_tmp=o.outHeight;
	             int scale=1;
	            
	             while(true){
	                 if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
	                     break;
	                 width_tmp/=2;
	                 height_tmp/=2;
	                 scale*=2;
	             }
	             
	             //decode with inSampleSize
	             BitmapFactory.Options o2 = new BitmapFactory.Options();
	             o2.inSampleSize=scale;
	             FileInputStream stream2=new FileInputStream(f);
	             bitmap=BitmapFactory.decodeStream(stream2, null, o2);
	             stream2.close();
	            
	 		 }catch(IOException e){
	 			 
	 			 e.printStackTrace();
	 		 }
	 		 
	 		  return bitmap;
	     }
	 	*/
	 	
	 	public String ImageLoacalSave2(Bitmap bm){  //?�더?�만 ??��

		 	
	 		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");

	 		 String fileName = "";
	 		 fileName += "_";
	 		 fileName += sdf.format(new Date());

	 		 File path = null;

	 		 try {

	 			 path = new File(Environment.getExternalStorageDirectory().getPath().toString()+ "/DietSecret");
	 			 if(!path.isDirectory()) {
	 				 path.mkdirs();
	 			 }
	 			 path = new File(path.getPath().toString() + "/"+fileName+".png");
	 			 
	 			 FileOutputStream out = new FileOutputStream(path.getPath().toString());
	 			 bm.compress(Bitmap.CompressFormat.PNG, 100, out);
 
	 			 ContentValues values = new ContentValues();   
	 			 values.put(MediaStore.Images.Media.DATA, path.getAbsolutePath());
	 			 values.put(MediaStore.Images.Media.TITLE, path.getName());
	 			 values.put(MediaStore.Images.Media.DISPLAY_NAME, path.getName());
	 			 values.put(MediaStore.Images.Media.BUCKET_ID, path.getName());
	 			 values.put(MediaStore.Images.Media.DESCRIPTION, "InterCop");
	 			 values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
	 			 values.put(MediaStore.Images.Media.SIZE, path.length());

	 			 Uri uri =  getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	 			 
	 			 sendBroadcast(new Intent("com.android.camera.NEW_PICTURE", uri));
	 			 
	 		 } catch (FileNotFoundException e) {
	 			 e.printStackTrace();
	 			 path = null;
	 		 }
	 		 
	 		 return path.toString();
	 	 }

	 	
	 	/* public void ImageLoacalSave(Bitmap bm){  //?�전첩에 ?�진??��

	 	
	 		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");

	 		 String fileName = "";
	 		 fileName += "_";
	 		 fileName += sdf.format(new Date());

	 		 File path = null;

	 		 try {

	 			 path = new File(Environment.getExternalStorageDirectory().getPath().toString()+ "/DietSecret");
	 			 if(!path.isDirectory()) {
	 				 path.mkdirs();
	 			 }

	 			 path = new File(path.getPath().toString() + "/"+fileName+".png");

	 			 FileOutputStream out = new FileOutputStream(path.getPath().toString());

	 			 bm.compress(Bitmap.CompressFormat.PNG, 100, out);

	 			 ContentValues values = new ContentValues();   
	 			 values.put(MediaStore.Images.Media.DATA, path.getAbsolutePath());
	 			 values.put(MediaStore.Images.Media.TITLE, path.getName());
	 			 values.put(MediaStore.Images.Media.DISPLAY_NAME, path.getName());
	 			 values.put(MediaStore.Images.Media.BUCKET_ID, path.getName());
	 			 values.put(MediaStore.Images.Media.DESCRIPTION, "InterCop");
	 			 values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
	 			 values.put(MediaStore.Images.Media.SIZE, path.length());

	 			 Uri uri =  getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	 			 Toast.makeText(this, "?�예???�이?�트 ?�더????�� ?�었?�니??, Toast.LENGTH_SHORT).show();

	 			 sendBroadcast(new Intent("com.android.camera.NEW_PICTURE", uri));

	 		 } catch (FileNotFoundException e) {
	 			 e.printStackTrace();
	 			 path = null;
	 		 }
	 	 }*/

	 	private void alert(String message) {
			new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.app_name)
				.setMessage(message)
				.setPositiveButton(android.R.string.ok, null)
				.create().show();
		}

	  
	 	private Bitmap overlayMark(Bitmap baseBmp, Bitmap overlayBmp){
	 		
	 	  resultBmp = Bitmap.createBitmap(baseBmp.getWidth(), baseBmp.getHeight(),  baseBmp.getConfig() );
	 		
	 		 int viewWidth  = baseBmp.getWidth();
	 		 
	 		 int height =  overlayBmp.getHeight();
	 		 
	 		 int width  = overlayBmp.getWidth();
	 		 
	 	  resized = null;
	 		
	 		while ( width > viewWidth) {
	 			 
	 		   float percente = (float)(width / 100);
	 		   float scale = (float)(viewWidth / percente);
	 		   width *= (scale / 100);
	 		   height *= (scale / 100);
	 		   
	 		 }
	 		  
	 		 resized = Bitmap.createScaledBitmap(overlayBmp, (int) width, (int) height, true);
	 		
	 		 int left = viewWidth / 2;
	 		 int bar = resized.getWidth() /2;
	 		 
	 		 int ileft = left - bar;
	 		 
	 		 Canvas canvas = new Canvas(resultBmp);
	 		 canvas.drawBitmap(baseBmp, new Matrix(), null);
	 		 canvas.drawBitmap(resized, ileft, baseBmp.getHeight()-80 , null);
	 		 
	 		 return resultBmp;
	 		
	 	}
	 	
	 	
	 
	 	 	@Override
	 		public void onBackPressed() 
	 		{
	 	 	 
	 	 		  super.onBackPressed();
	 					 
	 		}
	 		
	 	 	
	 	 	/*private ProgressDialog simpleWaitDialog;


	 	 	public class ImgDown extends AsyncTask{

	 	 		int cnt = normalItemList.size();
	 	 		
				@Override
				protected Object doInBackground(Object... param) {
					// TODO Auto-generated method stub
					return downloadBitmap();
				}
	 	 		
	 	 		
				@Override
	 	 		protected void onPreExecute() {
	 	 			Log.i("Async-Example", "onPreExecute Called");
	 	 			simpleWaitDialog = ProgressDialog.show(DetailActivity.this, "�?���?..", "?�시�?기다??주세??);

	 	 		}

		 
				
				@SuppressWarnings("unchecked")
				@Override
				protected void onPostExecute(Object result) {
					
					UISetFromData();
					simpleWaitDialog.dismiss();
					
					super.onPostExecute(result);
				}
				
	 	 		 
				@Override 
	 	 		protected void onPostExecute(Bitmap result) {
	 	 			Log.i("Async-Example", "onPostExecute Called");
	 	 		 
	 	 			UISetFromData();
	 	 			simpleWaitDialog.dismiss();

	 	 		}

	 	 		private Bitmap downloadBitmap() {
	 	 		 	 
	 	 		   try {

				 
								
						 ImageLoader.ChangeRequired_size(72);
						
						 for(int i=0 ; i < cnt ; i++){
							
							   bm1 = ImageLoader.getBitmap2(normalItemList.get(i).Img);
							 
							   arrBit[i] = bm1;
							   
							}
						
	 	 				
	 	 			} catch (Exception e) {
	 	 				// You Could provide a more explicit error message for IOException
	 	 				
	 	 				Log.e("ImageDownloader", "Something went wrong while");
	 	 						 
	 	 			} 

	 	 			return null;
	 	 		}
	 	 		
	 	 	}
	 	 	*/
	 	 	
	 	 	
/***********************************************************************************************************************************/
/**
* 	
* @param filename
* @param restype
* @return
 */
private int StringToResId(String filename, String restype){
		String pkgname = this.getPackageName();
		int resid = getResources().getIdentifier(filename, restype, pkgname); 
		return resid;
}
	 	  
	 	 
}
