package dev.vortexnode.sbrowserx.extension;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Persistent circular Restart button for both Samsung Internet's native
 * SettingsActivity (including all sub-screens like Dark Mode, Layout, Privacy) and
 * all SBrowserX settings screens.
 *
 * - Automatically detects overlapping top-right toolbar controls (e.g. Done / Reset / Search)
 *   using live view bounds: shifts left when toolbar space is available, or drops cleanly to
 *   bottom-right when the top bar is full.
 * - Idle state: subtle neutral icon matching One UI theme.
 * - Dirty state (any stock or SBrowserX preference toggled while in Settings):
 *   lights up in One UI accent blue with a soft halo and subtle pulse animation.
 * - Tapping cleanly restarts the browser process to apply engine/theme changes.
 */
public final class SBrowserXRestartManager {
    private static final String TAG = "SBrowserXRestart";
    private static final String BTN_TAG = "sbrowserx_restart_btn";
    private static final int TAG_WATCHER_ATTACHED = 0x53425801;
    private static final int TAG_FLOATING_BOTTOM = 0x53425802;

    private static volatile boolean sInitialized = false;
    private static volatile boolean sPendingRestart = false;
    private static volatile int sSettingsForegroundCount = 0;

    private static final Handler sMain = new Handler(Looper.getMainLooper());
    private static final List<WeakReference<ImageView>> sActiveButtons = new LinkedList<>();

    private static SharedPreferences.OnSharedPreferenceChangeListener sDefaultPrefListener;
    private static SharedPreferences.OnSharedPreferenceChangeListener sCustomPrefListener;

    private SBrowserXRestartManager() {}

    public static void init(Application app) {
        if (app == null || sInitialized) return;
        sInitialized = true;
        try {
            // Track foreground state across SettingsActivity and all SBrowserX*Activity screens
            app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

                @Override
                public void onActivityStarted(Activity activity) {}

                @Override
                public void onActivityResumed(Activity activity) {
                    if (isSettingsScreen(activity)) {
                        sSettingsForegroundCount++;
                        if (isStockSettingsActivity(activity)) {
                            attachToStockSettingsToolbar(activity);
                        }
                        refreshAllButtons(false);
                    }
                }

                @Override
                public void onActivityPaused(Activity activity) {
                    if (isSettingsScreen(activity)) {
                        sSettingsForegroundCount = Math.max(0, sSettingsForegroundCount - 1);
                    }
                }

                @Override
                public void onActivityStopped(Activity activity) {}

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

                @Override
                public void onActivityDestroyed(Activity activity) {}
            });

