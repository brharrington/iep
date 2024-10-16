/*
 * Copyright 2014-2022 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.iep.atlas;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.multibindings.Multibinder;
import com.netflix.iep.service.Service;
import com.netflix.spectator.api.Registry;

import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * Setup registry for reporting spectator data to Atlas.
 */
public class AtlasModule extends AbstractModule {
  @Override protected void configure() {
    Multibinder<Service> serviceBinder = Multibinder.newSetBinder(binder(), Service.class);
    serviceBinder.addBinding().to(AtlasRegistryService.class);

    bind(Registry.class).toProvider(AtlasRegistryProvider.class).asEagerSingleton();
  }

  @Singleton
  private static class AtlasRegistryProvider implements Provider<Registry> {

    @Inject
    private AtlasRegistryService service;

    @Override public Registry get() {
      return service.getRegistry();
    }
  }
}

