/**
 *  Copyright 2005-2014 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.fabric8.service.ssh.commands;

import io.fabric8.api.FabricService;
import io.fabric8.api.ZooKeeperClusterService;
import io.fabric8.api.scr.ValidatingReference;
import io.fabric8.boot.commands.support.AbstractCommandComponent;
import io.fabric8.boot.commands.support.ProfileCompleter;
import io.fabric8.boot.commands.support.ResolverCompleter;
import io.fabric8.boot.commands.support.VersionCompleter;
import io.fabric8.commands.support.RootContainerCompleter;
import org.apache.felix.gogo.commands.Action;
import org.apache.felix.gogo.commands.basic.AbstractCommand;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.service.command.Function;

@Component(immediate = true)
@Service({ Function.class, AbstractCommand.class })
@org.apache.felix.scr.annotations.Properties({
        @Property(name = "osgi.command.scope", value = ContainerCreateSsh.SCOPE_VALUE),
        @Property(name = "osgi.command.function", value = ContainerCreateSsh.FUNCTION_VALUE)
})
public final class ContainerCreateSsh extends AbstractCommandComponent {

    public static final String SCOPE_VALUE = "fabric";
    public static final String FUNCTION_VALUE =  "container-create-ssh";
    public static final String DESCRIPTION = "Creates one or more ssh containers";

    @Reference(referenceInterface = FabricService.class)
    private final ValidatingReference<FabricService> fabricService = new ValidatingReference<FabricService>();
    @Reference(referenceInterface = ZooKeeperClusterService.class)
    private final ValidatingReference<ZooKeeperClusterService> clusterService = new ValidatingReference<ZooKeeperClusterService>();

    // Optional Completers
    @Reference(referenceInterface = ProfileCompleter.class, bind = "bindProfileCompleter", unbind = "unbindProfileCompleter")
    private ProfileCompleter profileCompleter; // dummy field
    @Reference(referenceInterface = ResolverCompleter.class, bind = "bindResolverCompleter", unbind = "unbindResolverCompleter")
    private ResolverCompleter resolverCompleter;
    @Reference(referenceInterface = VersionCompleter.class, bind = "bindVersionCompleter", unbind = "unbindVersionCompleter")
    private VersionCompleter versionCompleter; // dummy field

    @Activate
    void activate() {
        activateComponent();
    }

    @Deactivate
    void deactivate() {
        deactivateComponent();
    }

    @Override
    public Action createNewAction() {
        assertValid();
        return new ContainerCreateSshAction(fabricService.get(), clusterService.get());
    }

    void bindFabricService(FabricService fabricService) {
        this.fabricService.bind(fabricService);
    }

    void unbindFabricService(FabricService fabricService) {
        this.fabricService.unbind(fabricService);
    }

    void bindClusterService(ZooKeeperClusterService clusterService) {
        this.clusterService.bind(clusterService);
    }

    void unbindClusterService(ZooKeeperClusterService clusterService) {
        this.clusterService.unbind(clusterService);
    }

    void bindRootContainerCompleter(RootContainerCompleter completer) {
        bindCompleter(completer);
    }

    void unbindRootContainerCompleter(RootContainerCompleter completer) {
        unbindCompleter(completer);
    }

    void bindProfileCompleter(ProfileCompleter completer) {
        bindOptionalCompleter(completer);
    }

    void unbindProfileCompleter(ProfileCompleter completer) {
        unbindOptionalCompleter(completer);
    }

    void bindResolverCompleter(ResolverCompleter completer) {
        bindOptionalCompleter(completer);
    }

    void unbindResolverCompleter(ResolverCompleter completer) {
        unbindOptionalCompleter(completer);
    }

    void bindVersionCompleter(VersionCompleter completer) {
        bindOptionalCompleter(completer);
    }

    void unbindVersionCompleter(VersionCompleter completer) {
        unbindOptionalCompleter(completer);
    }
}
