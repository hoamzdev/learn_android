package com.example.data.base

import com.example.domain.utils.ApiResult
import com.example.domain.utils.DataError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.util.concurrent.CancellationException
import kotlin.coroutines.CoroutineContext

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        context: CoroutineContext = Dispatchers.IO,
        request: suspend CoroutineScope.() -> T,
    ): ApiResult<T> {
        return withContext(context) {
            try {
                ApiResult.Success(request())
            } catch (e: HttpException) {
                ApiResult.Error(
                    DataError(
                        e.code(),
                        e.message(),
                        errorBody = e.response()?.errorBody()?.string()
                    )
                )
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                ApiResult.Error(
                    DataError(
                        null,
                        null,
                        errorBody = e.message.toString()
                    )
                )
            }
        }
    }
}
