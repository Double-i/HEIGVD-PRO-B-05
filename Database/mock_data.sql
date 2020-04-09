

insert into tag (name) values("jardin"),("bricolage"),("sport"),("dessin"),("skateboard"),("electricite"),("bois"),("neige"),("ete"),("hiver");

insert into country(country) values("Suisse");

insert into city(city,fkCountry) values("Glion",1);
insert into city(city,fkCountry) values("Lausanne",1);
insert into city(city,fkCountry) values("Martigny",1);
insert into city(city,fkCountry) values("Yverdon-Les-Bains",1);
insert into city(city,fkCountry) values("Cossonay",1);
insert into city(city,fkCountry) values("Nyon",1);

insert into address(address,district,postalcode,lat,lng,fkCity) values ("Route de valmont","Vaud","1823",46.4308093,6.9241496,1);
insert into address(address,district,postalcode,lat,lng,fkCity) values ("Rue des Laurelles 5","Vaud","1304",46.6129732,6.507043,5);
insert into address(address,district,postalcode,lat,lng,fkCity) values ("Maison rouget","Vaud","1400",46.7792522,6.6431692,4);
insert into address(address,district,postalcode,lat,lng,fkCity) values ("Place de la Cathédrale","Vaud","1005",46.5179283,6.6358628,2);
insert into address(address,district,postalcode,lat,lng,fkCity) values ("Avanue de la Gare 5","Valais","1920",46.0972172,7.0747864,3);
insert into address(address,district,postalcode,lat,lng,fkCity) values ("Allée de la Petite Prairie 14","Vaud","1260",46.3884263,6.221818,6);

insert into user (userName,firstName,lastName,password,isAdmin,email,fkAddress) values("heymanuel","emmanuel","janssens","password",0,"emmanuel.janssens@heig-vd.ch",1);
insert into user (userName,firstName,lastName,password,isAdmin,email,fkAddress) values("fukuchimiste","bastien","potet","password",0,"bastien.potet@heig-vd.ch",5);
insert into user (userName,firstName,lastName,password,isAdmin,email,fkAddress) values("double-i","Ilias","Goujgali","password",1,"Ilias.Goujgali@heig-vd.ch",3);
insert into user (userName,firstName,lastName,password,isAdmin,email,fkAddress) values("robinr","Robin","Reuteler","password",0,"Robin.Reuteler@heig-vd.ch",6);
insert into user (userName,firstName,lastName,password,isAdmin,email,fkAddress) values("vitorvaz","Vitor","Vaz Afomzo","password",0,"Vitor.vazafonzo@heig-vd.ch",2);
insert into user (userName,firstName,lastName,password,isAdmin,email,fkAddress) values("mauricel","Maurice","Lehman","password",0,"Maurice.Lehman@heig-vd.ch",4);

insert into ezobject (name,description,owner) values("pelle","pelle de jardinage en bon etat","fukuchimiste");
insert into ezobject (name,description,owner) values("perceuse","super perceuse","double-i");
insert into ezobject (name,description,owner) values("arc","un arc de bonne qualité","vitorvaz");
insert into ezobject (name,description,owner) values("skateboard","un skate board de bonne qualité peu utilise","heymanuel");
insert into ezobject (name,description,owner) values("raquette de ping pong","peu utilisé","fukuchimiste");
insert into ezobject (name,description,owner) values("guitare","guitare classique","mauricel");
insert into ezobject (name,description,owner) values("jumelle","de bonnes jumelles","robinr");
insert into ezobject (name,description,owner) values("sac a dos","sac a dos de randonnee","heymanuel");
insert into ezobject (name,description,owner) values("scie","fournis avec des lames","double-i");
insert into ezobject (name,description,owner) values("raclette ","pour vos diner en montagne","robinr");
insert into ezobject (name,description,owner) values("drapeau suisse","montrez votre fiereté nimporte ou vous allez","fukuchimiste");
insert into ezobject (name,description,owner) values("haut parleur","besoin de musique pour vos soirées?","heymanuel");
insert into ezobject (name,description,owner) values("agrapheuse","puissant ","mauricel");

insert into ezobjecttag(fkTag,fkEZObject) values("jardin",1);
insert into ezobjecttag(fkTag,fkEZObject) values("bricolage",1);
insert into ezobjecttag(fkTag,fkEZObject) values("bricolage",2);
insert into ezobjecttag(fkTag,fkEZObject) values("bricolage",9);
insert into ezobjecttag(fkTag,fkEZObject) values("bricolage",13);
insert into ezobjecttag(fkTag,fkEZObject) values("sport",4);
insert into ezobjecttag(fkTag,fkEZObject) values("skateboard",4);
insert into ezobjecttag(fkTag,fkEZObject) values("ete",1);
insert into ezobjecttag(fkTag,fkEZObject) values("ete",8);

insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-03-30","2020-04-10",null,"pending","vitorvaz",1);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-04-10","2020-05-1",null,"pending","heymanuel",11);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-04-20","2020-05-2",null,"pending","double-i",7);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-05-20","2020-06-1",null,"pending","robinr",9);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-03-27","2020-04-3",null,"pending","mauricel",10);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-08-10","2020-08-20",null,"pending","fukuchimiste",2);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-04-27","2020-05-7",null,"pending","heymanuel",3);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-10-2","2020-10-10",null,"pending","double-i",8);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-8-1","2020-8-13",null,"pending","vitorvaz",12);
insert into loan(dateStart, dateEnd,dateReturn, state,borrower, fkEZObject) values("2020-9-5","2020-9-20",null,"pending","mauricel",5);