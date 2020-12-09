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
        FiltroASQL.put("Guardián", "Guardian");
        FiltroASQL.put("Solar", "Solar");
        FiltroASQL.put("Astuto", "Smarty");
        FiltroASQL.put("Bestial", "Beastly");
        FiltroASQL.put("Cerebral", "Brainy");
        FiltroASQL.put("Furtivo", "Sneaky");
        FiltroASQL.put("Loco", "Crazy");
        FiltroASQL.put("Valeroso", "Hearty");

        // Valores de tribu (animal, banana y cactus no se han incluído por ser similares)
        FiltroASQL.put("Tonel", "Barrel");
        FiltroASQL.put("Reloj", "Clock");
        FiltroASQL.put("Bailarín", "Dancing");
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
        FiltroASQL.put("Maíz", "Corn");
        FiltroASQL.put("Dragón", "Dragon");
        FiltroASQL.put("Atrapamoscas", "Flytrap");
        FiltroASQL.put("Flor", "Flower");
        FiltroASQL.put("Fruta", "Fruit");
        FiltroASQL.put("Hoja", "Leaf");
        FiltroASQL.put("Musgo", "Moss");
        FiltroASQL.put("Seta", "Mushroom");
        FiltroASQL.put("Nuez", "Nut");
        FiltroASQL.put("Guisante", "Pea");
        FiltroASQL.put("Piña", "Pinecone");
        FiltroASQL.put("Raíz", "Root");
        FiltroASQL.put("Apisonaflor", "Squash");
        FiltroASQL.put("Árbol", "Tree");
        FiltroASQL.put("Semilla", "Seed");

        // Valores de atributo
        FiltroASQL.put("Doble Golpe", "Double Strike");
        FiltroASQL.put("Daño de área", "Splash Damage");
        FiltroASQL.put("Anti-Héroe", "Anti-Hero");
        FiltroASQL.put("Blindaje", "Armored");
        FiltroASQL.put("Impacto perforante", "Strikethrough");
        FiltroASQL.put("Equipo", "Team-Up");
        FiltroASQL.put("Anfibio", "Amphibious");
        FiltroASQL.put("Diana", "Bullseye");
        FiltroASQL.put("Intrucable", "Untrickable");
        FiltroASQL.put("Frenesí", "Frenzy");
        FiltroASQL.put("Rebasamiento", "Overshoot");
        FiltroASQL.put("Lápida", "Gravestone");
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
        FiltroASQL.put("No puede ser dañado", "Can't be hurt");
        FiltroASQL.put("Baraja", "Shuffle");
        FiltroASQL.put("Transforma", "Transform");
        FiltroASQL.put("Dinorrugido", "Dino-Roar");
        FiltroASQL.put("Evolución", "Evolution");
        FiltroASQL.put("Fusión", "Fusion");
        // QUE PASA CON SHIELDED

        // Valores de habilidades
        FiltroASQL.put("Al entrar en juego", "When played");
        FiltroASQL.put("Al ser destruido", "When destroyed");
        FiltroASQL.put("Antes del combate en esta línea", "Before combat here");
        FiltroASQL.put("Mientras esté en un Entorno", "While in an Environment");
        FiltroASQL.put("Al sufrir daño", "When hurt");
        FiltroASQL.put("Cuando juegas X", "When you play");
        FiltroASQL.put("Cuando tus X sufran daño", "When your % get hurt");
        FiltroASQL.put("Cuando un zombie le inflinge daño", "When a Zombie hurt this");
        FiltroASQL.put("Cuando entra en juego detrás de una Planta", "When played behind a Plant");
        FiltroASQL.put("Al comienzo del Turno", "Start of Turn");
        FiltroASQL.put("Cuando juegas X en su línea", "When you play % here");
        FiltroASQL.put("Cuando X sea destruido", "When a% is destroyed");
        FiltroASQL.put("Cuando otro X inflinja daño", "When another Berry"); // Posible revisión
        FiltroASQL.put("Al entrar en juego en el Suelo", "When played on the Ground");
        FiltroASQL.put("Mientras esté en tu mano", "While in your hand");
        FiltroASQL.put("Cuando inflinge daño", "When this does damage");
        FiltroASQL.put("Cuando X sufra daño y sobreviva", "Whenever % is hurt and survives");
        FiltroASQL.put("Después de combatir en su línea", "After combat here");
        FiltroASQL.put("Al jugarse en Alturas", "When played on Heights");
        FiltroASQL.put("Al jugarse al lado de X", "When played next to %");
        FiltroASQL.put("Cuando juegues un truco", "When you play a Trick");
        FiltroASQL.put("Cuando X realice un Ataque Extra", "When % does a Bonus Attack");
        FiltroASQL.put("Cuando juegas X en su línea o en las de al lado", "when you play % here or next door");
        FiltroASQL.put("Cuando juegas X en alturas", "When you play % on the Heights");
        FiltroASQL.put("Cuando inflinge daño al Héroe", "When this hurts the % Hero");
        FiltroASQL.put("Cuando inflinge daño a X", "When this hurts a %");
        FiltroASQL.put("Cuando X en esta línea inflinge daño al Héroe", "When % here hurts the % Hero");
        FiltroASQL.put("Al jugarse en un Entorno", "When played in an Environment");
        FiltroASQL.put("Cuando X es congelado", "When a %s frozen");
        FiltroASQL.put("Al final del Turno", "End of Turn");
        FiltroASQL.put("Cuando X de su línea inflinge daño y sobrevive", "When a % here does damage and survives");
        FiltroASQL.put("Cuando X o tu Héroe sea curado", "When a % or your Hero is healed");
        FiltroASQL.put("Cuando X inflinge daño a Y", "When % hurts %");
        FiltroASQL.put("Al revelarse", "When revealed");
        FiltroASQL.put("Cuando X con [habilidad] destruye Y", "When % with % destroys");
        FiltroASQL.put("Cuando X entra o sale de esta línea", "When % enters or leaves this lane");
        FiltroASQL.put("Cuando X entra en esta línea", "When % enters a lane");
        FiltroASQL.put("Cuando X destruye una Planta", "When % destroys a plant");
        FiltroASQL.put("Cuando se juega un Truco X", "When a % Trick is played");
        FiltroASQL.put("Al comienzo de los Trucos", "Start of the Tricks");
        FiltroASQL.put("Al jugarse en Alturas o en un Entorno", "When played on Heights or an Environment");
        FiltroASQL.put("Al poner en juego el primer Truco en cada Turno", "When you play your first Trick each Turn");
        FiltroASQL.put("Cuando el Héroe Planta roba una carta", "When the Plant Hero draws a card");
        FiltroASQL.put("Cuando se juegue una planta en esta línea", "When a Plant is played here");
        FiltroASQL.put("Cuando un Zombie sea revelado en esta línea", "When a Zombie is revealed from a Gravestone here");
        FiltroASQL.put("Al revelarse en un entorno", "When revealed in an Environment");
        FiltroASQL.put("Cuando X sufra daño", "When % is hurt");
        
              
        // Valores de rareza ("Sin rareza" no indicado)
        FiltroASQL.put("Común", "Common");
        FiltroASQL.put("Infrecuente", "Uncommon");
        FiltroASQL.put("Rara", "Rare");
        FiltroASQL.put("Superrara", "Super-Rare");
        FiltroASQL.put("Legendaria", "Legendary");

        // Valores de mazo ("Sin set" no indicado)
        FiltroASQL.put("Básica", "Basic");
        FiltroASQL.put("Prémium", "Premium");
        FiltroASQL.put("Colosal", "Colossal");
        FiltroASQL.put("Galáctica", "Galactic");
        FiltroASQL.put("Triásica", "Triasic");
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
        FiltroASQL.put("Máximo", "MAX ");
        FiltroASQL.put("Mínimo", "MIN ");
        
        
    }

    public static String TextoASQL(String clave) {
        return FiltroASQL.get(clave);
    }

}
