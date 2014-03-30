package com.meteorite.core.parser.book;

import java.util.Date;
import java.util.List;


/**
 * 网上电子书商产品信息
 *
 * @author weijiancai
 * @since 0.0.1
 */
public interface IWebProduct {
    /**
     * 获取来源站点
     *
     * @return 返回来源站点
     */
    String getSourceSite();
  
    /**
     * 获取商品的图片信息
     *
     * @return 返回商品的图片信息列表
     */
    List<ProductPic> getProductPic();
    
    /**
     * 获取商品图片ID
     * 
     * @return 返回商品图片ID
     */
    String getPictureId();

    /**
     * 获取商品名称
     *
     * @return 返回商品名称
     */
    String getName();

    /**
     * 获取商品中文名称
     *
     * @return 返回商品中文名称
     */
    String getCnName();

    /**
     * 获取商品概述
     *
     * @return 返回商品概述信息
     */
    String getHAbstract();

    /**
     * 获取商品内容信息
     *
     * @return 返回商品内容信息
     */
    String getContent();

    /**
     * 获取作者简介
     *
     * @return 返回作者简介信息
     */
    String getAuthorIntro();

    /**
     * 获取目录
     *
     * @return 返回商品目录信息
     */
    String getCatalog();

    /**
     * 获取前言
     *
     * @return 返回前言信息
     */
    String getPrologue();

    /**
     * 获取书摘
     *
     * @return 返回书摘信息
     */
    String getExtract();

    /**
     * 获取定价
     *
     * @return 返回商品定价
     */
    String getPrice();

    /**
     * 获取作者
     *
     * @return 返回作者信息
     */
    String getAuthor();

    /**
     * 获取译者
     *
     * @return 返回译者
     */
    String getTranslator();

    /**
     * 获取绘者
     *
     * @return 返回绘者
     */
    String getPainter();

    /**
     * 获取出版社
     *
     * @return 返回出版社信息
     */
    String getPublishing();

    /**
     * 获取ISBN
     *
     * @return 返回ISBN信息
     */
    String getIsbn();

    /**
     * 获取出版时间
     *
     * @return 返回出版时间信息
     */
    String getPublishDate();

    /**
     * 获取版次
     *
     * @return 返回版次信息
     */
    String getBanci();

    /**
     * 获取页数
     *
     * @return 返回页数
     */
    String getPageNum();

    /**
     * 获取装帧
     *
     * @return 返回装帧
     */
    String getPack();

    /**
     * 获取尺寸
     *
     * @return 返回尺寸
     */
    String getSize();

    /**
     * 获取重量
     *
     * @return 返回重量
     */
    String getWeight();

    /**
     * 获取纸张
     *
     * @return 返回纸张
     */
    String getPaper();

    /**
     * 获取语种
     *
     * @return 返回语种
     */
    String getLanguage();

    /**
     * 获取开本
     *
     * @return 返回开本
     */
    String getKaiben();

    /**
     * 获取印刷时间
     *
     * @return 返回印刷时间
     */
    String getPrintDate();

    /**
     * 获取印次
     *
     * @return 返回印次
     */
    String getPrintNum();

    /**
     * 获取套装数量
     *
     * @return 返回套装数量
     */
    String getSuitNum();

    /**
     * 获取读者对象
     *
     * @return 返回读者对象
     */
    String getReaders();

    /**
     * 获取丛书名
     *
     * @return 返回丛书名
     */
    String getSeriesName();

    /**
     * 获取媒体评论
     *
     * @return 返回媒体评论
     */
    String getMediaFeedback();

    /**
     * 获取字数
     *
     * @return 返回字数
     */
    String getWordCount();
    
    /**
     * 获取用户ID
     * 
     * @return 返回用户ID
     */
    String getUserId();
    
    /**
     * 获取长度
     * 
     * @return 返回长度
     */
    String getLength();
    
    /**
     * 获取宽度
     * 
     * @return 返回宽度
     */
    String getWidth();
    
    /**
     * 获取厚度
     * 
     * @return 返回厚度
     */
    String getDeep();
    
    /**
     * 获得创建时间
     * 
     * @return
     */
    Date getInputDate();
}
