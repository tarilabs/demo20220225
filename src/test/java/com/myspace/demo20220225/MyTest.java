package com.myspace.demo20220225;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.api.runtime.rule.Variable;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;

public class MyTest {
    private static final String URL = "http://localhost:8080/kie-server/services/rest/server";
    private static final String USER = "krisv";
    private static final String PASSWORD = "krisv";
    private static final MarshallingFormat FORMAT = MarshallingFormat.JAXB;
    private KieServicesConfiguration conf;
    private KieServicesClient kieServicesClient;
    private static final String containerId = "demo20220225_1.0.0-SNAPSHOT";

    @Before
    public void initialize() {
        conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);
        conf.setMarshallingFormat(FORMAT);
        conf.addExtraClasses(new HashSet<>(Arrays.asList(Person.class)));
        kieServicesClient = KieServicesFactory.newKieServicesClient(conf);
    }

    @Test
    public void demo() {
        initialize();
        RuleServicesClient ruleClient = kieServicesClient.getServicesClient(RuleServicesClient.class);
        ServiceResponse<ExecutionResults> executeResponse = ruleClient.executeCommandsWithResults(containerId, batchCommands());
        System.out.println("ServiceResponse.type: " + executeResponse.getType());
        ExecutionResults serverResult = executeResponse.getResult();
        for (String serverResultId : serverResult.getIdentifiers()) {
            System.out.println("ServiceResponse.result."+serverResultId+": "+serverResult.getValue(serverResultId));
        }
        // QueryResults results = (QueryResults) serverResult.getValue("qid");
        // Iterator<QueryResultsRow> i = results.iterator();
        // while (i.hasNext()) {
        //     QueryResultsRow row = i.next();
        //     System.out.println(Stream.of(results.getIdentifiers()).map(fid -> fid + ": "+row.get(fid)).collect(Collectors.joining(", ")));
        //     System.out.println(row.get("x") + " contains " + row.get("y") );
        // }
    }

    public static BatchExecutionCommand batchCommands() {
        Person johnDoe = new Person("John Doe", 47, null);
        Person aMinor = new Person("A. Minor", 14, null);
        
        KieCommands kieCommands = KieServices.Factory.get().getCommands();
        List<Command<?>> commandList = new ArrayList<Command<?>>();
        commandList.add(kieCommands.newInsert( johnDoe, "johnDoe" ));
        commandList.add(kieCommands.newInsert( aMinor,  "aMinor"  ));

        commandList.add(kieCommands.newFireAllRules());
        BatchExecutionCommand batch = kieCommands.newBatchExecution(commandList);
        
        return batch;
    }
}
