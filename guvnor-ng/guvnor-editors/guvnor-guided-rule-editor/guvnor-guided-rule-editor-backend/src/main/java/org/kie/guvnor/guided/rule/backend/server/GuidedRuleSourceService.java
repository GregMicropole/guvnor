/*
 * Copyright 2013 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.guvnor.guided.rule.backend.server;

import org.drools.guvnor.models.commons.backend.rule.BRDRLPersistence;
import org.drools.guvnor.models.commons.shared.rule.RuleModel;
import org.kie.commons.java.nio.file.Path;
import org.kie.guvnor.commons.service.source.BaseSourceService;

public class GuidedRuleSourceService
        extends BaseSourceService<RuleModel> {

    private static final String PATTERN = ".gre.drl";

    @Override
    public String getPattern() {
        return PATTERN;
    }

    @Override
    public String getSource( final Path path,
                             final RuleModel model ) {
        return new StringBuilder().append( BRDRLPersistence.getInstance().marshal( model ) ).toString();
    }

}
