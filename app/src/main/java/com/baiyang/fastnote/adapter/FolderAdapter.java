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

package com.baiyang.fastnote.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.baiyang.fastnote.R;
import com.baiyang.fastnote.model.Folder;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder> {
	private ArrayList<Folder> items;
	private ClickListener listener;

	public FolderAdapter(ArrayList<Folder> items, ClickListener listener) {
		this.items = items;
		this.listener = listener;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder, parent, false));
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final Folder item = items.get(position);

		holder.title.setText(item.name);
		if (item.isDirectory) {
			holder.icon.setImageResource(item.isBack ? R.drawable.back_folder : R.drawable.ic_folder);
		} else {
			holder.icon.setImageResource(R.drawable.ic_file);
		}

		holder.holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.onClick(item);
			}
		});
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		public View holder;
		public TextView title;
		public ImageView icon;

		public ViewHolder(View itemView) {
			super(itemView);
			holder = itemView.findViewById(R.id.holder);
			title = (TextView) itemView.findViewById(R.id.title);
			icon = (ImageView) itemView.findViewById(R.id.icon);
		}
	}

	public interface ClickListener {
		void onClick(Folder item);
	}
}
