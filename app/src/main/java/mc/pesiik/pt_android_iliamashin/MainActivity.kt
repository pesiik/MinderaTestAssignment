package mc.pesiik.pt_android_iliamashin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import mc.pesiik.pt_android_iliamashin.navigation.AppNavHost
import mc.pesiik.pt_android_iliamashin.ui.theme.PT_Android_iliaMashinTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PT_Android_iliaMashinTheme {
                AppNavHost(
                    onClose = { finish() }
                )
            }
        }
    }
}