package org.kie.guvnor.guided.rule.client.handlers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import org.drools.guvnor.models.commons.shared.rule.RuleModel;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.Caller;
import org.kie.guvnor.commons.ui.client.callbacks.HasBusyIndicatorDefaultErrorCallback;
import org.kie.guvnor.commons.ui.client.handlers.DefaultNewResourceHandler;
import org.kie.guvnor.commons.ui.client.handlers.NewResourcePresenter;
import org.kie.guvnor.commons.ui.client.popups.file.CommandWithCommitMessage;
import org.kie.guvnor.commons.ui.client.popups.file.SaveOperationService;
import org.kie.guvnor.commons.ui.client.resources.i18n.CommonConstants;
import org.kie.guvnor.commons.ui.client.widget.BusyIndicatorView;
import org.kie.guvnor.guided.rule.client.resources.GuidedRuleEditorResources;
import org.kie.guvnor.guided.rule.client.resources.i18n.Constants;
import org.kie.guvnor.guided.rule.client.type.GuidedRuleResourceType;
import org.kie.guvnor.guided.rule.service.GuidedRuleEditorService;
import org.uberfire.backend.vfs.Path;
import org.uberfire.client.mvp.PlaceManager;
import org.uberfire.shared.mvp.PlaceRequest;
import org.uberfire.shared.mvp.impl.PathPlaceRequest;

/**
 * Handler for the creation of new Guided Rules
 */
@ApplicationScoped
public class NewGuidedRuleHandler extends DefaultNewResourceHandler {

    @Inject
    private PlaceManager placeManager;

    @Inject
    private Caller<GuidedRuleEditorService> service;

    @Inject
    private GuidedRuleResourceType resourceType;

    @Inject
    private BusyIndicatorView busyIndicatorView;

    @Override
    public String getDescription() {
        return Constants.INSTANCE.NewGuidedRuleDescription();
    }

    @Override
    public IsWidget getIcon() {
        return new Image( GuidedRuleEditorResources.INSTANCE.images().guidedRuleIcon() );
    }

    @Override
    public void create( final Path contextPath,
                        final String baseFileName,
                        final NewResourcePresenter presenter ) {
        final RuleModel ruleModel = new RuleModel();
        ruleModel.name = baseFileName;

        new SaveOperationService().save( contextPath,
                                         new CommandWithCommitMessage() {
                                             @Override
                                             public void execute( final String comment ) {
                                                 busyIndicatorView.showBusyIndicator( CommonConstants.INSTANCE.Saving() );
                                                 service.call( getSuccessCallback( presenter ),
                                                               new HasBusyIndicatorDefaultErrorCallback( busyIndicatorView ) ).create( contextPath,
                                                                                                                                       buildFileName( resourceType,
                                                                                                                                                      baseFileName ),
                                                                                                                                       ruleModel,
                                                                                                                                       comment );
                                             }
                                         } );
    }

    private RemoteCallback<Path> getSuccessCallback( final NewResourcePresenter presenter ) {
        return new RemoteCallback<Path>() {

            @Override
            public void callback( final Path path ) {
                busyIndicatorView.hideBusyIndicator();
                presenter.complete();
                notifySuccess();
                final PlaceRequest place = new PathPlaceRequest( path );
                placeManager.goTo( place );
            }
        };
    }

}
