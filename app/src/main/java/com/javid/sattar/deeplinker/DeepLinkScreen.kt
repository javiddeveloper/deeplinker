package com.javid.sattar.deeplinker

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DeepLinkScreen(
    modifier: Modifier=Modifier,
    viewModel: DeepLinkViewModel = hiltViewModel()) {
    val context = LocalContext.current
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Deep Linker",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )
        FieldWithHistory(
            label = "Scheme",
            value = viewModel.scheme,
            onValueChange = viewModel::onSchemeChanged,
            history = viewModel.schemeHistory,
            onHistoryClick = viewModel::onSchemeChanged
        )

        FieldWithHistory(
            label = "Host",
            value = viewModel.host,
            onValueChange = viewModel::onHostChanged,
            history = viewModel.hostHistory,
            onHistoryClick = viewModel::onHostChanged
        )

        FieldWithHistory(
            label = "Path",
            value = viewModel.path,
            onValueChange = viewModel::onPathChanged,
            history = viewModel.pathHistory,
            onHistoryClick = viewModel::onPathChanged
        )

        Text(
            text = "Preview: ${viewModel.getFullUri()}",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = viewModel::onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }

        Button(
            onClick = {
                val uri = viewModel.getFullUri().toUri()
                try {
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "Invalid link", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Open DeepLink")
        }
    }
}