package com.meteorite.core.parser.capub;

import java.util.Date;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class ProductUploadInfo {
    private String cipId;
    private String cipDetailId;
    private String hMytm;
    private String hIsbn;
    private String hName;
    private String pubName;
    private String pubLocation;
    private Date thisPubTime;
    private Date inputDate;
    private String hWriter;
    private String thisEdition;
    private String printTimes;
    private double hPrice;
    private String language;
    private String kbSize;
    private String zzId;
    private int pageCount;
    private int printAmount;
    private String isbnClass;
    private String keywords;
    private String content;
    private String seriesName;

    public String getCipId() {
        return cipId;
    }

    public void setCipId(String cipId) {
        this.cipId = cipId;
    }

    public void setCipDetailId(String cipDetailId) {
        this.cipDetailId = cipDetailId;
    }

    public String getCipDetailId() {
        return cipDetailId;
    }

    public void sethMytm(String hMytm) {
        this.hMytm = hMytm;
    }

    public String gethMytm() {
        return hMytm;
    }

    public void sethIsbn(String hIsbn) {
        this.hIsbn = hIsbn;
    }

    public String gethIsbn() {
        return hIsbn;
    }

    public void sethName(String hName) {
        this.hName = hName;
    }

    public String gethName() {
        return hName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubLocation(String pubLocation) {
        this.pubLocation = pubLocation;
    }

    public String getPubLocation() {
        return pubLocation;
    }

    public void setThisPubTime(Date thisPubTime) {
        this.thisPubTime = thisPubTime;
    }

    public Date getThisPubTime() {
        return thisPubTime;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void sethWriter(String hWriter) {
        this.hWriter = hWriter;
    }

    public String gethWriter() {
        return hWriter;
    }

    public void setThisEdition(String thisEdition) {
        this.thisEdition = thisEdition;
    }

    public String getThisEdition() {
        return thisEdition;
    }

    public void setPrintTimes(String printTimes) {
        this.printTimes = printTimes;
    }

    public String getPrintTimes() {
        return printTimes;
    }

    public void sethPrice(double hPrice) {
        this.hPrice = hPrice;
    }

    public double gethPrice() {
        return hPrice;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setKbSize(String kbSize) {
        this.kbSize = kbSize;
    }

    public String getKbSize() {
        return kbSize;
    }

    public void setZzId(String zzId) {
        this.zzId = zzId;
    }

    public String getZzId() {
        return zzId;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPrintAmount(int printAmount) {
        this.printAmount = printAmount;
    }

    public int getPrintAmount() {
        return printAmount;
    }

    public void setIsbnClass(String isbnClass) {
        this.isbnClass = isbnClass;
    }

    public String getIsbnClass() {
        return isbnClass;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getSeriesName() {
        return seriesName;
    }
}
