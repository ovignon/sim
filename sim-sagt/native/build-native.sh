#!/bin/bash

native-image -cp ../target/sim-sagt-1.0.0-SNAPSHOT-jar-with-dependencies.jar \
	--no-fallback \
	--report-unsupported-elements-at-runtime \
	-H:ReflectionConfigurationFiles=reflect-config.json \
	-H:ResourceConfigurationFiles=resource-config.json \
	-H:Class=com.mobility.scoopptf.sim.sagt.SimSagtApp \
	-H:Name=sim-sagt
