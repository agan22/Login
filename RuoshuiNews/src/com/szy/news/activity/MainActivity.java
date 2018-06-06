package com.szy.news.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import com.szy.news.custom.Category;
import com.szy.news.custom.CustomSimpleAdapter;
import com.szy.news.service.SyncHttp;
import com.szy.news.util.DensityUtil;
import com.szy.news.util.StringUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.*;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class MainActivity extends Activity {
	GridView category;
	Button categoryArrowRight;
	ListView newsList;
	
	private final int COLUMNWIDTHPX=55;
	private int mColumnWidthDip;
	private final int FLINGVELOCITYPX=500;
	private int mFingVelocityDip;
	private final int NEWSCOUNT=5;
	
    //默认选中的新闻分类
    int mCid=1;
	List<HashMap<String,Object>> newsData;
	SimpleAdapter newsListAdapter;
	private LayoutInflater mInflater;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mInflater=this.getLayoutInflater();
        newsData=new ArrayList<HashMap<String,Object>>();
        mColumnWidthDip=DensityUtil.px2dip(this, COLUMNWIDTHPX);
        mFingVelocityDip=DensityUtil.px2dip(this,FLINGVELOCITYPX );
        
    	//从array.xml获取新闻分类
		String[] categoryArray = getResources().getStringArray(R.array.categories);
		//把新闻分类保存到List中
		final List<HashMap<String, Category>> categories = new ArrayList<HashMap<String, Category>>();
		//分割新闻类型字符串
		for(int i=0;i<categoryArray.length;i++)
		{
			String[] temp = categoryArray[i].split("[|]");
			if (temp.length==2)
			{
				int cid = StringUtil.String2Int(temp[0]);
				String title = temp[1];
				Category type = new Category(cid, title);
				HashMap<String, Category> hashMap = new HashMap<String, Category>();
				hashMap.put("category_title", type);
				categories.add(hashMap);
			}
		}
		CustomSimpleAdapter categoryAdapter=new CustomSimpleAdapter(this,categories,R.layout.category_title,new String[]{"category_title"},new int[]{R.id.category_title});
        category=new GridView(this);//动态创建
        category.setColumnWidth(mColumnWidthDip);
        category.setNumColumns(GridView.AUTO_FIT);
        category.setGravity(Gravity.CENTER);
        //
        category.setSelector(new ColorDrawable(Color.TRANSPARENT));
        //
        int width=mColumnWidthDip*categories.size();
        LayoutParams params=new LayoutParams(width,LayoutParams.WRAP_CONTENT);
        category.setLayoutParams(params);
        category.setAdapter(categoryAdapter);
        
        LinearLayout categoryLayout=(LinearLayout)this.findViewById(R.id.category_layout);
        categoryLayout.addView(category);
        
        //
        category.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//先遍历恢复颜色
				TextView categoryTitle=(TextView)view;
				for (int i = 0; i < parent.getCount(); i++) {
					categoryTitle=(TextView)parent.getChildAt(i);
					//categoryTitle.setTextColor(R.color.category_title_normal_background);//有一定问题,此处不能从xml文件中读取颜色
					categoryTitle.setTextColor(0XFFADB2AD);
					categoryTitle.setBackgroundDrawable(null);
				}
				categoryTitle=(TextView)view;
				categoryTitle.setTextColor(0XFFFFFFFF);
				categoryTitle.setBackgroundResource(R.drawable.categorybar_item_background);//背景图片
				Toast.makeText(MainActivity.this, categoryTitle.getText(), Toast.LENGTH_SHORT).show();
				mCid=categories.get(position).get("category_title").getCid();
				System.out.println("----oriented----"+newsData.size());
				//newsData=getSpeCateNews(mCid);
				getSpeCateNews(mCid, newsData,0,true);
				System.out.println("-----Now---"+newsData.size());
				newsListAdapter.notifyDataSetChanged();//可以在修改适配器绑定的数组后,不用重新刷新Activity,通知Activity更新ListView
			}
		});
        //此处调用自定义的CustomSimpleAdapter，来完成初始界面的渲染效果
        final HorizontalScrollView categoryScrollview=(HorizontalScrollView)this.findViewById(R.id.category_scrollview);
        categoryArrowRight=(Button)this.findViewById(R.id.category_arrow_right);
        categoryArrowRight.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				categoryScrollview.fling(mFingVelocityDip);
			}
		});
        //填充ListView，显示新闻列表
