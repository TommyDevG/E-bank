package bankmanager.restservice;

import bankmanager.datamanagement.OperationBancaire;

import java.util.List;
import java.util.*;

import bankmanager.problemdomain.OperationBancaireApp;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

public class ObjectToJSON {

    public static String ListOfOperationToJSON(List<OperationBancaire> listOperation)  {
        ObjectMapper mapper = new ObjectMapper();

        String jsonListOperation = "";

        try {
            jsonListOperation = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(listOperation);
        } catch(JsonProcessingException jsonProcessingException) {
            System.out.println(jsonProcessingException.toString());

        }

        return jsonListOperation;
    }

}
