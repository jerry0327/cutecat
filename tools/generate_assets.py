from PIL import Image, ImageDraw, ImageFilter
import os, math

OUT = 'app/src/main/res/drawable-nodpi'
os.makedirs(OUT, exist_ok=True)

CREAM=(255,244,220); PEACH=(255,208,190); BROWN=(116,61,38); PINK=(255,154,178)

def save(img, name):
    img.save(os.path.join(OUT, name + '.png'))

def bg(w,h,top=(255,246,224),bottom=(255,220,202)):
    im=Image.new('RGB',(w,h),top); d=ImageDraw.Draw(im)
    for y in range(h):
        t=y/(h-1); c=tuple(int(top[i]*(1-t)+bottom[i]*t) for i in range(3)); d.line((0,y,w,y),fill=c)
    for i in range(28):
        x=(i*79)%w; y=(i*131)%h; r=8+(i%5)*3
        d.ellipse((x-r,y-r,x+r,y+r),fill=(255,235,215),outline=(245,190,160),width=2)
    return im.convert('RGBA')

def rr(d,box,r,fill,outline=None,w=2): d.rounded_rectangle(box,radius=r,fill=fill,outline=outline,width=w)

def cat(draw,cx,cy,s,body,patch=None,eye=(70,42,30)):
    sh=(0,0,0,35)
    draw.ellipse((cx-82*s,cy+80*s,cx+82*s,cy+110*s),fill=sh)
    draw.ellipse((cx-70*s,cy+8*s,cx+70*s,cy+110*s),fill=body,outline=BROWN,width=max(2,int(3*s)))
    draw.ellipse((cx-86*s,cy-92*s,cx+86*s,cy+58*s),fill=body,outline=BROWN,width=max(2,int(3*s)))
    draw.polygon([(cx-70*s,cy-35*s),(cx-48*s,cy-112*s),(cx-18*s,cy-48*s)],fill=body,outline=BROWN)
    draw.polygon([(cx+70*s,cy-35*s),(cx+48*s,cy-112*s),(cx+18*s,cy-48*s)],fill=body,outline=BROWN)
    draw.polygon([(cx-58*s,cy-40*s),(cx-48*s,cy-88*s),(cx-28*s,cy-48*s)],fill=(255,190,190))
    draw.polygon([(cx+58*s,cy-40*s),(cx+48*s,cy-88*s),(cx+28*s,cy-48*s)],fill=(255,190,190))
    if patch:
        draw.ellipse((cx-80*s,cy-88*s,cx-15*s,cy-10*s),fill=patch)
        draw.ellipse((cx+18*s,cy-70*s,cx+70*s,cy-15*s),fill=patch)
    for ox in (-32,32):
        draw.ellipse((cx+ox*s-13*s,cy-20*s,cx+ox*s+13*s,cy+9*s),fill=eye)
        draw.ellipse((cx+ox*s-5*s,cy-16*s,cx+ox*s+1*s,cy-8*s),fill=(255,255,255))
    draw.ellipse((cx-8*s,cy+8*s,cx+8*s,cy+19*s),fill=(238,112,128))
    draw.arc((cx-28*s,cy+12*s,cx,cy+42*s),15,155,fill=BROWN,width=max(2,int(3*s)))
    draw.arc((cx,cy+12*s,cx+28*s,cy+42*s),25,165,fill=BROWN,width=max(2,int(3*s)))
    for side in (-1,1):
        for yy in (-2,12,26): draw.line((cx+side*15*s,cy+yy*s,cx+side*55*s,cy+(yy-8)*s),fill=BROWN,width=max(1,int(2*s)))

def portrait(name, body, patch=None, eye=(70,42,30)):
    im=bg(720,720); d=ImageDraw.Draw(im,'RGBA')
    rr(d,(70,70,650,650),44,(255,255,255,210),(238,168,132),6)
    cat(d,360,370,2.2,body,patch,eye)
    rr(d,(185,590,535,660),30,(255,230,210,230),(238,168,132),4)
    save(im,name)

