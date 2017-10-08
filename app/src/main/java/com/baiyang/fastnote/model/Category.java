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

package com.baiyang.fastnote.model;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Locale;

import com.baiyang.fastnote.App;
import com.baiyang.fastnote.R;
import com.baiyang.fastnote.db.Controller;
import com.baiyang.fastnote.db.OpenHelper;

public class Category extends DatabaseModel {
	public static final int THEME_RED       = 0;
	public static final int THEME_PINK      = 1;
	public static final int THEME_PURPLE    = 2;
	public static final int THEME_BLUE      = 3;
	public static final int THEME_CYAN      = 4;
	public static final int THEME_TEAL      = 5;
	public static final int THEME_GREEN     = 6;
	public static final int THEME_AMBER     = 7;
	public static final int THEME_ORANGE    = 8;

	public int counter;

	public Category() {}

	/**
	 * Instantiates a new object of Category class using the data retrieved from database.
	 * @param c cursor object returned from a database query
	 */
	public Category(Cursor c) {
		super(c);
		this.theme = c.getInt(c.getColumnIndex(OpenHelper.COLUMN_THEME));
		this.counter = c.getInt(c.getColumnIndex(OpenHelper.COLUMN_COUNTER));
	}

	/**
	 * @return ContentValue object to be saved or updated
	 */
	@Override
	public ContentValues getContentValues() {
		ContentValues values = new ContentValues();

		if (id == DatabaseModel.NEW_MODEL_ID) {
			values.put(OpenHelper.COLUMN_TYPE, type);
			values.put(OpenHelper.COLUMN_DATE, createdAt);
			values.put(OpenHelper.COLUMN_COUNTER, counter);
			values.put(OpenHelper.COLUMN_ARCHIVED, isArchived);
		}

		values.put(OpenHelper.COLUMN_TITLE, title);
		values.put(OpenHelper.COLUMN_THEME, theme);

		return values;
	}


	/**
	 * @param theme the color id of category
	 * @return the style of theme
	 */
	public static int getStyle(int theme) {
		switch (theme) {
			case THEME_RED:
				return R.style.AppThemeRed;
			case THEME_PINK:
				return R.style.AppThemePink;
			case THEME_AMBER:
				return R.style.AppThemeAmber;
			case THEME_BLUE:
				return R.style.AppThemeBlue;
			case THEME_CYAN:
				return R.style.AppThemeCyan;
			case THEME_GREEN:
				return R.style.AppThemeGreen;
			case THEME_ORANGE:
				return R.style.AppThemeOrange;
			case THEME_PURPLE:
				return R.style.AppThemePurple;
			case THEME_TEAL:
				return R.style.AppThemeTeal;
		}

		return R.style.AppTheme;
	}

	/**
	 * Reads a category by its id
	 * @param id primary key of category
	 * @return the category object or null if it was not found
	 */
	public static Category find(long id) {
		return Controller.instance.findNote(Category.class, id);
	}

	/**
	 * Reads all categories
	 * @return a list of categories which is populated by database
	 */
	public static ArrayList<Category> all() {
		return Controller.instance.findNotes(
			Category.class,
			new String[] {
				OpenHelper.COLUMN_ID,
				OpenHelper.COLUMN_TITLE,
				OpenHelper.COLUMN_DATE,
				OpenHelper.COLUMN_TYPE,
				OpenHelper.COLUMN_ARCHIVED,
				OpenHelper.COLUMN_THEME,
				OpenHelper.COLUMN_COUNTER
			},
			OpenHelper.COLUMN_TYPE + " = ? AND " + OpenHelper.COLUMN_ARCHIVED + " = ?",
			new String[]{
				String.format(Locale.US, "%d", DatabaseModel.TYPE_CATEGORY),
				"0"
			},
			App.sortCategoriesBy
		);
	}

	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Category && id == (((Category) o).id);
	}
}
