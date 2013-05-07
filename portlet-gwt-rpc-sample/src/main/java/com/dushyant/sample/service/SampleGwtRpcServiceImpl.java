package com.dushyant.sample.service;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import com.dushyant.sample.shared.dto.SampleException;
import com.dushyant.sample.shared.dto.ServiceInput;
import com.dushyant.sample.shared.dto.ServiceOutput;
import com.dushyant.sample.shared.rpc.SampleGwtRpcService;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.portlet.context.PortletRequestAttributes;

/**
 * An implementation of the GWT RPC service
 */
@Service
public class SampleGwtRpcServiceImpl implements SampleGwtRpcService
{
    private static final Logger LOG = LoggerFactory.getLogger(SampleGwtRpcServiceImpl.class);

    @Override
    public ServiceOutput doSomeStuff(ServiceInput input) throws SampleException
    {
        LOG.debug("RPC service invoked");

        ServiceOutput serviceOutput = null;
        try
        {
            // Here in the GWT RPC method we can now access PortletRequest, PortletSession etc and get whatever we can
            // from those objects as follows.

            // Lets get the current ThemeDisplay object from portlet request
            ThemeDisplay themeDisplay = (ThemeDisplay) getPortletRequest().getAttribute(WebKeys.THEME_DISPLAY);

            // Let's get current group id from the theme display (i.e., from current community or organization)
            Long groupId = themeDisplay.getScopeGroupId();
            String groupName = themeDisplay.getScopeGroup().getDescriptiveName();

            String sessionId = getPortletSession().getId();

            serviceOutput = new ServiceOutput("Hello " + input.getName() + ". You are currently accessing group = " +
                groupName + " with id = " + groupId + " and your sessionId = " + sessionId, input);
        }
        catch (Throwable throwable)
        {
            throw new SampleException("Could not do \"doSomeStuff\"", throwable);
        }
        return serviceOutput;
    }

    public PortletRequest getPortletRequest()
    {
        return getPortletRequestAttributes().getRequest();
    }

    public PortletSession getPortletSession()
    {
        return getPortletRequest().getPortletSession();
    }

    private PortletRequestAttributes getPortletRequestAttributes()
    {
        return (PortletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    private ThemeDisplay getThemeDisplay(PortletRequest request)
    {
        return request == null ? null : (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    }
}
