package com.apicatalog.jsonld;

import java.net.URI;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonString;

import com.apicatalog.jsonld.api.impl.CompactionApi;
import com.apicatalog.jsonld.api.impl.ExpansionApi;
import com.apicatalog.jsonld.api.impl.FlatteningApi;
import com.apicatalog.jsonld.api.impl.FramingApi;
import com.apicatalog.jsonld.api.impl.FromRdfApi;
import com.apicatalog.jsonld.api.impl.ToRdfApi;
import com.apicatalog.jsonld.document.Document;
import com.apicatalog.jsonld.uri.UriUtils;
import com.apicatalog.rdf.RdfDataset;

/**
 * The {@link JsonLd} interface is the high-level programming structure
 * that developers use to access the JSON-LD transformation methods.
 * This class provides all methods to process JSON-LD.
 */
public final class JsonLd {

    private static final String DOCUMENT_LOCATION_PARAM_NAME = "documentLocation";
    private static final String DOCUMENT_URI_PARAM_NAME = "documentUri";
    private static final String DOCUMENT_PARAM_NAME = "document";
    private static final String CONTEXT_PARAM_NAME = "context";
    private static final String FRAME_LOCATION_PARAM_NAME = "frameLocation";
    private static final String FRAME_URI_PARAM_NAME = "frameUri";
    private static final String FRAME_PARAM_NAME = "frame";

    private JsonLd() {
    }
    
    /**
     * Expands the referenced document.
     * 
     * @param documentLocation {@code IRI} referencing JSON-LD document to expand
     * @return {@link ExpansionApi} allowing to set additional parameters 
     */
    public static final ExpansionApi expand(final String documentLocation) {

        assertLocation(documentLocation, DOCUMENT_LOCATION_PARAM_NAME);
        
        return new ExpansionApi(UriUtils.create(documentLocation));
    }

    /**
     * Expands the referenced document.
     * 
     * @param documentUri {@link URI} referencing JSON-LD document to expand
     * @return {@link ExpansionApi} allowing to set additional parameters 
     */
    public static final ExpansionApi expand(final URI documentUri) {
            
        assertUri(documentUri, DOCUMENT_URI_PARAM_NAME);
        
        return new ExpansionApi(documentUri);
    }

    /**
     * Expands the provided remote document.
     * 
     * @param document to expand
     * @return {@link ExpansionApi} allowing to set additional parameters 
     */
    public static final ExpansionApi expand(final Document document) {

        assertJsonDocument(document, DOCUMENT_PARAM_NAME);
        
        return new ExpansionApi(document);
    }

    /**
     * Compacts the referenced document using the context.
     * 
     * @param documentLocation {@code IRI} referencing JSON-LD document to compact
     * @param contextLocation {@code IRI} referencing the context to use when compacting the document
     * @return {@link CompactionApi} allowing to set additional parameters 
     */
    public static final CompactionApi compact(final String documentLocation, final String contextLocation) {
        
        assertLocation(documentLocation, DOCUMENT_LOCATION_PARAM_NAME);
        assertLocation(contextLocation, "contextLocation");
                
        return compact(UriUtils.create(documentLocation), UriUtils.create(contextLocation));
    }

    /**
     * Compacts the referenced document using the context.
     * 
     * @param documentUri {@link URI} referencing JSON-LD document to compact
     * @param contextUri {@link URI} referencing the context to use when compacting the document
     * @return {@link CompactionApi} allowing to set additional parameters 
     */
    public static final CompactionApi compact(final URI documentUri, final URI contextUri) {
        
        assertUri(documentUri, DOCUMENT_URI_PARAM_NAME);
        assertUri(contextUri, "contextUri");
        
        return new CompactionApi(documentUri, contextUri);
    }

    /**
     * Compacts the referenced document using the context.
     * 
     * @param documentLocation {@code IRI} referencing JSON-LD document to compact
     * @param context {@link Document} representing the context or {@link JsonArray} consisting of {@link JsonObject} and {@link JsonString} referencing the context to use when compacting the document
     * @return {@link CompactionApi} allowing to set additional parameters 
     */
    public static final CompactionApi compact(final String documentLocation, final Document context) {
        
        assertLocation(documentLocation, DOCUMENT_LOCATION_PARAM_NAME);
        assertJsonDocument(context, CONTEXT_PARAM_NAME);
        
        return new CompactionApi(UriUtils.create(documentLocation), context);
    }

