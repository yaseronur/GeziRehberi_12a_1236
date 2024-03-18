package FragmentTutucu;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.gezirehberi_12a_1236.R;
import com.google.android.material.snackbar.Snackbar;


public class FotoKayitFragment extends Fragment {
    public static Uri imageData; // Galeriden gelen görüntünün Uri adresinin tutulacağı değişken
    ImageView imageView3; // ImageView tanımı
    ActivityResultLauncher<Intent> activityResultLauncher; //Galeriye gitmek için gereken Intent
    ActivityResultLauncher<String> permissionResultLauncher; //İzin istemek için gereken tanımlama
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResultLauncherBaslatmalari();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_foto_kayit, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView3 = view.findViewById(R.id.imageView_KayitFotografi);
        if(imageData!=Uri.EMPTY){
            imageView3.setImageURI(imageData);
        }
        //ImageViewe tıklanırsa ne olacağının yazıldığı listener
        imageView3.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             if(ContextCompat.checkSelfPermission(getContext(),
                                                     Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                                                 if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.
                                                         READ_EXTERNAL_STORAGE)){
                                                     Snackbar.make(view,"Permission needed galery",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View view) {
                                                             //Gerekli izin istenir.
                                                             permissionResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                                                         }
                                                     }).show();
                                                 }else{
                                                     // Gerekli izin istenir.
                                                     permissionResultLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                                                 }
                                             }else{
                                                 //Galeriye gidilir.
                                                 Intent intentToGalery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                                 //Launcher yazılarak getirilir.
                                                 activityResultLauncher.launch(intentToGalery);
                                             }
                                         }
                                     }
        );
    }

    private void activityResultLauncherBaslatmalari(){
        //Galeriden görseli ve görselin datasını döndürmek için yazılması gerekenler
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if(intentFromResult!=null){
                        imageData = intentFromResult.getData();
                        imageView3.setImageURI(imageData);
                        System.out.println(imageData);
                    }
                }
            }
        });
        //İzin istekleri için yazılması gerekenler
        permissionResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if(result){
                            Intent intentToGalery = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            activityResultLauncher.launch(intentToGalery);
                        }else{
                            Toast.makeText(getContext(),"Permission needed!", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    } }