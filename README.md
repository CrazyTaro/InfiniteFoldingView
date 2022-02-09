# 项目简介

本项目根据原项目 https://github.com/FrancisYuric/InfiniteFoldingView 修改，更新了一些方法及功能。并发布成开源库。

# 更新内容

1. 分隔demo及库项目
2. 新增标题及内容控件的自定义配置，不再是通过写死固定 id 查找的方式自动获取控件，方便使用者自行调整控件的位置及显示方式。

# 示例

<img src="./gif/demo.gif" style="width:300px">

# 使用方式

[![](https://jitpack.io/v/CrazyTaro/InfiniteFoldingView.svg)](https://jitpack.io/#CrazyTaro/InfiniteFoldingView)

在根目录下添加仓库依赖

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

添加库依赖，将 Tag 更新成上述的发布版本号

```groovy
dependencies {
    implementation 'com.github.CrazyTaro:InfiniteFoldingView:Tag'
}
```

# License

Apache License Version 2.0