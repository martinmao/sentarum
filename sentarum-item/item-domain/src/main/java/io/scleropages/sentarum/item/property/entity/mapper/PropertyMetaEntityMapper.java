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
package io.scleropages.sentarum.item.property.entity.mapper;

import io.scleropages.sentarum.item.property.entity.ConstraintEntity;
import io.scleropages.sentarum.item.property.entity.PropertyMetaEntity;
import io.scleropages.sentarum.item.property.entity.ValuesSourceEntity;
import io.scleropages.sentarum.item.property.model.Constraint;
import io.scleropages.sentarum.item.property.model.Input;
import io.scleropages.sentarum.item.property.model.PropertyMetadata;
import io.scleropages.sentarum.item.property.model.PropertyValueType;
import io.scleropages.sentarum.item.property.model.ValuesSource;
import io.scleropages.sentarum.item.property.model.impl.PropertyMetadataModel;
import io.scleropages.sentarum.item.property.model.vs.AbstractValuesSource;
import io.scleropages.sentarum.item.property.model.vs.DataValuesSource;
import io.scleropages.sentarum.item.property.model.vs.HttpGetValuesSource;
import io.scleropages.sentarum.item.property.model.vs.SqlQueryValuesSource;
import org.mapstruct.Mapper;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.crud.ModelMapper;
import org.springframework.util.Assert;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Mapper(config = ModelMapper.DefaultConfig.class)
public interface PropertyMetaEntityMapper extends ModelMapper<PropertyMetaEntity, PropertyMetadataModel> {

    default Integer toOrdinal(PropertyValueType propertyValueType) {
        return propertyValueType.getOrdinal();
    }

    default PropertyValueType toPropertyValueType(Integer ordinal) {
        return PropertyValueType.getByOrdinal(ordinal);
    }


    default Integer toOrdinal(PropertyMetadata.PropertyStructureType propertyStructureType) {
        return propertyStructureType.ordinal();
    }

    default PropertyMetadata.PropertyStructureType toPropertyStructureType(Integer ordinal) {
        return PropertyMetadata.PropertyStructureType.getByOrdinal(ordinal);
    }


    default Integer toOrdinal(Input input) {
        return input.getType().getOrdinal();
    }

    default Input toInput(Integer ordinal) {
        try {
            return (Input) Input.InputType.getByOrdinal(ordinal).getInputClass().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("failure to create input instance by input type: " + ordinal, e);
        }
    }

    default ConstraintEntity toConstraintEntity(Constraint constraint) {
        ConstraintEntity constraintEntity = new ConstraintEntity();
        constraintEntity.setName(constraint.getName());
        constraintEntity.setRule(JsonMapper2.toJson(constraint));
        return constraintEntity;
    }

    default Constraint toConstraint(ConstraintEntity constraintEntity) {
        String name = constraintEntity.getName();
        try {
            return JsonMapper2.fromJson(constraintEntity.getRule(), Constraint.getConstraintImplementationClass(name));
        } catch (Exception e) {
            throw new IllegalStateException("failure to create constraint by entity: " + constraintEntity.getId(), e);
        }
    }


    default ValuesSource toValuesSource(ValuesSourceEntity valuesSourceEntity) {
        Integer valuesSourceType = valuesSourceEntity.getValuesSourceType();
        ValuesSource.ValuesSourceType sourceType = ValuesSource.ValuesSourceType.getByOrdinal(valuesSourceType);
        Assert.notNull(sourceType, "no ValuesSourceType found by ordinal: " + valuesSourceType);
        Class implementationClass = sourceType.getImplementationClass();
        sourceType.getImplementationClass();
        AbstractValuesSource valuesSource;
        try {
            valuesSource = (AbstractValuesSource) implementationClass.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException("failure to create ValuesSource of :" + implementationClass, e);
        }
        valuesSource.setId(valuesSourceEntity.getId());
        if (valuesSource instanceof DataValuesSource) {
            DataValuesSource dataValuesSource = (DataValuesSource) valuesSource;
            dataValuesSource.setFetchSize(valuesSourceEntity.getFetchSize());
        }
        if (valuesSource instanceof SqlQueryValuesSource) {
            SqlQueryValuesSource sqlQueryValuesSource = (SqlQueryValuesSource) valuesSource;
            sqlQueryValuesSource.setQuery(valuesSourceEntity.getCommand());
        }
        if (valuesSource instanceof HttpGetValuesSource) {
            HttpGetValuesSource httpGetValuesSource = (HttpGetValuesSource) valuesSource;
            httpGetValuesSource.setUrl(valuesSourceEntity.getCommand());
            httpGetValuesSource.setHttpHeaders(JsonMapper2.fromJson(valuesSourceEntity.getParametersPayLoad()));
        }
        return valuesSource;
    }

    default ValuesSourceEntity toValuesSourceEntity(ValuesSource valuesSource) {
        ValuesSourceEntity valuesSourceEntity = new ValuesSourceEntity();
        valuesSourceEntity.setValuesSourceType(valuesSource.valuesSourceType().getOrdinal());
        if (valuesSource instanceof DataValuesSource) {
            valuesSourceEntity.setFetchSize(((DataValuesSource) valuesSource).getFetchSize());
        }
        if (valuesSource instanceof SqlQueryValuesSource) {
            String query = ((SqlQueryValuesSource) valuesSource).getQuery();
            Assert.hasText(query, "query must not empty.");
            valuesSourceEntity.setCommand(query);
        }
        if (valuesSource instanceof HttpGetValuesSource) {
            String url = ((HttpGetValuesSource) valuesSource).getUrl();
            Assert.hasText(url, "url must not empty.");
            valuesSourceEntity.setCommand(url);
            valuesSourceEntity.setParametersPayLoad(JsonMapper2.toJson(((HttpGetValuesSource) valuesSource).getHttpHeaders()));
        }
        return valuesSourceEntity;
    }
}
