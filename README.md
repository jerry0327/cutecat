# CuteCat / 喵喵小屋

可愛貓咪寵物飼養遊戲 Android 專案。

這是第一版 MVP，目標是先讓 GitHub Actions 能穩定打包 Android debug APK，同時放入完整遊戲架構：貓咪抽卡、培養、裝扮、道具、小遊戲、商店與隱藏管理員模式。

## 目前內容

- 原生 Android Java App
- 可愛貓咪寵物飼養主畫面
- 6 隻可抽卡貓咪：橘白貓、奶油布偶貓、灰虎斑貓、黑貓、三花貓、白貓
- 狀態值：飽足、心情、清潔、活力
- 資源：金幣、鑽石、愛心、抽卡券
- 裝扮與配件系統
- 家具與道具收藏系統
- 簡易小遊戲
- 隱藏管理員模式：連點右上角設定鈕 7 次開啟
- 內建圖片引擎產生的高品質概念圖素材，作為第一版視覺資產
- GitHub Actions 自動打包 debug APK

## 如何下載 APK

1. 到 GitHub repo 的 **Actions** 分頁。
2. 打開最新一次 **Build Android Debug APK** workflow。
3. 在 Artifacts 區塊下載 `android-debug-apk`。
4. 解壓縮後安裝 APK 到 Android 手機。

> 這是 debug APK。手機可能需要允許「安裝未知來源 App」。

## 本地建置

```bash
gradle --no-daemon :app:assembleDebug
```

輸出位置：

```text
app/build/outputs/apk/debug/app-debug.apk
```

## 後續可擴充

- 將更多圖片引擎生成的 PNG/WebP 素材拆成角色立繪、背景、道具、卡面、動畫序列幀
- 增加音效與背景音樂
- 加入正式簽章 APK / AAB
- 加入資料儲存、成就圖鑑、每日任務與更多小遊戲