//        List<HashMap<String,String>> newsData=new ArrayList<HashMap<String,String>>();
//        for (int i = 0; i < 10; i++) { //每页显示10条信息
//			HashMap<String,String> hashMap=new HashMap<String, String>();
//			hashMap.put("newslist_item_title","辽宁机电新闻端发布啦！");
//			hashMap.put("newslist_item_digest","这里是摘要");
//			hashMap.put("newslist_item_source","发自：辽宁机电新闻工作室");
//			hashMap.put("newslist_item_ptime","2013-12-27");
//			newsData.add(hashMap);
//		}

      //  newsData=this.getSpeCateNews(mCid);
        this.getSpeCateNews(mCid, newsData,0,true);
        newsListAdapter=new SimpleAdapter(this, newsData, R.layout.newslist_item, 
        		new String[]{"newslist_item_title","newslist_item_digest","newslist_item_source","newslist_item_ptime"}, 
        		new int[]{R.id.newslist_item_title,R.id.newslist_item_digest,R.id.newslist_item_source,R.id.newslist_item_ptime});
        
        newsList=(ListView)this.findViewById(R.id.news_list);
        //增加“加载更多”提示View
        View footerView=mInflater.inflate(R.layout.loadmore,null);
        newsList.addFooterView(footerView);
        newsList.setAdapter(newsListAdapter);
        
        //点击ListView，打开NewsDetailsActivity窗口
        newsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(MainActivity.this,NewsDetailsActivity.class);
				startActivity(intent);
			}
        	
		});
        Button loadMoreBtn=(Button)this.findViewById(R.id.loadmore_btn);
        loadMoreBtn.setOnClickListener(loadMoreListener);
    }

	/**
	 * 获取指定类型的新闻列表
	 * @param cid 类型ID
	 * @param newsList 保存新闻信息的集合
	 * @param startnid 分页
	 * @param firstTimes	是否第一次加载
	 */
	private void getSpeCateNews(int cid,List<HashMap<String, Object>> newsList,int startnid,Boolean firstTimes) {
		//List<HashMap<String, Object>> newsList = new ArrayList<HashMap<String, Object>>();
		if (firstTimes)
		{
			//如果是第一次，则清空集合里数据
			newsList.clear();
		}
		//请求URL和字符串
		String url = "http://10.0.2.2:8080/web/getSpecifyCategoryNews";
		String params = "startnid="+startnid+"&count="+NEWSCOUNT+"&cid="+cid;
		SyncHttp syncHttp = new SyncHttp();
		try
		{
			//以Get方式请求，并获得返回结果
			String retStr = syncHttp.httpGet(url, params);
			JSONObject jsonObject = new JSONObject(retStr);
			//获取返回码，0表示成功
			int retCode = jsonObject.getInt("ret");
			if (0==retCode)
			{
				JSONObject dataObject = jsonObject.getJSONObject("data");
				//获取返回数目
				int totalnum = dataObject.getInt("totalnum");
				if (totalnum>0)
				{
					//获取返回新闻集合
					JSONArray newslist = dataObject.getJSONArray("newslist");
					for(int i=0;i<newslist.length();i++)
					{
						JSONObject newsObject = (JSONObject)newslist.opt(i); 
						HashMap<String, Object> hashMap = new HashMap<String, Object>();//一个新闻的所有信息
						hashMap.put("nid", newsObject.getInt("nid"));
						hashMap.put("newslist_item_title", newsObject.getString("title"));
						hashMap.put("newslist_item_digest", newsObject.getString("digest"));
						hashMap.put("newslist_item_source", newsObject.getString("source"));
						hashMap.put("newslist_item_ptime", newsObject.getString("ptime"));
						newsList.add(hashMap);//放入新闻信息列表（当前全部新闻）
					}
				}
				else
				{
					if (firstTimes)
					{
						Toast.makeText(MainActivity.this, "该栏目下暂时没有新闻", Toast.LENGTH_LONG).show();
					}
					else
					{
						Toast.makeText(MainActivity.this, "该栏目下没有更多新闻", Toast.LENGTH_LONG).show();
					}
				}
			}
			else
			{
				Toast.makeText(MainActivity.this, "获取新闻失败", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			Toast.makeText(MainActivity.this, "获取新闻失败", Toast.LENGTH_LONG).show();
		}
	//	return newsList;
	}
	private OnClickListener loadMoreListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			//获取该栏目下新闻
			getSpeCateNews(mCid,newsData,newsData.size(),false);//接下来更多的新闻
			//通知ListView进行更新
			newsListAdapter.notifyDataSetChanged();
		}
	};
}