# CuteCat / 喵喵小屋

可愛貓咪寵物飼養遊戲 Android 專案。

這是第一版 MVP，目標是讓 GitHub Actions 穩定打包 Android debug APK，並改成 PNG 圖片素材版，不再使用文字貓臉主視覺。

## 目前內容

- 原生 Android Java App
- GitHub Actions 打包前自動生成 PNG 圖片素材
- 6 隻可抽卡貓咪：橘白貓、奶油布偶貓、灰虎斑貓、黑貓、三花貓、白貓
- 狀態值：飽足、心情、清潔、活力
- 資源：金幣、鑽石、愛心、抽卡券
- 裝扮與配件系統
- 家具與道具收藏系統
- 簡易小遊戲
- 隱藏管理員模式：連點設定入口 7 次開啟
- GitHub Actions 自動打包 debug APK

## APK 打包

1. 到 GitHub repo 的 **Actions** 分頁。
2. 打開最新一次 **Build Android Debug APK** workflow。
3. 在 Artifacts 區塊下載 `android-debug-apk`。
4. 解壓縮後安裝 APK 到 Android 手機。

> 這是 debug APK。手機可能需要允許「安裝未知來源 App」。

## 本地建置

```bash
pip install pillow
python tools/generate_assets.py
gradle --no-daemon :app:assembleDebug
```

輸出位置：

```text
app/build/outputs/apk/debug/app-debug.apk
```
