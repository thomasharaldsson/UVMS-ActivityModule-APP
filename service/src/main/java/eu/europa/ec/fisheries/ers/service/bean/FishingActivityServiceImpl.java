/*
 *
 *  Developed by the European Commission - Directorate General for Maritime Affairs and Fisheries @ European Union, 2015-2020.
 *
 *  This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can redistribute it
 *  and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of
 *  the License, or any later version. The IFDM Suite is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 *  details. You should have received a copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.
 *
 */

package eu.europa.ec.fisheries.ers.service.bean;

import eu.europa.ec.fisheries.ers.fa.entities.DelimitedPeriodEntity;
import eu.europa.ec.fisheries.ers.fa.entities.FaReportDocumentEntity;
import eu.europa.ec.fisheries.ers.fa.entities.FishingActivityEntity;
import eu.europa.ec.fisheries.ers.service.FishingTripService;
import eu.europa.ec.fisheries.ers.service.MovementModuleService;
import eu.europa.ec.fisheries.ers.service.dto.DateRangeDto;
import eu.europa.ec.fisheries.schema.movement.v1.MovementBaseType;
import eu.europa.ec.fisheries.schema.movement.v1.MovementType;
import eu.europa.ec.fisheries.uvms.commons.service.exception.RuntimeServiceException;
import eu.europa.ec.fisheries.uvms.commons.service.exception.ServiceException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class FishingActivityServiceImpl implements FishingActivityService {

    private final MovementModuleService movementModuleService;
    private final FishingTripService fishingTripService;

    @Inject
    public FishingActivityServiceImpl(MovementModuleService movementModuleService, FishingTripService fishingTripService){
        this.movementModuleService = movementModuleService;
        this.fishingTripService = fishingTripService;
    }

    @Override
    public List<String> findMovementGuidsByIdentifierIdsAndAssetGuid(List<String> reportIds, String assetGuid) throws ServiceException {
        try {
            return fishingTripService.findFaReportDocumentsByIdentifierIds(reportIds).stream()
                    .map(report -> Optional.of(report)
                        	.map(this::findStartAndEndDate)
							.flatMap(range -> getAllMovementGuidsForDateRange(range.getStartDate(), range.getEndDate(), Collections.singletonList(assetGuid))
									.sorted(byDateClosestTo(range))
									.map(MovementBaseType::getGuid)
									.findFirst()
							)
							.orElse(null)
                    )
					.filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (RuntimeServiceException e) {
            throw (ServiceException) e.getCause();
        }
    }

    @Override
    public DateRangeDto findStartAndEndDate(FaReportDocumentEntity faReportDocumentEntity) {
        DateRangeDto result = new DateRangeDto();
        for (FishingActivityEntity fishingActivity : faReportDocumentEntity.getFishingActivities()) {
            Date currentDate = fishingActivity.getOccurence() != null ? fishingActivity.getOccurence() : getFirstDateFromDelimitedPeriods(fishingActivity.getDelimitedPeriods());
            if (currentDate != null) {
                if (result.getStartDate() == null || currentDate.before(result.getStartDate())) {
                    result.setStartDate(currentDate);
                }
                if (result.getEndDate() == null || currentDate.after(result.getEndDate())) {
                    result.setEndDate(currentDate);
                }
            }
        }
        return result;
    }

    @Override
    public Date getFirstDateFromDelimitedPeriods(Collection<DelimitedPeriodEntity> delimitedPeriods) {
        return Optional.ofNullable(delimitedPeriods)
                .flatMap(dp -> dp.stream()
                            .map(DelimitedPeriodEntity::getStartDate)
                            .filter(Objects::nonNull)
                            .sorted()
                            .findFirst()
                )
                .orElse(null);
    }

    private Stream<MovementType> getAllMovementGuidsForDateRange(Date startDate, Date endDate, List<String> assetGuidList) {
        try {
            List<MovementType> result = movementModuleService.getMovement(assetGuidList, startDate, endDate);
            if(result == null || result.isEmpty()) {
                return Stream.empty();
            }
            return result.stream();
        } catch (ServiceException e) {
            throw new RuntimeServiceException("error calling movementModuleService.getMovement", e);
        }
    }

	/**
	 * The comparator returns the date closest to the start date of the given range;
	 * in case of equality, it prefers the date before the start date.
	 */
	private Comparator<MovementType> byDateClosestTo(DateRangeDto range) {
		return Comparator.comparing(MovementType::getPositionTime, (a, b) -> {
			long time1 = Math.abs(a.getTime() - range.getStartDate().getTime());
			long time2 = Math.abs(b.getTime() - range.getStartDate().getTime());
			if (time1 == time2) {
				if (a.before(range.getStartDate()) && b.after(range.getStartDate())) {
					return -1;
				} else if (a.after(range.getStartDate()) && b.before(range.getStartDate())) {
					return 1;
				} else {
					return 0;
				}
			} else {
				return Long.compare(time1, time2);
			}
		});
	}
}
