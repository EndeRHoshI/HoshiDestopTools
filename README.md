# Hoshi 桌面工具
用于工作开发过程中快速使用各种方便的小工具，同时练习自己的 Compose、Kotlin 多平台开发技能

目前完成了如下功能
* 第几周 (支持计算年/月进度与周数，拥有全新质感的进度条显示)
* 进制转换
* 时间戳转换
* ADB 工具箱 (支持安卓设备连接管理、获取设备型号等详细参数、快捷设置/还原屏幕分辨率及 DPI)

## README from Google
下面这个是 Google 自动生成的 README，保留一下以供参考

This is a Kotlin Multiplatform project targeting Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…