
package easyapps.ms.com.config_annotation.ui;

import android.content.Context;

import java.util.List;

import easyapps.ms.com.config_annotation.R;
import easyapps.ms.com.config_annotation.model.AbModel;
import easyapps.ms.com.config_annotation.model.BaseBindingDataItem;
import easyapps.ms.com.config_annotation.ui.adapter.BaseBindingRecyclerAdapter;

import static easyapps.ms.com.config_annotation.utils.Constants.FIELD_BOOLEAN;


public class AbListAdapter extends BaseBindingRecyclerAdapter<Object> {

    private final List<AbModel> abElementList;

    public AbListAdapter(Context context, List<AbModel> abElementList) {
        super(null, context);
        this.abElementList = abElementList;
    }

    @Override
    public int getLayoutIdForViewType(int viewType) {
        switch (viewType) {
            case FIELD_BOOLEAN:
                return R.layout.ab_row_boolean;
            default:
                return R.layout.ab_row_text;
        }
    }

    @Override
    public BaseBindingDataItem getObjectForPosition(int position) {
        return  abElementList.get(position);
    }

    @Override
    public int getItemViewType(int position) {
        return abElementList.get(position).getFieldType();
    }

    @Override
    public int getItemCount() {
        return abElementList.size();
    }

}
