package dev.vortexnode.sbrowserx.extension;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Direct DOM page translator powered by Google Translate (via Java HTTP to bypass site CSP/CORS)
 * and hooked straight into Samsung Internet's native TransBar UI events.
 */
public final class SBrowserXDomTranslator {
    private static final String TAG = "SBrowserXDomTrans";
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(2);
    private static final AtomicInteger GENERATION = new AtomicInteger(0);
    private static final String SEP = "\n<<<SBX_SEP>>>\n";

    private static final String EXTRACT_JS =
            "(function(){"
            + "var skip={SCRIPT:1,STYLE:1,NOSCRIPT:1,TEXTAREA:1,CODE:1,PRE:1,SVG:1};"
            + "if(!window.__sbxNodes||!window.__sbxOrig){"
            + "  var nodes=[],orig=[];"
            + "  var w=document.createTreeWalker(document.body||document.documentElement,NodeFilter.SHOW_TEXT,{"
            + "    acceptNode:function(n){"
            + "      var p=n.parentElement;"
            + "      if(!p||skip[p.tagName])return NodeFilter.FILTER_REJECT;"
            + "      var t=n.nodeValue;"
            + "      if(!t||t.trim().length<2)return NodeFilter.FILTER_REJECT;"
            + "      if(/^[\\d\\s.,:%$€£₽+\\-\\/()]+$/.test(t.trim()))return NodeFilter.FILTER_REJECT;"
            + "      return NodeFilter.FILTER_ACCEPT;"
            + "    }"
            + "  });"
            + "  var cur;"
            + "  while((cur=w.nextNode())&&nodes.length<350){"
            + "    nodes.push(cur);orig.push(cur.nodeValue);"
            + "  }"
            + "  window.__sbxNodes=nodes;window.__sbxOrig=orig;"
            + "}"
            + "return JSON.stringify(window.__sbxOrig);"
            + "})();";

    private static final String REVERT_JS =
            "(function(){"
            + "if(window.__sbxNodes&&window.__sbxOrig){"
            + "  for(var i=0;i<window.__sbxNodes.length;i++){"
            + "    try{window.__sbxNodes[i].nodeValue=window.__sbxOrig[i];}catch(e){}"
            + "  }"
            + "}"
            + "window.__sbxNodes=null;window.__sbxOrig=null;"
            + "})();";

    private SBrowserXDomTranslator() {}

    public static void translateCurrentTab(String sourceLang, String targetLang) {
        Context ctx = SBrowserXPrefs.getAppContext();
        if (ctx != null && !SBrowserXPrefs.isGoogleTranslateEnabled(ctx)) return;

        final String sl = (sourceLang == null || sourceLang.isEmpty() || "auto".equalsIgnoreCase(sourceLang) || "und".equalsIgnoreCase(sourceLang))
                ? "auto" : sourceLang.replace('_', '-');
        String resolvedTl = targetLang;
        if ((resolvedTl == null || resolvedTl.isEmpty()) && ctx != null) {
            resolvedTl = SBrowserXPrefs.getTranslateTargetLang(ctx);
        }
        final String tl = (resolvedTl == null || resolvedTl.isEmpty()) ? "ru" : resolvedTl.replace('_', '-');
        final int gen = GENERATION.incrementAndGet();

        new Handler(Looper.getMainLooper()).post(() -> {
            Object tab = getCurrentTab();
            if (tab == null) return;
            evalJs(tab, EXTRACT_JS, rawJson -> {
                if (gen != GENERATION.get()) return;
                if (rawJson == null || rawJson.isEmpty() || "null".equals(rawJson)) return;
                EXECUTOR.execute(() -> {
                    try {
                        String unwrapped = unwrapJsString(rawJson);
                        JSONArray arr = new JSONArray(unwrapped);
                        int n = arr.length();
                        if (n == 0) return;
                        String[] originals = new String[n];
                        for (int i = 0; i < n; i++) {
                            originals[i] = arr.optString(i, "");
                        }
                        String[] translated = translateBatchArray(sl, tl, originals, gen);
                        if (gen != GENERATION.get()) return;
                        applyTranslations(translated, gen);
                    } catch (Throwable t) {
                        Log.e(TAG, "DOM translation failed", t);
                    }
                });
            });
        });
    }

