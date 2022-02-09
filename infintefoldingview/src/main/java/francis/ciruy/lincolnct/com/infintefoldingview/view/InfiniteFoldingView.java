package francis.ciruy.lincolnct.com.infintefoldingview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Collections;
import java.util.List;

import francis.ciruy.lincolnct.com.infintefoldingview.R;
import francis.ciruy.lincolnct.com.infintefoldingview.adapter.CustomContactViewAdapter;
import francis.ciruy.lincolnct.com.infintefoldingview.controller.viewController.ContactViewController;
import francis.ciruy.lincolnct.com.infintefoldingview.controller.viewController.impl.ContentViewController;
import francis.ciruy.lincolnct.com.infintefoldingview.controller.viewController.impl.TitleViewController;
import francis.ciruy.lincolnct.com.infintefoldingview.entity.BaseContactEntity;
import francis.ciruy.lincolnct.com.infintefoldingview.entity.OnItemChildViewClickListener;
import francis.ciruy.lincolnct.com.infintefoldingview.util.ContactUtil;


/**
 * @author: Ciruy
 * @E-mail: 398712739@qq.com
 * @Blog: https://blog.csdn.net/qq_31433709
 */
public class InfiniteFoldingView extends LinearLayout {
    public enum CLICK_POS {
        //点击位置
        TITLE,
        CONTENT,
        RIGHT_BTN,
    }

    private LinearLayout titleView;
    private RecyclerView contentView;

    private ContactViewController contactViewController;
    private ContactViewController contactTitleController;

    private Boolean isDemoView;
    private CustomContactViewAdapter adapter;
    private Context context;
    private ContactUtil contactUtil;

    private OnItemChildViewClickListener onItemChildViewClickListener;

    public InfiniteFoldingView(Context context) {
        this(context, null);
    }

    public InfiniteFoldingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InfiniteFoldingView onItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
        return this;
    }

    public InfiniteFoldingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttributeSet(attrs);
        setOrientation(VERTICAL);
    }

    public void refreshTitle() {
        if (titleView == null || contactUtil == null) {
            return;
        }
        final List<BaseContactEntity> titleList = contactUtil.getTitleList();
        titleView.removeAllViews();
        for (int i = 0; i < titleList.size(); ++i) {
            BaseContactEntity baseContactEntity = titleList.get(i);
            View childRootView = LayoutInflater.from(context).inflate(contactTitleController.layout(), null, false);
            contactTitleController.view(childRootView).visit(baseContactEntity);
            final int finalI = i;
            childRootView.setOnClickListener(v -> {
                if (onItemChildViewClickListener != null) {
                    onItemChildViewClickListener.onItemChildViewClick(childRootView, -1, CLICK_POS.TITLE.name(), baseContactEntity);
                }
                contactUtil.clicked(contactUtil.getTitleList().get(finalI));
                refreshTitle();
                adapter.setList(contactUtil.getLast().subDepartment);
            });
            titleView.addView(childRootView);
        }
    }

    public InfiniteFoldingView rootContentEntity(BaseContactEntity baseContactEntity) {
        checkViewValid();
        initContent(baseContactEntity);
        return this;
    }

    private void initContent(BaseContactEntity baseContactEntity) {
        if (contactTitleController == null) {
            contactTitleController = new TitleViewController();
        }
        if (contactViewController == null) {
            contactViewController = new ContentViewController();
        }

        this.contactUtil = new ContactUtil(baseContactEntity);
        titleView.removeAllViews();
        View view = LayoutInflater.from(context).inflate(contactTitleController.layout(), null, false);
        contactTitleController.view(view).visit(baseContactEntity);
        view.setOnClickListener(v -> {
            contactUtil.clicked(baseContactEntity);
            if (onItemChildViewClickListener != null) {
                onItemChildViewClickListener.onItemChildViewClick(view, -1, CLICK_POS.TITLE.name(), baseContactEntity);
            }
        });
        titleView.addView(view);
        if (adapter == null || adapter.getList() == null) {
            if (adapter == null) {
                adapter = new CustomContactViewAdapter(context, baseContactEntity.subDepartment);
            }
            if (adapter.getList() == null) {
                adapter.setList(baseContactEntity.subDepartment);
            }
            adapter.registerViewController(contactViewController);
            adapter.setOnItemChildViewClickListener((childView, position, action, obj) -> {
                BaseContactEntity baseContactEntity1 = (BaseContactEntity) obj;
                if (onItemChildViewClickListener != null) {
                    onItemChildViewClickListener.onItemChildViewClick(childView, position, action, obj);
                }
                if (baseContactEntity1.subDepartment == null || baseContactEntity1.subDepartment.size() <= 0) {
                    return;
                }
                contactUtil.clicked(baseContactEntity1);
                refreshTitle();
                adapter.setList(contactUtil.getLast().subDepartment);
            });
            contentView.setAdapter(adapter);
        } else {
            adapter.setList(baseContactEntity.subDepartment);
        }
    }


    private void setContentValue(BaseContactEntity baseContactEntity) {
        contactUtil = new ContactUtil(baseContactEntity);
    }

    public InfiniteFoldingView titleViewController(ContactViewController titleViewController) {
        this.contactTitleController = titleViewController;
        return this;
    }

    public InfiniteFoldingView contentViewController(ContactViewController contentViewController) {
        this.contactViewController = contentViewController;
        return this;
    }

    public LinearLayout getTitleView() {
        return titleView;
    }

    public RecyclerView getContentRecyclerView() {
        return contentView;
    }

    @NonNull
    public List<BaseContactEntity> getTitleList() {
        //2020/8/29 Lincoln-这里可能会获取到空对象，外面会直接使用这个list，返回空集合避免一些错误
        List<BaseContactEntity> list = contactUtil != null ? contactUtil.getTitleList() : null;
        return list == null ? Collections.emptyList() : list;
    }

    public void pushSubDepartments(BaseContactEntity rootContactEntity) {
        contactUtil.clicked(rootContactEntity);
        refreshTitle();
        adapter.setList(contactUtil.getLast().subDepartment);
    }

    protected void initAttributeSet(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.InfiniteFoldingView);
            isDemoView = array.getBoolean(R.styleable.InfiniteFoldingView_isDemoView, false);
            array.recycle();
        }
    }

    private void checkViewValid() {
        if (titleView == null || contentView == null) {
            throw new NullPointerException("是否设置了标题与内容控件？请确认已调用过 setTitleView() 与 setContentView()");
        }
    }

    public InfiniteFoldingView setTitleView(@NonNull LinearLayout titleView) {
        this.titleView = titleView;
        refreshTitle();
        return this;
    }

    public InfiniteFoldingView setContentView(@NonNull RecyclerView contentView) {
        this.contentView = contentView;
        initRecyclerView();
        return this;
    }

    public InfiniteFoldingView setAdapter(CustomContactViewAdapter adapter) {
        if (contactViewController == null) {
            Log.e("Ciruy Log", "Please call contentViewController() to init it first!");
            contactViewController = new ContentViewController();
        }
        this.adapter = adapter.registerViewController(contactViewController);
        contentView.setAdapter(adapter);
        return this;
    }

    //2020/6/20 Lincoln-新增的方法，用于模拟点击当前页面的标题项，达到处理标题项逻辑的功能
    public void performCurrentTitleClick() {
        if (contactTitleController != null) {
            contactTitleController.view().performClick();
        }
    }

    private void initRecyclerView() {
        contentView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        contentView.addItemDecoration(new SpaceItemDecoration(2));
    }
}