portrait('cat_orange',(239,151,72),(255,237,205))
portrait('cat_cream',(246,229,203),(210,178,140),(70,115,160))
portrait('cat_tabby',(170,160,145),(80,80,80))
portrait('cat_black',(42,38,36),None,(248,190,40))
portrait('cat_calico',(255,245,225),(226,120,68))
portrait('cat_white',(252,248,238),None,(65,135,190))

im=bg(1280,760); d=ImageDraw.Draw(im,'RGBA')
rr(d,(55,70,1225,690),45,(255,255,255,190),(238,168,132),6)
rr(d,(105,120,500,520),34,(255,232,212),(238,168,132),5)
rr(d,(760,120,1130,520),34,(235,248,255),(180,205,220),5)
rr(d,(140,350,430,540),28,(255,190,185),(180,110,100),4)
for i in range(4): d.rectangle((590+i*32,130,612+i*32,410),fill=(185,126,82)); d.ellipse((550,100,720,170),fill=(204,150,100))
cat(d,640,430,1.65,(239,151,72),(255,237,205))
for x,y,r in [(920,550,40),(1010,590,28),(1080,540,34),(225,190,20),(250,215,12)]: d.ellipse((x-r,y-r,x+r,y+r),fill=(255,150,175,180))
save(im,'hero_scene')

im=bg(1280,700,(245,232,255),(255,218,230)); d=ImageDraw.Draw(im,'RGBA')
rr(d,(70,65,1210,635),42,(40,35,80,220),(238,168,132),6)
d.ellipse((360,135,650,425),fill=(255,190,220),outline=(255,235,245),width=8)
d.ellipse((405,180,605,380),fill=(180,220,255,170),outline=(255,255,255),width=5)
rr(d,(380,390,630,540),35,(255,120,160),(255,235,245),5)
for i,c in enumerate([(239,151,72),(246,229,203),(42,38,36),(252,248,238)]):
    x=700+i*120; rr(d,(x,170,x+95,360),20,(255,255,255,230),(238,168,132),4); cat(d,x+48,280,.55,c)
for i in range(22):
    x=120+(i*51)%1040; y=90+(i*89)%500; d.polygon([(x,y-8),(x+6,y),(x,y+8),(x-6,y)],fill=(255,235,120,220))
save(im,'gacha_scene')

im=bg(1280,640); d=ImageDraw.Draw(im,'RGBA')
rr(d,(55,55,1225,585),42,(255,255,255,210),(238,168,132),6)
colors=[(160,110,255),(255,120,160),(80,150,230),(250,205,90),(120,190,130),(240,145,85),(210,95,95),(80,200,200)]
labels=['bow','hat','scarf','food','ball','fish','brush','gift']
for i in range(8):
    x=120+(i%4)*270; y=120+(i//4)*220; rr(d,(x,y,x+190,y+150),28,(255,242,232),(238,168,132),4)
    d.ellipse((x+45,y+20,x+145,y+120),fill=colors[i],outline=BROWN,width=4)
    rr(d,(x+30,y+122,x+160,y+145),16,(255,255,255,230),(238,168,132),2)
save(im,'items_scene')

im=bg(1280,640,(235,248,255),(255,232,216)); d=ImageDraw.Draw(im,'RGBA')
rr(d,(55,55,1225,585),42,(255,255,255,200),(238,168,132),6)
rr(d,(110,120,430,460),30,(255,218,210),(190,120,100),4)
rr(d,(800,110,1120,450),30,(220,245,255),(160,190,210),4)
cat(d,650,400,1.35,(239,151,72),(255,237,205))
for x,y in [(260,510),(920,510),(1080,500),(190,220)]: d.ellipse((x-30,y-30,x+30,y+30),fill=(255,170,190,180))
save(im,'room_scene')
print('Generated CuteCat PNG assets in', OUT)
