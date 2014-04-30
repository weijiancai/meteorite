/**
 * 
 */
package com.meteorite.taobao.api;

import com.meteorite.core.util.UString;
import com.meteorite.taobao.TbItemCat;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.ItemCat;
import com.taobao.api.domain.Product;
import com.taobao.api.domain.SellerCat;
import com.taobao.api.request.*;
import com.taobao.api.response.*;

import java.util.*;

/**
 * 淘宝商品API
 * 
 * @author wei_jc
 * @since webshop 1.00
 */
public class TbItemFacade {
//	private static final Logger log = LogFactory.getLogger(TbItemFacade.class);
	
	private TaobaoClient client;
	private String sessionKey;
	
	public TbItemFacade(TaobaoClient client, String sessionKey) {
		this.client = client;
		this.sessionKey = sessionKey;
	}
	
	/**
	 * 添加一个商品
	 * 
	 * @param item 商品信息
	 * @throws com.taobao.api.ApiException
	 * @throws com.taobao.api.ApiException
	 * @since webshop 1.00
	 */
	public ItemAddResponse addItem(Item item) throws ApiException {
//		log.debug("开始添加淘宝商品：" + item.getTitle());
		ItemAddRequest req = new ItemAddRequest();
		// 必填项
		req.setNum(item.getNum()); // 商品数量
		req.setPrice(item.getPrice());  // 商品价格
		req.setType(item.getType());  // 发布类型
		req.setStuffStatus(item.getStuffStatus());  // 新旧程度
		req.setTitle(item.getTitle());  // 宝贝标题
		req.setDesc(item.getDesc());  // 宝贝描述
//		req.setLocationState(item.getLocationState());  // 所在地省份
//		req.setLocationCity(item.getLocationCity());  // 所在地城市
		req.setCid(item.getCid());  // 叶子类目id

		// 可选项
		req.setSellerCids(item.getSellerCids());  // 商品所属的店铺类目列表
		req.setApproveStatus(item.getApproveStatus());  // 商品上传后的状态
		req.setDesc(item.getDesc());  // 宝贝描述
		req.setFreightPayer(item.getFreightPayer());  // 运费承担方式
		req.setValidThru(item.getValidThru());  // 有效期
		req.setHasInvoice(item.getHasInvoice());  // 是否有发票
		req.setHasWarranty(item.getHasWarranty());  // 是否有保修
		req.setHasShowcase(item.getHasShowcase());  // 橱窗推荐
		req.setHasDiscount(item.getHasDiscount());  // 支持会员打折
		req.setListTime(item.getListTime());  // 定时上架时间
		req.setIncrement(item.getIncrement());  // 加价幅度
//		req.setImage(item.getImage());  // 商品主图片
		req.setAuctionPoint(item.getAuctionPoint());  // 商品的积分返点比例
		req.setInputPids(item.getInputPids());  // 属性值别名
//		req.setSkuProperties(item.getSkuProperties());  // 更新的Sku的属性串
//		req.setSkuQuantities(item.getSkuQuantities());  // Sku的数量串
//		req.setSkuPrices(item.getSkuPrices());  // Sku的价格串
//		req.setSkuOuterIds(item.getSkuOuterIds());  // Sku的外部id串
//		req.setLang(item.getLang());  // 商品文字的字符集
		req.setOuterId(item.getOuterId());  // 商家编码，该字段的最大长度是512个字节
		req.setProductId(item.getProductId());  // 商品所属的产品ID(B商家发布商品需要用)
//		req.setPicPath(item.getPicPath());  // 商品主图需要关联的图片空间的相对url
		req.setAutoFill(item.getAutoFill());  // 代充商品类型
		req.setInputStr(item.getInputStr());  // 用户自行输入的子属性名和属性值
		req.setIsTaobao(item.getIsTaobao());  // 是否在淘宝上显示
		req.setIsEx(item.getIsEx());  // 是否在外店显示
		req.setIs3D(item.getIs3D());  // 是否是3D
		req.setSellPromise(item.getSellPromise());  // 是否承诺退换货服务!虚拟商品无须设置此项!
		req.setAfterSaleId(item.getAfterSaleId());  // 售后说明模板id
		req.setCodPostageId(item.getCodPostageId());  // 此为货到付款运费模板的ID
		req.setProps(item.getProps());  // 商品属性列表
		req.setPostageId(item.getPostageId());  // 宝贝所属的运费模版ID
		req.setSubStock(item.getSubStock());  // 商家是否支持拍下减库存
		req.setItemWeight(item.getItemWeight());  // 商品的重量
		req.setIsLightningConsignment(item.getIsLightningConsignment());  // 实物闪电发货

		return client.execute(req, sessionKey);
	}

