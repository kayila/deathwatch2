package com.kitsuneindustries.deathwatch2.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kitsuneindustries.deathwatch2.ModConfig;
import com.kitsuneindustries.deathwatch2.data.PlayerDeath;
import com.kitsuneindustries.deathwatch2.store.DeathRegistry;


public class DeathwatchServlet extends HttpServlet {
	
	private static final long serialVersionUID = 0L;
	
	private final ObjectMapper objMapper;
	private final DeathRegistry deathRegistry;
	
	public DeathwatchServlet() {
		objMapper = new ObjectMapper();
		deathRegistry = new DeathRegistry();
	}
	
	@Override
	protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		Map<String, String[]> params = request.getParameterMap();
		
		List<PlayerDeath> death = deathRegistry.findAll();
		response.setStatus(200);
		response.setHeader("Access-Control-Allow-Origin", ModConfig.webserver.corsAllowOrigin);
		response.setContentType("application/json");
		response.getWriter().write(objMapper.writeValueAsString(death));
	}
}
