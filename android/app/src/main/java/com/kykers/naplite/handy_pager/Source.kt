package dashkudov.handy_pager

import android.util.Log
import androidx.paging.PositionalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


internal class Source<T>(private val builder: HandyPager.Builder<T>) : PositionalDataSource<T>() {

    override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<T>
    ) {

        CoroutineScope(IO).launch {

            if (builder.isErrorOccured) {

                withContext(Main) { builder.mCallback.onError(params.requestedStartPosition, builder.mPageSize) }

            } else {

                withContext(Main) { builder.mCallback.onLoading(params.requestedStartPosition, builder.mPageSize) }

            }

            val part = builder.requireFactory().invoke(params.requestedStartPosition, builder.mPageSize)

            if (part.isNotEmpty()) {

                builder.isErrorOccured = false
                withContext(Main) { builder.mCallback.onSuccess(params.requestedStartPosition, builder.mPageSize) }
                callback.onResult(part, params.requestedStartPosition)

            } else {

                builder.isErrorOccured = true
                withContext(Main) { builder.mCallback.onError(params.requestedStartPosition, builder.mPageSize) }
                loadInitial(params, callback)

            }
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {

        CoroutineScope(IO).launch {

            if (builder.isErrorOccured) {

                withContext(Main) { builder.mCallback.onError(params.startPosition, params.loadSize) }

            } else {

                withContext(Main) { builder.mCallback.onLoading(params.startPosition, params.loadSize) }

            }

            val part = builder.requireFactory().invoke(params.startPosition, params.loadSize)

            if (part.isNotEmpty()) {

                builder.isErrorOccured = false
                withContext(Main) { builder.mCallback.onSuccess(params.startPosition, params.loadSize) }
                callback.onResult(part)

            } else {

                builder.isErrorOccured = true
                withContext(Main) { builder.mCallback.onError(params.startPosition, params.loadSize) }
                loadRange(params, callback)

            }
        }
    }
}