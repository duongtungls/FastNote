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

package com.baiyang.fastnote.adapter.template;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import com.baiyang.fastnote.model.DatabaseModel;
import com.baiyang.fastnote.widget.template.ModelViewHolder;

abstract public class ModelAdapter<T extends DatabaseModel, VH extends ModelViewHolder<T>> extends RecyclerView.Adapter<VH> {
	private ArrayList<T> items;
	private ArrayList<T> selected;
	private ClickListener<T> listener;

	public ModelAdapter(ArrayList<T> items, ArrayList<T> selected, ClickListener<T> listener) {
		this.items = items;
		this.selected = selected;
		this.listener = listener;
	}

	@Override
	public void onBindViewHolder(final VH holder, int position) {
		final T item = items.get(position);

		// Populate view
		holder.populate(item);

		// Check if item is selected
		if (selected.contains(item)) holder.setSelected(true);
		else holder.setSelected(false);

		holder.holder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (selected.isEmpty()) listener.onClick(item, items.indexOf(item));
				else toggleSelection(holder, item);
			}
		});

		holder.holder.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View view) {
				toggleSelection(holder, item);
				return true;
			}
		});
	}

	@Override
	public int getItemCount() {
		return items.size();
	}

	private void toggleSelection(VH holder, T item) {
		if (selected.contains(item)) {
			selected.remove(item);
			holder.setSelected(false);
			if (selected.isEmpty()) listener.onChangeSelection(false);
		} else {
			if (selected.isEmpty()) listener.onChangeSelection(true);
			selected.add(item);
			holder.setSelected(true);
		}
		listener.onCountSelection(selected.size());
	}

	public interface ClickListener<M extends DatabaseModel> {
		void onClick(M item, int position);
		void onChangeSelection(boolean haveSelected);
		void onCountSelection(int count);
	}
}
