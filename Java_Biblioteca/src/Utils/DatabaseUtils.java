package Utils;

import com.db4o.ObjectContainer;
import com.db4o.Db4oEmbedded;
import com.db4o.config.EmbeddedConfiguration;

import Entities.Client;
import Entities.ItemSale;
import Entities.Sale;

public class DatabaseUtils {
	
    // Criamos atributo do tipo ObjectContainer.
    private static ObjectContainer database;

    // Criamos método do tipo ObjectContainer.
    public static ObjectContainer openDatabase() {
        // SE O BANCO NÃO EXISTIR!!!, iremos criar um e configurá-lo.
        if (database == null || database.ext().isClosed()) {
            // Criamos o objeto de configuração zerado.
            EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
            // Adicionamos a sua configuração, Cascata de Atualizações, para salvar automaticamente objetos relacionados.
            config.common().objectClass(Sale.class).cascadeOnUpdate(true);
            config.common().objectClass(Client.class).cascadeOnUpdate(true);
            config.common().objectClass(ItemSale.class).cascadeOnUpdate(true);
            database = Db4oEmbedded.openFile(config, "databaseBiblioteca.db4o");
        }
        return database;
    }

    public static void closeDatabase() {
        if (database != null && !database.ext().isClosed()) {
            database.close();
        }   
    }
    
}



