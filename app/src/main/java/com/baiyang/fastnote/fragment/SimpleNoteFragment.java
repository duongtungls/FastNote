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

package com.baiyang.fastnote.fragment;

import android.support.v4.content.ContextCompat;
import android.view.View;

import com.baiyang.fastnote.R;
import com.baiyang.fastnote.fragment.template.NoteFragment;
import com.baiyang.fastnote.model.DatabaseModel;
import jp.wasabeef.richeditor.RichEditor;

public class SimpleNoteFragment extends NoteFragment {
	private RichEditor body;

	public SimpleNoteFragment() {}

	@Override
	public int getLayout() {
		return R.layout.fragment_simple_note;
	}

	@Override
	public void saveNote(final SaveListener listener) {
		super.saveNote(listener);
		note.body = body.getHtml();

		new Thread() {
			@Override
			public void run() {
				long id = note.save();
				if (note.id == DatabaseModel.NEW_MODEL_ID) {
					note.id = id;
				}
				listener.onSave();
				interrupt();
			}
		}.start();
	}

	@Override
	public void init(View view) {
		body = (RichEditor) view.findViewById(R.id.editor);
		body.setPlaceholder("Note");
		body.setEditorBackgroundColor(ContextCompat.getColor(getContext(), R.color.bg));

		view.findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				body.setBold();
			}
		});

		view.findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				body.setItalic();
			}
		});

		view.findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				body.setUnderline();
			}
		});

		body.setHtml(note.body);
	}
}
