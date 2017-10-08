/*
 * FastNote
 * <p/>
 * Created by Tung Duong <duongtungls@gmail.com> on 08/01/2017.
 * Copyright (c) 2017  Bach Duong Solution
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.baiyang.fastnote.fragment.template;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.baiyang.fastnote.R;
import com.baiyang.fastnote.activity.NoteActivity;
import com.baiyang.fastnote.db.OpenHelper;
import com.baiyang.fastnote.model.DatabaseModel;
import com.baiyang.fastnote.model.Note;

abstract public class NoteFragment extends Fragment {
	public Note note = null;
	public Callbacks activity;
	private View deleteBtn;
	public EditText title;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(getLayout(), container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		deleteBtn = getActivity().findViewById(R.id.delete_btn);
		title = (EditText) view.findViewById(R.id.title_txt);

		Intent data = getActivity().getIntent();
		long noteId = data.getLongExtra(OpenHelper.COLUMN_ID, DatabaseModel.NEW_MODEL_ID);
		final long categoryId = data.getLongExtra(OpenHelper.COLUMN_PARENT_ID, DatabaseModel.NEW_MODEL_ID);

		if (noteId != DatabaseModel.NEW_MODEL_ID) {
			note = Note.find(noteId);
		}

		if (note == null) {
			note = new Note();
			activity.setNoteResult(NoteActivity.RESULT_NEW, false);
			deleteBtn.setVisibility(View.GONE);
			note.categoryId = categoryId;
			note.title = "";
			note.body = "";
			note.isArchived = false;
			note.type = data.getIntExtra(OpenHelper.COLUMN_TYPE, DatabaseModel.TYPE_NOTE_SIMPLE);
		} else {
			activity.setNoteResult(NoteActivity.RESULT_EDIT, false);
			deleteBtn.setVisibility(View.VISIBLE);
			deleteBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					activity.setNoteResult(NoteActivity.RESULT_DELETE, true);
				}
			});
		}

		title.setText(note.title);

		init(view);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		activity = (Callbacks) context;
	}

	public void saveNote(SaveListener listener) {
		String inputTitle = title.getText().toString();
		if (inputTitle.isEmpty()) inputTitle = "Untitled";
		note.title = inputTitle;
		if (note.id == DatabaseModel.NEW_MODEL_ID) {
			note.createdAt = System.currentTimeMillis();
		}
	}

	abstract public int getLayout();
	abstract public void init(View view);

	public interface SaveListener {
		void onSave();
	}

	public interface Callbacks {
		void setNoteResult(int result, boolean closeActivity);
	}
}
