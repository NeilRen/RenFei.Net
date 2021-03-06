package net.renfei.dao.entity;

import java.math.BigDecimal;

public class IPV6DO {
    private BigDecimal ipFrom;

    private BigDecimal ipTo;

    private String countryCode;

    private String countryName;

    private String regionName;

    private String cityName;

    private Double latitude;

    private Double longitude;

    private String zipCode;

    private String timeZone;

    public BigDecimal getIpFrom() {
        return ipFrom;
    }

    public void setIpFrom(BigDecimal ipFrom) {
        this.ipFrom = ipFrom;
    }

    public BigDecimal getIpTo() {
        return ipTo;
    }

    public void setIpTo(BigDecimal ipTo) {
        this.ipTo = ipTo;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode == null ? null : countryCode.trim();
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName == null ? null : countryName.trim();
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName == null ? null : regionName.trim();
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone == null ? null : timeZone.trim();
    }
}