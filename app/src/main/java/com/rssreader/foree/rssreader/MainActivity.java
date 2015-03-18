package com.rssreader.foree.rssreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baseapplication.foree.rssreader.BaseActivity;
import com.baseapplication.foree.rssreader.MyApplication;
import com.db.foree.rssreader.RssDao;
import com.xmlparse.foree.rssreader.ParseTask;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private static final String TAG = "MainActivity";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     * fragment管理navigation drawer的行为，交互和描述
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     * 通常用来存储最后一个屏幕的标题
     */
    private CharSequence mTitle;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DayTheme);
        setContentView(R.layout.main);

        //应用启动初始化环境变量
        myApplication = new MyApplication(this);
        myApplication.initEnv();
        //myApplication.cleanListCache();
        //myApplication.initSettings(this);

        //drawerlayout
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        // 启动drawer，将drawer的布局加载到位于navigationDrawer上的fragment中
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
        Log.v(TAG, "onCreate");

    }

    //在navigation被选中的时候
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        // 同过替换fragment来更新主界面
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
        Log.v(TAG, "onNavigationDrawerItemSelected");
    }

    //在section被选中的时候设置标题
    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }


    //重新设置actionBar的相关信息
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            restoreActionBar();
            return true;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                MainActivity.this.startActivity(intent);
                break;
            case R.id.action_about:
                String message = "增加以下特性：" + "\n"
                        + "1.可以浏览新闻";
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("v" + MyApplication.myVersionName);
                builder.setMessage(message);
                builder.setNeutralButton("返回", null);
                builder.show();
                break;
            //点击new,对侧边的数据进行增加,并调用adapter提示变化
            case R.id.action_new:
                //创建Dialog
                NavigationDrawerFragment.FeedInfos.add("直呼");
                NavigationDrawerFragment.adapter.setNotifyOnChange(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     * 一个位置存储fragment包含一个简单的视图
     */
    public static class PlaceholderFragment extends Fragment {
        private static final String TAG = "PlaceholderFragment";
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private View rootView;
        private ListView articel_listview;
        private MainActivity mainActivity;
        ProgressBar mProgressBar;
        TextView mTextView;
        public final String RSS_URL = "http://blog.sina.com.cn/rss/1267454277.xml";
        public final String RSS_URL2 = "http://www.dogear.cn/feed/9768.xml";
        public final String RSS_URL3 = "http://www.dogear.cn/feed/10244.xml";
        private String URLString = null;

        public final String urlName1 = "http://blog.sina.com.cn/phoenixweekly";
        public final String urlName2 = "http://weixin.sogou.com/gzh?openid=oIWsFt916x87mkcHhJp_lgt1-Q7c";
        public final String urlName3 = "http://weixin.sogou.com/gzh?openid=oIWsFt5vznVWdVAk9hw4dsXVj5y8";
        private String urlName = null;

        public ListView getItemlist() {
            return articel_listview;
        }

        public String getpostionString() {
            return URLString;
        }

        public String getpostionUrlName() {
            return urlName;
        }

        public ProgressBar getmProgressBar() {
            return mProgressBar;
        }

        public TextView getmTextView() {
            return mTextView;
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         * 根据给定的section number返回一个新的例子,并带有相应的参数
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        //在创建fragment之后，将要填充的布局文件转换为view控件
        //在此生命周期后，布局中的控件才可以被找到
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //初始化fragment中的控件
            articel_listview = (ListView) rootView.findViewById(R.id.articel_listview);
            mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
            mTextView = (TextView) rootView.findViewById(R.id.nonetwork);
            Log.v(TAG, "onCreateView");
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
            //初始化mainactivity实例，用于与主界面通信
            mainActivity = (MainActivity) activity;
            Log.v(TAG, "OnAttach");

        }

        //在onStart方法中对listview进行动态的数据添加
        @Override
        public void onStart() {
            super.onStart();
            //获取当前所选中的fragment,并解析对应的url
            int id = getArguments().getInt(ARG_SECTION_NUMBER);
            //获取左侧Drawer相对应位置的FeedName,来查找数据库中对应的url
            String FeedName = NavigationDrawerFragment.FeedInfos.get(id);
            RssDao rssDao = new RssDao(mainActivity, "rss.db", null, 1);
            //取得对应名称的url链接
            urlName = FeedName;
            URLString = rssDao.findUrl(FeedName);
            //解析对应url获取数据
            ParseTask parseTask = new ParseTask(mainActivity);
            parseTask.execute(this);
            /*Log.v(TAG,"Fragment的id = " + id);
            switch (id) {
                case 1:
                    URLString = url;
                    urlName = urlName1;
                    ParseTask parseTask = new ParseTask(mainActivity);
                    parseTask.execute(this);
                    break;
                case 2:
                    URLString = RSS_URL2;
                    urlName = urlName2;
                    ParseTask parseTask1 = new ParseTask(mainActivity);
                    parseTask1.execute(this);
                    break;
                case 3:
                    URLString = RSS_URL3;
                    urlName = urlName3;
                    ParseTask parseTask2 = new ParseTask(mainActivity);
                    parseTask2.execute(this);
            }*/
            Log.v(TAG, "onStart");
        }
    }
}
