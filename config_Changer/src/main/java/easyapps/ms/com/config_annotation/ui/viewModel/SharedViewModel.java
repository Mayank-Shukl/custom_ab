package easyapps.ms.com.config_annotation.ui.viewModel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.VisibleForTesting;

import java.util.List;
import java.util.Map;

import easyapps.ms.com.config_annotation.model.AbModel;
import easyapps.ms.com.config_annotation.repo.LocalMapRepository;
import easyapps.ms.com.config_annotation.utils.LocalMap;

public class SharedViewModel extends ViewModel {

    private Map<String, List<AbModel>> lobListMap;
    private LocalMapRepository localMapRepository = new LocalMapRepository();
    private MutableLiveData<String> toastString = new MutableLiveData<>();

    @VisibleForTesting
    public LiveData<Map<String, List<AbModel>>> getAbData(Object data) {
        return Transformations.map(localMapRepository.generateData(data), input -> {
            lobListMap = input;
            return input;
        });
    }

    public void onSaveSettingsClicked() {
        LocalMap.storeData(lobListMap);
        toastString.setValue("settings saved successfully");
    }

    public void onUseAptimizeClicked() {
        LocalMap.getLocalMap().clear();
        toastString.setValue("settings reset done");
    }


    @VisibleForTesting
    public List<AbModel> getLobList(String lob){
        return lobListMap.get(lob);
    }

    public LiveData<String> getToastString(){
        return toastString;
    }

    @Override
    protected void onCleared() {
        localMapRepository.onCleared();
    }
}
