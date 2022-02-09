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

在 xml 中配置该控件，该控件是一个 viewGroup，可以在其下配置相应的子 view

```xml
    <francis.ciruy.lincolnct.com.infintefoldingview.view.InfiniteFoldingView
        android:id="@+id/infiniteFoldingView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <!--使用 horizontalScrollView 是因为 LinearLayout 无法完全显示时，可以滚动显示-->
        <HorizontalScrollView
            android:id="@+id/horizontalScrollView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:paddingLeft="@dimen/dp_10"
            android:paddingRight="@dimen/dp_10"
            android:scrollbars="none"
            tools:ignore="UselessParent">

            <!--标题容器-->
            <LinearLayout
                android:id="@+id/titleView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center_vertical"
                android:orientation="horizontal" />
        </HorizontalScrollView>

        <!--定义的内容布局，可以添加空数据页面等处理-->
        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent">

            <!--内容容器-->
            <android.support.v7.widget.RecyclerView
                android:id="@+id/contentView"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />

            <ProgressBar
                android:id="@+id/loading"
                style="?android:attr/progressBarStyleLarge"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:layout_gravity="center"
                android:background="@color/transparent"
                android:indeterminate="true"
                android:visibility="gone" />
        </FrameLayout>
        
    </francis.ciruy.lincolnct.com.infintefoldingview.view.InfiniteFoldingView>
```

在代码中需要配置与该 view 关联的 titleView 及 contentView，分别对应是 LinearLayout 用于显示标题；RecyclerView 为内容列表，用于显示数据。

```java
//查找view及标题、内容相关的控件
InfiniteFoldingView infiniteFoldingView = findViewById(R.id.infiniteFoldingView);
LinearLayout titleView = findViewById(R.id.titleView);
RecyclerView contentView = findViewById(R.id.contentView);

//创建相应的数据内容及结构，这个需要根据自己的需要进行配置
BaseContactEntity rootEntity = ContactGenerator.createDemoOriginEntity();
infiniteFoldingView
        //设置标题容器的控件
        .setTitleView(titleView)
        //设置内容容器的控件
        .setContentView(contentView)
        //配置标题、内容布局的处理，可以自定义，也可以使用默认的
        .titleViewController(new TitleViewController())
        .contentViewController(new ContentViewController())
        //配置点击事件
        .onItemChildViewClickListener((view, pos, action, entity) -> {
            if (action.equals(InfiniteFoldingView.CLICK_POS.CONTENT.name())) {
                Toast.makeText(MainActivity.this, "点击了部门内容", Toast.LENGTH_SHORT).show();
            } else if (action.equals(InfiniteFoldingView.CLICK_POS.RIGHT_BTN.name())) {
                Toast.makeText(MainActivity.this, "点击了部门右侧箭头", Toast.LENGTH_SHORT).show();
            } else if (action.equals(InfiniteFoldingView.CLICK_POS.TITLE.name())) {
                Toast.makeText(MainActivity.this, "点击了部门标题", Toast.LENGTH_SHORT).show();
            }
        })
        .setAdapter(new CustomContactViewAdapter(MainActivity.this))
        .rootContentEntity(rootEntity);
```

**目前仅支持竖直方向显示**

# License

Apache License Version 2.0