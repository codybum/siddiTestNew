
public class Launcher {

    public static void main(String[] args) {

        try {

            CEPEngine cepEngine = new CEPEngine();

//public void createCEP(String inputRecordSchemaString, String inputStreamName, String inputTopic, String outputStreamName, String outputTopic, String outputStreamAttributesString,String queryString) {

            String inputRecordSchemaString = "{\"type\":\"record\",\"name\":\"Ticker\",\"fields\":[{\"name\":\"source\",\"type\":\"string\"},{\"name\":\"urn\",\"type\":\"string\"},{\"name\":\"metric\",\"type\":\"string\"},{\"name\":\"ts\",\"type\":\"long\"},{\"name\":\"value\",\"type\":\"double\"}]}";
            String inputStreamName = "UserStream";
            String inputStreamAttributesString = "source string, urn string, metric string, ts long, value double";
            //"source":"mysource","urn":"myurn","metric":"mymetric","ts":1576355714623,"value":0.3142739729174573

            String outputStreamName = "BarStream";
            String outputStreamAttributesString = "source string, avgValue double";

            String queryString = " " +
                    //from TempStream#window.timeBatch(10 min)
                    //"from UserStream#window.time(5 sec) " +
                    "from UserStream#window.timeBatch(5 sec) " +
                    "select source, avg(value) as avgValue " +
                    "  group by source " +
                    "insert into BarStream; ";
// public void createCEP(String inputStreamName, String outputStreamName, String inputStreamAttributesString, String outputStreamAttributesString,String queryString) {

            cepEngine.createCEP(inputStreamName, outputStreamName, inputStreamAttributesString , outputStreamAttributesString, queryString);

            //System.out.println("OUTPUT SCHEMA : " + cepEngine.getSchema(outputStreamName));

            cepEngine.input(inputStreamName, cepEngine.getStringPayload());

            //while (true) {
            //    cepEngine.input(inputStreamName, cepEngine.getStringPayload());
            //}

            //cepEngine.shutdown();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
