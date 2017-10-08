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

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

public class Drawer {
	public static final int TYPE_SPLITTER = 0;
	public static final int TYPE_ABOUT = 1;
	public static final int TYPE_BACKUP = 2;
	public static final int TYPE_RESTORE = 3;
	public static final int TYPE_SETTINGS = 4;

	public int type;
	@DrawableRes
	public int resId;
	@StringRes
	public int title;

	public Drawer() {}

	public Drawer(int type, @DrawableRes int resId, @StringRes int title) {
		this.type = type;
		this.resId = resId;
		this.title = title;
	}

	public static Drawer divider() {
		Drawer splitter = new Drawer();
		splitter.type = TYPE_SPLITTER;
		return splitter;
	}
}
