package com.ufrstgi.imr.application.database.server;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ufrstgi.imr.application.object.Adresse;
import com.ufrstgi.imr.application.object.Client;
import com.ufrstgi.imr.application.object.Livraison;
import com.ufrstgi.imr.application.object.Operation;
import com.ufrstgi.imr.application.object.Reception;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by Duduf on 11/01/2017.
 */

public class OperationSerializer  implements JsonDeserializer<Operation> {

    public Operation deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj= json.getAsJsonObject();
        int id_operation = obj.get("id_operation").getAsInt();
        Date date_theorique = context.deserialize(obj.get("date_theorique"), Date.class);
        Date date_reelle = context.deserialize(obj.get("date_reelle"), Date.class);
        Date date_limite = context.deserialize(obj.get("date_limite"), Date.class);
        String quai=obj.get("quai").getAsString();
        String batiment=obj.get("batiment").getAsString();
        Adresse adresse=context.deserialize(obj.get("id_adresse"), Adresse.class);
        Client client= context.deserialize(obj.get("id_client"), Client.class);
        int estLivraison=obj.get("est_livraison").getAsInt();

        if(estLivraison==1)
            return new Livraison(id_operation,date_theorique, date_reelle,date_limite,quai,batiment,adresse,client);
        else
            return new Reception(id_operation,date_theorique, date_reelle,date_limite,quai,batiment,adresse,client);


    }

}
