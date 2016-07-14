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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "activity_fa_report_document")
public class FaReportDocumentEntity implements Serializable {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "vessel_transport_means_id", nullable = false)
	private VesselTransportMeansEntity vesselTransportMeans;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "flux_report_document_id", nullable = false)
	private FluxReportDocumentEntity fluxReportDocument;

	@Column(name = "type_code", nullable = false)
	private String typeCode;

	@Column(name = "type_code_list_id", nullable = false)
	private String typeCodeListId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "accepted_datetime", length = 29)
	private Date acceptedDatetime;

	@Column(name = "fmc_marker")
	private String fmcMarker;

	@Column(name = "fmc_marker_list_id")
	private String fmcMarkerListId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "faReportDocument", cascade = CascadeType.ALL)
	private Set<FaReportIdentifierEntity> faReportIdentifiers = new HashSet<FaReportIdentifierEntity>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "faReportDocument", cascade = CascadeType.ALL)
	private Set<FishingActivityEntity> fishingActivities = new HashSet<FishingActivityEntity>(0);

	public FaReportDocumentEntity() {
	}

	public int getId() {
		return this.id;
	}

	public VesselTransportMeansEntity getVesselTransportMeans() {
		return this.vesselTransportMeans;
	}

	public void setVesselTransportMeans(
			VesselTransportMeansEntity vesselTransportMeans) {
		this.vesselTransportMeans = vesselTransportMeans;
	}

	public FluxReportDocumentEntity getFluxReportDocument() {
		return this.fluxReportDocument;
	}

	public void setFluxReportDocument(
			FluxReportDocumentEntity fluxReportDocument) {
		this.fluxReportDocument = fluxReportDocument;
	}

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeCodeListId() {
		return this.typeCodeListId;
	}

	public void setTypeCodeListId(String typeCodeListId) {
		this.typeCodeListId = typeCodeListId;
	}

	public Date getAcceptedDatetime() {
		return this.acceptedDatetime;
	}

	public void setAcceptedDatetime(Date acceptedDatetime) {
		this.acceptedDatetime = acceptedDatetime;
	}

	public String getFmcMarker() {
		return this.fmcMarker;
	}

	public void setFmcMarker(String fmcMarker) {
		this.fmcMarker = fmcMarker;
	}

	public String getFmcMarkerListId() {
		return this.fmcMarkerListId;
	}

	public void setFmcMarkerListId(String fmcMarkerListId) {
		this.fmcMarkerListId = fmcMarkerListId;
	}

	public Set<FaReportIdentifierEntity> getFaReportIdentifiers() {
		return this.faReportIdentifiers;
	}

	public void setFaReportIdentifiers(
			Set<FaReportIdentifierEntity> faReportIdentifiers) {
		this.faReportIdentifiers = faReportIdentifiers;
	}

	public Set<FishingActivityEntity> getFishingActivities() {
		return this.fishingActivities;
	}

	public void setFishingActivities(
			Set<FishingActivityEntity> fishingActivities) {
		this.fishingActivities = fishingActivities;
	}

}