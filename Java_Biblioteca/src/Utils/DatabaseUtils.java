package Utils;

import com.db4o.ObjectContainer;
import com.db4o.Db4oEmbedded;
import com.db4o.config.EmbeddedConfiguration;

import Entities.Cliente;
import Entities.ItemVenda;
import Entities.Venda;

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
            config.common().objectClass(Venda.class).cascadeOnUpdate(true);
            config.common().objectClass(Cliente.class).cascadeOnUpdate(true);
            config.common().objectClass(ItemVenda.class).cascadeOnUpdate(true);

            config.common().objectClass(Venda.class).cascadeOnDelete(true);
            config.common().objectClass(Cliente.class).cascadeOnDelete(true);
            // Por fim, abrimos o banco com as configurações estabelecidas.
            database = Db4oEmbedded.openFile(config, "databaseBiblioteca.db4o");
            
        }
        
        // Retornamos o banco de dados aberto.
        return database;
        
    }

    public static void closeDatabase() {
    	
    	// 
        if (database != null && !database.ext().isClosed()) {
            database.close();
        }
        
    }
    
}



