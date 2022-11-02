package control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
    @RequestMapping("/index")
//    public String handleRequest(Model model) throws Exception {
//        model.addAttribute("message","这是第一个SpringMVC网页");
//        return "index";
//    }
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView model=new ModelAndView("index");
        model.addObject("message","这是第一个SpringMVC网页");
        return model;
    }
}