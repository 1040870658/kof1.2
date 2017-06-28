package com.example.ye.kofv12.com.example.subfragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ye.kofv12.R;
import com.example.ye.kofv12.com.example.com.example.adapter.HotNewsPageAdapter;
import com.example.ye.kofv12.com.example.com.example.adapter.OtherAdapter;
import com.example.ye.kofv12.com.example.com.example.adapter.VideoAdapter;
import com.example.ye.kofv12.com.example.com.example.listener.DetailNewsListener;
import com.example.ye.kofv12.com.example.com.example.listener.StartActivityListener;
import com.example.ye.kofv12.com.example.com.example.presenter.NewsPresenter;
import com.example.ye.kofv12.com.example.com.example.util.HoverListDecorator;
import com.example.ye.kofv12.com.example.com.example.util.MyDecoration;
import com.example.ye.kofv12.com.example.com.example.util.RetrieveNews;
import com.example.ye.kofv12.com.example.model.NewsModel;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.example.ye.kofv12.com.example.com.example.listener.NewsScrollListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ye on 2016/9/2.
 */
public class SubFragment_1_1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private String name = "sub1";
    private String commentString;
    public static final String ARG = "TYPE";
    private RecyclerView.Adapter hsv_adapter;
    private HotNewsPageAdapter hotNews_adapter;
    private DisplayMetrics metrics;
    private View contentView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    public static final int REFRESH_COMPLETED = 0x000001;
    public static final int REQUIRE_APPEND = 0x000002;
    public static final int VIDEO=43;
    public static final int IMPORTANT=1;
    public static final int CSL=56;
    public static final int COLLECTION=11;
    public static final int TALK=55;
    public static final int LAUGH=37;
    public static final int SPECIAL=99;
    public static final int ISA=4;
    public static final int SPL=5;
    public static final int EPL=3;
    public static final int GPL=6;
    protected MyDecoration myDecoration;
    private Handler handler = new Sub_1_Handler();
    private List<NewsModel> hotNews;
    private List<NewsModel> news;
    private Thread thread;
    private int type;
    private NewsPresenter newsPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        type = getArguments().getInt(ARG);
        hotNews = new ArrayList();
        news = new ArrayList<>();
        // test_Init(hotNews);
        metrics = new DisplayMetrics();
        commentString = getActivity().getResources().getString(R.string.comment);
        myDecoration = new MyDecoration(getActivity(),MyDecoration.VERTICAL_LIST);
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        super.onCreate(savedInstanceState);
    }


    private RecyclerView.Adapter simpleAdapterFactory(int type){
        if(type == 1){
            return new MyAdapter();
        }
        else if(type == SubFragment_1_1.COLLECTION || type == SubFragment_1_1.VIDEO){
            return  new VideoAdapter(news,metrics,getActivity());
        }
        else{
            return new OtherAdapter(news,metrics,getActivity());
        }
    }
    private void test_Init(List newsModel) {
        List titles = new ArrayList();
        List summaries = new ArrayList();
        List images = new ArrayList();
        titles.add("五大联赛唯一不败之师，&ldquo;霍村儿&rdquo;是如何做到的？");
        titles.add("拜仁复盘：中场推进不力，得胜磕磕绊绊");
        titles.add("致敬范加尔：当代足球版图的奠基者");
        titles.add("官方：杰拉德任利物浦青训教练");
        titles.add("神剧情！米兰官宣德乌洛费乌遭埃弗顿否认，随后删帖");
        summaries.add("五大联赛唯一不败之师;霍村儿;是如何做到的？");
        summaries.add("拜仁复盘：中场推进不力，得胜磕磕绊绊");
        summaries.add("致敬范加尔：当代足球版图的奠基者");
        summaries.add("官方：杰拉德任利物浦青训教练");
        summaries.add("神剧情！米兰官宣德乌洛费乌遭埃弗顿否认，随后删帖");
        images.add("http://img1.dongqiudi.com/fastdfs/M00/13/35/640x400/-/-/ooYBAFiCL5-AFub9ABVIHNcu9t0521.jpg");
        images.add("http://img1.dongqiudi.com/fastdfs/M00/13/3F/640x400/-/-/ooYBAFiC2e-AdOr2AAEShAHGnBE677.jpg");
        images.add("http://img1.dongqiudi.com/fastdfs/M00/13/29/640x400/-/-/ooYBAFiB1GOAShuLAAGvS0xKZBM940.jpg");
        images.add("http://img1.dongqiudi.com/fastdfs/M00/13/38/640x400/-/-/ooYBAFiCVVaAadIuAAEOJbac_ms166.jpg");
        images.add("http://img1.dongqiudi.com/fastdfs/M00/13/53/640x400/-/-/oYYBAFiCYKiATTy3AAE7QXvub5c122.jpg");
        for (int i = 0; i != titles.size(); i++) {
            NewsModel tmp = new NewsModel();
            tmp.setImage((String) images.get(i));
            tmp.setTitle((String) titles.get(i));
            tmp.setSummary((String) summaries.get(i));
            newsModel.add(tmp);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(thread == null){
            newsPresenter = new NewsPresenter(hotNews,news,handler);
            newsPresenter.setNewsurl(type);
            thread = new Thread(new RetrieveNews(newsPresenter,type,type == 1));
            thread.start();
        }
        if (contentView == null) {
            hsv_adapter = simpleAdapterFactory(type);
            contentView = inflater.inflate(R.layout.layout_subfragment1_1, null);
            swipeRefreshLayout = (SwipeRefreshLayout) contentView.findViewById(R.id.srl_refresh);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setColorSchemeColors(
                    Color.GREEN,
                    Color.BLUE,
                    Color.CYAN);
            recyclerView = (RecyclerView) swipeRefreshLayout.findViewById(R.id.rcv_main);
            recyclerView.setAdapter(hsv_adapter);
            recyclerView.addItemDecoration(myDecoration);
            List[] datas = new ArrayList[2];
            datas[0] = hotNews;
            datas[1] = news;
          //  recyclerView.addItemDecoration(new HoverListDecorator(getResources(),datas));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.addOnScrollListener(new NewsScrollListener(ImageLoader.getInstance(),getActivity(),handler,true));

        }
        return contentView;
    }

    @Override
    public void onRefresh() {
        synchronized (newsPresenter){
            newsPresenter.setNewsurl(1);
            thread = new Thread(new RetrieveNews(newsPresenter,1,type == 1));
            thread.start();
        }
        handler.sendEmptyMessageDelayed(REFRESH_COMPLETED, 1000);
    }
    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final int HEAD = 0x00000000;
        private final int TEXT = 0x00000001;
        private DisplayImageOptions displayImageOptions =
                new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .showImageOnFail(R.drawable.ic_launcher)
                        .showImageOnLoading(R.drawable.abc_list_pressed_holo_dark)
                        .showImageForEmptyUri(R.drawable.ic_launcher)
                        .build();

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view;
            if (getItemViewType(i) == HEAD) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_hotnews, null);
                HotNewsHolder hotNewsHolder = new HotNewsHolder(view, getActivity(),hotNews, metrics);
                hotNews_adapter = new HotNewsPageAdapter(hotNewsHolder);
                hotNewsHolder.setAdapter(hotNews_adapter);
                return hotNewsHolder;
            }
            else {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news, null);
                TextViewHolder holder = new TextViewHolder(view,metrics);

                return holder;
            }
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
           if(getItemViewType(i) == HEAD){
                HotNewsHolder hotNewsHolder = (HotNewsHolder)viewHolder;
                if(hotNews.size() != 0) {
                    hotNewsHolder.hotTextView.setText(hotNews.get(0).getTitle());
                }
            }
            else {
                if(news.size() == 0)
                    return;
                int position = i-1;
                TextViewHolder holder = (TextViewHolder) viewHolder;
                holder.setListener(news.get(position),getActivity());
                holder.title.setText(news.get(position).getTitle());
                holder.summary.setText(news.get(position).getSummary());
                holder.comment_total.setText(news.get(position).getComment_total()+commentString);

                ImageLoader.getInstance().displayImage(news.get(position).getImage(),holder.image,displayImageOptions);
            }
        }

        @Override
        public int getItemCount() {
            if (hotNews.size() != 0)
                return news.size() + 1;
            else
                return news.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0)
                return HEAD;
            else
                return TEXT;
        }


    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;
        public TextView summary;
        public TextView comment_total;
        public ImageView player;
        public RelativeLayout thumb;
        public DetailNewsListener startActivityListener;
        public TextViewHolder(View itemView, DisplayMetrics metrics) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            image = (ImageView) itemView.findViewById(R.id.iv_thumb);
            summary = (TextView) itemView.findViewById(R.id.tv_summary);
            comment_total = (TextView) itemView.findViewById(R.id.tv_comment_total);
            player = (ImageView) itemView.findViewById(R.id.iv_player);
            thumb = (RelativeLayout) itemView.findViewById(R.id.rl_thumb);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(metrics.widthPixels/4,metrics.widthPixels/4);
            thumb.setLayoutParams(params);

        }
        public void setListener(NewsModel news, Context context, DetailNewsListener listener){
            listener.setNewsModel(news);
            itemView.setOnClickListener(listener);
        }
        public void setListener(NewsModel news, Context context){
            setListener(news,context,new StartActivityListener(news, context));
        }
    }

    public static class HotNewsHolder extends RecyclerView.ViewHolder {
        public static final int ROLLHOTNEWS = 0X00001001;
        public static final int STOPROLLING = 0X00001002;
        public static final long ROLLINGSPEED = 2000;
        public ViewPager viewPager;
        public TextView hotTextView;
        private LinearLayout linearLayout;
        public List<NewsModel> newsModel;
        private View view;
        private Context context;
        private List imageViews = new ArrayList();

        public Handler imageHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ROLLHOTNEWS:
                        int item = viewPager.getCurrentItem();
                        if(newsModel.size() != 0) {
                            item = (item + 1) % newsModel.size();
                        }
                        else{
                            item = 0;
                        }
                        viewPager.setCurrentItem(item, true);
                        break;
                    case STOPROLLING:
                        if (imageHandler.hasMessages(ROLLHOTNEWS))
                            imageHandler.removeMessages(ROLLHOTNEWS);
                    default:
                        break;
                }
            }
        };

        public void setListener(NewsModel news, Context context, DetailNewsListener listener){
            listener.setNewsModel(news);
            itemView.setOnClickListener(listener);
        }
        public HotNewsHolder(final View view, Context context,final List<NewsModel> hotNews, DisplayMetrics metrics) {
            super(view);
            this.view = view;
            this.context = context;
            newsModel = hotNews;
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    metrics.widthPixels / 18 * 7);
            viewPager = (ViewPager) view.findViewById(R.id.vp_hotdisplay);
            viewPager.setLayoutParams(params);
            linearLayout = (LinearLayout) view.findViewById(R.id.ll_hotText);
            hotTextView = (TextView) linearLayout.findViewById(R.id.tv_hotText);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    hotTextView.setText(hotNews.get(position % hotNews.size()).getTitle());
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    switch (state) {
                        case ViewPager.SCROLL_STATE_IDLE:
                            imageHandler.sendEmptyMessageDelayed(ROLLHOTNEWS, ROLLINGSPEED);
                            break;
                        case ViewPager.SCROLL_STATE_DRAGGING:
                            imageHandler.sendEmptyMessage(STOPROLLING);
                        default:
                            break;
                    }
                }
            });

            setImageViews();
            imageHandler.sendEmptyMessageDelayed(ROLLHOTNEWS, ROLLINGSPEED);
        }
        public void setImageViews(){
            DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).build();
            for (NewsModel item : newsModel) {
                ImageView imageView = (ImageView) LayoutInflater.from(view.getContext()).inflate(R.layout.hot_image, null);
                ImageLoader.getInstance().displayImage(item.getImage(), imageView, displayImageOptions);
                imageView.setOnClickListener(new StartActivityListener(item,context));
                imageViews.add(imageView);
            }
        }
        public void setAdapter(HotNewsPageAdapter adapter){
            viewPager.setAdapter(adapter);
        }

        public List<ImageView> getImageViews(){
            return imageViews;
        }

    }
    public void LoadMore(int page){
        synchronized (newsPresenter) {
            newsPresenter.setNewsurl(page);
            thread = new Thread(new RetrieveNews(newsPresenter, page, type == 1));
            thread.start();
        }
        handler.sendEmptyMessageDelayed(NewsPresenter.NEWSFINSH,1000);
    }

    private class Sub_1_Handler extends Handler{
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETED:
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case NewsPresenter.HOTNEWSFINISH:
                    hsv_adapter.notifyDataSetChanged();
                    if(hotNews_adapter != null)
                        hotNews_adapter.notifyDataSetChanged();
                    break;
                case NewsPresenter.NEWSFINSH:
                    hsv_adapter.notifyDataSetChanged();
                    break;
                case REQUIRE_APPEND:
                    ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.show();
                    LoadMore(msg.arg1);
                    hsv_adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                    break;
                default:
                    ;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }
}
