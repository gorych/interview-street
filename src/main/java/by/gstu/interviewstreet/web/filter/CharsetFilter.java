package by.gstu.interviewstreet.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {
    private static final String REQUEST_ENCODING_PARAM = "requestEncoding";
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private static final String UTF_8_ENCODING = "UTF-8";

    private String encoding;

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter(REQUEST_ENCODING_PARAM);
        if (encoding == null) {
            encoding = UTF_8_ENCODING;
        }
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(encoding);
        }

        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(UTF_8_ENCODING);

        next.doFilter(request, response);
    }

    public void destroy() {
    }
}