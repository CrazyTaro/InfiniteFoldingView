package francis.ciruy.lincolnct.com.infintefoldingview.controller.viewController;

import android.view.View;

import francis.ciruy.lincolnct.com.infintefoldingview.controller.BaseController;
import francis.ciruy.lincolnct.com.infintefoldingview.entity.BaseEntity;


public abstract class BaseViewController<E extends BaseEntity> extends BaseController {
    View rootView;

    public BaseViewController rootView(View view) {
        this.rootView = view;
        findViewById(view);
        return this;
    }

    protected abstract void findViewById(View rootView);
}
