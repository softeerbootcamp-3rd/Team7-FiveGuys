package org.softeer.robocar.data.mapper

import org.softeer.robocar.data.dto.operation.OnboardData
import org.softeer.robocar.data.model.Onboard

fun OnboardData.toOnboard(): Onboard {
    return Onboard(
        departureAddress = this.departureAddress,
        hostDestAddress = this.hostDestAddress,
        guestDestAddress = this.guestDestAddress,
        hostId = this.hostId,
        guestId = this.guestId
    )
}
