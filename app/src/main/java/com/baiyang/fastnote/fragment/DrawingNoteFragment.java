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

package com.baiyang.fastnote.fragment;

import android.util.Base64;
import android.view.View;

import com.baiyang.fastnote.CanvasView;

import com.baiyang.fastnote.R;
import com.baiyang.fastnote.fragment.template.NoteFragment;
import com.baiyang.fastnote.model.DatabaseModel;

public class DrawingNoteFragment extends NoteFragment {
	private CanvasView canvas;

	public DrawingNoteFragment() {}

	@Override
	public int getLayout() {
		return R.layout.fragment_drawing_note;
	}

	@Override
	public void saveNote(final SaveListener listener) {
		super.saveNote(listener);

		new Thread() {
			@Override
			public void run() {
				note.body = Base64.encodeToString(canvas.getBitmapAsByteArray(), Base64.NO_WRAP);

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
		canvas = (CanvasView) view.findViewById(R.id.canvas);

		view.findViewById(R.id.pen_tool).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				canvas.setMode(CanvasView.Mode.DRAW);
				canvas.setPaintStrokeWidth(3F);
			}
		});

		view.findViewById(R.id.eraser_tool).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				canvas.setMode(CanvasView.Mode.ERASER);
				canvas.setPaintStrokeWidth(40F);
			}
		});

		if (!note.body.isEmpty()) {
			canvas.drawBitmap(Base64.decode(note.body, Base64.NO_WRAP));
		}
	}
}
