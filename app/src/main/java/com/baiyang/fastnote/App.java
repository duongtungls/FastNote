/*
 * MIT License
 *
 * Copyright (c) 2017 Duong Tung <duongtungls@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.baiyang.fastnote;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.view.WindowManager;

import com.baiyang.fastnote.db.Controller;

public class App extends Application {
	public static App instance;
	public static int DEVICE_HEIGHT;

	/* Preferences */
	public static boolean smartFab;
	public static int sortCategoriesBy;
	public static int sortNotesBy;
	public static String last_path;

	public static final String BACKUP_EXTENSION = "mem";

	/* Preferences' Keys */
	public static final String SMART_FAB_KEY = "a1";
	public static final String SORT_CATEGORIES_KEY = "a2";
	public static final String SORT_NOTES_KEY = "a3";
	public static final String LAST_PATH_KEY = "a4";

	private SharedPreferences prefs;

	@Override
	public void onCreate() {
		super.onCreate();

		// Get preferences
		prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
		smartFab = prefs.getBoolean(SMART_FAB_KEY, true);
		sortCategoriesBy = sanitizeSort(prefs.getInt(SORT_CATEGORIES_KEY, Controller.SORT_DATE_DESC));
		sortNotesBy = sanitizeSort(prefs.getInt(SORT_NOTES_KEY, Controller.SORT_DATE_DESC));
		last_path = prefs.getString(LAST_PATH_KEY, null);

		// Setup database controller
		Controller.create(getApplicationContext());

		Point size = new Point();
		((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
		DEVICE_HEIGHT = size.y;

		instance = this;
	}

	private int sanitizeSort(int sortId) {
		if (sortId < 0 || sortId > 3) return Controller.SORT_DATE_DESC;
		return sortId;
	}

	public void putPrefs(String key, boolean value) {
		prefs.edit().putBoolean(key, value).apply();
	}

	public void putPrefs(String key, String value) {
		prefs.edit().putString(key, value).apply();
	}
}
