package francis.ciruy.lincolnct.com.infintefoldingview.controller.viewController.impl;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import francis.ciruy.lincolnct.com.infintefoldingview.R;
import francis.ciruy.lincolnct.com.infintefoldingview.controller.viewController.ContactViewController;
import francis.ciruy.lincolnct.com.infintefoldingview.entity.impl.DemoEntity;


public class TitleViewController extends ContactViewController<DemoEntity> {
    protected TextView name;
    protected ImageView right;

    @Override
    public void visit(DemoEntity obj) {
        super.visit(obj);
        name.setText(obj.name);
        right.setVisibility(hasNextOrChild == null || !hasNextOrChild ? View.VISIBLE : View.GONE);
        name.setTextColor((obj.isSelected) ? Color.BLUE : Color.BLACK);
    }

    @Override
    protected void findViewById(View rootView) {
        name = rootView.findViewById(R.id.name);
        right = rootView.findViewById(R.id.right);
    }

    @Override
    public int layout() {
        return R.layout.ly_my_contact_title;
    }
}
