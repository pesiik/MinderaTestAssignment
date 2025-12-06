package mc.pesiik.pt_android_iliamashin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import mc.pesiik.pt_android_iliamashin.ui.ReposScreenList
import mc.pesiik.pt_android_iliamashin.ui.theme.PT_Android_iliaMashinTheme
import mc.pesiik.pt_android_iliamashin.view.ReposListViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val vm: ReposListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PT_Android_iliaMashinTheme {
                ReposScreenList(vm) {
                    finish()
                }
            }
        }
    }
}