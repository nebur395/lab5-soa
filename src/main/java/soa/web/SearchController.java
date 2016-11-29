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
    public Object search(@RequestParam("q") String query, @RequestParam("max") String max,
                         @RequestParam("lang") String lang) {
        Map<String,Object> headers = new HashMap<String,Object>();
        headers.put("CamelTwitterKeywords",query);

        // check the max RequestParam
        if (!max.isEmpty()) {
            Integer limitCount = Integer.parseInt(max);
            headers.put("CamelTwitterCount",limitCount);
        }
        // check the max RequestParam
        if (!lang.isEmpty()) {
            headers.put("CamelTwitterSearchLanguage", lang);
        }

        return producerTemplate.requestBodyAndHeaders("direct:search", "", headers);
    }
}