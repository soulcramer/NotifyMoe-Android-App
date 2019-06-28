package app.soulcramer.arn.ui.common

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DebouncingQueryTextListener(
    lifecycle: Lifecycle,
    private val onDebouncingTextChange: (String) -> Unit
) : TextWatcher {
    var debouncePeriod: Long = 300

    private val coroutineScope = lifecycle.coroutineScope

    private var searchJob: Job? = null

    override fun afterTextChanged(s: Editable?) {
        // Not used
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Not used
    }

    override fun onTextChanged(newText: CharSequence?, start: Int, before: Int, count: Int) {
        val newFilter = newText?.toString()?.trim()
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newFilter?.let {
                delay(debouncePeriod)
                onDebouncingTextChange(it)
            }
        }
    }
}