    public static void revertCurrentTab() {
        GENERATION.incrementAndGet();
        new Handler(Looper.getMainLooper()).post(() -> {
            Object tab = getCurrentTab();
            if (tab != null) {
                evalJs(tab, REVERT_JS, null);
            }
        });
    }

    private static String[] translateBatchArray(String sl, String tl, String[] originals, int gen) {
        String[] out = new String[originals.length];
        int i = 0;
        while (i < originals.length && gen == GENERATION.get()) {
            StringBuilder batch = new StringBuilder();
            int start = i;
            int count = 0;
            while (i < originals.length && (count == 0 || batch.length() + originals[i].length() < 1400)) {
                if (count > 0) batch.append(SEP);
                batch.append(originals[i]);
                i++;
                count++;
            }
            try {
                String res = SBrowserXGoogleTranslator.translateTextSync(sl, tl, batch.toString());
                String[] parts = res.split("\\s*<<<SBX_SEP>>>\\s*", -1);
                if (parts.length == count) {
                    for (int k = 0; k < count; k++) {
                        out[start + k] = preserveEdgeWhitespace(originals[start + k], parts[k]);
                    }
                } else {
                    for (int k = 0; k < count; k++) {
                        String single = SBrowserXGoogleTranslator.translateTextSync(sl, tl, originals[start + k]);
                        out[start + k] = preserveEdgeWhitespace(originals[start + k], single);
                    }
                }
            } catch (Throwable t) {
                Log.w(TAG, "Batch translate error", t);
                for (int k = 0; k < count; k++) {
                    out[start + k] = originals[start + k];
                }
            }
        }
        return out;
    }

    private static String preserveEdgeWhitespace(String orig, String trans) {
        if (orig == null || trans == null) return trans != null ? trans : "";
        String lead = orig.length() > 0 && Character.isWhitespace(orig.charAt(0)) ? " " : "";
        String trail = orig.length() > 0 && Character.isWhitespace(orig.charAt(orig.length() - 1)) ? " " : "";
        return lead + trans.trim() + trail;
    }

    private static void applyTranslations(String[] translated, int gen) {
        JSONArray outArr = new JSONArray();
        for (String s : translated) {
            outArr.put(s != null ? s : "");
        }
        String js = "(function(arr){"
                + "if(!window.__sbxNodes)return;"
                + "var len=Math.min(window.__sbxNodes.length,arr.length);"
                + "for(var i=0;i<len;i++){"
                + "  if(arr[i]){try{window.__sbxNodes[i].nodeValue=arr[i];}catch(e){}}"
                + "}"
                + "})(" + outArr.toString() + ");";

        new Handler(Looper.getMainLooper()).post(() -> {
            if (gen != GENERATION.get()) return;
            Object tab = getCurrentTab();
            if (tab != null) {
                evalJs(tab, js, null);
            }
        });
    }

    private static String unwrapJsString(String raw) {
        String s = raw.trim();
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() >= 2) {
            try {
                // Chromium evaluateJavaScript returns JSON-encoded string literal
                JSONArray wrapper = new JSONArray("[" + s + "]");
                return wrapper.getString(0);
            } catch (Throwable ignored) {}
        }
        return s;
    }

    private static Object getCurrentTab() {
        try {
            Activity main = SBrowserXHooks.getMainActivity();
            if (main == null || main.isFinishing() || main.isDestroyed()) return null;
            return main.getClass().getMethod("getCurrentTab").invoke(main);
        } catch (Throwable t) {
            Log.e(TAG, "getCurrentTab failed", t);
            return null;
        }
    }

    private interface JsResultCallback {
        void onResult(String result);
    }

    private static void evalJs(Object tab, String script, JsResultCallback cb) {
        try {
            Class<?> cbCls = Class.forName("com.sec.terrace.TerraceJavaScriptCallback");
            Object proxy = null;
            if (cb != null) {
                proxy = Proxy.newProxyInstance(
                        cbCls.getClassLoader(),
                        new Class<?>[] {cbCls},
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object p, Method m, Object[] args) {
                                if ("handleJavaScriptResult".equals(m.getName()) && args != null && args.length > 0) {
                                    cb.onResult(args[0] instanceof String ? (String) args[0] : null);
                                }
                                return null;
                            }
                        });
            }
            Method eval = tab.getClass().getMethod("evaluateJavaScript", String.class, cbCls);
            eval.invoke(tab, script, proxy);
        } catch (Throwable t) {
            Log.e(TAG, "evaluateJavaScript failed", t);
        }
    }
}
