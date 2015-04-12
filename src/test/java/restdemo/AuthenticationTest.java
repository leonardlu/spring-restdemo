//package restdemo;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import org.mockito.Mockito;
//import org.springframework.security.oauth2.client.OAuth2RestOperations;
//import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//
//public class AuthenticationTest {
//	private OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(
//			"http://localhost:8080/api");
//
//	private ResourceServerTokenServices tokenServices = Mockito.mock(ResourceServerTokenServices.class);
//
//	private OAuth2RestOperations restTemplate = Mockito.mock(OAuth2RestOperations.class);
//
//	private OAuth2Authentication authentication;
//	
//	@Rule
//	public ExpectedException expected = ExpectedException.none();
//
//	@Test
//	public void testAuthentication() throws Exception {
//		filter.setRestTemplate(restTemplate);
//		filter.setTokenServices(tokenServices);
//		Mockito.when(restTemplate.getAccessToken()).thenReturn(new DefaultOAuth2AccessToken("FOO"));
//		Set<String> scopes = new HashSet<String>();
//		scopes.addAll(Arrays.asList("read", "write"));
//		OAuth2Request storedOAuth2Request = RequestTokenFactory.createOAuth2Request("client", false, scopes);
//		this.authentication = new OAuth2Authentication(storedOAuth2Request, null);
//		Mockito.when(tokenServices.loadAuthentication("FOO")).thenReturn(authentication);
//		Authentication authentication = filter.attemptAuthentication(new MockHttpServletRequest(), null);
//		assertEquals(this.authentication, authentication);
//		Mockito.verify(restTemplate, Mockito.times(1)).getAccessToken();
//	}
//
//	@Test
//	public void testAuthenticationWithTokenType() throws Exception {
//		filter.setRestTemplate(restTemplate);
//		filter.setTokenServices(tokenServices);
//		DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken("FOO");
//		token.setTokenType("foo");
//		Mockito.when(restTemplate.getAccessToken()).thenReturn(token);
//		Set<String> scopes = new HashSet<String>();
//		scopes.addAll(Arrays.asList("read", "write"));
//		OAuth2Request storedOAuth2Request = RequestTokenFactory.createOAuth2Request("client", false, scopes);
//		this.authentication = new OAuth2Authentication(storedOAuth2Request, null);
//		Mockito.when(tokenServices.loadAuthentication("FOO")).thenReturn(authentication);
//		Authentication authentication = filter.attemptAuthentication(new MockHttpServletRequest(), null);
//		assertEquals("foo", ((OAuth2AuthenticationDetails) authentication.getDetails()).getTokenType());
//	}
//
//	@Test
//	public void testSuccessfulAuthentication() throws Exception {
//		filter.setRestTemplate(restTemplate);
//		Set<String> scopes = new HashSet<String>();
//		scopes.addAll(Arrays.asList("read", "write"));
//		OAuth2Request storedOAuth2Request = RequestTokenFactory.createOAuth2Request("client", false, scopes);
//		this.authentication = new OAuth2Authentication(storedOAuth2Request, null);
//		filter.successfulAuthentication(new MockHttpServletRequest(), new MockHttpServletResponse(), null, authentication);
//		Mockito.verify(restTemplate, Mockito.times(1)).getAccessToken();
//	}
//
//	@Test
//	public void testDeniedToken() throws Exception {
//		filter.setRestTemplate(restTemplate);
//		Mockito.when(restTemplate.getAccessToken()).thenThrow(new OAuth2Exception("User denied acess token"));
//		expected.expect(BadCredentialsException.class);
//		filter.attemptAuthentication(null, null);
//	}
//
//	@Test
//	public void testUnsuccessfulAuthentication() throws IOException, ServletException {
//		try {
//			filter.unsuccessfulAuthentication(null, null, new AccessTokenRequiredException("testing", null));
//			fail("AccessTokenRedirectException must be thrown");
//		}
//		catch (AccessTokenRequiredException ex) {
//
//		}
//	}
//}
