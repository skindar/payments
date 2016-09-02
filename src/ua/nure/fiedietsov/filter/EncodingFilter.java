/**
* @author Fiedietsov V.
* @version 1.0
*/
package ua.nure.fiedietsov.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * EncodingFilter sets UTF-8 encoding.
 * 
 * @author Fiedietsov V.
 * @version 1.0
 */
public class EncodingFilter implements Filter {
	private static final Logger LOGGER = Logger.getLogger(EncodingFilter.class);

	public void destroy() {
		// empty
	}
	/**
	 * Method sets UTF-8 encoding.
	 * 
	 * @author Fiedietsov V.
	 * @version 1.0
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		LOGGER.trace("Request uri --> " + httpRequest.getRequestURI());

		request.setCharacterEncoding("UTF-8");
		LOGGER.debug("UTF-8 is enabled");
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		LOGGER.debug("Filter initialization starts");
	}

}