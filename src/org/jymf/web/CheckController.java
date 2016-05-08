package org.jymf.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CheckController {
	@RequestMapping(value = "/check")
	public String montitorLogin() {
		return "check";
	}
}