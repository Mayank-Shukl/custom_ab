package easyapps.ms.com.config_annotation.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import easyapps.ms.com.config_annotation.AbElement;
import easyapps.ms.com.config_annotation.model.AbModel;
import easyapps.ms.com.config_annotation.utils.LocalMap;
import easyapps.ms.com.config_annotation.utils.Utils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class LocalMapRepository {

    private CompositeDisposable disposable = new CompositeDisposable();

    public LiveData<Map<String, List<AbModel>>> generateData(Object data) {
        MutableLiveData<Map<String,List<AbModel>>> mapLiveData =  new MutableLiveData<>();
        mapLiveData.setValue(null);
        disposable.add(Observable.fromCallable(()->getDataFromReflection(data))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(lobListMap -> {
                    mapLiveData.setValue(lobListMap);
                    Log.d("ABCONFIG",lobListMap.size()+"");
                }
                        , throwable ->{ mapLiveData.setValue(null);
                            Log.d("ABCONFIG","error when loaded data");
                }
                            ));
        return mapLiveData;
    }

    @VisibleForTesting
    public Map<String,List<AbModel>> getDataFromReflection(Object data){
        Map<String, List<AbModel>> mapElements = new LinkedHashMap<>();
        Class abConfigClass = data.getClass();
        for (Field field : abConfigClass.getDeclaredFields()) {
            AbElement element = field.getAnnotation(AbElement.class);
            if (element == null) {
                continue;
            }
            AbModel abModel = getAbModelForField(field,data);
            final List<AbModel> abElementList;
            if (mapElements.containsKey(element.lob())) {
                abElementList = mapElements.get(element.lob());
            } else {
                abElementList = new ArrayList<>();
            }

            abElementList.add(abModel);
            mapElements.put(element.lob(), abElementList);
        }
        return mapElements;

    }

    @VisibleForTesting
    public AbModel getAbModelForField(Field field,Object data){
        AbElement element = field.getAnnotation(AbElement.class);
        AbModel abModel = new AbModel();
        abModel.setFieldName(field.getName());
        abModel.setDate(element.date());
        abModel.setFieldType(Utils.getFieldFromClassName(field.getType()));
        abModel.setClassName(field.getType().getName());
        abModel.setLobName(element.lob());
        Object value = null;
        field.setAccessible(true);
        try {
            value = LocalMap.getLocalMap().get(field.getName()) == null ? field.get(data) : LocalMap.getLocalMap().get(field.getName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        abModel.setData(value);
        return abModel;
    }


    public void onCleared(){
        if(!disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