	/**
	 * 上传一个产品
	 *
	 * @param prod
	 * @return
	 * @throws com.taobao.api.ApiException
	 */
	public ProductAddResponse addProduct(Product prod) throws ApiException {
//		log.debug("开始添加淘宝产品：" + prod.getName());
		ProductAddRequest req = new ProductAddRequest();

		req.setCid(prod.getCid());
		req.setName(prod.getName());
		req.setPrice(prod.getPrice());
//		req.setImage(prod.getImage());
		req.setCustomerProps(prod.getCustomerProps());

		return client.execute(req, sessionKey);
	}

	/**
	 * 获取当前会话用户所有出售中的商品列表
	 *
	 * @throws com.taobao.api.ApiException
	 */
	public List<Item> getOnsaleItems() throws ApiException {
//		log.debug("开始查询淘宝所有在销的商品");
		List<Item> result = new ArrayList<Item>();

		ItemsOnsaleGetRequest req = new ItemsOnsaleGetRequest();
		req.setFields("num_iid");
		req.setPageSize(200L);
		long pageNo = 1;

		while(true) {
			req.setPageNo(pageNo++);
			ItemsOnsaleGetResponse res = client.execute(req, sessionKey);
			if(!res.isSuccess() || res.getItems() == null) {
				break;
			}

			result.addAll(res.getItems());
		}

		return result;
	}

	/**
	 * 得到当前会话用户库存中的商品列表
	 *
	 * @param banner 分类字段。可选值:
	 *                      regular_shelved(定时上架) never_on_shelf(从未上架) off_shelf(我下架的)
	 *                      for_shelved(等待所有上架) sold_out(全部卖完) violation_off_shelf(违规下架的)
	 *                      默认查询的是for_shelved(等待所有上架)这个状态的商品
	 * @return
	 * @throws com.taobao.api.ApiException
	 */
	public List<Item> getInventoryItems(String banner) throws ApiException {
//		log.debug("开始查询淘宝当前会话用户库存中的商品列表, banner = " + banner);
		List<Item> result = new ArrayList<Item>();

		ItemsInventoryGetRequest req = new ItemsInventoryGetRequest();
		req.setFields("num_iid");
		req.setBanner(UString.isEmpty(banner) ? "for_shelved" : banner);
		req.setPageSize(200L);
		long pageNo = 1;

		while(true) {
			req.setPageNo(pageNo++);
			ItemsInventoryGetResponse res = client.execute(req, sessionKey);
			if(!res.isSuccess() || res.getItems() == null) {
				break;
			}

			result.addAll(res.getItems());
		}

		return result;
	}

	/**
	 * 获取所有的商品列表Ids，包括出售中的商品，全部卖完的商品，我下架的商品，违规下架的商品
	 *
	 * @return
	 * @throws com.taobao.api.ApiException
	 */
	public Set<Long> getAllListItemIds() throws ApiException {
		Set<Long> set = new HashSet<Long>();
		// 取出售中的商品
		List<Item> onSaleList = getOnsaleItems();
		for(Item item : onSaleList) {
			set.add(item.getNumIid());
		}
		// 取全部卖完的商品
		List<Item> soldOutList = getInventoryItems("sold_out");
		for(Item item : soldOutList) {
			set.add(item.getNumIid());
		}
		// 等待所有上架
		List<Item> offShelfList = getInventoryItems("for_shelved");
		for(Item item : offShelfList) {
			set.add(item.getNumIid());
		}
		// 取违规下架的的商品
		List<Item> violationOffShelfList = getInventoryItems("violation_off_shelf");
		for(Item item : violationOffShelfList) {
			set.add(item.getNumIid());
		}

		return set;
	}

