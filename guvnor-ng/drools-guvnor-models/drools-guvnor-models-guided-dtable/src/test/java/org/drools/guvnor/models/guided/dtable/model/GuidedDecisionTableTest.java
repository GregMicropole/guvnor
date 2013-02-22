/*
 * Copyright 2010 JBoss Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.drools.guvnor.models.guided.dtable.model;

import org.drools.guvnor.models.guided.dtable.model.ActionInsertFactCol52;
import org.drools.guvnor.models.guided.dtable.model.ActionSetFieldCol52;
import org.drools.guvnor.models.guided.dtable.model.AttributeCol52;
import org.drools.guvnor.models.guided.dtable.model.ConditionCol52;
import org.drools.guvnor.models.guided.dtable.model.DescriptionCol52;
import org.drools.guvnor.models.guided.dtable.model.GuidedDecisionTable52;
import org.drools.guvnor.models.guided.dtable.model.MetadataCol52;
import org.drools.guvnor.models.guided.dtable.model.Pattern52;
import org.drools.guvnor.models.guided.dtable.model.RowNumberCol52;
import org.junit.Assert;
import org.junit.Test;
import org.kie.guvnor.datamodel.backend.server.builder.packages.PackageDataModelOracleBuilder;
import org.kie.guvnor.datamodel.backend.server.builder.projects.ProjectDefinitionBuilder;
import org.kie.guvnor.datamodel.model.FieldAccessorsAndMutators;
import org.kie.guvnor.datamodel.model.ModelField;
import org.kie.guvnor.datamodel.oracle.DataModelOracle;
import org.drools.guvnor.models.commons.oracle.DataType;
import org.kie.guvnor.datamodel.oracle.ProjectDefinition;
import org.kie.guvnor.guided.dtable.model.util.GuidedDecisionTableUtils;
import org.drools.guvnor.models.commons.rule.BaseSingleFieldConstraint;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GuidedDecisionTableTest {

    @Test
    public void testValueLists() {
        final GuidedDecisionTable52 dt = new GuidedDecisionTable52();
        final DataModelOracle oracle = PackageDataModelOracleBuilder.newDataModelBuilder()
                .addEnum( "Driver",
                          "name",
                          new String[]{ "bob", "michael" } )
                .addEnum( "Person",
                          "rating",
                          new String[]{ "1", "2" } )
                .build();
        final GuidedDecisionTableUtils utils = new GuidedDecisionTableUtils( oracle,
                                                                             dt );

        final Map<String, String> currentValueMap = new HashMap<String, String>();

        // add cols for LHS
        final ConditionCol52 c1 = new ConditionCol52();
        final Pattern52 p1 = new Pattern52();
        p1.setBoundName( "c1" );
        p1.setFactType( "Driver" );
        c1.setFactField( "name" );
        c1.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p1.getChildColumns().add( c1 );
        dt.getConditions().add( p1 );

        final ConditionCol52 c1_ = new ConditionCol52();
        final Pattern52 p1_ = new Pattern52();
        p1_.setBoundName( "c1" );
        p1_.setFactType( "Driver" );
        c1_.setFactField( "name" );
        p1_.getChildColumns().add( c1_ );
        c1_.setConstraintValueType( BaseSingleFieldConstraint.TYPE_RET_VALUE );
        dt.getConditions().add( p1_ );

        final ConditionCol52 c1__ = new ConditionCol52();
        c1__.setFactField( "sex" );
        p1_.getChildColumns().add( c1__ );
        c1__.setConstraintValueType( BaseSingleFieldConstraint.TYPE_RET_VALUE );
        c1__.setValueList( "Male,Female" );
        dt.getConditions().add( p1_ );

        final ConditionCol52 c1___ = new ConditionCol52();
        final Pattern52 p1__ = new Pattern52();
        p1__.setBoundName( "c1" );
        p1__.setFactType( "Driver" );
        c1___.setFactField( "name" );
        c1___.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        c1___.setValueList( "one,two,three" );
        p1__.getChildColumns().add( c1___ );
        dt.getConditions().add( p1__ );

        final ConditionCol52 c2 = new ConditionCol52();
        final Pattern52 p2 = new Pattern52();
        p2.setBoundName( "c2" );
        p2.setFactType( "Driver" );
        c2.setFactField( "nothing" );
        c2.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p2.getChildColumns().add( c2 );
        dt.getConditions().add( p2 );

        final ActionSetFieldCol52 asf = new ActionSetFieldCol52();
        asf.setBoundName( "c1" );
        asf.setFactField( "name" );
        dt.getActionCols().add( asf );

        final ActionInsertFactCol52 ins = new ActionInsertFactCol52();
        ins.setBoundName( "x" );
        ins.setFactField( "rating" );
        ins.setFactType( "Person" );
        dt.getActionCols().add( ins );

        final ActionInsertFactCol52 ins_ = new ActionInsertFactCol52();
        ins_.setBoundName( "x" );
        ins_.setFactField( "rating" );
        ins_.setFactType( "Person" );
        ins_.setValueList( "one,two,three" );
        dt.getActionCols().add( ins_ );

        final ActionSetFieldCol52 asf_ = new ActionSetFieldCol52();
        asf_.setBoundName( "c1" );
        asf_.setFactField( "goo" );
        dt.getActionCols().add( asf_ );

        final ActionSetFieldCol52 asf__ = new ActionSetFieldCol52();
        asf__.setBoundName( "c1" );
        asf__.setFactField( "goo" );
        asf__.setValueList( "one,two,three" );
        dt.getActionCols().add( asf__ );

        assertTrue( oracle.hasEnums( p1.getFactType(),
                                     c1.getFactField() ) );
        assertFalse( utils.hasValueList( c1 ) );
        String[] r = oracle.getEnums( p1.getFactType(),
                                      c1.getFactField(),
                                      currentValueMap ).getFixedList();
        assertEquals( 2,
                      r.length );
        assertEquals( "bob",
                      r[ 0 ] );
        assertEquals( "michael",
                      r[ 1 ] );

        assertTrue( oracle.hasEnums( p1_.getFactType(),
                                     c1_.getFactField() ) );
        assertFalse( utils.hasValueList( c1_ ) );
        r = oracle.getEnums( p1_.getFactType(),
                             c1_.getFactField(),
                             currentValueMap ).getFixedList();
        assertEquals( 2,
                      r.length );
        assertEquals( "bob",
                      r[ 0 ] );
        assertEquals( "michael",
                      r[ 1 ] );

        assertFalse( oracle.hasEnums( p1_.getFactType(),
                                      c1__.getFactField() ) );
        assertTrue( utils.hasValueList( c1__ ) );
        r = utils.getValueList( c1__ );
        assertEquals( 2,
                      r.length );
        assertEquals( "Male",
                      r[ 0 ] );
        assertEquals( "Female",
                      r[ 1 ] );

        assertTrue( oracle.hasEnums( p1__.getFactType(),
                                     c1___.getFactField() ) );
        assertTrue( utils.hasValueList( c1___ ) );
        r = utils.getValueList( c1___ );
        assertEquals( 3,
                      r.length );
        assertEquals( "one",
                      r[ 0 ] );
        assertEquals( "two",
                      r[ 1 ] );
        assertEquals( "three",
                      r[ 2 ] );

        Assert.assertEquals( 0,
                             utils.getValueList( c2 ).length );

        assertTrue( oracle.hasEnums( p1.getFactType(),
                                     asf.getFactField() ) );
        assertFalse( utils.hasValueList( asf ) );
        r = oracle.getEnums( p1.getFactType(),
                             asf.getFactField(),
                             currentValueMap ).getFixedList();
        assertEquals( 2,
                      r.length );
        assertEquals( "bob",
                      r[ 0 ] );
        assertEquals( "michael",
                      r[ 1 ] );

        assertTrue( oracle.hasEnums( ins.getFactType(),
                                     ins.getFactField() ) );
        assertFalse( utils.hasValueList( ins ) );
        r = oracle.getEnums( ins.getFactType(),
                             ins.getFactField(),
                             currentValueMap ).getFixedList();
        assertEquals( 2,
                      r.length );
        assertEquals( "1",
                      r[ 0 ] );
        assertEquals( "2",
                      r[ 1 ] );

        assertTrue( oracle.hasEnums( ins_.getFactType(),
                                     ins_.getFactField() ) );
        assertTrue( utils.hasValueList( ins_ ) );
        r = utils.getValueList( ins_ );
        assertEquals( 3,
                      r.length );
        assertEquals( "one",
                      r[ 0 ] );
        assertEquals( "two",
                      r[ 1 ] );
        assertEquals( "three",
                      r[ 2 ] );

        Assert.assertEquals( 0,
                             utils.getValueList( asf_ ).length );

        assertFalse( oracle.hasEnums( p1.getFactType(),
                                      asf__.getFactField() ) );
        assertTrue( utils.hasValueList( asf__ ) );
        r = utils.getValueList( asf__ );
        assertEquals( 3,
                      r.length );
        assertEquals( "one",
                      r[ 0 ] );
        assertEquals( "two",
                      r[ 1 ] );
        assertEquals( "three",
                      r[ 2 ] );

        AttributeCol52 at = new AttributeCol52();
        at.setAttribute( "no-loop" );
        dt.getAttributeCols().add( at );

        r = utils.getValueList( at );
        assertEquals( 2,
                      r.length );
        assertEquals( "true",
                      r[ 0 ] );
        assertEquals( "false",
                      r[ 1 ] );

        at.setAttribute( "enabled" );
        Assert.assertEquals( 2,
                             utils.getValueList( at ).length );

        at.setAttribute( "salience" );
        Assert.assertEquals( 0,
                             utils.getValueList( at ).length );

    }

    @Test
    @SuppressWarnings("serial")
    public void testNumeric() {
        final GuidedDecisionTable52 dt = new GuidedDecisionTable52();
        final ProjectDefinition pd = ProjectDefinitionBuilder.newProjectDefinitionBuilder()
                .addFact( "Driver" )
                .addField( new ModelField( "age",
                                           Integer.class.getName(),
                                           ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                           FieldAccessorsAndMutators.BOTH,
                                           DataType.TYPE_NUMERIC_INTEGER ) )
                .addField( new ModelField( "name",
                                           String.class.getName(),
                                           ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                           FieldAccessorsAndMutators.BOTH,
                                           DataType.TYPE_STRING ) )
                .end()
                .build();

        final DataModelOracle oracle = PackageDataModelOracleBuilder.newDataModelBuilder().setProjectDefinition( pd ).build();

        final GuidedDecisionTableUtils utils = new GuidedDecisionTableUtils( oracle,
                                                                             dt );

        final AttributeCol52 at = new AttributeCol52();
        at.setAttribute( "salience" );
        final AttributeCol52 at_ = new AttributeCol52();
        at_.setAttribute( "enabled" );

        dt.getAttributeCols().add( at );
        dt.getAttributeCols().add( at_ );

        final ConditionCol52 c1 = new ConditionCol52();
        final Pattern52 p1 = new Pattern52();
        p1.setBoundName( "c1" );
        p1.setFactType( "Driver" );
        c1.setFactField( "name" );
        c1.setOperator( "==" );
        c1.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p1.getChildColumns().add( c1 );
        dt.getConditions().add( p1 );

        final ConditionCol52 c1_ = new ConditionCol52();
        final Pattern52 p1_ = new Pattern52();
        p1_.setBoundName( "c1" );
        p1_.setFactType( "Driver" );
        c1_.setFactField( "age" );
        c1_.setOperator( "==" );
        c1_.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p1_.getChildColumns().add( c1_ );
        dt.getConditions().add( p1_ );

        final ConditionCol52 c2 = new ConditionCol52();
        final Pattern52 p2 = new Pattern52();
        p2.setBoundName( "c1" );
        p2.setFactType( "Driver" );
        c2.setFactField( "age" );
        c2.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p2.getChildColumns().add( c2 );
        dt.getConditions().add( p2 );

        final ActionSetFieldCol52 a = new ActionSetFieldCol52();
        a.setBoundName( "c1" );
        a.setFactField( "name" );
        dt.getActionCols().add( a );

        final ActionSetFieldCol52 a2 = new ActionSetFieldCol52();
        a2.setBoundName( "c1" );
        a2.setFactField( "age" );
        dt.getActionCols().add( a2 );

        final ActionInsertFactCol52 ins = new ActionInsertFactCol52();
        ins.setBoundName( "x" );
        ins.setFactType( "Driver" );
        ins.setFactField( "name" );
        dt.getActionCols().add( ins );

        final ActionInsertFactCol52 ins_ = new ActionInsertFactCol52();
        ins_.setBoundName( "x" );
        ins_.setFactType( "Driver" );
        ins_.setFactField( "age" );
        dt.getActionCols().add( ins_ );

        Assert.assertEquals( DataType.TYPE_NUMERIC_INTEGER,
                             utils.getType( at ) );
        Assert.assertEquals( DataType.TYPE_NUMERIC_INTEGER,
                             utils.getType( c1_ ) );
        Assert.assertEquals( DataType.TYPE_NUMERIC_INTEGER,
                             utils.getType( a2 ) );
        Assert.assertEquals( DataType.TYPE_NUMERIC_INTEGER,
                             utils.getType( ins_ ) );

        Assert.assertEquals( DataType.TYPE_BOOLEAN,
                             utils.getType( at_ ) );
        Assert.assertEquals( DataType.TYPE_STRING,
                             utils.getType( c1 ) );
        Assert.assertEquals( DataType.TYPE_STRING,
                             utils.getType( a ) );
        Assert.assertEquals( DataType.TYPE_STRING,
                             utils.getType( ins ) );

        Assert.assertEquals( DataType.TYPE_STRING,
                             utils.getType( c2 ) );
    }

    @Test
    @SuppressWarnings("serial")
    public void testGetType() {
        final GuidedDecisionTable52 dt = new GuidedDecisionTable52();
        final ProjectDefinition pd = ProjectDefinitionBuilder.newProjectDefinitionBuilder()
                .addFact( "Driver" )
                .addField( new ModelField( "age",
                                           Integer.class.getName(),
                                           ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                           FieldAccessorsAndMutators.BOTH,
                                           DataType.TYPE_NUMERIC_INTEGER ) )
                .addField( new ModelField( "name",
                                           String.class.getName(),
                                           ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                           FieldAccessorsAndMutators.BOTH,
                                           DataType.TYPE_STRING ) )
                .addField( new ModelField( "date",
                                           Date.class.getName(),
                                           ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                           FieldAccessorsAndMutators.BOTH,
                                           DataType.TYPE_DATE ) )
                .addField( new ModelField( "approved",
                                           Boolean.class.getName(),
                                           ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                           FieldAccessorsAndMutators.BOTH,
                                           DataType.TYPE_BOOLEAN ) )
                .end()
                .build();

        final DataModelOracle oracle = PackageDataModelOracleBuilder.newDataModelBuilder().setProjectDefinition( pd ).build();

        final GuidedDecisionTableUtils utils = new GuidedDecisionTableUtils( oracle,
                                                                             dt );

        final AttributeCol52 salienceAttribute = new AttributeCol52();
        salienceAttribute.setAttribute( "salience" );
        final AttributeCol52 enabledAttribute = new AttributeCol52();
        enabledAttribute.setAttribute( "enabled" );

        dt.getAttributeCols().add( salienceAttribute );
        dt.getAttributeCols().add( enabledAttribute );

        final Pattern52 p1 = new Pattern52();

        final ConditionCol52 conditionColName = new ConditionCol52();
        p1.setBoundName( "c1" );
        p1.setFactType( "Driver" );
        conditionColName.setFactField( "name" );
        conditionColName.setOperator( "==" );
        conditionColName.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p1.getChildColumns().add( conditionColName );

        final ConditionCol52 conditionColAge = new ConditionCol52();
        conditionColAge.setFactField( "age" );
        conditionColAge.setOperator( "==" );
        conditionColAge.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p1.getChildColumns().add( conditionColAge );

        final ConditionCol52 conditionColDate = new ConditionCol52();
        conditionColDate.setFactField( "date" );
        conditionColDate.setOperator( "==" );
        conditionColDate.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p1.getChildColumns().add( conditionColDate );

        final ConditionCol52 conditionColApproved = new ConditionCol52();
        conditionColApproved.setFactField( "approved" );
        conditionColApproved.setOperator( "==" );
        conditionColApproved.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p1.getChildColumns().add( conditionColApproved );

        final ConditionCol52 conditionColAge2 = new ConditionCol52();
        conditionColAge2.setFactField( "age" );
        conditionColAge2.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p1.getChildColumns().add( conditionColAge2 );

        dt.getConditions().add( p1 );

        final ActionSetFieldCol52 a = new ActionSetFieldCol52();
        a.setBoundName( "c1" );
        a.setFactField( "name" );
        dt.getActionCols().add( a );

        final ActionSetFieldCol52 a2 = new ActionSetFieldCol52();
        a2.setBoundName( "c1" );
        a2.setFactField( "age" );
        dt.getActionCols().add( a2 );

        final ActionInsertFactCol52 ins = new ActionInsertFactCol52();
        ins.setBoundName( "x" );
        ins.setFactType( "Driver" );
        ins.setFactField( "name" );
        dt.getActionCols().add( ins );

        final ActionInsertFactCol52 ins_ = new ActionInsertFactCol52();
        ins_.setBoundName( "x" );
        ins_.setFactType( "Driver" );
        ins_.setFactField( "age" );
        dt.getActionCols().add( ins_ );

        Assert.assertEquals( DataType.TYPE_NUMERIC_INTEGER,
                             utils.getType( salienceAttribute ) );
        Assert.assertEquals( DataType.TYPE_BOOLEAN,
                             utils.getType( enabledAttribute ) );
        Assert.assertEquals( DataType.TYPE_STRING,
                             utils.getType( conditionColName ) );
        Assert.assertEquals( DataType.TYPE_NUMERIC_INTEGER,
                             utils.getType( conditionColAge ) );
        Assert.assertEquals( DataType.TYPE_DATE,
                             utils.getType( conditionColDate ) );
        Assert.assertEquals( DataType.TYPE_BOOLEAN,
                             utils.getType( conditionColApproved ) );
        Assert.assertEquals( DataType.TYPE_STRING,
                             utils.getType( a ) );
        Assert.assertEquals( DataType.TYPE_NUMERIC_INTEGER,
                             utils.getType( a2 ) );
        Assert.assertEquals( DataType.TYPE_STRING,
                             utils.getType( ins ) );
        Assert.assertEquals( DataType.TYPE_NUMERIC_INTEGER,
                             utils.getType( ins_ ) );
        Assert.assertEquals( DataType.TYPE_STRING,
                             utils.getType( conditionColAge2 ) );
    }

    @Test
    public void testNoConstraintLists() {
        final GuidedDecisionTable52 dt = new GuidedDecisionTable52();
        final DataModelOracle oracle = PackageDataModelOracleBuilder.newDataModelBuilder()
                .addEnum( "Driver",
                          "name",
                          new String[]{ "bob", "michael" } )
                .build();

        final GuidedDecisionTableUtils utils = new GuidedDecisionTableUtils( oracle,
                                                                             dt );

        // add cols for LHS
        final ConditionCol52 c1 = new ConditionCol52();
        final Pattern52 p1 = new Pattern52();
        p1.setBoundName( "c1" );
        p1.setFactType( "Driver" );
        c1.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p1.getChildColumns().add( c1 );
        dt.getConditions().add( p1 );

        final ConditionCol52 c2 = new ConditionCol52();
        final Pattern52 p2 = new Pattern52();
        p2.setBoundName( "c2" );
        p2.setFactType( "Driver" );
        c2.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        c2.setValueList( "a,b,c" );
        p2.getChildColumns().add( c2 );
        dt.getConditions().add( p1 );

        Assert.assertEquals( 0,
                             utils.getValueList( c1 ).length );
        Assert.assertEquals( 3,
                             utils.getValueList( c2 ).length );
    }

    @SuppressWarnings("serial")
    @Test
    public void testNoConstraints() {
        final GuidedDecisionTable52 dt = new GuidedDecisionTable52();
        final ProjectDefinition pd = ProjectDefinitionBuilder.newProjectDefinitionBuilder()
                .addFact( "Driver" )
                .addField( new ModelField( "age",
                                           Integer.class.getName(),
                                           ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                           FieldAccessorsAndMutators.BOTH,
                                           DataType.TYPE_NUMERIC_INTEGER ) )
                .addField( new ModelField( "name",
                                           String.class.getName(),
                                           ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                           FieldAccessorsAndMutators.BOTH,
                                           DataType.TYPE_STRING ) )
                .end()
                .build();

        final DataModelOracle oracle = PackageDataModelOracleBuilder.newDataModelBuilder().setProjectDefinition( pd ).build();

        final GuidedDecisionTableUtils utils = new GuidedDecisionTableUtils( oracle,
                                                                             dt );

        // add cols for LHS
        final RowNumberCol52 rnc = new RowNumberCol52();
        final DescriptionCol52 dc = new DescriptionCol52();

        final MetadataCol52 mdc = new MetadataCol52();
        mdc.setMetadata( "cheese" );

        final AttributeCol52 ac = new AttributeCol52();
        ac.setAttribute( "salience" );

        final ActionSetFieldCol52 asfc = new ActionSetFieldCol52();
        asfc.setBoundName( "d1" );
        asfc.setFactField( "age" );

        final ActionInsertFactCol52 aifc = new ActionInsertFactCol52();
        aifc.setBoundName( "d2" );
        aifc.setFactType( "Driver" );
        aifc.setFactField( "age" );

        final ConditionCol52 c1 = new ConditionCol52();
        Pattern52 p1 = new Pattern52();
        p1.setBoundName( "c1" );
        p1.setFactType( "Driver" );
        c1.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p1.getChildColumns().add( c1 );
        dt.getConditions().add( p1 );

        final ConditionCol52 c2 = new ConditionCol52();
        Pattern52 p2 = new Pattern52();
        p2.setBoundName( "c2" );
        p2.setFactType( "Driver" );
        c2.setFactField( "age" );
        c2.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p2.getChildColumns().add( c2 );
        dt.getConditions().add( p2 );

        final ConditionCol52 c3 = new ConditionCol52();
        Pattern52 p3 = new Pattern52();
        p3.setBoundName( "c3" );
        p3.setFactType( "Driver" );
        c3.setOperator( "==" );
        c3.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p3.getChildColumns().add( c3 );
        dt.getConditions().add( p3 );

        final ConditionCol52 c4 = new ConditionCol52();
        Pattern52 p4 = new Pattern52();
        p4.setBoundName( "c4" );
        p4.setFactType( "Driver" );
        c4.setFactField( "age" );
        c4.setOperator( "==" );
        c4.setConstraintValueType( BaseSingleFieldConstraint.TYPE_LITERAL );
        p4.getChildColumns().add( c4 );
        dt.getConditions().add( p4 );

        final ConditionCol52 c5 = new ConditionCol52();
        Pattern52 p5 = new Pattern52();
        p5.setBoundName( "c5" );
        p5.setFactType( "Driver" );
        c5.setConstraintValueType( BaseSingleFieldConstraint.TYPE_PREDICATE );
        p5.getChildColumns().add( c5 );
        dt.getConditions().add( p5 );

        final ConditionCol52 c6 = new ConditionCol52();
        Pattern52 p6 = new Pattern52();
        p6.setBoundName( "c6" );
        p6.setFactType( "Driver" );
        c6.setConstraintValueType( BaseSingleFieldConstraint.TYPE_RET_VALUE );
        p6.getChildColumns().add( c6 );
        dt.getConditions().add( p6 );

        assertTrue( utils.isConstraintValid( rnc ) );
        assertTrue( utils.isConstraintValid( dc ) );
        assertTrue( utils.isConstraintValid( mdc ) );
        assertTrue( utils.isConstraintValid( ac ) );
        assertTrue( utils.isConstraintValid( asfc ) );
        assertTrue( utils.isConstraintValid( aifc ) );

        assertFalse( utils.isConstraintValid( c1 ) );
        assertFalse( utils.isConstraintValid( c2 ) );
        assertFalse( utils.isConstraintValid( c3 ) );
        assertTrue( utils.isConstraintValid( c4 ) );
        assertTrue( utils.isConstraintValid( c5 ) );
        assertTrue( utils.isConstraintValid( c6 ) );
    }

    @SuppressWarnings("serial")
    @Test
    public void testConditionPredicateChoices() {
        final GuidedDecisionTable52 dt = new GuidedDecisionTable52();
        final ProjectDefinition pd = ProjectDefinitionBuilder.newProjectDefinitionBuilder()
                .addFact( "Driver" )
                .addField( new ModelField( "age",
                                           Integer.class.getName(),
                                           ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                           FieldAccessorsAndMutators.BOTH,
                                           DataType.TYPE_NUMERIC_INTEGER ) )
                .addField( new ModelField( "name",
                                           String.class.getName(),
                                           ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                           FieldAccessorsAndMutators.BOTH,
                                           DataType.TYPE_STRING ) )
                .end()
                .build();

        final DataModelOracle oracle = PackageDataModelOracleBuilder.newDataModelBuilder().setProjectDefinition( pd ).build();

        final GuidedDecisionTableUtils utils = new GuidedDecisionTableUtils( oracle,
                                                                             dt );

        final ConditionCol52 c1 = new ConditionCol52();
        final Pattern52 p1 = new Pattern52();
        p1.setBoundName( "c1" );
        p1.setFactType( "Driver" );
        c1.setConstraintValueType( BaseSingleFieldConstraint.TYPE_PREDICATE );
        c1.setFieldType( DataType.TYPE_STRING );
        c1.setValueList( "age>10,age>20,age>30" );
        p1.getChildColumns().add( c1 );
        dt.getConditions().add( p1 );

        assertTrue( utils.getValueList( c1 ).length > 0 );
        assertTrue( utils.getValueList( c1 ).length == 3 );
        Assert.assertEquals( "age>10",
                             utils.getValueList( c1 )[ 0 ] );
        Assert.assertEquals( "age>20",
                             utils.getValueList( c1 )[ 1 ] );
        Assert.assertEquals( "age>30",
                             utils.getValueList( c1 )[ 2 ] );
    }

    @SuppressWarnings("serial")
    @Test
    public void testConditionFormulaChoices() {
        final GuidedDecisionTable52 dt = new GuidedDecisionTable52();
        final ProjectDefinition pd = ProjectDefinitionBuilder.newProjectDefinitionBuilder()
                .addFact( "Driver" )
                .addField( new ModelField( "age",
                                           Integer.class.getName(),
                                           ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                           FieldAccessorsAndMutators.BOTH,
                                           DataType.TYPE_NUMERIC_INTEGER ) )
                .addField( new ModelField( "name",
                                           String.class.getName(),
                                           ModelField.FIELD_CLASS_TYPE.REGULAR_CLASS,
                                           FieldAccessorsAndMutators.BOTH,
                                           DataType.TYPE_STRING ) )
                .end()
                .build();

        final DataModelOracle oracle = PackageDataModelOracleBuilder.newDataModelBuilder().setProjectDefinition( pd ).build();

        final GuidedDecisionTableUtils utils = new GuidedDecisionTableUtils( oracle,
                                                                             dt );

        final ConditionCol52 c1 = new ConditionCol52();
        final Pattern52 p1 = new Pattern52();
        p1.setBoundName( "c1" );
        p1.setFactType( "Driver" );
        c1.setConstraintValueType( BaseSingleFieldConstraint.TYPE_RET_VALUE );
        c1.setFieldType( DataType.TYPE_STRING );
        c1.setValueList( "getAge()>10,getAge()>20,getAge()>30" );
        p1.getChildColumns().add( c1 );
        dt.getConditions().add( p1 );

        assertTrue( utils.getValueList( c1 ).length > 0 );
        assertTrue( utils.getValueList( c1 ).length == 3 );
        Assert.assertEquals( "getAge()>10",
                             utils.getValueList( c1 )[ 0 ] );
        Assert.assertEquals( "getAge()>20",
                             utils.getValueList( c1 )[ 1 ] );
        Assert.assertEquals( "getAge()>30",
                             utils.getValueList( c1 )[ 2 ] );
    }

}