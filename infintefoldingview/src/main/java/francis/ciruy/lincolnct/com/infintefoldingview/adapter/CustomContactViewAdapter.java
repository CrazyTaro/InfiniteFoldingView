package francis.ciruy.lincolnct.com.infintefoldingview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import francis.ciruy.lincolnct.com.infintefoldingview.controller.viewController.ContactViewController;
import francis.ciruy.lincolnct.com.infintefoldingview.entity.BaseContactEntity;
import francis.ciruy.lincolnct.com.infintefoldingview.entity.OnItemChildViewClickListener;
import francis.ciruy.lincolnct.com.infintefoldingview.view.InfiniteFoldingView;


public class CustomContactViewAdapter<T extends BaseContactEntity> extends RecyclerView.Adapter {
    private List<T> list;
    private ContactViewController contactViewController;
    private OnItemChildViewClickListener onItemChildViewClickListener;


    public void setOnItemChildViewClickListener(OnItemChildViewClickListener onItemChildViewClickListener) {
        this.onItemChildViewClickListener = onItemChildViewClickListener;
    }

    public CustomContactViewAdapter(Context context, List<T> list) {
        super();
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public CustomContactViewAdapter(Context context) {
        super();
    }

    public CustomContactViewAdapter registerViewController(ContactViewController contactViewController) {
        this.contactViewController = contactViewController;
        return this;
    }

    public class VH extends RecyclerView.ViewHolder {
        public VH(View itemView) {
            super(itemView);
            View right = contactViewController.view(itemView).rightView();
            if (right != null) {
                right.setOnClickListener(v -> {
                    if (onItemChildViewClickListener != null && getAdapterPosition() >= 0) {
                        onItemChildViewClickListener.onItemChildViewClick(v, VH.this.getAdapterPosition(),
                                InfiniteFoldingView.CLICK_POS.RIGHT_BTN.name(), list.get(VH.this.getAdapterPosition()));
                    }
                });
            }
            itemView.setOnClickListener(v -> {
                if (onItemChildViewClickListener != null && getAdapterPosition() >= 0) {
                    onItemChildViewClickListener.onItemChildViewClick(v, VH.this.getAdapterPosition(),
                            InfiniteFoldingView.CLICK_POS.CONTENT.name(), list.get(VH.this.getAdapterPosition()));
                }
            });
        }
    }

    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(contactViewController.layout(), parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        contactViewController.view(holder.itemView).visit(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

}