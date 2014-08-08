/*
 * Copyright (C) 2013 Koushik Dutta (@koush)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.koushikdutta.widgets;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;


public class BetterListActivity extends FragmentActivity implements BetterListFragment.ActivityBaseFragmentListener {
    Class<? extends BetterListFragment> clazz;
    public BetterListActivity(Class<? extends BetterListFragment> clazz) {
        super();
        this.clazz = clazz;
    }
    
    public BetterListActivity() {
        super();
        this.clazz = BetterListFragment.class;
    }

    protected int getListContainerId() {
        return R.id.activity_content;
    }
    
    protected int getContentView() {
        return R.layout.container_activity;
    }

    public BetterListFragment getFragment() {
        return (BetterListFragment)getSupportFragmentManager().findFragmentByTag("content");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        int cv = getContentView();
        if (0 != cv)
            setContentView(cv);

        if (savedInstanceState == null) {
            try {
                BetterListFragment fragment = (BetterListFragment)clazz.getConstructors()[0].newInstance();
                fragment.setListener(this);
                fragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().replace(getListContainerId(), fragment, "content").commit();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            BetterListFragment fragment = getFragment();
            if (fragment != null)
                fragment.setListener(this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState, View view) {
    }

    protected ListItem addItem(int sectionName, ListItem item) {
        return getFragment().addItem(getString(sectionName), item);
    }

    protected ListItem addItem(int sectionName, ListItem item, int index) {
        return getFragment().addItem(getString(sectionName), item, index);
    }
    
    protected ListItem addItem(String sectionName, ListItem item) {
        return getFragment().addItem(sectionName, item, -1);
    }

    public boolean isDestroyedLegacy() {
        return mDestroyed;
    }
    
    private boolean mDestroyed = false;
    @Override
    protected void onDestroy() {
        mDestroyed = true;
        super.onDestroy();
    }
}
