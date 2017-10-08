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

package com.baiyang.fastnote.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baiyang.fastnote.R;
import com.baiyang.fastnote.model.Drawer;

public class DrawerAdapter extends BaseAdapter {
	private static final int LENGTH = 4;

	private Context context;
	private ClickListener listener;
	private Drawer[] drawers;

	public DrawerAdapter(Context context, ClickListener listener) {
		this.context = context;
		this.listener = listener;
		populate();
	}

	private void populate() {
		drawers = new Drawer[LENGTH];

		int counter = -1;

		drawers[++counter] = new Drawer(
			Drawer.TYPE_BACKUP,
			R.drawable.drawer_backup,
			R.string.backup
		);

		drawers[++counter] = new Drawer(
			Drawer.TYPE_RESTORE,
			R.drawable.drawer_restore,
			R.string.restore
		);

		drawers[++counter] = Drawer.divider();

		drawers[++counter] = new Drawer(
			Drawer.TYPE_ABOUT,
			R.drawable.drawer_about,
			R.string.about
		);
	}

	@Override
	public int getCount() {
		return LENGTH;
	}

	@Override
	public Drawer getItem(int i) {
		return drawers[i];
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		return drawers[position].type > 0 ? 1 : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Drawer drawer = getItem(position);
		ViewHolder holder = null;

		if (convertView == null) {
			LayoutInflater inflator = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

			switch (drawer.type) {
				case Drawer.TYPE_SPLITTER:
					convertView = inflator.inflate(R.layout.drawer_separator, parent, false);
					break;
				default:
					convertView = inflator.inflate(R.layout.drawer_item, parent, false);
					holder = new ViewHolder();
					holder.icon = (ImageView) convertView.findViewById(R.id.icon);
					holder.title = (TextView) convertView.findViewById(R.id.title);
					holder.item = convertView.findViewById(R.id.item);
					convertView.setTag(holder);
					break;
			}
		}

		if (drawer.type > 0) {
			if (holder == null) {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.title.setText(drawer.title);
			holder.item.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					listener.onClick(drawer.type);
				}
			});
			holder.icon.setImageResource(drawer.resId);
		}

		return convertView;
	}

	@Override
	public boolean isEnabled(int position) {
		return drawers[position].type > 1;
	}

	private class ViewHolder {
		public View item;
		public TextView title;
		public ImageView icon;
	}

	public interface ClickListener {
		void onClick(int type);
	}
}