            // Listen to SBrowserX preferences
            SharedPreferences customPrefs = app.getSharedPreferences(SBrowserXPrefs.PREFS_NAME, Context.MODE_PRIVATE);
            sCustomPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
                    if (sSettingsForegroundCount > 0 && !isNoisyPrefKey(key)) {
                        markDirtyInternal(true);
                    }
                }
            };
            customPrefs.registerOnSharedPreferenceChangeListener(sCustomPrefListener);

            // Listen to Samsung Internet's default SharedPreferences (<pkg>_preferences)
            SharedPreferences defaultPrefs = app.getSharedPreferences(
                    app.getPackageName() + "_preferences", Context.MODE_PRIVATE);
            sDefaultPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
                    if (sSettingsForegroundCount > 0 && !isNoisyPrefKey(key)) {
                        markDirtyInternal(true);
                    }
                }
            };
            defaultPrefs.registerOnSharedPreferenceChangeListener(sDefaultPrefListener);
        } catch (Throwable t) {
            Log.w(TAG, "init failed", t);
        }
    }

    private static boolean isSettingsScreen(Activity act) {
        if (act == null) return false;
        String name = act.getClass().getName();
        return name.contains("SettingsActivity")
                || name.startsWith("dev.vortexnode.sbrowserx.extension.settings.");
    }

    private static boolean isStockSettingsActivity(Activity act) {
        if (act == null) return false;
        return "com.sec.android.app.sbrowser.settings.SettingsActivity".equals(act.getClass().getName());
    }

    private static boolean isNoisyPrefKey(String key) {
        if (key == null || key.isEmpty()) return true;
        String k = key.toLowerCase(java.util.Locale.ROOT);
        return k.contains("timestamp")
                || k.contains("last_ts")
                || k.contains("last_checked")
                || k.contains("search_history")
                || k.contains("recent_search")
                || k.startsWith("sa_")
                || k.contains("badge");
    }

    /** Called from bytecode hooks (e.g. Dark Mode radio/toggle) or UI listeners. */
    public static void markDirtyFromUi() {
        markDirtyInternal(true);
    }

    public static void markDirty(Context ignored) {
        markDirtyInternal(true);
    }

    public static boolean isPendingRestart() {
        return sPendingRestart;
    }

    private static void markDirtyInternal(final boolean animate) {
        sPendingRestart = true;
        sMain.post(new Runnable() {
            @Override
            public void run() {
                refreshAllButtons(animate);
            }
        });
    }

    /** Called from SettingsActivity.onResume / onConfigurationChanged bytecode hooks. */
    public static void onSettingsActivityResume(final Activity activity) {
        if (activity == null) return;
        sMain.post(new Runnable() {
            @Override
            public void run() {
                attachToStockSettingsToolbar(activity);
                refreshAllButtons(false);
            }
        });
    }

    public static void onSettingsActivityConfigChanged(final Activity activity) {
        if (activity == null) return;
        sMain.postDelayed(new Runnable() {
            @Override
            public void run() {
                attachToStockSettingsToolbar(activity);
                refreshAllButtons(false);
            }
        }, 80L);
    }

    private static void attachToStockSettingsToolbar(final Activity activity) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) return;
        try {
            android.view.Window win = activity.getWindow();
            if (win == null) return;
            View decorObj = win.getDecorView();
            if (!(decorObj instanceof ViewGroup)) return;
            ViewGroup decor = (ViewGroup) decorObj;

            View existing = decor.findViewWithTag(BTN_TAG);
            final ImageView btn;
            if (existing instanceof ImageView) {
                btn = (ImageView) existing;
            } else {
                btn = buildRestartImageView(activity);
                float d = activity.getResources().getDisplayMetrics().density;
                int size = Math.round(40 * d);
                int statusH = getStatusBarHeight(activity, d);

                android.widget.FrameLayout.LayoutParams lp = new android.widget.FrameLayout.LayoutParams(
                        size, size, Gravity.TOP | Gravity.END);
                lp.topMargin = statusH + Math.round(8 * d);
                lp.setMarginEnd(Math.round(14 * d));
                btn.setElevation(16 * d);
                decor.addView(btn, lp);
                registerButton(btn);
            }
            btn.setVisibility(View.VISIBLE);
            btn.bringToFront();
            updateButtonPosition(activity, btn);
            applyButtonVisualState(btn, activity, false);
            registerLayoutCollisionWatcher(activity, btn);
        } catch (Throwable t) {
            Log.w(TAG, "attachToStockSettingsToolbar failed", t);
        }
    }

    private static int getStatusBarHeight(Activity activity, float d) {
        int statusH = 0;
        try {
            int resId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resId > 0) statusH = activity.getResources().getDimensionPixelSize(resId);
        } catch (Throwable ignored) {}
        return statusH > 0 ? statusH : Math.round(28 * d);
    }

    private static int getNavBarHeight(Activity activity, float d) {
        int navH = 0;
        try {
            int resId = activity.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            if (resId > 0) navH = activity.getResources().getDimensionPixelSize(resId);
        } catch (Throwable ignored) {}
        return navH > 0 ? navH : Math.round(48 * d);
    }

    private static void collectToolbarObstacles(
            View view, View ignoreBtn, int screenW, int bandTop, int bandBottom, int maxBtnH, List<Rect> out) {
        if (view == null || view == ignoreBtn || view.getVisibility() != View.VISIBLE || view.getAlpha() < 0.05f) {
            return;
        }
        int w = view.getWidth();
        int h = view.getHeight();
        if (w <= 0 || h <= 0) return;

        int[] loc = new int[2];
        view.getLocationOnScreen(loc);
        int left = loc[0];
        int top = loc[1];
        int right = left + w;
        int bottom = top + h;

        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            // Capture clickable pill containers (e.g. Done / Reset buttons) in the toolbar band
            if (view.isClickable()
                    && w <= (int) (screenW * 0.45f)
                    && h <= maxBtnH
                    && top < bandBottom
                    && bottom > bandTop) {
                out.add(new Rect(left, top, right, bottom));
            }
            for (int i = 0; i < vg.getChildCount(); i++) {
                collectToolbarObstacles(vg.getChildAt(i), ignoreBtn, screenW, bandTop, bandBottom, maxBtnH, out);
            }
            return;
        }

        // Leaf view inside the top toolbar band
        if (top >= bandBottom || bottom <= bandTop || w > (int) (screenW * 0.85f)) {
            return;
        }
        boolean hasVisibleContent = false;
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            CharSequence txt = tv.getText();
            if (txt != null && txt.length() > 0) {
                hasVisibleContent = true;
            } else {
                Drawable[] cds = tv.getCompoundDrawables();
                for (Drawable cd : cds) {
                    if (cd != null) {
                        hasVisibleContent = true;
                        break;
                    }
                }
            }
        } else if (view instanceof ImageView) {
            hasVisibleContent = ((ImageView) view).getDrawable() != null;
        } else if (view.isClickable()) {
            hasVisibleContent = true;
        }

        if (hasVisibleContent) {
            out.add(new Rect(left, top, right, bottom));
        }
    }

    private static void updateButtonPosition(Activity activity, ImageView btn) {
        if (activity == null || btn == null || activity.isFinishing() || activity.isDestroyed()) return;
        try {
            android.view.Window win = activity.getWindow();
            if (win == null) return;
            View decor = win.getDecorView();
            int screenW = decor.getWidth();
            int screenH = decor.getHeight();
            if (screenW <= 0 || screenH <= 0) return;

            float d = activity.getResources().getDisplayMetrics().density;
            int size = Math.round(40 * d);
            int statusH = getStatusBarHeight(activity, d);
            int navH = getNavBarHeight(activity, d);
            int defaultEnd = Math.round(14 * d);
            int defaultTop = statusH + Math.round(8 * d);
            int pad = Math.round(4 * d);
            int gap = Math.round(8 * d);

            int defLeft = screenW - defaultEnd - size;
            int defRight = screenW - defaultEnd;
            int defBottom = defaultTop + size;

            List<Rect> obstacles = new ArrayList<>();
            collectToolbarObstacles(
                    decor,
                    btn,
                    screenW,
                    statusH,
                    statusH + Math.round(80 * d),
                    Math.round(72 * d),
                    obstacles);

            Rect defPadded = new Rect(defLeft - pad, defaultTop - pad, defRight + pad, defBottom + pad);
            boolean collidesDefault = false;
            int minObsLeft = defLeft;
            for (Rect obs : obstacles) {
                if (Rect.intersects(defPadded, obs)) {
                    collidesDefault = true;
                    if (obs.left < minObsLeft) minObsLeft = obs.left;
                }
            }

            int targetGravity = Gravity.TOP | Gravity.END;
            int targetTop = defaultTop;
            int targetBottom = 0;
            int targetEnd = defaultEnd;
            boolean targetFloatingBottom = false;

            if (collidesDefault) {
                // Expand past any adjacent right-side action buttons in the toolbar
                boolean expanded = true;
                while (expanded) {
                    expanded = false;
                    int candRight = minObsLeft - gap;
                    int candLeft = candRight - size;
                    Rect candPadded = new Rect(candLeft - pad, defaultTop - pad, candRight + pad, defBottom + pad);
                    for (Rect obs : obstacles) {
                        if (obs.centerX() > screenW * 0.55f && Rect.intersects(candPadded, obs) && obs.left < minObsLeft) {
                            minObsLeft = obs.left;
                            expanded = true;
                        }
                    }
                }

                int candRight = minObsLeft - gap;
                int candLeft = candRight - size;
                Rect candPadded = new Rect(candLeft - pad, defaultTop - pad, candRight + pad, defBottom + pad);
                boolean shiftLeftClear = candLeft >= Math.round(16 * d);
                if (shiftLeftClear) {
                    for (Rect obs : obstacles) {
                        if (Rect.intersects(candPadded, obs)) {
                            shiftLeftClear = false;
                            break;
                        }
                    }
                }

                if (shiftLeftClear) {
                    targetGravity = Gravity.TOP | Gravity.END;
                    targetTop = defaultTop;
                    targetBottom = 0;
                    targetEnd = screenW - candRight;
                    targetFloatingBottom = false;
                } else {
                    // Top bar is full (e.g. long title + Reset button): drop cleanly to bottom-right
                    targetGravity = Gravity.BOTTOM | Gravity.END;
                    targetTop = 0;
                    targetBottom = navH + Math.round(20 * d);
                    targetEnd = Math.round(16 * d);
                    targetFloatingBottom = true;
                }
            }

            ViewGroup.LayoutParams rawLp = btn.getLayoutParams();
            if (rawLp instanceof android.widget.FrameLayout.LayoutParams) {
                android.widget.FrameLayout.LayoutParams lp = (android.widget.FrameLayout.LayoutParams) rawLp;
                boolean layoutChanged = lp.gravity != targetGravity
                        || lp.topMargin != targetTop
                        || lp.bottomMargin != targetBottom
                        || lp.getMarginEnd() != targetEnd;
                boolean prevFloating = Boolean.TRUE.equals(btn.getTag(TAG_FLOATING_BOTTOM));
                if (layoutChanged) {
                    lp.gravity = targetGravity;
                    lp.topMargin = targetTop;
                    lp.bottomMargin = targetBottom;
                    lp.setMarginEnd(targetEnd);
                    btn.setLayoutParams(lp);
                }
                if (prevFloating != targetFloatingBottom) {
                    btn.setTag(TAG_FLOATING_BOTTOM, targetFloatingBottom);
                    applyButtonVisualState(btn, activity, false);
                }
            }
        } catch (Throwable ignored) {}
    }

    private static void registerLayoutCollisionWatcher(final Activity activity, final ImageView btn) {
        try {
            if (Boolean.TRUE.equals(btn.getTag(TAG_WATCHER_ATTACHED))) return;
            btn.setTag(TAG_WATCHER_ATTACHED, Boolean.TRUE);
            final View decor = activity.getWindow() != null ? activity.getWindow().getDecorView() : null;
            if (decor != null) {
                decor.getViewTreeObserver().addOnGlobalLayoutListener(new android.view.ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if (activity.isFinishing() || activity.isDestroyed()) return;
                        updateButtonPosition(activity, btn);
                    }
                });
            }
        } catch (Throwable ignored) {}
    }

    /** Attaches the same circular Restart button to the right end of any SBrowserX custom header row. */
    public static void attachToCustomHeader(Activity activity, LinearLayout header, boolean night) {
        if (activity == null || header == null) return;
        try {
            View existing = header.findViewWithTag(BTN_TAG);
            ImageView btn;
            if (existing instanceof ImageView) {
                btn = (ImageView) existing;
            } else {
                btn = buildRestartImageView(activity);
                float d = activity.getResources().getDisplayMetrics().density;
                int size = Math.round(38 * d);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
                lp.leftMargin = Math.round(6 * d);
                btn.setLayoutParams(lp);
                header.addView(btn);
                registerButton(btn);
            }
            applyButtonVisualState(btn, activity, false);
        } catch (Throwable ignored) {}
    }

    private static ImageView buildRestartImageView(final Activity activity) {
        ImageView btn = new ImageView(activity);
        btn.setTag(BTN_TAG);
        btn.setScaleType(ImageView.ScaleType.CENTER);
        btn.setClickable(true);
        btn.setFocusable(true);
        btn.setContentDescription(SBrowserXStrings.get(SBrowserXStrings.BTN_RESTART));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sPendingRestart = false;
                SBrowserXWebRtcManager.restartBrowser(activity);
            }
        });
        return btn;
    }

    private static void registerButton(ImageView btn) {
        synchronized (sActiveButtons) {
            Iterator<WeakReference<ImageView>> it = sActiveButtons.iterator();
            while (it.hasNext()) {
                ImageView cur = it.next().get();
                if (cur == null) {
                    it.remove();
                } else if (cur == btn) {
                    return;
                }
            }
            sActiveButtons.add(new WeakReference<>(btn));
        }
    }

    private static void refreshAllButtons(boolean animate) {
        synchronized (sActiveButtons) {
            Iterator<WeakReference<ImageView>> it = sActiveButtons.iterator();
            while (it.hasNext()) {
                ImageView btn = it.next().get();
                if (btn == null) {
                    it.remove();
                    continue;
                }
                Context ctx = btn.getContext();
                applyButtonVisualState(btn, ctx, animate);
            }
        }
    }

    private static void applyButtonVisualState(final ImageView btn, Context ctx, boolean animate) {
        if (btn == null || ctx == null) return;
        try {
            boolean night = SBrowserXHooks.isBrowserNightMode(ctx);
            boolean dirty = sPendingRestart;
            boolean floatingBottom = Boolean.TRUE.equals(btn.getTag(TAG_FLOATING_BOTTOM));
            float d = ctx.getResources().getDisplayMetrics().density;

            int iconColor;
            if (dirty) {
                iconColor = night ? 0xFF4C9BFF : 0xFF2563EB;
            } else {
                iconColor = night ? 0x99FFFFFF : 0x8A000000;
            }

            btn.setImageDrawable(new CircularRestartDrawable(d, iconColor, dirty));

            if (dirty) {
                GradientDrawable pill = new GradientDrawable();
                pill.setShape(GradientDrawable.OVAL);
                if (floatingBottom) {
                    pill.setColor(night ? 0xEB1E293B : 0xF5EFF6FF);
                    pill.setStroke(Math.round(1 * d), night ? 0x554C9BFF : 0x442563EB);
                } else {
                    pill.setColor(night ? 0x2B4C9BFF : 0x222563EB);
                }
                btn.setBackground(pill);
            } else if (floatingBottom) {
                GradientDrawable pill = new GradientDrawable();
                pill.setShape(GradientDrawable.OVAL);
                pill.setColor(night ? 0xE6222226 : 0xF2FFFFFF);
                pill.setStroke(Math.round(1 * d), night ? 0x28FFFFFF : 0x1F000000);
                btn.setBackground(pill);
            } else {
                TypedValue ripple = new TypedValue();
                ctx.getTheme().resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, ripple, true);
                if (ripple.resourceId != 0) {
                    btn.setBackgroundResource(ripple.resourceId);
                } else {
                    btn.setBackground(null);
                }
            }

            if (animate && dirty) {
                btn.animate().cancel();
                btn.setScaleX(0.92f);
                btn.setScaleY(0.92f);
                btn.animate()
                        .scaleX(1.16f)
                        .scaleY(1.16f)
                        .setDuration(140L)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                btn.animate().scaleX(1f).scaleY(1f).setDuration(140L).start();
                            }
                        })
                        .start();
            }
        } catch (Throwable ignored) {}
    }

    /** Crisp One UI-style circular restart arrow (↻) drawn natively on Canvas. */
    private static final class CircularRestartDrawable extends Drawable {
        private final float mDensity;
        private final boolean mEmphasized;
        private final Paint mStrokePaint;
        private final Paint mFillPaint;
        private final Paint mDotPaint;

        CircularRestartDrawable(float density, int color, boolean emphasized) {
            mDensity = density;
            mEmphasized = emphasized;
            mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mStrokePaint.setStyle(Paint.Style.STROKE);
            mStrokePaint.setStrokeWidth((emphasized ? 2.25f : 1.9f) * density);
            mStrokePaint.setStrokeCap(Paint.Cap.ROUND);
            mStrokePaint.setStrokeJoin(Paint.Join.ROUND);
            mStrokePaint.setColor(color);

            mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mFillPaint.setStyle(Paint.Style.FILL);
            mFillPaint.setColor(color);

            mDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mDotPaint.setStyle(Paint.Style.FILL);
            mDotPaint.setColor(0xFFFF7A00);
        }

        @Override
        public int getIntrinsicWidth() {
            return Math.round(22 * mDensity);
        }

        @Override
        public int getIntrinsicHeight() {
            return Math.round(22 * mDensity);
        }

        @Override
        public void draw(Canvas canvas) {
            Rect b = getBounds();
            float cx = b.exactCenterX();
            float cy = b.exactCenterY();
            float r = Math.min(b.width(), b.height()) * 0.36f;

            RectF arc = new RectF(cx - r, cy - r, cx + r, cy + r);
            // Clockwise 285-degree arc ending near 1:30 o'clock (-50 deg)
            canvas.drawArc(arc, 25f, 285f, false, mStrokePaint);

            // Clean triangular arrowhead at the end of the arc (-50 degrees)
            double endRad = Math.toRadians(-50.0);
            float tipX = (float) (cx + r * Math.cos(endRad));
            float tipY = (float) (cy + r * Math.sin(endRad));
            float headLen = 4.6f * mDensity;

            // Tangent direction for clockwise arc at -50 deg is +40 deg
            double tangRad = Math.toRadians(40.0);
            float backX = (float) (tipX - headLen * Math.cos(tangRad));
            float backY = (float) (tipY - headLen * Math.sin(tangRad));

            float perpX = (float) (-Math.sin(tangRad) * headLen * 0.62f);
            float perpY = (float) (Math.cos(tangRad) * headLen * 0.62f);

            Path head = new Path();
            head.moveTo(tipX + (float) (1.2f * mDensity * Math.cos(tangRad)),
                    tipY + (float) (1.2f * mDensity * Math.sin(tangRad)));
            head.lineTo(backX + perpX, backY + perpY);
            head.lineTo(backX - perpX, backY - perpY);
            head.close();
            canvas.drawPath(head, mFillPaint);

            if (mEmphasized) {
                canvas.drawCircle(cx, cy, 2.6f * mDensity, mDotPaint);
            }
        }

        @Override
        public void setAlpha(int alpha) {
            mStrokePaint.setAlpha(alpha);
            mFillPaint.setAlpha(alpha);
            mDotPaint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {
            mStrokePaint.setColorFilter(colorFilter);
            mFillPaint.setColorFilter(colorFilter);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }
    }
}
