import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringMapMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ECSlogger {

    private PropertiesReader reader;
    private static final Logger mtlogger =  LogManager.getLogger("MT_LOGGER");
    private static final Logger gklogger =  LogManager.getLogger("GK_LOGGER");



    public static void main(String[] args) throws IOException {
        new ECSlogger().doSomelogging();
    }

    private void doSomelogging() throws IOException {
        reader = new PropertiesReader("properties-from-pom.properties");

        List<StringMapMessage> stuffToLogg = new ArrayList<>();
        stuffToLogg.addAll(createSomeCaseViewing());
        stuffToLogg.addAll(createSomeConfidentialityChanges());
        stuffToLogg.addAll(createSomeCaseUpdating());
//        stuffToLogg.addAll(createSomeCaseListViewing());


        for(StringMapMessage singeRow: stuffToLogg){
            logSingleRow(singeRow);
        }
    }

    private void logSingleRow(StringMapMessage loggInfo) {
        String version = reader.getProperty("version");

//        gklogger.info(loggInfo.with("service.version", version));
        mtlogger.info(loggInfo.with("service.version", version));
    }

    private List<StringMapMessage> createSomeCaseViewing() {
        List<StringMapMessage> result = new ArrayList<>();

        result.add(createSingleCaseViewing( "jonsw", "6.6.2-2023-228261", "8823e8f8f30f5001ba344e4e54d8d9cb82cb10AA", "2"));
        result.add(createSingleCaseViewing("jonsw", "6.6.2-2023-228262", "9923e8f8f30f5001ba344e4e54d8d9cb82cb10BB", "2"));
        result.add(createSingleCaseViewing("stefanos", "6.6.2-2023-228262", "8823e8f8f30f5001ba344e4e54d8d9cb82cb10AA", "1"));
        result.add(createSingleCaseViewing("stefanos", "6.6.2-2023-228261", "8823e8f8f30f5001ba344e4e54d8d9cb82cb10BB", "1"));

        return result;
    }

    private StringMapMessage createSingleCaseViewing(String user, String diaryNumber, String caseId, String condfidentialityLevel) {
        return new StringMapMessage()
                .with("event.action", "mpa_view_case_details")
                .with("message", "The user views a case.")
                .with("user.name", user)
                .with("Custom.case", createSingleCaseAsMap(diaryNumber, caseId, condfidentialityLevel));
    }


    private List<StringMapMessage> createSomeConfidentialityChanges() {
        List<StringMapMessage> result = new ArrayList<>();

        result.add(createSingleConfidentialityChange("jonsw", "6.6.2-2023-228261", "8823e8f8f30f5001ba344e4e54d8d9cb82cb10AA", "2","3"));
        result.add(createSingleConfidentialityChange("jonsw", "6.6.2-2023-228262", "9923e8f8f30f5001ba344e4e54d8d9cb82cb10BB", "2", "1"));
        result.add(createSingleConfidentialityChange("stefanos", "6.6.2-2023-228263", "9923e8f8f30f5001ba344e4e54d8d9cb82cb10CC", "3", "2"));
        result.add(createSingleConfidentialityChange("jonsw", "6.6.2-2023-228264", "9923e8f8f30f5001ba344e4e54d8d9cb82cb10DD", "3", "2"));

        return result;
    }

    private StringMapMessage createSingleConfidentialityChange(String user, String diaryNumber, String caseId, String condfidentialityBefore, String condfidentialityAfter) {
        return new StringMapMessage()
                .with("event.action", "mpa_change_case_confidentiality")
                .with("message", "The user changes the confidentiality of a case.")
                .with("user.name", user)
                .with("Custom.case", createSingleCaseAsMap(diaryNumber, caseId, null))
                .with("Custom.attributes_update.before.case.confidentiality", condfidentialityBefore)
                .with("Custom.attributes_update.after.case.confidentiality", condfidentialityAfter);
    }



    private List<StringMapMessage> createSomeCaseUpdating() {
        List<StringMapMessage> result = new ArrayList<>();

        result.add(createSingleCaseUpdating("jonsw", "6.6.2-2023-228261", "8823e8f8f30f5001ba344e4e54d8d9cb82cb10AA"));
        result.add(createSingleCaseUpdating("jonsw", "6.6.2-2023-228262", "9923e8f8f30f5001ba344e4e54d8d9cb82cb10BB"));
        result.add(createSingleCaseUpdating("stefanos", "6.6.2-2023-228262", "8823e8f8f30f5001ba344e4e54d8d9cb82cb10AA"));
        result.add(createSingleCaseUpdating("stefanos", "6.6.2-2023-228261", "8823e8f8f30f5001ba344e4e54d8d9cb82cb10BB"));

        return result;
    }

    private StringMapMessage createSingleCaseUpdating(String user, String diaryNumber, String caseId) {
        return new StringMapMessage()
                .with("event.action", "mpa_update_case_details")
                .with("message", "The user updates data in a case.")
                .with("user.name", user)
                .with("Custom.case", createSingleCaseAsMap(diaryNumber, caseId, null));
    }

    private List<StringMapMessage> createSomeCaseListViewing() {
        List<StringMapMessage> result = new ArrayList<>();

        result.add(createSingleCaseListViewing( "stefanos",
                new String[] {"6.6.2-228261", "6.6.2-2023-228262", "6.6.2-2023-228263"},
                new String[] {"1123e8f8f30f5001ba344e4e54d8d9cb82cb1011", "2223e8f8f30f5001ba344e4e54d8d9cb82cb1022", "3323e8f8f30f5001ba344e4e54d8d9cb82cb1023"},
                new String[] {"1", "2", "2"}));

        result.add(createSingleCaseListViewing( "stefanos",
                new String[] {"6.6.2-2023-228261", "6.6.2-2023-228264"},
                new String[] {"1123e8f8f30f5001ba344e4e54d8d9cb82cb1011", "4423e8f8f30f5001ba344e4e54d8d9cb82cb1024"},
                new String[] {"1", "3"}));


        return result;
    }

    private StringMapMessage createSingleCaseListViewing(String user, String[] diaryNumbers, String[] caseIds, String[] condfidentialityLevels) throws RuntimeException {

        List<Map<String, String>> cases = createCasesAsListOfMaps(diaryNumbers,caseIds, condfidentialityLevels);

        return new StringMapMessage()
                .with("event.action", "mpa_view_case_list")
                .with("message", "The user views a list of cases.")
                .with("user.name", user)
                .with("Custom.case", cases);
    }

    private Map<String, String> createSingleCaseAsMap(String diaryNumber, String caseId, String condfidentialityLevel) {
        var caseAsMap = new HashMap<String, String>();
        caseAsMap.put("registration_number", diaryNumber);
        caseAsMap.put("id", caseId);
        if(condfidentialityLevel != null) {
            caseAsMap.put("confidentiality", condfidentialityLevel);
        }

        return caseAsMap;
    }

    private List<Map<String, String>> createCasesAsListOfMaps(String[] diaryNumbers, String[] caseIds, String[] condfidentialityLevels) {
        var result = new ArrayList<Map<String, String>>();

        for (int i = 0; i < diaryNumbers.length; i++) {
            result.add(createSingleCaseAsMap(diaryNumbers[i], caseIds[i], condfidentialityLevels[i]));
        }
        return result;
    }
}