	/**
	 * 获取的商品列表
	 *
	 * @param set numIid集合
	 * @return
	 * @throws com.taobao.api.ApiException
	 */
	public List<Item> getListItems(Set<Long> set) throws ApiException {
		List<Item> result = new ArrayList<Item>();

		StringBuilder sb = new StringBuilder();
		int i = 1;
		for(Iterator<Long> iterator = set.iterator(); iterator.hasNext();) {
			Long id = iterator.next();
			sb.append(id).append(",");
			if(i++ % 20 == 0) {
				if(sb.toString().endsWith(",")) {
					sb.deleteCharAt(sb.length() - 1);
				}

				result.addAll(getListItems(sb.toString()));
				sb = new StringBuilder();
			}
		}
		// 取剩余的
		if(sb.toString().endsWith(",")) {
			sb.deleteCharAt(sb.length() - 1);
		}
		result.addAll(getListItems(sb.toString()));

		return result;
	}

	/**
	 * 批量获取商品信息
	 *
	 * @param numIids 商品数字id列表，多个num_iid用逗号隔开，一次不超过20个。
	 * @return
	 * @throws com.taobao.api.ApiException
	 */
	public List<Item> getListItems(String numIids) throws ApiException {
		List<Item> result = new ArrayList<Item>();
		if(UString.isEmpty(numIids)) {
			return result;
		}
		ItemsListGetRequest req = new ItemsListGetRequest();
		String fields = "approve_status,num_iid,cid,title,pic_url,num,props,list_time,price,outer_id,seller_cids,input_pids,input_str,desc,product_id";
		req.setFields(fields);
		req.setNumIids(numIids);
		ItemsListGetResponse res = client.execute(req, sessionKey);
		if(res.isSuccess()) {
			result.addAll(res.getItems());
		}

		return result;
	}

	/**
	 * 获取一个产品的信息 ,两种方式: 传入product_id来查询或 传入cid和props来查询
	 *
	 * @param productId
	 * @param cid
	 * @param props
	 * @return
	 * @throws com.taobao.api.ApiException
	 */
	public Product getProduct(Long productId, Long cid, String props) throws ApiException {
		ProductGetRequest req = new ProductGetRequest();
		req.setFields("product_id,price,props_str,binds_str");
		if(productId != null) {
			req.setProductId(productId);
		} else {
			req.setCid(cid);
			req.setProps(props);
		}
		ProductGetResponse res = client.execute(req, sessionKey);
		if(res.isSuccess()) {
			return res.getProduct();
		}

		return null;
	}

	/**
	 * 更新商品信息（库存及一口价）
	 *
	 * @param info
	 * @return
	 * @throws com.taobao.api.ApiException
	 */
	public ItemUpdateResponse updateItem(Item info) throws ApiException {
		ItemUpdateRequest req = new ItemUpdateRequest();

		req.setNumIid(info.getNumIid());
		req.setNum(info.getNum());
		req.setPrice(info.getPrice());

		return client.execute(req, sessionKey);
	}

	/**
	 * 一口价商品上架
	 *
	 * @param info
	 * @return
	 * @throws com.taobao.api.ApiException
	 */
	public ItemUpdateListingResponse listingUpdateItem(Item info) throws ApiException {
		ItemUpdateListingRequest req = new ItemUpdateListingRequest();

		req.setNumIid(info.getNumIid());
		req.setNum(info.getNum());

		return client.execute(req, sessionKey);
	}

	/**
	 * 单个商品下架
	 *
	 * @param numIid
	 * @return
	 * @throws com.taobao.api.ApiException
	 */
	public ItemUpdateDelistingResponse delistingUpdateItem(Long numIid) throws ApiException {
		ItemUpdateDelistingRequest req = new ItemUpdateDelistingRequest();
		req.setNumIid(numIid);
		return client.execute(req, sessionKey);
	}

	/**
	 * 删除淘宝上的商品
	 *
	 * @param numIid
	 * @return
	 * @throws com.taobao.api.ApiException
	 */
	public ItemDeleteResponse deleteItem(Long numIid) throws ApiException {
		ItemDeleteRequest req = new ItemDeleteRequest();
		req.setNumIid(numIid);
		return client.execute(req, sessionKey);
	}

