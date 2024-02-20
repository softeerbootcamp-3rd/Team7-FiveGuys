package org.softeer.robocar.data.repository.route

import org.softeer.robocar.data.model.Route
import org.softeer.robocar.data.service.RouteService
import org.softeer.robocar.data.mapper.toRoute

class RouteRepositoryImpl(private val routeService: RouteService) : RouteRepository {

    override suspend fun getOptimizedRoute(
        startPoint: String,
        hostGoal: String,
        guestGoal: String
    ): Route {
        // 백엔드로부터 RouteResponse를 가져온 후, Route 모델로 변환
        val routeResponse = routeService.getRoute(
            startPoint = startPoint,
            hostGoal = hostGoal,
            guestGoal = guestGoal
        )
        return routeResponse.toRoute() // 응답을 Route 모델로 변환하여 반환
    }
}
