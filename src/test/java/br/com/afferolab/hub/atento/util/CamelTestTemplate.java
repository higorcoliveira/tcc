package br.com.afferolab.hub.atento.util;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.model.ModelCamelContext;
import org.apache.camel.test.spring.CamelSpringTestContextLoaderTestExecutionListener;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import br.com.afferolab.hub.atento.ativos.mappings.LoadService;
import br.com.afferolab.utils.camel.response.IntegrationResponse;
import br.com.afferolab.utils.camel.response.ResponseFactory;
import br.com.afferolab.utils.http.security.cxf.HMACSecurityInterceptor;
import br.com.afferolab.utils.util.StringUtil;
import br.com.afferolab.utils.util.TestUtil;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

@ContextConfiguration(locations = { "/META-INF/spring/camel-context.xml",
									"/META-INF/spring/config-context.xml",
									"/META-INF/spring/cxf-context.xml",
									"/META-INF/spring/client-rest-cxf.xml",
									"/META-INF/spring/ibatis-context.xml",
									"/META-INF/spring/jms-context.xml"})
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@TestExecutionListeners(inheritListeners = false, listeners = {
		CamelSpringTestContextLoaderTestExecutionListener.class,
		DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
public abstract class CamelTestTemplate {
	
	@Autowired
	private ModelCamelContext context;
	
	private ProducerTemplate producerTemplate;
	
	public StringUtil util = new StringUtil();
	
	public ModelCamelContext getCamelContext() {
		return context;
	}
	
	public ProducerTemplate createProducerTemplate() throws Exception {
		return context.createProducerTemplate();
	}
	
	public ProducerTemplate startProducerTemplate() throws Exception {
		producerTemplate = createProducerTemplate();
		producerTemplate.start();
		return producerTemplate;
	}
	
	public void stopProducerTemplate() throws Exception {
		producerTemplate.stop();
	}
	
	@Before
	public abstract void init() throws Exception;
	
	public void assertBadRequest(Response reply, IntegrationResponse integrationResponse) {
		Assert.assertEquals(HttpServletResponse.SC_BAD_REQUEST, reply.getStatus());
		Assert.assertEquals(ResponseFactory.ERROR_CODE.toString(), TestUtil.getHeader(reply, ResponseFactory.H_CODE_HEADER));
		Assert.assertNotNull(TestUtil.getHeader(reply, ResponseFactory.H_REQUESTID_HEADER));
		Assert.assertEquals(ResponseFactory.ERROR_CODE, integrationResponse.getCode());
		Assert.assertEquals(TestUtil.getHeader(reply, ResponseFactory.H_REQUESTID_HEADER), integrationResponse.getRequestID());
	}
	
	public void assertOK(Response reply, IntegrationResponse integrationResponse) {
		Assert.assertEquals(HttpServletResponse.SC_OK, reply.getStatus());
		Assert.assertEquals(ResponseFactory.SUCCESS_CODE.toString(), TestUtil.getHeader(reply, ResponseFactory.H_CODE_HEADER));
		Assert.assertNotNull(TestUtil.getHeader(reply, ResponseFactory.H_REQUESTID_HEADER));
		Assert.assertEquals(ResponseFactory.SUCCESS_CODE, integrationResponse.getCode());
		Assert.assertEquals(TestUtil.getHeader(reply, ResponseFactory.H_REQUESTID_HEADER), integrationResponse.getRequestID());
	}
	
	public LoadService createProxy(String url) {
		LoadService proxy = JAXRSClientFactory.create(url, LoadService.class);
		ClientConfiguration config = WebClient.getConfig(proxy);
		config.getOutInterceptors().add(new HMACSecurityInterceptor("atento", "1234"));
		
		return proxy;
	}
}
