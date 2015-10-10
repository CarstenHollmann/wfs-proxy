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


import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsAdHocQuery;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsExtendedOperators;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsFunctions;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsMinSpatialFilter;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsMinStandardFilter;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsMinTemporalFilter;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsMinimumXPath;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsQuery;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsResourceld;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsSchemaElementFunc;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsSorting;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsSpatialFilter;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsStandardFilter;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsTemporalFilter;
import static org.n52.iceland.ogc.filter.FilterConstants.ConformanceClassConstraintNames.ImplementsVersionNav;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.xml.namespace.QName;

import org.n52.iceland.exception.CodedException;
import org.n52.iceland.exception.ows.CompositeOwsException;
import org.n52.iceland.exception.ows.InvalidParameterValueException;
import org.n52.iceland.exception.ows.OwsExceptionReport;
import org.n52.iceland.exception.ows.VersionNegotiationFailedException;
import org.n52.iceland.ogc.OGCConstants;
import org.n52.iceland.ogc.filter.FilterConstants.ComparisonOperator;
import org.n52.iceland.ogc.filter.FilterConstants.SpatialOperator;
import org.n52.iceland.ogc.filter.FilterConstants.TimeOperator;
import org.n52.iceland.ogc.gml.GmlConstants;
import org.n52.iceland.ogc.om.OmConstants;
import org.n52.iceland.ogc.ows.OWSConstants;
import org.n52.iceland.ogc.ows.OwsDomainType;
import org.n52.iceland.ogc.ows.OwsNoValues;
import org.n52.iceland.ogc.ows.OwsOperation;
import org.n52.iceland.ogc.ows.OwsOperationsMetadata;
import org.n52.iceland.ogc.ows.OwsParameterValuePossibleValues;
import org.n52.iceland.ogc.ows.OwsServiceIdentification;
import org.n52.iceland.ogc.ows.ServiceIdentificationFactory;
import org.n52.iceland.ogc.ows.ServiceMetadataRepository;
import org.n52.iceland.ogc.ows.ServiceProviderFactory;
import org.n52.iceland.request.operator.RequestOperator;
import org.n52.iceland.request.operator.RequestOperatorRepository;
import org.n52.iceland.service.operator.ServiceOperatorKey;
import org.n52.iceland.service.operator.ServiceOperatorRepository;
import org.n52.iceland.util.ThrowingCallable;
import org.n52.iceland.util.collections.MultiMaps;
import org.n52.iceland.util.collections.SetMultiMap;
import org.n52.iceland.util.http.MediaTypes;
import org.n52.ogc.wfs.WfsCapabilities;
import org.n52.ogc.wfs.WfsCapabilitiesCrs;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsElement;
import org.n52.ogc.wfs.WfsExtendedDescription;
import org.n52.ogc.wfs.WfsFeatureType;
import org.n52.sos.ogc.filter.FilterCapabilities;
import org.n52.sos.ogc.sos.SosEnvelope;
import org.n52.sos.util.GeometryHandler;
import org.n52.wfs.request.GetCapabilitiesRequest;
import org.n52.wfs.response.GetCapabilitiesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

/**
 * WFS 2.0 GetCapabilities DAO class
 *
 * @author Carsten Hollmann <c.hollmann@52north.org>
 *
 * @since 1.0.0
 *
 */