    /**
     * Compacts the referenced document using the context.
     * 
     * @param documentUri {@code URI} referencing JSON-LD document to compact
     * @param context {@link Document} representing the context or {@link JsonArray} consisting of one or many {@link JsonObject} and {@link JsonString} referencing the context to use when compacting the document
     * @return {@link CompactionApi} allowing to set additional parameters 
     */
    public static final CompactionApi compact(final URI documentUri, final Document context) {
        
        assertUri(documentUri, DOCUMENT_URI_PARAM_NAME);
        assertJsonDocument(context, CONTEXT_PARAM_NAME);
        
        return new CompactionApi(documentUri, context);
    }

    /**
     * Compacts {@link Document} document using the context.
     * 
     * @param document to compact
     * @param context JSON-LD document 
     * @return {@link CompactionApi} allowing to set additional parameters 
     */
    public static final CompactionApi compact(final Document document, final Document context) {
        
        assertJsonDocument(document, DOCUMENT_PARAM_NAME);
        assertJsonDocument(context, CONTEXT_PARAM_NAME);
        
        return new CompactionApi(document, context);
    }

    /**
     * Flattens the given input and optionally compacts it using context.
     * 
     * @param documentLocation {@code IRI} referencing JSON-LD document to flatten
     * @return {@link FlatteningApi} allowing to set additional parameters
     */
    public static final FlatteningApi flatten(final String documentLocation) {
        
        assertLocation(documentLocation, DOCUMENT_LOCATION_PARAM_NAME);
        
        return new FlatteningApi(UriUtils.create(documentLocation));
    }

    /**
     * Flattens the given input and optionally compacts it using context.
     * 
     * @param documentUri {@code URI} referencing JSON-LD document to flatten
     * @return {@link FlatteningApi} allowing to set additional parameters
     */
    public static final FlatteningApi flatten(final URI documentUri) {
        
        assertUri(documentUri, DOCUMENT_URI_PARAM_NAME);
        
        return new FlatteningApi(documentUri);
    }

    /**
     * Flattens the remote input and optionally compacts it using context.
     * 
     * @param document to flatten
     * @return {@link FlatteningApi} allowing to set additional parameters
     */
    public static final FlatteningApi flatten(final Document document) {
        
        assertJsonDocument(document, DOCUMENT_PARAM_NAME);
        
        return new FlatteningApi(document);
    }
    
    /**
     *  Frames the given input using frame.
     *  
     * @param documentUri {@code URI} referencing JSON-LD document to frame
     * @param frameUri {@code URI} referencing JSON-LD frame
     * @return {@link FramingApi} allowing to set additional parameters
     */
    public static final FramingApi frame(final URI documentUri, final URI frameUri) {
        
        assertUri(documentUri, DOCUMENT_URI_PARAM_NAME);
        assertUri(frameUri, FRAME_URI_PARAM_NAME);
        
        return new FramingApi(documentUri, frameUri);
    }

    /**
     *  Frames the given input using frame.
     *  
     * @param documenLocation {@code IRI} referencing JSON-LD document to frame
     * @param frameLocation {@code IRI} referencing JSON-LD frame
     * @return {@link FramingApi} allowing to set additional parameters
     */
    public static final FramingApi frame(final String documenLocation, final String frameLocation) {

        assertLocation(documenLocation, DOCUMENT_URI_PARAM_NAME);
        assertLocation(frameLocation, FRAME_LOCATION_PARAM_NAME);

        return new FramingApi(UriUtils.create(documenLocation), UriUtils.create(frameLocation));
    }

    /**
     *  Frames the remote input using given remote frame.
     *  
     * @param document to frame
     * @param frame JSON-LD definition
     * @return {@link FramingApi} allowing to set additional parameters
     */
    public static final FramingApi frame(final Document document, final Document frame) {
        
        assertJsonDocument(document, DOCUMENT_PARAM_NAME);
        assertJsonDocument(frame, FRAME_PARAM_NAME);
        
        return new FramingApi(document, frame);
    }

