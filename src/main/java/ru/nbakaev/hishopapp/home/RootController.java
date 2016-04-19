package ru.nbakaev.hishopapp.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Nikita Bakaev, ya@nbakaev.ru on 1/10/2016.
 * All Rights Reserved
 */

/**
 * This is just test root controller
 * to check that all is right
 */
@Controller
@RequestMapping("/api/v1/")
public class RootController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    RootClass index(HttpServletRequest request) {
        RootClass myCustomClass = new RootClass();
        myCustomClass.setServerRequestName(request.getServerName());
        return myCustomClass;
    }

}