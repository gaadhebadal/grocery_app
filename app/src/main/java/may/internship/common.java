package may.internship;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class common {
    public common(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public common(View v, String message){
        Snackbar.make(v,message,Snackbar.LENGTH_SHORT).show();
    }
    public common(Context context, Class<?> nextClass) {
        Intent intent = new Intent(context,nextClass);
        context.startActivity(intent);
    }
}
