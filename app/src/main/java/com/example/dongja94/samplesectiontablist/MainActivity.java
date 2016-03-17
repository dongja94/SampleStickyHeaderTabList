package com.example.dongja94.samplesectiontablist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> mAdapter;
    TabHost tabHost;
    TabHost headerHost;

    static class DummyContentFactory implements TabHost.TabContentFactory {
        Context mContext;
        public DummyContentFactory(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            TextView tv = new TextView(mContext);
            return tv;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        View headerView = getLayoutInflater().inflate(R.layout.view_header, null);
        listView.addHeaderView(headerView, null, false);
        TabHost.TabContentFactory dummyFactory = new DummyContentFactory(this);
        headerHost = (TabHost)getLayoutInflater().inflate(R.layout.view_tab_header, null);
        headerHost.setup();
        headerHost.addTab(headerHost.newTabSpec("tab1").setIndicator("TAB1").setContent(dummyFactory));
        headerHost.addTab(headerHost.newTabSpec("tab2").setIndicator("TAB2").setContent(dummyFactory));
        headerHost.addTab(headerHost.newTabSpec("tab3").setIndicator("TAB3").setContent(dummyFactory));
        headerHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (!tabHost.getCurrentTabTag().equals(tabId)) {
                    tabHost.setCurrentTabByTag(tabId);
                }
            }
        });
        listView.addHeaderView(headerHost, null, false);
        tabHost = (TabHost)findViewById(R.id.fixed_tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("TAB1").setContent(dummyFactory));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("TAB2").setContent(dummyFactory));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("TAB3").setContent(dummyFactory));
        tabHost.setVisibility(View.GONE);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (!headerHost.getCurrentTabTag().equals(tabId)) {
                    headerHost.setCurrentTabByTag(tabId);
                }
                setItem(tabId);
            }
        });

        listView.setAdapter(mAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    tabHost.setVisibility(View.GONE);
                } else {
                    tabHost.setVisibility(View.VISIBLE);
                }
            }
        });

        setItem("tab1");
    }

    public void setItem(String tabId) {
        mAdapter.clear();

        for (int i = 0; i < 20; i++) {
            mAdapter.add(tabId + ":" + i);
        }
    }
}
