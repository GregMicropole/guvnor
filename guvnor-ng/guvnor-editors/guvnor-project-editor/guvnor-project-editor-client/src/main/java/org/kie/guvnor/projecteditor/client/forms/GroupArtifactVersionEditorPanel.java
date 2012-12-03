/*
 * Copyright 2012 JBoss Inc
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

package org.kie.guvnor.projecteditor.client.forms;

import com.google.gwt.user.client.ui.Widget;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.Caller;
import org.kie.guvnor.projecteditor.model.GroupArtifactVersionModel;
import org.kie.guvnor.projecteditor.service.ProjectEditorService;
import org.uberfire.backend.vfs.Path;
import org.uberfire.client.annotations.WorkbenchMenu;
import org.uberfire.client.mvp.Command;
import org.uberfire.client.workbench.widgets.menu.MenuBar;
import org.uberfire.client.workbench.widgets.menu.impl.DefaultMenuBar;
import org.uberfire.client.workbench.widgets.menu.impl.DefaultMenuItemCommand;

import javax.inject.Inject;

public class GroupArtifactVersionEditorPanel
        implements GroupArtifactVersionEditorPanelView.Presenter {

    private final Caller<ProjectEditorService> projectEditorServiceCaller;
    private final GroupArtifactVersionEditorPanelView view;
    private Path path;
    private GroupArtifactVersionModel model;

    @Inject
    public GroupArtifactVersionEditorPanel(Caller<ProjectEditorService> projectEditorServiceCaller,
                                           GroupArtifactVersionEditorPanelView view) {
        this.projectEditorServiceCaller = projectEditorServiceCaller;
        this.view = view;
        view.setPresenter(this);
    }

    public void init(Path path) {
        this.path = path;
        projectEditorServiceCaller.call(
                new RemoteCallback<GroupArtifactVersionModel>() {
                    @Override
                    public void callback(GroupArtifactVersionModel gav) {
                        GroupArtifactVersionEditorPanel.this.model = gav;
                        view.setGroupId(gav.getGroupId());
                        view.setArtifactId(gav.getArtifactId());
                        view.setVersionId(gav.getVersion());
                    }
                }
        ).loadGav(path);
    }

    public Widget asWidget() {
        return view.asWidget();
    }

    @WorkbenchMenu
    public MenuBar buildMenuBar() {
        MenuBar menuBar = new DefaultMenuBar();

        menuBar.addItem(new DefaultMenuItemCommand(
                view.getSaveMenuItemText(),
                new Command() {
                    @Override
                    public void execute() {
                        projectEditorServiceCaller.call(
                                new RemoteCallback<Object>() {
                                    @Override
                                    public void callback(Object o) {
                                        view.showSaveSuccessful();
                                    }
                                }
                        ).saveGav(path, model);
                    }
                }
        ));

        return menuBar;
    }

    @Override
    public void onGroupIdChange(String groupId) {
        model.setGroupId(groupId);
    }

    @Override
    public void onArtifactIdChange(String artifactId) {
        model.setArtifactId(artifactId);
    }

    @Override
    public void onVersionIdChange(String versionId) {
        model.setVersion(versionId);
    }
}
