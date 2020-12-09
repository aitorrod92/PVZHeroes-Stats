package pvzheroes.clases.diccionarios;

import java.util.HashMap;

public class DiccionarioSQL {

    public static HashMap<String, String> FiltroASQL = new HashMap();

    public DiccionarioSQL() {
        // Relaciones        
        FiltroASQL.put("Contiene", "LIKE");
        FiltroASQL.put("No contiene", "NOT LIKE");
        FiltroASQL.put("Es exactamente", "=");
        FiltroASQL.put("Es diferente de", "<>");
        FiltroASQL.put("Mayor que", ">");
        FiltroASQL.put("Mayor o igual que", ">=");
        FiltroASQL.put("Igual que", "=");
        FiltroASQL.put("Menor o igual que", "<=");
        FiltroASQL.put("Menor que", "<");
        FiltroASQL.put("Es", "=");
        FiltroASQL.put("No es", "<>");

        // Valores de clases
        FiltroASQL.put("Kabum", "Kabloom");
        FiltroASQL.put("Gigante", "Mega-Grow");
        FiltroASQL.put("Guardi�n", "Guardian");
        FiltroASQL.put("Solar", "Solar");
        FiltroASQL.put("Astuto", "Smarty");
        FiltroASQL.put("Bestial", "Beastly");
        FiltroASQL.put("Cerebral", "Brainy");
        FiltroASQL.put("Furtivo", "Sneaky");
        FiltroASQL.put("Loco", "Crazy");
        FiltroASQL.put("Valeroso", "Hearty");

        // Valores de tribu (animal, banana y cactus no se han inclu�do por ser similares)
        FiltroASQL.put("Tonel", "Barrel");
        FiltroASQL.put("Reloj", "Clock");
        FiltroASQL.put("Bailar�n", "Dancing");
        FiltroASQL.put("Zombistein", "Gargantuar");
        FiltroASQL.put("Gourmet", "Gourmet");
        FiltroASQL.put("Historia", "History");
        FiltroASQL.put("Zombidito", "Imp");
        FiltroASQL.put("Mimo", "Mime");
        FiltroASQL.put("Monstruo", "Monster");
        FiltroASQL.put("Mostacho", "Mustache");
        FiltroASQL.put("Fiesta", "Party");
        FiltroASQL.put("Mascota", "Pet");
        FiltroASQL.put("Pirata", "Pirate");
        FiltroASQL.put("Profesional", "Professional");
        FiltroASQL.put("Ciencias", "Science");
        FiltroASQL.put("Deportista", "Sports");
        FiltroASQL.put("Truco", "Trick");
        FiltroASQL.put("Entorno", "Environment");
        FiltroASQL.put("Superpoder", "Superpower");
        FiltroASQL.put("Frijol", "Bean");
        FiltroASQL.put("Baya", "Berry");
        FiltroASQL.put("Ma�z", "Corn");
        FiltroASQL.put("Drag�n", "Dragon");
        FiltroASQL.put("Atrapamoscas", "Flytrap");
        FiltroASQL.put("Flor", "Flower");
        FiltroASQL.put("Fruta", "Fruit");
        FiltroASQL.put("Hoja", "Leaf");
        FiltroASQL.put("Musgo", "Moss");
        FiltroASQL.put("Seta", "Mushroom");
        FiltroASQL.put("Nuez", "Nut");
        FiltroASQL.put("Guisante", "Pea");
        FiltroASQL.put("Pi�a", "Pinecone");
        FiltroASQL.put("Ra�z", "Root");
        FiltroASQL.put("Apisonaflor", "Squash");
        FiltroASQL.put("�rbol", "Tree");
        FiltroASQL.put("Semilla", "Seed");

        // Valores de atributo
        FiltroASQL.put("Doble Golpe", "Double Strike");
        FiltroASQL.put("Da�o de �rea", "Splash Damage");
        FiltroASQL.put("Anti-H�roe", "Anti-Hero");
        FiltroASQL.put("Blindaje", "Armored");
        FiltroASQL.put("Impacto perforante", "Strikethrough");
        FiltroASQL.put("Equipo", "Team-Up");
        FiltroASQL.put("Anfibio", "Amphibious");
        FiltroASQL.put("Diana", "Bullseye");
        FiltroASQL.put("Intrucable", "Untrickable");
        FiltroASQL.put("Frenes�", "Frenzy");
        FiltroASQL.put("Rebasamiento", "Overshoot");
        FiltroASQL.put("L�pida", "Gravestone");
        FiltroASQL.put("Letal", "Deadly");
        FiltroASQL.put("Cazar", "Hunt");
        FiltroASQL.put("Ninguno", "Sin atributos");

        // Valores de habilidades ("Sin habilidades" no indicado)
        FiltroASQL.put("Ataque extra", "Bonus Attack");
        FiltroASQL.put("Rebota", "Bounce");
        FiltroASQL.put("Conjura", "Conjure");
        FiltroASQL.put("Destruye", "Destroy");
        FiltroASQL.put("Roba", "Draw");
        FiltroASQL.put("Congela", "Freeze");
        FiltroASQL.put("Cura", "Heal");
        FiltroASQL.put("Crea", "Make");
        FiltroASQL.put("Mueve", "Move");
        FiltroASQL.put("No puede ser da�ado", "Can't be hurt");
        FiltroASQL.put("Baraja", "Shuffle");
        FiltroASQL.put("Transforma", "Transform");
        FiltroASQL.put("Dinorrugido", "Dino-Roar");
        FiltroASQL.put("Evoluci�n", "Evolution");
        FiltroASQL.put("Fusi�n", "Fusion");
        // QUE PASA CON SHIELDED

        // Valores de habilidades
        FiltroASQL.put("Al entrar en juego", "When played");
        FiltroASQL.put("Al ser destruido", "When destroyed");
        FiltroASQL.put("Antes del combate en esta l�nea", "Before combat here");
        FiltroASQL.put("Mientras est� en un Entorno", "While in an Environment");
        FiltroASQL.put("Al sufrir da�o", "When hurt");
        FiltroASQL.put("Cuando juegas X", "When you play");
        FiltroASQL.put("Cuando tus X sufran da�o", "When your % get hurt");
        FiltroASQL.put("Cuando un zombie le inflinge da�o", "When a Zombie hurt this");
        FiltroASQL.put("Cuando entra en juego detr�s de una Planta", "When played behind a Plant");
        FiltroASQL.put("Al comienzo del Turno", "Start of Turn");
        FiltroASQL.put("Cuando juegas X en su l�nea", "When you play % here");
        FiltroASQL.put("Cuando X sea destruido", "When a% is destroyed");
        FiltroASQL.put("Cuando otro X inflinja da�o", "When another Berry"); // Posible revisi�n
        FiltroASQL.put("Al entrar en juego en el Suelo", "When played on the Ground");
        FiltroASQL.put("Mientras est� en tu mano", "While in your hand");
        FiltroASQL.put("Cuando inflinge da�o", "When this does damage");
        FiltroASQL.put("Cuando X sufra da�o y sobreviva", "Whenever % is hurt and survives");
        FiltroASQL.put("Despu�s de combatir en su l�nea", "After combat here");
        FiltroASQL.put("Al jugarse en Alturas", "When played on Heights");
        FiltroASQL.put("Al jugarse al lado de X", "When played next to %");
        FiltroASQL.put("Cuando juegues un truco", "When you play a Trick");
        FiltroASQL.put("Cuando X realice un Ataque Extra", "When % does a Bonus Attack");
        FiltroASQL.put("Cuando juegas X en su l�nea o en las de al lado", "when you play % here or next door");
        FiltroASQL.put("Cuando juegas X en alturas", "When you play % on the Heights");
        FiltroASQL.put("Cuando inflinge da�o al H�roe", "When this hurts the % Hero");
        FiltroASQL.put("Cuando inflinge da�o a X", "When this hurts a %");
        FiltroASQL.put("Cuando X en esta l�nea inflinge da�o al H�roe", "When % here hurts the % Hero");
        FiltroASQL.put("Al jugarse en un Entorno", "When played in an Environment");
        FiltroASQL.put("Cuando X es congelado", "When a %s frozen");
        FiltroASQL.put("Al final del Turno", "End of Turn");
        FiltroASQL.put("Cuando X de su l�nea inflinge da�o y sobrevive", "When a % here does damage and survives");
        FiltroASQL.put("Cuando X o tu H�roe sea curado", "When a % or your Hero is healed");
        FiltroASQL.put("Cuando X inflinge da�o a Y", "When % hurts %");
        FiltroASQL.put("Al revelarse", "When revealed");
        FiltroASQL.put("Cuando X con [habilidad] destruye Y", "When % with % destroys");
        FiltroASQL.put("Cuando X entra o sale de esta l�nea", "When % enters or leaves this lane");
        FiltroASQL.put("Cuando X entra en esta l�nea", "When % enters a lane");
        FiltroASQL.put("Cuando X destruye una Planta", "When % destroys a plant");
        FiltroASQL.put("Cuando se juega un Truco X", "When a % Trick is played");
        FiltroASQL.put("Al comienzo de los Trucos", "Start of the Tricks");
        FiltroASQL.put("Al jugarse en Alturas o en un Entorno", "When played on Heights or an Environment");
        FiltroASQL.put("Al poner en juego el primer Truco en cada Turno", "When you play your first Trick each Turn");
        FiltroASQL.put("Cuando el H�roe Planta roba una carta", "When the Plant Hero draws a card");
        FiltroASQL.put("Cuando se juegue una planta en esta l�nea", "When a Plant is played here");
        FiltroASQL.put("Cuando un Zombie sea revelado en esta l�nea", "When a Zombie is revealed from a Gravestone here");
        FiltroASQL.put("Al revelarse en un entorno", "When revealed in an Environment");
        FiltroASQL.put("Cuando X sufra da�o", "When % is hurt");
        
              
        // Valores de rareza ("Sin rareza" no indicado)
        FiltroASQL.put("Com�n", "Common");
        FiltroASQL.put("Infrecuente", "Uncommon");
        FiltroASQL.put("Rara", "Rare");
        FiltroASQL.put("Superrara", "Super-Rare");
        FiltroASQL.put("Legendaria", "Legendary");

        // Valores de mazo ("Sin set" no indicado)
        FiltroASQL.put("B�sica", "Basic");
        FiltroASQL.put("Pr�mium", "Premium");
        FiltroASQL.put("Colosal", "Colossal");
        FiltroASQL.put("Gal�ctica", "Galactic");
        FiltroASQL.put("Tri�sica", "Triasic");
        FiltroASQL.put("Evento", "Event");

        // Valores de tipo
        FiltroASQL.put("Planta", "plants");
        FiltroASQL.put("Zombie", "zombies");

        // AND y OR
        FiltroASQL.put("Necesaria", " AND ");
        FiltroASQL.put("Alternativa", " OR ");

        // Funciones de grupo
        FiltroASQL.put("Conteo", "COUNT ");
        FiltroASQL.put("Media", "AVG ");
        FiltroASQL.put("M�ximo", "MAX ");
        FiltroASQL.put("M�nimo", "MIN ");
        
        
    }

    public static String TextoASQL(String clave) {
        return FiltroASQL.get(clave);
    }

}
