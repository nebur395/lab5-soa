package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;


@Controller
public class SearchController {

	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        // save the "limit" restriction of the RequestParam  into a [limitCount] variable
        int indexMax = q.indexOf("max");
        String limitString = q.substring(indexMax);
        String[] limit = limitString.split(":");
        limitString = limit[1];
        Integer limitCount = Integer.parseInt(limitString);
        // save the query of the RequestParam into a [q] variable
        q = q.substring(0,indexMax);
        Map<String,Object> headers = new HashMap<String,Object>();
        headers.put("CamelTwitterKeywords",q);
        headers.put("CamelTwitterCount",limitCount);
        return producerTemplate.requestBodyAndHeaders("direct:search", "", headers);
    }
}