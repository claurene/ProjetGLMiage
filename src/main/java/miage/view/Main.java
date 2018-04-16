package miage.view;

import miage.model.*;

import java.util.ArrayList;

public class Main {

    /*
     * Lancement du programme principal
     */
    public static void main(String args[]){

        // Création des stations de métro
        System.out.println("Initialisation des données...");

        // Ligne 1

        ArrayList<Station> stations1 = new ArrayList<Station>();

        Station s11 = new Station("Château de Vincennes",2,false,48.844,2.442);
		stations1.add(s11);
        Station s12 = new Station("Berault",2,false,48.845,2.427);
		stations1.add(s12);
        Station s13 = new Station("Saint-Mandé",2,false,48.846,2.418);
		stations1.add(s13);
        Station s14 = new Station("Porte de Vincennes",2,false,48.847,2.410);
		stations1.add(s14);
        Station s15 = new Station("Nation",2,false,48.848,2.396);
		stations1.add(s15);
        Station s16 = new Station("Reuilly-Diderot",2,false,48.847,2.385);
		stations1.add(s16);
        Station s17 = new Station("Gare de Lyon",2,false,48.843,2.373);
		stations1.add(s17);
        Station s18 = new Station("Bastille",2,false,48.845,2.44);
		stations1.add(s18);
        Station s19 = new Station("Saint-Paul",2,false,48.853,2.369);
		stations1.add(s19);
        Station s110 = new Station("Hotel de ville",2,false,48.856,2.350);
		stations1.add(s110);
        Station s111 = new Station("Châtelet",2,false,48.858,2.348);
		stations1.add(s111);
        Station s112 = new Station("Louvre-Rivoli",2,false,48.861,2.341);
		stations1.add(s112);
        Station s113 = new Station("Palais-Royal",2,false,48.863,2.335);
		stations1.add(s113);
        Station s114 = new Station("Tuileries",2,false,48.864,2.329);
		stations1.add(s114);
        Station s115 = new Station("Concorde",2,false,48.865,2.321);
		stations1.add(s115);
        Station s116 = new Station("Champs-Elysées-Clémenceau",2,false,48.867,2.311);
		stations1.add(s116);
        Station s117 = new Station("Franklin-Roosevelt",2,false,48.868,2.307);
		stations1.add(s117);
        Station s118 = new Station("George V",2,false,48.872,2.298);
		stations1.add(s118);
        Station s119 = new Station("Charles de Gaulle-Étoile",2,false,48.874,2.292);
		stations1.add(s119);
        Station s120 = new Station("Argentine",2,false,48.875,2.287);
		stations1.add(s120);
        Station s121 = new Station("Porte Maillot",2,false,48.879,2.282);
		stations1.add(s121);
        Station s122 = new Station("Les Sablons",2,false,48.881,2.270);
		stations1.add(s122);
        Station s123 = new Station("Pont de Neuilly",2,false,48.885,2.257);
		stations1.add(s123);
        Station s124 = new Station("Esplanade de la Défense",2,false,48.888,2.247);
		stations1.add(s124);
        Station s125 = new Station("La Défense",2,false,48.892,2.236);
		stations1.add(s125);

        Ligne l1 = new Ligne(1,"Métro 1",50,stations1);

        // Ligne 2

        ArrayList<Station> stations2 = new ArrayList<Station>();

        //Station s21 = s15;
        stations2.add(s15);
        Station s22 = new Station("Avron",2,false,48.851,2.396);
		stations2.add(s22);
        Station s23 = new Station("Alexandre-Dumas",2,false,48.856,2.392);
		stations2.add(s23);
        Station s24 = new Station("Philippe Auguste",2,false,48.858,2.388);
		stations2.add(s24);
        Station s25 = new Station("Père-Lachaise",2,false,48.863,2.385);
		stations2.add(s25);
        Station s26 = new Station("Menilmontant",2,false,48.866,2.381);
		stations2.add(s26);
        Station s27 = new Station("Couronnes",2,false,48.869,2.378);
		stations2.add(s27);
        Station s28 = new Station("Belleville",2,false,48.872,2.374);
		stations2.add(s28);
        Station s29 = new Station("Colonel Fabien",2,false,48.877,2.368);
		stations2.add(s29);
        Station s210 = new Station("Jaurès",2,false,48.881,2.368);
		stations2.add(s210);
        Station s211 = new Station("Stalingrad",2,false,48.884,2.363);
		stations2.add(s211);
        Station s212 = new Station("La Chapelle",2,false,48.884,2.358);
		stations2.add(s212);
        Station s213 = new Station("Barbes-Rochechouart",2,false,48.883,2.348);
		stations2.add(s213);
        Station s214 = new Station("Anvers",2,false,48.882,2.342);
		stations2.add(s214);
        Station s215 = new Station("Pigalle",2,false,48.882,2.335);
		stations2.add(s215);
        Station s216 = new Station("Blanche",2,false,48.883,2.330);
		stations2.add(s216);
        Station s217 = new Station("Place de Clichy",2,false,48.883,2.325);
		stations2.add(s217);
        Station s218 = new Station("Rome",2,false,48.882,2.319);
		stations2.add(s218);
        Station s219 = new Station("Villiers",2,false,48.881,2.313);
		stations2.add(s219);
        Station s220 = new Station("Monceau",2,false,48.880,2.307);
		stations2.add(s220);
        Station s221 = new Station("Courcelles",2,false,48.879,2.301);
		stations2.add(s221);
        Station s222 = new Station("Ternes",2,false,48.878,2.298);
		stations2.add(s222);
        //Station s223 = s119;
		stations2.add(s119);
        Station s224 = new Station("Victor Hugo",2,false,48.869,2.283);
		stations2.add(s224);
        Station s225 = new Station("Porte Dauphine",2,false,48.871,2.275);
		stations2.add(s225);

        Ligne l2 = new Ligne(2,"Métro 2",50,stations2);

        // Ligne 3

        ArrayList<Station> stations3 = new ArrayList<Station>();

        Station s31 = new Station("Gallieni",2,false,48.865,2.414);
		stations3.add(s31);
        Station s32 = new Station("Porte de Bagnolet",2,false,48.864,2.407);
		stations3.add(s32);
        Station s33 = new Station("Gambetta",2,false,48.865,2.397);
		stations3.add(s33);
        //PEre lachaise s34;
		stations3.add(s25);
        Station s35 = new Station("Rue Saint-Maur",2,false,48.864,2.376);
		stations3.add(s35);
        Station s36 = new Station("Parmentier",2,false,48.865,2.372);
		stations3.add(s36);
        Station s37 = new Station("République",2,false,48.867,2.361);
		stations3.add(s37);
        Station s38 = new Station("Temple",2,false,48.866,2.358);
		stations3.add(s38);
        Station s39 = new Station("Arts-et-Métiers",2,false,48.865,2.353);
		stations3.add(s39);
        Station s310 = new Station("Reaumur-Sebastopol",2,false,48.866,2.350);
		stations3.add(s310);
        Station s311 = new Station("Sentier",2,false,48.867,2.345);
		stations3.add(s311);
        Station s312 = new Station("Bourse",2,false,48.868,2.338);
		stations3.add(s312);
        Station s313 = new Station("Quatre Septembre",2,false,48.869,2.333);
		stations3.add(s313);
        Station s314 = new Station("Opéra",2,false,48.870,2.330);
		stations3.add(s314);
        Station s315 = new Station("Havre-Caumartin",2,false,48.873,2.326);
		stations3.add(s315);
        Station s316 = new Station("Saint-Lazare",2,false,48.875,2.322);
		stations3.add(s316);
        Station s317 = new Station("Europe",2,false,48.878,2.321);
		stations3.add(s317);
        Station s318 = new Station("Villiers",2,false,48.881,2.313);
		stations3.add(s318);
        Station s319 = new Station("Malesherbes",2,false,48.883,2.306);
		stations3.add(s319);
        Station s320 = new Station("Wagram",2,false,48.884,2.301);
		stations3.add(s320);
        Station s321 = new Station("Pereire",2,false,48.884,2.295);
		stations3.add(s321);
        Station s322 = new Station("Porte de Champerret",2,false,48.885,2.290);
		stations3.add(s322);
        Station s323 = new Station("Louise Michel",2,false,48.888,2.285);
		stations3.add(s323);
        Station s324 = new Station("Anatole France",2,false,48.891,2.283);
		stations3.add(s324);
        Station s325 = new Station("Pont de Levallois-Becon",2,false,48.897,2.278);
		stations3.add(s325);

        Ligne l3 = new Ligne(3,"Métro 3",50,stations3);

        // Ligne 4

        ArrayList<Station> stations4 = new ArrayList<Station>();

        Station s41 = new Station("Porte de Clignancourt",2,false,48.897,2.342);
		stations4.add(s41);
        Station s42 = new Station("Simplon",2,false,48.893,2.345);
		stations4.add(s42);
        Station s43 = new Station("Marcadet-Poissonniers",2,false,48.890,2.347);
		stations4.add(s43);
        Station s44 = new Station("Château Rouge",2,false,48.887,2.347);
		stations4.add(s44);
        //Barbes Rochecouart s45;
		stations4.add(s213);
        Station s46 = new Station("Gare du Nord",2,false,48.879,2.353);
		stations4.add(s46);
        Station s47 = new Station("Gare de l'Est",2,false,48.876,2.358);
		stations4.add(s47);
        Station s48 = new Station("Château d'Eau",2,false,48.872,2.353);
		stations4.add(s48);
        Station s49 = new Station("Strasbourg-Saint-Denis",2,false,48.868,2.351);
		stations4.add(s49);
        //Reaumur s410;
		stations4.add(s310);
        Station s411 = new Station("Etienne Marcel",2,false,48.863,2.346);
		stations4.add(s411);
        Station s412 = new Station("Les Halles",2,false,48.862,2.343);
		stations4.add(s412);
        //chatelet s413;
		stations4.add(s111);
        Station s414 = new Station("Cité",2,false,48.855,2.344);
		stations4.add(s414);
        Station s415 = new Station("Saint-Michel",2,false,48.853,2.342);
		stations4.add(s415);
        Station s416 = new Station("Saint-Germain des Près",2,false,48.853,2.332);
		stations4.add(s416);
        Station s417 = new Station("Saint-Sulpice",2,false,48.850,2.330);
		stations4.add(s417);
        Station s418 = new Station("Saint-Placide",2,false,48.846,2.324);
		stations4.add(s418);
        Station s419 = new Station("Montparnasse-Bienvenue",2,false,48.843,2.323);
		stations4.add(s419);
        Station s420 = new Station("Vavin",2,false,48.842,2.326);
		stations4.add(s420);
        Station s421 = new Station("Raspail",2,false,48.838,2.328);
		stations4.add(s421);
        Station s422 = new Station("Denfert-Rochereau",2,false,48.833,2.331);
		stations4.add(s422);
        Station s423 = new Station("Mouton-Duvernet",2,false,48.831,2.327);
		stations4.add(s423);
        Station s424 = new Station("Alesia",2,false,48.828,2.324);
		stations4.add(s424);
        Station s425 = new Station("Porte d'Orléans",2,false,48.823,2.323);
		stations4.add(s425);
        Station s426 = new Station("Mairie de Montrouge",2,false,48.818,2.318);
		stations4.add(s426);

        Ligne l4 = new Ligne(4,"Métro 4",50,stations4);

        System.out.println("Initialisation terminée");
    }
}
