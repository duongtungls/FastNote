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

package com.baiyang.fastnote.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.baiyang.fastnote.R;
import com.baiyang.fastnote.db.OpenHelper;
import com.baiyang.fastnote.fragment.DrawingNoteFragment;
import com.baiyang.fastnote.fragment.SimpleNoteFragment;
import com.baiyang.fastnote.fragment.template.NoteFragment;
import com.baiyang.fastnote.model.Category;
import com.baiyang.fastnote.model.DatabaseModel;

public class NoteActivity extends AppCompatActivity implements NoteFragment.Callbacks {
	public static final int REQUEST_CODE = 2;
	public static final int RESULT_NEW = 101;
	public static final int RESULT_EDIT = 102;
	public static final int RESULT_DELETE = 103;

	private int noteResult = 0;
	private int position;

	private NoteFragment fragment;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		Intent data = getIntent();
		setTheme(Category.getStyle(data.getIntExtra(OpenHelper.COLUMN_THEME, Category.THEME_GREEN)));
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);

		AdView adView = (AdView)findViewById(R.id.adView);

		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.build();

		adView.loadAd(adRequest);

		position = data.getIntExtra("position", 0);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		try {
			//noinspection ConstantConditions
			getSupportActionBar().setDisplayShowTitleEnabled(false);
		} catch (Exception ignored) {
		}

		toolbar.findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		if (savedInstanceState == null) {
			if (data.getIntExtra(OpenHelper.COLUMN_TYPE, DatabaseModel.TYPE_NOTE_SIMPLE) == DatabaseModel.TYPE_NOTE_SIMPLE) {
				fragment = new SimpleNoteFragment();
			} else {
				fragment = new DrawingNoteFragment();
			}

			getSupportFragmentManager().beginTransaction()
				.add(R.id.container, fragment)
				.commit();
		}
	}

	@Override
	public void onBackPressed() {
		fragment.saveNote(new NoteFragment.SaveListener() {
			@Override
			public void onSave() {
				final Intent data = new Intent();
				data.putExtra("position", position);
				data.putExtra(OpenHelper.COLUMN_ID, fragment.note.id);

				switch (noteResult) {
					case RESULT_NEW:
						data.putExtra(OpenHelper.COLUMN_TYPE, fragment.note.type);
						data.putExtra(OpenHelper.COLUMN_DATE, fragment.note.createdAt);
					case RESULT_EDIT:
						data.putExtra(OpenHelper.COLUMN_TITLE, fragment.note.title);
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						setResult(noteResult, data);
						finish();
					}
				});
			}
		});
	}

	@Override
	public void setNoteResult(int result, boolean closeActivity) {
		noteResult = result;
		if (closeActivity) {
			Intent data = new Intent();
			data.putExtra("position", position);
			data.putExtra(OpenHelper.COLUMN_ID, fragment.note.id);
			setResult(result, data);
			finish();
		}
	}
}
