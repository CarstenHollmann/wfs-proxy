/**
 * Copyright (C) 2013
 * by 52 North Initiative for Geospatial Open Source Software GmbH
 *
 * Contact: Andreas Wytzisk
 * 52 North Initiative for Geospatial Open Source Software GmbH
 * Martin-Luther-King-Weg 24
 * 48155 Muenster, Germany
 * info@52north.org
 *
 * This program is free software; you can redistribute and/or modify it under
 * the terms of the GNU General Public License version 2 as published by the
 * Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; even without the implied
 * WARRANTY OF MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program (see gnu-gpl v2.txt). If not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA or
 * visit the Free Software Foundation web page, http://www.fsf.org.
 */
package org.n52.wfs.ds;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.n52.ogc.wfs.WfsCapabilities;
import org.n52.ogc.wfs.WfsCapabilitiesCrs;
import org.n52.ogc.wfs.WfsConstants;
import org.n52.ogc.wfs.WfsElement;
import org.n52.ogc.wfs.WfsExtendedDescription;
import org.n52.ogc.wfs.WfsFeatureType;
import org.n52.ogc.wfs.WfsValueList;
import org.n52.sos.exception.ows.InvalidParameterValueException;
import org.n52.sos.exception.ows.VersionNegotiationFailedException;
import org.n52.sos.ogc.OGCConstants;
import org.n52.sos.ogc.filter.FilterCapabilities;
import org.n52.sos.ogc.filter.FilterConstants.ComparisonOperator;
import org.n52.sos.ogc.filter.FilterConstants.ConformanceClassConstraintNames;
import org.n52.sos.ogc.filter.FilterConstants.SpatialOperator;
import org.n52.sos.ogc.filter.FilterConstants.TimeOperator;
import org.n52.sos.ogc.gml.GmlConstants;
import org.n52.sos.ogc.gml.time.TimePeriod;
import org.n52.sos.ogc.om.OmConstants;
import org.n52.sos.ogc.ows.OWSConstants;
import org.n52.sos.ogc.ows.OwsDomainType;
import org.n52.sos.ogc.ows.OwsExceptionReport;
import org.n52.sos.ogc.ows.OwsExtendedCapabilitiesRepository;
import org.n52.sos.ogc.ows.OwsMetadata;
import org.n52.sos.ogc.ows.OwsNoValues;
import org.n52.sos.ogc.ows.OwsOperation;
import org.n52.sos.ogc.ows.OwsOperationsMetadata;
import org.n52.sos.ogc.ows.OwsParameterValuePossibleValues;
import org.n52.sos.ogc.ows.SosServiceIdentification;
import org.n52.sos.ogc.sos.SosEnvelope;
import org.n52.sos.request.operator.RequestOperatorKey;
import org.n52.sos.request.operator.RequestOperatorRepository;
import org.n52.sos.service.operator.ServiceOperatorRepository;
import org.n52.sos.util.GeometryHandler;
import org.n52.sos.util.MultiMaps;
import org.n52.sos.util.SetMultiMap;
import org.n52.sos.util.http.MediaTypes;
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
public class GetCapabilitiesDAO extends AbstractGetCapabilitiesDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetCapabilitiesDAO.class);

    /* section flags (values are powers of 2) */
    private static final int SERVICE_IDENTIFICATION = 0x01;

    private static final int SERVICE_PROVIDER = 0x02;

    private static final int OPERATIONS_METADATA = 0x04;

    private static final int FILTER_CAPABILITIES = 0x08;

    private static final int FEATURE_TYPE_LIST = 0x10;

    private static final int ALL = 0x20 | SERVICE_IDENTIFICATION | SERVICE_PROVIDER | OPERATIONS_METADATA
            | FILTER_CAPABILITIES | FEATURE_TYPE_LIST;

    /**
     * constructor
     */
    public GetCapabilitiesDAO() {
        super(WfsConstants.WFS);
    }

    @Override
    protected Set<String> getExtensionSections(String arg0, String arg1) throws OwsExceptionReport {
        return Collections.emptySet();
    }

    @Override
    public GetCapabilitiesResponse getCapabilities(GetCapabilitiesRequest request) throws OwsExceptionReport {
        final GetCapabilitiesResponse response = new GetCapabilitiesResponse();
        response.setService(WfsConstants.WFS);
        response.setVersion(getVersionParameter(request));

        final Set<String> availableExtensionSections =
                getExtensionSections(response.getService(), response.getVersion());
        final Set<String> requestedExtensionSections = new HashSet<String>(availableExtensionSections.size());
        final int requestedSections =
                identifyRequestedSections(request, response, availableExtensionSections, requestedExtensionSections);

        final WfsCapabilities wfsCapabilities = new WfsCapabilities(response.getVersion());
        addSectionSpecificContent(response, requestedExtensionSections, requestedSections, wfsCapabilities);
        response.setCapabilities(wfsCapabilities);

        return response;
    }

    private void addSectionSpecificContent(final GetCapabilitiesResponse response,
            final Set<String> requestedExtensionSections, final int sections, final WfsCapabilities wfsCapabilities)
            throws OwsExceptionReport {
        if (isServiceIdentificationSectionRequested(sections)) {
            wfsCapabilities.setServiceIdentification(getServiceIdentification(response.getVersion()));
        }
        if (isServiceProviderSectionRequested(sections)) {
            wfsCapabilities.setServiceProvider(getConfigurator().getServiceProvider());
        }
        if (isOperationsMetadataSectionRequested(sections)) {
            wfsCapabilities.setOperationsMetadata(getOperationsMetadataForOperations(response.getService(),
                    response.getVersion()));
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
                            WfsConstants.GetCapabilitiesParams.Section.name());
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
        if (!request.isSetVersion()) {
            if (request.isSetAcceptVersions()) {
                for (final String acceptedVersion : request.getAcceptVersions()) {
                    if (ServiceOperatorRepository.getInstance().isVersionSupported(request.getService(),
                            acceptedVersion)) {
                        return acceptedVersion;
                    }
                }
            } else {
                for (final String supportedVersion : ServiceOperatorRepository.getInstance().getSupportedVersions(
                        request.getService())) {
                    return supportedVersion;
                }
            }
        } else {
            return request.getVersion();
        }

        throw new VersionNegotiationFailedException().withMessage(
                "The requested '%s' values are not supported by this service!",
                WfsConstants.GetCapabilitiesParams.AcceptVersions);
    }

    private SosServiceIdentification getServiceIdentification(final String version) throws OwsExceptionReport {
        // TODO rename SosServiceIdentification to OwsServiceIdentification
        final SosServiceIdentification serviceIdentification = getConfigurator().getServiceIdentification();
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
     * @param service
     *            Requested service
     * @param version
     *            Requested service version
     * @return OperationsMetadata for all operations supported by the requested
     *         service and version
     * @throws OwsExceptionReport
     *             If an error occurs
     */
    private OwsOperationsMetadata getOperationsMetadataForOperations(final String service, final String version)
            throws OwsExceptionReport {

        final OwsOperationsMetadata operationsMetadata = new OwsOperationsMetadata();
        operationsMetadata.addCommonValue(OWSConstants.RequestParams.service.name(),
                new OwsParameterValuePossibleValues(WfsConstants.WFS));
        operationsMetadata.addCommonValue(
                OWSConstants.RequestParams.version.name(),
                new OwsParameterValuePossibleValues(ServiceOperatorRepository.getInstance().getSupportedVersions(
                        service)));

        // FIXME: OpsMetadata for InsertSensor, InsertObservation SOS 2.0
        final Set<RequestOperatorKey> requestOperatorKeyTypes =
                RequestOperatorRepository.getInstance().getActiveRequestOperatorKeys();
        final List<OwsOperation> opsMetadata = new ArrayList<OwsOperation>(requestOperatorKeyTypes.size());
        for (final RequestOperatorKey requestOperatorKeyType : requestOperatorKeyTypes) {
            if (requestOperatorKeyType.getServiceOperatorKey().getVersion().equals(version)) {
                final OwsOperation operationMetadata =
                        RequestOperatorRepository.getInstance().getRequestOperator(requestOperatorKeyType)
                                .getOperationMetadata(service, version);
                if (operationMetadata != null) {
                    opsMetadata.add(operationMetadata);
                }
            }
        }
        operationsMetadata.setOperations(opsMetadata);

        if (OwsExtendedCapabilitiesRepository.getInstance().hasExtendedCapabilitiesFor(service)) {
            operationsMetadata.setExtendedCapabilities(OwsExtendedCapabilitiesRepository.getInstance()
                    .getExtendedCapabilities(service));
        }

        return operationsMetadata;
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
        getConformance(filterCapabilities);
        // // !!! Modify methods addicted to your implementation !!!
        // if (version.equals(Sos1Constants.SERVICEVERSION)) {
        // getScalarFilterCapabilities(filterCapabilities);
        // }
        getScalarFilterCapabilities(filterCapabilities);
        getSpatialFilterCapabilities(filterCapabilities, version);
        getTemporalFilterCapabilities(filterCapabilities, version);

        return filterCapabilities;
    }

    private void getConformance(FilterCapabilities filterCapabilities) {
        // set Query conformance class
        filterCapabilities.addConformance(new OwsDomainType(ConformanceClassConstraintNames.ImplementsQuery.name(),
                new OwsNoValues(), FALSE));
        // set Ad hoc query conformance class
        filterCapabilities.addConformance(new OwsDomainType(ConformanceClassConstraintNames.ImplementsAdHocQuery
                .name(), new OwsNoValues(), TRUE));
        // set Functions conformance class
        filterCapabilities.addConformance(new OwsDomainType(
                ConformanceClassConstraintNames.ImplementsFunctions.name(), new OwsNoValues(), FALSE));
        // set Resource Identification conformance class
        filterCapabilities.addConformance(new OwsDomainType(ConformanceClassConstraintNames.ImplementsResourceld
                .name(), new OwsNoValues(), FALSE));
        // set Minimum Standard Filter conformance class
        filterCapabilities.addConformance(new OwsDomainType(
                ConformanceClassConstraintNames.ImplementsMinStandardFilter.name(), new OwsNoValues(), FALSE));
        // set Standard Filter conformance class
        filterCapabilities.addConformance(new OwsDomainType(ConformanceClassConstraintNames.ImplementsStandardFilter
                .name(), new OwsNoValues(), FALSE));
        // set Minimum Spatial Filter conformance class
        filterCapabilities.addConformance(new OwsDomainType(ConformanceClassConstraintNames.ImplementsMinSpatialFilter
                .name(), new OwsNoValues(), TRUE));
        // set Spatial Filter conformance class
        filterCapabilities.addConformance(new OwsDomainType(ConformanceClassConstraintNames.ImplementsSpatialFilter
                .name(), new OwsNoValues(), FALSE));
        // set Minimum Temporal Filter conformance class
        filterCapabilities.addConformance(new OwsDomainType(
                ConformanceClassConstraintNames.ImplementsMinTemporalFilter.name(), new OwsNoValues(), TRUE));
        // set Temporal Filter conformance class
        filterCapabilities.addConformance(new OwsDomainType(ConformanceClassConstraintNames.ImplementsTemporalFilter
                .name(), new OwsNoValues(), FALSE));
        // set Version navigation conformance class
        filterCapabilities.addConformance(new OwsDomainType(ConformanceClassConstraintNames.ImplementsVersionNav
                .name(), new OwsNoValues(), FALSE));
        // set Sorting conformance class
        filterCapabilities.addConformance(new OwsDomainType(ConformanceClassConstraintNames.ImplementsSorting.name(),
                new OwsNoValues(), FALSE));
        // set Extended Operators conformance class
        filterCapabilities.addConformance(new OwsDomainType(
                ConformanceClassConstraintNames.ImplementsExtendedOperators.name(), new OwsNoValues(), FALSE));
        // set Minimum XPath conformance class
        filterCapabilities.addConformance(new OwsDomainType(ConformanceClassConstraintNames.ImplementsMinimumXPath
                .name(), new OwsNoValues(), FALSE));
        // set Schema Element Function conformance class
        filterCapabilities.addConformance(new OwsDomainType(
                ConformanceClassConstraintNames.ImplementsSchemaElementFunc.name(), new OwsNoValues(), FALSE));
    }

    /**
     * Set SpatialFilterCapabilities to FilterCapabilities
     * 
     * @param filterCapabilities
     *            FilterCapabilities
     * @param version
     *            SOS version
     */
    private void getSpatialFilterCapabilities(final FilterCapabilities filterCapabilities, final String version) {

        // set GeometryOperands
        final List<QName> operands = new LinkedList<QName>();
        operands.add(GmlConstants.QN_ENVELOPE_32);

        filterCapabilities.setSpatialOperands(operands);

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
        final List<QName> operands = new ArrayList<QName>(2);
        operands.add(GmlConstants.QN_TIME_PERIOD_32);
        operands.add(GmlConstants.QN_TIME_INSTANT_32);

        filterCapabilities.setTemporalOperands(operands);

        // set TemporalOperators
        final SetMultiMap<TimeOperator, QName> ops = MultiMaps.newSetMultiMap(TimeOperator.class);
        ops.add(TimeOperator.TM_Equals, GmlConstants.QN_TIME_INSTANT_32);
        ops.add(TimeOperator.TM_During, GmlConstants.QN_TIME_PERIOD_32);
        filterCapabilities.setTempporalOperators(ops);
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
        final List<ComparisonOperator> comparisonOperators = new ArrayList<ComparisonOperator>(8);
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

    private Collection<WfsFeatureType> getFeatureTypeList() {
        String featureType = "observatons";
        WfsFeatureType wfsFeatureType = new WfsFeatureType(getName(featureType), getWfsCapabilitiesCrs(featureType));
        wfsFeatureType.setTitles(getTitles(featureType));
        wfsFeatureType.setAbstracts(getAbstracts(featureType));
        wfsFeatureType.setKeywords(getKeywods(featureType));
        wfsFeatureType.setOutputFormats(getOutputFormats(featureType));
        wfsFeatureType.addWgs84BoundingBoxes(wgs84BoundingBoxes(featureType));
        wfsFeatureType.setExtendedDescription(extendedDescription(featureType));
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

    private WfsCapabilitiesCrs getWfsCapabilitiesCrs(String featureType) {
        String defaultCrs = OGCConstants.URN_DEF_CRS_EPSG + GeometryHandler.getInstance().getDefaultEPSG();
        return new WfsCapabilitiesCrs(defaultCrs);
    }

    private Set<String> getOutputFormats(String featureType) {
        return Sets.newHashSet(MediaTypes.APPLICTION_OM_20.toString());
    }

    private SosEnvelope wgs84BoundingBoxes(String featureType) {
        return getCache().getGlobalEnvelope();
    }

    private WfsExtendedDescription extendedDescription(String featureType) {
        WfsElement element = getPhenomenonTimeElement(featureType);
        if (element != null) {
            return new WfsExtendedDescription(getPhenomenonTimeElement(featureType));
        }
        return null;
    }

    private WfsElement getPhenomenonTimeElement(String featureType) {
        TimePeriod timePeriod = new TimePeriod(getCache().getMinPhenomenonTime(), getCache().getMaxPhenomenonTime());
        if (!timePeriod.isEmpty()) {
            OwsMetadata metadata = new OwsMetadata();
            metadata.setTitle("Time for which observations are available");
            return new WfsElement("TemporalExtend", GmlConstants.QN_TIME_PERIOD_32, metadata, new WfsValueList(timePeriod));
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
