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

package com.baiyang.fastnote.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.baiyang.fastnote.App;
import com.baiyang.fastnote.R;
import com.baiyang.fastnote.inner.Animator;

public class Fab extends AppCompatImageView {
	private boolean isHidden = false;

	public Fab(Context context) {
		super(context);
	}

	public Fab(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public Fab(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * Makes the fab visible if it is hidden
	 */
	public void show() {
		if (isHidden) {
			isHidden = false;
			Animator.create(getContext().getApplicationContext())
				.on(this)
				.setStartVisibility(View.VISIBLE)
				.animate(R.anim.fab_scroll_in);
		}
	}

	/**
	 * Makes the fab gone if it is visible and smart fab preference is enabled
	 */
	public void hide() {
		if (App.smartFab && !isHidden) {
			isHidden = true;
			Animator.create(getContext().getApplicationContext())
				.on(this)
				.setEndVisibility(View.GONE)
				.animate(R.anim.fab_scroll_out);
		}
	}
}
