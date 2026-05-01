package com.jerry0327.cutecat;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.*;
import android.graphics.*;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import java.util.Random;

public class MainActivity extends Activity{
  Random r=new Random(); LinearLayout root; int cat=0,coin=12345,gem=1250,ticket=10,lv=12,tap=0; boolean admin=false;
  String page="home"; int food=85,happy=90,clean=80,power=75;
  String[] names={"橘白貓","奶油布偶貓","灰虎斑貓","黑貓","三花貓","白貓"};
  String[] rare={"N","R","R","SR","SR","SSR"};
  int[] pics={R.drawable.cat_orange,R.drawable.cat_cream,R.drawable.cat_tabby,R.drawable.cat_black,R.drawable.cat_calico,R.drawable.cat_white};
  public void onCreate(Bundle b){super.onCreate(b);draw();}
  void draw(){ScrollView s=new ScrollView(this);root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(14),dp(14),dp(14),dp(22));root.setBackgroundColor(Color.rgb(255,244,220));s.addView(root);head();pet();nav();if(page.equals("home"))home();if(page.equals("care"))care();if(page.equals("gacha"))gacha();if(page.equals("dress"))list("裝扮",new String[]{"蝴蝶結","紳士帽","魔法帽","藍圍巾","眼鏡","水手服","小披風","花環"});if(page.equals("item"))list("道具",new String[]{"貓糧","魚罐頭","毛線球","逗貓棒","浴巾","寶箱","愛心","抽卡券"});if(page.equals("room"))list("家具",new String[]{"沙發","貓跳台","小床","浴缸","花園","扭蛋機","衣櫃","小夜燈"});if(page.equals("game"))game();if(page.equals("admin"))admin();setContentView(s);}
  void head(){LinearLayout c=card();TextView t=txt("喵喵小屋",31,true);t.setGravity(Gravity.CENTER);c.addView(t);c.addView(txt("PNG 素材版：抽卡・培養・裝扮・道具・小遊戲",14,false));Button b=btn("設定 / 隱藏入口");b.setOnClickListener(v->{tap++;if(tap>=7){admin=true;page="admin";}draw();});c.addView(b);c.addView(txt("金幣 "+coin+"   鑽石 "+gem+"   抽卡券 "+ticket+(admin?"   ADMIN":""),15,true));root.addView(c);} 
  void pet(){LinearLayout c=card();c.addView(img(pics[cat],280));TextView n=txt(names[cat]+"  Lv."+lv+"  ["+rare[cat]+"]",22,true);n.setGravity(Gravity.CENTER);c.addView(n);bar(c,"飽足",food);bar(c,"心情",happy);bar(c,"清潔",clean);bar(c,"活力",power);root.addView(c);} 
  void nav(){LinearLayout c=card();GridLayout g=new GridLayout(this);g.setColumnCount(3);nb(g,"主畫面","home");nb(g,"照顧","care");nb(g,"抽卡","gacha");nb(g,"裝扮","dress");nb(g,"道具","item");nb(g,"家具","room");nb(g,"小遊戲","game");if(admin)nb(g,"管理","admin");c.addView(g);root.addView(c);} 
  void home(){LinearLayout c=card();c.addView(img(R.drawable.hero_scene,230));c.addView(txt("今日目標",22,true));c.addView(txt("照顧貓咪、抽卡收集、裝扮房間。這版已加入建置時自動產生的 PNG 素材。",15,false));root.addView(c);} 
  void care(){LinearLayout c=card();c.addView(img(R.drawable.room_scene,200));c.addView(txt("照顧互動",22,true));ab(c,"餵食 +飽足",0);ab(c,"洗澡 +清潔",1);ab(c,"陪玩 +心情",2);ab(c,"睡覺 +活力",3);root.addView(c);} 
  void gacha(){LinearLayout c=card();c.addView(img(R.drawable.gacha_scene,230));c.addView(txt("喵喵招募機",22,true));Button a=btn("單抽");a.setOnClickListener(v->pull(1));c.addView(a);Button ten=btn("十連抽");ten.setOnClickListener(v->pull(10));c.addView(ten);root.addView(c);} 
  void game(){LinearLayout c=card();c.addView(img(R.drawable.items_scene,180));c.addView(txt("毛線球小遊戲",22,true));Button p=btn("拍貓掌！+50 金幣");p.setOnClickListener(v->{coin+=admin?100:50;draw();});c.addView(p);root.addView(c);} 
  void admin(){LinearLayout c=card();c.addView(txt("隱藏管理員模式",22,true));Button b=btn("取得大量資源");b.setOnClickListener(v->{coin+=50000;gem+=5000;ticket+=60;draw();});c.addView(b);Button m=btn("狀態全滿");m.setOnClickListener(v->{food=happy=clean=power=100;draw();});c.addView(m);root.addView(c);} 
  void list(String title,String[] xs){LinearLayout c=card();c.addView(img(R.drawable.items_scene,150));c.addView(txt(title,22,true));GridLayout g=new GridLayout(this);g.setColumnCount(2);for(String x:xs){TextView v=txt(x+"\n已收集",15,false);v.setGravity(Gravity.CENTER);v.setBackground(bg(Color.WHITE,Color.rgb(244,174,146),14));GridLayout.LayoutParams lp=new GridLayout.LayoutParams();lp.width=0;lp.height=dp(76);lp.columnSpec=GridLayout.spec(GridLayout.UNDEFINED,1f);lp.setMargins(dp(4),dp(4),dp(4),dp(4));g.addView(v,lp);}c.addView(g);root.addView(c);} 
  void pull(int n){if(ticket<n&&gem<150*n){toast("資源不足");return;}if(ticket>=n)ticket-=n;else gem-=150*n;String got="";for(int i=0;i<n;i++){int x=r.nextInt(100);cat=x<4?5:x<20?3+r.nextInt(2):r.nextInt(3);got=names[cat]+" "+rare[cat];}toast("獲得："+got);draw();}
  void ab(LinearLayout c,String s,int k){Button b=btn(s);b.setOnClickListener(v->{if(k==0)food=Math.min(100,food+15);if(k==1)clean=Math.min(100,clean+15);if(k==2)happy=Math.min(100,happy+15);if(k==3)power=Math.min(100,power+15);draw();});c.addView(b);} 
  void nb(GridLayout g,String s,String p){Button b=btn(s);b.setOnClickListener(v->{page=p;draw();});GridLayout.LayoutParams lp=new GridLayout.LayoutParams();lp.width=0;lp.height=dp(54);lp.columnSpec=GridLayout.spec(GridLayout.UNDEFINED,1f);lp.setMargins(dp(5),dp(5),dp(5),dp(5));g.addView(b,lp);} 
  void bar(LinearLayout c,String s,int v){c.addView(txt(s+" "+v+"/100",13,true));ProgressBar p=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);p.setMax(100);p.setProgress(v);c.addView(p);} 
  ImageView img(int res,int h){ImageView i=new ImageView(this);i.setImageResource(res);i.setScaleType(ImageView.ScaleType.CENTER_CROP);i.setBackground(bg(Color.rgb(255,236,219),Color.rgb(244,174,146),18));i.setLayoutParams(new LinearLayout.LayoutParams(-1,dp(h)));return i;} 
  LinearLayout card(){LinearLayout l=new LinearLayout(this);l.setOrientation(LinearLayout.VERTICAL);l.setPadding(dp(14),dp(14),dp(14),dp(14));l.setBackground(bg(Color.WHITE,Color.rgb(239,171,136),22));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.setMargins(0,0,0,dp(13));l.setLayoutParams(p);return l;} 
  TextView txt(String s,int z,boolean bold){TextView t=new TextView(this);t.setText(s);t.setTextSize(z);t.setTextColor(Color.rgb(96,52,35));if(bold)t.setTypeface(Typeface.DEFAULT_BOLD);t.setPadding(dp(4),dp(4),dp(4),dp(4));return t;} 
  Button btn(String s){Button b=new Button(this);b.setText(s);b.setAllCaps(false);b.setTextColor(Color.rgb(100,50,30));b.setBackground(bg(Color.rgb(255,225,196),Color.rgb(235,158,119),14));return b;} 
  GradientDrawable bg(int f,int st,int r){GradientDrawable d=new GradientDrawable();d.setColor(f);d.setCornerRadius(dp(r));d.setStroke(dp(2),st);return d;}int dp(int v){return(int)(v*getResources().getDisplayMetrics().density+.5f);}void toast(String s){Toast.makeText(this,s,Toast.LENGTH_LONG).show();}
}
