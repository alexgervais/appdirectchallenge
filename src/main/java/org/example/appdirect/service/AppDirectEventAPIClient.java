package org.example.appdirect.service;

import oauth.signpost.OAuthConsumer;
import org.example.appdirect.service.dto.EventDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class AppDirectEventAPIClient {

    @Inject
    private OAuthConsumer oAuthConsumer;

    public EventDTO getSubscriptionEvent(final String eventURL) throws AppDirectAPIException {

        try {
            final URL url = new URL(eventURL);
            final HttpURLConnection request = (HttpURLConnection) url.openConnection();
            oAuthConsumer.sign(request);
            request.connect();

            JAXBContext jaxbContext = JAXBContext.newInstance(EventDTO.class);
            return (EventDTO) jaxbContext.createUnmarshaller().unmarshal(request.getInputStream());

        } catch (Exception e) {
            throw new AppDirectAPIException(eventURL, e);
        }
    }
}
