package br.com.afferolab.hub.atento.ativos.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LoadService {

	@POST
    @Path(value = "/")
    @Produces(MediaType.APPLICATION_XML)
    public Response ativos(byte[] file) {
		return null;
	}
}
