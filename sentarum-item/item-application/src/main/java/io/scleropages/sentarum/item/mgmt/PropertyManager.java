/**
 * Copyright 2001-2005 The Apache Software Foundation.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.scleropages.sentarum.item.mgmt;

import io.scleropages.sentarum.item.property.entity.ConstraintEntity;
import io.scleropages.sentarum.item.property.entity.GroupedMetaEntity;
import io.scleropages.sentarum.item.property.entity.GroupedMetaEntryEntity;
import io.scleropages.sentarum.item.property.entity.PropertyMetaEntity;
import io.scleropages.sentarum.item.property.entity.SourceValueEntity;
import io.scleropages.sentarum.item.property.entity.ValuesSourceEntity;
import io.scleropages.sentarum.item.property.entity.mapper.GroupedMetaEntityMapper;
import io.scleropages.sentarum.item.property.entity.mapper.PropertyMetaEntityMapper;
import io.scleropages.sentarum.item.property.entity.mapper.SourceValueEntityMapper;
import io.scleropages.sentarum.item.property.model.Constraint;
import io.scleropages.sentarum.item.property.model.GroupedPropertyMetadata;
import io.scleropages.sentarum.item.property.model.Input;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.ValuesSource;
import io.scleropages.sentarum.item.property.model.impl.GroupedPropertyMetadataModel;
import io.scleropages.sentarum.item.property.model.impl.PropertyMetadataModel;
import io.scleropages.sentarum.item.property.model.impl.SourceValueModel;
import io.scleropages.sentarum.item.property.model.vs.AbstractValuesSource;
import io.scleropages.sentarum.item.property.model.vs.HttpGetValuesSource;
import io.scleropages.sentarum.item.property.model.vs.NativeValuesSource;
import io.scleropages.sentarum.item.property.model.vs.SqlQueryValuesSource;
import io.scleropages.sentarum.item.property.repo.ConstraintRepository;
import io.scleropages.sentarum.item.property.repo.GroupedMetaEntryRepository;
import io.scleropages.sentarum.item.property.repo.GroupedMetaRepository;
import io.scleropages.sentarum.item.property.repo.PropertyMetaRepository;
import io.scleropages.sentarum.item.property.repo.SourceValueRepository;
import io.scleropages.sentarum.item.property.repo.ValuesSourceRepository;
import org.apache.commons.collections.ComparatorUtils;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.GenericManager;
import org.scleropages.crud.dao.orm.SearchFilter;
import org.scleropages.crud.dao.orm.jpa.entity.EntityAware;
import org.scleropages.crud.exception.BizError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 属性元数据({@link io.scleropages.sentarum.item.property.model.PropertyMetadata}) 通用（原子）功能管理器.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Service
@Validated
@BizError("10")
public class PropertyManager implements GenericManager<PropertyMetadataModel, Long, PropertyMetaEntityMapper> {


    private PropertyMetaRepository propertyMetaRepository;
    private ValuesSourceRepository valuesSourceRepository;
    private SourceValueRepository sourceValueRepository;
    private ConstraintRepository constraintRepository;
    private GroupedMetaRepository groupedMetaRepository;
    private GroupedMetaEntryRepository groupedMetaEntryRepository;


    @Validated({PropertyMetadataModel.Create.class})
    @Transactional
    @BizError("01")
    public void createPropertyMetadata(@Valid PropertyMetadataModel propertyMetadataModel) {
        propertyMetadataModel.assertModelValid();
        PropertyMetaEntity propertyMetadataEntity = getModelMapper().mapForSave(propertyMetadataModel);
        propertyMetaRepository.save(propertyMetadataEntity);
    }

    @Validated({PropertyMetadataModel.Update.class})
    @Transactional
    @BizError("02")
    public void savePropertyMetadata(@Valid PropertyMetadataModel propertyMetadataModel) {
        propertyMetadataModel.assertModelValid();
        Long id = propertyMetadataModel.getId();
        PropertyMetaEntity propertyMetaEntity = propertyMetaRepository.get(id).orElseThrow(() -> new IllegalArgumentException("no property meta data found: " + id));
        getModelMapper().mapForUpdate(propertyMetadataModel, propertyMetaEntity);
        propertyMetaRepository.save(propertyMetaEntity);
    }

    @Validated({NativeValuesSource.Create.class})
    @Transactional
    @BizError("04")
    public void createValuesSource(@Valid NativeValuesSource valuesSource) {
        createValuesSourceInternal(valuesSource);
    }

    @Validated({SqlQueryValuesSource.Create.class})
    @Transactional
    @BizError("05")
    public void createValuesSource(@Valid SqlQueryValuesSource valuesSource) {
        createValuesSourceInternal(valuesSource);
    }

    @Validated({HttpGetValuesSource.Create.class})
    @Transactional
    @BizError("06")
    public void createValuesSource(@Valid HttpGetValuesSource valuesSource) {
        createValuesSourceInternal(valuesSource);
    }

    @Transactional
    @BizError("07")
    public void bindValuesSourceToPropertyMetadata(Long valueSourceId, Long propertyMetaId) {
        Assert.notNull(valueSourceId, "valueSourceId must not be null.");
        Assert.notNull(propertyMetaId, "propertyMetaId must not be null.");
        PropertyMetaEntity propertyMetaEntity = propertyMetaRepository.get(propertyMetaId).orElseThrow(() -> new IllegalArgumentException("no property meta data found: " + propertyMetaId));
        Assert.isTrue(Input.InputType.isCheckInput(propertyMetaEntity.getInput()), "not support values source for this property metadata (make sure it's a check input.): " + propertyMetaId);
        ValuesSourceEntity valuesSourceEntity = valuesSourceRepository.get(valueSourceId).orElseThrow(() -> new IllegalArgumentException("no values source found: " + valueSourceId));
        propertyMetaEntity.setValuesSource(valuesSourceEntity);
        propertyMetaRepository.save(propertyMetaEntity);
    }

    @Validated({NativeValuesSource.Update.class})
    @Transactional
    @BizError("08")
    public void saveValuesSource(@Valid NativeValuesSource valuesSource) {
        saveValuesSourceInternal(valuesSource);
    }

    @Validated({SqlQueryValuesSource.Update.class})
    @Transactional
    @BizError("09")
    public void saveValuesSource(@Valid SqlQueryValuesSource valuesSource) {
        saveValuesSourceInternal(valuesSource);
    }

    @Validated({HttpGetValuesSource.Update.class})
    @Transactional
    @BizError("10")
    public void saveValuesSource(@Valid HttpGetValuesSource valuesSource) {
        saveValuesSourceInternal(valuesSource);
    }


    @Validated({SourceValueModel.Create.class})
    @Transactional
    @BizError("11")
    public void createSourceValue(@Valid SourceValueModel sourceValueModel) {
        ValuesSourceEntity valuesSourceEntity = valuesSourceRepository.get(sourceValueModel.getValuesSourceId()).orElseThrow(() -> new IllegalArgumentException("no values source found: " + sourceValueModel.getValuesSourceId()));
        SourceValueEntity sourceValueEntity = getModelMapper(SourceValueEntityMapper.class).mapForSave(sourceValueModel);
        sourceValueEntity.setValuesSource(valuesSourceEntity);
        sourceValueRepository.save(sourceValueEntity);
    }

    @Validated({SourceValueModel.Update.class})
    @Transactional
    @BizError("12")
    public void saveSourceValue(@Valid SourceValueModel sourceValueModel) {
        SourceValueEntity sourceValueEntity = sourceValueRepository.get(sourceValueModel.getId()).orElseThrow(() -> new IllegalArgumentException("no source value found: " + sourceValueModel.getId()));
        getModelMapper(SourceValueEntityMapper.class).mapForUpdate(sourceValueModel, sourceValueEntity);
        sourceValueRepository.save(sourceValueEntity);
    }

    @Transactional
    @BizError("13")
    public void deleteSourceValue(Long sourceValueId) {
        Assert.notNull(sourceValueId, "SourceValue id must not be null.");
        sourceValueRepository.deleteById(sourceValueId);
    }

    @Validated({Constraint.Create.class})
    @Transactional
    @BizError("14")
    public void createConstraint(Long propertyMetaId, @Valid Constraint constraint) {
        Assert.notNull(propertyMetaId, "property metadata id must not be null.");
        constraint.assertValid();
        PropertyMetaEntity propertyMetaEntity = propertyMetaRepository.get(propertyMetaId).orElseThrow(() -> new IllegalArgumentException("no property meta data found: " + propertyMetaId));
        ConstraintEntity constraintEntity = new ConstraintEntity();
        constraintEntity.setName(constraint.getName());
        constraintEntity.setRule(JsonMapper2.toJson(constraint));
        constraintEntity.setPropertyMeta(propertyMetaEntity);
        constraintRepository.save(constraintEntity);
    }

    @Validated({Constraint.Update.class})
    @Transactional
    @BizError("15")
    public void saveConstraint(@Valid Constraint constraint) {
        ConstraintEntity constraintEntity = constraintRepository.get(constraint.getId()).orElseThrow(() -> new IllegalArgumentException("no constraint found: " + constraint.getId()));
        Assert.isTrue(Objects.equals(constraintEntity.getName(), constraint.getName()), "constraint name(type) not allowed change.");
        constraintEntity.setRule(JsonMapper2.toJson(constraint));
        constraintRepository.save(constraintEntity);
    }

    @Transactional
    @BizError("16")
    public void deleteConstraint(Long constraintId) {
        constraintRepository.deleteById(constraintId);
    }

    @Validated({GroupedPropertyMetadataModel.Create.class})
    @Transactional
    @BizError("17")
    public void createGroupedPropertyMetadata(GroupedPropertyMetadataModel groupedPropertyMetadataModel) {
        GroupedMetaEntity groupedMetaEntity = getModelMapper(GroupedMetaEntityMapper.class).mapForSave(groupedPropertyMetadataModel);
        groupedMetaRepository.save(groupedMetaEntity);
    }

    @Validated({GroupedPropertyMetadataModel.Update.class})
    @Transactional
    @BizError("18")
    public void saveGroupedPropertyMetadata(GroupedPropertyMetadataModel groupedPropertyMetadataModel) {
        GroupedMetaEntity groupedMetaEntity = groupedMetaRepository.get(groupedPropertyMetadataModel.getId()).orElseThrow(() -> new IllegalArgumentException("no grouped property metadata found: " + groupedPropertyMetadataModel.getId()));
        getModelMapper(GroupedMetaEntityMapper.class).mapForUpdate(groupedPropertyMetadataModel, groupedMetaEntity);
    }

    @Transactional
    @BizError("19")
    public void deleteGroupedPropertyMetadata(Long groupedPropertyMetadataId) {
        Assert.notNull(groupedPropertyMetadataId, "groupedPropertyMetadataId must not be null.");
        groupedMetaRepository.deleteById(groupedPropertyMetadataId);
    }

    @Transactional
    @BizError("20")
    public void addPropertyMetadataToGroup(Long groupedPropertyMetadataId, Long propertyMetadataId, Float order) {
        Assert.notNull(groupedPropertyMetadataId, "groupedPropertyMetadataId must not be null.");
        Assert.notNull(propertyMetadataId, "propertyMetadataId must not be null.");
        Assert.notNull(order, "order must not be null.");
        GroupedMetaEntity groupedMetaEntity = groupedMetaRepository.get(groupedPropertyMetadataId).orElseThrow(() -> new IllegalArgumentException("no grouped property metadata found: " + groupedPropertyMetadataId));
        PropertyMetaEntity propertyMetaEntity = propertyMetaRepository.get(propertyMetadataId).orElseThrow(() -> new IllegalArgumentException("no property meta data found: " + propertyMetadataId));
        GroupedMetaEntryEntity groupedMetaEntryEntity = new GroupedMetaEntryEntity();
        groupedMetaEntryEntity.setGroupedMeta(groupedMetaEntity);
        groupedMetaEntryEntity.setPropertyMetadata(propertyMetaEntity);
        groupedMetaEntryEntity.setOrder(order);
        groupedMetaEntryRepository.save(groupedMetaEntryEntity);
    }

    @Transactional
    @BizError("21")
    public void removePropertyMetadataToGroup(Long groupedPropertyMetadataId, Long propertyMetadataId) {
        Assert.notNull(groupedPropertyMetadataId, "groupedPropertyMetadataId must not be null.");
        Assert.notNull(propertyMetadataId, "propertyMetadataId must not be null.");
        groupedMetaEntryRepository.deleteByGroupedMeta_IdAndPropertyMetadata_Id(groupedPropertyMetadataId, propertyMetadataId);
    }


    @Transactional(readOnly = true)
    @BizError("50")
    public Page<PropertyMetadata> findPropertyMetadataPage(Map<String, SearchFilter> searchFilters, Pageable pageable) {
        return propertyMetaRepository.findPage(searchFilters, pageable).map(propertyMetaEntity -> getModelMapper().mapForRead(propertyMetaEntity));
    }

    @Transactional(readOnly = true)
    @BizError("55")
    public PropertyMetadata getPropertyMetadata(Long propertyMetadataId) {
        Assert.notNull(propertyMetadataId, "propertyMetadataId must not be null.");
        PropertyMetaEntity propertyMetaEntity = propertyMetaRepository.get(propertyMetadataId).orElseThrow(() -> new IllegalArgumentException("no property meta data found: " + propertyMetadataId));
        return createPropertyMetadataFromEntity(propertyMetaEntity);
    }

    @Transactional(readOnly = true)
    @BizError("56")
    public PropertyMetadata getPropertyMetadataByName(String name) {
        Assert.hasText(name, "name must not be empty text.");
        PropertyMetaEntity propertyMetaEntity = propertyMetaRepository.getByName(name);
        if (null == propertyMetaEntity)
            return null;
        return createPropertyMetadataFromEntity(propertyMetaEntity);
    }

    @Transactional(readOnly = true)
    @BizError("56")
    public PropertyMetadata getPropertyMetadataDetail(Long propertyMetadataId) {
        Assert.notNull(propertyMetadataId, "propertyMetadataId must not be null.");
        return getModelMapper().mapForRead(propertyMetaRepository.getDetailsById(propertyMetadataId));
    }


    @Transactional(readOnly = true)
    @BizError("60")
    public Page<ValuesSource> findValuesSourcePage(Map<String, SearchFilter> searchFilters, Pageable pageable) {
        return valuesSourceRepository.findPage(searchFilters, pageable).map(entity -> getModelMapper().toValuesSource(entity));
    }

    @Transactional(readOnly = true)
    @BizError("65")
    public List<Constraint> findAllConstraintByPropertyMeta(Long propertyMetaId) {
        Assert.notNull(propertyMetaId, "propertyMetaId must not be null.");
        return constraintRepository.findAllByPropertyMeta_Id(propertyMetaId).stream().map(constraintEntity -> getModelMapper().toConstraint(constraintEntity)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @BizError("70")
    public ValuesSource getValuesSource(Long valuesSourceId) {
        Assert.notNull(valuesSourceId, "valuesSourceId must not be null.");
        ValuesSourceEntity valuesSourceEntity = valuesSourceRepository.get(valuesSourceId).orElseThrow(() -> new IllegalArgumentException("no ValuesSource found: " + valuesSourceId));
        return getModelMapper().toValuesSource(valuesSourceEntity);
    }

    @Transactional(readOnly = true)
    @BizError("71")
    public ValuesSource getValuesSourceByName(String name) {
        Assert.hasText(name, "name must not be empty text.");
        return getModelMapper().toValuesSource(valuesSourceRepository.getByName(name));
    }

    @Transactional(readOnly = true)
    @BizError("75")
    public Page<? extends ValuesSource.SourceValue> findSourceValuePage(SourceValueModel searchModel, Pageable pageable) {
        ValuesSource valuesSource = getValuesSource(searchModel.getValuesSourceId());
        return valuesSource.readValues(searchModel, pageable);
    }

    @Transactional(readOnly = true)
    @BizError("80")
    public Page<GroupedPropertyMetadata> findGroupedPropertyMetadataPage(Map<String, SearchFilter> searchFilters, Pageable pageable) {
        return groupedMetaRepository.findPage(searchFilters, pageable).map(entity -> getModelMapper(GroupedMetaEntityMapper.class).mapForRead(entity));
    }

    @Transactional(readOnly = true)
    @BizError("85")
    public GroupedPropertyMetadata getGroupedPropertyMetadata(Long id) {
        return getModelMapper(GroupedMetaEntityMapper.class).mapForRead(groupedMetaRepository.get(id).orElseThrow(() -> new IllegalArgumentException("not grouped property metadata found: " + id)));
    }

    @Transactional(readOnly = true)
    @BizError("90")
    public List<? extends PropertyMetadata> findAllPropertyMetadataInGroup(Long groupId) {
        GroupedMetaEntity groupEntity = groupedMetaRepository.getById(groupId);
        Assert.notNull(groupEntity, "not grouped property metadata found: " + groupId);
        List<GroupedMetaEntryEntity> entries = groupEntity.getEntries();
        List<PropertyMetaEntity> collect = entries.stream().sorted((o1, o2) -> ComparatorUtils.naturalComparator().compare(o1.getOrder(), o2.getOrder())).map(entryEntity -> entryEntity.getPropertyMetadata()).collect(Collectors.toList());
        return (List<? extends PropertyMetadata>) getModelMapper().mapForReads(collect);
    }

    protected void awarePropertyMetaEntity(Long id, EntityAware entityAware) {
        entityAware.setEntity(propertyMetaRepository.get(id).orElseThrow(() -> new IllegalArgumentException("no property meta found: " + id)));
    }


    protected void createValuesSourceInternal(ValuesSource valuesSource) {
        ValuesSourceEntity valuesSourceEntity = getModelMapper().toValuesSourceEntity(valuesSource);
        valuesSourceRepository.save(valuesSourceEntity);
    }

    protected void saveValuesSourceInternal(AbstractValuesSource valuesSource) {
        ValuesSourceEntity update = valuesSourceRepository.get(valuesSource.getId()).orElseThrow(() -> new IllegalArgumentException("no values source found: " + valuesSource.getId()));
        Assert.isTrue(Objects.equals(valuesSource.valuesSourceType().getOrdinal(), update.getValuesSourceType()), "values source type not allowed change.");
        ValuesSourceEntity changed = getModelMapper().toValuesSourceEntity(valuesSource);
        update.setPayload(changed.getPayload());
    }

    protected PropertyMetadata createPropertyMetadataFromEntity(PropertyMetaEntity propertyMetaEntity) {
        if (Input.InputType.isCheckInput(propertyMetaEntity.getInput())) {//如果属性的input 为check类型，加载其关联 ValuesSource
            propertyMetaEntity.getValuesSource();
        }
        PropertyMetadataModel propertyMetadataModel = getModelMapper().mapForRead(propertyMetaEntity);
        propertyMetadataModel.setConstraints(findAllConstraintByPropertyMeta(propertyMetadataModel.id()));
        return propertyMetadataModel;
    }


    @Autowired
    public void setPropertyMetaRepository(PropertyMetaRepository propertyMetaRepository) {
        this.propertyMetaRepository = propertyMetaRepository;
    }

    @Autowired
    public void setValuesSourceRepository(ValuesSourceRepository valuesSourceRepository) {
        this.valuesSourceRepository = valuesSourceRepository;
    }

    @Autowired
    public void setSourceValueRepository(SourceValueRepository sourceValueRepository) {
        this.sourceValueRepository = sourceValueRepository;
    }

    @Autowired
    public void setConstraintRepository(ConstraintRepository constraintRepository) {
        this.constraintRepository = constraintRepository;
    }

    @Autowired
    public void setGroupedMetaRepository(GroupedMetaRepository groupedMetaRepository) {
        this.groupedMetaRepository = groupedMetaRepository;
    }

    @Autowired
    public void setGroupedMetaEntryRepository(GroupedMetaEntryRepository groupedMetaEntryRepository) {
        this.groupedMetaEntryRepository = groupedMetaEntryRepository;
    }

}
