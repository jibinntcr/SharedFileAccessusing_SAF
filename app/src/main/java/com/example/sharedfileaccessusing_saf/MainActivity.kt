package com.example.sharedfileaccessusing_saf

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sharedfileaccessusing_saf.ui.theme.SharedFileAccessusing_SAFTheme

// BITS Pilani Branding Colors
val BitsBlue = Color(0xFF003366)
val BitsRed = Color(0xFFC41230)
val BackgroundGray = Color(0xFFF2F2F2)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedFileAccessusing_SAFTheme {
                SafDemo()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SafDemo() {
    val context = LocalContext.current
    var fileContent by remember { mutableStateOf("No file selected yet.") }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }

    // CONCEPT: The Storage Access Framework (SAF) Workflow
    // SAF allows the user to grant the app access to a specific file.

    // 1. Register a launcher for the "Open Document" contract.
    // This is the modern replacement for startActivityForResult.
    val pickDocLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        // 2. If the user selects a file, the system returns a URI (not a file path).
        selectedUri = uri
        if (uri != null) {
            try {
                // 3. We use the ContentResolver to open an InputStream from the URI.
                // We use .use {} to ensure the stream is automatically closed.
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    fileContent = inputStream.bufferedReader().use { it.readText() }
                }
            } catch (e: Exception) {
                fileContent = "Error: Could not read the selected file."
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("BITS SHARED STORAGE (SAF)", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("User-Driven Document Access", color = Color.White, fontSize = 10.sp)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = BitsBlue)
            )
        },
        bottomBar = {
            BottomAppBar(containerColor = BitsBlue) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("JIBIN N | Course Faculty", color = Color.White, fontWeight = FontWeight.Medium)
                    Text("BITS Pilani - WILP 2026", color = Color.LightGray, fontSize = 11.sp)
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BackgroundGray)
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Privacy-First File Access", color = BitsBlue, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text("Selecting files via System Picker", color = Color.Gray, fontSize = 12.sp)

            Spacer(modifier = Modifier.height(24.dp))

            // BUTTON: Launches the SAF System Picker.
            Button(
                onClick = {
                    // We specify the MIME types we want to allow.
                    // "text/plain" limits the picker to .txt files.
                    pickDocLauncher.launch(arrayOf("text/plain"))
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = BitsBlue),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Select Document from Device")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // In SAF, we don't work with /sdcard/ style paths.
            // We work with URIs like content://com.android.providers...
            Text(
                text = "Selected URI: ${selectedUri ?: "None"}",
                fontSize = 11.sp,
                color = BitsBlue,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // UI Display for the content read from the shared file.
            Surface(
                modifier = Modifier.fillMaxWidth().heightIn(min = 200.dp),
                color = Color.White,
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 2.dp
            ) {
                Text(
                    text = fileContent,
                    modifier = Modifier.padding(16.dp),
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}