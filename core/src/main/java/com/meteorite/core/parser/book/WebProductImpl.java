package com.meteorite.core.parser.book;

import java.util.Date;
import java.util.List;


/**
 * @author weijiancai
 * @since 0.0.1
 */
public class WebProductImpl implements IWebProduct {
    private String name;
    private List<ProductPic> productPic;
    private String hAbstract;
    private String content;
    private String authorIntro;
    private String catalog;
    private String prologue;
    private String extract;
    private String price;
    private String author;
    private String publishing;
    private String isbn;
    private String publishDate;
    private String banci;
    private String pageNum;
    private String pack;
    private String size;
    private String weight;
    private String paper;
    private String language;
    private String kaiben;
    private String printDate;
    private String printNum;
    private String suitNum;
    private String readers;
    private String sourceSite;
    private String seriesName;
    private String translator;
    private String painter;
    private String cnName;
    private String mediaFeedback;
    private String wordCount;
    private String pictureId;
    private String userId;
    private String length;
    private String width;
    private String deep;
    private Date inputDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductPic> getProductPic() {
        return productPic;
    }

    public void setProductPic(List<ProductPic> productPic) {
        this.productPic = productPic;
    }

    public String getHAbstract() {
        return hAbstract;
    }

    public void setHAbstract(String hAbstract) {
        this.hAbstract = hAbstract;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorIntro() {
        return authorIntro;
    }

    public void setAuthorIntro(String authorIntro) {
        this.authorIntro = authorIntro;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPrologue() {
        return prologue;
    }

    public void setPrologue(String prologue) {
        this.prologue = prologue;
    }

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }

    public String getPrice() {
        return price;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getBanci() {
        return banci;
    }

    public void setBanci(String banci) {
        this.banci = banci;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPaper() {
        return paper;
    }

    public void setPaper(String paper) {
        this.paper = paper;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getKaiben() {
        return kaiben;
    }

    public void setKaiben(String kaiben) {
        this.kaiben = kaiben;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getPrintNum() {
        return printNum;
    }

    public void setPrintNum(String printNum) {
        this.printNum = printNum;
    }

    public String getSuitNum() {
        return suitNum;
    }

    public void setSuitNum(String suitNum) {
        this.suitNum = suitNum;
    }

    public String getReaders() {
        return readers;
    }

    public void setReaders(String readers) {
        this.readers = readers;
    }

    public String getSourceSite() {
        return sourceSite;
    }

    public void setSourceSite(String sourceSite) {
        this.sourceSite = sourceSite;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getPainter() {
        return painter;
    }

    public void setPainter(String painter) {
        this.painter = painter;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getMediaFeedback() {
        return mediaFeedback;
    }

    public void setMediaFeedback(String mediaFeedback) {
        this.mediaFeedback = mediaFeedback;
    }

    public String getWordCount() {
        return wordCount;
    }

    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }

    public String getPictureId() {
		return pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getDeep() {
		return deep;
	}

	public void setDeep(String deep) {
		this.deep = deep;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	@Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("WebProductImpl").append("{\n");
        sb.append("name = '").append(name).append('\'').append('\n');
        
        sb.append("hAbstract = '").append(hAbstract).append('\'').append('\n');
        sb.append("content = '").append(content).append('\'').append('\n');
        sb.append("authorIntro = '").append(authorIntro).append('\'').append('\n');
        sb.append("catalog = '").append(catalog).append('\'').append('\n');
        sb.append("prologue = '").append(prologue).append('\'').append('\n');
        sb.append("extract = '").append(extract).append('\'').append('\n');
        sb.append("price = '").append(price).append('\'').append('\n');
        sb.append("author = '").append(author).append('\'').append('\n');
        sb.append("publishing = '").append(publishing).append('\'').append('\n');
        sb.append("isbn = '").append(isbn).append('\'').append('\n');
        sb.append("publishDate = '").append(publishDate).append('\'').append('\n');
        sb.append("banci = '").append(banci).append('\'').append('\n');
        sb.append("pageNum = '").append(pageNum).append('\'').append('\n');
        sb.append("pack = '").append(pack).append('\'').append('\n');
        sb.append("size = '").append(size).append('\'').append('\n');
        sb.append("weight = '").append(weight).append('\'').append('\n');
        sb.append("paper = '").append(paper).append('\'').append('\n');
        sb.append("language = '").append(language).append('\'').append('\n');
        sb.append("kaiben = '").append(kaiben).append('\'').append('\n');
        sb.append("printDate = '").append(printDate).append('\'').append('\n');
        sb.append("printNum = '").append(printNum).append('\'').append('\n');
        sb.append("suitNum = '").append(suitNum).append('\'').append('\n');
        sb.append("readers = '").append(readers).append('\'').append('\n');
        sb.append("sourceSite = '").append(sourceSite).append('\'').append('\n');
        sb.append("seriesName = '").append(seriesName).append('\'').append('\n');
        sb.append("translator = '").append(translator).append('\'').append('\n');
        sb.append("painter = '").append(painter).append('\'').append('\n');
        sb.append("cnName = '").append(cnName).append('\'').append('\n');
        sb.append("mediaFeedback = '").append(mediaFeedback).append('\'').append('\n');
        sb.append("wordCount = '").append(wordCount).append('\'').append('\n');
        sb.append('}');
        return sb.toString();
    }
}
