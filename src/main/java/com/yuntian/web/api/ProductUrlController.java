package com.yuntian.web.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baidu.ueditor.ConfigManager;
import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.upload.BinaryUploader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuntian.domain.Product;
import com.yuntian.service.ProductRepository;
import com.yuntian.util.FarmException;
import com.yuntian.web.CommonController;

/**
 * 保存产品图片
 * @author jiahh 2016年8月23日
 *
 */
@Controller
@RequestMapping(value="/api/producturl",method={RequestMethod.POST,RequestMethod.GET})
public class ProductUrlController extends CommonController {
    @Autowired
    private ProductRepository productRepository;
    @RequestMapping(value="/saveurl",method={RequestMethod.GET,RequestMethod.POST})
    public void saveurl(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Long id = Long.parseLong(request.getParameter("id"));
    	if(id == null){
    		throw new FarmException("没有指定产品");
    	}
    	Product product = null;
    	product = productRepository.findOne(id);
    	

		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String contextPath = request.getContextPath();
		ConfigManager configManager = ConfigManager.getInstance( rootPath, contextPath, request.getRequestURI() );
		
    	Map<String, Object> conf = configManager.getConfig(ActionMap.UPLOAD_IMAGE);

		BaseState state = (BaseState)BinaryUploader.save(request, conf);
		
    	if(state.isSuccess()){
        	product.setUrl(state.getInfoMap().get("url"));
        	product = productRepository.save(product);
    	}
    	

    }
    @RequestMapping(value="/upload",method={RequestMethod.GET,RequestMethod.POST})
    public void upload(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Long id = Long.parseLong(request.getParameter("id"));
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		String contextPath = request.getContextPath();
		ConfigManager configManager = ConfigManager.getInstance( rootPath, contextPath, request.getRequestURI() );
		
    	Map<String, Object> conf = configManager.getConfig(ActionMap.UPLOAD_IMAGE);
    	conf.put("savePath", "/upload/product/"+id);
    	
		BaseState state = (BaseState)BinaryUploader.save(request, conf);
		
		ObjectMapper mapper = new ObjectMapper();
		String stateStr = mapper.writeValueAsString(state);
		// 返回前端
		response.getWriter().write(stateStr);

    }


}