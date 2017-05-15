package com.happy3w.seeworld.controller;

import com.happy3w.seeworld.entity.Hero;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysgao on 09/05/2017.
 */
@RestController
@RequestMapping("api/heroes")
public class HeroController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Hero> query() {
        List<Hero> heroes = new ArrayList<>();
        heroes.add(new Hero(11, "Nice"));
        heroes.add(new Hero(12, "Jerry"));
        heroes.add(new Hero(14, "Tom"));
        return heroes;
    }

}
