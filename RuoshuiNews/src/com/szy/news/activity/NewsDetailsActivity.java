package com.szy.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

/**
 *
 */
public class NewsDetailsActivity extends Activity {
	private ViewFlipper mNewsBodyFlipper;
	private LayoutInflater mNewsBodyInflater;
	int count;
	private OnTouchListener newsBodyOnTouchListener=new NewsBodyOnTouchListener() ;
	private float mStartX;
	public final String NEWS = "<p>　　环比全部停涨首次出现</p>\r\n<p>　　数据显示，在新建商品住宅方面，2012年1月全国70个大中城市，价格环比下降的城市有48个，持平的城市有22个，没有一个城市出现上涨。从房价价格指数公布来看，首次出现了新建商品住宅环比全部停涨的现象。</p>\r\n<p>　　从同比看，70个大中城市中，价格下降的城市有15个，比去年12月份增加6个。1月份，同比涨幅回落的城市有50个，涨幅均未超过3.9%。</p>\r\n<p>　　二手住宅</p>\r\n<p>　　仅有5个城市环比上涨</p>\r\n<p>　　从二手房看,与上月相比，70个大中城市中，价格下降的城市有54个，持平的城市有11个。与去年12月份相比，1月份环比价格下降的城市增加了3个。环比上涨的仅5个城市：分别为贵阳、济宁、襄阳、韶关、遵义，均仅上涨0.1%。同比看，70个大中城市中，价格下降的城市有37个，比去年12月份增加了8个。1月份，同比涨幅回落的城市有29个，涨幅均未超过3.5%。</p>\r\n<p>　　北京情况</p>\r\n<p>　　二手房环比同比均下跌</p>\r\n<p>　　数据显示，北京二手房价格不管是环比还是同比，都在下跌，且下跌幅度均有所加大。</p>\r\n<p>　　在环比方面，自去年8月份出现停涨后，二手房价格环比开始下跌，且此后每个月的下跌幅度在不断加大，到2012年1月份，其环比下跌幅度已达到0.9%。而在同比方面，2012年1月份下跌3.1%，创下最大跌幅。</p>\r\n<p>　　北京新建住宅价格指数2010年5月时同比涨幅为22%，而到了2012年1月同比涨幅仅为0.1%。新建商品房价格指数同比涨幅也是连续下跌，下降幅度也比较大，去年年底同比涨幅为1.3%，而到了今年1月份，则同比涨幅下跌为0.1%。在环比方面，继2011年10月首次出现下跌后，环比继续下跌为0.1%。</p>\r\n<p>　　■ 分析</p>\r\n<p>　　北京房价调控成效明显</p>\r\n<p>　　北京中原地产市场总监张大伟认为，北京作为限购执行最严格的城市，房价调控已经见到明显成效。限购导致的直接需求减少，限购抑制投资、投机，在北京出台的最严格版限购下，5年外地户籍限购年限使得购房者回归自住，本地需求占据9成，自住首套占据9成，全市最近一年成交量中投资及投机基本绝迹。</p>\r\n<p>　　同时，买卖双方博弈加剧，限购限贷使得购房者期待价格下调，但是投资手段匮乏、通货膨胀依然是阻碍价格下滑的关键因素。特别是城区部分二手房房主依然坚挺价格，惜售，这使得在价格依然居高不下的情况下，购房者入市谨慎。</p>\r\n<p>　　■ 预测</p>\r\n<p>　　今年房价或下调10%-20%</p>\r\n<p>　　张大伟认为，2012年限购政策不会放松，一线城市楼市拐点已经明确，预期在6-12个月内房价可能还有10%-20%的下调，而且一线城市对全国的示范作用非常大，不仅在政策执行力度上，在房价下调过程中也会明显影响全国。</p>\r\n<p>　　链家地产市场研究部冯联联认为，在楼市调控效果继续巩固的背景下，降价趋势仍会持续。1月份全国多个城市新房市场成交跌入谷底，观望情绪依旧浓重，为加速销售回款，将不断有开发商加入降价换量阵营，新房价格预期会进一步下调。</p>";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newsdetails);
		//
		Button newsDetailsPrev = (Button) findViewById(R.id.newsdetails_titlebar_previous);
		Button newsDetailsNext = (Button) findViewById(R.id.newsdetails_titlebar_next);
		NewsDetailsTitleBarOnClickListenter newsDetailsTitleBarOnClickListenter = new NewsDetailsTitleBarOnClickListenter();
		newsDetailsPrev.setOnClickListener(newsDetailsTitleBarOnClickListenter);
		newsDetailsNext.setOnClickListener(newsDetailsTitleBarOnClickListenter);
		//跟帖按钮
		Button newsDetailsTitleBarComments=(Button)this.findViewById(R.id.newsdetails_titlebar_comments);
		newsDetailsTitleBarComments.setOnClickListener(newsDetailsTitleBarOnClickListenter);
		mNewsBodyInflater = getLayoutInflater();
		mNewsBodyFlipper = (ViewFlipper) this
				.findViewById(R.id.news_body_flipper);

		View newsBodyView = mNewsBodyInflater.inflate(R.layout.news_body, null);
		TextView newsTitle = (TextView) newsBodyView
				.findViewById(R.id.news_body_title);// 注意View对象修改了！
		newsTitle.setText("辽宁机电新闻客户端发布啦");
		TextView newsPtimeAndSource = (TextView) newsBodyView
				.findViewById(R.id.news_body_ptime_source);
		newsPtimeAndSource.setText("来源：机电工作室      2012-03-12 10:21:22");
		TextView newsDetails = (TextView) newsBodyView
				.findViewById(R.id.news_body_details);
		newsDetails.setText(Html.fromHtml(NEWS));
		mNewsBodyFlipper.addView(newsBodyView);
		newsDetails.setFocusable(true);
		newsDetails.setOnTouchListener(newsBodyOnTouchListener);
		
		//
		// View NewsBodyView2=mNewsBodyInflater.inflate(R.layout.news_body,
		// null);
		// //mNewsBodyFlipper=(ViewFlipper)this.findViewById(R.id.news_body_flipper);
		// TextView newsTitle2 =
		// (TextView)NewsBodyView2.findViewById(R.id.news_body_title);
		// newsTitle2.setText("辽宁机电新闻客户端发布啦2");
		// TextView newsPtimeAndSource2 =
		// (TextView)NewsBodyView2.findViewById(R.id.news_body_ptime_source);
		// newsPtimeAndSource2.setText("来源：机电工作室 2     2012-03-12 10:21:22");
		// TextView newsDetails2 =
		// (TextView)NewsBodyView2.findViewById(R.id.news_body_details);
		// newsDetails2.setText(Html.fromHtml(NEWS));
		// mNewsBodyFlipper.addView(NewsBodyView2);

	}

	private void showNext() {
		mNewsBodyFlipper.setInAnimation(this,R.anim.push_left_in);
		mNewsBodyFlipper.setOutAnimation(this,R.anim.push_left_out);
		//
		count++;
		// 点击next按钮时再创建下一屏
		View newsBodyView = mNewsBodyInflater.inflate(R.layout.news_body,
				null);
		TextView newsTitle = (TextView) newsBodyView
				.findViewById(R.id.news_body_title);// 注意View对象修改了！
		newsTitle.setText("辽宁机电新闻客户端发布啦" + count);
		TextView newsPtimeAndSource = (TextView) newsBodyView
				.findViewById(R.id.news_body_ptime_source);
		newsPtimeAndSource.setText("来源：机电工作室      2012-03-12 10:21:22");
		TextView newsDetails = (TextView) newsBodyView
				.findViewById(R.id.news_body_details);
		newsDetails.setText(Html.fromHtml(NEWS));
		mNewsBodyFlipper.addView(newsBodyView);
		newsDetails.setFocusable(true);
		newsDetails.setOnTouchListener(newsBodyOnTouchListener);
		// TODO Auto-generated method stub
		mNewsBodyFlipper.showNext();
	}

	private void showPrev() {
		// TODO Auto-generated method stub
		mNewsBodyFlipper.setInAnimation(this,R.anim.push_right_in);
		mNewsBodyFlipper.setOutAnimation(this,R.anim.push_right_out);
		mNewsBodyFlipper.showPrevious();
	}
	private class NewsDetailsTitleBarOnClickListenter implements
			OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.newsdetails_titlebar_previous:
				showPrev();
				break;
			case R.id.newsdetails_titlebar_next:
				showNext();
				break;
			case R.id.newsdetails_titlebar_comments:
				Intent intent=new Intent(NewsDetailsActivity.this,CommentsActivity.class);
				startActivity(intent);
			default:
				break;
			}
		}
	}

	private class NewsBodyOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mStartX = event.getX();
				break;
			case MotionEvent.ACTION_UP:
				if(event.getX()<mStartX){
					showNext();
				}
				else if(event.getX()>mStartX){
					showPrev();
				}
				break;
			default:
				break;
			}
			return true;
		}
	};	
}
