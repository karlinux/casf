package mx.com.aulaxalapa.capturaelec.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class FotoViewModel extends AndroidViewModel {

    ProfileRepository profileRepository;
    //public LiveData<String> photoProfile;

    public FotoViewModel(@NonNull Application application) {
        super(application);
        profileRepository = new ProfileRepository();
    }

    public void uploadFoto( String photo ){
        profileRepository.subirFoto( photo );
    }
}
