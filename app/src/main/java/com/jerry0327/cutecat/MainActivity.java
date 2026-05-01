package com.jerry0327.cutecat;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {
    private static final String PREF = "cutecat_v1";
    private final Random rng = new Random();
    private SharedPreferences sp;
    private String screen = "HOME";
    private int food = 82, mood = 88, clean = 74, energy = 80;
    private int coins = 12345, gems = 1250, hearts = 260, tickets = 10, level = 12, exp = 38;
    private int cat = 0, adminTap = 0;
    private boolean admin = false;

    private final String[] cats = {"橘白貓", "奶油布偶貓", "灰虎斑貓", "黑貓", "三花貓", "白貓"};
    private final String[] rarities = {"N", "R", "R", "SR", "SR", "SSR"};
    private final String[] outfits = {"紅色蝴蝶結", "紳士帽", "藍色圍巾", "花環", "圓框眼鏡", "鈴鐺項圈", "水手服", "小披風", "魔法帽", "小背包", "雨衣", "星星髮夾"};
    private final String[] items = {"高級貓糧", "鮮魚罐頭", "毛線球", "逗貓棒", "羽毛玩具", "洗澡海綿", "柔軟毛巾", "貓咪餅乾", "寶藏箱", "愛心藥水", "金幣加倍券", "抽卡券"};
    private final String[] furniture = {"粉色沙發", "貓跳台", "暖暖小床", "窗邊盆栽", "魚骨地毯", "木質餐桌", "夢幻浴缸", "玩具收納櫃", "秘密花園門", "扭蛋機", "小夜燈", "甜甜圈抱枕"};

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        sp = getSharedPreferences(PREF, MODE_PRIVATE);
        load();
        draw();
    }

    private void load() {
        food = sp.getInt("food", food); mood = sp.getInt("mood", mood); clean = sp.getInt("clean", clean); energy = sp.getInt("energy", energy);
        coins = sp.getInt("coins", coins); gems = sp.getInt("gems", gems); hearts = sp.getInt("hearts", hearts); tickets = sp.getInt("tickets", tickets);
        level = sp.getInt("level", level); exp = sp.getInt("exp", exp); cat = sp.getInt("cat", cat); admin = sp.getBoolean("admin", false);
    }

    private void save() {
        sp.edit().putInt("food", food).putInt("mood", mood).putInt("clean", clean).putInt("energy", energy).putInt("coins", coins).putInt("gems", gems).putInt("hearts", hearts).putInt("tickets", tickets).putInt("level", level).putInt("exp", exp).putInt("cat", cat).putBoolean("admin", admin).apply();
    }

    private void draw() {
        ScrollView scroll = new ScrollView(this);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(14), dp(14), dp(14), dp(24));
        root.setBackgroundColor(Color.rgb(255, 244, 220));
        scroll.addView(root);
        root.addView(header());
        root.addView(catPanel());
        root.addView(menu());
        if (screen.equals("HOME")) root.addView(home());
        if (screen.equals("CARE")) root.addView(care());
        if (screen.equals("GACHA")) root.addView(gacha());
        if (screen.equals("DRESS")) root.addView(grid("裝扮與配件", outfits));
        if (screen.equals("ITEMS")) root.addView(grid("道具收藏", items));
        if (screen.equals("ROOM")) root.addView(grid("家具與房間", furniture));
        if (screen.equals("MINI")) root.addView(miniGame());
        if (screen.equals("ADMIN")) root.addView(adminPanel());
        setContentView(scroll);
    }

    private LinearLayout header() {
        LinearLayout c = card();
        TextView title = text("喵喵小屋", 30, true);
        title.setGravity(Gravity.CENTER);
        c.addView(title);
        TextView sub = text("可愛貓咪寵物飼養遊戲 V1：抽卡、培養、裝扮、道具、小遊戲與隱藏管理員模式", 14, false);
        sub.setGravity(Gravity.CENTER);
        c.addView(sub);
        Button gear = button("設定 / 隱藏模式入口");
        gear.setOnClickListener(v -> { adminTap++; if (adminTap >= 7) { admin = true; screen = "ADMIN"; toast("管理員模式已開啟"); save(); draw(); } else toast("設定點擊 " + adminTap + "/7"); });
        c.addView(gear);
        c.addView(text("金幣 " + coins + "   鑽石 " + gems + "   愛心 " + hearts + "   抽卡券 " + tickets, 15, true));
        return c;
    }

    private LinearLayout catPanel() {
        LinearLayout c = card();
        TextView face = text(catFace(cat), 84, false);
        face.setGravity(Gravity.CENTER);
        c.addView(face);
        TextView name = text(cats[cat] + "  Lv." + level + "  [" + rarities[cat] + "]", 22, true);
        name.setGravity(Gravity.CENTER);
        c.addView(name);
        c.addView(bar("飽足", food)); c.addView(bar("心情", mood)); c.addView(bar("清潔", clean)); c.addView(bar("活力", energy));
        return c;
    }

    private LinearLayout menu() {
        LinearLayout c = card();
        GridLayout g = new GridLayout(this); g.setColumnCount(3);
        addNav(g, "主畫面", "HOME"); addNav(g, "照顧", "CARE"); addNav(g, "抽卡", "GACHA");
        addNav(g, "裝扮", "DRESS"); addNav(g, "道具", "ITEMS"); addNav(g, "家具", "ROOM");
        addNav(g, "小遊戲", "MINI"); if (admin) addNav(g, "管理", "ADMIN");
        c.addView(g); return c;
    }

    private LinearLayout home() {
        LinearLayout c = card();
        c.addView(text("今日目標", 22, true));
        c.addView(text("1. 餵食一次  2. 幫貓咪洗澡  3. 參加毛線球小遊戲  4. 抽卡或整理房間", 15, false));
        c.addView(text("美術方向：第一版先保留高品質圖片引擎素材規格，後續拆成角色立繪、背景、道具、卡面、動畫序列幀與音效包。", 15, false));
        return c;
    }

    private LinearLayout care() {
        LinearLayout c = card(); c.addView(text("照顧互動", 22, true));
        Button feed = button("餵食 +飽足"); feed.setOnClickListener(v -> action("feed")); c.addView(feed);
        Button bath = button("洗澡 +清潔"); bath.setOnClickListener(v -> action("bath")); c.addView(bath);
        Button play = button("陪玩 +心情"); play.setOnClickListener(v -> action("play")); c.addView(play);
        Button rest = button("睡覺 +活力"); rest.setOnClickListener(v -> action("rest")); c.addView(rest);
        return c;
    }

    private LinearLayout gacha() {
        LinearLayout c = card(); c.addView(text("喵喵招募機", 22, true));
        c.addView(text("消耗 1 張抽卡券或 150 鑽石，獲得不同稀有度貓咪。SSR 保底會在後續版本加入完整記錄。", 15, false));
        Button once = button("單抽"); once.setOnClickListener(v -> pull(1)); c.addView(once);
        Button ten = button("十連抽"); ten.setOnClickListener(v -> pull(10)); c.addView(ten);
        return c;
    }

    private LinearLayout miniGame() {
        LinearLayout c = card(); c.addView(text("毛線球節奏小遊戲", 22, true));
        c.addView(text("點擊貓掌賺取金幣、愛心與經驗。後續會加入動畫、音效、Combo 與關卡制。", 15, false));
        Button paw = button("拍貓掌！+50 金幣 +5 愛心");
        paw.setTextSize(20);
        paw.setOnClickListener(v -> { coins += admin ? 100 : 50; hearts += 5; gainExp(8); save(); draw(); });
        c.addView(paw); return c;
    }

    private LinearLayout adminPanel() {
        LinearLayout c = card(); c.addView(text("隱藏管理員模式", 22, true));
        c.addView(text("測試與體驗專用。可快速取得資源、啟用培養加速與抽卡測試。正式版可改為開發者選單。", 15, false));
        Button rich = button("取得測試資源"); rich.setOnClickListener(v -> { coins += 50000; gems += 5000; tickets += 60; hearts += 999; save(); draw(); }); c.addView(rich);
        Button max = button("全狀態滿值"); max.setOnClickListener(v -> { food = mood = clean = energy = 100; save(); draw(); }); c.addView(max);
        return c;
    }

    private LinearLayout grid(String title, String[] data) {
        LinearLayout c = card(); c.addView(text(title, 22, true));
        GridLayout g = new GridLayout(this); g.setColumnCount(2);
        for (String s : data) { TextView t = text(s + "\n已收集", 15, false); t.setGravity(Gravity.CENTER); t.setBackground(box(Color.WHITE, Color.rgb(244, 174, 146), 14)); GridLayout.LayoutParams lp = new GridLayout.LayoutParams(); lp.width = 0; lp.height = dp(76); lp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); lp.setMargins(dp(4), dp(4), dp(4), dp(4)); g.addView(t, lp); }
        c.addView(g); return c;
    }

    private void action(String type) {
        if (type.equals("feed")) { food = Math.min(100, food + 16); coins -= 20; }
        if (type.equals("bath")) { clean = Math.min(100, clean + 18); coins -= 25; }
        if (type.equals("play")) { mood = Math.min(100, mood + 20); energy = Math.max(0, energy - 8); }
        if (type.equals("rest")) { energy = Math.min(100, energy + 22); mood = Math.min(100, mood + 4); }
        gainExp(10); save(); draw();
    }

    private void pull(int count) {
        int costTickets = count == 10 ? 10 : 1;
        int costGems = count == 10 ? 1350 : 150;
        if (tickets >= costTickets) tickets -= costTickets; else if (gems >= costGems) gems -= costGems; else { toast("抽卡券或鑽石不足"); return; }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) { int r = rng.nextInt(100); int idx = r < 3 ? 5 : r < 18 ? 3 + rng.nextInt(2) : rng.nextInt(3); cat = idx; result.append(cats[idx]).append(" ").append(rarities[idx]).append("\n"); }
        hearts += 20 * count; gainExp(5 * count); save(); toast("獲得：\n" + result.toString()); draw();
    }

    private void gainExp(int v) { exp += admin ? v * 2 : v; while (exp >= 100) { exp -= 100; level++; coins += 300; gems += 20; } }

    private void addNav(GridLayout g, String label, String target) { Button b = button(label); b.setOnClickListener(v -> { screen = target; draw(); }); GridLayout.LayoutParams lp = new GridLayout.LayoutParams(); lp.width = 0; lp.height = dp(52); lp.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); lp.setMargins(dp(4), dp(4), dp(4), dp(4)); g.addView(b, lp); }

    private LinearLayout card() { LinearLayout l = new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); l.setPadding(dp(14), dp(14), dp(14), dp(14)); l.setBackground(box(Color.WHITE, Color.rgb(239, 171, 136), 20)); LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2); p.setMargins(0, 0, 0, dp(12)); l.setLayoutParams(p); return l; }
    private TextView text(String s, int sp, boolean bold) { TextView t = new TextView(this); t.setText(s); t.setTextSize(sp); t.setTextColor(Color.rgb(96, 52, 35)); if (bold) t.setTypeface(Typeface.DEFAULT_BOLD); t.setPadding(dp(4), dp(4), dp(4), dp(4)); return t; }
    private Button button(String s) { Button b = new Button(this); b.setText(s); b.setAllCaps(false); b.setTextColor(Color.rgb(100, 50, 30)); return b; }
    private ProgressBar bar(String label, int value) { ProgressBar p = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal); p.setMax(100); p.setProgress(value); p.setContentDescription(label + " " + value); return p; }
    private GradientDrawable box(int fill, int stroke, int radius) { GradientDrawable d = new GradientDrawable(); d.setColor(fill); d.setCornerRadius(dp(radius)); d.setStroke(dp(2), stroke); return d; }
    private String catFace(int i) { String[] faces = {"ฅ^•ﻌ•^ฅ", "=＾● ⋏ ●＾=", "ฅ(＾・ω・＾ฅ)", "=^._.^= 黒", "ฅ三花ฅ", "ฅ白ฅ"}; return faces[i]; }
    private int dp(int v) { return (int)(v * getResources().getDisplayMetrics().density + 0.5f); }
    private void toast(String s) { Toast.makeText(this, s, Toast.LENGTH_LONG).show(); }
}
