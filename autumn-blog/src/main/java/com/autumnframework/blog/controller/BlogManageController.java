package com.autumnframework.blog.controller;

import com.autumnframework.blog.dao.mongo.BlogRepository;
import com.autumnframework.blog.model.document.BlogDetail;
import com.autumnframework.blog.service.impl.BlogManageImpl;
import com.autumnframework.common.architect.constant.ResponseCode;
import com.autumnframework.common.architect.utils.ResponseMsgUtil;
import com.autumnframework.common.model.bo.DataPageResponseMsg;
import com.autumnframework.common.model.bo.ResponseMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Junlan Shuai[shuaijunlan@gmail.com].
 * @date Created on 15:22 2017/11/15.
 */
@Controller
@RequestMapping(value = "blog/")
public class BlogManageController {
    private static final Logger logger = LoggerFactory.getLogger(BlogManageController.class);

    private BlogRepository blogRepository;
    private BlogManageImpl blogManage;

    public BlogManageController(BlogRepository blogRepository, BlogManageImpl blogManage){
        this.blogRepository = blogRepository;
        this.blogManage = blogManage;
    }

    @RequestMapping(value = "getBlogById/{id}")
    @ResponseBody
    public BlogDetail getBlogById(@PathVariable("id") String id){

        Optional<BlogDetail> blog =  blogRepository.findById(id);
        if (!blog.isPresent()){
            return null;
        }
        return blog.get();
    }

    @RequestMapping(value = "insertBlog/")
    @ResponseBody
    public ResponseMsg insertBlog(@RequestBody BlogDetail blogDetail){
        return blogManage.shareBlog(blogDetail);
    }

    @RequestMapping(value = "shareBlog/")
    @ResponseBody
    public ResponseMsg shareBlog(BlogDetail blogDetail){

        return blogManage.shareBlog(blogDetail);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public DataPageResponseMsg getArticleList(){
        //按访问次数降序查询
        Sort sort = new Sort(Sort.Direction.DESC, "visit_times");
        List<BlogDetail> list = blogRepository.findAll(sort);
        int count = list.size();
        return ResponseMsgUtil.returnCodeMessage(ResponseCode.REQUEST_SUCCESS, list, count);
    }


}
