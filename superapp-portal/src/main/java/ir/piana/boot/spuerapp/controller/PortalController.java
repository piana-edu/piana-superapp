package ir.piana.boot.spuerapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("template")
public class PortalController {
    @GetMapping("/{asset-name}")
    public ModelAndView index(@PathVariable(name = "asset-name") String assetName) {
        ModelAndView modelAndView = new ModelAndView("index/index");
        // add title to Model
        modelAndView.addObject("title", "Freemarker");
        return modelAndView;
    }
}
