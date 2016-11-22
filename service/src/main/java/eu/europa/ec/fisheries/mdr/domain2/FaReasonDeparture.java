/*
Developed by the European Commission - Directorate General for Maritime Affairs and Fisheries @ European Union, 2015-2016.

This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can redistribute it
and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of
the License, or any later version. The IFDM Suite is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
details. You should have received a copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.

 */
package eu.europa.ec.fisheries.mdr.domain2;


import eu.europa.ec.fisheries.mdr.domain.base.ExtendedMasterDataRegistry;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "mdr_fa_reason_departure")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FaReasonDeparture extends ExtendedMasterDataRegistry {
	private static final long serialVersionUID = 1L;

	@Override
	public String getAcronym() {
		return "FA_REASON_DEPARTURE";
	}

}