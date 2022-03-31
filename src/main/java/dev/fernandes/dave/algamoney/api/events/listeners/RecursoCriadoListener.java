package dev.fernandes.dave.algamoney.api.events.listeners;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import dev.fernandes.dave.algamoney.api.events.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoCriadoEvent>{

	@Override
	public void onApplicationEvent(RecursoCriadoEvent event) {
		
		HttpServletResponse response = event.getResponse();
		int id = event.getId();
		
		addHeaderLocation(response, id);		
	}

	private void addHeaderLocation(HttpServletResponse response, int id) {
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		//System.out.println("Location:" + uri.toASCIIString());
		response.setHeader("Location", uri.toASCIIString());
	}

}
