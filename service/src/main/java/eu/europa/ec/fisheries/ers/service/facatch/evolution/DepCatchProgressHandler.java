package eu.europa.ec.fisheries.ers.service.facatch.evolution;

import eu.europa.ec.fisheries.ers.fa.entities.FaCatchEntity;
import eu.europa.ec.fisheries.ers.fa.entities.FishingActivityEntity;
import eu.europa.ec.fisheries.ers.fa.utils.FaReportDocumentType;
import eu.europa.ec.fisheries.ers.service.dto.fishingtrip.catchprogress.CatchProgressDTO;
import eu.europa.ec.fisheries.ers.service.dto.fishingtrip.catchprogress.DepCatchProgressDTO;

public class DepCatchProgressHandler extends CatchProgressHandler {
    @Override
    public CatchProgressDTO prepareCatchProgressDTO(FishingActivityEntity fishingActivity) {
        if (fishingActivity == null) {
            throw new IllegalArgumentException("Fishing activity is null");
        }

        String reportType = fishingActivity.getFaReportDocument().getTypeCode();
        DepCatchProgressDTO catchProgressDTO = new DepCatchProgressDTO(reportType, reportType.equals(FaReportDocumentType.DECLARATION.name()), fishingActivity.getCalculatedStartTime());

        fishingActivity.getFaCatchs().forEach(faCatch -> handleSimpleCatch(faCatch, catchProgressDTO));

        return catchProgressDTO;
    }
}
