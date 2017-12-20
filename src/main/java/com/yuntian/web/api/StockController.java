package com.yuntian.web.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yuntian.domain.Product;
import com.yuntian.domain.Stock;
import com.yuntian.domain.StockPK;
import com.yuntian.service.FarmRepository;
import com.yuntian.service.ProductRepository;
import com.yuntian.service.StockRepository;
import com.yuntian.web.CommonController;

@RestController
@RequestMapping(value="/api/stock",method={RequestMethod.POST,RequestMethod.GET})
public class StockController extends CommonController {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private FarmRepository farmRepository;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/save")
    public Stock save(@RequestBody Stock stock) {
    	stock = stockRepository.save(stock);
        return stock;
    }
    @RequestMapping("/savelist")
    public String save(@RequestBody List<Stock> stocks) {
    	for(Stock stock : stocks){
        	stock = stockRepository.save(stock);
    	}
        return "Success";
    }
    @RequestMapping("/get")
    public Stock get(Long userid,Long productid) {
    	
    	Stock stock = stockRepository.findOne(new StockPK(userid,productid));
        return stock;
    }
    @RequestMapping("/getbyuserid")
    public List<Stock> getbyuserid(Long uid) {
//    	SysUser user = new SysUser();
//    	user.setUid(uid);
    	List<Stock> stocks = stockRepository.findByUser_uid(uid);

        return stocks;
    }
    @RequestMapping("/getsgroup")
    public List<Stock> getbyuseridgroup(Long uid) {
    	
    	List<Object[]> stocks = stockRepository.groupByUser_uid(uid);
    	List<Stock> list = new ArrayList<Stock>();
    	
    	// 翻译
    	List<Product> products = (List<Product>)productRepository.findAll();
    	for(Object[] array :stocks){
    		Stock stock = new Stock();
    		stock.setProductid(Long.parseLong(array[0].toString()));
    		stock.setStock(Long.parseLong(array[1].toString()));
    		list.add(stock);
    		for(Product product : products){
    			if(stock.getProductid() == product.getId()){
    				stock.setName(product.getName());
    				stock.setType(product.getType());
    				stock.setProduct(product);
    				break;
    			}
    		}
    	}
    		
        return list;
    }
    @RequestMapping("/list")
    public List<Stock> getall() {
    	List<Stock> stocks = (List<Stock>)stockRepository.findAll();
        return stocks;
    }
    

}