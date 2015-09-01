/*
 * Copyright 2015 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public
 * License version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */
package org.n52.wfs.ds;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.n52.iceland.config.annotation.Configurable;
import org.n52.iceland.config.annotation.Setting;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.lifecycle.Constructable;
import org.n52.iceland.lifecycle.Destroyable;
import org.n52.iceland.util.CollectionHelper;
import org.n52.iceland.util.Constants;
import org.n52.iceland.util.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

@Configurable
public class HttpClientHandler implements Constructable, Destroyable {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetFeatureHandler.class);
    
    public static final String SOS_URL_KEY = "wfs.sosUrl";

    private CloseableHttpClient httpclient;

    private URI url;

    @Setting(SOS_URL_KEY)
    public void setUrl(URI url) {
        this.url = url;
    }

    public String doGet(Map<String, List<String>> parameter) throws OwsExceptionReport {
        HttpGet httpGet;
        try {
            httpGet = new HttpGet(getGetUrl(url, parameter));
            return getContent(httpclient.execute(httpGet));
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String doPost(String content, MediaType contentType) {
        try {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(content, ContentType.create(contentType.toString(), "UTF-8")));
        return getContent(httpclient.execute(httpPost));
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private String getContent(CloseableHttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity(), "UTF-8");
    }

    private URI getGetUrl(URI url, Map<String, List<String>> parameters) throws URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        if (CollectionHelper.isNotEmpty(parameters)) {
            for (String key : parameters.keySet()) {
                uriBuilder.addParameter(key, Joiner.on(Constants.COMMA_CHAR).join(parameters.get(key)));
            }
        }
        URI uri = uriBuilder.build();
        LOGGER.debug("Executing GET method '{}'", uri);
        return uri;
    }

    @Override
    public void init() {
        httpclient = HttpClients.createDefault();
    }

    @Override
    public void destroy() {
        if (httpclient != null) {
            try {
                httpclient.close();
            } catch (IOException ioe) {
                LOGGER.error("Error while closing HttpClient", ioe);
            }
        }

    }
}