	/**
	 * 递归调用返回此类目的所有分类
	 *
	 * @return
	 * @throws com.taobao.api.ApiException
	 */
	public void getItemCats(TbItemCat parent) throws ApiException {
		ItemcatsGetRequest req = new ItemcatsGetRequest();
		req.setFields("cid, parent_cid, name, is_parent, status, sort_order");
		req.setParentCid(parent.getCid());
		ItemcatsGetResponse res = client.execute(req, sessionKey);
		if (res.isSuccess()) {
			if(res.getItemCats() != null && res.getItemCats().size() > 0) {
				for(ItemCat itemCat : res.getItemCats()) {
//					DefaultCategory cat = new DefaultCategory(itemCat.getCid(), itemCat.getParentCid(), itemCat.getName());
                    TbItemCat cat = new TbItemCat();
                    cat.setCid(itemCat.getCid());
                    cat.setParentCid(itemCat.getParentCid());
                    cat.setName(itemCat.getName());
                    cat.setParent(parent);
                    cat.setIsParent(itemCat.getIsParent());
                    cat.setStatus(itemCat.getStatus());
                    cat.setSortOrder(itemCat.getSortOrder());
					parent.addChild(cat);
					if(itemCat.getIsParent()) {
						getItemCats(cat);
					}
				}
			}
		}
	}

	/**
	 * 获得卖家的自定义商品分类
	 *
	 * @param venderName
	 * @return
	 * @throws com.taobao.api.ApiException
	 */
	public List<ItemCat> getSellerCats(String venderName) throws ApiException {
		List<ItemCat> result = new ArrayList<ItemCat>();

		SellercatsListGetRequest req = new SellercatsListGetRequest();
		req.setNick(venderName);
		SellercatsListGetResponse res = client.execute(req, sessionKey);
		if(res.isSuccess()) {
			if(res.getSellerCats() != null && res.getSellerCats().size() > 0) {
				for(SellerCat sellerCat : res.getSellerCats()) {
					/*DefaultCategory cat;
					if(sellerCat.getParentCid() == 0) {
						cat = new DefaultCategory(sellerCat.getCid() + "", "root", sellerCat.getName());
					} else {
						cat = new DefaultCategory(sellerCat.getCid(), sellerCat.getParentCid(), sellerCat.getName());
					}
					result.add(cat);*/
				}
			}
		}

		return result;
	}

	/**
	 *
	 * @param numIid
	 * @param images
	 * @throws com.taobao.api.ApiException
	 */
	public void uploadImg(Long numIid, byte[] images, boolean isMajor) throws ApiException {
		ItemImgUploadRequest req = new ItemImgUploadRequest();
		req.setNumIid(numIid);
		req.setIsMajor(isMajor);
//		String fileName = "封面" + "." + UtilImage.getImageType(images);
//		req.setImage(new FileItem(fileName, images));

		ItemImgUploadResponse res = client.execute(req, sessionKey);
		if(!res.isSuccess()) {
//			log.error(String.format("上传淘宝图片失败【%s】\n, %s", numIid + "", ErrorMsgFactory.getTbErrorMsg(res)));
		}
	}

    /**
     * 获取单个商品的详细信息 卖家未登录时只能获得这个商品的公开数据，卖家登录后可以获取商品的所有数据
     *
     * @param numIid
     * @return
     * @throws com.taobao.api.ApiException
     */
    public ItemGetResponse getItem(Long numIid) throws ApiException {
        ItemGetRequest req = new ItemGetRequest();
        req.setNumIid(numIid);
        req.setFields("detail_url,num_iid,title,nick,type,cid,seller_cids,props,input_pids,input_str,desc,pic_url,num,valid_thru,list_time,delist_time,stuff_status,location,price,post_fee,express_fee,ems_fee,has_discount,freight_payer,has_invoice,has_warranty,has_showcase,modified,increment,approve_status,postage_id,product_id,auction_point,property_alias,item_img,prop_img,sku,video,outer_id,is_virtual");

        return client.execute(req, sessionKey);
    }

    public ProductGetResponse getProduct(Long productId) throws ApiException {
        ProductGetRequest req=new ProductGetRequest();
        req.setFields("product_id,cid,cat_name,props,props_str,name,binds,binds_str,sale_props,sale_props_str,price,desc,pic_url,created,modified,product_img.id,product_img.url,product_img.position,product_prop_img.id,product_prop_img.props,product_prop_img.url,product_prop_img.position");
        req.setProductId(productId);

        return client.execute(req);
    }

    public ProductsSearchResponse searchProduct(String isbn) throws ApiException {
        ProductsSearchRequest req = new ProductsSearchRequest();
        req.setFields("product_id,cid,price,props,name,pic_url,status,cspu_feature");
        req.setMarketId("2");
        req.setCustomerProps("1636953:" + isbn);

        return client.execute(req, sessionKey);
    }
}
