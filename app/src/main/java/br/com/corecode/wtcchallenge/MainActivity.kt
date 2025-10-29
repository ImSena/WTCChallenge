package br.com.corecode.wtcchallenge

import android.Manifest
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import br.com.corecode.wtcchallenge.ui.navigation.AppNavigation
import br.com.corecode.wtcchallenge.ui.theme.WTCChallengeTheme
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val token = task.result
                    Log.d("TOKEN_FIREBASE", token)
                    println(token)
                }else{
                    Log.d("TOKEN_FIREBASE", "sem token")
                }
            }
        enableEdgeToEdge()

        setContent {
            WTCChallengeTheme(darkTheme = false) {
                NotificationPermissionHandler {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun NotificationPermissionHandler(content: @Composable () -> Unit) {
    val context = LocalContext.current

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Permissão de notificações concedida", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permissão negada", Toast.LENGTH_SHORT).show()
            }
        }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permission = Manifest.permission.POST_NOTIFICATIONS
        when {
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED -> Unit
            else -> {
                AlertDialog.Builder(context)
                    .setTitle("Permitir notificações")
                    .setMessage("Podemos enviar notificações para avisar sobre novas mensagens e atualizações importantes?")
                    .setPositiveButton("Permitir") { _, _ ->
                        requestPermissionLauncher.launch(permission)
                    }
                    .setNegativeButton("Agora não", null)
                    .show()
            }
        }
    }

    content()
}
