package easyapps.ms.com.config_annotation.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import easyapps.ms.com.config_annotation.BR;
import easyapps.ms.com.config_annotation.model.BaseBindingDataItem;

/**

 * @param <H> - handler class which would handle clicks and interactions
 */
public abstract class BaseBindingRecyclerAdapter<H> extends RecyclerView.Adapter<BaseBindingRecyclerAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private H handler;

    public BaseBindingRecyclerAdapter(H handler, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.handler = handler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding itemBinding =
                DataBindingUtil.inflate(layoutInflater, getLayoutIdForViewType(viewType), parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(BaseBindingRecyclerAdapter.ViewHolder holder, int position) {
        BaseBindingDataItem object = getObjectForPosition(position);
        holder.bind(object, position);
    }


    public abstract int getLayoutIdForViewType(int viewType);

    public abstract BaseBindingDataItem getObjectForPosition(int position);


    class ViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;

        ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(BaseBindingDataItem object, final int position) {
            object.setPosition(position);
            this.binding.setVariable(BR.itemData, object);
            this.binding.executePendingBindings();
        }
    }

}
