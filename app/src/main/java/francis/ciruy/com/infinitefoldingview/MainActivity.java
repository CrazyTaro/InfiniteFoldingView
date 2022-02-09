package francis.ciruy.com.infinitefoldingview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import francis.ciruy.lincolnct.com.infintefoldingview.adapter.CustomContactViewAdapter;
import francis.ciruy.lincolnct.com.infintefoldingview.controller.viewController.impl.ContentViewController;
import francis.ciruy.lincolnct.com.infintefoldingview.controller.viewController.impl.TitleViewController;
import francis.ciruy.lincolnct.com.infintefoldingview.demoGenerator.ContactGenerator;
import francis.ciruy.lincolnct.com.infintefoldingview.entity.BaseContactEntity;
import francis.ciruy.lincolnct.com.infintefoldingview.view.InfiniteFoldingView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InfiniteFoldingView infiniteFoldingView = findViewById(R.id.infiniteFoldingView);
        LinearLayout titleView = findViewById(R.id.titleView);
        RecyclerView contentView = findViewById(R.id.contentView);

        BaseContactEntity rootEntity = ContactGenerator.createDemoOriginEntity();
        infiniteFoldingView
                .setTitleView(titleView)
                .setContentView(contentView)
                .titleViewController(new TitleViewController())
                .contentViewController(new ContentViewController())
                .onItemChildViewClickListener((view, pos, action, entity) -> {
                    if (action.equals(InfiniteFoldingView.CLICK_POS.CONTENT.name())) {
                        Toast.makeText(MainActivity.this, "点击了部门内容", Toast.LENGTH_SHORT).show();
                        Log.e("Ciruy Log", "点击了部门内容");
                    } else if (action.equals(InfiniteFoldingView.CLICK_POS.RIGHT_BTN.name())) {
                        Toast.makeText(MainActivity.this, "点击了部门右侧箭头", Toast.LENGTH_SHORT).show();
                        Log.e("Ciruy Log", "点击了部门右侧箭头");
                    } else if (action.equals(InfiniteFoldingView.CLICK_POS.TITLE.name())) {
                        Toast.makeText(MainActivity.this, "点击了部门标题", Toast.LENGTH_SHORT).show();
                        Log.e("Ciruy Log", "点击了部门标题");
                    }
                })
                .setAdapter(new CustomContactViewAdapter(MainActivity.this))
                .rootContentEntity(rootEntity);
    }
}
