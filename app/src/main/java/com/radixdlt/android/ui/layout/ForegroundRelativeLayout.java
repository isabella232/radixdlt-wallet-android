package com.radixdlt.android.ui.layout;

/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import com.radixdlt.android.R;

public class ForegroundRelativeLayout extends RelativeLayout {

  private Drawable mForegroundSelector;
  private Rect mRectPadding;
  private boolean mUseBackgroundPadding = false;

  public ForegroundRelativeLayout(Context context) {
    super(context);
  }

  public ForegroundRelativeLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ForegroundRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    TypedArray a =
        context.obtainStyledAttributes(attrs, R.styleable.ForegroundRelativeLayout, defStyle, 0);

    final Drawable d = a.getDrawable(R.styleable.ForegroundRelativeLayout_android_foreground);
    if (d != null) {
      setForeground(d);
    }

    a.recycle();

    if (this.getBackground() instanceof NinePatchDrawable) {
      final NinePatchDrawable npd = (NinePatchDrawable) this.getBackground();
      mRectPadding = new Rect();
      if (npd.getPadding(mRectPadding)) {
        mUseBackgroundPadding = true;
      }
    }
  }

  @Override protected void drawableStateChanged() {
    super.drawableStateChanged();

    if (mForegroundSelector != null && mForegroundSelector.isStateful()) {
      mForegroundSelector.setState(getDrawableState());
    }
  }

  @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);

    if (mForegroundSelector != null) {
      if (mUseBackgroundPadding) {
        mForegroundSelector.setBounds(mRectPadding.left, mRectPadding.top, w - mRectPadding.right,
            h - mRectPadding.bottom);
      } else {
        mForegroundSelector.setBounds(0, 0, w, h);
      }
    }
  }

  @Override protected void dispatchDraw(@NonNull Canvas canvas) {
    super.dispatchDraw(canvas);

    if (mForegroundSelector != null) {
      mForegroundSelector.draw(canvas);
    }
  }

  @Override protected boolean verifyDrawable(@NonNull Drawable who) {
    return super.verifyDrawable(who) || (who == mForegroundSelector);
  }

  @Override public void jumpDrawablesToCurrentState() {
    super.jumpDrawablesToCurrentState();
    if (mForegroundSelector != null) mForegroundSelector.jumpToCurrentState();
  }

  public void setForeground(Drawable drawable) {
    if (mForegroundSelector != drawable) {
      if (mForegroundSelector != null) {
        mForegroundSelector.setCallback(null);
        unscheduleDrawable(mForegroundSelector);
      }

      mForegroundSelector = drawable;

      if (drawable != null) {
        setWillNotDraw(false);
        drawable.setCallback(this);
        if (drawable.isStateful()) {
          drawable.setState(getDrawableState());
        }
      } else {
        setWillNotDraw(true);
      }
      requestLayout();
      invalidate();
    }
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) @Override
  public void drawableHotspotChanged(float x, float y) {
    super.drawableHotspotChanged(x, y);
    if (mForegroundSelector != null) {
      mForegroundSelector.setHotspot(x, y);
    }
  }
}
