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
import com.baiyang.fastnote.db.Controller;
import com.baiyang.fastnote.db.OpenHelper;

public class Note extends DatabaseModel {
	public long categoryId;
	public String body;

	public Note() {}

	/**
	 * Instantiates a new object of Note class using the data retrieved from database.
	 * @param c cursor object returned from a database query
	 */
	public Note(Cursor c) {
		super(c);
		this.categoryId = c.getLong(c.getColumnIndex(OpenHelper.COLUMN_PARENT_ID));
		try {
			this.body = c.getString(c.getColumnIndex(OpenHelper.COLUMN_BODY));
		} catch (Exception ignored) {}
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
			values.put(OpenHelper.COLUMN_ARCHIVED, isArchived);
			values.put(OpenHelper.COLUMN_PARENT_ID, categoryId);
		}

		values.put(OpenHelper.COLUMN_TITLE, title);
		values.put(OpenHelper.COLUMN_BODY, body);

		return values;
	}

	/**
	 * Reads a note by its id
	 * @param id primary key of note
	 * @return the note object or null if it was not found
	 */
	public static Note find(long id) {
		return Controller.instance.findNote(Note.class, id);
	}

	/**
	 * Reads all notes
	 * @param categoryId the id of parent category
	 * @return a list of notes which is populated by database
	 */
	public static ArrayList<Note> all(long categoryId) {
		return Controller.instance.findNotes(
			Note.class,
			new String[] {
				OpenHelper.COLUMN_ID,
				OpenHelper.COLUMN_TITLE,
				OpenHelper.COLUMN_DATE,
				OpenHelper.COLUMN_TYPE,
				OpenHelper.COLUMN_ARCHIVED,
				OpenHelper.COLUMN_PARENT_ID,
			},
			OpenHelper.COLUMN_TYPE + " != ? AND " + OpenHelper.COLUMN_PARENT_ID + " = ? AND " + OpenHelper.COLUMN_ARCHIVED + " = ?",
			new String[]{
				String.format(Locale.US, "%d", DatabaseModel.TYPE_CATEGORY),
				String.format(Locale.US, "%d", categoryId),
				"0"
			},
			App.sortNotesBy
		);
	}

	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Note && id == (((Note) o).id);
	}
}
