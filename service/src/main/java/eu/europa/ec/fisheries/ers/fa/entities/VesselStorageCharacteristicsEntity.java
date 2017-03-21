/*
Developed by the European Commission - Directorate General for Maritime Affairs and Fisheries @ European Union, 2015-2016.

This file is part of the Integrated Fisheries Data Management (IFDM) Suite. The IFDM Suite is free software: you can redistribute it 
and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of 
the License, or any later version. The IFDM Suite is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more 
details. You should have received a copy of the GNU General Public License along with the IFDM Suite. If not, see <http://www.gnu.org/licenses/>.

 */
package eu.europa.ec.fisheries.ers.fa.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "activity_vessel_storage_characteristics")
public class VesselStorageCharacteristicsEntity implements Serializable {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@SequenceGenerator(name="SEQ_GEN", sequenceName="str_char_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	private int id;

	@Column(name = "vessel_id")
	private String vesselId;

	@Column(name = "vessel_scheme_id")
	private String vesselSchemaId;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "destVesselCharId")
	private FishingActivityEntity fishingActivitiesForDestVesselCharId;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "sourceVesselCharId")
	private FishingActivityEntity fishingActivitiesForSourceVesselCharId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "vesselStorageCharacteristics", cascade = CascadeType.ALL)
	private Set<VesselStorageCharCodeEntity> vesselStorageCharCode;

	public VesselStorageCharacteristicsEntity() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public String getVesselId() {
		return this.vesselId;
	}

	public void setVesselId(String vesselId) {
		this.vesselId = vesselId;
	}

	public String getVesselSchemaId() {
		return this.vesselSchemaId;
	}

	public void setVesselSchemaId(String vesselSchemaId) {
		this.vesselSchemaId = vesselSchemaId;
	}

	public FishingActivityEntity getFishingActivitiesForDestVesselCharId() {
		return fishingActivitiesForDestVesselCharId;
	}

	public void setFishingActivitiesForDestVesselCharId(FishingActivityEntity fishingActivitiesForDestVesselCharId) {
		this.fishingActivitiesForDestVesselCharId = fishingActivitiesForDestVesselCharId;
	}

	public FishingActivityEntity getFishingActivitiesForSourceVesselCharId() {
		return fishingActivitiesForSourceVesselCharId;
	}

	public void setFishingActivitiesForSourceVesselCharId(FishingActivityEntity fishingActivitiesForSourceVesselCharId) {
		this.fishingActivitiesForSourceVesselCharId = fishingActivitiesForSourceVesselCharId;
	}

	public Set<VesselStorageCharCodeEntity> getVesselStorageCharCode() {
		return vesselStorageCharCode;
	}

	public void setVesselStorageCharCode(Set<VesselStorageCharCodeEntity> vesselStorageCharCode) {
		this.vesselStorageCharCode = vesselStorageCharCode;
	}
}