    /**
     * Transforms the given input into {@link RdfDataset}.
     * 
     * @param documentLocation {@code IRI} referencing JSON-LD document to transform
     * @return {@link ToRdfApi} allowing to set additional parameters
     */
    public static final ToRdfApi toRdf(final String documentLocation) {
        
        assertLocation(documentLocation, DOCUMENT_LOCATION_PARAM_NAME);
        
        return new ToRdfApi(UriUtils.create(documentLocation));
    }

    /**
     * Transforms the given input into {@link RdfDataset}.
     * 
     * @param documentUri {@code URI} referencing JSON-LD document to transform
     * @return {@link ToRdfApi} allowing to set additional parameters
     */
    public static final ToRdfApi toRdf(final URI documentUri) {
        
        assertUri(documentUri, DOCUMENT_URI_PARAM_NAME);
        
        return new ToRdfApi(documentUri);
    }

    /**
     * Transforms {@link Document} into {@link RdfDataset}.
     * 
     * @param document to transform
     * @return {@link ToRdfApi} allowing to set additional parameters
     */
    public static final ToRdfApi toRdf(final Document document) {
        
        assertJsonDocument(document, DOCUMENT_PARAM_NAME);
        
        return new ToRdfApi(document);
    }

    /**
     * Transforms the referenced N-Quads document into a JSON-LD document in expanded form.
     * 
     * @param documentLocation {@link URI} referencing N-Quads document to expand
     * @return {@link FromRdfApi} allowing to set additional parameters
     */
    public static final FromRdfApi fromRdf(final String documentLocation) {
        
        assertLocation(documentLocation, DOCUMENT_LOCATION_PARAM_NAME);

        return new FromRdfApi(UriUtils.create(documentLocation));
    }
    
    /**
     * Transforms the referenced N-Quads document into a JSON-LD document in expanded form.
     * 
     * @param documentUri {@link URI} referencing N-Quads document to expand
     * @return {@link FromRdfApi} allowing to set additional parameters
     */
    public static final FromRdfApi fromRdf(final URI documentUri) {
        
        assertUri(documentUri, DOCUMENT_URI_PARAM_NAME);

        return new FromRdfApi(documentUri);
    }
    
    /**
     * Transforms {@link Document} into a JSON-LD document in expanded form.
     * 
     * @param document to transform
     * @return {@link FromRdfApi} allowing to set additional parameters
     */
    public static final FromRdfApi fromRdf(final Document document) {
        
        assertRdfDocument(document, DOCUMENT_PARAM_NAME);

        return new FromRdfApi(document);
    }

    private static final void assertLocation(final String location, final String param) {
        
        assertNotNull(location, param);

        if (location.isBlank()) {
            throw new IllegalArgumentException("'" + param + "' is blank string.");
        }
        
        if (UriUtils.isNotAbsoluteUri(location.strip())) {
            throw new IllegalArgumentException("'" + param + "' is not an absolute URI [" + location + "].");
        }
    }
    
    private static final void assertUri(final URI uri, final String param) {
        
        assertNotNull(uri, param);
        
        if (!uri.isAbsolute()) {
            throw new IllegalArgumentException("'" + param + "' is not an absolute URI [" + uri + "].");
        }
    }    
    
    private static final void assertJsonDocument(final Document document, final String param) {
        
        assertNotNull(document, param);

        if (document.getJsonContent().isEmpty()) {
            throw new IllegalArgumentException("'" + param + "' is not not JSON document but [" + document.getContentType() + "].");
        }
    }
    
    private static final void assertRdfDocument(final Document document, final String param) {
        
        assertNotNull(document, param);
        
        if (document.getRdfContent().isEmpty()) {
            throw new IllegalArgumentException("'" + param + "' is not not RDF document but [" + document.getContentType() + "].");
        }
    }
    
    private static final void assertNotNull(Object value, final String param) {
        if (value == null) {
            throw new IllegalArgumentException("'" + param + "' is null.");
        }
    }
}
