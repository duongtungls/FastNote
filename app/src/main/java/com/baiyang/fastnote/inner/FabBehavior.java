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

package com.baiyang.fastnote.inner;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.baiyang.fastnote.widget.Fab;

@SuppressWarnings("unused")
public class FabBehavior extends CoordinatorLayout.Behavior<Fab> {
	public FabBehavior(Context context, AttributeSet attrs) {}

	@Override
	public boolean layoutDependsOn(CoordinatorLayout parent, Fab child, View dependency) {
		return dependency instanceof Snackbar.SnackbarLayout;
	}

	@Override
	public boolean onDependentViewChanged(CoordinatorLayout parent, Fab child, View dependency) {
		if (dependency instanceof Snackbar.SnackbarLayout) {
			float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
			child.setTranslationY(translationY);
			return true;
		}
		return false;
	}

	@Override
	public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Fab child, View directTargetChild, View target, int nestedScrollAxes) {
		return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
			super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
				nestedScrollAxes);
	}

	@Override
	public void onNestedScroll(CoordinatorLayout coordinatorLayout, Fab child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
		super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);

		if (dyConsumed > 0) {
			child.hide();
		} else if (dyConsumed < 0) {
			child.show();
		}
	}
}
