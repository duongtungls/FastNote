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

package com.baiyang.fastnote.widget;

import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import com.baiyang.fastnote.R;
import com.baiyang.fastnote.inner.Formatter;
import com.baiyang.fastnote.model.Category;
import com.baiyang.fastnote.widget.template.ModelViewHolder;

public class CategoryViewHolder extends ModelViewHolder<Category> {
	public TextView badge;
	public TextView title;
	public TextView date;
	public TextView counter;

	public CategoryViewHolder(View itemView) {
		super(itemView);
		badge = (TextView) itemView.findViewById(R.id.badge_txt);
		title = (TextView) itemView.findViewById(R.id.title_txt);
		counter = (TextView) itemView.findViewById(R.id.counter_txt);
		date = (TextView) itemView.findViewById(R.id.date_txt);
	}

	@Override
	public void populate(Category item) {
		badge.setText(item.title.substring(0, 1).toUpperCase(Locale.US));
		badge.setBackgroundResource(item.getThemeBackground());
		title.setText(item.title);
		if (item.counter == 0) counter.setText("");
		else if (item.counter == 1) counter.setText(R.string.one_note);
		else counter.setText(String.format(Locale.US, "%d notes", item.counter));
		date.setText(Formatter.formatShortDate(item.createdAt));
	}
}