public abstract class AbstractWfsGetCapabilitiesHandler extends AbstractGetCapabilitiesHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractWfsGetCapabilitiesHandler.class);

    /* section flags (values are powers of 2) */
    private static final int SERVICE_IDENTIFICATION = 0x01;
    private static final int SERVICE_PROVIDER = 0x02;
    private static final int OPERATIONS_METADATA = 0x04;
    private static final int FILTER_CAPABILITIES = 0x08;
    private static final int FEATURE_TYPE_LIST = 0x10;

    private static final int ALL = 0x20 |
                                   SERVICE_IDENTIFICATION |
                                   SERVICE_PROVIDER |
                                   OPERATIONS_METADATA |
                                   FILTER_CAPABILITIES |
                                   FEATURE_TYPE_LIST;

    @Inject
    private ServiceMetadataRepository serviceMetadataRepository;
    @Inject
    private ServiceOperatorRepository serviceOperatorRepository;
    @Inject
    private RequestOperatorRepository requestOperatorRepository;
    @Inject
    private GeometryHandler geometryHandler;

    public AbstractWfsGetCapabilitiesHandler() {
        super(WfsConstants.WFS);
    }


    @Override
    protected Set<String> getExtensionSections(String service, String version)
            throws OwsExceptionReport {
        return Collections.emptySet();
    }

    @Override
    public GetCapabilitiesResponse getCapabilities(GetCapabilitiesRequest request) throws OwsExceptionReport {
        GetCapabilitiesResponse response = new GetCapabilitiesResponse();
        response.setService(WfsConstants.WFS);
        response.setVersion(getVersionParameter(request));

        Set<String> availableExtensionSections = getExtensionSections(response.getService(), response.getVersion());
        Set<String> requestedExtensionSections = new HashSet<>(availableExtensionSections.size());
        int requestedSections = identifyRequestedSections(request, response, availableExtensionSections, requestedExtensionSections);
        WfsCapabilities wfsCapabilities = new WfsCapabilities(response.getVersion());
        addSectionSpecificContent(request, response, requestedExtensionSections, requestedSections, wfsCapabilities);
        response.setCapabilities(wfsCapabilities);
        return response;
    }

    protected abstract SosEnvelope wgs84BoundingBoxes(String featureType) throws OwsExceptionReport;

    protected abstract WfsElement getPhenomenonTimeElement(String featureType) throws OwsExceptionReport;

    private void addSectionSpecificContent(GetCapabilitiesRequest request,
                                           GetCapabilitiesResponse response,
                                           Set<String> requestedExtensionSections,
                                           int sections,
                                           WfsCapabilities wfsCapabilities)
            throws OwsExceptionReport {
        if (isServiceIdentificationSectionRequested(sections)) {
            wfsCapabilities.setServiceIdentification(getServiceIdentification(response.getService(), response.getVersion()));
        }
        if (isServiceProviderSectionRequested(sections)) {
            wfsCapabilities.setServiceProvider( this.serviceMetadataRepository.getServiceProviderFactory(response.getService()).get());
        }
        if (isOperationsMetadataSectionRequested(sections)) {
            wfsCapabilities.setOperationsMetadata(getOperationsMetadataForOperations(request, response.getService(), response.getVersion()));
        }
        if (isFilterCapabilitiesSectionRequested(sections)) {
            wfsCapabilities.setFilterCapabilities(getFilterCapabilities(response.getVersion()));
        }
        if (isFeatureTypeListSectionRequested(sections)) {
            wfsCapabilities.setFeatureTypeList(getFeatureTypeList());
        }

        // if (isVersionSos2(response)) {
        // if (sections == ALL) {
        // sosCapabilities.setExensions(getAndMergeExtensions(response.getService(),
        // response.getVersion()));
        // } else if (!requestedExtensionSections.isEmpty()) {
        // sosCapabilities.setExensions(getExtensions(requestedExtensionSections,
        // response.getService(),
        // response.getVersion()));
        // }
        // }
    }

    private int identifyRequestedSections(final GetCapabilitiesRequest request,
            final GetCapabilitiesResponse response, final Set<String> availableExtensionSections,
            final Set<String> requestedExtensionSections) throws OwsExceptionReport {
        int sections = 0;
        // handle sections array and set requested sections flag
        if (!request.isSetSections()) {
            sections = ALL;
        } else {
            for (final String section : request.getSections()) {
                if (section.isEmpty()) {
                    LOGGER.warn("A {} element is empty! Check if operator checks for empty elements!",
                            WfsConstants.GetCapabilitiesParams.Section);
                    continue;
                }
                if (section.equals(WfsConstants.CapabilitiesSections.All.name())) {
                    sections = ALL;
                    break;
                } else if (section.equals(WfsConstants.CapabilitiesSections.ServiceIdentification.name())) {
                    sections |= SERVICE_IDENTIFICATION;
                } else if (section.equals(WfsConstants.CapabilitiesSections.ServiceProvider.name())) {
                    sections |= SERVICE_PROVIDER;
                } else if (section.equals(WfsConstants.CapabilitiesSections.OperationsMetadata.name())) {
                    sections |= OPERATIONS_METADATA;
                } else if (section.equals(WfsConstants.CapabilitiesSections.Filter_Capabilities.name())) {
                    sections |= FILTER_CAPABILITIES;
                } else if (section.equals(WfsConstants.CapabilitiesSections.FeatureTypeList.name())) {
                    sections |= FEATURE_TYPE_LIST;
                    // } else if (availableExtensionSections.contains(section)
                    // && isVersionSos2(response)) {
                    // requestedExtensionSections.add(section);
                } else {
                    throw new InvalidParameterValueException().at(WfsConstants.GetCapabilitiesParams.Section)
                            .withMessage("The requested section '%s' does not exist or is not supported!", section);
                }
            }
        }
        return sections;
    }

    private String getVersionParameter(final GetCapabilitiesRequest request) throws OwsExceptionReport {
        if (request.isSetVersion()) {
            return request.getVersion();
        } else if (request.isSetAcceptVersions()) {
            return getVersion(request.getService(),
                              request.getAcceptVersions());
        } else {
            return getVersion(request.getService());
        }
    }

    private String getVersion(String service) throws OwsExceptionReport {
        return this.serviceOperatorRepository
                .getSupportedVersions(service).stream()
                .findFirst().orElseThrow(this::versionNegotiationFailed);
    }

    private String getVersion(String service, Collection<String> acceptVersions)
            throws OwsExceptionReport {
        return acceptVersions.stream()
                .filter(version -> this.serviceOperatorRepository
                        .isVersionSupported(service, version))
                .findFirst().orElseThrow(this::versionNegotiationFailed);
    }

    private CodedException versionNegotiationFailed() {
        return new VersionNegotiationFailedException().withMessage(
                "The requested '%s' values are not supported by this service!",
                WfsConstants.GetCapabilitiesParams.AcceptVersions);
    }

    private OwsServiceIdentification getServiceIdentification(String service, String version) throws OwsExceptionReport {
        // TODO rename SosServiceIdentification to OwsServiceIdentification
        OwsServiceIdentification serviceIdentification =  this.serviceMetadataRepository.getServiceIdentificationFactory(service).get();
        serviceIdentification.setServiceType(WfsConstants.WFS);
        serviceIdentification.setVersions(Sets.newHashSet(WfsConstants.VERSION));
        // if (version.equals(WfsConstants.VERSION)) {
        // serviceIdentification.setProfiles(getProfiles());
        // }
        return serviceIdentification;
    }

    /**
     * Get the OperationsMetadat for all supported operations
     *
     * @param request the request
     * @param service
     *            Requested service
     * @param version
     *            Requested service version
     * @return OperationsMetadata for all operations supported by the requested
     *         service and version
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    private OwsOperationsMetadata getOperationsMetadataForOperations(GetCapabilitiesRequest request, String service, String version)
            throws OwsExceptionReport {

        OwsOperationsMetadata operationsMetadata = new OwsOperationsMetadata();
        Set<String> versions = serviceOperatorRepository.getSupportedVersions(service);
        operationsMetadata.addCommonValue(OWSConstants.RequestParams.service.name(), new OwsParameterValuePossibleValues(WfsConstants.WFS));
        operationsMetadata.addCommonValue(OWSConstants.RequestParams.version.name(), new OwsParameterValuePossibleValues(versions));
        ServiceOperatorKey sok = new ServiceOperatorKey(service, version);
        CompositeOwsException exceptions = new CompositeOwsException();
        List<OwsOperation> opsMetadata = requestOperatorRepository.getActiveRequestOperators(sok).stream()
                .map(op -> exceptions.wrap(this.getOwsOperation(op, sok)))
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toList());
        exceptions.throwIfNotEmpty();
        operationsMetadata.setOperations(opsMetadata);
        return operationsMetadata;
    }

    private ThrowingCallable<OwsOperation, OwsExceptionReport> getOwsOperation(RequestOperator op, ServiceOperatorKey sok) {
        return new ThrowingCallable<OwsOperation, OwsExceptionReport>() {
            @Override
            public OwsOperation call() throws OwsExceptionReport {
                return op.getOperationMetadata(sok.getService(), sok.getVersion());
            }
        };
    }

    /**
     * Get the FilterCapabilities
     *
     * @param version
     *            Requested service version
     * @return FilterCapabilities
     */
    private FilterCapabilities getFilterCapabilities(final String version) {
        final FilterCapabilities filterCapabilities = new FilterCapabilities();
        addConformance(filterCapabilities);
        // // !!! Modify methods addicted to your implementation !!!
        // if (version.equals(Sos1Constants.SERVICEVERSION)) {
        // getScalarFilterCapabilities(filterCapabilities);
        // }
        getScalarFilterCapabilities(filterCapabilities);
        getSpatialFilterCapabilities(filterCapabilities, version);
        getTemporalFilterCapabilities(filterCapabilities, version);

        return filterCapabilities;
    }

    private void addConformance(FilterCapabilities caps) {
        caps.addConformance(new OwsDomainType(ImplementsQuery, new OwsNoValues(), FALSE));
        caps.addConformance(new OwsDomainType(ImplementsAdHocQuery, new OwsNoValues(), TRUE));
        caps.addConformance(new OwsDomainType(ImplementsFunctions, new OwsNoValues(), FALSE));
        caps.addConformance(new OwsDomainType(ImplementsResourceld, new OwsNoValues(), FALSE));
        caps.addConformance(new OwsDomainType(ImplementsMinStandardFilter, new OwsNoValues(), FALSE));
        caps.addConformance(new OwsDomainType(ImplementsStandardFilter, new OwsNoValues(), FALSE));
        caps.addConformance(new OwsDomainType(ImplementsMinSpatialFilter, new OwsNoValues(), TRUE));
        caps.addConformance(new OwsDomainType(ImplementsSpatialFilter, new OwsNoValues(), FALSE));
        caps.addConformance(new OwsDomainType(ImplementsMinTemporalFilter, new OwsNoValues(), TRUE));
        caps.addConformance(new OwsDomainType(ImplementsTemporalFilter, new OwsNoValues(), FALSE));
        caps.addConformance(new OwsDomainType(ImplementsVersionNav, new OwsNoValues(), FALSE));
        caps.addConformance(new OwsDomainType(ImplementsSorting, new OwsNoValues(), FALSE));
        caps.addConformance(new OwsDomainType(ImplementsExtendedOperators, new OwsNoValues(), FALSE));
        caps.addConformance(new OwsDomainType(ImplementsMinimumXPath, new OwsNoValues(), FALSE));
        caps.addConformance(new OwsDomainType(ImplementsSchemaElementFunc, new OwsNoValues(), FALSE));
    }

    /**
     * Set SpatialFilterCapabilities to FilterCapabilities
     *
     * @param filterCapabilities
     *            FilterCapabilities
     * @param version
     *            SOS version
     */
    private void getSpatialFilterCapabilities(FilterCapabilities filterCapabilities, String version) {

        // set GeometryOperands

        filterCapabilities.setSpatialOperands(Collections.singletonList(GmlConstants.QN_ENVELOPE_32));

        // set SpatialOperators
        final SetMultiMap<SpatialOperator, QName> ops = MultiMaps.newSetMultiMap(SpatialOperator.class);
        ops.add(SpatialOperator.BBOX, GmlConstants.QN_ENVELOPE_32);

        filterCapabilities.setSpatialOperators(ops);
    }

    /**
     * Set TemporalFilterCapabilities to FilterCapabilities
     *
     * @param filterCapabilities
     *            FilterCapabilities
     * @param version
     *            SOS version
     */
    private void getTemporalFilterCapabilities(final FilterCapabilities filterCapabilities, final String version) {

        // set TemporalOperands
        filterCapabilities.setTemporalOperands(Arrays
                .asList(GmlConstants.QN_TIME_PERIOD_32,
                        GmlConstants.QN_TIME_INSTANT_32));

        // set TemporalOperators
        final SetMultiMap<TimeOperator, QName> ops = MultiMaps.newSetMultiMap(TimeOperator.class);
        ops.add(TimeOperator.TM_Equals, GmlConstants.QN_TIME_INSTANT_32);
        ops.add(TimeOperator.TM_During, GmlConstants.QN_TIME_PERIOD_32);
        filterCapabilities.setTemporalOperators(ops);
    }

    /**
     * Set ScalarFilterCapabilities to FilterCapabilities
     *
     * @param filterCapabilities
     *            FilterCapabilities
     */
    private void getScalarFilterCapabilities(final FilterCapabilities filterCapabilities) {
        // TODO PropertyIsNil, PropertyIsNull? better:
        // filterCapabilities.setComparisonOperators(Arrays.asList(ComparisonOperator.values()));
        final List<ComparisonOperator> comparisonOperators = new ArrayList<>(8);
        // comparisonOperators.add(ComparisonOperator.PropertyIsBetween);
        comparisonOperators.add(ComparisonOperator.PropertyIsEqualTo);
        // comparisonOperators.add(ComparisonOperator.PropertyIsNotEqualTo);
        // comparisonOperators.add(ComparisonOperator.PropertyIsLessThan);
        // comparisonOperators.add(ComparisonOperator.PropertyIsLessThanOrEqualTo);
        // comparisonOperators.add(ComparisonOperator.PropertyIsGreaterThan);
        // comparisonOperators.add(ComparisonOperator.PropertyIsGreaterThanOrEqualTo);
        // comparisonOperators.add(ComparisonOperator.PropertyIsLike);
        filterCapabilities.setComparisonOperators(comparisonOperators);
    }

    protected Collection<WfsFeatureType> getFeatureTypeList() throws OwsExceptionReport {
        String featureType = "observatons";
        WfsFeatureType wfsFeatureType = new WfsFeatureType(getName(featureType), getWfsCapabilitiesCrs(featureType));
        wfsFeatureType.setTitles(getTitles(featureType));
        wfsFeatureType.setAbstracts(getAbstracts(featureType));
        wfsFeatureType.setKeywords(getKeywods(featureType));
        wfsFeatureType.setOutputFormats(getOutputFormats(featureType));
        wfsFeatureType.addWgs84BoundingBoxes(wgs84BoundingBoxes(featureType));
        // FIXME for testing Geoserver
//        wfsFeatureType.setExtendedDescription(extendedDescription(featureType));
        return Sets.newHashSet(wfsFeatureType);
    }

    private QName getName(String featureType) {
        return OmConstants.QN_OM_20_OBSERVATION;
    }

    private Set<String> getTitles(String featureType) {
        // TODO settings?
        return Sets.newHashSet("Observations");
    }

    private Set<String> getAbstracts(String featureType) {
        // TODO settings?
        return Sets.newHashSet("OWS-10 observation for VGI");
    }

    private Set<String> getKeywods(String featureType) {
        // TODO settings?
        return Sets.newHashSet("observations");
    }


    protected WfsCapabilitiesCrs getWfsCapabilitiesCrs(String featureType) {
        String defaultCrs = OGCConstants.URN_DEF_CRS_EPSG + this.geometryHandler.getDefaultResponseEPSG();
        return new WfsCapabilitiesCrs(defaultCrs);
    }

    private Set<String> getOutputFormats(String featureType) {
//        return Sets.newHashSet(MediaTypes.APPLICATION_GML_32.toString(), MediaTypes.APPLICATION_OM_20.toString());
        return Sets.newHashSet(MediaTypes.APPLICATION_GML_32.toString());
    }

    private WfsExtendedDescription extendedDescription(String featureType) throws OwsExceptionReport {
        WfsElement element = getPhenomenonTimeElement(featureType);
        if (element != null) {
            return new WfsExtendedDescription(getPhenomenonTimeElement(featureType));
        }
        return null;
    }

    private boolean isFeatureTypeListSectionRequested(final int sections) {
        return (sections & FEATURE_TYPE_LIST) != 0;
    }

    private boolean isFilterCapabilitiesSectionRequested(final int sections) {
        return (sections & FILTER_CAPABILITIES) != 0;
    }

    private boolean isOperationsMetadataSectionRequested(final int sections) {
        return (sections & OPERATIONS_METADATA) != 0;
    }

    private boolean isServiceProviderSectionRequested(final int sections) {
        return (sections & SERVICE_PROVIDER) != 0;
    }

    private boolean isServiceIdentificationSectionRequested(final int sections) {
        return (sections & SERVICE_IDENTIFICATION) != 0;
    }

}
