package net.marco27.api.sling.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "jcr:root")
public class SlingKeys {

    @JacksonXmlProperty(localName = "jcr:primaryType")
    @JsonIgnore
    private String primaryType;

    @JacksonXmlProperty(localName = "jcr:mixinTypes")
    @JsonIgnore
    private String mixinTypes;

    @JacksonXmlProperty(localName = "sling:basename")
    @JsonIgnore
    private String basename;

    @JacksonXmlProperty(localName = "jcr:language")
    @JsonIgnore
    private String language;

    @JsonIgnore
    private Object[] keys;

}
