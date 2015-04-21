/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.wjc.nnairport;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import org.apache.cordova.*;

public class CordovaApp extends CordovaActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        super.init();
        // Set by <content src="index.html" /> in config.xml
        loadUrl(launchUrl);
//        loadUrl("http://metaui.duapp.com/tpl/jqmobi/nanning.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Menu.FIRST + 1, 1, "南宁机场");
        menu.add(Menu.NONE, Menu.FIRST + 2, 1, "桂林机场");
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case Menu.FIRST + 1 : {
                loadUrl("http://metaui.duapp.com/tpl/jqmobi/nanning.html");
                break;
            }
            case Menu.FIRST + 2 : {
                loadUrl("http://metaui.duapp.com/tpl/jqmobi/guilin.html");
                break;
            }
        }
        return super.onMenuItemSelected(featureId, item);
    }

}
