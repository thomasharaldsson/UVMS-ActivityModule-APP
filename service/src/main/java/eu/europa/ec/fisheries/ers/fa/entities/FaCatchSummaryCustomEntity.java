package eu.europa.ec.fisheries.ers.fa.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sanera on 26/01/2017.
 */
//@Entity
public class FaCatchSummaryCustomEntity implements Serializable {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FaCatchSummaryCustomEntity)) return false;

        FaCatchSummaryCustomEntity that = (FaCatchSummaryCustomEntity) o;

        if (getDate() != null ? !getDate().equals(that.getDate()) : that.getDate() != null) return false;
        if (getFlagState() != null ? !getFlagState().equals(that.getFlagState()) : that.getFlagState() != null)
            return false;
        if (getGearType() != null ? !getGearType().equals(that.getGearType()) : that.getGearType() != null)
            return false;
        if (getPresentation() != null ? !getPresentation().equals(that.getPresentation()) : that.getPresentation() != null)
            return false;
        if (getTerritory() != null ? !getTerritory().equals(that.getTerritory()) : that.getTerritory() != null)
            return false;
        if (getFaoArea() != null ? !getFaoArea().equals(that.getFaoArea()) : that.getFaoArea() != null) return false;
        if (getIcesStatRectangle() != null ? !getIcesStatRectangle().equals(that.getIcesStatRectangle()) : that.getIcesStatRectangle() != null)
            return false;
        if (getEffortZone() != null ? !getEffortZone().equals(that.getEffortZone()) : that.getEffortZone() != null)
            return false;
        if (getRfmo() != null ? !getRfmo().equals(that.getRfmo()) : that.getRfmo() != null) return false;
        if (getGfcmGsa() != null ? !getGfcmGsa().equals(that.getGfcmGsa()) : that.getGfcmGsa() != null) return false;
        return getGfcmStatRectangle() != null ? getGfcmStatRectangle().equals(that.getGfcmStatRectangle()) : that.getGfcmStatRectangle() == null;

    }

    @Override
    public int hashCode() {
        int result = getDate() != null ? getDate().hashCode() : 0;
        result = 31 * result + (getFlagState() != null ? getFlagState().hashCode() : 0);
        result = 31 * result + (getGearType() != null ? getGearType().hashCode() : 0);
        result = 31 * result + (getPresentation() != null ? getPresentation().hashCode() : 0);
        result = 31 * result + (getTerritory() != null ? getTerritory().hashCode() : 0);
        result = 31 * result + (getFaoArea() != null ? getFaoArea().hashCode() : 0);
        result = 31 * result + (getIcesStatRectangle() != null ? getIcesStatRectangle().hashCode() : 0);
        result = 31 * result + (getEffortZone() != null ? getEffortZone().hashCode() : 0);
        result = 31 * result + (getRfmo() != null ? getRfmo().hashCode() : 0);
        result = 31 * result + (getGfcmGsa() != null ? getGfcmGsa().hashCode() : 0);
        result = 31 * result + (getGfcmStatRectangle() != null ? getGfcmStatRectangle().hashCode() : 0);
        return result;
    }

    public FaCatchSummaryCustomEntity(){


    }


    public FaCatchSummaryCustomEntity(Date date, String flagState, String gearType, String presentation, String territory, String faoArea, String icesStatRectangle, String effortZone, String rfmo, String gfcmGsa, String gfcmStatRectangle, String fishClass, String species, long count) {
        this.date = date;
        this.flagState = flagState;
        this.gearType = gearType;
        this.presentation = presentation;
        this.territory = territory;
        this.faoArea = faoArea;
        this.icesStatRectangle = icesStatRectangle;
        this.effortZone = effortZone;
        this.rfmo = rfmo;
        this.gfcmGsa = gfcmGsa;
        this.gfcmStatRectangle = gfcmStatRectangle;
        this.fishClass = fishClass;
        this.species = species;
        this.count = count;
    }

    /* @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;*/

    private Date date;
    private String flagState;
    private String gearType;
    private String presentation;
    private String territory;
    private String faoArea;
    private String icesStatRectangle;
    private String effortZone;
    private String rfmo;
    private String gfcmGsa;
    private String gfcmStatRectangle;
    private String fishClass;
    private String species;
    private long count;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFishClass() {
        return fishClass;
    }

    public void setFishClass(String fishClass) {
        this.fishClass = fishClass;
    }

    public String getFlagState() {
        return flagState;
    }

    public void setFlagState(String flagState) {
        this.flagState = flagState;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGearType() {
        return gearType;
    }

    public void setGearType(String gearType) {
        this.gearType = gearType;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getTerritory() {
        return territory;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public String getFaoArea() {
        return faoArea;
    }

    public void setFaoArea(String faoArea) {
        this.faoArea = faoArea;
    }

    public String getIcesStatRectangle() {
        return icesStatRectangle;
    }

    public void setIcesStatRectangle(String icesStatRectangle) {
        this.icesStatRectangle = icesStatRectangle;
    }

    public String getEffortZone() {
        return effortZone;
    }

    public void setEffortZone(String effortZone) {
        this.effortZone = effortZone;
    }

    public String getRfmo() {
        return rfmo;
    }

    public void setRfmo(String rfmo) {
        this.rfmo = rfmo;
    }

    public String getGfcmGsa() {
        return gfcmGsa;
    }

    public void setGfcmGsa(String gfcmGsa) {
        this.gfcmGsa = gfcmGsa;
    }

    public String getGfcmStatRectangle() {
        return gfcmStatRectangle;
    }

    public void setGfcmStatRectangle(String gfcmStatRectangle) {
        this.gfcmStatRectangle = gfcmStatRectangle;
    }

    @Override
    public String toString() {
        return "FaCatchSummaryCustomEntity{" +
                "date=" + date +
                ", flagState='" + flagState + '\'' +
                ", gearType='" + gearType + '\'' +
                ", presentation='" + presentation + '\'' +
                ", territory='" + territory + '\'' +
                ", faoArea='" + faoArea + '\'' +
                ", icesStatRectangle='" + icesStatRectangle + '\'' +
                ", effortZone='" + effortZone + '\'' +
                ", rfmo='" + rfmo + '\'' +
                ", gfcmGsa='" + gfcmGsa + '\'' +
                ", gfcmStatRectangle='" + gfcmStatRectangle + '\'' +
                ", fishClass='" + fishClass + '\'' +
                ", species='" + species + '\'' +
                ", count=" + count +
                '}';
    }
}
