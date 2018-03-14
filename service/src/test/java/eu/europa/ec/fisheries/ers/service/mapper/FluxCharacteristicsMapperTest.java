/*
 *
 * Developed by the European Commission - Directorate General for Maritime Affairs and Fisheries © European Union, 2015-2016.
 *
 * This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or any later version. The IFDM Suite is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package eu.europa.ec.fisheries.ers.service.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import eu.europa.ec.fisheries.ers.fa.entities.FluxCharacteristicEntity;
import eu.europa.ec.fisheries.ers.service.util.MapperUtil;
import org.junit.Test;
import un.unece.uncefact.data.standard.reusableaggregatebusinessinformationentity._20.FLUXCharacteristic;

public class FluxCharacteristicsMapperTest {

    @Test
    public void testFluxCharacteristicsMapper() {
        FLUXCharacteristic fluxCharacteristic = MapperUtil.getFluxCharacteristics();
        FluxCharacteristicEntity fluxCharacteristicEntity = FluxCharacteristicsMapper.INSTANCE.mapToFluxCharEntity(fluxCharacteristic);
        assertEquals(fluxCharacteristic.getTypeCode().getValue(), fluxCharacteristicEntity.getTypeCode());
        assertEquals(fluxCharacteristic.getTypeCode().getListID(), fluxCharacteristicEntity.getTypeCodeListId());
        assertEquals(fluxCharacteristic.getValueMeasure().getValue().intValue(), fluxCharacteristicEntity.getValueMeasure().intValue());
        assertEquals(fluxCharacteristic.getValueMeasure().getUnitCode(), fluxCharacteristicEntity.getValueMeasureUnitCode());
        assertEquals(fluxCharacteristic.getValueMeasure().getValue().intValue(), fluxCharacteristicEntity.getCalculatedValueMeasure().intValue());
        assertEquals(fluxCharacteristic.getValueDateTime().getDateTime().toGregorianCalendar().getTime(), fluxCharacteristicEntity.getValueDateTime());
        assertEquals(fluxCharacteristic.getValueIndicator().getIndicatorString().getValue(), fluxCharacteristicEntity.getValueIndicator());
        assertEquals(fluxCharacteristic.getValueCode().getValue(), fluxCharacteristicEntity.getValueCode());
        assertTrue(fluxCharacteristicEntity.getValueText().startsWith(fluxCharacteristic.getValues().get(0).getValue()));
        assertEquals(fluxCharacteristic.getValueQuantity().getValue().intValue(), fluxCharacteristicEntity.getValueQuantity().intValue());
        assertEquals(fluxCharacteristic.getValueQuantity().getUnitCode(), fluxCharacteristicEntity.getValueQuantityCode());
        assertEquals(fluxCharacteristic.getValueQuantity().getValue().intValue(), fluxCharacteristicEntity.getCalculatedValueQuantity().intValue());
        assertTrue(fluxCharacteristicEntity.getDescription().startsWith(fluxCharacteristic.getDescriptions().get(0).getValue()));
    }

}
