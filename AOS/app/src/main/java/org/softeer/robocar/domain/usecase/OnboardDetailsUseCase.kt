package org.softeer.robocar.domain.usecase

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import org.softeer.robocar.data.dto.operation.OnboardData
import org.softeer.robocar.data.repository.onboard.OnboardRepository
import javax.inject.Inject

class OnboardDetailsUseCase @Inject constructor(
    private val onboardRepository: OnboardRepository
) {
    // CoroutineExceptionHandler를 사용하여 비동기 작업 중 발생하는 예외를 처리합니다.
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("OnboardDetailsUseCase", "Error fetching onboard details", exception)
        // 필요한 경우 여기서 사용자에게 오류를 알리는 등의 추가적인 처리를 할 수 있습니다.
    }

    suspend operator fun invoke(
        inOperationId: Long
    ): Result<OnboardData> {
        return try {
            // 성공적으로 데이터를 가져오면 Result의 성공 인스턴스를 반환합니다.
            onboardRepository.getOnboardDetails(inOperationId)
        } catch (exception: Exception) {
            // 예외가 발생하면 로그를 출력하고, Result의 실패 인스턴스를 반환합니다.
            Log.e("OnboardDetailsUseCase", "Error fetching onboard details", exception)
            Result.failure(exception)
        }
    }
}
