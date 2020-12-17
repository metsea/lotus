/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.metsea.lotus.server.registry;

import cn.metsea.lotus.service.zookeeper.config.ZookeeperConfig;
import cn.metsea.lotus.service.zookeeper.opeartor.ZookeeperCachedOperator;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Zookeeper Registry Center
 */
@Service
public class ZookeeperRegistryCenter implements InitializingBean {

    private static final String EMPTY = "";

    @Getter
    @Autowired
    protected ZookeeperCachedOperator zookeeperCachedOperator;

    @Autowired
    private ZookeeperConfig zookeeperConfig;

    /**
     * nodes namespace
     */
    private String nodesPath;

    /**
     * master path
     */
    @Getter
    private String masterPath;

    /**
     * worker path
     */
    @Getter
    private String workerPath;

    @Override
    public void afterPropertiesSet() {
        this.nodesPath = this.zookeeperConfig.getLotusRoot() + "/nodes";
        this.masterPath = this.nodesPath + "/master";
        this.workerPath = this.nodesPath + "/worker";

        init();
    }

    /**
     * init node persist
     */
    public void init() {
        initNodes();
    }

    /**
     * init nodes
     */
    private void initNodes() {
        this.zookeeperCachedOperator.upsertPersist(this.masterPath, EMPTY, true);
        this.zookeeperCachedOperator.upsertPersist(this.workerPath, EMPTY, true);
    }

}
