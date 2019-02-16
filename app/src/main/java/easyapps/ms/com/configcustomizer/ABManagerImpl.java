package easyapps.ms.com.configcustomizer;

import com.easyapps.AbManager;

public class ABManagerImpl extends AbManager {


    private static ABManagerImpl abManager;

    private ABManagerImpl(){
        // empty
    }

    public static ABManagerImpl getInstance(){
        if(abManager == null){
            abManager = new ABManagerImpl();
        }
        return abManager;
    }

}
