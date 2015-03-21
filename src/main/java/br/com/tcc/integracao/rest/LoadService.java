package br.com.tcc.integracao.rest;

import java.awt.PageAttributes.MediaType;

import javax.xml.ws.Response;

public class LoadService {

	@POST
    @Path(value = "/")
    @Produces(MediaType.APPLICATION_XML)
    public Response ativos(byte[] file) {
		return null;
	}
}
